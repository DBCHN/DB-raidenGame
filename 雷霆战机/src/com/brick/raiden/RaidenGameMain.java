package com.brick.raiden;

import java.awt.Cursor;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;

/**
 * JFrame代表了一个窗体界面
 * @author Haste
 *
 */
public class RaidenGameMain extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//构造方法
	public RaidenGameMain() {
		//设置界面大小
		this.setSize(550,830);
		
		//设置界面位置
		this.setLocation(500,0);
		
		//设置标题
		this.setTitle("雷霆战机2020-浩哥出品");
		
		//设置窗体关闭并退出
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//不允许调整窗口大小
		this.setResizable(false);
		
		//设置窗体上鼠标的样式
		this.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
		
		//设置内容面板，将我们开发的面板类RaidenGamePanel，设置到我们的游戏主界面中
		this.setContentPane(new RaidenGamePanel());
		
		//设置游戏界面为无边框模式
		//this.setUndecorated(true);
		
		//给JFrame添加鼠标移动的事件处理监听器
		//自动补全的快捷键是：alt+/
		/*this.addMouseMotionListener(new MouseMotionListener() {
			
			//鼠标移动处理的方法
			@Override
			public void mouseMoved(MouseEvent e) {
				//当鼠标移动的时候，我们让英雄战机自己来处理相关操作
				RaidenGamePanel.myHero.mouseMoved(e);
			}
			
			//鼠标拖拽的处理方法
			@Override
			public void mouseDragged(MouseEvent e) {
				RaidenGamePanel.myHero.mouseMoved(e);
			}
		});*/
		
		//添加键盘处理事件监听器,使用匿名类的方式，使用适配器模式
		this.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_1) {
					Hero.index = 0;
				}
				if(e.getKeyCode() == KeyEvent.VK_2) {
					Hero.index = 1;
				}
				if(e.getKeyCode() == KeyEvent.VK_3) {
					Hero.index = 2;
				}
				
				//按a键，超级火力
				if(e.getKeyCode() == KeyEvent.VK_A) {
					//System.out.println("super fire");
					RaidenGamePanel.myHero.superFire();
				}
				
				//无敌模式
				if(e.getKeyCode() == KeyEvent.VK_F) {
					RaidenGamePanel.myHero.live = true;
					RaidenGamePanel.myHero.life = 100;
				}
			}
			
		});
		
		//点击鼠标监听器
		/*this.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				//RaidenGamePanel.myHero.fire();
				
				Hero.fireFlag = true;
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				Hero.fireFlag = false;
			}
		});*/
		
		//设置窗体的可见行为：true，因为默认为false，不显示
		this.setVisible(true);
		
	}
	
	public static void main(String[] args) {
		new RaidenGameMain();
	}

}
