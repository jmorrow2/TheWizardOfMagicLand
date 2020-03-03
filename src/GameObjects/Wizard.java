package GameObjects;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.state.StateBasedGame;

import HelperClasses.ImageHelper;
import game.Game;
import game.LevelHandler;
import game.Main;

public class Wizard extends GameObject{
	
	/*Animation needs to have an array of ints for the duration
	 * for these animations we have 2 sprites per movement so duration array is size 2
	 * the numbers in duration are how long each image will last on screen
	 */	
	private float health = 100, maxHealth=100, mana = 100, maxMana=100;
	
	private boolean dead=false, changeFacingTimer=false, changeDirectionTimer=false;
	
	//direction wiz is facing. goes clockwise 0-3
	//t is time for damageInvincibility
	//magic 1-4 fire, water, earth, wind
	private int t=0, magic, tt=0, ttt=0;;
	
	private double manaRegen = .005;
	
	private Animation movupDmgAn, movdownDmgAn, movleftDmgAn, movrightDmgAn, upLeftDmgAn, downLeftDmgAn, upRightDmgAn, downRightDmgAn;
	private Animation[] damagedAns;

	public Wizard() {
		super((Main.getScreenwidth()/2)-16, (Main.getScreenheight()/2)-16, 32, 32);
		active=true;
		init();
		facing=4;
	}
	
	@Override
	public void init() {
		/*to create the animation for walking we use Image[] for all the sprites for each direction
		 * once we have the arrays of Images we create the Animation objs for each direction
		 * Animation params are Image array, int[] duration, boolean autoupdate means the animation is always happening 
		 */
		try {
			movupAn = ImageHelper.initAnimation(new String[]{"res/wizard sprites/wizard_back_1.png", "res/wizard sprites/wizard_back_2.png"}, 
								new int[] {500,500},0, false, false);
			movdownAn = ImageHelper.initAnimation(new String[]{"res/wizard sprites/wizard_front_1.png", "res/wizard sprites/wizard_front_2.png"}, 
								new int[] {500,500},0, false, false);
			movleftAn = ImageHelper.initAnimation(new String[]{"res/wizard sprites/wizard_left_1.png", "res/wizard sprites/wizard_left_2.png"}, 
								new int[] {500,500},0, false, false);
			movrightAn = ImageHelper.initAnimation(new String[]{"res/wizard sprites/wizard_right_1.png", "res/wizard sprites/wizard_right_2.png"}, 
								new int[] {500,500},0, false, false);
			
			upLeftAn = ImageHelper.initAnimation(new String[]{"res/wizard sprites/wizard_left_1.png", "res/wizard sprites/wizard_left_2.png"}, 
					new int[] {500,500},45, false, false);
			downLeftAn = ImageHelper.initAnimation(new String[]{"res/wizard sprites/wizard_front_1.png", "res/wizard sprites/wizard_front_2.png"}, 
					new int[] {500,500},45, false, false);
			upRightAn = ImageHelper.initAnimation(new String[]{"res/wizard sprites/wizard_right_1.png", "res/wizard sprites/wizard_right_2.png"}, 
					new int[] {500,500},-45, false, false);
			downRightAn = ImageHelper.initAnimation(new String[]{"res/wizard sprites/wizard_front_1.png", "res/wizard sprites/wizard_front_2.png"}, 
					new int[] {500,500},-45, false, false);
			
			movupDmgAn = ImageHelper.initAnimation(new String[]{"res/wizard sprites/wizard_back_dmg_1.png", "res/wizard sprites/wizard_back_1.png", 
					"res/wizard sprites/wizard_back_dmg_2.png", "res/wizard sprites/wizard_back_2.png"}, 
					new int[] {250,250,250,250},0, false, false);
			movdownDmgAn = ImageHelper.initAnimation(new String[]{"res/wizard sprites/wizard_front_dmg_1.png", "res/wizard sprites/wizard_front_1.png", 
					"res/wizard sprites/wizard_front_dmg_2.png", "res/wizard sprites/wizard_front_2.png"}, 
					new int[] {250,250,250,250},0, false, false);
			movleftDmgAn = ImageHelper.initAnimation(new String[]{"res/wizard sprites/wizard_left_dmg_1.png", "res/wizard sprites/wizard_left_1.png", 
					"res/wizard sprites/wizard_left_dmg_2.png", "res/wizard sprites/wizard_left_2.png"}, 
					new int[] {250,250,250,250},0, false, false);
			movrightDmgAn = ImageHelper.initAnimation(new String[]{"res/wizard sprites/wizard_right_dmg_1.png", "res/wizard sprites/wizard_right_1.png", 
					"res/wizard sprites/wizard_right_dmg_2.png", "res/wizard sprites/wizard_right_2.png"}, 
					new int[] {250,250,250,250},0, false, false);
			
			upLeftDmgAn = ImageHelper.initAnimation(new String[]{"res/wizard sprites/wizard_left_dmg_1.png", "res/wizard sprites/wizard_left_1.png",
					"res/wizard sprites/wizard_left_dmg_2.png", "res/wizard sprites/wizard_left_2.png"}, 
					new int[] {250,250,250,250},45, false, false);
			downLeftDmgAn = ImageHelper.initAnimation(new String[]{"res/wizard sprites/wizard_front_dmg_1.png", "res/wizard sprites/wizard_front_1.png",
					"res/wizard sprites/wizard_front_dmg_2.png", "res/wizard sprites/wizard_front_2.png"}, 
					new int[] {250,250,250,250},45, false, false);
			upRightDmgAn = ImageHelper.initAnimation(new String[]{"res/wizard sprites/wizard_right_dmg_1.png", "res/wizard sprites/wizard_right_1.png",
					"res/wizard sprites/wizard_right_dmg_2.png", "res/wizard sprites/wizard_right_2.png"}, 
					new int[] {250,250,250,250},-45, false, false);
			downRightDmgAn = ImageHelper.initAnimation(new String[]{"res/wizard sprites/wizard_front_dmg_1.png", "res/wizard sprites/wizard_front_1.png",
					"res/wizard sprites/wizard_front_dmg_2.png", "res/wizard sprites/wizard_front_2.png"}, 
					new int[] {250,250,250,250},-45, false, false);
					
			damagedAns = new Animation[8];
			damagedAns[0] = movupDmgAn;
			damagedAns[1] = upRightDmgAn;
			damagedAns[2] = movrightDmgAn;
			damagedAns[3] = downRightDmgAn;
			damagedAns[4] = movdownDmgAn;
			damagedAns[5] = downLeftDmgAn;
			damagedAns[6] = movleftDmgAn;
			damagedAns[7] = upLeftDmgAn;
			
			/*here we set the main character animation to down because we start facing down
			 * then as we change direction we just set wizard to the different directions
			 */
			currAn = movdownAn;
			
			soundEffects = new Sound[]{};
		} catch (SlickException e) {
			e.printStackTrace();
		}	
		magic = Game.getMagic();
	}
	
	public void update(GameContainer gc, StateBasedGame sbg, int delta) {
		Input input = gc.getInput();
		
		boolean blockedtl=false, blockedtr=false, blockedbl=false, blockedbr=false,changeDir=true;
		
		int tileIDtl = Game.getMap().getTileId((int)x/32,(int)y/32,Game.getObjLayerIdx());
		blockedtl = "true".equals(Game.getMap().getTileProperty(tileIDtl, "blocked", "def"));
		
		int tileIDtr = Game.getMap().getTileId((int)(x+32)/32,(int)y/32,Game.getObjLayerIdx());
		blockedtr = "true".equals(Game.getMap().getTileProperty(tileIDtr, "blocked", "def"));
		
		int tileIDbl = Game.getMap().getTileId((int)x/32,(int)(y+32)/32,Game.getObjLayerIdx());
		blockedbl = "true".equals(Game.getMap().getTileProperty(tileIDbl, "blocked", "def"));
		
		int tileIDbr = Game.getMap().getTileId((int)(x+32)/32,(int)(y+32)/32,Game.getObjLayerIdx());
		blockedbr = "true".equals(Game.getMap().getTileProperty(tileIDbr, "blocked", "def"));
		
		if(damageInvincible) {
			t+=delta;
			if(t>2000) {
				damageInvincible=false;
				changeAnimationUsingFacing(facing, null);
			}
		}
		
		if(changeFacingTimer) {
			tt+=delta;
			if(tt>80) {
				changeFacingTimer=false;				
			}
		}
		
		if(changeDirectionTimer) {
			ttt+=delta;
			if(ttt>50) {
				changeDirectionTimer=false;
				if(damageInvincible) {
					changeAnimationUsingFacing(facing, damagedAns);
				}else {
					changeAnimationUsingFacing(facing, null);
				}
			}
		}
		
		if(!gc.isPaused()) {
			float prevX = x, prevY = y;
			
			if(input.isKeyDown(Input.KEY_LSHIFT)) {
				if(input.isKeyDown(Input.KEY_UP) && !changeDirectionTimer) {					
					if(input.isKeyDown(Input.KEY_LEFT) && !changeDirectionTimer) {
						facing=7;
						changeDirectionTimer=true;
						ttt=0;
					}else if(input.isKeyDown(Input.KEY_RIGHT) && !changeDirectionTimer) {
						facing=1;
						changeDirectionTimer=true;
						ttt=0;
					}else {
						facing=0;
						changeDirectionTimer=true;
						ttt=0;
					}
				}else 
				if(input.isKeyDown(Input.KEY_DOWN) && !changeDirectionTimer) {
					if(input.isKeyDown(Input.KEY_LEFT) && !changeDirectionTimer) {
						facing=5;
						changeDirectionTimer=true;
						ttt=0;
					}else if(input.isKeyDown(Input.KEY_RIGHT) && !changeDirectionTimer) {
						facing=3;
						changeDirectionTimer=true;
						ttt=0;
					}else {
						facing=4;
						changeDirectionTimer=true;
						ttt=0;
					}
				}else
				if(input.isKeyDown(Input.KEY_LEFT) && !changeDirectionTimer) {
					facing=6;
					changeDirectionTimer=true;
					ttt=0;
				}else
				if(input.isKeyDown(Input.KEY_RIGHT) && !changeDirectionTimer) {
					facing=2;
					changeDirectionTimer=true;
					ttt=0;
				}
									
			}else {
				if(input.isKeyDown(Input.KEY_UP)) {
					if(y>5 && !blockedtl && !blockedtr) {
						y-= delta * .1f;
					}else {
	//					y+= .1f;
	//					changeDir=false;
					}
				}
				if(input.isKeyDown(Input.KEY_DOWN)) {
					if(y<(Main.getScreenheight()-height)-5 && !blockedbl && !blockedbr) {
						y+= delta * .1f;
					}else {
	//					y-= .1f;
	//					changeDir=false;
					}
				}
				if(input.isKeyDown(Input.KEY_LEFT)) {
					if(x>5 && !blockedtl && !blockedbl) {
						x-= delta * .1f;
					}else {
	//					x+= .1f;
	//					changeDir=false;
					}
				}
				if(input.isKeyDown(Input.KEY_RIGHT)) {
					if(x<(Main.getScreenwidth()-width)-5 && !blockedtr && !blockedbr) {
						x+= delta * .1f;
					}else {
	//					x-= .1f;
	//					changeDir=false;
					}
				}
				
				if(x-prevX!=0 || y-prevY!=0 && !changeFacingTimer) {
					facing=newFacing(x,y,prevX,prevY);
					if(damageInvincible) {						
						changeAnimationUsingFacing(facing, damagedAns);
					}else {
						changeAnimationUsingFacing(facing, null);
					}
					changeFacingTimer=true;
					tt=0;
				}
				
//				if(!changeDirectionTimer) {
//					int prevFacing=facing;
//					int newFacing= newFacing(x,y,prevX,prevY);
//
//					if(prevFacing!=newFacing){
//						changeDirectionTimer=true;
//						ttt=0;
//					}
//				}
//				
//				if(!changeFacingTimer) {
//					int prevFacing=facing;
//					int newFacing= newFacing(x,y,prevX,prevY);
//					if(prevFacing!=newFacing){
//						facing=newFacing;
//						System.out.println("Facing : " + facing);
//						if(damageInvincible) {
//							changeAnimationUsingFacing(facing, damagedAns);
//						}else {
//							changeAnimationUsingFacing(facing, null);
//						}
//						changeFacingTimer=true;
//						tt=0;
//					}
//				}

//				if(prevFacing!=newFacing){
//			
//				if((x-prevX !=0 || y-prevY != 0) && changeDir && !changeAnimationTimer) {
//					if(!damageInvincible) {
//						changeAnimationUsingFacing(x,y,prevX,prevY,null);
//					}else {
//						changeAnimationUsingFacing(x,y,prevX,prevY,damagedAns);
//					}
//					changeAnimationTimer=true;
//					tt=0;
//				}
//			}
			}
		
		
			if(mana<100) {
				mana+=manaRegen;
			}
		}
	}
	
	public void gotHit(float damage) {
		if(!damageInvincible) {
			if(health-damage<0) {
				health=0;
				dead=true;
				LevelHandler.checkHighScoreAndWrite();
			}else {
				health-=damage;
				damageInvincible = true;
				changeAnimationUsingFacing(facing, damagedAns);
			}
			t=0;
		}
	}

	public void reset() {
		x=(Main.getScreenwidth()/2)-16;
		y=(Main.getScreenheight()/2)-16;
		facing=4;
		currAn=movdownAn;
		health=maxHealth;
		mana=maxMana;
	}
	
	public float getHealth() {
		return health;
	}
	
	public void setHealth(float h) {
		health = h;
	}
	
	public float getMana() {
		return mana;
	}
	
	public void setMana(float m) {
		mana = m;
	}

	public boolean isDead() {
		return dead;
	}

	public void setDead(boolean dead) {
		this.dead = dead;
	}	
}
