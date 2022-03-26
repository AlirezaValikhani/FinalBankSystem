package repositpries;

import models.Transaction;
import models.TransactionType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionRepository implements BaseRepository<Transaction> {
    private Connection connection;

    public TransactionRepository() {
        this.connection = Singleton.getInstance().getConnection();
    }

    @Override
    public Long save(Transaction transaction) {
        try {
            connection = Singleton.getInstance().getConnection();
            PreparedStatement ps = connection
                    .prepareStatement(" INSERT INTO transaction_table (account_number,origin_card_number" +
                            ",destination_card_number,amount,date,time,transaction_type) " +
                            "VALUES (?, ?, ?, ?, NOW(), NOW() ,?) returning id");
            ps.setString(1, transaction.getAccountNumber());
            ps.setString(2, transaction.getOriginCardNumber());
            ps.setString(3, transaction.getDestinationCardNumber());
            ps.setString(4, transaction.getAmount());
            ps.setString(5, String.valueOf(transaction.getTransactionType()));
            ResultSet resultSet = ps.executeQuery();
            if(resultSet.next()){
                return resultSet.getLong("id");
            }
        }catch (SQLException exception){
            System.out.println(exception.getMessage());
        }
        return null;
    }

    @Override
    public Transaction read(Transaction transaction) {
        try {
            String find = "SELECT * FROM transaction_table WHERE id = ? ";
            PreparedStatement preparedStatement = connection.prepareStatement(find);
            preparedStatement.setLong(1, transaction.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            return mapTo(resultSet);
        }catch (SQLException exception){
            System.out.println(exception.getMessage());
        }
        return null;
    }

    @Override
    public List<Transaction> readAll() {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("SELECT * FROM transaction_table ");
            ResultSet resultSet = preparedStatement.executeQuery();
            return mapToList(resultSet);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public void update(Transaction transaction) {
        try {
            PreparedStatement ps = connection
                    .prepareStatement("UPDATE transaction_table SET account_number = ? , origin_card_number = ? ," +
                            "destination_card_number = ? , amount = ? , date = NOW() , time = NOW() , transaction_type = ? WHERE id = ?");
            ps.setString(1, transaction.getAccountNumber());
            ps.setString(2, transaction.getOriginCardNumber());
            ps.setString(3, transaction.getDestinationCardNumber());
            ps.setString(4, transaction.getAmount());
            ps.setString(5, String.valueOf(transaction.getTransactionType()));
            ps.setLong(6,transaction.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(Transaction transaction) {
        try {
            PreparedStatement ps = connection
                    .prepareStatement("DELETE FROM transaction_table WHERE id = ?");
            ps.setLong(1, transaction.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Transaction mapTo(ResultSet resultSet) {
        try {
            if (resultSet.next()) {
                return new Transaction(resultSet.getLong("id"),
                        resultSet.getString("account_number"),
                        resultSet.getString("origin_card_number"),
                        resultSet.getString("destination_card_number"),
                        resultSet.getString("amount"),
                        resultSet.getDate("date"),
                        resultSet.getTime("time"),
                        TransactionType.valueOf(resultSet.getString("status")));
            }else return null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Transaction> mapToList(ResultSet resultSet) {
        List<Transaction> transactions = new ArrayList<>();
        try {
            if (resultSet.next()) {
                transactions.add(new Transaction(resultSet.getLong("id"),
                        resultSet.getString("account_number"),
                        resultSet.getString("origin_card_number"),
                        resultSet.getString("destination_card_number"),
                        resultSet.getString("amount"),
                        resultSet.getDate("date"),
                        resultSet.getTime("time"),
                        TransactionType.valueOf(resultSet.getString("status"))));
            }
            return transactions;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }



    public List<Transaction> findAllTransactions(String accountNumber, Date date) {
        try {
            String find = "SELECT * FROM transaction_table WHERE account_number = ? AND dateT >= ? ";
            PreparedStatement preparedStatement = connection.prepareStatement(find);
            preparedStatement.setString(1, accountNumber);
            preparedStatement.setDate(2, date);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Transaction> transactions = new ArrayList<>();
            while (resultSet.next()) {
                Transaction transaction = new Transaction(resultSet.getString("account_number"),
                        resultSet.getString("origin_card_number"),
                        resultSet.getString("destination_card_number"),
                        resultSet.getString("amount"),
                        resultSet.getDate("dateT"),
                        resultSet.getTime("timeT"),
                        TransactionType.valueOf(resultSet.getString("transaction_type"))
                        );
                transactions.add(transaction);
            }
            return transactions;
        }catch (SQLException exception){
            System.out.println(exception.getMessage());
        }
        return null;
    }
}
