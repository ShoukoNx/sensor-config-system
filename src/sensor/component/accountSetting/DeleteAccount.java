package sensor.component.accountSetting;

import sensor.Main;
import sensor.component.Database;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

/**
 * 删除用户
 *
 * @author ysr
 */
public class DeleteAccount extends JPanel implements ActionListener {

    private PreparedStatement statement;
    private Connection con;
    private Database db;

    private JLabel slogan = new JLabel("账 号 删 除", JLabel.CENTER);

    private JSplitPane spliter =new JSplitPane(JSplitPane.VERTICAL_SPLIT,true);

    private JLabel usernameCaption = new JLabel("用户名:",SwingConstants.RIGHT);
    private JLabel passwordCaption = new JLabel("密码:",SwingConstants.RIGHT);
    private JLabel againPasswordCaption = new JLabel("确认密码:",SwingConstants.RIGHT);
    private JTextField usernameField = new JTextField();
    private JPasswordField passwordField = new JPasswordField();
    private JPasswordField againPasswordField = new JPasswordField();
    private JPanel mainPanel = new JPanel();

    private JButton deleteButton = new JButton("删除");
    private JButton clearButton = new JButton("清空");



    public DeleteAccount(){


        this.setLayout(new GridLayout(1,1));
        spliter.setTopComponent(mainPanel);
        mainPanel.setLayout(null);
        mainPanel.add(slogan);
        mainPanel.add(usernameCaption);
        mainPanel.add(passwordCaption);
        mainPanel.add(againPasswordCaption);

        slogan.setBounds(350,100,300,40);
        slogan.setFont(new Font("华文楷体",Font.PLAIN,40));
        usernameCaption.setBounds(400,200,120,25);
        passwordCaption.setBounds(400,300,120,25);
        againPasswordCaption.setBounds(400,400,120,25);
        usernameCaption.setFont(new Font("华文楷体",Font.PLAIN,25));
        passwordCaption.setFont(new Font("华文楷体",Font.PLAIN,25));
        againPasswordCaption.setFont(new Font("华文楷体",Font.PLAIN,25));
        mainPanel.add(usernameField);
        mainPanel.add(passwordField);
        mainPanel.add(againPasswordField);
        usernameField.setBounds(550,200,200,30);
        passwordField.setBounds(550,300,200,30);
        againPasswordField.setBounds(550,400,200,30);
        usernameField.setFont(new Font("华文楷体",Font.PLAIN,25));
        passwordField.setFont(new Font("华文楷体",Font.PLAIN,25));
        againPasswordField.setFont(new Font("华文楷体",Font.PLAIN,25));

        mainPanel.add(deleteButton);
        mainPanel.add(clearButton);
        deleteButton.setBounds(400,500,100,30);
        clearButton.setBounds(650,500,100,30);
        deleteButton.setFont(new Font("华文楷体",Font.PLAIN,25));
        clearButton.setFont(new Font("华文楷体",Font.PLAIN,25));
        deleteButton.addActionListener(this);
        clearButton.addActionListener(this);

        JLabel backgroundLabel=new JLabel();

        URL url= Main.class.getResource("static/delete.jpg");
        ImageIcon imageIcon = new ImageIcon(url);
        backgroundLabel.setIcon(imageIcon);
        backgroundLabel.setLayout(null);
        backgroundLabel.setBounds(0,0,2000,1000);
        mainPanel.add(backgroundLabel);

        mainPanel.setBounds(1000,0,3000,100);
        spliter.setDividerLocation(250);
        spliter.setDividerSize(5);
        this.add(spliter);
        this.setVisible(true);

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == clearButton){
            usernameField.setText("");
            passwordField.setText("");
            againPasswordField.setText("");
            return;
        }
        if(e.getSource()== deleteButton){
            String username= usernameField.getText().trim();
            String password = String.valueOf(passwordField.getPassword());
            String againPassword = String.valueOf(againPasswordField.getPassword());
            if(username.equals("admin")){
                JOptionPane.showMessageDialog(this,"不能删除admin用户!","消息",JOptionPane.INFORMATION_MESSAGE);
                usernameField.setText("");
                passwordField.setText("");
                againPasswordField.setText("");
                return;
            }

            if(username.equals("") || password.equals("") || againPassword.equals("")){
                JOptionPane.showMessageDialog(this,"用户名或密码不能为空!","消息",JOptionPane.INFORMATION_MESSAGE);
                usernameField.setText("");
                passwordField.setText("");
                againPasswordField.setText("");
                return;
            }

            if(!password.equals(againPassword)){
                JOptionPane.showMessageDialog(this,"两次密码不一致!","消息",JOptionPane.INFORMATION_MESSAGE);
                usernameField.setText("");
                passwordField.setText("");
                againPasswordField.setText("");
                return;
            }

            String searchSql = String.format("SELECT * FROM t_user WHERE username = '%s'", username);
            String deleteSql = String.format("DELETE FROM t_user WHERE username = '%s'", username);
            db=new Database();
            try{
                statement=db.getCon().prepareStatement(searchSql);
                ResultSet rs=statement.executeQuery();
                if(rs.next()){
                    statement=db.getCon().prepareStatement(deleteSql);
                    boolean bl=statement.execute();
                    if(!bl){
                        JOptionPane.showMessageDialog(this,"删除成功!","消息",JOptionPane.INFORMATION_MESSAGE);
                        usernameField.setText("");
                        passwordField.setText("");
                        againPasswordField.setText("");
                    }
                }
                else{
                    JOptionPane.showMessageDialog(this,"用户不存在!","消息",JOptionPane.INFORMATION_MESSAGE);
                }

            }catch (Exception exception){
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
