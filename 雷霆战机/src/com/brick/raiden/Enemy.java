package com.brick.raiden;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.Random;

/**
 * 敌人的类
 * @author Haste
 *
 */
public class Enemy {

	int x;
	
	int y;
	
	// 宽度，高度
	public static final int ENEMY_WIDTH = 60;

	public static final int ENEMY_HEIGHT = 60;

	// 加载敌人的图片，是一个图片数组，因为有多个图片表示左中右三个方向的敌人
	static Image[] enemyImg = new Image[3];

	static Toolkit tk = Toolkit.getDefaultToolkit();
	
	//定义一个表示敌机方向的变量，变量的值是0,1,2；分别代表左中右
	int dir = 1;
	
	//生死的变量
	boolean live = true;
	
	//每次改变方向后，走n多步
	int n = new Random().nextInt(20)+10;

	static {
		enemyImg[0] = tk.createImage(Enemy.class.getClassLoader().getResource("dijileft.gif"));
		enemyImg[1] = tk.createImage(Enemy.class.getClassLoader().getResource("diji.gif"));
		enemyImg[2] = tk.createImage(Enemy.class.getClassLoader().getResource("dijiright.gif"));

	}

	// 构造方法
	public Enemy(int x, int y) {
		this.x = x;
		this.y = y;
	}
		
	// 画敌机的方法
	public void paint(Graphics g) {
		n--;
		if( n<=0 ) {
			//每次画的时候，我们改变一下dir的值，也就是改变了敌机的方向
			dir = new Random().nextInt(3);//1,2,3
			n = new Random().nextInt(10)+10;
		}
		 
		g.drawImage(enemyImg[1], x, y, ENEMY_WIDTH, ENEMY_HEIGHT, null);
		//调用敌机移动的方法
		move();
		
		int f = new Random().nextInt(1000);//0-999
		//每次画的时候，敌人开火
		if(f > 990) {
			fire();
		}
	}
	
	//移动的方法
	public void move() {
		y+=8;
		if(dir == 0) {
			//左边方向
			x-=5;
		}
		if(dir == 2) {
			x+=5;
		}
		
		//不让敌机超出左右边界
		if(x <= 0) {//左边界
			x=0;
			//dir = 0;
		}
		
		if(x >= 550-Enemy.ENEMY_WIDTH) {//右边界
			x = 550-Enemy.ENEMY_WIDTH;
			//dir = 2;
		}
		
		//如果移动后，飞出下边界，敌机就死了
		if(y > 830) {
			//敌机就死了
			live = false;
			
		}
	}
	
	//获取敌机所在区域的方法
		public Rectangle getRect() {
			return new Rectangle(x,y,ENEMY_WIDTH,ENEMY_HEIGHT);
		}

		//让敌机开火
		public void fire() {
			EnemyMissile em = new EnemyMissile(x+ENEMY_WIDTH/2, y+ENEMY_HEIGHT/2);
			
			RaidenGamePanel.enemyMissileList.add(em);
		}
		
}


















