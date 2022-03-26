package repositpries;

import models.Bank;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BankRepository {
    private Connection connection;

    public BankRepository() throws SQLException, ClassNotFoundException {
        this.connection = Singleton.getInstance().getConnection();
    }

    public Long save(Bank bank) {
        try {
            String insertBank = "INSERT INTO bank (bank_name) VALUES (?) returning id ";
            PreparedStatement preparedStatement = connection.prepareStatement(insertBank);
            preparedStatement.setString(1, bank.getName());
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return resultSet.getLong("id");
            }
        }catch (SQLException exception){
            System.out.println(exception.getMessage());
        }
        return null;
    }

    public Bank read(Long id) {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("SELECT * FROM bank WHERE id = ? ");
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                return new Bank(resultSet.getLong("id"),
                        resultSet.getString("bank_name"));
            }
        }catch (SQLException exception){
            System.out.println(exception.getMessage());
        }
        return null;
    }

    public Bank readByName(String name) throws SQLException, ClassNotFoundException {
        connection = Singleton.getInstance().getConnection();
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("SELECT * FROM bank WHERE bank_name = ? ");
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                return new Bank(resultSet.getLong("id"),
                        resultSet.getString("bank_name"));
            }

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}
