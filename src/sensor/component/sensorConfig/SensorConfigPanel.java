package sensor.component.sensorConfig;

import sensor.Main;
import sensor.component.Database;
import sensor.component.measureAnalysis.CreateMeasure;
import sensor.component.measureAnalysis.DeleteMeasure;
import sensor.component.measureAnalysis.UpdateMeasure;

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
 * 传感器配置界面
 *
 * @author ysr
 */
public class SensorConfigPanel extends JPanel implements ActionListener {

    // 数据库准备部分
    private PreparedStatement statement;
    private Connection con;
    private Database db=new Database();
    private String sql;

    // 按钮定义
    private static final int BUTTON_FONT_SIZE = 25;
    private static final String BUTTON_FONT = "华文楷体";

    private JButton searchButton =new JButton("传感器配置");
    private JButton createButton =new JButton("新建传感器");
    private JButton updateButton =new JButton("修改传感器");
    private JButton deleteButton =new JButton("删除传感器");

    private JButton distanceButton =new JButton("位移配置");
    private JButton vibrationButton =new JButton("振动配置");
    private JButton forceButton =new JButton("应力配置");
    private JButton temperatureButton =new JButton("温度配置");
    private JButton moistureButton =new JButton("湿度配置");
    private JButton speedButton =new JButton("速度配置");
    private JButton accelerationButton =new JButton("加速度配置");
    private JButton soundButton =new JButton("声音配置");
    private JButton addButton =new JButton("+");

    // 显示表格定义
    private Vector<String> head = new Vector<String>();
    {
        head.add("序号");
        head.add("传感器ID");
        head.add("名称");
        head.add("类型");
        head.add("工作温度");
        head.add("工作电源");
        head.add("输出频率");
        head.add("测量量");
        head.add("测量范围");
        head.add("位置");
        head.add("描述");
    }
    private Vector<Vector> data = new Vector<Vector>();
    private DefaultTableModel dtm = new DefaultTableModel(data, head);
    private JTable table =new JTable(dtm);
    // 这一步设置出滚轮
    private JScrollPane scrollPane = new JScrollPane(table);

    // 创建上下分割线
    private JSplitPane spliter = new JSplitPane(JSplitPane.VERTICAL_SPLIT,true);


    public SensorConfigPanel(){

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
        bottomPanel.setDividerLocation(170);
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

        distanceButton.setBounds(50,70,160,30);
        distanceButton.setFont(new Font(BUTTON_FONT,Font.PLAIN,BUTTON_FONT_SIZE));
        distanceButton.addActionListener(this);

        vibrationButton.setBounds(250,70,160,30);
        vibrationButton.setFont(new Font(BUTTON_FONT,Font.PLAIN,BUTTON_FONT_SIZE));
        vibrationButton.addActionListener(this);

        forceButton.setBounds(450,70,160,30);
        forceButton.setFont(new Font(BUTTON_FONT,Font.PLAIN,BUTTON_FONT_SIZE));
        forceButton.addActionListener(this);

        temperatureButton.setBounds(650,70,160,30);
        temperatureButton.setFont(new Font(BUTTON_FONT,Font.PLAIN,BUTTON_FONT_SIZE));
        temperatureButton.addActionListener(this);

        moistureButton.setBounds(850,70,160,30);
        moistureButton.setFont(new Font(BUTTON_FONT,Font.PLAIN,BUTTON_FONT_SIZE));
        moistureButton.addActionListener(this);

        speedButton.setBounds(50,120,160,30);
        speedButton.setFont(new Font(BUTTON_FONT,Font.PLAIN,BUTTON_FONT_SIZE));
        speedButton.addActionListener(this);

        accelerationButton.setBounds(250,120,160,30);
        accelerationButton.setFont(new Font(BUTTON_FONT,Font.PLAIN,BUTTON_FONT_SIZE));
        accelerationButton.addActionListener(this);

        soundButton.setBounds(450,120,160,30);
        soundButton.setFont(new Font(BUTTON_FONT,Font.PLAIN,BUTTON_FONT_SIZE));
        soundButton.addActionListener(this);

        addButton.setBounds(650,120,60,30);
        addButton.setFont(new Font(BUTTON_FONT,Font.BOLD,BUTTON_FONT_SIZE));
        addButton.addActionListener(this);

        buttonAreaPanel.setLayout(null);
        buttonAreaPanel.add(searchButton);
        buttonAreaPanel.add(createButton);
        buttonAreaPanel.add(updateButton);
        buttonAreaPanel.add(deleteButton);
        buttonAreaPanel.add(distanceButton);
        buttonAreaPanel.add(vibrationButton);
        buttonAreaPanel.add(forceButton);
        buttonAreaPanel.add(temperatureButton);
        buttonAreaPanel.add(moistureButton);
        buttonAreaPanel.add(speedButton);
        buttonAreaPanel.add(accelerationButton);
        buttonAreaPanel.add(soundButton);
        buttonAreaPanel.add(addButton);


        buttonAreaPanel.setBounds(1000,0,3000,170);
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
            new CreateSensor();
            return;
        } else if(e.getSource()== deleteButton){
            new DeleteSensor();
            return;
        } else if(e.getSource()== updateButton){
            new UpdateSensor();
            return;
        }
        db = new Database();
        if(e.getSource()== searchButton){
            sql = "SELECT s.id, s.sensor_id, s.name AS sensor_name, " +
                    "s.type, s.temperature, s.power, s.frequency, " +
                    "m.name AS measure_name, m.measure_range, s.position, s.description " +
                    "FROM t_sensor s LEFT JOIN t_measure m ON s.measure_id = m.measure_id";
        }else{
            if(e.getSource() == distanceButton){
                sql = "SELECT s.id, s.sensor_id, s.name AS sensor_name, " +
                        "s.type, s.temperature, s.power, s.frequency, " +
                        "m.name AS measure_name, m.measure_range, s.position, s.description " +
                        "FROM t_sensor s LEFT JOIN t_measure m ON s.measure_id = m.measure_id" + " WHERE s.type = '位移'";
            }
            else if(e.getSource() == vibrationButton){
                sql = "SELECT s.id, s.sensor_id, s.name AS sensor_name, " +
                        "s.type, s.temperature, s.power, s.frequency, " +
                        "m.name AS measure_name, m.measure_range, s.position, s.description " +
                        "FROM t_sensor s LEFT JOIN t_measure m ON s.measure_id = m.measure_id" + " WHERE s.type = '振动'";
            }
            else if(e.getSource() == forceButton){
                sql = "SELECT s.id, s.sensor_id, s.name AS sensor_name, " +
                        "s.type, s.temperature, s.power, s.frequency, " +
                        "m.name AS measure_name, m.measure_range, s.position, s.description " +
                        "FROM t_sensor s LEFT JOIN t_measure m ON s.measure_id = m.measure_id" + " WHERE s.type = '应力'";
            }
            else if(e.getSource() == temperatureButton){
                sql = "SELECT s.id, s.sensor_id, s.name AS sensor_name, " +
                        "s.type, s.temperature, s.power, s.frequency, " +
                        "m.name AS measure_name, m.measure_range, s.position, s.description " +
                        "FROM t_sensor s LEFT JOIN t_measure m ON s.measure_id = m.measure_id" + " WHERE s.type = '温度'";
            }
            else if(e.getSource() == moistureButton){
                sql = "SELECT s.id, s.sensor_id, s.name AS sensor_name, " +
                        "s.type, s.temperature, s.power, s.frequency, " +
                        "m.name AS measure_name, m.measure_range, s.position, s.description " +
                        "FROM t_sensor s LEFT JOIN t_measure m ON s.measure_id = m.measure_id" + " WHERE s.type = '湿度'";
            }
            else if(e.getSource() == speedButton){
                sql = "SELECT s.id, s.sensor_id, s.name AS sensor_name, " +
                        "s.type, s.temperature, s.power, s.frequency, " +
                        "m.name AS measure_name, m.measure_range, s.position, s.description " +
                        "FROM t_sensor s LEFT JOIN t_measure m ON s.measure_id = m.measure_id" + " WHERE s.type = '速度'";
            }
            else if(e.getSource() == accelerationButton){
                sql = "SELECT s.id, s.sensor_id, s.name AS sensor_name, " +
                        "s.type, s.temperature, s.power, s.frequency, " +
                        "m.name AS measure_name, m.measure_range, s.position, s.description " +
                        "FROM t_sensor s LEFT JOIN t_measure m ON s.measure_id = m.measure_id" + " WHERE s.type = '加速度'";
            }
            else if(e.getSource() == soundButton){
                sql = "SELECT s.id, s.sensor_id, s.name AS sensor_name, " +
                        "s.type, s.temperature, s.power, s.frequency, " +
                        "m.name AS measure_name, m.measure_range, s.position, s.description " +
                        "FROM t_sensor s LEFT JOIN t_measure m ON s.measure_id = m.measure_id" + " WHERE s.type = '声音'";
            }else{
                ((DefaultTableModel) table.getModel()).getDataVector().clear();
                return;
            }
        }
        ((DefaultTableModel) table.getModel()).getDataVector().clear();
        try{
            statement = db.getCon().prepareStatement(sql);
            ResultSet rs= statement.executeQuery();
            // JOptionPane.showMessageDialog(this,"查询成功","消息",JOptionPane.INFORMATION_MESSAGE);
            while (rs.next()){
                Vector<String> v=new Vector<String>();
                v.add(rs.getString("s.id"));
                v.add(rs.getString("s.sensor_id"));
                v.add(rs.getString("sensor_name"));
                v.add(rs.getString("s.type"));
                v.add(rs.getString("s.temperature"));
                v.add(rs.getString("s.power"));
                v.add(rs.getString("s.frequency"));
                v.add(rs.getString("measure_name"));
                v.add(rs.getString("m.measure_range"));
                v.add(rs.getString("s.position"));
                v.add(rs.getString("s.description"));
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
