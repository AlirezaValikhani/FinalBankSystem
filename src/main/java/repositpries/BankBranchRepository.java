package repositpries;

import models.BankBranch;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BankBranchRepository implements BaseRepository<BankBranch> {
    private Connection connection;

    public BankBranchRepository() {
        this.connection = Singleton.getInstance().getConnection();
    }

    @Override
    public Long save(BankBranch bankBranch) {
        try {
            connection = Singleton.getInstance().getConnection();
            PreparedStatement preparedStatement = connection
                    .prepareStatement(" INSERT INTO bank_branch (bank_name,branch_code,boss_full_name,national_code" +
                            ",password) VALUES (?, ?, ?, ?, ?) returning id");
            preparedStatement.setString(1, bankBranch.getBankName());
            preparedStatement.setString(2, bankBranch.getBranchCode());
            preparedStatement.setString(3, bankBranch.getBossFullName());
            preparedStatement.setString(4, bankBranch.getNationalCode());
            preparedStatement.setString(5, bankBranch.getPassword());
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return resultSet.getLong("id");
            }
        }catch (SQLException exception){
            System.out.println(exception.getMessage());
        }
        return null;
    }

    @Override
    public BankBranch read(BankBranch bankBranch) {
        try {
            String find = "SELECT * FROM bank_branch WHERE id = ? ";
            PreparedStatement preparedStatement = connection.prepareStatement(find);
            preparedStatement.setLong(1, bankBranch.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            return mapTo(resultSet);
        }catch (SQLException exception){
            System.out.println(exception.getMessage());
        }
        return null;
    }

    public BankBranch readByBranchCode(BankBranch bankBranch) {
        try {
            String find = "SELECT * FROM bank_branch WHERE branch_code = ? ";
            PreparedStatement preparedStatement = connection.prepareStatement(find);
            preparedStatement.setString(1, bankBranch.getBranchCode());
            ResultSet resultSet = preparedStatement.executeQuery();
            return mapTo(resultSet);
        }catch (SQLException exception){
            System.out.println(exception.getMessage());
        }
        return null;
    }

    @Override
    public List<BankBranch> readAll() {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("SELECT * FROM bank_branch ");
            ResultSet resultSet = preparedStatement.executeQuery();
            return mapToList(resultSet);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public void update(BankBranch bankBranch) {
        try {
            PreparedStatement ps = connection
                    .prepareStatement("UPDATE bank_branch SET bank_name = ? , branch_code = ? , " +
                            " boss_full_name = ? , national_code = ? , password = ? WHERE id = ?");
            ps.setString(1, bankBranch.getBankName());
            ps.setString(2, bankBranch.getBranchCode());
            ps.setString(3, bankBranch.getBossFullName());
            ps.setString(4, bankBranch.getNationalCode());
            ps.setString(5, bankBranch.getPassword());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(BankBranch bankBranch) {
        try {
            PreparedStatement ps = connection
                    .prepareStatement("DELETE FROM bank_branch WHERE id = ?");
            ps.setLong(1, bankBranch.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public BankBranch mapTo(ResultSet resultSet) {
        try {
            if (resultSet.next()) {
                return new BankBranch(resultSet.getLong("id"),
                        resultSet.getString("bank_name"),
                        resultSet.getString("branch_code"),
                        resultSet.getString("boss_full_name"),
                        resultSet.getString("national_code"),
                        resultSet.getString("password"));
            }else return null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<BankBranch> mapToList(ResultSet resultSet) {
        List<BankBranch> bankBranches = new ArrayList<>();
        try {
            if (resultSet.next()) {
                bankBranches.add(new BankBranch(resultSet.getLong("id"),
                        resultSet.getString("bank_name"),
                        resultSet.getString("branch_code"),
                        resultSet.getString("boss_full_name"),
                        resultSet.getString("national_code"),
                        resultSet.getString("password")));
            }
            return bankBranches;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String findCodeBranch(String nationalCode) throws SQLException {
        try{
            PreparedStatement ps = connection
                    .prepareStatement("select * from bank_branch where national_code = ?");
            ps.setString(1,nationalCode);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next())
                return resultSet.getString("branch_code");
            else return null;
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}
