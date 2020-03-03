package HelperClasses;

import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;

public class MusicHandler {
	
	    //Music is the background music
		//SOUND FILES MUST BE SIGNED 16-BIT WAV FILES
		private static Music music;
		
		public static void playMusic(String filePath) {
			try {
				music = new Music(filePath);
				music.loop();
				music.setVolume(.05f);
			} catch (SlickException e) {
				e.printStackTrace();
			}
			
		}
		
		

}
