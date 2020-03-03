package HelperClasses;

import java.util.Random;

import org.newdawn.slick.geom.Vector2f;

import game.Main;

public class CoordAndDirectionHelper {
	
	private static Random rand = new Random();
	
	public static float[] getRandScreenCoords(float width, float height) {
		float[] ret = new float[2];
		ret[0] = rand.nextInt(Main.getScreenwidth()-(int)width);
		ret[1] = rand.nextInt(Main.getScreenheight()-(int)height);
		return ret;
	}
	
	public static float[] getRandOffScreenCoords() {
		float[] ret = new float[2];
		int rx = rand.nextInt(2), ry = rand.nextInt(2);
		ret[0] = rx==0 ? rand.nextInt(100)*-1 : rand.nextInt(((Main.getScreenwidth()+100)-Main.getScreenwidth()) +  1) + Main.getScreenwidth();
		ret[1] = ry==0 ? rand.nextInt(100)*-1 : rand.nextInt(((Main.getScreenheight()+100)-Main.getScreenheight()) +  1) + Main.getScreenheight();
		return ret;
	}
	
	public static Random getRand() {
		return rand;
	}
	
	public static float slope(float x1, float y1, float x2, float y2){
        float deltaX = x1 - x2;
        float deltaY = y1 - y2;
        if(deltaX==0) {
        	deltaX+=.00001f;
        }
        return deltaY / deltaX;	    
	}
	
	public static boolean isInsideTriangle(int x1, int y1, int x2, int y2, int x3, int y3, int checkX, int checkY) {    
	    return (areaOfTriangle (x1, y1, x2, y2, x3, y3) == 
	    		areaOfTriangle (checkX, checkY, x2, y2, x3, y3) + 
	    		areaOfTriangle (x1, y1, checkX, checkY, x3, y3) + 
	    		areaOfTriangle (x1, y1, x2, y2, checkX, checkY)); 
	} 
	
	public static int areaOfTriangle(int x1, int y1, int x2, int y2, int x3, int y3) { 
		return  (int)Math.abs((x1*(y2-y3) + x2*(y3-y1)+ 
		        x3*(y1-y2))/2.0); 
	}
	
	public static boolean inBox(Vector2f vec, float x, float y, int offset) {
		return x>vec.x-offset && x<vec.x+offset &&
				y>vec.y-offset && y<vec.y+offset;
	}
}
