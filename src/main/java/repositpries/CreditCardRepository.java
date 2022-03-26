package repositpries;

import models.AccountType;
import models.CreditCard;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CreditCardRepository implements BaseRepository<CreditCard>{
    private Connection connection;

    public CreditCardRepository() {
        this.connection = Singleton.getInstance().getConnection();
    }

    @Override
    public Long save(CreditCard creditCard) {
        try {
            connection = Singleton.getInstance().getConnection();
            PreparedStatement ps = connection
                    .prepareStatement(" INSERT INTO credit_card (account_number,card_number,cvv2,expire_date,status) " +
                            "VALUES (?, ?, ?, ?, ?) returning id");
            ps.setString(1, creditCard.getAccountNumber());
            ps.setString(2, creditCard.getCardNumber());
            ps.setString(3, creditCard.getCvv2());
            ps.setString(4, creditCard.getExpireDate());
            ps.setString(5, String.valueOf(creditCard.getAccountType()));
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
    public CreditCard read(CreditCard creditCard) {
        try {
            String find = "SELECT * FROM credit_card WHERE id = ? ";
            PreparedStatement preparedStatement = connection.prepareStatement(find);
            preparedStatement.setString(1, creditCard.getAccountNumber());
            ResultSet resultSet = preparedStatement.executeQuery();
            return mapTo(resultSet);
        }catch (SQLException exception){
            System.out.println(exception.getMessage());
        }
        return null;
    }

    public CreditCard readByCardNumber(String cardNumber) {
        try {
            String find = "SELECT * FROM credit_card WHERE card_number = ? ";
            PreparedStatement preparedStatement = connection.prepareStatement(find);
            preparedStatement.setString(1, cardNumber);
            ResultSet resultSet = preparedStatement.executeQuery();
            return mapTo(resultSet);
        }catch (SQLException exception){
            System.out.println(exception.getMessage());
        }
        return null;
    }

    @Override
    public List<CreditCard> readAll() {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("SELECT * FROM credit_card ");
            ResultSet resultSet = preparedStatement.executeQuery();
            return mapToList(resultSet);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public void update(CreditCard creditCard) {
        try {
            PreparedStatement ps = connection
                    .prepareStatement("UPDATE credit_card SET account_number = ? , card_number = ? , " +
                            " cvv2 = ? , expire_date = ? , status = ? WHERE id = ?");
            ps.setString(1, creditCard.getAccountNumber());
            ps.setString(2, creditCard.getCardNumber());
            ps.setString(3, creditCard.getCvv2());
            ps.setString(5, String.valueOf(creditCard.getAccountType()));
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(CreditCard creditCard) {
        try {
            PreparedStatement ps = connection
                    .prepareStatement("DELETE FROM credit_card WHERE id = ?");
            ps.setLong(1, creditCard.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public CreditCard mapTo(ResultSet resultSet) {
        try {
            if (resultSet.next()) {
                return new CreditCard(resultSet.getLong("id"),
                        resultSet.getString("account_number"),
                        resultSet.getString("card_number"),
                        resultSet.getString("cvv2"),
                        resultSet.getString("expire_date"),
                        AccountType.valueOf(resultSet.getString("status")));
            }else return null;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public List<CreditCard> mapToList(ResultSet resultSet) {
        List<CreditCard> creditCards = new ArrayList<>();
        try {
            if (resultSet.next()) {
                creditCards.add(new CreditCard(resultSet.getLong("id"),
                        resultSet.getString("account_number"),
                        resultSet.getString("card_number"),
                        resultSet.getString("cvv2"),
                        resultSet.getString("expire_date"),
                        AccountType.valueOf(resultSet.getString("status"))));
            }
            return creditCards;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Integer findActiveCard(String accountNumber) {
        try {
            PreparedStatement ps = connection
                    .prepareStatement("SELECT * FROM credit_card WHERE account_number = ? AND status = 'ACTIVE' ");
            ps.setString(1, accountNumber);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next())
                return 1;
            else
                return 0;
        }catch (SQLException exception){
            System.out.println(exception.getMessage());
        }
        return 0;
    }

    public List<CreditCard> readAllByAccountNumber(String accountNumber) {
        try {
            PreparedStatement ps = connection
                    .prepareStatement("SELECT * FROM credit_card WHERE account_number = ?");
            ps.setString(1,accountNumber);
            ResultSet resultSet = ps.executeQuery();
            return mapToList(resultSet);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<String> showCustomerCreditCards(String nationalCode) {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("select  * from credit_card INNER JOIN account ON account.account_number = " +
                            "credit_card.account_number where account.national_code = ? AND credit_card.status = 'ACTIVE' ");
            preparedStatement.setString(1, nationalCode);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.isBeforeFirst()) {
                return null;
            } else {
                System.out.println("Your cards : ");
                List<String> numberId = new ArrayList<>();
                while (resultSet.next()) {
                    System.out.println(resultSet.getInt("id") + ": " +
                            resultSet.getString("card_number"));
                    numberId.add(String.valueOf(resultSet.getInt("id")));
                }
                return numberId;
            }
        }catch (SQLException i){
            System.out.println(i.getMessage());
        }
        return null;
    }

    public void setPassword(int id,String password) {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("UPDATE credit_card SET password = ? WHERE id = ? ");
            preparedStatement.setString(1, password);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        }catch (SQLException exception){
            System.out.println(exception.getMessage());
        }
    }


    public List<String> returnCardInformation(int id) {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("SELECT * FROM credit_card WHERE id = ? ");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            List<String> result2 = new ArrayList<>();
            result2.add(resultSet.getString("account_number"));
            result2.add(resultSet.getString("cardNumber"));
            result2.add(resultSet.getString("password"));
            result2.add(resultSet.getString("expireDate"));
            result2.add(resultSet.getString("cvv2"));
            return result2;
        }catch (SQLException exception){
            System.out.println(exception.getMessage());
        }
        return null;
    }

    public void deactivateCreditCard(Long id){
        try{
            PreparedStatement ps = connection
                    .prepareStatement("update credit_card set status = 'INACTIVE' where id = ?");
            ps.setLong(1,id);
            ps.executeUpdate();
        }catch (SQLException i){
            System.out.println(i.getMessage());
        }
    }


    public boolean checkCardNumber(String cardNumber) {
        try {
            String check = "SELECT * FROM credit_card WHERE card_number = ? AND status = 'ACTIVE' ";
            PreparedStatement preparedStatement = connection.prepareStatement(check);
            preparedStatement.setString(1, cardNumber);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
                return true;
            else
                return false;
        }catch (SQLException exception){
            System.out.println(exception.getMessage());
        }
        return false;
    }
}
