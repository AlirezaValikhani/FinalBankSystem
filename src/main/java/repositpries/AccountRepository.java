package repositpries;

import models.Account;
import models.AccountType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountRepository implements BaseRepository<Account> {
    private Connection connection;

    public AccountRepository() {
        this.connection = Singleton.getInstance().getConnection();
    }

    @Override
    public Long save(Account account) {
        try {
            connection = Singleton.getInstance().getConnection();
            PreparedStatement preparedStatement = connection
                    .prepareStatement(" INSERT INTO account(branch_code,national_code,account_number,balance,status) " +
                            "VALUES (?, ?, ?, ?, ?) returning id");
            preparedStatement.setString(1, account.getBranchCode());
            preparedStatement.setString(2, account.getNationalCode());
            preparedStatement.setString(3, account.getAccountNumber());
            preparedStatement.setDouble(4, account.getBalance());
            preparedStatement.setString(5, String.valueOf(account.getAccountType()));
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getLong("id");
            }
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
        return null;
    }

    @Override
    public Account read(Account account) {
        try {
            String find = "SELECT * FROM account WHERE id = ? ";
            PreparedStatement preparedStatement = connection.prepareStatement(find);
            preparedStatement.setLong(1, account.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            return mapTo(resultSet);
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
        return null;
    }

    public Account readByAccountNumber(String account_number) {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("SELECT * FROM account WHERE account_number = ? ");
            preparedStatement.setString(1, account_number);
            ResultSet resultSet = preparedStatement.executeQuery();
            return mapTo(resultSet);
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
        return null;
    }

    @Override
    public List<Account> readAll() {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("SELECT * FROM account ");
            ResultSet resultSet = preparedStatement.executeQuery();
            return mapToList(resultSet);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Account> readAllByNationalCode(String nationalCode) {
        try {
            PreparedStatement ps = connection
                    .prepareStatement("SELECT * FROM account WHERE national_code = ? ");
            ps.setString(1, nationalCode);
            ResultSet resultSet = ps.executeQuery();
            return mapToList(resultSet);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public void update(Account account) {
        try {
            PreparedStatement ps = connection
                    .prepareStatement("UPDATE account SET branch_code = ? , national_code = ? , " +
                            " account_number = ? , balance = ? , status = ? WHERE id = ?");
            ps.setString(1, account.getBranchCode());
            ps.setString(2, account.getNationalCode());
            ps.setString(3, account.getAccountNumber());
            ps.setDouble(4, account.getBalance());
            ps.setString(5, String.valueOf(account.getAccountType()));
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(Account account) {
        try {
            PreparedStatement ps = connection
                    .prepareStatement("DELETE FROM account WHERE id = ?");
            ps.setLong(1, account.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Account mapTo(ResultSet resultSet) {
        try {
            if (resultSet.next()) {
                return new Account(resultSet.getLong("id"),
                        resultSet.getString("branch_code"),
                        resultSet.getString("national_code"),
                        resultSet.getString("account_number"),
                        resultSet.getDouble("balance"),
                        AccountType.valueOf(resultSet.getString("status")));
            } else return null;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public List<Account> mapToList(ResultSet resultSet) {
        List<Account> accounts = new ArrayList<>();
        try {
            while (resultSet.next()) {
                accounts.add(new Account(resultSet.getLong("id"),
                        resultSet.getString("branch_code"),
                        resultSet.getString("national_code"),
                        resultSet.getString("account_number"),
                        resultSet.getDouble("balance"),
                        AccountType.valueOf(resultSet.getString("status"))));
            }
            return accounts;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void deactivateAccount(String accountNumber) {
        try {
            PreparedStatement ps = connection
                    .prepareStatement("UPDATE account SET status = ? WHERE account_number = ?");
            ps.setString(1, "INACTIVE");
            ps.setString(2, accountNumber);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public String showAccounts(String nationalCode) {
        try {
            PreparedStatement ps = connection
                    .prepareStatement("select * from account where national_code = ?");
            ps.setString(1, nationalCode);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                if (resultSet.getString("status").equals("ACTIVE")) {
                    System.out.print(resultSet.getInt("id") + " : ");
                    System.out.println(resultSet.getString("account_number"));
                }
            }
            return "ACTIVE";
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return "ACTIVE";
    }

    public List<Account> showAccountsWithNationalCode(String nationalCode) {
        try {
            PreparedStatement ps = connection
                    .prepareStatement("select * from account where national_code = ?");
            ps.setString(1, nationalCode);
            ResultSet resultSet = ps.executeQuery();
            return mapToList(resultSet);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }


    public void inactiveAccount(String accountNumber) {
        try {
            PreparedStatement ps = connection
                    .prepareStatement("update account set status = ? where account_number = ?;");
            ps.setString(1, "INACTIVE");
            ps.setString(2, accountNumber);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Boolean checkAccount(String accountNumber) {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("SELECT * FROM account WHERE account_number = ? ");
            preparedStatement.setString(1, accountNumber);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            if (resultSet.getString("status").equals("ACTIVE"))
                return true;
            else
                return false;
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
        return false;
    }

    public void depositToCard(Double amount, String accountNumber) {
        try {
            PreparedStatement ps = connection
                    .prepareStatement("update account set balance = balance + ? where account_number = ?");
            ps.setDouble(1, amount);
            ps.setString(2, accountNumber);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public void deposit(Double amount, String accountNumber) {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("UPDATE account SET balance = balance + ? WHERE account_number = ?");
            preparedStatement.setDouble(1, amount);
            preparedStatement.setString(2, accountNumber);
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
    }

    public void withdraw(Double amount,String accountNumber) {
        try {
            String deposit = "UPDATE account SET balance = balance - ? WHERE account_number = ? ";
            PreparedStatement preparedStatement = connection.prepareStatement(deposit);
            preparedStatement.setDouble(1, amount);
            preparedStatement.setString(2, accountNumber);
            preparedStatement.executeUpdate();
        }catch (SQLException exception){
            System.out.println(exception.getMessage());
        }
    }
}
