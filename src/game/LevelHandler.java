package game;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

import GameObjects.Enemy.Bat;
import GameObjects.Enemy.Demon;
import GameObjects.Enemy.Skeleton;
import GameObjects.Enemy.Zombie;
import GameObjects.Magic.Magic;
import HelperClasses.FileHelper;
import HelperClasses.MusicHandler;

public class LevelHandler{
	
	private static int level;
	private TiledMap map;
	
	private static int oldHighScore = 0;
	private static int highScore = 0;
	
	private static int KILL_COUNT = 0;
	
	private static int SKELETON_MAX = 5, ZOMBIE_MAX=3, BAT_MAX=2,
						DEMON_MAX = 0;
	private static int SKELETON_SPAWN_RATE = 5000, ZOMBIE_SPAWN_RATE=7000, BAT_SPAWN_RATE=9000,
						DEMON_SPAWN_RATE = 100;
	private static int SKELTON_KILL_GOAL=2, ZOMBIE_KILL_GOAL=2, BAT_KILL_GOAL=2,
						DEMON_KILL_GOAL=1;
	private static int skeleton_kill_count=0, zombie_kill_count=0, bat_kill_count=0, 
						demon_kill_count=0;
	private static int skeleton_index=0, zombie_index=0, bat_index=0, 
						demon_index=0;
	private static boolean[] killGoalReached;
	
	private static LevelHandler instance; 
	
	private LevelHandler() {}
	
	public static LevelHandler getInstance() 
    { 
        if (instance == null) 
        	instance = new LevelHandler(); 
  
        return instance; 
    } 
	
	public void addWizAndMagic() {
		Game.getGameObjects().add(Game.getWiz());
		for(Magic m : Game.getMagicObjects()) {
			Game.getGameObjects().add(m);
		}
	}
	
	public void initLevel1() {
		try {
			map = new TiledMap("res/map.tmx", "res");
			Game.setMap(map);
			Game.setObjLayerIdx(map.getLayerIndex("Objects"));
		} catch (SlickException e) {
			e.printStackTrace();
		}
		
		addWizAndMagic();
		
		Skeleton[] skeletons = new Skeleton[SKELETON_MAX];		
		for(int i=0; i<SKELETON_MAX; i++){
			Skeleton s = new Skeleton(0,0);
			Game.getGameObjects().add(s);
			skeletons[i] = s;
//			Game.getSkeletons()[i] = s;
		}
		Game.getEnemyObjects().add(skeletons);
		int[] skeletonTimeandRate = new int[] {0,SKELETON_SPAWN_RATE};
		Game.getEnemyTimeAndRate().add(skeletonTimeandRate);
		skeleton_index=0;
//		SKELTON_KILL_GOAL=1;
		
		Zombie[] zombies = new Zombie[ZOMBIE_MAX];
		for(int i=0; i<ZOMBIE_MAX; i++){
			Zombie z = new Zombie(0,0);
			Game.getGameObjects().add(z);
			zombies[i] = z;
//			Game.getZombies()[i] = z;
		}
		Game.getEnemyObjects().add(zombies);
		int[] zombieTimeandRate = new int[] {0,ZOMBIE_SPAWN_RATE};
		Game.getEnemyTimeAndRate().add(zombieTimeandRate);
		zombie_index=1;
//		ZOMBIE_KILL_GOAL=0;
		
		Bat[] bats = new Bat[BAT_MAX];
		for(int i=0; i<BAT_MAX; i++){
			Bat b = new Bat(0,0);
			Game.getGameObjects().add(b);
			bats[i] = b;
		}
		Game.getEnemyObjects().add(bats);
		int[] batTimeandRate = new int[] {0,BAT_SPAWN_RATE};
		Game.getEnemyTimeAndRate().add(batTimeandRate);
		bat_index=2;
//		BAT_KILL_GOAL=0;
		
		killGoalReached = new boolean[Game.getEnemyObjects().size()];
		for(boolean b : killGoalReached) {
			b=false;
		}
		KILL_COUNT=0;
		level=1;
	}
	
	public void initLevel2() {
		try {
			map = new TiledMap("res/map2.tmx", "res");
			Game.setMap(map);
			Game.setObjLayerIdx(map.getLayerIndex("Objects"));
		} catch (SlickException e) {
			e.printStackTrace();
		}
		
		addWizAndMagic();
		
		Demon[] demons = new Demon[DEMON_MAX];
		for(int i=0; i<DEMON_MAX; i++){
			Demon d = new Demon(0,0);
			Game.getGameObjects().add(d);
			demons[i] = d;
		}
		Game.getEnemyObjects().add(demons);
		int[] demonTimeandRate = new int[] {0,DEMON_SPAWN_RATE};
		Game.getEnemyTimeAndRate().add(demonTimeandRate);
		demon_index=0;
		DEMON_KILL_GOAL=1;
		
		killGoalReached = new boolean[Game.getEnemyObjects().size()];
		for(boolean b : killGoalReached) {
			b=false;
		}
		
		level=2;
	}
	
	public void nextLevel() {
		switch(level) {
		case 1 : initLevel2();
		break;
		case 2 : initLevel2();
		break;
		}
	}
	
//	public static void skeletonDead() {skeleton_kill_count++; killGoalReached[skeleton_index]= skeleton_kill_count>=SKELTON_KILL_GOAL;
//										if(checkIfBeatLevel()) {Game.beatLevel();}}
//	public static void zombieDead() {zombie_kill_count++; killGoalReached[zombie_index]= zombie_kill_count>=ZOMBIE_KILL_GOAL;
//										if(checkIfBeatLevel()) {Game.beatLevel();}}
//	public static void batDead() {bat_kill_count++; killGoalReached[bat_index]= bat_kill_count>=BAT_KILL_GOAL;
//										if(checkIfBeatLevel()) {Game.beatLevel();}}
//	public static void demonDead() {demon_kill_count++; killGoalReached[demon_index]= demon_kill_count>=DEMON_KILL_GOAL;
//										if(checkIfBeatLevel()) {Game.beatLevel();}}
	
	public static void skeletonDead() {increaseKillCount();}
	public static void zombieDead() {increaseKillCount();}
	public static void batDead() {increaseKillCount();}
	
	public static void increaseKillCount() {
		KILL_COUNT++;
		if(KILL_COUNT > highScore) {
			highScore = KILL_COUNT;
		}
		if(KILL_COUNT==100) {
			MusicHandler.playMusic("res/sounds/music/oneHundredKills.wav");
		}
	}
	
	//This iteration of the game is only going to be one endless level
	public static boolean checkIfBeatLevel(){
//		boolean ret = true;
//		for(boolean b : killGoalReached) {
//			ret&=b;
//		}
//		return ret;
		return false;
	}
	
	public void clearLevel() {
		Game.getEnemyObjects().clear();
		Game.getGameObjects().clear();
		Game.getEnemyTimeAndRate().clear();
		for(Magic m : Game.getMagicObjects()) {
			m.deactivate();
		}
		skeleton_kill_count=0; zombie_kill_count=0; bat_kill_count=0; demon_kill_count=0;
		Game.getWiz().reset();
	}
	
	public static void checkHighScoreAndWrite() {
		if(highScore > oldHighScore) {
			oldHighScore = highScore;
			FileHelper.writeHighScoreToFile(highScore);
		}
	}
	
	public static int getOldHighScore() {
		return oldHighScore;
	}

	public static void setOldHighScore(int oldHighScore) {
		LevelHandler.oldHighScore = oldHighScore;
	}
	
	public static int getHighScore() {
		return highScore;
	}

	public static void setHighScore(int highScore) {
		LevelHandler.highScore = highScore;
	}
	
	
	public static int get_kill_count() {
		return KILL_COUNT;
	}

	public static void set_kill_count(int kill_count) {
		LevelHandler.KILL_COUNT = kill_count;
	}	

	public static int getSkeleton_kill_count() {
		return skeleton_kill_count;
	}

	public static void setSkeleton_kill_count(int skeleton_kill_count) {
		LevelHandler.skeleton_kill_count = skeleton_kill_count;
	}

	public static int getZombie_kill_count() {
		return zombie_kill_count;
	}

	public static void setZombie_kill_count(int zombie_kill_count) {
		LevelHandler.zombie_kill_count = zombie_kill_count;
	}

	public static int getBat_kill_count() {
		return bat_kill_count;
	}

	public static void setBat_kill_count(int bat_kill_count) {
		LevelHandler.bat_kill_count = bat_kill_count;
	}

	public static int getDemon_kill_count() {
		return demon_kill_count;
	}

	public static void setDemon_kill_count(int demon_kill_count) {
		LevelHandler.demon_kill_count = demon_kill_count;
	}

	public static int getLevel() {
		return level;
	}

	public static void setLevel(int level) {
		LevelHandler.level = level;
	}

	public static boolean[] getKillGoalReached() {
		return killGoalReached;
	}

	public static void setKillGoalReached(boolean[] killGoalReached) {
		LevelHandler.killGoalReached = killGoalReached;
	}

}
