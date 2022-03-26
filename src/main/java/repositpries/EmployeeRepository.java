package repositpries;

import models.Employee;
import models.UserType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepository implements BaseRepository<Employee>{
    private Connection connection;

    public EmployeeRepository() {
        this.connection = Singleton.getInstance().getConnection();
    }

    @Override
    public Long save(Employee employee) {
        try {
            connection = Singleton.getInstance().getConnection();
            PreparedStatement ps = connection
                    .prepareStatement(" INSERT INTO employee (full_name,national_code,branch_code,password,status) " +
                            "VALUES (?, ?, ?, ?, ?) returning id");
            ps.setString(1, employee.getFullName());
            ps.setString(2, employee.getNationalId());
            ps.setString(3, employee.getCodeBranch());
            ps.setString(4, employee.getPassword());
            ps.setString(5, String.valueOf(employee.getUserType()));
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
    public Employee read(Employee employee) {
        try {
            String find = "SELECT * FROM employee WHERE id = ? ";
            PreparedStatement preparedStatement = connection.prepareStatement(find);
            preparedStatement.setLong(1, employee.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            return mapTo(resultSet);
        }catch (SQLException exception){
            System.out.println(exception.getMessage());
        }
        return null;
    }


    public Employee readByUserName(String nationalCode) {
        try {
            String find = "SELECT * FROM employee WHERE national_code = ? ";
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
    public List<Employee> readAll() {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("SELECT * FROM employee ");
            ResultSet resultSet = preparedStatement.executeQuery();
            return mapToList(resultSet);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public void update(Employee employee) {
        try {
            PreparedStatement ps = connection
                    .prepareStatement("UPDATE employee SET full_name = ? , national_code = ? , " +
                            " branch_code = ? , password = ? , status = ? WHERE id = ?");
            ps.setString(1, employee.getFullName());
            ps.setString(2, employee.getNationalId());
            ps.setString(3, employee.getCodeBranch());
            ps.setString(4, employee.getPassword());
            ps.setString(5, String.valueOf(employee.getUserType()));
            ps.setLong(6,employee.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(Employee employee) {
        try {
            PreparedStatement ps = connection
                    .prepareStatement("DELETE FROM employee WHERE id = ?");
            ps.setLong(1, employee.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Employee mapTo(ResultSet resultSet) {
        try {
            if (resultSet.next()) {
                    return new Employee(resultSet.getLong("id"),
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
    public List<Employee> mapToList(ResultSet resultSet) {
        List<Employee> employees = new ArrayList<>();
        try {
            if (resultSet.next()) {
                employees.add(new Employee(resultSet.getLong("id"),
                        resultSet.getString("full_name"),
                        resultSet.getString("national_code"),
                        resultSet.getString("branch_code"),
                        resultSet.getString("password"),
                        UserType.valueOf(resultSet.getString("status"))));
            }
            return employees;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
