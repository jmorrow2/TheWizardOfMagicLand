package GameObjects.Magic;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.state.StateBasedGame;

import GameObjects.GameObject;
import GameObjects.Enemy.Enemy;
import HelperClasses.ImageHelper;

public class Fireball extends Magic{
    
	//Fireball is a 22x12 rectangle so we need to swap width and height
	//based on the direction
    public Fireball() {
    	//speed.35
    	super(0, 0, 0, 0, 1, 5, 0, .1, 8000);
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
			String[] imgs = new String[] {"res/magic sprites/fireball_1.png", "res/magic sprites/fireball_2.png", "res/magic sprites/fireball_3.png"};
			int[] duration = new int[] {200,200,200};
			movupAn = ImageHelper.initAnimation(imgs, duration,180, false, false);
			movdownAn = ImageHelper.initAnimation(imgs, duration,0, false, false);
			movleftAn = ImageHelper.initAnimation(imgs, duration,90, false, false);
			movrightAn = ImageHelper.initAnimation(imgs, duration, -90, false, false);
			upLeftAn = ImageHelper.initAnimation(imgs, duration, 135, false, false);
			upRightAn = ImageHelper.initAnimation(imgs, duration, -135, false, false);
			downLeftAn = ImageHelper.initAnimation(imgs, duration, 45, false, false);
			downRightAn = ImageHelper.initAnimation(imgs, duration,-45, false, false);
			
			currAn=movdownAn;
			
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
    
    @Override
    public void hitEnemy(Enemy e, int delta) {
    	super.hitEnemy(e, delta);
    	deactivate();
    }
}
