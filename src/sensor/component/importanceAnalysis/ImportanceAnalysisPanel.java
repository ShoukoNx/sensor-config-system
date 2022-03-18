package sensor.component.importanceAnalysis;
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
 * 零部件重要度分析界面
 *
 * @author ysr
 */
public class ImportanceAnalysisPanel extends JPanel implements ActionListener {

    // 数据库准备部分
    private PreparedStatement statement;
    private Connection con;
    Database db=new Database();
    String Sql;

    // 按钮定义
    private static final int BUTTON_FONT_SIZE = 25;
    private static final String BUTTON_FONT = "华文楷体";
    private JButton searchButton =new JButton("转向架及其重要零部件");
    private JButton createButton =new JButton("新建零部件");
    private JButton updateButton =new JButton("修改零部件");
    private JButton deleteButton =new JButton("删除零部件");

    // 显示表格定义
    private Vector<String> head = new Vector<>();
    {
        head.add("序号");
        head.add("零件ID");
        head.add("零件名称");
        head.add("个数");
        head.add("描述");
    }
    private Vector<Vector> data=new Vector<>();
    private DefaultTableModel dtm=new DefaultTableModel(data, head);
    private JTable table =new JTable(dtm);
    // 这一步设置出滚轮
    private JScrollPane scrollPane =new JScrollPane(table);

    // 创建上下分割线
    private JSplitPane spliter =new JSplitPane(JSplitPane.VERTICAL_SPLIT,true);


    public ImportanceAnalysisPanel(){

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
        bottomPanel.setDividerLocation(100);
        bottomPanel.setDividerSize(5);
        searchButton.setBounds(100,25,350,30);
        searchButton.setFont(new Font(BUTTON_FONT,Font.PLAIN,BUTTON_FONT_SIZE));
        searchButton.addActionListener(this);

        createButton.setBounds(470,25,180,30);
        createButton.setFont(new Font(BUTTON_FONT,Font.PLAIN,BUTTON_FONT_SIZE));
        createButton.addActionListener(this);

        updateButton.setBounds(670,25,180,30);
        updateButton.setFont(new Font(BUTTON_FONT,Font.PLAIN,BUTTON_FONT_SIZE));
        updateButton.addActionListener(this);

        deleteButton.setBounds(870,25,180,30);
        deleteButton.setFont(new Font(BUTTON_FONT,Font.PLAIN,BUTTON_FONT_SIZE));
        deleteButton.addActionListener(this);

        buttonAreaPanel.setLayout(null);
        buttonAreaPanel.add(searchButton);
        buttonAreaPanel.add(createButton);
        buttonAreaPanel.add(updateButton);
        buttonAreaPanel.add(deleteButton);
        buttonAreaPanel.setBounds(1000,0,3000,100);
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
        if (e.getSource()== searchButton){
            ((DefaultTableModel) table.getModel()).getDataVector().clear();
            Sql = "select * from t_component";
            db = new Database();
            try{
                statement = db.getCon().prepareStatement(Sql);
                ResultSet rs= statement.executeQuery();
                // JOptionPane.showMessageDialog(this,"查询成功","消息",JOptionPane.INFORMATION_MESSAGE);
                while (rs.next()){
                    Vector<String> v=new Vector<String>();
                    v.add(rs.getString("id"));
                    v.add(rs.getString("component_id"));
                    v.add(rs.getString("name"));
                    v.add(rs.getString("count"));
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
        else if (e.getSource()== createButton){
            new CreateComponent();
        }
        else if (e.getSource()== updateButton){
            new UpdateComponent();
        }
        else if (e.getSource()== deleteButton){
            new DeleteComponent();
        }
    }
}
