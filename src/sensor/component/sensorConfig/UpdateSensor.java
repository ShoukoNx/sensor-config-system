package sensor.component.sensorConfig;


import sensor.DefaultConfig;
import sensor.component.Database;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Objects;

/**
 * 更新传感器弹窗
 *
 * @author ysr
 */
public class UpdateSensor extends JFrame implements ActionListener {

    private boolean checked;

    private final static int POPUP_WINDOW_WIDTH = 600;
    private final static int POPUP_WINDOW_HEIGHT = 380;
    private final static int TOP_MARGIN_BIAS = 300;
    private final static int LEFT_MARGIN_BIAS = 500;
    private final static String DEFAULT_FONT = "华文楷体";

    private PreparedStatement statement;
    private Connection con;

    private JPanel panel = new JPanel();

    private JLabel idCaption = new JLabel("传感器ID:");
    private JLabel nameCaption = new JLabel("传感器名称:");
    private JLabel typeCaption = new JLabel("类型:");
    private JLabel temperatureCaption = new JLabel("工作温度:");
    private JLabel powerCaption = new JLabel("工作电源:");
    private JLabel frequencyCaption = new JLabel("输出频率:");
    private JLabel measureIdCaption = new JLabel("测量量ID:");
    private JLabel positionCaption = new JLabel("位置:");
    private JLabel descriptionCaption = new JLabel("描述:");


    private JTextField idField = new JTextField();
    private JTextField nameField = new JTextField();
    private JComboBox typeComboBox=new JComboBox();
    {
        typeComboBox.addItem("位移");
        typeComboBox.addItem("振动");
        typeComboBox.addItem("应力");
        typeComboBox.addItem("温度");
        typeComboBox.addItem("湿度");
        typeComboBox.addItem("速度");
        typeComboBox.addItem("加速度");
        typeComboBox.addItem("声音");
        typeComboBox.addItem("其他");
    }
    private JTextField temperatureField =new JTextField();
    private JTextField powerField =new JTextField();
    private JTextField frequencyField =new JTextField();
    private JTextField measureIdField =new JTextField();
    private JTextField positionField =new JTextField();
    private JTextField descriptionField =new JTextField();


    private JButton searchButton =new JButton("检索传感器");
    private JButton confirmButton =new JButton("确认");
    private JButton exitButton =new JButton("退出");

    public UpdateSensor() {
        panel.setLayout(null);

        idCaption.setBounds(10,10,120,30);
        idCaption.setFont(new Font(DEFAULT_FONT,Font.PLAIN,22));
        idField.setBounds(120,10,130,30);
        idField.setFont(new Font(DEFAULT_FONT,Font.PLAIN,22));

        searchButton.setBounds(270,10,250,30);
        searchButton.setFont(new Font(DEFAULT_FONT,Font.PLAIN,22));
        searchButton.addActionListener(this);

        nameCaption.setBounds(10,50,120,30);
        nameCaption.setFont(new Font(DEFAULT_FONT,Font.PLAIN,22));
        nameField.setBounds(120,50,130,30);
        nameField.setFont(new Font(DEFAULT_FONT,Font.PLAIN,22));

        typeCaption.setBounds(280,50,120,30);
        typeCaption.setFont(new Font(DEFAULT_FONT,Font.PLAIN,22));
        typeComboBox.setBounds(420,50,130,30);
        typeComboBox.setFont(new Font(DEFAULT_FONT,Font.PLAIN,22));

        temperatureCaption.setBounds(10,100,120,30);
        temperatureCaption.setFont(new Font(DEFAULT_FONT,Font.PLAIN,22));
        temperatureField.setBounds(120,100,130,30);
        temperatureField.setFont(new Font(DEFAULT_FONT,Font.PLAIN,22));

        powerCaption.setBounds(280,100,120,30);
        powerCaption.setFont(new Font(DEFAULT_FONT,Font.PLAIN,22));
        powerField.setBounds(420,100,130,30);
        powerField.setFont(new Font(DEFAULT_FONT,Font.PLAIN,22));

        frequencyCaption.setBounds(10,150,120,30);
        frequencyCaption.setFont(new Font(DEFAULT_FONT,Font.PLAIN,22));
        frequencyField.setBounds(120,150,130,30);
        frequencyField.setFont(new Font(DEFAULT_FONT,Font.PLAIN,22));

        measureIdCaption.setBounds(280,150,120,30);
        measureIdCaption.setFont(new Font(DEFAULT_FONT,Font.PLAIN,22));
        measureIdField.setBounds(420,150,130,30);
        measureIdField.setFont(new Font(DEFAULT_FONT,Font.PLAIN,22));

        positionCaption.setBounds(10,200,120,30);
        positionCaption.setFont(new Font(DEFAULT_FONT,Font.PLAIN,22));
        positionField.setBounds(120,200,130,30);
        positionField.setFont(new Font(DEFAULT_FONT,Font.PLAIN,22));

        descriptionCaption.setBounds(10,250,100,30);
        descriptionCaption.setFont(new Font(DEFAULT_FONT,Font.PLAIN,22));
        descriptionField.setBounds(120,250,450,30);
        descriptionField.setFont(new Font(DEFAULT_FONT,Font.PLAIN,22));

        confirmButton.setBounds(320,300,100,40);
        confirmButton.setFont(new Font(DEFAULT_FONT,Font.PLAIN,22));
        confirmButton.addActionListener(this);

        exitButton.setBounds(450,300,100,40);
        exitButton.setFont(new Font(DEFAULT_FONT,Font.PLAIN,22));
        exitButton.addActionListener(this);

        panel.add(idCaption);
        panel.add(idField);
        panel.add(nameCaption);
        panel.add(nameField);
        panel.add(typeCaption);
        panel.add(typeComboBox);
        panel.add(temperatureCaption);
        panel.add(temperatureField);
        panel.add(powerCaption);
        panel.add(powerField);
        panel.add(frequencyCaption);
        panel.add(frequencyField);
        panel.add(measureIdCaption);
        panel.add(measureIdField);
        panel.add(positionCaption);
        panel.add(positionField);
        panel.add(descriptionCaption);
        panel.add(descriptionField);
        panel.add(searchButton);
        panel.add(confirmButton);
        panel.add(exitButton);

        this.add(panel);
        this.setTitle("修改传感器");
        this.setVisible(true);
        this.setResizable(DefaultConfig.WINDOW_RESIZABLE);
        this.setBounds(DefaultConfig.WINDOW_LEFT_MARGIN + LEFT_MARGIN_BIAS,DefaultConfig.WINDOW_LEFT_MARGIN + TOP_MARGIN_BIAS, POPUP_WINDOW_WIDTH,POPUP_WINDOW_HEIGHT);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()== confirmButton){
            String sensorId = idField.getText().trim();
            String name = nameField.getText().trim();
            String type = Objects.requireNonNull(typeComboBox.getSelectedItem()).toString();
            String temperature = temperatureField.getText().trim();
            String power = powerField.getText().trim();
            String frequency = frequencyField.getText().trim();
            String measureId = measureIdField.getText().trim();
            String position = positionField.getText().trim();
            String description = descriptionField.getText().trim();
            if (sensorId.length() == 0 || !checked){
                JOptionPane.showMessageDialog(this,"请输入传感器ID并确认传感器信息","Warning",JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            if (name.length() == 0){
                JOptionPane.showMessageDialog(this,"请输入必填项：传感器名称","Warning",JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            Database db = new Database();
            String sql = String.format("UPDATE t_sensor SET name='%s', type='%s', temperature='%s', power='%s', frequency='%s',measure_id='%s', position='%s',description='%s' WHERE sensor_id='%s';", name, type, temperature, power, frequency, measureId, position, description, sensorId);
            try {
                statement=db.getCon().prepareStatement(sql);
                boolean failed=statement.execute();
                if(!failed){
                    JOptionPane.showMessageDialog(this,"修改成功!","消息",JOptionPane.INFORMATION_MESSAGE);
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
            String sensorId = idField.getText().trim();
            if (sensorId.length() == 0){
                JOptionPane.showMessageDialog(this,"请输入传感器ID","Warning",JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            Database db = new Database();
            String sql = String.format("SELECT * FROM t_sensor WHERE sensor_id='%s';", sensorId);
            try {
                statement=db.getCon().prepareStatement(sql);
                ResultSet rs= statement.executeQuery();
                if (rs.next()){
                    this.nameField.setText(rs.getString("name"));
                    this.typeComboBox.setSelectedItem(rs.getString("type"));
                    this.temperatureField.setText(rs.getString("temperature"));
                    this.powerField.setText(rs.getString("power"));
                    this.frequencyField.setText(rs.getString("frequency"));
                    this.measureIdField.setText(rs.getString("measure_id"));
                    this.positionField.setText(rs.getString("position"));
                    this.descriptionField.setText(rs.getString("description"));
                    checked = true;
                } else {
                    JOptionPane.showMessageDialog(this,"未查询到相关传感器","Warning",JOptionPane.INFORMATION_MESSAGE);
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
        new UpdateSensor();
    }


};
