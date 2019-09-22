package service;

import dao.AtmDao;
import domain.Atm;
import util.MySpring;

//业务层，负责数据的处理，比较、判断、计算等等一些逻辑
public class AtmService {

    private AtmDao dao = MySpring.getBean("dao.AtmDao");

    public String login(String aname,String apassword){
        //查询数据库中的真实的账号和密码
        //做比对
        Atm atm = dao.selectOne(aname);
        if(atm != null && atm.getApassword().equals(apassword)){
            return "登录成功";
        }
        return "用户名或密码错误";
    }

    public Float query(String aname){
        return dao.selectOne(aname).getAbalance();
    }

    public void deposit(String aname,Float money){
        Atm atm = dao.selectOne(aname);
        atm.setAbalance(atm.getAbalance()+money);
        dao.update(atm);
    }

    public String withdraw(String aname,Float money){
        Atm atm = dao.selectOne(aname);
        if(atm.getAbalance() >= money){
            atm.setAbalance(atm.getAbalance()-money);
            dao.update(atm);
            return "取款成功";
        }
        return "取款失败";
    }

    public String transfer(String aname,String to, Float money){
        Atm atm = dao.selectOne(aname);
        Atm atm1 = dao.selectOne(to);
        if(atm1 != null && atm.getAbalance() >= money){
            atm.setAbalance(atm.getAbalance()-money);
            dao.update(atm);
            atm1.setAbalance(atm1.getAbalance()+money);
            dao.update(atm1);
            return "转账成功";
        }
        return "转账失败";
    }


}
