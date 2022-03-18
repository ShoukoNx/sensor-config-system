package sensor.component.importanceAnalysis;


import sensor.DefaultConfig;
import sensor.component.Database;
import sensor.component.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * 创建零部件弹窗
 *
 * @author ysr
 */
public class CreateComponent extends JFrame implements ActionListener {

    private final static int POPUP_WINDOW_WIDTH = 600;
    private final static int POPUP_WINDOW_HEIGHT = 300;
    private final static int TOP_MARGIN_BIAS = 300;
    private final static int LEFT_MARGIN_BIAS = 500;
    private final static String DEFAULT_FONT = "华文楷体";


    private PreparedStatement statement;
    private Connection con;

    private JPanel panel =new JPanel();

    private JLabel text =new JLabel("注意：零件ID和零件名称是必填项！");
    private JLabel idCaption =new JLabel("零件ID:");
    private JLabel nameCaption =new JLabel("零件名称:");
    private JLabel countCaption =new JLabel("个数:");
    private JLabel descriptionCaption =new JLabel("描述:");
    private JTextField idField =new JTextField();
    private JTextField nameField =new JTextField();
    private JTextField countField =new JTextField();
    private JTextField descriptionField =new JTextField();
    private JButton confirmButton =new JButton("确认");
    private JButton exitButton =new JButton("退出");



    public CreateComponent() {
        panel.setLayout(null);

        text.setBounds(10,10,450,30);
        text.setFont(new Font(DEFAULT_FONT,Font.BOLD,20));
        text.setForeground(Color.red);

        idCaption.setBounds(10,50,100,30);
        idCaption.setFont(new Font(DEFAULT_FONT,Font.PLAIN,22));
        idField.setBounds(100,50,150,30);
        idField.setFont(new Font(DEFAULT_FONT,Font.PLAIN,22));

        nameCaption.setBounds(280,50,100,30);
        nameCaption.setFont(new Font(DEFAULT_FONT,Font.PLAIN,22));
        nameField.setBounds(400,50,150,30);
        nameField.setFont(new Font(DEFAULT_FONT,Font.PLAIN,22));

        countCaption.setBounds(10,100,100,30);
        countCaption.setFont(new Font(DEFAULT_FONT,Font.PLAIN,22));
        countField.setBounds(100,100,150,30);
        countField.setFont(new Font(DEFAULT_FONT,Font.PLAIN,22));

        descriptionCaption.setBounds(10,150,100,30);
        descriptionCaption.setFont(new Font(DEFAULT_FONT,Font.PLAIN,22));
        descriptionField.setBounds(100,150,450,30);
        descriptionField.setFont(new Font(DEFAULT_FONT,Font.PLAIN,22));

        confirmButton.setBounds(320,200,100,40);
        confirmButton.setFont(new Font(DEFAULT_FONT,Font.PLAIN,22));
        confirmButton.addActionListener(this);

        exitButton.setBounds(450,200,100,40);
        exitButton.setFont(new Font(DEFAULT_FONT,Font.PLAIN,22));
        exitButton.addActionListener(this);

        panel.add(text);
        panel.add(idCaption);
        panel.add(idField);
        panel.add(nameCaption);
        panel.add(nameField);
        panel.add(countCaption);
        panel.add(countField);
        panel.add(descriptionCaption);
        panel.add(descriptionField);
        panel.add(confirmButton);
        panel.add(exitButton);

        this.add(panel);
        this.setTitle("新建零部件");
        this.setVisible(true);
        this.setResizable(DefaultConfig.WINDOW_RESIZABLE);
        this.setBounds(DefaultConfig.WINDOW_LEFT_MARGIN + LEFT_MARGIN_BIAS,DefaultConfig.WINDOW_LEFT_MARGIN + TOP_MARGIN_BIAS, POPUP_WINDOW_WIDTH,POPUP_WINDOW_HEIGHT);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()== confirmButton){
            Database db;
            String componentId= idField.getText().trim();
            String name= nameField.getText().trim();
            String countStr= countField.getText().trim();
            String description= descriptionField.getText().trim();
            if (componentId.length() == 0){
                JOptionPane.showMessageDialog(this,"请输入必填项：零件ID","Warning",JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            if (name.length() == 0){
                JOptionPane.showMessageDialog(this,"请输入必填项：零件名称","Warning",JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            int count;
            try{
                count = Integer.parseInt(countStr);
                if (count < 0){
                    JOptionPane.showMessageDialog(this,"个数应输入自然数","Warning",JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,"个数应输入自然数","Warning",JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            db=new Database();
            String sql = String.format("INSERT INTO t_component (component_id, name, count, description) VALUES ('%s','%s','%s','%s');",componentId, name, count, description);
            try {
                statement=db.getCon().prepareStatement(sql);
                boolean failed=statement.execute();
                if(!failed){
                    JOptionPane.showMessageDialog(this,"添加成功!","消息",JOptionPane.INFORMATION_MESSAGE);
                    this.dispose();
                }
                else{
                    JOptionPane.showMessageDialog(this,"数据库插入异常!","Warning",JOptionPane.INFORMATION_MESSAGE);
                }
            }catch (java.sql.SQLIntegrityConstraintViolationException e1){
                JOptionPane.showMessageDialog(this,"该零件ID已存在!","Warning",JOptionPane.INFORMATION_MESSAGE);
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

        }else if(e.getSource()== exitButton){
            this.dispose();
        }
    }



    public static void main(String[] args) {
        new CreateComponent();
    }


};
