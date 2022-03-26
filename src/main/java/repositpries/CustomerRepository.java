package repositpries;

import models.Customer;
import models.UserType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerRepository implements BaseRepository<Customer>{
    private Connection connection;

    public CustomerRepository() {
        this.connection = Singleton.getInstance().getConnection();
    }

    @Override
    public Long save(Customer customer) {
        try {
            connection = Singleton.getInstance().getConnection();
            PreparedStatement ps = connection
                    .prepareStatement(" INSERT INTO customer (full_name,national_code,branch_code,password,status) " +
                            "VALUES (?, ?, ?, ?, ?) returning id");
            ps.setString(1, customer.getFullName());
            ps.setString(2, customer.getNationalId());
            ps.setString(3, customer.getCodeBranch());
            ps.setString(4, customer.getPassword());
            ps.setString(5, String.valueOf(customer.getUserType()));
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
    public Customer read(Customer customer) {
        try {
            String find = "SELECT * FROM customer WHERE id = ? ";
            PreparedStatement preparedStatement = connection.prepareStatement(find);
            preparedStatement.setLong(1, customer.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            return mapTo(resultSet);
        }catch (SQLException exception){
            System.out.println(exception.getMessage());
        }
        return null;
    }

    public Customer readByNationalCode(String nationalCode) {
        try {
            String find = "SELECT * FROM customer WHERE national_code = ? ";
            PreparedStatement preparedStatement = connection.prepareStatement(find);
            preparedStatement.setString(1, nationalCode);
            ResultSet resultSet = preparedStatement.executeQuery();
            return mapTo(resultSet);
        }catch (SQLException exception){
            System.out.println(exception.getMessage());
        }
        return null;
    }

    @Override
    public List<Customer> readAll() {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("SELECT * FROM customer ");
            ResultSet resultSet = preparedStatement.executeQuery();
            return mapToList(resultSet);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public void update(Customer customer) {
        try {
            PreparedStatement ps = connection
                    .prepareStatement("UPDATE customer SET full_name = ? , national_code = ? , " +
                            " branch_code = ? , password = ? , status = ? WHERE id = ?");
            ps.setString(1, customer.getFullName());
            ps.setString(2, customer.getNationalId());
            ps.setString(3, customer.getCodeBranch());
            ps.setString(4, customer.getPassword());
            ps.setString(5, String.valueOf(customer.getUserType()));
            ps.setLong(6,customer.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(Customer customer) {
        try {
            PreparedStatement ps = connection
                    .prepareStatement("DELETE FROM customer WHERE id = ?");
            ps.setLong(1, customer.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Customer mapTo(ResultSet resultSet) {
        try {
            if (resultSet.next()) {
                return new Customer(resultSet.getLong("id"),
                        resultSet.getString("full_name"),
                        resultSet.getString("national_code"),
                        resultSet.getString("branch_code"),
                        resultSet.getString("password"),
                        UserType.valueOf(resultSet.getString("status")));
            }else return null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Customer> mapToList(ResultSet resultSet) {
        List<Customer> customers = new ArrayList<>();
        try {
            if (resultSet.next()) {
                customers.add(new Customer(resultSet.getLong("id"),
                        resultSet.getString("full_name"),
                        resultSet.getString("national_code"),
                        resultSet.getString("branch_code"),
                        resultSet.getString("password"),
                        UserType.valueOf(resultSet.getString("status"))));
            }
            return customers;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
