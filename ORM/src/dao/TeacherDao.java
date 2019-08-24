package dao;

import domain.Teacher;
import sqlsesssion.Param;
import sqlsesssion.SQL;
import sqlsesssion.SQLEnums;

import java.sql.SQLException;

public interface TeacherDao {


    //数据的修改
    @SQL(sql = "UPDATE TEACHER SET TNAME=?,TSEX=?,TBIRTHDAY=? WHERE TID=?",
            type = SQLEnums.UPDATE)
    public int update(@Param({"name","sex","birthday","id"}) String name,String sex,String birthday,int id)throws SQLException;


    //删除一行记录
    @SQL(sql = "DELETE FROM TEACHER WHERE TID=?",
            type = SQLEnums.DELETE)
    public int delete(@Param("id") int id)throws SQLException;


    //新增一条记录
    @SQL(sql = "INSERT INTO TEACHER(TID,TNAME,TSEX,TBIRTHDAY) VALUES(?,?,?,?)",
            type = SQLEnums.IESERT)
    public long insert(Teacher teacher)throws SQLException;


    //查询单条记录
    @SQL(sql = "SELECT * FROM TEACHER WHERE TID=?",
    type = SQLEnums.QUERY,
    resultType = Teacher.class)
    public Teacher selectOne(@Param("id") int id)throws SQLException;


}
