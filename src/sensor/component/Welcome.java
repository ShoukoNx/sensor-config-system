package sensor.component;

import sensor.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 欢迎界面
 *
 * @author ysr
 */
public class Welcome extends JPanel implements ActionListener {

    private static final String BOGIE_URL = "static/bogie2.jpg";

    private static final String INTRODUCTION = "<html><p>&emsp;</p><p>转向架及其部件信息简介：</p>" +
            "<p>&emsp;&emsp;转向架转向架一般可分为动车用转向架和拖车用转向架，</p>" +
            "<p>两种类型转向架的主要结构型式基本上是一致的。其功能主</p>" +
            "<p>要有以下几点：①承载车体重量；②确保正确导向；③实现缓</p>" +
            "<p>冲减振；④传递牵引力及制动力。</p>" +
            "<p>&emsp;&emsp;动车组转向架需要实现诸多重要的功能，其结构复杂，涉</p>" +
            "<p>及到的系统及零部件众多。转向架的基本结构一般包括：轮对</p>" +
            "<p>轴箱装置、转向架构架装置、驱动装置、基础制动装置、悬挂</p>" +
            "<p>装置等。</p></html>";


    public Welcome(){

        ImageIcon bogieImage = new ImageIcon(Main.class.getResource(BOGIE_URL));
        JLabel bogiePictureLabel=new JLabel();
        bogiePictureLabel.setIcon(bogieImage);
        bogiePictureLabel.setBounds(400,50,2000,1000);

        // JLabel baseLabel=new JLabel();
        JLabel introductionLabel = new JLabel(INTRODUCTION,JLabel.CENTER);
        // baseLabel.setLayout(null);
        introductionLabel.setLayout(null);
        // baseLabel.setBounds(0,0,1440,880);
        introductionLabel.setBounds(300,500,800,100);
        introductionLabel.setFont(new Font("华文楷体",Font.PLAIN,25));
        introductionLabel.setForeground(Color.BLACK);
        introductionLabel.setBackground(Color.BLACK);


        this.add(bogiePictureLabel);
        this.add(introductionLabel);
        this.setVisible(true);

    }


    @Override
    public void actionPerformed(ActionEvent e) {

    }

}