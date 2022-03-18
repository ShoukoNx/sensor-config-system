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
 * 创建传感器弹窗
 *
 * @author ysr
 */
public class CreateSensor extends JFrame implements ActionListener {

    private final static int POPUP_WINDOW_WIDTH = 600;
    private final static int POPUP_WINDOW_HEIGHT = 420;
    private final static int TOP_MARGIN_BIAS = 300;
    private final static int LEFT_MARGIN_BIAS = 500;
    private final static String DEFAULT_FONT = "华文楷体";

    private PreparedStatement statement;
    private Connection con;

    private JPanel panel = new JPanel();

    private JLabel text = new JLabel("<html><p>注意：传感器ID、名称和类型是必填项</p><p>若输入测量量ID，则确保该测量量存在！</p></html>");
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

    private JButton confirmButton =new JButton("确认");
    private JButton exitButton =new JButton("退出");

    public CreateSensor() {
        panel.setLayout(null);

        text.setBounds(10,10,450,60);
        text.setFont(new Font(DEFAULT_FONT,Font.BOLD,20));
        text.setForeground(Color.red);

        idCaption.setBounds(10,80,120,30);
        idCaption.setFont(new Font(DEFAULT_FONT,Font.PLAIN,22));
        idField.setBounds(120,80,130,30);
        idField.setFont(new Font(DEFAULT_FONT,Font.PLAIN,22));

        nameCaption.setBounds(280,80,120,30);
        nameCaption.setFont(new Font(DEFAULT_FONT,Font.PLAIN,22));
        nameField.setBounds(420,80,130,30);
        nameField.setFont(new Font(DEFAULT_FONT,Font.PLAIN,22));

        typeCaption.setBounds(10,130,120,30);
        typeCaption.setFont(new Font(DEFAULT_FONT,Font.PLAIN,22));
        typeComboBox.setBounds(120,130,130,30);
        typeComboBox.setFont(new Font(DEFAULT_FONT,Font.PLAIN,22));

        temperatureCaption.setBounds(280,130,120,30);
        temperatureCaption.setFont(new Font(DEFAULT_FONT,Font.PLAIN,22));
        temperatureField.setBounds(420,130,130,30);
        temperatureField.setFont(new Font(DEFAULT_FONT,Font.PLAIN,22));

        powerCaption.setBounds(10,180,120,30);
        powerCaption.setFont(new Font(DEFAULT_FONT,Font.PLAIN,22));
        powerField.setBounds(120,180,130,30);
        powerField.setFont(new Font(DEFAULT_FONT,Font.PLAIN,22));

        frequencyCaption.setBounds(280,180,120,30);
        frequencyCaption.setFont(new Font(DEFAULT_FONT,Font.PLAIN,22));
        frequencyField.setBounds(420,180,130,30);
        frequencyField.setFont(new Font(DEFAULT_FONT,Font.PLAIN,22));

        measureIdCaption.setBounds(10,230,120,30);
        measureIdCaption.setFont(new Font(DEFAULT_FONT,Font.PLAIN,22));
        measureIdField.setBounds(120,230,130,30);
        measureIdField.setFont(new Font(DEFAULT_FONT,Font.PLAIN,22));

        positionCaption.setBounds(280,230,120,30);
        positionCaption.setFont(new Font(DEFAULT_FONT,Font.PLAIN,22));
        positionField.setBounds(420,230,130,30);
        positionField.setFont(new Font(DEFAULT_FONT,Font.PLAIN,22));


        descriptionCaption.setBounds(10,280,100,30);
        descriptionCaption.setFont(new Font(DEFAULT_FONT,Font.PLAIN,22));
        descriptionField.setBounds(120,280,450,30);
        descriptionField.setFont(new Font(DEFAULT_FONT,Font.PLAIN,22));

        confirmButton.setBounds(320,330,100,40);
        confirmButton.setFont(new Font(DEFAULT_FONT,Font.PLAIN,22));
        confirmButton.addActionListener(this);

        exitButton.setBounds(450,330,100,40);
        exitButton.setFont(new Font(DEFAULT_FONT,Font.PLAIN,22));
        exitButton.addActionListener(this);

        panel.add(text);
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
        panel.add(confirmButton);
        panel.add(exitButton);

        this.add(panel);
        this.setTitle("新建传感器");
        this.setVisible(true);
        this.setResizable(DefaultConfig.WINDOW_RESIZABLE);
        this.setBounds(DefaultConfig.WINDOW_LEFT_MARGIN + LEFT_MARGIN_BIAS,DefaultConfig.WINDOW_LEFT_MARGIN + TOP_MARGIN_BIAS, POPUP_WINDOW_WIDTH,POPUP_WINDOW_HEIGHT);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()== confirmButton){
            Database db;
            String sensorId = idField.getText().trim();
            String name = nameField.getText().trim();
            String type = Objects.requireNonNull(typeComboBox.getSelectedItem()).toString();
            String temperature = temperatureField.getText().trim();
            String power = powerField.getText().trim();
            String frequency = frequencyField.getText().trim();
            String measureId = measureIdField.getText().trim();
            String position = positionField.getText().trim();
            String description = descriptionField.getText().trim();
            if (sensorId.length() == 0){
                JOptionPane.showMessageDialog(this,"请输入必填项：传感器ID","Warning",JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            if (name.length() == 0){
                JOptionPane.showMessageDialog(this,"请输入必填项：传感器名称","Warning",JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            db=new Database();
            String sqlSearch = String.format("SELECT name FROM t_measure WHERE measure_id='%s';", measureId);
            String sqlInsert = String.format("INSERT INTO t_sensor (sensor_id, name, type, temperature, power, frequency, measure_id, position, description) VALUES ('%s','%s','%s','%s','%s','%s','%s','%s','%s');",sensorId, name, type, temperature, power, frequency, measureId, position, description);
            try {
                if (measureId.length() != 0 ){
                    statement = db.getCon().prepareStatement(sqlSearch);
                    ResultSet rs= statement.executeQuery();
                    if (!rs.next()){
                        JOptionPane.showMessageDialog(this,"测量量ID不存在!","Warning",JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                }
                statement = db.getCon().prepareStatement(sqlInsert);
                boolean failed = statement.execute();
                if(!failed){
                    JOptionPane.showMessageDialog(this,"添加成功!","消息",JOptionPane.INFORMATION_MESSAGE);
                    this.dispose();
                }
                else{
                    JOptionPane.showMessageDialog(this,"数据库插入异常!","Warning",JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (java.sql.SQLIntegrityConstraintViolationException e1){
                JOptionPane.showMessageDialog(this,"该传感器ID已存在!","Warning",JOptionPane.INFORMATION_MESSAGE);
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
        new CreateSensor();
    }


};
