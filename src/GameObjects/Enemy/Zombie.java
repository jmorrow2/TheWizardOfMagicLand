package GameObjects.Enemy;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.state.StateBasedGame;

import HelperClasses.CoordAndDirectionHelper;
import HelperClasses.ImageHelper;
import game.Game;
import game.LevelHandler;

public class Zombie extends Enemy{
	
	private int t=0;
	private boolean walkingDirTimer=false, up=false, down=false, left=false, right=false;
	
	public Zombie(float x, float y){
		super(x, y, 28, 52, 0, 25, 60, .02f);
		init();
		active=false;
	}
	
	@Override
	public void activate() {
		float[] coords = CoordAndDirectionHelper.getRandOffScreenCoords();
		activate(coords[0], coords[1]);
	}
	
	@Override
	public void activate(float x,float y) {
		super.activate(x, y);
		t=0;
		this.spawn=false;
	}
	
	@Override
	public void init(){
		try {
			int[] duration = new int[] {500,500,500};
			movupAn = ImageHelper.initAnimation(new String[]{"res/enemy sprites/zombie_back_1.png", "res/enemy sprites/zombie_back_2.png", "res/enemy sprites/zombie_back_3.png"}, 
					duration,0, false, false);
			movdownAn = ImageHelper.initAnimation(new String[]{"res/enemy sprites/zombie_front_1.png", "res/enemy sprites/zombie_front_2.png", "res/enemy sprites/zombie_front_3.png"}, 
					duration,0, false, false);
			movleftAn = ImageHelper.initAnimation(new String[]{"res/enemy sprites/zombie_left_1.png", "res/enemy sprites/zombie_left_2.png", "res/enemy sprites/zombie_left_3.png"}, 
					duration,0, false, false);
			movrightAn = ImageHelper.initAnimation(new String[]{"res/enemy sprites/zombie_right_1.png", "res/enemy sprites/zombie_right_2.png", "res/enemy sprites/zombie_right_3.png"}, 
					duration,0, false, false);
			
			upLeftAn=movleftAn;
			downLeftAn=movleftAn;
			upRightAn=movrightAn;
			downRightAn=movrightAn;
			
			soundEffects = new Sound[]{new Sound("res/sounds/enemy/zombie.wav")};
		} catch (SlickException e) {
			e.printStackTrace();
		}
		currAn = movdownAn;
	}
	
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) {		
		if(active) {				
			if(health<=0) {
				LevelHandler.zombieDead();
				deactivate();
				return;
			}
			
			if(!stopMovement()) {
				if(walkingDirTimer) {
					t+=delta;
					if(t>1500) {
						walkingDirTimer=false;				
					}						
					
					if(!up && y+26>Game.getWiz().getY()+16) {
						up=true;
					}
					if(!right && x+14<Game.getWiz().getX()+16) {
						right=true;
					}
					if(!down && y+26<Game.getWiz().getY()+16) {
						down=true;
					}
					if(!left && x+14>Game.getWiz().getY()+16) {
						left=true;
					}
					
					switch(facing) {
					case 0 : if(y+26<Game.getWiz().getY()+16) {
								up=false;
								walkingDirTimer=false;
								break;
							}else {
								y-= delta * speed;
								break;
							}
					case 2 : if(x+14>Game.getWiz().getX()+16) {
								right=false;
								walkingDirTimer=false;
								break;
							}else {
								x+= delta * speed;
								break;
							}
					case 4 : if(y+26>Game.getWiz().getY()+16) {
								down=false;
								walkingDirTimer=false;
								break;
							}else {
								y+= delta * speed;
								break;
							}
					case 6 : if(x+14<Game.getWiz().getX()+16) {
								left=false;
								walkingDirTimer=false;
								break;
							}else {
								x-= delta * speed;
								break;
							}
					}
				}else{						
					facing = newFacing(Game.getWiz().getX()+16, Game.getWiz().getY()+16, x+14, y+26);
					switch(facing) {
					case 1 : if(!up) {
								facing=2;
							}else if(!right) {
								facing=0;
							}else {
								facing=CoordAndDirectionHelper.getRand().nextInt(2)==0 ? 0 : 2;
							}
						break;
					case 3 : if(!right) {
								facing=4;
							}else if(!down) {
								facing=2;
							}else {
								facing=CoordAndDirectionHelper.getRand().nextInt(2)==0 ? 2 : 4;
							}
						break;
					case 5 : if(!down) {
								facing=6;
							}else if(!left) {
								facing=4;
							}else {
								facing=CoordAndDirectionHelper.getRand().nextInt(2)==0 ? 4 : 6;
							}
						break;
					case 7 : if(!left) {
								facing=0;
							}else if(!up) {
								facing=6;
							}else {
								facing=CoordAndDirectionHelper.getRand().nextInt(2)==0 ? 6 : 0;
							}
						break;
					}
					t=0;
					walkingDirTimer=true;					
					changeAnimationUsingFacing(facing, null);
				}
			}	
		}
	}
	
	public boolean stopMovement() {
		return x+14>Game.getWiz().getX()+6 && x+14<Game.getWiz().getX()+26 &&
				y+26>Game.getWiz().getY()+6 && y+26<Game.getWiz().getY()+26;
	}
	
	@Override
	public void startSounds() {
		soundEffects[0].loop(1f, .08f);
	}
}
