package sensor.component.measureAnalysis;

import sensor.Main;
import sensor.component.Database;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

/**
 * 测量量分析界面
 *
 * @author ysr
 */
public class MeasureAnalysisPanel extends JPanel implements ActionListener {


    // 数据库准备部分
    private PreparedStatement statement;
    private Connection con;
    Database db=new Database();
    String Sql;

    // 按钮定义
    private static final int BUTTON_FONT_SIZE = 25;
    private static final String BUTTON_FONT = "华文楷体";

    private JButton searchButton =new JButton("测量量参数");
    private JButton createButton =new JButton("新建测量量");
    private JButton updateButton =new JButton("修改测量量");
    private JButton deleteButton =new JButton("删除测量量");

    private JButton distanceButton =new JButton("位移");
    private JButton temperatureButton =new JButton("温度");
    private JButton voltageButton =new JButton("电压");
    private JButton forceButton =new JButton("应力");
    private JButton speedButton =new JButton("速度");

    // 显示表格定义
    Vector<String> head = new Vector<String>();
    {
        head.add("序号");
        head.add("测量量ID");
        head.add("名称");
        head.add("类型");
        head.add("当前值");
        head.add("标准范围");
        head.add("测量频率");
        head.add("描述");
    }
    Vector<Vector> data=new Vector<Vector>();
    DefaultTableModel dtm=new DefaultTableModel(data, head);
    JTable table =new JTable(dtm);
    // 这一步设置出滚轮
    JScrollPane scrollPane =new JScrollPane(table);

    // 创建上下分割线
    private JSplitPane spliter =new JSplitPane(JSplitPane.VERTICAL_SPLIT,true);


    public MeasureAnalysisPanel(){

        /* 上半部分图片显示 */
        JPanel upperPanel = new JPanel();
        ImageIcon bogiePicture = new ImageIcon(Main.class.getResource("static/bogie.png"));
        JLabel bogiePictureLabel=new JLabel();
        bogiePictureLabel.setIcon(bogiePicture);
        bogiePictureLabel.setBounds(400,0,2000,1000);
        upperPanel.add(bogiePictureLabel);

        /* 下半部分表格区域的显示 */
        table.setFont(new Font("华文楷体",Font.PLAIN,20));
        table.setRowHeight(30);
        JTableHeader tabHeader = table.getTableHeader();
        tabHeader.setFont(new Font("华文楷体",Font.PLAIN,20));
        // 这一步使得表格可以用滚轮
        JSplitPane bottomPanel = new JSplitPane(JSplitPane.VERTICAL_SPLIT,true);
        JPanel buttonAreaPanel = new JPanel();
        this.setLayout(new GridLayout(1,1));
        bottomPanel.setTopComponent(buttonAreaPanel);
        bottomPanel.setBottomComponent(scrollPane);
        bottomPanel.setDividerLocation(150);
        bottomPanel.setDividerSize(5);

        searchButton.setBounds(50,20,200,30);
        searchButton.setFont(new Font(BUTTON_FONT,Font.PLAIN,BUTTON_FONT_SIZE));
        searchButton.addActionListener(this);

        createButton.setBounds(275,20,200,30);
        createButton.setFont(new Font(BUTTON_FONT,Font.PLAIN,BUTTON_FONT_SIZE));
        createButton.addActionListener(this);

        updateButton.setBounds(500,20,200,30);
        updateButton.setFont(new Font(BUTTON_FONT,Font.PLAIN,BUTTON_FONT_SIZE));
        updateButton.addActionListener(this);

        deleteButton.setBounds(725,20,200,30);
        deleteButton.setFont(new Font(BUTTON_FONT,Font.PLAIN,BUTTON_FONT_SIZE));
        deleteButton.addActionListener(this);

        distanceButton.setBounds(50,100,160,30);
        distanceButton.setFont(new Font(BUTTON_FONT,Font.PLAIN,BUTTON_FONT_SIZE));
        distanceButton.addActionListener(this);

        temperatureButton.setBounds(250,100,160,30);
        temperatureButton.setFont(new Font(BUTTON_FONT,Font.PLAIN,BUTTON_FONT_SIZE));
        temperatureButton.addActionListener(this);

        voltageButton.setBounds(450,100,160,30);
        voltageButton.setFont(new Font(BUTTON_FONT,Font.PLAIN,BUTTON_FONT_SIZE));
        voltageButton.addActionListener(this);

        forceButton.setBounds(650,100,160,30);
        forceButton.setFont(new Font(BUTTON_FONT,Font.PLAIN,BUTTON_FONT_SIZE));
        forceButton.addActionListener(this);

        speedButton.setBounds(850,100,160,30);
        speedButton.setFont(new Font(BUTTON_FONT,Font.PLAIN,BUTTON_FONT_SIZE));
        speedButton.addActionListener(this);

        buttonAreaPanel.setLayout(null);
        buttonAreaPanel.add(searchButton);
        buttonAreaPanel.add(createButton);
        buttonAreaPanel.add(updateButton);
        buttonAreaPanel.add(deleteButton);
        buttonAreaPanel.add(distanceButton);
        buttonAreaPanel.add(temperatureButton);
        buttonAreaPanel.add(voltageButton);
        buttonAreaPanel.add(forceButton);
        buttonAreaPanel.add(speedButton);
        buttonAreaPanel.setBounds(1000,0,3000,150);

        JLabel buttonAreaBackgroundLabel = new JLabel();
        ImageIcon buttonAreaBackground = new ImageIcon(Main.class.getResource("static/register.jpg"));
        buttonAreaBackgroundLabel.setIcon(buttonAreaBackground);
        buttonAreaBackgroundLabel.setLayout(null);
        buttonAreaBackgroundLabel.setBounds(0,0,2000,1000);
        buttonAreaPanel.add(buttonAreaBackgroundLabel);

        // 设置上下分割线以及上下区域的内容
        // 分割线距离上边的距离由转向架图片的高度加10
        spliter.setDividerLocation(bogiePicture.getIconHeight() + 10);
        spliter.setDividerSize(5);
        spliter.setTopComponent(upperPanel);
        spliter.setBottomComponent(bottomPanel);
        spliter.setEnabled(false);
        this.add(spliter);
        this.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()== createButton){
            new CreateMeasure();
            return;
        } else if(e.getSource()== deleteButton){
            new DeleteMeasure();
            return;
        } else if(e.getSource()== updateButton){
            new UpdateMeasure();
            return;
        }
        db = new Database();
        if(e.getSource()== searchButton){
            Sql = "SELECT * FROM t_measure;";
        }else if(e.getSource() == distanceButton){
            Sql = "SELECT * FROM t_measure WHERE type='位移';";
        }
        else if(e.getSource() == temperatureButton){
            Sql = "SELECT * FROM t_measure WHERE type='温度';";
        }
        else if(e.getSource() == voltageButton){
            Sql = "SELECT * FROM t_measure WHERE type='电压';";
        }
        else if(e.getSource() == forceButton){
            Sql = "SELECT * FROM t_measure WHERE type='应力';";
        }
        else if(e.getSource() == speedButton){
            Sql = "SELECT * FROM t_measure WHERE type='速度';";
        }else{
            ((DefaultTableModel) table.getModel()).getDataVector().clear();
            return;
        }
        ((DefaultTableModel) table.getModel()).getDataVector().clear();
        try{
            statement = db.getCon().prepareStatement(Sql);
            ResultSet rs= statement.executeQuery();
            // JOptionPane.showMessageDialog(this,"查询成功","消息",JOptionPane.INFORMATION_MESSAGE);
            while (rs.next()){
                Vector<String> v=new Vector<String>();
                v.add(rs.getString("id"));
                v.add(rs.getString("measure_id"));
                v.add(rs.getString("name"));
                v.add(rs.getString("type"));
                v.add(rs.getString("current_value"));
                v.add(rs.getString("measure_range"));
                v.add(rs.getString("frequency"));
                v.add(rs.getString("description"));
                data.add(v);
                dtm.setDataVector(data, head);
                table.updateUI();
                table.repaint();
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
