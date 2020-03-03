package GameObjects.Magic;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import GameObjects.GameObject;
import GameObjects.GameObject;
import GameObjects.Enemy.Enemy;
import game.Game;

public class Magic extends GameObject{
	
	protected int type, dir, lived=0, MAX_LIFETIME;
	protected double speed, damage;

	public Magic(float x, float y, float width, float height, int type, double damage, int dir, double speed, int maxLifeTime) {
		super(x, y, width, height);
		this.type = type;
		this.damage = damage;
		this.dir = dir;
		this.speed = speed;
		this.MAX_LIFETIME = maxLifeTime;
	}
	
	public void activate(float x, float y, int dir) {
    	active = true;
		this.x = x;
		this.y = y;
		this.dir = dir;
		lived=0;
		adjustXYforCenterShot(dir);
	}
	
	public void deactivate() {
		this.active = false;
	}
	
	public void adjustXYforCenterShot(int dir) {
			switch (dir) {
		    	case 0: this.x-=((width-Game.getWiz().getWidth())/2); this.y-=height;
		    		break;
		    	case 1: this.x+=Game.getWiz().getWidth(); this.y-=height;
					break;
		    	case 2: this.x+=Game.getWiz().getWidth(); this.y-=(height-Game.getWiz().getHeight())/2;
		    		break;
		    	case 3: this.x+=Game.getWiz().getWidth(); this.y+=Game.getWiz().getHeight();
					break;
		    	case 4: this.x-=(width-Game.getWiz().getWidth())/2; this.y+=Game.getWiz().getHeight();
		    		break;
		    	case 5: this.x-=width; this.y+=Game.getWiz().getHeight();
					break;
		    	case 6: this.x-=width; this.y-=(height-Game.getWiz().getHeight())/2; 
		    		break;
		    	case 7: this.x-=width; this.y-=height;
					break;
			}
	}
	
	public void hitEnemy(Enemy e, int delta) {
		e.gotHit(typeDamageCalc(e));
	}
	
	// 0: neutral, 1: fire, 2: water, 3: earth, 4: wind
	public double typeDamageCalc(Enemy e) {
		double ret = damage;
		
		switch(e.getType()) {
			case 0: 
				break;
	    	case 1 : switch(type) {
				    	case 1 : ret=0;
				    		break;
				    	case 2 : ret*=2;
				    		break;
				    	case 3 : ret/=2;
				    		break;
				    	case 4 : 
				    		break;
	    				}
	    		break;
	    	case 2 :  switch(type) {
				    	case 1 : ret/=2;
				    		break;
				    	case 2 : ret=0;
				    		break;
				    	case 3 : 
				    		break;
				    	case 4 : ret*=2;
				    		break;
						}
	    		break;
	    	case 3 :  switch(type) {
				    	case 1 : ret*=2;
				    		break;
				    	case 2 :
				    		break;
				    	case 3 : ret=0;
				    		break;
				    	case 4 : ret/=2;
				    		break;
						}
	    		break;
	    	case 4 : switch(type) {
				    	case 1 : 
				    		break;
				    	case 2 : ret/=2;
				    		break;
				    	case 3 : ret*=2;
				    		break;
				    	case 4 : ret=0;
				    		break;
						}
	    		break;
		}
		return ret;
	}
	
	public void move(int delta) {
		switch (dir) {
	    	case 0: y-= delta * speed;
	    		break;
	    	case 1: x+= delta * speed; y-= delta * speed;
				break;
	    	case 2: x+= delta * speed;
	    		break;
	    	case 3: x+= delta * speed; y+= delta * speed;
				break;
	    	case 4: y+= delta * speed;
	    		break;
	    	case 5: x-= delta * speed; y+= delta * speed;
				break;
	    	case 6: x-= delta * speed;
	    		break;
	    	case 7: x-= delta * speed; y-= delta * speed;
				break;
		} 
	}
	
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public double getDamage() {
		return damage;
	}

	public void setDamage(double damage) {
		this.damage = damage;
	}

	public int getDir() {
		return dir;
	}

	public void setDir(int dir) {
		this.dir = dir;
	}

}
