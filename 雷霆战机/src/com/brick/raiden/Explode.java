package com.brick.raiden;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

/**
 * 爆炸类
 * @author Haste
 *
 */
public class Explode {

	// 定义属性，坐标，宽度、高度，成员变量也称为实例变量，还可以称为属性
	int x;
	
	int y;
	
	int w = 100;
	
	int h = 100;
	
	//静态变量，也称为类变量
	static Image[] img = new Image[12];
	
	static Toolkit tk = Toolkit.getDefaultToolkit();
	
	//爆炸的声音
	static AudioClip ac; 
	
	int index ;
	
	//添加爆炸生死变量
	boolean live = true;
	
	// 每张爆炸的图片我们画n次
	int n = 2;
	
	static{
		img[0] = tk.createImage(Explode.class.getClassLoader().getResource("b0.gif"));
		img[1] = tk.createImage(Explode.class.getClassLoader().getResource("b1.gif"));
		img[2] = tk.createImage(Explode.class.getClassLoader().getResource("b1.gif"));
		img[3] = tk.createImage(Explode.class.getClassLoader().getResource("b2.gif"));
		img[4] = tk.createImage(Explode.class.getClassLoader().getResource("b2.gif"));
		img[5] = tk.createImage(Explode.class.getClassLoader().getResource("b3.gif"));
		img[6] = tk.createImage(Explode.class.getClassLoader().getResource("b3.gif"));
		img[7] = tk.createImage(Explode.class.getClassLoader().getResource("b4.gif"));
		img[8] = tk.createImage(Explode.class.getClassLoader().getResource("b5.gif"));
		img[9] = tk.createImage(Explode.class.getClassLoader().getResource("b2.gif"));
		img[10] = tk.createImage(Explode.class.getClassLoader().getResource("b2.gif"));
		img[11] = tk.createImage(Explode.class.getClassLoader().getResource("b6.gif"));
		
		//img[10] = tk.createImage(Explode.class.getClassLoader().getResource("b7.gif"));
		//img[11] = tk.createImage(Explode.class.getClassLoader().getResource("b8.gif"));
		
		ac=Applet.newAudioClip(Explode.class.getClassLoader().getResource("missle.au"));

	}
	
	// 构造方法
	public Explode(int x,int y){
		this.x = x;
		this.y = y;
		
		//创建一个爆炸，播放声音
		ac.play();
	}
	
	// 画爆炸的方法
	public void paint(Graphics g) {
		n--;
		if(index == img.length){
			//爆炸死了
			live = false;
			return;
		}
		// 
		g.drawImage(img[index], x, y, w, h, null);
		if( n<=0 ) {
			index++;
			n = 2;
		}
		n--;
		
	}
	
	
}











