package GameObjects.Enemy;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import HelperClasses.CoordAndDirectionHelper;
import HelperClasses.ImageHelper;
import game.Game;
import game.LevelHandler;

public class Demon extends Enemy{
	
	private Vector2f wizVec, destVec, currVec;
	private boolean reachedDest=false, moveTimer=true;
	private int t, moveTime=3000;
	
	public Demon(float x, float y){
		super(x, y, 32, 32, 1, 15, 15, .02f);
		init();
		active=false;
	}
	
	@Override
	public void activate() {
//		float[] coords = CoordAndDirectionHelper.getRandOffScreenCoords();
//		activate(coords[0], coords[1]);
		activate(180, 265);
	}
	
	@Override
	public void activate(float x,float y) {
		super.activate(x, y);
		spawnT=0;
	}
	
	@Override
	public void init(){
		try {
			movupAn = ImageHelper.initAnimation(new String[]{"res/enemy sprites/demon_back_1.png", "res/enemy sprites/demon_back_2.png"}, 
					new int[] {500,500},0, false, false);
			movdownAn = ImageHelper.initAnimation(new String[]{"res/enemy sprites/demon_front_1.png", "res/enemy sprites/demon_front_2.png"}, 
					new int[] {500,500},0, false, false);
			movleftAn = ImageHelper.initAnimation(new String[]{"res/enemy sprites/demon_left_1.png", "res/enemy sprites/demon_left_2.png"}, 
					new int[] {500,500},0, false, false);
			movrightAn = ImageHelper.initAnimation(new String[]{"res/enemy sprites/demon_right_1.png", "res/enemy sprites/demon_right_2.png"}, 
					new int[] {500,500},0, false, false);
			
			upLeftAn=movleftAn;
			downLeftAn=movleftAn;
			upRightAn=movrightAn;
			downRightAn=movrightAn;
			
			spawnAn = ImageHelper.initAnimation(new String[] {"res/enemy sprites/skeleton_spawn_1.png", "res/enemy sprites/skeleton_spawn_1.png"}, 
								new int[] {500,500},0, false, false);
			
			wizVec = new Vector2f(Game.getWiz().getX(), Game.getWiz().getY());
			destVec  = new Vector2f(Game.getWiz().getX(), Game.getWiz().getY());
			currVec = new Vector2f(x,y);
			
			
			soundEffects = new Sound[]{};
		} catch (SlickException e) {
			e.printStackTrace();
		}
		currAn = movdownAn;
	}
	
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) {
		if(active) {
			if(moveTimer) {
				t+=delta; 
				if(t>moveTime) {
					moveTimer=false;
					chooseDest();
//					slopeToWiz = CoordAndDirectionHelper.slope(wizVec.x, wizVec.y, x, y);
				}
			}else {						
				if(!reachedDest) {					
					reachedDest=CoordAndDirectionHelper.inBox(destVec, x, y, 0);

				}else {
					moveTimer=true;
					reachedDest=false;
					t=0;
				}
			}
			
//			if(spawn) {
//				spawnT+=delta;
//				if(spawnT>3000) {
//					spawn = false;
//					currAn = movdownAn;
//				}
//			}else {
//				
//				int prevFacing=facing;
//				int newFacing= newFacing(x,y,prevX,prevY);
//				facing=newFacing;
//				if(prevFacing!=newFacing){
//					changeAnimationUsingFacing(newFacing, null);
//				}
//			}
		}
		
		if(health<=0) {
			deactivate();
			//LevelHandler.demonDead();
		}
	}
	
	public void chooseDest() {
		wizVec.x=Game.getWiz().getX(); wizVec.y=Game.getWiz().getY();
		currVec.x=x; currVec.y=y;
		if(wizVec.x==currVec.x) {
			currVec.x+=.000001f;
		}
	}
	
	@Override
	public void startSounds() {}

}
