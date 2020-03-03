package GameObjects.Enemy;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import GameObjects.GameObject;
import game.Game;

public class Enemy extends GameObject{
	
	protected int type, damage, spawnT=0;
	protected float health, maxHealth;
	protected boolean spawn=false, gotHit=false;
	protected float speed;

	public Enemy(float x, float y, float width, float height, int type, int damage, float health, float speed) {
		super(x, y, width, height);
		this.type = type;
		this.damage = damage;
		this.health = health;
		this.maxHealth = health;
		this.speed = speed;
	}
	
	public void activate(float x,float y) {
		this.x = x; this.y=y;
		this.active=true;
		health=maxHealth;
		this.spawn=true;
		startSounds();
	}
	
	public void activate() {}
	
	@Override
	public void render(GameContainer gc, Graphics g) {
		if(active && currAn!=null) {
    		currAn.draw(x,y);
    		if(Game.isShowEnemyHealth() && gotHit) {
	    		g.setColor(Color.red);
	    		g.drawRoundRect(x-(Math.abs((width-33))/2), y-15, 33, 6, 15);
	    		g.fillRoundRect(x-(Math.abs((width-33))/2), y-15, 33 * (health/maxHealth), 6, 15);
    		}
    	}
	}
	
	public void gotHit(double damage) {
		if(!spawn) {
			health-=damage;
			gotHit=true;
		}
	}
	
	public void deactivate() {
		this.active=false;
		gotHit=false;
		stopAllSounds();
	}
	
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public float getHealth() {
		return health;
	}

	public void setHealth(float health) {
		this.health = health;
	}

	public boolean isSpawn() {
		return spawn;
	}

	public void setSpawn(boolean spawn) {
		this.spawn = spawn;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

}
