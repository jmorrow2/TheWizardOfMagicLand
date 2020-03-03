package GameObjects.Magic;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.state.StateBasedGame;

import GameObjects.GameObject;
import GameObjects.Enemy.Enemy;
import HelperClasses.ImageHelper;

public class Tidalwave extends Magic{
    
    public Tidalwave() {
    	super(0, 0, 138, 55, 2, 10, 0, .2, 2000);
    	init();
    }
    
    @Override
    public void activate(float x, float y, int dir) {
    	changeAnimationUsingFacing(dir, null);
    	adjustRectangularObj();
    	super.activate(x, y, dir);
    }
    
    @Override
    public void init(){
    	try {
    		String[] imgs = new String[]{"res/magic sprites/tidalwave_1.png", "res/magic sprites/tidalwave_2.png"};
    		int[] duration = new int[] {300,300};
    		
    		movupAn = ImageHelper.initAnimation(imgs, duration,-90,false, false);
    		movdownAn = ImageHelper.initAnimation(imgs, duration,90,false, false);
    		
    		movleftAn = ImageHelper.initAnimation(imgs, duration,0, true, false);
    		upLeftAn = ImageHelper.initAnimation(imgs, duration,45, true, false);
    		downLeftAn = ImageHelper.initAnimation(imgs, duration,-45, true, false);
    		
    		movrightAn = ImageHelper.initAnimation(imgs, duration,0, false, false); 
    		upRightAn = ImageHelper.initAnimation(imgs, duration,-45,false, false);
			downRightAn = ImageHelper.initAnimation(imgs, duration,45,false, false);
		
			currAn=movleftAn;
			
			soundEffects = new Sound[]{};
		} catch (SlickException e) {
			e.printStackTrace();
		}
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta){
        if(active){ 
        	move(delta);
        	lived+=delta;
            if(lived>MAX_LIFETIME || isOffScreen()) {
            	deactivate();
            }     	
        } 
    }
}
