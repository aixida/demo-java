package util;


import java.sql.*;

public class MyConnection extends AbstractConnection {

    private Connection connection;//真实的连接
    private int index;//索引，找寻连接对应的状态

    public MyConnection(int index) {
        this.index = index;
    }

    private static String driver = DBConfig.getConfig("driver");
    private static String url = DBConfig.getConfig("url");
    private static String user = DBConfig.getConfig("user");
    private static String password = DBConfig.getConfig("password");

    static {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    {
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public Connection getConnection() {
        return connection;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public Statement createStatement() throws SQLException {
        return this.connection.createStatement();
    }

    @Override
    public PreparedStatement prepareStatement(String sql) throws SQLException {
        return this.connection.prepareStatement(sql);
    }

    @Override
    public void close() throws SQLException {
        ConnectionPool.giveBack(this);
    }

    @Override
    public DatabaseMetaData getMetaData() throws SQLException {
        return this.connection.getMetaData();
    }

}

