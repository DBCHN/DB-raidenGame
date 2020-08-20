package com.brick.raiden;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JPanel;
/**
 * 游戏面板类，我们游戏中要画出来的内容，都是在这个面板中实现的
 * 然后，我们将这个面板放到JFrame中
 * @author Haste
 *
 */
public class RaidenGamePanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// 最开始必须先画出游戏背景图片
	// 加载图片到我们的类中
	// Image类，就是图片类
	static Image bjImg ;
	
	// 音乐的播放器
	//static AudioClip ac;
	static Clip c;
	final static String f="./src/Every Breath You Take.mid";
	
	// Toolkit工具包类，可以创建图片
	static Toolkit tk = Toolkit.getDefaultToolkit();
	
	// 在静态块中，创建图片
	static{
		bjImg = tk.createImage(RaidenGamePanel.class.getClassLoader().getResource("bj005.jpg"));
		
		// 创建音乐播放器
		//ac = Applet.newAudioClip(RaidenGamePanel.class.getClassLoader().getResource("Every Breath You Take.amr"));
		try {
			c=AudioSystem.getClip();
			c.open(AudioSystem.getAudioInputStream(new File(f)));
		}catch(Exception ex) {
			ex.printStackTrace();
		}

	}
	
	// 定义一个y变量，用来设置背景图片的滚动
	int y = -1460;
	
	//游戏结束文字升起的起始位置，从游戏的下边界位置升起
	int gameOverStrY = 830;
	
	//游戏是否启动的变量,初始化值为false，游戏没有开始
	boolean gameStart = false;
	
	//游戏结束标识变量:false 没有结束；true游戏结束
	boolean gameStop = false;
	
	//积分变量
	static int score;
	
	//创建一个英雄，new一个英雄的实例(对象)
	static Hero myHero =new Hero(220,550);
	
	//英雄的子弹
	//HeroMissile missile = new HeroMissile(250,400);
	
	//定义一个英雄子弹的集合，用来盛放子弹的容器
	static List<HeroMissile> heroMissileList = new ArrayList<>();
	
	//创建一个敌机
	//Enemy enemy = new Enemy(250, 0);
	
	//定义一个敌人的集合，用来放敌机
	static List<Enemy> enemyList = new ArrayList<>();
	
	//定义一个爆炸
	//Explode explode = new Explode(250, 250);
	
	//定义一个爆炸的集合
	static List<Explode> explodeList = new ArrayList<>();
	
	//创建敌机子弹的实例
	//EnemyMissile enemyMissile = new EnemyMissile(250, 250);
	static List<EnemyMissile> enemyMissileList = new ArrayList<>();
	
	//加血包的创建
	//BloodBank blood = new BloodBank(250,250);
	
	//加血包创建一个集合
	static List<BloodBank> bloodList = new ArrayList<>();
	
	//构造方法
	public RaidenGamePanel(){
		/*MyGameThread myThread = new MyGameThread();
		//启动线程
		myThread.start();*/
		
		this.addMouseMotionListener(new MouseMotionListener() {
					
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
		});
		
		//点击鼠标监听器
		this.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				//RaidenGamePanel.myHero.fire();
				
				Hero.fireFlag = true;
				
				//鼠标点击，判断如果游戏没有启动，那么就启动游戏
				if(!gameStart) {
					//没有启动游戏线程，那么我们就启动这个线程
					MyGameThread myThread = new MyGameThread();
					//启动线程
					myThread.start();
					//游戏启动后，给变量赋值为true，说明游戏已经启动
					gameStart = true;
				}
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				Hero.fireFlag = false;
			}
		});
	}

	// 在面板上画出游戏背景图片
	@Override
	public void paint(Graphics g) {
		//每次画的时候，判断集合中是否有血包，如果没有，可以考虑加一个
		if(bloodList.isEmpty()) {
			if(new Random().nextInt(10000)>5000) {
				BloodBank blood = new BloodBank(new Random().nextInt(550),new Random().nextInt(300)-400);
				
				bloodList.add(blood);
			}
		}
		
		//在添加敌机的时候，判断集合中如果敌机的数量少于或等于10个，我们就添加n多个敌机
		if(enemyList.size() <= 10) {
			//每次画的时候加入敌人
			int n = new Random().nextInt(10)+10;
			for(int i=0;i<n;i++) {
				int x = new Random().nextInt(550);
				int y = new Random().nextInt(1000)-1000;
				
				Enemy enemy = new Enemy(x, y);
				
				enemyList.add(enemy);
			}
		}
		
		// 画游戏背景(你要画的图片，坐标x，坐标y，画出图片的宽度，想画多高，通知对象)
		g.drawImage(bjImg, 0, y, 550, 830*3, this);
		
		//画出英雄
		myHero.paint(g);
		
		//
		myHero.collideWithBloodList(bloodList);
		
		//画子弹,我们要画出多个子弹，遍历集合，逐个取出子弹，一个一个画
		//missile.paint(g);
		for(Iterator<HeroMissile> it = heroMissileList.iterator();it.hasNext();) {
			HeroMissile missile = it.next();
			//判断子弹是不是活的
			if(missile.live) {
				missile.paint(g);
				//打敌人的集合
				missile.hitEnemyList(enemyList);
				//打敌人的子弹集合
				missile.hitEnemyMissileList(enemyMissileList);
			}else {
				//如果子弹不是活的，就从集合中移除
				it.remove();
			}
		}
		
		//画敌机
		//enemy.paint(g);
		
		//画集合中的敌人，逐个画
		for(Iterator<Enemy> it = enemyList.iterator();it.hasNext();) {
			Enemy enemy = it.next();
			if(enemy.live) {
				enemy.paint(g);
			}else {
				it.remove();
			}
			
		}
		
		//画爆炸
		//explode.paint(g);
		
		//画集合中的爆炸
		for(Iterator<Explode> it = explodeList.iterator();it.hasNext();) {
			Explode explode = it.next();
			if(explode.live) {
				explode.paint(g);
			}else{
				it.remove();
			}
		}
		
		//画敌机子弹
		//enemyMissile.paint(g);
		Iterator<EnemyMissile> it = enemyMissileList.iterator();
		
		while(it.hasNext()) {
			//补齐前面变量声明部分的代码，快捷键是：Ctrl+2放手，然后按L键
			EnemyMissile enemyMissile = it.next();
			if(enemyMissile.live) {
				enemyMissile.paint(g);
				//敌机子弹打英雄
				enemyMissile.hitHero(myHero);
			}else {
				it.remove();
			}
		}
		
		//画出加血包
		//blood.paint(g);
		
		//画出加血包
		for(Iterator<BloodBank> it1 = bloodList.iterator();it1.hasNext();) {
			BloodBank blood = it1.next();
			
			if(blood.live) {
				blood.paint(g);
			}else {
				it1.remove();
			}
		}
		
		//设置颜色
		g.setColor(new Color(255,0,0));
		
		//设置字体
		g.setFont(new Font("宋体",Font.BOLD,20));
		
		//在游戏的窗口中写出一段文字
		/*g.drawString("战机的子弹个数是："+heroMissileList.size(), 20, 30);
		
		g.drawString("敌人的子弹个数是："+enemyMissileList.size(), 20, 60);
		
		g.drawString("敌机的个数是："+enemyList.size(), 20, 90);
		
		g.drawString("爆炸的个数是："+explodeList.size(), 20, 120);*/
		
		//游戏还没开始运行的时候，我们要输出一些文字信息
		//你能过1000分吗？来挑战吧
		//点击鼠标开始游戏
		if(!gameStart) {
			g.drawString("你能过1000分吗？来挑战吧！", 150, 250);
			
			g.drawString("点击鼠标开始游戏！", 150, 350);
		}
		
		g.drawString("积分是："+score, 10, 30);
		
		g.drawString("生命值是："+myHero.life, 390, 30);
		
		if(!myHero.live) {
			//如果英雄牺牲了，我们升起一段文字：GAME OVER
			g.setFont(new Font("宋体",Font.BOLD,35));
			
			g.drawString("GAME OVER", 200, gameOverStrY);		
			gameOverStrY -= 5;
			
			if(gameOverStrY <= 300) {
				//真正结束游戏
				gameStop = true;
			}
		}
		
		//图形化英雄的生命值
		g.drawRect(390, 35, 130, 20);
		
		g.setColor(Color.GREEN);
		
		g.fillRect(391, 36, 130*myHero.life/100, 18);
		
	}
	
	// 通过线程来实现背景图片的滚动功能，定义一个内部类，实现线程
	class MyGameThread extends Thread{

		//线程启动后，自动调用这个run方法
		@Override
		public void run() {
			//播放背景音乐,并且循环播放
			//ac.loop();
			c.loop(0);
			
			//无限循环，完成背景图片的滚动
			while(true){
				//
				if(gameStop) {
					c.stop();
					
					return;
				}
				
				y+=3; // y = y+5;
				RaidenGamePanel.this.repaint();
				if(y >= 0){
					y = -1460;
				}
				try {
					sleep(30);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
	
}







