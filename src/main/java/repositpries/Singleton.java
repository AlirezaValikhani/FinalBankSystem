package repositpries;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class Singleton
{
    private Connection connection;
    private static Singleton obj;
    private Singleton() {
        try{
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/FinalBankSystem", "postgres", "Alireza1376");
        }catch (SQLException e){
            e.printStackTrace();
        }
        catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }


    public static Singleton getInstance()  {
        if (obj==null)
            obj = new Singleton();
        return obj;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public static Singleton getObj() {
        return obj;
    }

    public static void setObj(Singleton obj) {
        Singleton.obj = obj;
    }
}
