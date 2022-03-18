package sensor.component.accountSetting;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import sensor.Main;
import sensor.component.Database;

/**
 * 修改密码
 *
 * @author ysr
 */
public class ChangePassword extends JPanel implements ActionListener {

    private String loginUser;

    private JLabel slogan = new JLabel("修 改 密 码", JLabel.CENTER);

    private PreparedStatement statement;
    private Connection con;
    private Database db;
    private JSplitPane spliter = new JSplitPane(JSplitPane.VERTICAL_SPLIT,true);
    private JPanel mainPanel = new JPanel();
    private JLabel oldPasswordCaption = new JLabel("原密码:",SwingConstants.RIGHT);
    private JLabel newPasswordCaption = new JLabel("新密码:",SwingConstants.RIGHT);
    private JLabel againPasswordCaption = new JLabel("确认密码:",SwingConstants.RIGHT);
    private JPasswordField oldPasswordField =  new JPasswordField();
    private JPasswordField newPasswordField = new JPasswordField();
    private JPasswordField againPasswordField = new JPasswordField();
    private JButton confirmButton = new JButton("确认修改");
    private JButton clearButton =new JButton("清空");

    public ChangePassword(String user){
        this.loginUser = user;
        slogan.setBounds(350,100,300,40);
        slogan.setFont(new Font("华文楷体",Font.PLAIN,40));

        oldPasswordCaption.setBounds(350,200,150,25);
        newPasswordCaption.setBounds(350,300,150,25);
        againPasswordCaption.setBounds(350,400,150,25);
        oldPasswordField.setBounds(550,200,200,30);
        newPasswordField.setBounds(550,300,200,30);
        againPasswordField.setBounds(550,400,200,30);
        confirmButton.setBounds(350,500,150,30);
        clearButton.setBounds(600,500,150,30);

        oldPasswordCaption.setFont(new Font("华文楷体",Font.PLAIN,25));
        newPasswordCaption.setFont(new Font("华文楷体",Font.PLAIN,25));
        againPasswordCaption.setFont(new Font("华文楷体",Font.PLAIN,25));
        confirmButton.setFont(new Font("华文楷体",Font.PLAIN,25));
        clearButton.setFont(new Font("华文楷体",Font.PLAIN,25));
        oldPasswordField.setFont(new Font("华文楷体",Font.PLAIN,25));
        newPasswordField.setFont(new Font("华文楷体",Font.PLAIN,25));
        againPasswordField.setFont(new Font("华文楷体",Font.PLAIN,25));

        confirmButton.addActionListener(this);
        clearButton.addActionListener(this);
        mainPanel.add(slogan);
        mainPanel.add(oldPasswordCaption);
        mainPanel.add(newPasswordCaption);
        mainPanel.add(againPasswordCaption);
        mainPanel.add(oldPasswordField);
        mainPanel.add(newPasswordField);
        mainPanel.add(againPasswordField);
        mainPanel.add(confirmButton);
        mainPanel.add(clearButton);
        JLabel backgroundLabel = new JLabel();
        URL url= Main.class.getResource("static/update.jpg");
        ImageIcon imageIcon = new ImageIcon(url);
        backgroundLabel.setIcon(imageIcon);
        backgroundLabel.setLayout(null);
        backgroundLabel.setBounds(0,0,2000,1000);
        mainPanel.add(backgroundLabel);
        this.setLayout(new GridLayout(1,1));
        spliter.setTopComponent(mainPanel);
        mainPanel.setLayout(null);
        mainPanel.setBounds(1000,0,3000,100);
        spliter.setDividerLocation(500);
        spliter.setDividerSize(5);
        this.add(spliter);
        this.setVisible(true);

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()== clearButton){
            oldPasswordField.setText("");
            newPasswordField.setText("");
            againPasswordField.setText("");
            return;
        }
        if(e.getSource()== confirmButton){
            String oldPassword = String.valueOf(oldPasswordField.getPassword());
            String newPassword = String.valueOf(newPasswordField.getPassword());
            String againPassword=String.valueOf(againPasswordField.getPassword());
            if(!newPassword.equals(againPassword)){
                JOptionPane.showMessageDialog(this,"两次输入密码不一致，请重新输入!","消息",JOptionPane.INFORMATION_MESSAGE);
                newPasswordField.setText("");
                againPasswordField.setText("");
            } else {
                if (againPassword.length() == 0){
                    JOptionPane.showMessageDialog(this,"新密码不能为空!","消息",JOptionPane.INFORMATION_MESSAGE);
                    newPasswordField.setText("");
                    againPasswordField.setText("");
                }
                db = new Database();
                String searchSql = String.format( "SELECT * FROM t_user WHERE username='%s' AND password='%s';", this.loginUser, oldPassword);
                String updateSql = String.format( "UPDATE t_user SET password='%s' where username='%s';", newPassword, this.loginUser);
                db=new Database();
                try{
                    statement=db.getCon().prepareStatement(searchSql);
                    ResultSet rs=statement.executeQuery();
                    if(rs.next()){
                        if(oldPassword.equals(newPassword)){
                            JOptionPane.showMessageDialog(this,"新密码不能与原密码一致，请重新输入","消息",JOptionPane.INFORMATION_MESSAGE);
                            newPasswordField.setText("");
                            againPasswordField.setText("");
                            return;
                        }
                        statement=db.getCon().prepareStatement(updateSql);
                        boolean bl=statement.execute();
                        if(!bl){
                            JOptionPane.showMessageDialog(this,"修改成功!","消息",JOptionPane.INFORMATION_MESSAGE);
                            oldPasswordField.setText("");
                            newPasswordField.setText("");
                            againPasswordField.setText("");
                        }
                    }
                    else{
                        JOptionPane.showMessageDialog(this,"原密码错误!","消息",JOptionPane.INFORMATION_MESSAGE);
                        oldPasswordField.setText("");
                        newPasswordField.setText("");
                        againPasswordField.setText("");
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

}