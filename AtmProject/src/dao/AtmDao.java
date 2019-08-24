package dao;

import domain.Atm;

import java.sql.*;

public class AtmDao {

    //负责的是纯粹的JDBC操作  没有任何的逻辑
    //对于atm表格的新增  修改  删除  查询单条记录

    private String className = "com.mysql.cj.jdbc.Driver";
    private String url = "jdbc:mysql://localhost:3306/atm?serverTimezone=CST";
    private String user = "root";
    private String password = "root";

    private Connection connection = null;
    private PreparedStatement pstat = null;


    //设计一个方法，负责将某一条记录删掉
    public int delete(String aname){
        int count = 0;
        String sql = "DELETE FROM ATM WHERE ANAME = ?";
        try{
            Class.forName(className);
            connection = DriverManager.getConnection(url,user,password);
            pstat = connection.prepareStatement(sql);
            pstat.setString(1,aname);
            count = pstat.executeUpdate();
        } catch(Exception e){
            e.printStackTrace();
        }finally {
            try {
                if(pstat != null){
                    pstat.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if(connection != null){
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return count;
    }

    //设计一个方法，负责将一行新的记录写入数据库中
    public void insert(Atm atm){
        String sql = "INSERT INTO ATM VALUES(?,?,?)";
        try{
            Class.forName(className);
            connection = DriverManager.getConnection(url,user,password);
            pstat = connection.prepareStatement(sql);
            pstat.setString(1,atm.getAname());
            pstat.setString(2,atm.getApassword());
            pstat.setFloat(3,atm.getAbalance());
            pstat.executeUpdate();
        } catch(Exception e){
            e.printStackTrace();
        }finally {
            try {
                if(pstat != null){
                    pstat.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if(connection != null){
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }



    //查询一行记录（这是复用代码：登录、查询...）
    public Atm selectOne(String aname){
        Atm atm = null;
        String sql = "SELECT ANAME,APASSWORD,ABALANCE FROM ATM WHERE ANAME = ? ";
        ResultSet rs = null;
        try{
            Class.forName(className);
            connection = DriverManager.getConnection(url,user,password);
            pstat = connection.prepareStatement(sql);
            pstat.setString(1,aname);
            rs = pstat.executeQuery();
            if(rs.next()){
                //用户存在，查询到了一行记录
                //  然后需要把这一行记录返回出去，那么这个方法的返回值是什么呢？
                //      是返回类型是ResultSet，然后return rs;吗？
                //      不不不，这个不行，因为rs在执行返回语句之前会被关闭
                //  那么就需要一个小容器来作为返回类型了
                //      数组，集合，对象？
                //      对象是最好的选择
                atm = new Atm();
                atm.setAname(rs.getString("aname"));
                atm.setAbalance(rs.getFloat("abalance"));
                atm.setApassword(rs.getString("apassword"));
            }
        } catch(Exception e){
            e.printStackTrace();
        }finally {
            try {
                if(rs != null){
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if(pstat != null){
                    pstat.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if(connection != null){
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return atm;
    }


    //修改一行记录（复用代码：取款，存款）
    public int update(Atm atm){
        int count = 0;//记录更改的行数
        String sql = "UPDATE ATM SET APASSWORD = ?,ABALANCE = ? WHERE ANAME = ?";
        try{
            Class.forName(className);
            connection = DriverManager.getConnection(url,user,password);
            pstat = connection.prepareStatement(sql);
            pstat.setString(1,atm.getApassword());
            pstat.setFloat(2,atm.getAbalance());
            pstat.setString(3,atm.getAname());
            count = pstat.executeUpdate();
        } catch(Exception e){
            e.printStackTrace();
        }finally {
            try {
                if(pstat != null){
                    pstat.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if(connection != null){
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return count;
    }
}
