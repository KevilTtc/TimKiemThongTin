import java.sql.*;
import java.util.ArrayList;

public class MySQL {
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    private String url = "jdbc:mysql://localhost:3306/";
    private String user = "root", pass = "";
    private String db_table = "dictionary", db = "datair";


    public MySQL() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(url + db, user, pass);

        statement = connection.createStatement();
        //getData();
    }

    private void getData() throws SQLException {
        DatabaseMetaData dbmd = connection.getMetaData();

        String dbName = dbmd.getDatabaseProductName();

        String dbVersion = dbmd.getDatabaseProductVersion();

        String dbUrl = dbmd.getURL();

        String userName = dbmd.getUserName();

        String driverName = dbmd.getDriverName();

        System.out.println("Database Name is " + dbName);

        System.out.println("Database Version is " + dbVersion);

        System.out.println("Database Connection Url is " + dbUrl);

        System.out.println("Database User Name is " + userName);

        System.out.println("Database Driver Name is " + driverName);
    }

    public Words getWordsFlowEnglish(String sentence) throws SQLException {
        Words words = new Words();
        ResultSet resultSet = statement.executeQuery(
                "SELECT * FROM " + db_table + " WHERE word = '" + sentence + "'");

        while (resultSet.next()) {
            words.setId( resultSet.getString(1));
            words.setWord( resultSet.getString(2));
            words.setType( resultSet.getString(3));
            words.setMean( resultSet.getString(4));
            words.setExemple( resultSet.getString(5));
            words.setMean_exemple( resultSet.getString(6));

        }
        return words;
    }

    public Words getWordsFlowVietNam(String sentence) throws SQLException {
        Words words = new Words();
        ResultSet resultSet = statement.executeQuery(
                "SELECT * FROM " + db_table + " WHERE mean = '" + sentence + "'");

        while (resultSet.next()) {
            words.setId( resultSet.getString(1));
            words.setWord( resultSet.getString(2));
            words.setType( resultSet.getString(3));
            words.setMean( resultSet.getString(4));
            words.setExemple( resultSet.getString(5));
            words.setMean_exemple( resultSet.getString(6));

        }
        return words;


    }
}
