package com.brick.raiden;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.Iterator;
import java.util.List;

/**
 * 英雄的子弹类
 * @author Haste
 *
 */
public class HeroMissile {
	
	//标识生死的变量，true活着，false死了
	boolean live = true;

	int x;
	int y;
	
	//宽度 高度
	public static  final int HERO_MISSILE_WIDTH = 30;
	
	public static  final int HERO_MISSILE_HEIGHT = 35;
	
	//子弹的图片
	static Image missileImg;
	
	static Toolkit tk = Toolkit.getDefaultToolkit();
	
	static {
		missileImg = tk.createImage(HeroMissile.class.getClassLoader().getResource("herozidan.gif"));
	}
	
	public HeroMissile(int x,int y) {
		this.x = x;
		this.y = y;
	}
	
	//定义英雄画自己的方法
		public void paint(Graphics g) {
			// 画英雄的子弹方法(你要画的图片，坐标x，坐标y，画出图片的宽度，想画多高，通知对象)
			g.drawImage(missileImg, x, y, HeroMissile.HERO_MISSILE_WIDTH, HeroMissile.HERO_MISSILE_HEIGHT, null);
			
			move();
		}
		
	//子弹飞，所以我们要在英雄的子弹类中定义一个移动的方法
	public void move() {
		y-=10;
		
		//如果子弹飞出上边界，子弹就死了
		if(y<0) {
			live = false;
		}
	}
	
	//打一个敌机,返回值为true就是打中了，false没打中
	public boolean hitEnemy(Enemy enemy) {
		Rectangle misslieRect = this.getRect();
		Rectangle enemyRect = enemy.getRect();
		
		if(misslieRect.intersects(enemyRect)) {
			//打中了，子弹死了，敌人也死了
			live = false;
			enemy.live = false;
			
			//产生爆炸,并且添加爆炸到集合中
			Explode exp = new Explode(x,y);
			RaidenGamePanel.explodeList.add(exp);
			
			//积分增加10分
			RaidenGamePanel.score += 10;
			return true;
		}
		
		return false;
	}
	
	//打敌机的集合
	public void hitEnemyList(List<Enemy> enemyList) {
		for(Iterator<Enemy> it = enemyList.iterator();it.hasNext();) {
			Enemy enemy = it.next();
			boolean b = hitEnemy(enemy);
			if(b) {
				return;
			}
		}
	}
	
	//获取子弹所在区域的方法
	public Rectangle getRect() {
		return new Rectangle(x,y,HERO_MISSILE_WIDTH,HERO_MISSILE_HEIGHT);
	}
	
	//英雄的子弹可以消灭敌人的子弹
	public boolean hitEnemyMissile(EnemyMissile enemyMissile) {
		Rectangle heroMissileRect = this.getRect();
		Rectangle enemyMissileRect = enemyMissile.getRect();
		if(heroMissileRect.intersects(enemyMissileRect)) {
			//子弹打子弹，打中了
			this.live = false;
			enemyMissile.live = false;
			//产生爆炸,并且添加爆炸到集合中
			Explode exp = new Explode(x,y);
			RaidenGamePanel.explodeList.add(exp);
			
			//拦截一个子弹，积分增加5
			RaidenGamePanel.score += 5;
			
			//如果打中了，返回true
			return true;
		}
		return false;
	}
	
	
	//子弹打子弹的集合
	public void hitEnemyMissileList(List<EnemyMissile> enemyMissileList) {
		for(Iterator<EnemyMissile> it = enemyMissileList.iterator();it.hasNext();) {
			EnemyMissile enemyMissile = it.next();
			
			if(hitEnemyMissile(enemyMissile)) {
				return;
			}
		}
	}
}








