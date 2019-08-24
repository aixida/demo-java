package util;

import domain.Question;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

public class QuestionFileReader {

    private HashSet<Question> questionBox = new HashSet<>();

    {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("src/dbfile/Question.properties"));
            String question = reader.readLine();
            while(question != null){
                String[] values = question.split("#");
                String title = values[0];
                String answer = values[1];
                if(values.length == 2){//没有图片信息，只有题干和答案
                    questionBox.add(new Question(title,answer));
                }else if(values.length == 3){//含有图片信息
                    questionBox.add(new Question(title,answer,values[2]));
                }
                question = reader.readLine();
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

    //获取全部集合
    public HashSet<Question> getQuestions(){
        return questionBox;
    }

}
