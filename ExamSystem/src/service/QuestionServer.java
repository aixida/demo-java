package service;

import dao.QuestionDao;
import domain.Question;
import util.MySpring;

import java.util.ArrayList;

public class QuestionServer {

    //包含一个dao对象作为属性
    QuestionDao dao = MySpring.getBean("dao.QuestionDao");

    public ArrayList<Question> getPaper(int count){
        return dao.getPaper(count);
    }

}
