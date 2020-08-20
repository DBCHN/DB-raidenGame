package com.brick.raiden;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import java.util.List;

/**
 * 英雄的类   OOAD --> OOP
 * @author Haste
 *
 */
public class Hero {
	//英雄的属性
	//英雄的位置，通过x，y坐标来标识出来的
	int x;
	
	int y;
	
	//英雄的生死的变量
	boolean live = true;
	
	//英雄的生命值变量,每次中弹后减去10
	int life = 100;
	
	//定义一下英雄的大小(宽度、高度)，并且是不会变的
	//所以，我们要定义常量
	public static final int HERO_WIDTH =100;
	public static final int HERO_HEIGHT =100;

	private static final int List = 0;

	private static final int BloodBank = 0;
	
	//英雄的图片(数组)
	static Image[] heroImg = new Image[3];
	
	static Toolkit tk = Toolkit.getDefaultToolkit();
	
	static int index = 0;
	
	//开火的声音加载
	static AudioClip ac;
	
	//定义一个子弹类的对象，用来保存前一个子弹
	HeroMissile oldMissile;
	
	static {
		//加载英雄的图片
		heroImg[0] = tk.createImage(Hero.class.getClassLoader().getResource("hero2.gif"));
		heroImg[1] = tk.createImage(Hero.class.getClassLoader().getResource("hero3.gif"));
		heroImg[2] = tk.createImage(Hero.class.getClassLoader().getResource("hero4.gif"));
		
		//加载开火的声音
		ac = Applet.newAudioClip(Hero.class.getClassLoader().getResource("zzam.au"));
	}
	
	//定义一个变量，标识出是否开火，true开火；false不开火
	public static boolean fireFlag = false;
	
	//写英雄类的构造方法
	public Hero(int x,int y) {
		this.x = x;
		this.y = y;
	}
	
	//定义英雄画自己的方法
	public void paint(Graphics g) {
		//判断英雄是不是活的，如果不是活的，就不用画出英雄了
		if(!live) {
			return;
		}
		// 画英雄的方法(你要画的图片，坐标x，坐标y，画出图片的宽度，想画多高，通知对象)
		g.drawImage(heroImg[index], x, y, Hero.HERO_WIDTH, Hero.HERO_HEIGHT, null);
		
		//判断是否开火
		if(fireFlag) {
			fire();
		}
	}
	
	//
	public void mouseMoved(MouseEvent e) {
		//输出鼠标所在的位置坐标
		//System.out.println(e.getX()+" | "+e.getY());
		//越界判断
		if(e.getX() < 0) {
			x = 0;
		}else if(e.getX() >= 550-Hero.HERO_WIDTH) {
			x = 550-Hero.HERO_WIDTH;
		}else {
			x = e.getX() - 59;
		}
		if(e.getY() < 0) {
			y = 0;
		}else if(e.getY() >= 830-Hero.HERO_HEIGHT) {
			y = 830-Hero.HERO_HEIGHT;
		}else {
			y = e.getY() -83;
		}
		
	}
	
	//战机开火的方法
	public HeroMissile fire() {
		
		//判断是不是第一次开火
		if(oldMissile == null) {
			//打出一个子弹，就是new一个子弹。然后把这个子弹放到子弹的集合中，就可以了
			HeroMissile missile = new HeroMissile(x+38,y);
			//播放开火的声音
			ac.play();
			
			RaidenGamePanel.heroMissileList.add(missile);
			oldMissile = missile;
		}else if(Math.abs(oldMissile.y - y) > 50 || !oldMissile.live) {
			//打出一个子弹，就是new一个子弹。然后把这个子弹放到子弹的集合中，就可以了
			HeroMissile missile = new HeroMissile(x+38,y);
			//播放开火的声音
			ac.play();
			
			RaidenGamePanel.heroMissileList.add(missile);
			oldMissile = missile;
		}
		return null;
	}
	
	//超级火力
	public void superFire() {
		//打出10发子弹
		for(int i=1;i<10;i++) {
			HeroMissile missile = new HeroMissile(50*i, y);
			RaidenGamePanel.heroMissileList.add(missile);
		}
	}

	//获取英雄战机所在区域的方法
	public Rectangle getRect() {
		return new Rectangle(x,y,Hero.HERO_WIDTH,Hero.HERO_HEIGHT);
	}
	
	//加血包碰撞的方法
	public void collideWithBlood(BloodBank blood) {
		Rectangle heroRect = this.getRect();
		Rectangle bloodRect = blood.getRect();
		
		if(heroRect.intersects(bloodRect)) {
			//加血后，血包死了
			blood.live = false;
			
			this.life += 20;
			
			if(life>100) {
				life = 100;
			}
		}
	}
	
	//
	public void collideWithBloodList(List<BloodBank> bloodList) {
		for(Iterator<BloodBank> it = bloodList.iterator();it.hasNext();) {
			BloodBank bloodBank = it.next();
			collideWithBlood(bloodBank);
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
