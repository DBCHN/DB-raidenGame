package com.brick.raiden;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;

/**
 * 加血包的类
 * @author Haste
 *
 */
public class BloodBank {
	// 坐标的属性
	int x;
	
	int y;
	
	int width = 70;
	
	int height = 70;
	
	// 是死是活的变量
	boolean live = true;
	
	// 加载图片
	static Image img ;
	
	static Toolkit tk = Toolkit.getDefaultToolkit();
	
	static{
		img = tk.createImage(BloodBank.class.getClassLoader().getResource("blood1.png"));
	}
	
	// 构造方法，初始化成员变量
	public BloodBank(int x,int y){
		this.x = x;
		this.y = y;
	}
	
	public void paint(Graphics g) {
		g.drawImage(img, x, y, width, height, null);
		
		move();
	}
	
	// 移动的方法
	public void move(){
		y+=9;
		if(y>=830){
			live = false;
		}
	}
	
	//
	public Rectangle getRect(){
		return new Rectangle(x,y,width,height);
	}
	
	
}

