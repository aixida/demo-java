package service;

import dao.UserDao;
import domain.User;
import util.MySpring;

public class UserServer {

    //包含一个dao对象作为属性
    private UserDao dao = MySpring.getBean("dao.UserDao");

    //设计一个方法  负责登录
    public String login(String account,String password){
        User user = dao.selectOne(account);
        if(user != null){
            if(password.equals(user.getPassword())){
                return "登录成功";
            }
        }
        return "用户名或密码错误";
    }

}

