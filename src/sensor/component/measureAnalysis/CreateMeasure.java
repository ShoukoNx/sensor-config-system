package sensor.component.measureAnalysis;


import sensor.DefaultConfig;
import sensor.component.Database;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Objects;

/**
 * 创建测量量弹窗
 *
 * @author ysr
 */
public class CreateMeasure extends JFrame implements ActionListener {

    private final static int POPUP_WINDOW_WIDTH = 600;
    private final static int POPUP_WINDOW_HEIGHT = 350;
    private final static int TOP_MARGIN_BIAS = 300;
    private final static int LEFT_MARGIN_BIAS = 500;
    private final static String DEFAULT_FONT = "华文楷体";

    private PreparedStatement statement;
    private Connection con;

    private JPanel panel = new JPanel();

    private JLabel text = new JLabel("注意：测量量ID、名称和类型是必填项！");
    private JLabel idCaption = new JLabel("测量量ID:");
    private JLabel nameCaption = new JLabel("测量量名称:");
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

    private JButton confirmButton =new JButton("确认");
    private JButton exitButton =new JButton("退出");

    public CreateMeasure() {
        panel.setLayout(null);

        text.setBounds(10,10,450,30);
        text.setFont(new Font(DEFAULT_FONT,Font.BOLD,20));
        text.setForeground(Color.red);

        idCaption.setBounds(10,50,120,30);
        idCaption.setFont(new Font(DEFAULT_FONT,Font.PLAIN,22));
        idField.setBounds(120,50,130,30);
        idField.setFont(new Font(DEFAULT_FONT,Font.PLAIN,22));

        nameCaption.setBounds(280,50,120,30);
        nameCaption.setFont(new Font(DEFAULT_FONT,Font.PLAIN,22));
        nameField.setBounds(420,50,130,30);
        nameField.setFont(new Font(DEFAULT_FONT,Font.PLAIN,22));

        typeCaption.setBounds(10,100,120,30);
        typeCaption.setFont(new Font(DEFAULT_FONT,Font.PLAIN,22));
        typeComboBox.setBounds(120,100,130,30);
        typeComboBox.setFont(new Font(DEFAULT_FONT,Font.PLAIN,22));

        valueCaption.setBounds(280,100,120,30);
        valueCaption.setFont(new Font(DEFAULT_FONT,Font.PLAIN,22));
        valueField.setBounds(420,100,130,30);
        valueField.setFont(new Font(DEFAULT_FONT,Font.PLAIN,22));

        rangeCaption.setBounds(10,150,120,30);
        rangeCaption.setFont(new Font(DEFAULT_FONT,Font.PLAIN,22));
        rangeField.setBounds(120,150,130,30);
        rangeField.setFont(new Font(DEFAULT_FONT,Font.PLAIN,22));

        frequencyCaption.setBounds(280,150,120,30);
        frequencyCaption.setFont(new Font(DEFAULT_FONT,Font.PLAIN,22));
        frequencyField.setBounds(420,150,130,30);
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

        panel.add(text);
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
        panel.add(confirmButton);
        panel.add(exitButton);

        this.add(panel);
        this.setTitle("新建测量量");
        this.setVisible(true);
        this.setResizable(DefaultConfig.WINDOW_RESIZABLE);
        this.setBounds(DefaultConfig.WINDOW_LEFT_MARGIN + LEFT_MARGIN_BIAS,DefaultConfig.WINDOW_LEFT_MARGIN + TOP_MARGIN_BIAS, POPUP_WINDOW_WIDTH,POPUP_WINDOW_HEIGHT);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()== confirmButton){
            Database db;
            String measureId = idField.getText().trim();
            String name = nameField.getText().trim();
            String type = Objects.requireNonNull(typeComboBox.getSelectedItem()).toString();
            String value = valueField.getText().trim();
            String range = rangeField.getText().trim();
            String frequency = frequencyField.getText().trim();
            String description = descriptionField.getText().trim();
            if (measureId.length() == 0){
                JOptionPane.showMessageDialog(this,"请输入必填项：测量量ID","Warning",JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            if (name.length() == 0){
                JOptionPane.showMessageDialog(this,"请输入必填项：测量量名称","Warning",JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            db=new Database();
            String sql = String.format("INSERT INTO t_measure (measure_id, name, type, current_value, measure_range, frequency, description) VALUES ('%s','%s','%s','%s','%s','%s','%s');",measureId, name, type, value, range, frequency, description);
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
            } catch (java.sql.SQLIntegrityConstraintViolationException e1){
                JOptionPane.showMessageDialog(this,"该测量量ID已存在!","Warning",JOptionPane.INFORMATION_MESSAGE);
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
        new CreateMeasure();
    }


};
