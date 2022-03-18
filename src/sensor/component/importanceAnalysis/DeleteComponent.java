package sensor.component.importanceAnalysis;


import sensor.DefaultConfig;
import sensor.component.Database;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * 删除零部件弹窗
 *
 * @author ysr
 */
public class DeleteComponent extends JFrame implements ActionListener {

    private boolean checked;

    private final static int POPUP_WINDOW_WIDTH = 600;
    private final static int POPUP_WINDOW_HEIGHT = 300;
    private final static int TOP_MARGIN_BIAS = 300;
    private final static int LEFT_MARGIN_BIAS = 500;
    private final static String DEFAULT_FONT = "华文楷体";

    private PreparedStatement statement;
    private Connection con;

    private JPanel panel =new JPanel();

    private JLabel idCaption = new JLabel("零件ID:");
    private JLabel nameCaption = new JLabel("名称:");
    private JLabel countCaption = new JLabel("个数:");
    private JLabel descriptionCaption = new JLabel("描述:");

    private JTextField idField =new JTextField();
    private JLabel nameInfo = new JLabel();
    private JLabel countInfo = new JLabel();
    private JLabel descriptionInfo = new JLabel();

    private JButton searchButton =new JButton("检索并确认零件信息");
    private JButton confirmButton =new JButton("确认");
    private JButton exitButton =new JButton("退出");



    public DeleteComponent() {
        panel.setLayout(null);


        idCaption.setBounds(10,10,100,30);
        idCaption.setFont(new Font(DEFAULT_FONT,Font.PLAIN,22));
        idField.setBounds(100,10,150,30);
        idField.setFont(new Font(DEFAULT_FONT,Font.PLAIN,22));
        searchButton.setBounds(270,10,250,30);
        searchButton.setFont(new Font(DEFAULT_FONT,Font.PLAIN,22));
        searchButton.addActionListener(this);

        nameCaption.setBounds(10,50,100,30);
        nameCaption.setFont(new Font(DEFAULT_FONT,Font.PLAIN,22));
        nameInfo.setBounds(100,50,150,30);
        nameInfo.setFont(new Font(DEFAULT_FONT,Font.PLAIN,22));

        countCaption.setBounds(280,50,100,30);
        countCaption.setFont(new Font(DEFAULT_FONT,Font.PLAIN,22));
        countInfo.setBounds(400,50,150,30);
        countInfo.setFont(new Font(DEFAULT_FONT,Font.PLAIN,22));

        descriptionCaption.setBounds(10,100,100,30);
        descriptionCaption.setFont(new Font(DEFAULT_FONT,Font.PLAIN,22));
        descriptionInfo.setBounds(100,100,450,30);
        descriptionInfo.setFont(new Font(DEFAULT_FONT,Font.PLAIN,22));

        confirmButton.setBounds(320,200,100,40);
        confirmButton.setFont(new Font(DEFAULT_FONT,Font.PLAIN,22));
        confirmButton.addActionListener(this);

        exitButton.setBounds(450,200,100,40);
        exitButton.setFont(new Font(DEFAULT_FONT,Font.PLAIN,22));
        exitButton.addActionListener(this);

        panel.add(idCaption);
        panel.add(idField);
        panel.add(nameCaption);
        panel.add(nameInfo);
        panel.add(countCaption);
        panel.add(countInfo);
        panel.add(descriptionCaption);
        panel.add(descriptionInfo);
        panel.add(searchButton);
        panel.add(confirmButton);
        panel.add(exitButton);

        this.add(panel);
        this.setTitle("删除零部件");
        this.setVisible(true);
        this.setResizable(DefaultConfig.WINDOW_RESIZABLE);
        this.setBounds(DefaultConfig.WINDOW_LEFT_MARGIN + LEFT_MARGIN_BIAS,DefaultConfig.WINDOW_LEFT_MARGIN + TOP_MARGIN_BIAS, POPUP_WINDOW_WIDTH,POPUP_WINDOW_HEIGHT);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()== confirmButton){
            String componentId= idField.getText().trim();
            if (componentId.length() == 0 || !checked){
                JOptionPane.showMessageDialog(this,"请输入零件ID并确认零件信息","Warning",JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            Database db = new Database();
            String sql = String.format("DELETE FROM t_component WHERE component_id='%s';",componentId);
            try {
                statement=db.getCon().prepareStatement(sql);
                boolean failed=statement.execute();
                if(!failed){
                    JOptionPane.showMessageDialog(this,"删除成功!","消息",JOptionPane.INFORMATION_MESSAGE);
                    this.dispose();
                }
                else{
                    JOptionPane.showMessageDialog(this,"出现意外错误!","Warning",JOptionPane.INFORMATION_MESSAGE);
                }
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
        }else if(e.getSource()== searchButton){
            String componentId = idField.getText().trim();
            if (componentId.length() == 0){
                JOptionPane.showMessageDialog(this,"请输入零件ID","Warning",JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            Database db = new Database();
            String sql = String.format("SELECT * FROM t_component WHERE component_id='%s';", componentId);
            try {
                statement=db.getCon().prepareStatement(sql);
                ResultSet rs= statement.executeQuery();
                if (rs.next()){
                    this.nameInfo.setText(rs.getString("name"));
                    this.countInfo.setText(rs.getString("count"));
                    this.descriptionInfo.setText(rs.getString("description"));
                    checked = true;
                } else {
                    JOptionPane.showMessageDialog(this,"未查询到相关零件","Warning",JOptionPane.INFORMATION_MESSAGE);
                    checked = false;
                    return;
                }
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


    public static void main(String[] args) {
        new DeleteComponent();
    }


};
