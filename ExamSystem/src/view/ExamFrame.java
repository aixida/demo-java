package view;

import domain.Question;
import service.QuestionServer;
import util.BaseFrame;
import util.MySpring;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

@SuppressWarnings("all")
public class ExamFrame extends BaseFrame {

    //获取QuestionServer对象，为了获得考试卷子
    private QuestionServer server = MySpring.getBean("service.QuestionServer");
    private ArrayList<Question> paper = server.getPaper(5);//参数：卷子出题数
    //创建一个用于存储学生选择的答案的容器
    private String[] answers = new String[paper.size()];

    //创建几个变量  分别控制窗口右侧的当前题号、总题数、已答题数、未答题数
    private int nowNum = 0;
    private int total = paper.size();
    private int answeredCount = 0;
    private int noAnsweredCount = total;

    //创建一个变量,用来记录时间（分钟为单位）
    private int time = 61;
    //创建一个线程对象  控制时间的变化
    private TimeControlThread timeControlThread = new TimeControlThread();


    //=========================================================================================
    //添加构造方法
    public ExamFrame(){
        this.init();
    }
    public ExamFrame(String title){
        super(title);
        this.init();
    }

    //添加三个panel 区域的分割
    private JPanel mainPanel = new JPanel();//负责答题主页面展示
    private JPanel messagePanel = new JPanel();//负责右侧信息展示
    private JPanel buttonPanel = new JPanel();//负责下方按钮的展示
    //添加主要答题的组件
    private JTextArea examArea = new JTextArea();//考试文本域 展示题目
    private JScrollPane scrollPane = new JScrollPane(examArea);//滚动条
    //添加右侧信息的组件
    private JLabel pictureLabel = new JLabel();//展示图片信息
    private JLabel nowNumLabel = new JLabel("当前题号：");//提示当前的题号
    private JLabel totalCountLabel = new JLabel("题目总数：");//提示题目的总数
    private JLabel answerCountLabel = new JLabel("已答题数：");//提示已经答过的题目数量
    private JLabel unanswerCountLabel = new JLabel("未答题数：");//提示未答题数量
    private JTextField nowNumField = new JTextField("0");//展示题号
    private JTextField totalCountField = new JTextField("0");//展示总数
    private JTextField answerCountField = new JTextField("0");//展示已答数
    private JTextField unanswerCountField = new JTextField("0");//展示未答数
    private JLabel timeLabel = new JLabel("剩余答题时间");//提示剩余时间
    private JLabel realTimeLabel = new JLabel("00:00:00");//倒计时真实时间
    //添加下方按钮的组件
    private JButton aButton = new JButton("A");//a按钮
    private JButton bButton = new JButton("B");//b按钮
    private JButton cButton = new JButton("C");//c按钮
    private JButton dButton = new JButton("D");//d按钮
    private JButton prevButton = new JButton("上一题");//previous题
    private JButton nextButton = new JButton("下一题");//next题
    private JButton submitButton = new JButton("提交试卷");//提交按钮

    @Override
    protected void setup() {
        //设置panel布局管理---->自定义
        mainPanel.setLayout(null);
        mainPanel.setBackground(Color.LIGHT_GRAY);
        //设置message区域的位置
        messagePanel.setLayout(null);
        messagePanel.setBounds(680,10,300,550);
        messagePanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        //设置button区域的位置
        buttonPanel.setLayout(null);
        buttonPanel.setBounds(16,470,650,90);
        buttonPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        //手动设置每一个组件的位置 字体 背景
        scrollPane.setBounds(16,10,650,450);
        examArea.setFont(new Font("黑体",Font.BOLD,34));
        examArea.setEnabled(false);//文本域中的文字不能编辑



        //设置message区域中的每一个组件位置 大小 颜色
        pictureLabel.setBounds(10,10,280,230);
        pictureLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        //pictureLabel.setIcon(null);//展示图片信息
        nowNumLabel.setBounds(40,270,100,30);
        nowNumLabel.setFont(new Font("黑体",Font.PLAIN,20));
        nowNumField.setBounds(150,270,100,30);
        nowNumField.setFont(new Font("黑体",Font.BOLD,20));
        nowNumField.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        nowNumField.setEnabled(false);
        nowNumField.setHorizontalAlignment(JTextField.CENTER);

        totalCountLabel.setBounds(40,310,100,30);
        totalCountLabel.setFont(new Font("黑体",Font.PLAIN,20));
        totalCountField.setBounds(150,310,100,30);
        totalCountField.setFont(new Font("黑体",Font.BOLD,20));
        totalCountField.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        totalCountField.setEnabled(false);
        totalCountField.setHorizontalAlignment(JTextField.CENTER);

        answerCountLabel.setBounds(40,350,100,30);
        answerCountLabel.setFont(new Font("黑体",Font.PLAIN,20));
        answerCountField.setBounds(150,350,100,30);
        answerCountField.setFont(new Font("黑体",Font.BOLD,20));
        answerCountField.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        answerCountField.setEnabled(false);
        answerCountField.setHorizontalAlignment(JTextField.CENTER);

        unanswerCountLabel.setBounds(40,390,100,30);
        unanswerCountLabel.setFont(new Font("黑体",Font.PLAIN,20));
        unanswerCountField.setBounds(150,390,100,30);
        unanswerCountField.setFont(new Font("黑体",Font.BOLD,20));
        unanswerCountField.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        unanswerCountField.setEnabled(false);
        unanswerCountField.setHorizontalAlignment(JTextField.CENTER);

        timeLabel.setBounds(90,460,150,30);
        timeLabel.setFont(new Font("黑体",Font.PLAIN,20));
        timeLabel.setForeground(Color.BLUE);
        realTimeLabel.setBounds(108,490,150,30);
        realTimeLabel.setFont(new Font("黑体",Font.BOLD,20));
        realTimeLabel.setForeground(Color.BLUE);

        aButton.setBounds(40,10,120,30);
        bButton.setBounds(190,10,120,30);
        cButton.setBounds(340,10,120,30);
        dButton.setBounds(490,10,120,30);
        prevButton.setBounds(40,50,100,30);
        nextButton.setBounds(510,50,100,30);
        submitButton.setBounds(276,50,100,30);
        submitButton.setForeground(Color.RED);
        submitButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        //------------------------------------------------------------------------
        //展示题目和可能有的图片
        showQuestionAndPicture();
        //设置窗口右侧中的组件的初始值
        nowNumField.setText(nowNum+1+"");
        totalCountField.setText(total+"");
        answerCountField.setText(answeredCount+"");
        unanswerCountField.setText(noAnsweredCount+"");

        realTimeLabel.setText(time+"");
        //--------------------------------------------------------------------------
    }

    @Override
    protected void addElement() {
        messagePanel.add(pictureLabel);
        messagePanel.add(nowNumLabel);
        messagePanel.add(nowNumField);
        messagePanel.add(totalCountLabel);
        messagePanel.add(totalCountField);
        messagePanel.add(answerCountLabel);
        messagePanel.add(answerCountField);
        messagePanel.add(unanswerCountLabel);
        messagePanel.add(unanswerCountField);
        messagePanel.add(timeLabel);
        messagePanel.add(realTimeLabel);
        buttonPanel.add(aButton);
        buttonPanel.add(bButton);
        buttonPanel.add(cButton);
        buttonPanel.add(dButton);
        buttonPanel.add(prevButton);
        buttonPanel.add(nextButton);
        buttonPanel.add(submitButton);
        mainPanel.add(scrollPane);
        mainPanel.add(messagePanel);
        mainPanel.add(buttonPanel);
        add(mainPanel);
    }

    @Override
    protected void addListener() {

        //创建一个监听器  用于提交按钮
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //1.确认框  确定提交吗
                //返回值  0--是   1--否  2--取消
                int value = JOptionPane.showConfirmDialog(ExamFrame.this,"确定交卷吗？");
                if(value == 0){
                    //2.倒计时时间停止---线程结束
                    timeControlThread.stopTimeThread();
                    //3.所有按钮失效
                    setOptionButtonEnable(false);
                    prevButton.setEnabled(false);
                    nextButton.setEnabled(false);
                    //4.清楚按钮的颜色
                    clearOptionButtonColor();
                    //5.最终成绩的计算  展示在中间的文本框中
                    float score = checkPaper();
                    examArea.setText("考试已经拉下帷幕\n您的分数为" + score);
                }
            }
        });

        //创建一个监听器  用于下一题按钮
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //还原上一题按钮的可用状态
                prevButton.setEnabled(true);
                //先清除所有按钮的颜色
                clearOptionButtonColor();
                //当前题目序号增加一个,已答题数加一个，未答题数减少一个
                nowNum++;
                answeredCount++;
                noAnsweredCount--;
                //判断题目是否到达最后
                if(nowNum == total){
                    examArea.setText("全部题目已经回答完毕\n请点击下方的红色按钮提交");
                    //让下一个按钮失效
                    nextButton.setEnabled(false);
                    //让四个选项按钮失效
                    setOptionButtonEnable(false);
                    answerCountField.setText(answeredCount+"");
                    unanswerCountField.setText(noAnsweredCount+"");
                }else{
                    //调用自己封装的方法显示下一个题目和可能有的图片
                    showQuestionAndPicture();
                    //修改右侧的当前题号、已答题数、未答题数
                    nowNumField.setText(nowNum+1+"");
                    answerCountField.setText(answeredCount+"");
                    unanswerCountField.setText(noAnsweredCount+"");
                }
                //还原之前这道题选择的选项
                restoreButton();
            }
        });


        //创建一个监听器  用于上一题按钮
        prevButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //还原四个选项按钮的可用状态
                setOptionButtonEnable(true);
                //设置下一题按钮的状态  变为可用
                nextButton.setEnabled(true);
                //先清除所有按钮的颜色
                clearOptionButtonColor();
                //当前题目序号减少一个
                nowNum--;
                //判断题目是否到达第一个
                if(nowNum == 0){
                    //让上一个按钮失效
                    prevButton.setEnabled(false);
                }
                //还原之前这道题选择的选项
                restoreButton();
                //显示题目
                showQuestionAndPicture();
                //修改右侧题号、已答题、未答题
                nowNumField.setText(nowNum+1+"");
                answerCountField.setText(--answeredCount+"");
                unanswerCountField.setText(++noAnsweredCount+"");
            }
        });


       //创建一个监听器对象  用于四个选项按钮
        ActionListener optionlistener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //清除之前所有选项按钮的颜色
                clearOptionButtonColor();
                //让被点中的按钮颜色变化一下
                JButton button = (JButton) e.getSource();//获取到哪一个按钮点击了
                button.setBackground(Color.blue);

                //将当前按钮的选项存储在answers数组中
                answers[nowNum] = button.getText();
            }
        };
        //将这四个监听器对象绑定在四个选项按钮身上
        aButton.addActionListener(optionlistener);
        bButton.addActionListener(optionlistener);
        cButton.addActionListener(optionlistener);
        dButton.addActionListener(optionlistener);

    }

    @Override
    protected void setFrameSelf() {
        this.setBounds(260,130,1000,600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);//不想让窗体拖拽大小
        this.setVisible(true);//最终展示整个窗体
        timeControlThread.start();//启动自己定义的线程，进入就绪状态，进行对时间的处理
    }



    //=====================================================================================

    //设计一个内部类  处理时间倒计时问题
    private class TimeControlThread extends Thread{

        private boolean flag = true;
        public void stopTimeThread(){
            this.flag = false;
        }
        @Override
        public void run() {
            //time进行格式转化  小时：分钟：秒
            int hour = time/60;
            int minute = time%60;
            int second = 0;
            while(flag){
                //对时、分、秒进行拼串
                StringBuilder builder = new StringBuilder();
                if(hour >= 0 && hour < 10){
                    builder.append("0");
                }
                builder.append(hour);
                builder.append(":");
                if(minute >=0 && minute < 10){
                    builder.append("0");
                }
                builder.append(minute);
                builder.append(":");
                if(second >=0 && second < 10){
                    builder.append("0");
                }
                builder.append(second);

                realTimeLabel.setText(builder.toString());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //改变
                if(second > 0){
                    second--;
                }else{
                    if(minute > 0){
                        minute--;
                        second = 59;
                    }else{
                        if(hour > 0){
                            hour--;
                            minute = 59;
                            second = 59;
                        }else{
                            //时间显示为红色
                            realTimeLabel.setForeground(Color.red);
                            //所有按钮失效
                            setOptionButtonEnable(false);
                            nextButton.setEnabled(false);
                            prevButton.setEnabled(false);
                            //弹出一个消息框  告诉考试结束  请提交
                            JOptionPane.showMessageDialog(ExamFrame.this,"考试结束，请提交");
                            break;//线程停止
                        }
                    }
                }

            }
        }
    }


    //设计一个方法  负责计算最终的成绩
    private float checkPaper(){
        float score = 0;
        int size = paper.size();
        for(int i = 0; i < size; i++){
            Question question = paper.get(i);
            String realAnswer = question.getAnswer();
            if(answers[i].equals(realAnswer)){
                score += (100/size);
            }
        }
        return score;
    }

    //设计一个方法 负责还原上一题的选项
    private void restoreButton(){
        //获取当前题目的答案（学生已经选择了的）
        String answer = answers[nowNum];
        if(answer == null){
            return;
        }
        switch (answer){
            case "A":
                aButton.setBackground(Color.blue);
                break;
            case "B":
                bButton.setBackground(Color.blue);
                break;
            case "C":
                cButton.setBackground(Color.blue);
                break;
            case "D":
                dButton.setBackground(Color.blue);
                break;
            default:
                this.clearOptionButtonColor();
                break;
        }
    }


    //设计一个方法  让所有选项按钮全部失效
    private void setOptionButtonEnable(boolean key){
        aButton.setEnabled(key);
        bButton.setEnabled(key);
        cButton.setEnabled(key);
        dButton.setEnabled(key);
    }

    //设计一个方法 清除所有选项按钮的颜色
    private void clearOptionButtonColor(){
        aButton.setBackground(null);
        bButton.setBackground(null);
        cButton.setBackground(null);
        dButton.setBackground(null);
    }

    //设计一个方法  用来展示一道题目
    private void showQuestionAndPicture(){
        //从paper中获取一道题目
        Question question = paper.get(nowNum);//三个属性 题干、答案、图片路径（可能为null）
        //获取当前question的图片路径
        String picture = question.getPicture();//路径
        if(picture != null){
            pictureLabel.setIcon(drawImage(picture));
        }else{
            pictureLabel.setIcon(null);
        }
        //处理题目中的标记<br> 表示换行
        String title = question.getTitle().replace("<br>","\n");
        examArea.setText((nowNum+1) + "." + title);
    }

    //设计一个方法  用来处理图片展示
    private ImageIcon drawImage(String path){
        //通过给定路径创建一个icon对象
        ImageIcon imageIcon = new ImageIcon("src//img//" + path);
        //设置imageIcon对象内的image属性
        imageIcon.setImage(imageIcon.getImage().getScaledInstance(280,230,Image.SCALE_DEFAULT));

        return imageIcon;
    }
}

