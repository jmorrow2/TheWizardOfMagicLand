package GameObjects.Enemy;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import HelperClasses.CoordAndDirectionHelper;
import HelperClasses.ImageHelper;
import game.Game;
import game.LevelHandler;
import game.Main;

public class Bat extends Enemy{
	
	private Vector2f wizVec, landVec, currVec;
	private int t=0, tt=0, landBoxDist=250, perchingTime=4000;
	private boolean perchedTimer=true, screechTimer=true, xDir, yDir, reachedWizBox=false, reachedLandBox=false;
	private Animation perchedAn;
	private float slopeToWiz;
	
	public Bat(float x, float y){
		super(x, y, 32, 32, 0, 15, 15, .4f);
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
		this.x = x; this.y=y;
		this.active=true;
		this.perchedTimer=true;
		this.screechTimer=true;
		health=maxHealth;
		t=0;tt=0;
	}
	
	@Override
	public void init(){
		try {
			int[] duration = new int[] {100,100,100};
			
			Image spriteSheet = new Image("res/enemy sprites/bat_sprite_sheet.png");
			movupAn = new Animation(ImageHelper.getSubImagesFromSpriteSheet(spriteSheet,64,32,32),duration, true);
			movdownAn = new Animation(ImageHelper.getSubImagesFromSpriteSheet(spriteSheet,0,32,32),duration, true);
			movleftAn = new Animation(ImageHelper.getSubImagesFromSpriteSheet(spriteSheet,96,32,32),duration, true);
			movrightAn = new Animation(ImageHelper.getSubImagesFromSpriteSheet(spriteSheet,32,32,32),duration, true);
			
//			perchedAn = ImageHelper.initAnimation(new String[]{"res/enemy sprites/bat_perched.png"}, 
//					new int[] {1000},0, false, false);
			
			perchedAn = ImageHelper.initAnimation(new String[]{"res/enemy sprites/bat_down_1.png"}, 
					new int[] {1000},0, false, false);
			
			wizVec = new Vector2f(Game.getWiz().getX(), Game.getWiz().getY());
			landVec = new Vector2f(wizVec.getTheta());
			currVec= new Vector2f(x, y);		
			
			soundEffects = new Sound[]{new Sound("res/sounds/enemy/bat_screech.wav"), new Sound("res/sounds/enemy/bat_wing_flap.wav")};
		} catch (SlickException e) {
			e.printStackTrace();
		}
		currAn = perchedAn;
	}
	
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) {		
		if(active) {
			if(health<=0) {
				LevelHandler.batDead();
				deactivate();
			}
			if(perchedTimer) {
				t+=delta; 
				if(screechTimer) {
					tt+=delta;
					if(tt>perchingTime-1000) {
						//screech sound
						soundEffects[0].play(1f, .18f);
						screechTimer=false;}
				}
				if(t>perchingTime) {
					//flapping sound
					soundEffects[1].loop(1f, .5f);
					perchedTimer=false;
					updateVectorsAndAnimation();
					slopeToWiz = CoordAndDirectionHelper.slope(wizVec.x, wizVec.y, x, y);
				}
			}else {		
				if(!reachedWizBox) {
					reachedWizBox=CoordAndDirectionHelper.inBox(wizVec, x, y, 8);
				}
				if(!reachedLandBox) {
					reachedLandBox=CoordAndDirectionHelper.inBox(landVec, x, y, 8);
				}
				
				if(!reachedWizBox) {					
					if(xDir) {
						x=(float) (x+(speed * Math.abs(Math.cos(Math.atan(slopeToWiz)))));
					}else {
						x=(float) (x-(speed * Math.abs(Math.cos(Math.atan(slopeToWiz)))));
					}
					
					if(yDir) {
						y=(float) (y+(speed * Math.abs(Math.sin(Math.atan(slopeToWiz)))));
					}else {
						y=(float) (y-(speed * Math.abs(Math.sin(Math.atan(slopeToWiz)))));
					}
					
					currVec.x=x; currVec.y=y;
					
				}else if(!reachedLandBox) {
					x=lerpDirection(currVec.x, landVec.x, .001f);
					y=lerpDirection(currVec.y, landVec.y, .001f);
					currVec.x=x; currVec.y=y;
				}else {
					perchedTimer=true;
					screechTimer=true;
					reachedWizBox=false;
					reachedLandBox=false;
					currAn=perchedAn;
					t=0; tt=0;
					soundEffects[0].stop();
					soundEffects[1].stop();
				}
			}
		}
	}

	public void updateVectorsAndAnimation() {
		wizVec.x=Game.getWiz().getX(); wizVec.y=Game.getWiz().getY();
		currVec.x=x; currVec.y=y;
		if(wizVec.x==currVec.x) {
			currVec.x+=.000001f;
		}
		
		float slope = (wizVec.y-currVec.y)/(wizVec.x-currVec.x);
		
		boolean inUpTriangle = CoordAndDirectionHelper.isInsideTriangle((int)currVec.x, (int)currVec.y, (int)currVec.x-150, 0, (int)currVec.x+150, 0, (int)wizVec.x, (int)wizVec.y),
				inDownTriangle = CoordAndDirectionHelper.isInsideTriangle((int)currVec.x, (int)currVec.y, (int)currVec.x-150, (int)Main.getScreenheight(), (int)currVec.x+150, (int)Main.getScreenheight(), (int)wizVec.x, (int)wizVec.y);
		
		if(wizVec.x<currVec.x && wizVec.y<currVec.y) { //up left
			xDir=false; yDir=false;
			
			landVec.x = (float) (wizVec.x - Math.abs(landBoxDist*Math.cos(Math.atan(slope))));
			landVec.y = (float) (wizVec.y - Math.abs(landBoxDist*Math.sin(Math.atan(slope))));
			if(inUpTriangle) {
				currAn=movupAn;
			}else {
				currAn=movleftAn;
			}
			
		}else if(wizVec.x>currVec.x && wizVec.y<currVec.y){ //up right
			xDir=true; yDir=false;
			
			landVec.x = (float) (wizVec.x + Math.abs(landBoxDist*Math.cos(Math.atan(slope))));
			landVec.y = (float) (wizVec.y - Math.abs(landBoxDist*Math.sin(Math.atan(slope))));
			if(inUpTriangle) {
				currAn=movupAn;
			}else {
				currAn=movrightAn;
			}
			
		}else if(wizVec.x>currVec.x && wizVec.y>currVec.y){ //down right
			xDir=true; yDir=true;
			
			landVec.x = (float) (wizVec.x + Math.abs(landBoxDist*Math.cos(Math.atan(slope))));
			landVec.y = (float) (wizVec.y + Math.abs(landBoxDist*Math.sin(Math.atan(slope))));
			if(inDownTriangle) {
				currAn=movdownAn;
			}else {
				currAn=movrightAn;
			}
			
		}else if(wizVec.x<currVec.x && wizVec.y>currVec.y){//down left
			xDir=false; yDir=true;
			
			landVec.x = (float) (wizVec.x - Math.abs(landBoxDist*Math.cos(Math.atan(slope))));
			landVec.y = (float) (wizVec.y + Math.abs(landBoxDist*Math.sin(Math.atan(slope))));
			if(inDownTriangle) {
				currAn=movdownAn;
			}else {
				currAn=movleftAn;
			}
		}
	}
	
	@Override
	public void startSounds() {
		if(!perchedTimer) {	
			soundEffects[1].loop(1f, .5f);
		}
		if(!screechTimer) {
			soundEffects[0].loop(1f, .18f);
		}
	}
	
//	public boolean inLandBox() {
//		return x>landVec.x-8 && x<landVec.x+8 &&
//				y>landVec.y-8 && y<landVec.y+8;
//	}
//	
//	public boolean inWizBox() {
//		return x>wizVec.x-8 && x<wizVec.x+8 &&
//				y>wizVec.y-8 && y<wizVec.y+8;
//	}
}
