package game;

import java.awt.Font;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

import GameObjects.GameObject;
import GameObjects.Wizard;
import GameObjects.Enemy.Enemy;
import GameObjects.Magic.Bonfire;
import GameObjects.Magic.Fireball;
import GameObjects.Magic.Magic;
import GameObjects.Magic.Tidalwave;
import GameObjects.Magic.Waterjet;
import HelperClasses.FileHelper;
import HelperClasses.ImageHelper;
import HelperClasses.MusicHandler;

public class Game extends BasicGameState{
	
	private static int state, magic=1;
	private int screenWidth=Main.getScreenwidth(), screenHeight=Main.getScreenheight();
	
	private static TiledMap map;
	private static int objLayerIdx, waterjetCurrMax;

	private int FIREBALL_MAX = 10, FIREBALL_RATE = 400, fireballsIdx = 0, fireballsT = 0,
			WATERJET_MAX = 10, waterjetIdx = 0;
	
	private Image gameOver;
	private Image exitImg;
	private Image restartImg;
	private Image resumeImg;
	private Image[] fire, water, earth, wind;
	private static boolean gamePaused = false, beatLevel=false;
	private static boolean showEnemyHealth=true;
	
	private static Wizard wiz;
	private static Bonfire bf;
	private static Tidalwave tw;
	private static Fireball[] fireballs;
	private static Waterjet[] waterjet;
	
	protected static List<GameObject> gameObjects;
	private static List<Magic> magicObjects;
	protected static List<Enemy[]> enemyObjects;
	private static List<int[]> enemyTimeAndRate;
	
	private TrueTypeFont ttf;
	
	float[] imgButtonCenteredCoords;
	float[] gameOverCenteredCoords;
	
	public Game(int state) {
		this.state = state;
	}

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {	
		initGame();
	}
	
	public void initGame() throws SlickException{
		gameOver = new Image("res/UI sprites/gameOver.png");
		exitImg = new Image("res/UI sprites/exit button.png");
		restartImg = new Image("res/UI sprites/restart button.png");
		resumeImg = new Image("res/UI sprites/resume button.png");
		
		gameOverCenteredCoords = ImageHelper.centerImg(gameOver);
		imgButtonCenteredCoords = ImageHelper.centerImg(exitImg);
		
		initUI();
		
		gameObjects = new ArrayList<GameObject>();
		magicObjects = new ArrayList<Magic>();
		enemyObjects = new ArrayList<Enemy[]>();
		
		enemyTimeAndRate = new ArrayList<int[]>();
		
		wiz = new Wizard();
		bf = new Bonfire();
		tw = new Tidalwave();
		
		gameObjects.add(bf);
		gameObjects.add(tw);
		magicObjects.add(bf);
		magicObjects.add(tw);
		
		fireballs = new Fireball[FIREBALL_MAX];
		for(int i=0; i<fireballs.length; i++) {
			Fireball f = new Fireball();
			magicObjects.add(f);
			gameObjects.add(f);
			fireballs[i] = f; 
		}
		
		waterjet = new Waterjet[WATERJET_MAX];
		for(int i=0; i<waterjet.length; i++) {
			Waterjet w = new Waterjet(i);
			magicObjects.add(w);
			gameObjects.add(w);
			waterjet[i] = w; 
		}
		waterjetCurrMax=WATERJET_MAX;
		
		
		LevelHandler.getInstance().initLevel1();
	}
	

	public void initUI() throws SlickException{
		Font f = new Font("Kill Count Font", Font.BOLD, 22);
		ttf = new TrueTypeFont(f, false);
		
		fire = new Image[2];
		fire[0] = new Image("res/UI sprites/fire_unsel.png");
		fire[1] = new Image("res/UI sprites/fire_sel.png");
		
		water = new Image[2];
		water[0] = new Image("res/UI sprites/water_unsel.png");
		water[1] = new Image("res/UI sprites/water_sel.png");
		
//		earth = new Image[2];
//		earth[0] = new Image("res/UI sprites/earth_unsel.png");
//		earth[1] = new Image("res/UI sprites/earth_sel.png");
//		
//		wind = new Image[2];
//		wind[0] = new Image("res/UI sprites/wind_unsel.png");
//		wind[1] = new Image("res/UI sprites/wind_sel.png");
	}
	
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		map.render(0, 0);
		
		for(GameObject obj : gameObjects) {
			if(obj.isActive()) {
				obj.render(gc, g);
			}
		}
		
		if(wiz.isDead()) {
			drawGameOver(g);
		}else if(gamePaused){
			drawUI(g);
			drawPaused(g);
		}else {
			drawUI(g);
		}
		
	}
	
	public void drawUI(Graphics g) {	
		//killcount 
		g.setFont(ttf);
		g.setColor(Color.red);
		g.drawString("KILL COUNT: " + LevelHandler.get_kill_count(), 461, 5);
		
		//health bar
		g.drawRoundRect(15, screenHeight-30, 100, 15, 15);
		g.fillRoundRect(15, screenHeight-30, wiz.getHealth(), 15, 15);
		
		//mana bar
		g.setColor(Color.blue);
		g.drawRoundRect(135, screenHeight-30, 100, 15, 15);
		g.fillRoundRect(135, screenHeight-30, wiz.getMana(), 15, 15);
		
		//selected magic icons
		switch(magic) {
		case 1 : fire[1].draw(screenWidth-196, screenHeight-43);
				 water[0].draw(screenWidth-144, screenHeight-43);
			break;
		case 2 : fire[0].draw(screenWidth-196, screenHeight-43);
				 water[1].draw(screenWidth-144, screenHeight-43);
			break;
		}
		
//		switch(LevelHandler.getLevel()) {
////			case 1 : fire[1].draw(screenWidth-196, screenHeight-43);
////				break;
//			case 1 : 
//						switch(magic) {
//						case 1 : fire[1].draw(screenWidth-196, screenHeight-43);
//								 water[0].draw(screenWidth-144, screenHeight-43);
//							break;
//						case 2 : fire[0].draw(screenWidth-196, screenHeight-43);
//								 water[1].draw(screenWidth-144, screenHeight-43);
//							break;
//					}
//			break;
//			case 2 : 
//				switch(magic) {
//					case 1 : fire[1].draw(screenWidth-196, screenHeight-43);
//							 water[0].draw(screenWidth-144, screenHeight-43);
//						break;
//					case 2 : fire[0].draw(screenWidth-196, screenHeight-43);
//							 water[1].draw(screenWidth-144, screenHeight-43);
//						break;
//				}
//				break;
//			case 3 :
//				switch(magic) {
//				case 1 : fire[1].draw(screenWidth-196, screenHeight-43);
//						 water[0].draw(screenWidth-144, screenHeight-43);
//						 earth[0].draw(screenWidth-92, screenHeight-43);
//					break;
//				case 2 : fire[0].draw(screenWidth-196, screenHeight-43);
//						 water[1].draw(screenWidth-144, screenHeight-43);
//						 earth[0].draw(screenWidth-92, screenHeight-43);
//					break;
//				case 3 : fire[0].draw(screenWidth-196, screenHeight-43);
//				         water[0].draw(screenWidth-144, screenHeight-43);
//				         earth[1].draw(screenWidth-92, screenHeight-43);
//					break;
//				}
//				break;
//			default :
//				switch(magic) {
//				case 1 : fire[1].draw(screenWidth-196, screenHeight-43);
//						 water[0].draw(screenWidth-144, screenHeight-43);
//						 earth[0].draw(screenWidth-92, screenHeight-43);
//						 wind[0].draw(screenWidth-40, screenHeight-43);
//					break;
//				case 2 : fire[0].draw(screenWidth-196, screenHeight-43);
//						 water[1].draw(screenWidth-144, screenHeight-43);
//						 earth[0].draw(screenWidth-92, screenHeight-43);
//						 wind[0].draw(screenWidth-40, screenHeight-43);
//					break;
//				case 3 : fire[0].draw(screenWidth-196, screenHeight-43);
//				         water[0].draw(screenWidth-144, screenHeight-43);
//				         earth[1].draw(screenWidth-92, screenHeight-43);
//				         wind[0].draw(screenWidth-40, screenHeight-43);
//					break;
//				case 4 : fire[0].draw(screenWidth-196, screenHeight-43);
//				         water[0].draw(screenWidth-144, screenHeight-43);
//				         earth[0].draw(screenWidth-92, screenHeight-43);
//				         wind[1].draw(screenWidth-40, screenHeight-43);
//					break;
//			}
//		}
	}
	
	public void drawGameOver(Graphics g) {	
		gameOver.draw(gameOverCenteredCoords[0],15);		
		restartImg.draw(imgButtonCenteredCoords[0], 300);
		exitImg.draw(imgButtonCenteredCoords[0], 350);
		
		g.setFont(ttf);
		g.setColor(Color.black);
		g.drawString("HIGH SCORE: " + LevelHandler.getHighScore(), 235, 250);
	}
	
	public void drawPaused(Graphics g) {	
		resumeImg.draw(imgButtonCenteredCoords[0], 300);
		exitImg.draw(imgButtonCenteredCoords[0], 350);
		g.setFont(ttf);
		g.setColor(Color.black);
		g.drawString("HIGH SCORE: " + LevelHandler.getHighScore(), 235, 250);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		//just died
		if(!gc.isPaused() && wiz.isDead()) {
			wizDead();
			gc.pause();
		}
		
		//game paused
		if(gc.isPaused() && !wiz.isDead()) {
			int xpos = Mouse.getX();
			int ypos = Mouse.getY();
			
			if((xpos>173 && xpos<464) && (ypos>76 && ypos<115)) {
				if(Mouse.isButtonDown(0)){
					gamePaused = false;
					resumeGameObjectAnimations();
					playGameObjectSounds();
					gc.resume();
				}
			}
			
			if((xpos>173 && xpos<464) && (ypos>22 && ypos<66)) {
				if(Mouse.isButtonDown(0)){
					System.exit(0);
				}
			}
		}
		
		//gameover
		if(gc.isPaused() && wiz.isDead()) {
			int xpos = Mouse.getX();
			int ypos = Mouse.getY();
			
			if((xpos>173 && xpos<464) && (ypos>76 && ypos<115)) {
				if(Mouse.isButtonDown(0)){
					restart(gc);
				}
			}
			
			if((xpos>173 && xpos<464) && (ypos>22 && ypos<66)) {
				if(Mouse.isButtonDown(0)){
					System.exit(0);
				}
			}
		}
		
		Input input = gc.getInput();
		
		//playing game
		if(!gc.isPaused() && !wiz.isDead()) {
			
			updateEnemySpawn(delta);
						
			if(magic==2 && waterjetCurrMax<WATERJET_MAX && !waterjetCollisionCheck()) {
				waterjetCurrMax=WATERJET_MAX;
			}
			collisionCheck(delta);
			
			for(GameObject obj : gameObjects) {
				if(obj.isActive()) {
					obj.update(gc, sbg, delta);
				}
			}
			
			fireballsT+=delta;	
			
			if(input.isKeyPressed(Input.KEY_1)){
				magic = 1;
			}
			if(input.isKeyPressed(Input.KEY_2)){
				magic = 2;
			}
			if(input.isKeyPressed(Input.KEY_3)&& LevelHandler.getLevel()>=3){
				magic = 3;
			}
			if(input.isKeyPressed(Input.KEY_4)&& LevelHandler.getLevel()>=4){
				magic = 4;
			}
			
			if(input.isKeyPressed(Input.KEY_W)) {
				switch(magic) {
				case 1 : if(wiz.getMana()>=30 && !bf.isActive()) { 
							bf.activate(wiz.getX(),wiz.getY(), wiz.getFacing());
							wiz.setMana(wiz.getMana()-30);
						}
					break;
				case 2 : if(wiz.getMana()>=50 && !tw.isActive()) {
							tw.activate(wiz.getX(),wiz.getY(), wiz.getFacing());
							wiz.setMana(wiz.getMana()-30);
						}
					break;
				}				
			}	
			if(input.isKeyDown(Input.KEY_Q)) {
				switch(magic) {
				case 1 : if(wiz.getMana()>=5) { 
							if(fireballsT > FIREBALL_RATE) {
								fireballs[fireballsIdx].activate(wiz.getX(),wiz.getY(), wiz.getFacing());
								fireballsIdx++;
								wiz.setMana(wiz.getMana()-5);
								if( fireballsIdx >= fireballs.length) {
									fireballsIdx = 0;					
								}
								fireballsT=0;
							}
						}
					break;
				case 2 : if(wiz.getMana()>=0) { 
						wiz.setMana(wiz.getMana()-.02f);
						if(!waterjet[waterjetIdx].isActive()) {
							waterjet[waterjetIdx].activate(wiz.getX(),wiz.getY(), wiz.getFacing());
							waterjetIdx++;							
							if(waterjetIdx >= waterjetCurrMax) {
								waterjetIdx = 0;					
							}
						}
					}
					break;
				}
			}
			
			if(input.isKeyDown(Input.KEY_ESCAPE)) {
				gamePaused = true;
				pauseGameObjectAnimations();
				stopGameObjectSounds();
				gc.pause();
			}
		}
		
//		if(beatLevel) {
//			if(input.isKeyDown(Input.KEY_ENTER)) {
//				pause = false;
//				beatLevel=false;		
//				LevelHandler.getInstance().clearLevel();
//				LevelHandler.getInstance().nextLevel();
//				for(GameObject g : gameObjects) {
//					g.startAnimation();
//				}
//				gc.resume();
//			}
//		}
	}
	
//	public static void beatLevel() {
//		beatLevel=true;
//		for(GameObject g : gameObjects) {
//			g.stopAllSounds();
//			g.stopAnimation();
//		}
//	}
	
	
	public void updateEnemySpawn(int delta) {
		for(int i=0; i<enemyTimeAndRate.size(); i++) {
			if(!LevelHandler.getKillGoalReached()[i]) {
				enemyTimeAndRate.get(i)[0]+=delta;
				if(enemyTimeAndRate.get(i)[0]>=enemyTimeAndRate.get(i)[1]) {
					spawnEnemy(i);
					enemyTimeAndRate.get(i)[0]=0;
				}
			}
		}
	}
	
	public void spawnEnemy(int index) {
		Enemy[] arr = enemyObjects.get(index);
		for(Enemy e : arr){
			if(!e.isActive()) {
				e.activate();
				break;
			}
		}		
	}
	
	public void collisionCheck(int delta) {
		for(Enemy[] arr : enemyObjects) {
			for (Enemy e : arr) { 
				if(e.isActive() && !e.isSpawn()) {
				    for (Magic m : magicObjects) {
				        if (m.isActive() && e.intersects(m)) {
				        	m.hitEnemy(e, delta);
				        }
				    }
				    if(e.intersects(wiz)) {
				    	wiz.gotHit(e.getDamage());
				    }
				}
			}
		}
	}
	
	public boolean waterjetCollisionCheck() {
		for(Enemy[] arr : enemyObjects) {
			for (Enemy e : arr) { 
				if(e.isActive() && !e.isSpawn()) {
				    for (Waterjet w : waterjet) {
				        if (w.isActive() && e.intersects(w)) {
				        	return true;
				        }
				    }
				}
			}
		}
		return false;
	}
	
	public static void wizDead() {
		pauseGameObjectAnimations();
		stopGameObjectSounds();
	}
	
	public void restart(GameContainer gc) throws SlickException {
		initGame();
		gc.resume();
		gamePaused = false;
		MusicHandler.playMusic("res/sounds/music/gamePlay.wav");
	}
	
	public static void pauseGameObjectAnimations() {
		for(GameObject obj : gameObjects) {
			if(obj.getCurrAn() != null) {
				obj.getCurrAn().stop();
			}
		}
	}
	
	public static void resumeGameObjectAnimations() {
		for(GameObject obj : gameObjects) {
			if(obj.getCurrAn() != null) {
				obj.getCurrAn().start();
			}
		}
	}
	
	public static void stopGameObjectSounds() {
		for(GameObject obj : gameObjects) {
			if(obj.isActive()) {
				obj.stopAllSounds();
			}
		}
	}
	
	public static void playGameObjectSounds() {
		for(GameObject obj : gameObjects) {
			if(obj.isActive()) {
				obj.startSounds();
			}
		}
	}	
	
	public static int getMagic() {
		return magic;
	}
	
	public static Wizard getWiz() {
		return wiz;
	}
	
	public static TiledMap getMap() {
		return map;
	}

	public static void setMap(TiledMap map) {
		Game.map = map;
	}

	public static int getObjLayerIdx() {
		return objLayerIdx;
	}

	public static void setObjLayerIdx(int objLayerIdx) {
		Game.objLayerIdx = objLayerIdx;
	}

	public static int getWaterjetCurrMax() {
		return waterjetCurrMax;
	}

	public static void setWaterjetCurrMax(int waterjetCurrMax) {
		Game.waterjetCurrMax = waterjetCurrMax;
	}

	public static List<GameObject> getGameObjects() {
		return gameObjects;
	}

	public static void setGameObjects(List<GameObject> gameObjects) {
		Game.gameObjects = gameObjects;
	}

	public static List<Enemy[]> getEnemyObjects() {
		return enemyObjects;
	}

	public static void setEnemyObjects(List<Enemy[]> enemyObjects) {
		Game.enemyObjects = enemyObjects;
	}

	public static List<Magic> getMagicObjects() {
		return magicObjects;
	}

	public static void setMagicObjects(List<Magic> magicObjects) {
		Game.magicObjects = magicObjects;
	}

	public static boolean isShowEnemyHealth() {
		return showEnemyHealth;
	}

	public static void setShowEnemyHealth(boolean showEnemyHealth) {
		Game.showEnemyHealth = showEnemyHealth;
	}

	public static List<int[]> getEnemyTimeAndRate() {
		return enemyTimeAndRate;
	}

	public static void setEnemyTimeAndRate(List<int[]> enemyTimeAndRate) {
		Game.enemyTimeAndRate = enemyTimeAndRate;
	}

	@Override
	public int getID() {
		return state;
	}
}
