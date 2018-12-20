package cn.itcast;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;

import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.MultimediaInfo;

import javax.swing.*;

public class ReadAVI {

	public static void main(String[] args) throws Exception {

		// 创建 JFrame 实例
		JFrame frame = new JFrame("ReadAVI_v1.0");
		frame.setLocationRelativeTo(null);
		// Setting the width and height of frame
		frame.setSize(800, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        /* 创建面板，这个类似于 HTML 的 div 标签
         * 我们可以创建多个面板并在 JFrame 中指定位置
         * 面板中我们可以添加文本字段，按钮及其他组件。
         */
		JPanel panel = new JPanel();
		// 添加面板
		frame.add(panel);
        /*
         * 调用用户定义的方法并添加组件到面板
         */
		placeComponents(panel);

		// 设置界面可见
		frame.setVisible(true);

	}

	private static void placeComponents(JPanel panel) {

        /*
         * 这边设置布局为 null
         */
		panel.setLayout(null);

		// 创建 JLabel
		JLabel aviDir = new JLabel();
		aviDir.setFont(new Font("Dialog",1,20));
		aviDir.setText("请输入视频位置:");
        /* 这个方法定义了组件的位置。
         * setBounds(x, y, width, height)
         * x 和 y 指定左上角的新位置，由 width 和 height 指定新的大小。
         */
		aviDir.setBounds(10,20,200,25);
		panel.add(aviDir);

        /*
         * 创建文本域用于用户输入
         */
		final JTextField aviDirText = new JTextField(20);
		aviDirText.setBounds(180,20,365,35);
        aviDirText.setFont(new Font("Dialog",1,18));
		panel.add(aviDirText);

		// 保存视频信息的位置信息
		JLabel saveAviInfo = new JLabel();
		saveAviInfo.setFont(new Font("Dialog",1,20));
		saveAviInfo.setText("请输入保存位置:");

		saveAviInfo.setBounds(10,70,200,25);
		panel.add(saveAviInfo);


		final JTextField saveAviInfoText = new JTextField(20);
		saveAviInfoText.setFont(new Font("Dialog",1,18));
		saveAviInfoText.setBounds(180,70,365,35);
		panel.add(saveAviInfoText);

		// 创建登录按钮
		JButton startButton = new JButton("Start");
		startButton.setBounds(10, 120, 100, 30);
		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String aviDir = aviDirText.getText();
                String saveDir = saveAviInfoText.getText();

                try {
                    readAvi(aviDir,saveDir);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
		});
		panel.add(startButton);



	}

	private static void readAvi(String aviDir,String saveDir) throws Exception {
		File file = new File(aviDir);
		
		//写文件
		File infodir = new File(saveDir);
		if(!infodir.exists()){
			infodir.mkdir();
		}
		FileWriter writer =  new FileWriter(new File(infodir.getAbsolutePath(),"视频信息.txt"));
		if(file.exists() && file.isDirectory()){
			File[] files = file.listFiles();
			for (File childFile : files) {
					if(childFile.getName().endsWith(".avi")){
						Encoder encoder = new Encoder();
						MultimediaInfo info = encoder.getInfo(childFile);
						long ls = info.getDuration();
						long min = ls / 60000;
						long mm = ls/1000;
						String content = childFile.getName()+"		时长为:" + min + "分" + (mm-min*60) + "秒"+"\n";
						System.out.println(content);
						writer.write(content);
				}
				
			}
		}
		writer.close();
	}
	
}
