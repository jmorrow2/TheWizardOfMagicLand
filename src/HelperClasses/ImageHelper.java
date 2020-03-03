package HelperClasses;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import game.Main;

public class ImageHelper {
	
	public static float[] centerImg(Image i) {
		float[] ret = new float[2];
		ret[0] = (Main.getScreenwidth()/2)-(i.getWidth()/2);
		ret[1] = (Main.getScreenheight()/2)-(i.getHeight()/2);
		return ret;
	}
	
	public static Animation initAnimation(String[] files, int[] duration, float angle, boolean flipXAxis, boolean flipYAxis) throws SlickException {	
		Image[] imgs = new Image[files.length];
		
		for(int i=0; i<files.length; i++) {
			imgs[i] = new Image(files[i], true);
		}
		if(flipXAxis || flipYAxis) {
			for(int i=0; i<files.length; i++) {
				imgs[i] = imgs[i].getFlippedCopy(flipXAxis, flipYAxis);
			}
		}		
		if(angle!=0) {
			for(int i=0; i<files.length; i++) {
				imgs[i].rotate(angle);
			}
		}

		return new Animation(imgs, duration, true);
	}
	
	public static Image[] getSubImagesFromSpriteSheet(Image spriteSheet, int y, int spriteWidth, int spriteHeight) {
		Image[] ret = new Image[spriteSheet.getWidth()/spriteWidth];
		int pos = 0;
		for(int i=0; i<spriteSheet.getWidth(); i+=32) {
			Image img = spriteSheet.getSubImage(i, y, spriteWidth, spriteHeight);
			ret[pos]=img;
			pos++;
		}
		return ret;
	}

}
