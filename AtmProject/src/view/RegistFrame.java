package view;

import service.AtmService;
import util.BaseFrame;
import util.MySpring;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("all")
public class RegistFrame extends BaseFrame {

    private RegistFrame(){
        super("注 册");
        this.init();
    }
    private static RegistFrame registFrame = null;
    public synchronized static RegistFrame getRegistFrame(){
        if(registFrame == null){
            registFrame = new RegistFrame();
        }
        return registFrame;
    }

    //添加一个控制登陆界面的属性
    private LoginFrame loginFrame = LoginFrame.getLoginFrame();

    private JPanel mainPanel = new JPanel();
    private JLabel logoLabel = new JLabel();
    private JLabel titleLanel = new JLabel("请您填写信息");
    private JLabel accountLabel = new JLabel("请输入账号：");
    private JTextField accountField = new JTextField();
    private JLabel passwordLabel = new JLabel("请输入密码：");
    private JTextField passwordField = new JTextField();
    private JLabel balancedLabel = new JLabel("请输入金额：");
    private JTextField balancedField = new JTextField();
    private JButton registButton = new JButton("注 册");
    private JButton resetButton = new JButton("重 置");
    private JButton backButton = new JButton("返 回");


    @Override
    protected void setFontAndSoOn() {
        mainPanel.setLayout(null);
        logoLabel.setBounds(135,40,40,40);
        logoLabel.setIcon(this.drawImage("src//img//【lol】安妮Annie_海克斯科技01_1920x1080.jpg",40,40));
        titleLanel.setBounds(185,40,200,40);
        titleLanel.setFont(new Font("微软雅黑",Font.ITALIC,24));

        accountLabel.setBounds(40,100,140,40);
        accountLabel.setFont(new Font("微软雅黑",Font.BOLD,18));
        accountField.setBounds(170,105,260,40);
        accountField.setFont(new Font("微软雅黑",Font.BOLD,18));

        passwordLabel.setBounds(40,150,140,40);
        passwordLabel.setFont(new Font("微软雅黑",Font.BOLD,18));
        passwordField.setBounds(170,155,260,32);
        passwordField.setFont(new Font("微软雅黑",Font.BOLD,18));

        balancedLabel.setBounds(40,200,140,40);
        balancedLabel.setFont(new Font("微软雅黑",Font.BOLD,18));
        balancedField.setBounds(170,205,260,32);
        balancedField.setFont(new Font("微软雅黑",Font.BOLD,18));

        registButton.setBounds(60,260,100,32);
        registButton.setFont(new Font("微软雅黑",Font.BOLD,14));
        registButton.setBackground(Color.LIGHT_GRAY);

        resetButton.setBounds(190,260,100,32);
        resetButton.setFont(new Font("微软雅黑",Font.BOLD,14));
        resetButton.setBackground(Color.LIGHT_GRAY);

        backButton.setBounds(320,260,100,32);
        backButton.setFont(new Font("微软雅黑",Font.BOLD,14));
        backButton.setBackground(Color.LIGHT_GRAY);
    }

    @Override
    protected void addElements() {
        this.add(mainPanel);
        mainPanel.add(logoLabel);
        mainPanel.add(titleLanel);
        mainPanel.add(accountField);
        mainPanel.add(accountLabel);
        mainPanel.add(passwordLabel);
        mainPanel.add(passwordField);
        mainPanel.add(balancedLabel);
        mainPanel.add(balancedField);
        mainPanel.add(registButton);
        mainPanel.add(resetButton);
        mainPanel.add(backButton);
    }

    @Override
    protected void addListener() {
        registButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //获取名字，密码，余额
                String aname = accountField.getText();
                String apassword = passwordField.getText();
                String abalance = balancedField.getText();
                //调用新增方法
                AtmService atmService = MySpring.getBean("service.AtmService");
                if(atmService.isExit(aname)){
                    JOptionPane.showMessageDialog(RegistFrame.this,"账号已存在");
                    RegistFrame.this.reset();
                }else {
                    try{
                        atmService.open(aname,apassword,Float.parseFloat(abalance));
                        JOptionPane.showMessageDialog(RegistFrame.this,"注册成功，请登录");
                        RegistFrame.this.back();
                    } catch(NumberFormatException nfe){
                        JOptionPane.showMessageDialog(RegistFrame.this,"您输入的金额有误");
                        RegistFrame.this.reset();
                    }
                }
            }
        });

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RegistFrame.this.reset();
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RegistFrame.this.reset();
                RegistFrame.this.back();
            }
        });
    }

    //设计一个自定义方法  设置所有输入框清空
     void reset(){
        accountField.setText("");
        passwordField.setText("");
        balancedField.setText("");
    }

    //设计一个自定义方法  设置返回登陆界面
    private void back(){
        this.setVisible(false);
        loginFrame.setVisible(true);
        loginFrame.reset();
    }

    @Override
    protected void setFrameSelf() {
        this.setBounds(430,200,500,360);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);

    }
}
