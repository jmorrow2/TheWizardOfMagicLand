package GameObjects.Magic;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.state.StateBasedGame;

import GameObjects.GameObject;
import GameObjects.Enemy.Enemy;
import HelperClasses.ImageHelper;

public class Bonfire extends Magic{
      
    public Bonfire(){
    	super(0, 0, 60, 61, 1, .02, 0, 0, 3000);
    	init();
    }
    
    @Override
    public void init() {
    	try {
    		currAn = ImageHelper.initAnimation(new String[]{"res/magic sprites/bonfire_1.png", "res/magic sprites/bonfire_2.png"}, 
					new int[] {300,300},0, false, false);
    		soundEffects = new Sound[]{};
    	}catch (SlickException e) {
    		e.printStackTrace();
    	}
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta){
        if(active){
            lived+=delta;
            if(lived>MAX_LIFETIME || isOffScreen()) {
            	deactivate();
            }
        }
    }
}
