package sensor.component.measureAnalysis;


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
 * 更新测量量弹窗
 *
 * @author ysr
 */
public class UpdateMeasure extends JFrame implements ActionListener {

    private boolean checked;

    private final static int POPUP_WINDOW_WIDTH = 600;
    private final static int POPUP_WINDOW_HEIGHT = 350;
    private final static int TOP_MARGIN_BIAS = 300;
    private final static int LEFT_MARGIN_BIAS = 500;
    private final static String DEFAULT_FONT = "华文楷体";

    private PreparedStatement statement;
    private Connection con;

    private JPanel panel = new JPanel();

    private JLabel idCaption = new JLabel("ID:");
    private JLabel nameCaption = new JLabel("名称:");
    private JLabel typeCaption = new JLabel("类型:");
    private JLabel valueCaption = new JLabel("当前值:");
    private JLabel rangeCaption = new JLabel("标准范围:");
    private JLabel frequencyCaption = new JLabel("测量频率:");
    private JLabel descriptionCaption = new JLabel("描述:");

    private JTextField idField = new JTextField();
    private JTextField nameField = new JTextField();
    private JComboBox typeComboBox=new JComboBox();
    {
        typeComboBox.addItem("位移");
        typeComboBox.addItem("温度");
        typeComboBox.addItem("电压");
        typeComboBox.addItem("应力");
        typeComboBox.addItem("速度");
        typeComboBox.addItem("其他");
    }
    private JTextField valueField =new JTextField();
    private JTextField rangeField =new JTextField();
    private JTextField frequencyField =new JTextField();
    private JTextField descriptionField =new JTextField();

    private JButton searchButton =new JButton("检索测量量");
    private JButton confirmButton =new JButton("确认");
    private JButton exitButton =new JButton("退出");

    public UpdateMeasure() {
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

        valueCaption.setBounds(10,100,120,30);
        valueCaption.setFont(new Font(DEFAULT_FONT,Font.PLAIN,22));
        valueField.setBounds(120,100,130,30);
        valueField.setFont(new Font(DEFAULT_FONT,Font.PLAIN,22));

        rangeCaption.setBounds(280,100,120,30);
        rangeCaption.setFont(new Font(DEFAULT_FONT,Font.PLAIN,22));
        rangeField.setBounds(420,100,130,30);
        rangeField.setFont(new Font(DEFAULT_FONT,Font.PLAIN,22));

        frequencyCaption.setBounds(10,150,120,30);
        frequencyCaption.setFont(new Font(DEFAULT_FONT,Font.PLAIN,22));
        frequencyField.setBounds(120,150,130,30);
        frequencyField.setFont(new Font(DEFAULT_FONT,Font.PLAIN,22));

        descriptionCaption.setBounds(10,200,100,30);
        descriptionCaption.setFont(new Font(DEFAULT_FONT,Font.PLAIN,22));
        descriptionField.setBounds(120,200,450,30);
        descriptionField.setFont(new Font(DEFAULT_FONT,Font.PLAIN,22));

        confirmButton.setBounds(320,250,100,40);
        confirmButton.setFont(new Font(DEFAULT_FONT,Font.PLAIN,22));
        confirmButton.addActionListener(this);

        exitButton.setBounds(450,250,100,40);
        exitButton.setFont(new Font(DEFAULT_FONT,Font.PLAIN,22));
        exitButton.addActionListener(this);

        panel.add(idCaption);
        panel.add(idField);
        panel.add(nameCaption);
        panel.add(nameField);
        panel.add(typeCaption);
        panel.add(typeComboBox);
        panel.add(valueCaption);
        panel.add(valueField);
        panel.add(rangeCaption);
        panel.add(rangeField);
        panel.add(frequencyCaption);
        panel.add(frequencyField);
        panel.add(descriptionCaption);
        panel.add(descriptionField);
        panel.add(searchButton);
        panel.add(confirmButton);
        panel.add(exitButton);

        this.add(panel);
        this.setTitle("修改测量量");
        this.setVisible(true);
        this.setResizable(DefaultConfig.WINDOW_RESIZABLE);
        this.setBounds(DefaultConfig.WINDOW_LEFT_MARGIN + LEFT_MARGIN_BIAS,DefaultConfig.WINDOW_LEFT_MARGIN + TOP_MARGIN_BIAS, POPUP_WINDOW_WIDTH,POPUP_WINDOW_HEIGHT);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()== confirmButton){
            String measureId = idField.getText().trim();
            String name = nameField.getText().trim();
            String type = Objects.requireNonNull(typeComboBox.getSelectedItem()).toString();
            String value = valueField.getText().trim();
            String range = rangeField.getText().trim();
            String frequency = frequencyField.getText().trim();
            String description = descriptionField.getText().trim();
            if (measureId.length() == 0 || !checked){
                JOptionPane.showMessageDialog(this,"请输入测量量ID并确认测量量信息","Warning",JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            if (name.length() == 0){
                JOptionPane.showMessageDialog(this,"请输入必填项：测量量名称","Warning",JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            Database db = new Database();
            String sql = String.format("UPDATE t_measure SET name='%s', type='%s', current_value='%s', measure_range='%s', frequency='%s', description='%s' WHERE measure_id='%s';", name, type, value, range, frequency, description,measureId);
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
            String measureId = idField.getText().trim();
            if (measureId.length() == 0){
                JOptionPane.showMessageDialog(this,"请输入测量量ID","Warning",JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            Database db = new Database();
            String sql = String.format("SELECT * FROM t_measure WHERE measure_id='%s';", measureId);
            try {
                statement=db.getCon().prepareStatement(sql);
                ResultSet rs= statement.executeQuery();
                if (rs.next()){
                    this.nameField.setText(rs.getString("name"));
                    this.typeComboBox.setSelectedItem(rs.getString("type"));
                    this.valueField.setText(rs.getString("current_value"));
                    this.rangeField.setText(rs.getString("measure_range"));
                    this.frequencyField.setText(rs.getString("frequency"));
                    this.descriptionField.setText(rs.getString("description"));
                    checked = true;
                } else {
                    JOptionPane.showMessageDialog(this,"未查询到相关测量量","Warning",JOptionPane.INFORMATION_MESSAGE);
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
        new UpdateMeasure();
    }


};
