package sqlsesssion;

import util.ConnectionPool;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings("all")
public class SqlSessionFactory {

    //这个类的目的是为了帮所有的Dao处理冗余的JDBC操作

    //设计一个方法，可以处理任何一个表格的任何一条 删、改
    //  第一个参数：sql
    //  第二个参数：
    //      设计的方法必须通用才行，'?'怎么处理呢
    //      '?'有几个，是什么类型
    //      怎么把'?'传进来
    //      用对象吗？但是传进来的对象的类型是不确定的
    //          假设传进来的是Object对象，但是要拼接SQL的时候，需要调用对象它独有的方法，需要造型（可以使用泛型，放射，但是太麻烦了）
    //      除了对象，容器还有：
    //          数组，集合（List，Set，Map）
    //          这几个容器，哪个更好呢？应该是Map，因为Map是通过键值对来存储信息的
    //              但是Map<key,value>中value一旦造型，类型就确定了，这样不好，不通用
    //      对象在存储信息方面，比Map更加棒
    //      domain 类名--表名  属性名--列名  属性类型--列类型
    //      实际上这些容器存储信息都是一样的，但是对象的可读性最好
    //
    //      假如我们使用对象，就是作为容器，包着一堆信息
    //      但是把对象作为第二个参数传进来后，还要把它一个一个拆开，拼在'?'上，一包一拆之间产生了类型问题，而且不好处理
    //
    //      那么索性，就不要对象了
    //      直接把信息传进来就好了，可以选择数组、集合
    //      数组更加通用，使用数组
    public static int update(String sql,Object[] values)throws SQLException {
        Connection connection = ConnectionPool.getConnection();
        PreparedStatement pstat = connection.prepareStatement(sql);
        for(int i = 0; i < values.length; i++){
            pstat.setObject(i+1,values[i]);
        }
        int count = pstat.executeUpdate();
        pstat.close();
        connection.close();
        return count;
    }



    //新增一条记录
    //  返回值是新增记录的id
    public static long insert(String sql,Object[] values)throws SQLException{
        Connection connection = ConnectionPool.getConnection();
        PreparedStatement pstat = connection.prepareStatement(sql);
        for(int i = 0; i < values.length; i++){
            pstat.setObject(i+1,values[i]);
        }
        int count = pstat.executeUpdate();
        //
        ResultSet rs = pstat.getGeneratedKeys();//其中存储的是临时新增的数据
        if(rs.next()){
            return rs.getLong(1);//返回新增记录的id号
        }
        pstat.close();
        connection.close();
        return -1;
    }


    //查询单条记录
    public static <T>T selectOne(String sql,Object[] values,Class resultType) throws SQLException, InstantiationException, IllegalAccessException {
        T obj = null;
        Connection connection = ConnectionPool.getConnection();
        PreparedStatement pstat = connection.prepareStatement(sql);
        for(int i = 0; i < values.length; i++){
            pstat.setObject(i+1,values[i]);
        }
        //执行查询操作
        ResultSet rs = pstat.executeQuery();
        //要把信息取出来，因为rs需要在return之前关闭
        if(rs.next()){
            obj = construct(rs,resultType);
        }
        return obj;
    }

    //给一个对象进行赋值操作
    private static <T>T construct(ResultSet resultSet,Class resultType) throws IllegalAccessException, InstantiationException, SQLException {
        T obj = (T)resultType.newInstance();
        for(int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++){
            String metaName = resultSet.getMetaData().getColumnName(i);
            setAttribute(obj,metaName,resultSet);
        }
        return obj;
    }

    //给对象的某一个属性进行赋值操作
    //  对象的属性名遵循驼峰式命名规则,而MySQL数据库是不区分大小写的
    //
    //  Java：myName
    //  数据库：my_name
    private static void setAttribute(Object obj,String attr,ResultSet resultSet) throws SQLException, IllegalAccessException {
        //从数据库中把列名拿出来，存到对象的属性中：
        attr = lineToHump(attr);
        Field[] fields = obj.getClass().getDeclaredFields();
        for(Field field:fields){
            if(field.getName().equals(attr)){
                field.setAccessible(true);
                Class fieldType = field.getType();
                field.set(obj,resultSet.getObject(attr,fieldType));
            }
        }
    }

    //验证数据库中的列名是否存在下划线_  需要做一个处理（转化成驼峰式）
    private static String lineToHump(String str){
        Pattern linePattern = Pattern.compile("_(\\w)");
        str = str.toLowerCase();
        Matcher matcher = linePattern.matcher(str);
        StringBuilder sb = new StringBuilder();
        while (matcher.find()) {
            //appendReplacement：将当前匹配的子字符串替换为指定的字符串，并且将替换后的字符串及其之前到上次匹配的子字符串之后的字符串添加到一个StringBuilder对象中。
            //appendTail：将最后一次匹配之后的字符串添加到一个StringBuilder对象中。
            matcher.appendReplacement(sb,matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

}
