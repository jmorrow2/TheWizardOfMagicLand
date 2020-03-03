package GameObjects.Magic;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.state.StateBasedGame;

import GameObjects.Enemy.Enemy;
import HelperClasses.ImageHelper;
import game.Game;

public class Waterjet extends Magic{
      
	private int position, offset, wizPrevFacing;
	private float diagnalReposition=4.6f;
	private Animation movupEvenAn, movdownEvenAn, movleftEvenAn, movrightEvenAn, upLeftEvenAn, upRightEvenAn, downLeftEvenAn, downRightEvenAn,
	movupOddAn, movdownOddAn, movleftOddAn, movrightOddAn, upLeftOddAn, upRightOddAn, downLeftOddAn, downRightOddAn,
	movupFrontAn, movdownFrontAn, movleftFrontAn, movrightFrontAn, upLeftFrontAn, upRightFrontAn, downLeftFrontAn, downRightFrontAn;
	private Animation[] evenAns, oddAns, frontAns;
			
    public Waterjet(int position){
    	super(0, 0, 16, 16, 2, .01, 0, .3, 200);
    	this.position = position;
    	this.offset = (position+1)*16;
    	init();
    }
    
    @Override
    public void activate(float x, float y, int dir) {
    	super.activate(x, y, dir);
    	wizPrevFacing=dir;
    	if(position==Game.getWaterjetCurrMax()-1) {
    		changeAnimationUsingFacing(dir, frontAns);
    	}else if(position%2==0) {
    		changeAnimationUsingFacing(dir, evenAns);
    	}else {
    		changeAnimationUsingFacing(dir, oddAns);
    	}
    }
    
    @Override
    public void init() {
    	try {
    		String[] imgsEven = new String[] {"res/magic sprites/waterjet_even_1.png", "res/magic sprites/waterjet_even_2.png"};
    		String[] imgsOdd = new String[] {"res/magic sprites/waterjet_odd_1.png", "res/magic sprites/waterjet_odd_2.png"};
    		String[] imgsFront = new String[] {"res/magic sprites/waterjet_front_1.png", "res/magic sprites/waterjet_front_2.png"};
    		int[] duration = new int[] {200,200};
			movupEvenAn = ImageHelper.initAnimation(imgsEven, duration,180, false, false);
			movdownEvenAn = ImageHelper.initAnimation(imgsEven, duration,0, false, false);
			movleftEvenAn = ImageHelper.initAnimation(imgsEven, duration,90, false, false);
			movrightEvenAn = ImageHelper.initAnimation(imgsEven, duration, -90, false, false);
			upLeftEvenAn = ImageHelper.initAnimation(imgsEven, duration, 135, false, false);
			upRightEvenAn = ImageHelper.initAnimation(imgsEven, duration, -135, false, false);
			downLeftEvenAn = ImageHelper.initAnimation(imgsEven, duration, 45, false, false);
			downRightEvenAn = ImageHelper.initAnimation(imgsEven, duration,-45, false, false);
			
			movupOddAn = ImageHelper.initAnimation(imgsOdd, duration,180, false, false);
			movdownOddAn = ImageHelper.initAnimation(imgsOdd, duration,0, false, false);
			movleftOddAn = ImageHelper.initAnimation(imgsOdd, duration,90, false, false);
			movrightOddAn = ImageHelper.initAnimation(imgsOdd, duration, -90, false, false);
			upLeftOddAn = ImageHelper.initAnimation(imgsOdd, duration, 135, false, false);
			upRightOddAn = ImageHelper.initAnimation(imgsOdd, duration, -135, false, false);
			downLeftOddAn = ImageHelper.initAnimation(imgsOdd, duration, 45, false, false);
			downRightOddAn = ImageHelper.initAnimation(imgsOdd, duration,-45, false, false);
			
			movupFrontAn = ImageHelper.initAnimation(imgsFront, duration,180, false, false);
			movdownFrontAn = ImageHelper.initAnimation(imgsFront, duration,0, false, false);
			movleftFrontAn = ImageHelper.initAnimation(imgsFront, duration,90, false, false);
			movrightFrontAn = ImageHelper.initAnimation(imgsFront, duration, -90, false, false);
			upLeftFrontAn = ImageHelper.initAnimation(imgsFront, duration, 135, false, false);
			upRightFrontAn = ImageHelper.initAnimation(imgsFront, duration, -135, false, false);
			downLeftFrontAn = ImageHelper.initAnimation(imgsFront, duration, 45, false, false);
			downRightFrontAn = ImageHelper.initAnimation(imgsFront, duration,-45, false, false);
    		
			evenAns = new Animation[8];
			evenAns[0] = movupEvenAn;
			evenAns[1] = upRightEvenAn;
			evenAns[2] = movrightEvenAn;
			evenAns[3] = downRightEvenAn;
			evenAns[4] = movdownEvenAn;
			evenAns[5] = downLeftEvenAn;
			evenAns[6] = movleftEvenAn;
			evenAns[7] = upLeftEvenAn;
			
			oddAns = new Animation[8];
			oddAns[0] = movupOddAn;
			oddAns[1] = upRightOddAn;
			oddAns[2] = movrightOddAn;
			oddAns[3] = downRightOddAn;
			oddAns[4] = movdownOddAn;
			oddAns[5] = downLeftOddAn;
			oddAns[6] = movleftOddAn;
			oddAns[7] = upLeftOddAn;
			
			frontAns = new Animation[8];
			frontAns[0] = movupFrontAn;
			frontAns[1] = upRightFrontAn;
			frontAns[2] = movrightFrontAn;
			frontAns[3] = downRightFrontAn;
			frontAns[4] = movdownFrontAn;
			frontAns[5] = downLeftFrontAn;
			frontAns[6] = movleftFrontAn;
			frontAns[7] = upLeftFrontAn;
    		
    		soundEffects = new Sound[]{};
    	}catch (SlickException e) {
    		e.printStackTrace();
    	}
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta){
        if(active){        	
        	int wizCurrFacing=Game.getWiz().getFacing();
        	
        	switch(wizCurrFacing) {
        	case 0: this.y=Game.getWiz().getY() - offset;
        		break;
        	case 1: this.x=Game.getWiz().getX() + offset+16-((position+1)*diagnalReposition); this.y=Game.getWiz().getY() - offset+((position+1)*diagnalReposition);
    			break;
        	case 2: this.x=Game.getWiz().getX() + offset+16;
        		break;
        	case 3: this.x=Game.getWiz().getX() + offset+16-((position+1)*diagnalReposition); this.y=Game.getWiz().getY() + offset+16-((position+1)*diagnalReposition);
        		break;
        	case 4: this.y=Game.getWiz().getY() + offset+16;
        		break;
        	case 5: this.x=Game.getWiz().getX() - offset+((position+1)*diagnalReposition); this.y=Game.getWiz().getY() + offset+16-((position+1)*diagnalReposition);
				break;
        	case 6 : this.x=Game.getWiz().getX() - offset;
        		break;
        	case 7: this.x=Game.getWiz().getX() - offset+((position+1)*diagnalReposition); this.y=Game.getWiz().getY() - offset+((position+1)*diagnalReposition);
				break;
        	}
        	
        	if(wizPrevFacing!=wizCurrFacing) {
	        	if(position==Game.getWaterjetCurrMax()-1) {
	        		changeAnimationUsingFacing(wizCurrFacing, frontAns);
	        	}else if(position%2==0){
	        		changeAnimationUsingFacing(wizCurrFacing, evenAns);
	        	}else {
	        		changeAnimationUsingFacing(wizCurrFacing, oddAns);
	        	}
        	}
        	
            lived+=delta;
            if(lived>MAX_LIFETIME || isOffScreen()) {
            	deactivate();
            }
        }
    }
    
    @Override
    public void deactivate() {
    	super.deactivate();
    }    
    
    @Override
    public void hitEnemy(Enemy e, int delta) {
		super.hitEnemy(e, delta);
		if(position<Game.getWaterjetCurrMax()-1) {
			Game.setWaterjetCurrMax(Game.getWaterjetCurrMax()-1);
		}
	}
    
}
