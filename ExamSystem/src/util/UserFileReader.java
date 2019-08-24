package util;


import domain.User;

import java.io.*;
import java.util.HashMap;

//类的目的是为了增加一个缓存机制
//程序启动的时候将User.txt文件中的所有信息 一次性读出来
//以后做查询直接读取缓存中的数据  提高读取的性能
public class UserFileReader {

    //创建一个集合---充当一个缓存
    private HashMap<String, User> userBox = new HashMap<>();

    //块
    {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("src/dbfile/User.properties"));
            String message = reader.readLine();
            while(message != null){
                String[] values = message.split("-");
                String account = values[0];
                String password = values[1];
                userBox.put(account,new User(account,password));
                message = reader.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //返回缓存--User
    //static修饰，是为了不用创建对象
    public  User getUser(String account){
        return userBox.get(account);
    }

}
