package com.brick.raiden;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;

/**
 * 敌人的子弹类
 * @author Haste
 *
 */
public class EnemyMissile {

	// 属性和方法分析：坐标、大小、图片、构造方法、画敌机、子弹移动、打英雄
	int x;

	int y;

	int width = 15;

	int height = 30;
	
	//生死的变量
	boolean live = true;

	static Image img;

	static Toolkit tk = Toolkit.getDefaultToolkit();

	static {
		img = tk.createImage(EnemyMissile.class.getClassLoader().getResource("dijizidan.gif"));

	}

	public EnemyMissile(int x, int y) {
		this.x = x;
		this.y = y;
	}

	// 定义敌人的子弹画自己的方法
	public void paint(Graphics g) {
		//
		g.drawImage(img, x, y, width, height, null);
		
		//每次画子弹的时候，让子弹移动
		move();
	}
	
	//敌人的子弹移动方法
	public void move(){
		y+=15;
		if(y > 830) {
			//敌机子弹就死了
			live = false;
		}
	}
	
	//获取敌人子弹所在区域的方法
	public Rectangle getRect() {
		return new Rectangle(x,y,width,height);
	}
		
	//敌机的子弹打英雄，所以，我们要在敌机子弹类中写这个打英雄的方法
	public void hitHero(Hero hero) {
		Rectangle enemyMissileRect = this.getRect();
		Rectangle heroRect = hero.getRect();
		if(enemyMissileRect.intersects(heroRect)) {
			//打中了
			this.live = false;
			
			//英雄的生命值减去10
			int life = hero.life - 10;
			hero.life = life;
			
			if(life <= 0) {
				hero.live = false;
				hero.life = 0;
			}
			
			//产生爆炸
			//产生爆炸,并且添加爆炸到集合中
			Explode exp = new Explode(x,y);
			RaidenGamePanel.explodeList.add(exp);
		}
	}

}









