package services;

import models.Login;
import models.UserType;
import repositpries.LoginRepository;

import java.sql.Connection;
import java.sql.SQLException;

public class LoginService {
    private Connection connection;
    private LoginRepository loginRepository = new LoginRepository();

    public String findByUserPass(String userName,String password) throws SQLException {
        return loginRepository.findByUserPass(userName,password);
    }

    public String findByNationalCode(String nationalCode) throws SQLException {
        return loginRepository.findByNationalCode(nationalCode);
    }

    public Long addNewLogin(String nationalCode,String password, UserType userType) throws SQLException {
        Login login = new Login(nationalCode, password,userType);
        return loginRepository.save(login);
    }
}
