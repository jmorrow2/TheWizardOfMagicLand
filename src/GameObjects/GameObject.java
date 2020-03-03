package GameObjects;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;

import game.Main;

public class GameObject extends Rectangle{
	
	protected Sound[] soundEffects;
	protected Animation currAn, movupAn, movdownAn, movleftAn, movrightAn, upLeftAn, downLeftAn, upRightAn, downRightAn, 	
						spawnAn, deathAn; 
	protected boolean active, damageInvincible=false;
	protected int facing;
	
	public GameObject(float x, float y, float width, float height) {
		super(x, y, width, height);	
	}
	
	public void init() {};
	
	public void render(GameContainer gc, Graphics g) {
		if(active && currAn!=null) {
    		currAn.draw(x,y);
    	}
	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta) {}
	
	public void stopAnimation() {
		if(currAn!=null) {
			currAn.stop();
		}
	}
	
	public void startAnimation() {
		if(currAn!=null) {
			currAn.start();
		}
	}
	
	public void changeAnimationUsingFacing(int dir, Animation[] conditionalAnimations) {
		if(conditionalAnimations==null) {
			switch (dir) {
		    	case 0: currAn = movupAn;
		    		break;
		    	case 1: currAn = upRightAn;
					break;
		    	case 2: currAn = movrightAn;
		    		break;
		    	case 3: currAn = downRightAn;
					break;
		    	case 4: currAn = movdownAn;
		    		break;
		    	case 5: currAn = downLeftAn;
					break;
		    	case 6: currAn = movleftAn;
		    		break;
		    	case 7: currAn = upLeftAn;
					break;
			}
		}else {
			switch (dir) {
	    	case 0: currAn = conditionalAnimations[0];
	    		break;
	    	case 1: currAn = conditionalAnimations[1];
				break;
	    	case 2: currAn = conditionalAnimations[2];
	    		break;
	    	case 3: currAn = conditionalAnimations[3];
				break;
	    	case 4: currAn = conditionalAnimations[4];
	    		break;
	    	case 5: currAn = conditionalAnimations[5];
				break;
	    	case 6: currAn = conditionalAnimations[6];
	    		break;
	    	case 7: currAn = conditionalAnimations[7];
				break;
			}
		}
	}
	
	public int newFacing(float x, float y, float prevX, float prevY) {
		int newFacing=facing;
			if(prevX<x) {
				if(prevY<y) {
					newFacing = 3;
				}else
				if(prevY>y) {
					newFacing = 1;
				}else{
					newFacing = 2;
				}
			}else
			if(prevX>x) {
				if(prevY<y) {
					newFacing = 5;
				}else
				if(prevY>y) {
					newFacing = 7;
				}else{
					newFacing = 6;
				}
			}else
			if(prevX==x) {
				if(prevY<y) {
					newFacing = 4;
				}else
				if(prevY>y) {
					newFacing = 0;
				}
			}
		return newFacing;
	}
	
	public void adjustRectangularObj() {
		if(currAn!=null) {
	    	width=currAn.getWidth();
	    	height=currAn.getHeight();
		}
	}
	
	public boolean isOffScreen() {
		if(x+width<=0 || x>Main.getScreenwidth() || y+height<=0 || y>Main.getScreenheight()) {
			return true;
		}
		return false;
	}
	
	public void startSounds() {
		if(soundEffects!=null) {
			for(Sound s : soundEffects) {
				s.play();
			}
		}
	}
	
	public void stopAllSounds() {
		if(soundEffects!=null) {
			for(Sound s : soundEffects) {
				s.stop();
			}
		}
	}
	
	public float lerpDirection(float start, float end, float speed) {
	    return (1 - speed) * start + speed * end;
	}	
	
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
	public Animation getCurrAn() {
		return currAn;
	}

	public void setCurrAn(Animation currAn) {
		this.currAn = currAn;
	}
	
	public int getFacing() {
		return facing;
	}
	
}
