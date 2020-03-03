package HelperClasses;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.Cipher;

import game.LevelHandler;

public class FileHelper {
	
	public static int getHighScoreFromFile() {
		int highscore = 0;
		File file = new File("res/high score.txt"); 
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(file));
			String st; 
				while ((st = br.readLine()) != null) {
					highscore = Integer.parseInt(st.trim());
				  }
		} catch (IOException | NumberFormatException e) {
			e.printStackTrace();
		}	  
		return highscore;
	}
	
	public static void writeHighScoreToFile(int newHighScore) {
		  FileWriter fileWriter;
			try {
				fileWriter = new FileWriter("res/high score.txt");
				fileWriter.write(Integer.toString(newHighScore));
				 fileWriter.close();
			} catch (IOException | NumberFormatException e) {
				e.printStackTrace();
			}	
	}

}
