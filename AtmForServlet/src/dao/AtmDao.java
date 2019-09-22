package dao;

import domain.Atm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

//持久层，只负责数据的读写，全部都是存粹的jdbc，sql
@SuppressWarnings("all")
public class AtmDao {

    //web项目中，导入.jar文件的时候，操作与之前有些不一样了
    //需要将.jar文件放置在web文件夹的WEB-INF文件夹下
    //创建lib文件夹，将需要的.jar文件导入


    public Atm selectOne(String aname){
        Atm atm = null;
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/atm?serverTimezone=CST";
        String user = "root";
        String password = "root";
        String sql = "SELECT ANAME,APASSWORD,ABALANCE FROM ATM WHERE ANAME = ?";
        try {
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(url,user,password);
            PreparedStatement pstat = conn.prepareStatement(sql);
            pstat.setString(1,aname);
            ResultSet rs = pstat.executeQuery();
            if(rs.next()){
                atm = new Atm();
                atm.setAname(rs.getString("aname"));
                atm.setApassword(rs.getString("apassword"));
                atm.setAbalance(rs.getFloat("abalance"));
            }
            rs.close();
            pstat.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return atm;
    }


    public void update(Atm atm){
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/atm?serverTimezone=CST";
        String user = "root";
        String password = "root";
        String sql = "UPDATE ATM SET APASSWORD=?,ABALANCE=? WHERE ANAME=?";
        try {
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(url,user,password);
            PreparedStatement pstat = conn.prepareStatement(sql);
            pstat.setString(1,atm.getApassword());
            pstat.setFloat(2,atm.getAbalance());
            pstat.setString(3,atm.getAname());
            pstat.executeUpdate();
            pstat.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
