package service;

import dao.TeacherDao;
import sqlsesssion.DaoProxy;

public class TeacherService {

    private TeacherDao dao = (TeacherDao) DaoProxy.getInstance(TeacherDao.class);

    //...

}
