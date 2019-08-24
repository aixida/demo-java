package dao;

import domain.Question;
import util.MySpring;
import util.QuestionFileReader;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class QuestionDao {

    //负责读取文件，随机生成一套试卷
    //题库10道题
    //生成的试卷是5道题


    //获取缓存对象
    private QuestionFileReader buffer = MySpring.getBean("util.QuestionFileReader");
    //将缓存中的集合临时改为list集合(有序可重复)
    private ArrayList<Question> questionBank = new ArrayList<>(buffer.getQuestions());

    //生成试卷
    public ArrayList<Question> getPaper(int count){
        HashSet<Question> paper = new HashSet<>();//用来存储最终的试卷题目（无序无重复）
        while(paper.size() != count){
            Random r = new Random();
            int index = r.nextInt(questionBank.size());//随机数范围[0,集合长度)
            paper.add(questionBank.get(index));
        }
        return new ArrayList<Question>(paper);
    }

}
