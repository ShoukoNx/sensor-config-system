package sensor.component;


import sensor.DefaultConfig;
import sensor.Main;
import sensor.component.accountSetting.ChangePassword;
import sensor.component.accountSetting.DeleteAccount;
import sensor.component.accountSetting.Register;
import sensor.component.importanceAnalysis.ImportanceAnalysisPanel;
import sensor.component.measureAnalysis.MeasureAnalysisPanel;
import sensor.component.sensorConfig.SensorConfigPanel;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.net.URL;

/**
 * 主界面
 *
 * @author ysr
 */
public class MainFrame extends JFrame {

    // 登陆账户信息
    private String loginUser;

    private Component temp;

    // 创建树节点
    DefaultMutableTreeNode[] nodes ={
            new DefaultMutableTreeNode("动车组转向架传感器优化配置系统"),
            new DefaultMutableTreeNode("转向架零部件重要度分析"),
            new DefaultMutableTreeNode("测量量分析"),
            new DefaultMutableTreeNode("传感器配置"),
            new DefaultMutableTreeNode("账户管理"),
            new DefaultMutableTreeNode("退出"),
    };

    // 创建左右分割线
    private JSplitPane spliter =new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,true);

    // 创建根节点
    DefaultTreeModel rootNode =new DefaultTreeModel(nodes[0]);
    JTree tree =new JTree(rootNode);

    // 右侧panel
    private JPanel rightPanel =new JPanel();

    // 账户管理节点里的东西
    DefaultMutableTreeNode[] accountManageNode ={
            new DefaultMutableTreeNode("账号注册",true),
            new DefaultMutableTreeNode("账号删除",true),
            new DefaultMutableTreeNode("修改密码",true)
    };


    CardLayout cl =new CardLayout();

    public MainFrame(String loginUser) {
        this.loginUser = loginUser;
        temp = this;
        tree.setFont(new Font("微软雅黑",Font.PLAIN,18));
        tree.setRowHeight(30);
        DefaultTreeCellRenderer renderer = (DefaultTreeCellRenderer) tree.getCellRenderer();
        URL url= Main.class.getResource("static/tb.png");
        ImageIcon closeimage= new ImageIcon(url);
        URL url1= Main.class.getResource("static/open.png");
        ImageIcon openimage= new ImageIcon(url1);
        renderer.setClosedIcon(closeimage);
        renderer.setOpenIcon(openimage);
        renderer.setLeafIcon(closeimage);
        for(int i=1;i<6;i++){
            rootNode.insertNodeInto(nodes[i], nodes[0], i-1 );
        }
        for(int i=0;i<3;i++){
            nodes[4].insert(accountManageNode[i], i );
        }

        tree.setEditable(false);

        //设置分割窗体，位置与隔条，左边添加树，右边添加Panel
        spliter.setDividerLocation(350);
        spliter.setDividerSize(5);
        spliter.setRightComponent(rightPanel);
        spliter.setLeftComponent(tree);
        this.add(spliter);
        rightPanel.setLayout(new CardLayout());
        this.addTreeListener();
        this.initPanel();
        String role = this.loginUser.equals("admin") ? "管理员" : "普通用户";
        String title = String.format("动车组转向架传感器优化配置系统    当前登录用户: %s    用户角色: %s", this.loginUser, role);
        this.setTitle(title);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setVisible(true);
        this.setResizable(DefaultConfig.WINDOW_RESIZABLE);
        this.setBounds(10,10, DefaultConfig.WINDOW_WIDTH,DefaultConfig.WINDOW_HEIGHT);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    public void initPanel(){
        rightPanel.setLayout(cl);
        rightPanel.add(new Welcome(),"welcome");
        rightPanel.add(new ImportanceAnalysisPanel(),"importance_analysis");
        rightPanel.add(new MeasureAnalysisPanel(),"measure_analysis");
        rightPanel.add(new SensorConfigPanel(),"sensor_config");
        rightPanel.add(new DeleteAccount(),"delete_account");
        rightPanel.add(new Register(),"register");
        rightPanel.add(new ChangePassword(this.loginUser),"change_password");

    }

    public void addTreeListener(){
        tree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                DefaultMutableTreeNode treenode = (DefaultMutableTreeNode) e.getPath().getLastPathComponent();
                if("退出".equals(treenode.getUserObject())){
                    int i=JOptionPane.showConfirmDialog(MainFrame.this,"是否退出系统？",
                            "消息",JOptionPane.YES_NO_OPTION);
                    if (i==JOptionPane.YES_OPTION){
                        System.exit(0);
                    }
                }
                if("动车组转向架传感器优化配置系统".equals(treenode.getUserObject())){
                    cl.show(rightPanel,"welcome");
                }
                if("转向架零部件重要度分析".equals(treenode.getUserObject())){
                    cl.show(rightPanel,"importance_analysis");
                }
                if("测量量分析".equals(treenode.getUserObject())){
                    cl.show(rightPanel,"measure_analysis");
                }
                if("传感器配置".equals(treenode.getUserObject())){
                    cl.show(rightPanel,"sensor_config");
                }
                if("账号注册".equals(treenode.getUserObject())){
                    if(!loginUser.equals("admin")){
                        JOptionPane.showMessageDialog(temp,"您无权限注册新账号","Warning",JOptionPane.INFORMATION_MESSAGE);
                    }else{
                        cl.show(rightPanel,"register");
                    }
                }
                if("账号删除".equals(treenode.getUserObject())){
                    if(!loginUser.equals("admin")){
                        JOptionPane.showMessageDialog(temp,"您无权限删除账号","Warning",JOptionPane.INFORMATION_MESSAGE);
                    }else{
                        cl.show(rightPanel,"delete_account");
                    }
                }
                if("修改密码".equals(treenode.getUserObject())){
                    cl.show(rightPanel,"change_password");
                }
            }
        });
    }


    public static void main(String[] args) {
        //
        new MainFrame("admin");
    }
};
