package view;

import service.AtmService;
import util.BaseFrame;
import util.MySpring;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("all")
public class LoginFrame extends BaseFrame {

    private LoginFrame(){
        super("登 录");
        this.init();
    }
    private static LoginFrame loginFrame = null;
    public synchronized static LoginFrame getLoginFrame(){
        if(loginFrame == null){
            loginFrame = new LoginFrame();
        }
        return loginFrame;
    }

    //添加一些属性，登录窗口上的各种组件
    private JPanel mainPanel = new JPanel();
    private JLabel logoLabel = new JLabel();
    private JLabel titleLabel = new JLabel("请您登录系统");
    private JLabel accountLabel = new JLabel("请输入账号：");
    private JTextField accountField = new JTextField();
    private JLabel passwordLabel = new JLabel("请输入密码：");
    private JPasswordField passwordField = new JPasswordField();
    private JButton loginButton = new JButton("登录");
    private JButton registButton = new JButton("注册");

    private RegistFrame registFrame = null;

    @Override
    protected void setFontAndSoOn() {
        mainPanel.setLayout(null);
        logoLabel.setBounds(135,40,40,40);
        logoLabel.setIcon(this.drawImage("src//img//【lol】安妮Annie_海克斯科技01_1920x1080.jpg",40,40));
        titleLabel.setBounds(185,40,200,40);
        titleLabel.setFont(new Font("微软雅黑",Font.BOLD,24));

        accountLabel.setBounds(40,100,140,40);
        accountLabel.setFont(new Font("微软雅黑",Font.BOLD,18));
        accountField.setBounds(170,105,260,32);
        accountField.setFont(new Font("微软雅黑",Font.BOLD,20));

        passwordLabel.setBounds(40,150,140,40);
        passwordLabel.setFont(new Font("微软雅黑",Font.BOLD,18));
        passwordField.setBounds(170,155,260,32);
        passwordField.setFont(new Font("微软雅黑",Font.BOLD,20));

        loginButton.setBounds(120,210,100,32);
        loginButton.setFont(new Font("微软雅黑",Font.BOLD,14));
        loginButton.setBackground(Color.LIGHT_GRAY);

        registButton.setBounds(260,210,100,32);
        registButton.setFont(new Font("微软雅黑",Font.BOLD,14));
        registButton.setBackground(Color.LIGHT_GRAY);
    }

    @Override
    protected void addElements() {
        add(mainPanel);
        mainPanel.add(logoLabel);
        mainPanel.add(titleLabel);
        mainPanel.add(accountLabel);
        mainPanel.add(accountField);
        mainPanel.add(passwordLabel);
        mainPanel.add(passwordField);
        mainPanel.add(loginButton);
        mainPanel.add(registButton);
    }

    @Override
    protected void addListener() {

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //获取账号  密码
                String aname = accountField.getText();
                String apassword = new String(passwordField.getPassword());
                //调用登录的方法
                AtmService atmService = MySpring.getBean("service.AtmService");
                String result = atmService.login(aname,apassword);
                if(result.equals("登录成功")){
                    LoginFrame.this.setVisible(false);
                    AtmFrame.getAtmFrame(aname);
                }else {
                    JOptionPane.showMessageDialog(LoginFrame.this,result);
                    LoginFrame.this.reset();
                }
            }
        });

        registButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoginFrame.this.setVisible(false);
                if(registFrame == null){
                    registFrame = RegistFrame.getRegistFrame();
                }else{
                    registFrame.setVisible(true);
                    registFrame.reset();
                }
            }
        });
    }

    //设置一个方法  清空所有输入框
     void reset(){
        accountField.setText("");
        passwordField.setText("");
    }

    @Override
    protected void setFrameSelf() {
        this.setBounds(400,200,500,300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
    }

}
