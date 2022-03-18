package sensor;

import sensor.component.Database;
import sensor.component.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
/**
 * 主启动类、启动界面
 *
 * @author ysr
 */
public class Main extends JFrame implements ActionListener {

    // 默认全局设置
    private static final String DEFAULT_FONT = "华文楷体";

    // 数据库配置
    private PreparedStatement statement;
    private Connection con;
    String sql;

    private JPanel mainPanel =new JPanel();
    private JLabel loginArea =new JLabel();
    private JLabel text1 =new JLabel("动车组转向架",JLabel.CENTER);
    private JLabel text2 =new JLabel("传感器",JLabel.CENTER);
    private JLabel text3 =new JLabel("优化配置系统",JLabel.CENTER);
    private JLabel loginHeader =new JLabel("用 户 登 录");
    private JLabel usernameCaption =new JLabel("用户名:");
    private JLabel passwordCaption =new JLabel("密   码:");
    private JTextField usernameField =new JTextField();
    private JPasswordField passwordField =new JPasswordField();
    private JButton loginButton =new JButton("登录");
    private JButton exitButton =new JButton("退出");
    public Main(){
        URL url= Main.class.getResource("static/login.png");
        ImageIcon imageIcon = new ImageIcon(url);
        loginArea.setIcon(imageIcon);
        mainPanel.setLayout(null);
        loginArea.setLayout(null);
        text1.setLayout(null);
        text2.setLayout(null);
        text3.setLayout(null);
        loginHeader.setLayout(null);
        usernameCaption.setLayout(null);
        passwordCaption.setLayout(null);
        usernameField.setLayout(null);
        passwordField.setLayout(null);
        loginButton.setLayout(null);
        exitButton.setLayout(null);
        mainPanel.setBounds(0,0,1440,900);
        loginArea.setBounds(0,0,1440,900);
        text1.setBounds(220,260,600,80);
        text2.setBounds(270,400,500,80);
        text3.setBounds(250,540,500,80);
        loginHeader.setBounds(900,200,300,50);
        usernameCaption.setBounds(880,300,100,50);
        passwordCaption.setBounds(880,400,100,50);
        usernameField.setBounds(1000,305,200,40);
        passwordField.setBounds(1000,405,200,40);

        usernameCaption.setBackground(Color.gray);
        passwordCaption.setBackground(Color.gray);
        usernameCaption.setFont(new Font(DEFAULT_FONT,Font.PLAIN,30));
        passwordCaption.setFont(new Font(DEFAULT_FONT,Font.PLAIN,30));
        usernameField.setFont(new Font(DEFAULT_FONT,Font.PLAIN,30));
        passwordField.setFont(new Font(DEFAULT_FONT,Font.PLAIN,30));
        loginButton.setBounds(850,650,150,50);
        loginButton.setBackground(Color.pink);
        exitButton.setBackground(Color.pink);
        exitButton.setBounds(1080,650,150,50);
        loginButton.setFont(new Font(DEFAULT_FONT,Font.PLAIN,30));
        exitButton.setFont(new Font(DEFAULT_FONT,Font.PLAIN,30));
        text1.setFont(new Font(DEFAULT_FONT,Font.PLAIN,70));
        text2.setFont(new Font(DEFAULT_FONT,Font.PLAIN,70));
        text3.setFont(new Font(DEFAULT_FONT,Font.PLAIN,70));
        text1.setForeground(Color.white);
        text2.setForeground(Color.white);
        text3.setForeground(Color.white);
        loginHeader.setFont(new Font(DEFAULT_FONT,Font.BOLD,50));
        loginHeader.setForeground(Color.BLUE);
        loginButton.addActionListener(this);
        exitButton.addActionListener(this);
        loginArea.add(text1);
        loginArea.add(text2);
        loginArea.add(text3);
        loginArea.add(loginHeader);
        loginArea.add(usernameCaption);
        loginArea.add(passwordCaption);
        loginArea.add(usernameField);
        loginArea.add(passwordField);
        loginArea.add(loginButton);
        loginArea.add(exitButton);
        mainPanel.add(loginArea);
        this.setResizable(DefaultConfig.WINDOW_RESIZABLE);
        this.setBounds(DefaultConfig.WINDOW_LEFT_MARGIN,DefaultConfig.WINDOW_TOP_MARGIN,DefaultConfig.WINDOW_WIDTH,DefaultConfig.WINDOW_HEIGHT);
        // this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.add(mainPanel);
        this.setTitle("动车组转向架传感器优化配置系统");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()== exitButton)
        {
            int i=JOptionPane.showConfirmDialog(Main.this,"是否退出系统？",
                    "消息",JOptionPane.YES_NO_OPTION);
            if(i==JOptionPane.YES_OPTION)
            {
                System.exit(0);
            }
        }
        if(e.getSource()== loginButton)
        {
            Database db;
            char[] pwd= passwordField.getPassword();
            String password=String.valueOf(pwd);

            if(usernameField.getText().trim().equals("")||password.equals(""))
            {
                JOptionPane.showMessageDialog(this,"请输入用户名或密码","Warning",JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            else if(!usernameField.getText().trim().equals("")&&!password.equals(""))
            {
                String username= usernameField.getText().trim();
                sql =String.format("SELECT * FROM t_user where username=\"%s\" and password=\"%s\";", username, password);
                db=new Database();
                try {
                    statement=db.getCon().prepareStatement(sql);
                    ResultSet rs=statement.executeQuery();
                    if(rs.next()){
                        JOptionPane.showMessageDialog(this,"恭喜你，登录成功","消息",JOptionPane.INFORMATION_MESSAGE);
                        new MainFrame(username);
                        this.dispose();

                    }
                    else{
                        JOptionPane.showMessageDialog(this,"用户名或密码输入错误，请重新输入","消息",JOptionPane.INFORMATION_MESSAGE);
                        usernameField.setText("");
                        passwordField.setText("");
                    }
                    return;
                } catch (Exception exception) {
                    exception.printStackTrace();
                }finally {
                    try {
                        db.closeConnection(con);
                    }
                    catch (Exception exception) {
                        exception.printStackTrace();
                    }finally {
                        try {
                            db.closeConnection(con);
                        }
                        catch (Exception exception) {
                            exception.printStackTrace();
                        }
                    }
                }
            }
        }
    }
    public static void main(String[] args) {
        new Main();
    }
}
