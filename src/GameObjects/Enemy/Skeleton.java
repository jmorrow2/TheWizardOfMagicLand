package GameObjects.Enemy;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.state.StateBasedGame;

import HelperClasses.CoordAndDirectionHelper;
import HelperClasses.ImageHelper;
import game.Game;
import game.LevelHandler;

public class Skeleton extends Enemy{
	
	public Skeleton(float x, float y){
		super(x, y, 32, 32, 0, 15, 15, .04f);
		init();
		active=false;
	}
	
	@Override
	public void activate() {
		float[] coords = CoordAndDirectionHelper.getRandScreenCoords(width, height);
		activate(coords[0], coords[1]);
	}
	
	@Override
	public void activate(float x,float y) {
		super.activate(x, y);
		currAn = spawnAn;
		spawnT=0;
	}
	
	@Override
	public void init(){
		try {
			movupAn = ImageHelper.initAnimation(new String[]{"res/enemy sprites/skeleton_back_1.png", "res/enemy sprites/skeleton_back_2.png"}, 
								new int[] {500,500},0, false, false);
			movdownAn = ImageHelper.initAnimation(new String[]{"res/enemy sprites/skeleton_front_1.png", "res/enemy sprites/skeleton_front_2.png"}, 
								new int[] {500,500},0, false, false);
			movleftAn = ImageHelper.initAnimation(new String[]{"res/enemy sprites/skeleton_left_1.png", "res/enemy sprites/skeleton_left_2.png"}, 
								new int[] {500,500},0, false, false);
			movrightAn = ImageHelper.initAnimation(new String[]{"res/enemy sprites/skeleton_right_1.png", "res/enemy sprites/skeleton_right_2.png"}, 
								new int[] {500,500},0, false, false);
			
//			upLeftAn = ImageHelper.initAnimation(new String[]{"res/enemy sprites/skeleton_left_1.png", "res/enemy sprites/skeleton_left_2.png"}, 
//					new int[] {500,500},45, false, false);
//			downLeftAn = ImageHelper.initAnimation(new String[]{"res/enemy sprites/skeleton_front_1.png", "res/enemy sprites/skeleton_front_2.png"}, 
//					new int[] {500,500},45, false, false);
//			upRightAn = ImageHelper.initAnimation(new String[]{"res/enemy sprites/skeleton_right_1.png", "res/enemy sprites/skeleton_right_2.png"}, 
//					new int[] {500,500},-45, false, false);
//			downRightAn = ImageHelper.initAnimation(new String[]{"res/enemy sprites/skeleton_front_1.png", "res/enemy sprites/skeleton_front_2.png"}, 
//					new int[] {500,500},-45, false, false);
			
			upLeftAn=movleftAn;
			downLeftAn=movleftAn;
			upRightAn=movrightAn;
			downRightAn=movrightAn;
			
			
			spawnAn = ImageHelper.initAnimation(new String[] {"res/enemy sprites/skeleton_spawn_1.png", "res/enemy sprites/skeleton_spawn_2.png"}, 
								new int[] {500,500},0, false, false);
			
			
			soundEffects = new Sound[]{new Sound("res/sounds/enemy/skeleton_digging.wav"), new Sound("res/sounds/enemy/skeleton_rattling_bones.wav")};
		} catch (SlickException e) {
			e.printStackTrace();
		}
		currAn = spawnAn;
	}
	
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) {		
		if(active) {
			if(spawn) {
				spawnT+=delta;
				if(spawnT>3000) {
					spawn = false;
					startSounds();
					currAn = movdownAn;
				}
			}else {
				
				float prevX=x, prevY=y;
				
				if(x<Game.getWiz().getX()) {
					x+= delta * speed;
				}
				if(x>Game.getWiz().getX()) {
					x-= delta * speed;
				}
				if(y>Game.getWiz().getY()) {
					y-= delta * speed;
				}
				if(y<Game.getWiz().getY()) {
					y+= delta * speed;
				}
				
				int prevFacing=facing;
				int newFacing= newFacing(x,y,prevX,prevY);
				facing=newFacing;
				if(prevFacing!=newFacing){
					changeAnimationUsingFacing(newFacing, null);
				}
			}	
		
		}
		
		if(health<=0) {
			LevelHandler.skeletonDead();
			deactivate();
		}
	}
	
	@Override
	public void startSounds() {
		if(spawn) {
			//cant set volume later, need to use
			//parameters pitch, volume
			//keep pitch at 1 to remain unchanged
			soundEffects[0].loop(1f, .2f);	
		}else {
			soundEffects[0].stop();
			soundEffects[1].loop(1f, .3f);
		}
	}
}
