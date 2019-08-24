package view;

import service.AtmService;
import util.BaseFrame;
import util.MySpring;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("all")
public class AtmFrame extends BaseFrame {

    private AtmFrame(String aname){
        super("Annie Bank");
        this.aname = aname;
        this.init();
    }
    private static AtmFrame atmFrame = null;
    public synchronized static AtmFrame getAtmFrame(String aname){
        if(atmFrame == null){
            atmFrame = new AtmFrame(aname);
        }
        return atmFrame;
    }

    //添加一个用来管理当前用户的用户名
    private String aname;

    //创建一个AtmService对象作为属性，支持着所有的业务：查询，存款，取款，转账
    private AtmService atmService = MySpring.getBean("service.AtmService");

    private JPanel mainPanel = new JPanel();
    private JLabel logoLabel = new JLabel();
    private JLabel titleLabel = new JLabel("Annie Bank");
    private JLabel balanceLabelCN = new JLabel();
    private JLabel balanceLabelEN = new JLabel();
    private JLabel selectServerLabelCN = new JLabel("您好！请选择所需服务");
    private JLabel selectServerLabelEN = new JLabel("Hello,Please Select Service");
    private JButton dropButton = new JButton("销户");
    private JButton exitButton = new JButton("退出");
    private JButton depositButton = new JButton("存款");
    private JButton withdrawalButton = new JButton("取款");
    private JButton transferButton = new JButton("转账");


    @Override
    protected void setFontAndSoOn() {
        mainPanel.setLayout(null);
        logoLabel.setBounds(20,20,80,80);
        logoLabel.setIcon(this.drawImage("src//img//【lol】安妮Annie_海克斯科技01_1920x1080.jpg",80,80));
        titleLabel.setBounds(120,30,260,60);
        titleLabel.setFont(new Font("微软雅黑",Font.ITALIC,32));

        balanceLabelCN.setBounds(240,200,300,40);
        balanceLabelCN.setFont(new Font("微软雅黑",Font.BOLD,24));
        balanceLabelCN.setHorizontalAlignment(JTextField.CENTER);
        balanceLabelCN.setText("账户余额:￥"+atmService.query(aname));
        balanceLabelEN.setBounds(240,240,400,40);
        balanceLabelEN.setFont(new Font("微软雅黑",Font.BOLD,24));
        balanceLabelEN.setHorizontalAlignment(JTextField.CENTER);
        balanceLabelEN.setText("Account Balance:￥"+atmService.query(aname));

        selectServerLabelCN.setBounds(260,370,300,40);
        selectServerLabelCN.setFont(new Font("微软雅黑",Font.BOLD,24));
        selectServerLabelCN.setHorizontalAlignment(JTextField.CENTER);
        selectServerLabelEN.setBounds(220,400,360,40);
        selectServerLabelEN.setFont(new Font("微软雅黑",Font.BOLD,24));
        selectServerLabelEN.setHorizontalAlignment(JTextField.CENTER);

        dropButton.setBounds(10,150,120,40);
        dropButton.setFont(new Font("微软雅黑",Font.BOLD,14));
        dropButton.setBackground(Color.lightGray);
        dropButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        exitButton.setBounds(10,390,120,40);
        exitButton.setFont(new Font("微软雅黑",Font.BOLD,14));
        exitButton.setBackground(Color.lightGray);
        exitButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        depositButton.setBounds(670,150,120,40);
        depositButton.setFont(new Font("微软雅黑",Font.BOLD,14));
        depositButton.setBackground(Color.lightGray);
        depositButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        withdrawalButton.setBounds(670,270,120,40);
        withdrawalButton.setFont(new Font("微软雅黑",Font.BOLD,14));
        withdrawalButton.setBackground(Color.lightGray);
        withdrawalButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        transferButton.setBounds(670,390,120,40);
        transferButton.setFont(new Font("微软雅黑",Font.BOLD,14));
        transferButton.setBackground(Color.lightGray);
        transferButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    @Override
    protected void addElements() {
        mainPanel.add(logoLabel);
        mainPanel.add(titleLabel);
        mainPanel.add(balanceLabelCN);
        mainPanel.add(balanceLabelEN);
        mainPanel.add(selectServerLabelCN);
        mainPanel.add(selectServerLabelEN);
        mainPanel.add(dropButton);
        mainPanel.add(exitButton);
        mainPanel.add(depositButton);
        mainPanel.add(withdrawalButton);
        mainPanel.add(transferButton);
        this.add(mainPanel);
    }

    @Override
    protected void addListener() {

        dropButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int value = JOptionPane.showConfirmDialog(AtmFrame.this,"确认消除本账户吗？");
                if(value == 0){
                    JOptionPane.showMessageDialog(AtmFrame.this,"销户成功");
                    atmService.drop(aname);
                    System.exit(0);
                }else{
                    JOptionPane.showMessageDialog(AtmFrame.this,"销户失败");
                }
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int value = JOptionPane.showConfirmDialog(AtmFrame.this,"确定退出吗？");
                //0是  1否  2取消
                if(value == 0){
                    System.exit(0);
                }
            }
        });


        depositButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    String value = JOptionPane.showInputDialog(AtmFrame.this,"请输入存款金额");
                    if(value != null && !"".equals(value)){
                        Float money = Float.parseFloat(value);
                        if(money <= 0){
                            throw new NumberFormatException();
                        }
                        int count = atmService.deposit(aname,money);
                        if(count == 1){
                            JOptionPane.showMessageDialog(AtmFrame.this,"存款成功");
                            balanceLabelCN.setText("账户余额:￥"+atmService.query(aname));
                            balanceLabelEN.setText("Account Balance:￥"+atmService.query(aname));
                        }else{
                            JOptionPane.showMessageDialog(AtmFrame.this,"存款失败");
                        }

                    }
                }catch(NumberFormatException nfe){
                    JOptionPane.showMessageDialog(AtmFrame.this,"输入金额有误");
                }
            }
        });

        withdrawalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    String value = JOptionPane.showInputDialog(AtmFrame.this,"请输入取款金额");
                    if(value != null && !"".equals(value)){
                        Float money = Float.parseFloat(value);
                        if(money <= 0){
                            throw new NumberFormatException();
                        }
                        int count = atmService.withdrawal(aname,money);
                        if(count == 1){
                            JOptionPane.showMessageDialog(AtmFrame.this,"取款成功");
                            balanceLabelCN.setText("账户余额:￥"+atmService.query(aname));
                            balanceLabelEN.setText("Account Balance:￥"+atmService.query(aname));
                        }else if(count == -1){
                            JOptionPane.showMessageDialog(AtmFrame.this,"余额不足");
                        }else{
                            JOptionPane.showMessageDialog(AtmFrame.this,"取款失败");
                        }

                    }
                }catch(NumberFormatException nfe){
                    JOptionPane.showMessageDialog(AtmFrame.this,"输入金额有误");
                }
            }
        });

        transferButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    String to = JOptionPane.showInputDialog(AtmFrame.this,"请输入转账账户");
                    if(to != null && !"".equals(to) && atmService.isExit(to)){
                        String value = JOptionPane.showInputDialog(AtmFrame.this,"请输入转账金额");
                        if(value != null && !"".equals(value)){
                            Float money = Float.parseFloat(value);
                            if(money <= 0){
                                throw new NumberFormatException();
                            }
                            int count = atmService.transfer(aname,to,money);
                            if(count == 2){
                                JOptionPane.showMessageDialog(AtmFrame.this,"转账成功");
                                balanceLabelCN.setText("账户余额:￥"+atmService.query(aname));
                                balanceLabelEN.setText("Account Balance:￥"+atmService.query(aname));
                            }else if(count == -1){
                                JOptionPane.showMessageDialog(AtmFrame.this,"余额不足");
                            }else{
                                JOptionPane.showMessageDialog(AtmFrame.this,"转账失败");
                            }
                        }
                    }else{
                       JOptionPane.showMessageDialog(AtmFrame.this,"输入账号不存在");
                    }
                }catch(NumberFormatException nfe){
                    JOptionPane.showMessageDialog(AtmFrame.this,"输入金额有误");
                }
            }
        });

    }

    @Override
    protected void setFrameSelf() {
        this.setBounds(300,200,800,500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
    }

}
