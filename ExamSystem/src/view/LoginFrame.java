package view;

import service.UserServer;
import util.BaseFrame;
import util.MySpring;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends BaseFrame {

    public LoginFrame(){
        init();
    }
    public LoginFrame(String title){
        super(title);
        init();
    }

    private JPanel mainPanel = new JPanel();
    private  JLabel titleLable = new JLabel("在 线 考 试 系 统");
    private  JLabel nameLable = new JLabel("账 号：");
    private  JLabel passwordLable = new JLabel("密 码：");
    private  JTextField text = new JTextField();
    private  JPasswordField password = new JPasswordField();
    private  JButton login = new JButton("登 录");
    private  JButton exit = new JButton("退 出");

    @Override
    protected void setup() {
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setLayout(null);
        titleLable.setBounds(120,40,340,35);
        titleLable.setFont(new Font("黑体",Font.BOLD,34));
        nameLable.setBounds(94,124,90,30);
        nameLable.setFont(new Font("黑体",Font.BOLD,24));
        passwordLable.setBounds(94,174,90,30);
        passwordLable.setFont(new Font("黑体",Font.BOLD,24));
        text.setBounds(204,124,260,30);
        text.setFont(new Font("黑体",Font.BOLD,24));
        password.setBounds(204,174,260,30);
        password.setFont(new Font("黑体",Font.BOLD,24));
        login.setBounds(157,232,100,30);
        login.setFont(new Font("黑体",Font.BOLD,22));
        exit.setBounds(304,232,100,30);
        exit.setFont(new Font("黑体",Font.BOLD,22));
    }

    @Override
    protected void addElement() {
        add(mainPanel);
        mainPanel.add(titleLable);
        mainPanel.add(nameLable);
        mainPanel.add(passwordLable);
        mainPanel.add(text);
        mainPanel.add(password);
        mainPanel.add(login);
        mainPanel.add(exit);
    }

    @Override
    protected void addListener() {
        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = text.getText();
                char[] value = password.getPassword();
                UserServer user = MySpring.getBean("service.UserServer");
                String result = user.login(name,new String(value));
                if(result.equals("登录成功")){
                    new ExamFrame(name + "请认真答题");
                    setVisible(false);
                }else{
                    //这里的第一个参数，要注意
                    //在匿名内部类中this不好使
                    JOptionPane.showMessageDialog(LoginFrame.this,result);
                    password.setText(null);
                    text.setText(null);
                }
            }
        });
    }

    @Override
    protected void setFrameSelf() {
        setBounds(400,280,560,340);
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}

