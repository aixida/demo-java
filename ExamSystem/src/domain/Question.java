package domain;

public class Question {
    //domain实体  存储文件中的题目  增强可读性

    private String title;
    private String answer;
    private String picture;//存储图片路径

    public Question(){}
    public Question(String title,String answer){
        this.title = title;
        this.answer = answer;
    }
    public Question(String title,String answer,String picture){
        this.title = title;
        this.answer = answer;
        this.picture = picture;
    }

    //重写Question类中的两个方法  equals  hashCode
    //想要将Question集合存入HashSet集合中，让set集合帮我们去掉重复元素
    @Override
    public boolean equals(Object obj) {
        if(this == obj){
            return true;
        }
        if(obj instanceof Question){
            Question anotherQuestion = (Question)obj;
            String thisTitle = title.substring(0,title.indexOf("<br>"));
            String anotherTitle = anotherQuestion.getTitle().substring(0,anotherQuestion.getTitle().indexOf("<br>"));
            if(thisTitle.equals(anotherTitle)){
                return true;
            }
        }
        return false;
    }
    @Override
    public int hashCode() {
        return title.substring(0,title.indexOf("<br>")).hashCode();
    }

    public String getAnswer() {
        return answer;
    }

    public String getTitle() {
        return title;
    }

    public String getPicture() {
        return picture;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
