package game;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Vector;

public class FileManager {

	private String filename;
	
	
	public FileManager(){
		filename = "users.txt";
	}
	
	//returns true if username is in file
	//(for new user: show error message if return true, if not, add user)
	public boolean userExists(String username){
		boolean exists = false;
		
		try{
			FileReader fr= new FileReader(filename);
			BufferedReader br = new BufferedReader(fr);
			String line;
			while((line = br.readLine()) != null){
				if(line.startsWith(username)){
					exists = true;
				}
			} 
			br.close();
			fr.close();
		}catch(FileNotFoundException fnfe){
			System.out.println("File not found exception: " + fnfe.getMessage());
		}catch (IOException ioe) {
			System.out.println("IOException: " + ioe.getMessage());
		}
		
		
		return exists;
	}
	
	//returns true if username is in file and matches password
	public User userLogin(String username, String password){
		//boolean correctLogin = false;
		
		User u = null;
		try{
			FileReader fr= new FileReader(filename);
			BufferedReader br = new BufferedReader(fr);
			String line;
			String [] words;
			while((line = br.readLine()) != null){
				words = line.split("/");
				if((words[0].equals(username)) && words[1].equals(password)){
					//username, password, highscore, numgames, lastsong
					u = new User(words[0], words[1], Integer.parseInt(words[2]), Integer.parseInt(words[3]), words[4], words[5]);
				}
			} 
			br.close();
			fr.close();
		}catch(FileNotFoundException fnfe){
			System.out.println("File not found exception: " + fnfe.getMessage());
		}catch (IOException ioe) {
			System.out.println("IOException: " + ioe.getMessage());
		}
		
		return u;
		
	}
	
	//appends username, password, 0 for high score to end of file
	public User addUser(String username, String password){
		
		FileWriter fw = null;
		BufferedWriter bw = null;
		
		User u = new User(username, password);
		try{
			
			 fw = new FileWriter(filename, true);
			 bw = new BufferedWriter(fw);
//			 System.out.println("Writing: " + username + " " + password + " 0");
			 bw.newLine();
			bw.write(username + "/" + password + "/0" + "/0/ / " );
			bw.newLine();
			bw.flush();
			
		}catch(IOException ioe){
			System.out.println("IOException: " + ioe.getMessage());
		}
		finally{
			if(bw != null){
				try {
					bw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(fw != null){
				try {
					fw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
		
		return u;
	}
	
	//returns true if username is in file and matches password
		public String userLoginString(String username, String password){
			//boolean correctLogin = false;
			
			String toReturn = null;
			try{
				FileReader fr= new FileReader(filename);
				BufferedReader br = new BufferedReader(fr);
				String line;
				String [] words;
				while((line = br.readLine()) != null){
					words = line.split("/");
					if((words[0].equals(username)) && words[1].equals(password)){
						//correctLogin = true;
						//username, password, highscore, numgames, lastsong
						toReturn = line;
					}
				} 
				br.close();
				fr.close();
			}catch(FileNotFoundException fnfe){
				System.out.println("File not found exception: " + fnfe.getMessage());
			}catch (IOException ioe) {
				System.out.println("IOException: " + ioe.getMessage());
			}
			
			return toReturn;
			
		}
	
//returns vector of top 5 scores in descending order
	public Vector<String> highScores(){
		
		Vector<MyPair> allScores = new Vector<MyPair>();
		try{
			FileReader fr= new FileReader(filename);
			BufferedReader br = new BufferedReader(fr);
			String line;
			String [] words;
			while((line = br.readLine()) != null){
				words = line.split("/");
//				//username, password, highscore, numgames, lastsong
				String username = words[0];
				int highScore = Integer.parseInt(words[2]);
				allScores.add(new MyPair(username, highScore));
				
			} 
			br.close();
			fr.close();
		}catch(FileNotFoundException fnfe){
			System.out.println("File not found exception: " + fnfe.getMessage());
		}catch (IOException ioe) {
			System.out.println("IOException: " + ioe.getMessage());
		}
		
		Vector<String> topScores = new Vector<String>();
		
		for(int i = 0; i < 5; i++){
			int max = 0;
			int index = 0;
			String maxName = "";
			for(int j = 0; j < allScores.size(); j++){
				if(allScores.elementAt(j).getScore() > max){
					maxName = allScores.elementAt(j).getName();
					max = allScores.elementAt(j).getScore();
					index = j;
				}
			}
			allScores.remove(index);
			topScores.add(maxName + " " + max);
		}
		
		
		return topScores;
	}

	class MyPair{
		private String name;
		private int score;
		
		public MyPair(String name, int score){
			this.name = name;
			this.score = score;
		}
		
		public int getScore(){
			return score;
		}
		
		public String getName(){
			return name;
		}
	}
	
//changes high score
	public void changeHighScore(String username, int highScore){
		System.out.println("Username: " + username + ". new score: " + highScore);
        try {
            // Open the file that is the first
            // command line parameter
            FileInputStream fstream = new FileInputStream("users.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String strLine;
            StringBuilder fileContent = new StringBuilder();
            //Read File Line By Line
            while ((strLine = br.readLine()) != null) {
                // Print the content on the console
                System.out.println(strLine);
                String tokens[] = strLine.split("/");
                    // Here tokens[0] will have value of ID
                    if(tokens[0].equals(username)){
                    	String newLine = tokens[0] + "/" + tokens[1] + "/" + highScore + "/" + tokens[3] + "/" + tokens[4] + "/" + tokens[5];
                        fileContent.append(newLine);
                    }
                    else{
                    	fileContent.append(strLine);
                    }
                        
                        fileContent.append("\n");
                    
            }
            // Now fileContent will have updated content , which you can override into file
            br.close();
            fstream.close();
            FileWriter fstreamWrite = new FileWriter("users.txt");
            BufferedWriter out = new BufferedWriter(fstreamWrite);
            out.write(fileContent.toString());
            out.close();
            //Close the input stream
//            in.close();
        } catch (Exception e) {//Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }
    }
	
//changes number of games
	public void changeNumGames(String username, int numGames){
		System.out.println("Username: " + username + ". new num: " + numGames);
        try {
            // Open the file that is the first
            // command line parameter
            FileInputStream fstream = new FileInputStream("users.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String strLine;
            StringBuilder fileContent = new StringBuilder();
            //Read File Line By Line
            while ((strLine = br.readLine()) != null) {
                // Print the content on the console
                System.out.println(strLine);
                String tokens[] = strLine.split("/");
                    // Here tokens[0] will have value of ID
                    if(tokens[0].equals(username)){
                    	String newLine = tokens[0] + "/" + tokens[1] + "/" + tokens[2] + "/" + numGames + "/" + tokens[4] + "/" + tokens[5];
                        fileContent.append(newLine);
                    }
                    else{
                    	fileContent.append(strLine);
                    }
                        
                        fileContent.append("\n");
                    
            }
            // Now fileContent will have updated content , which you can override into file
            br.close();
            fstream.close();
            FileWriter fstreamWrite = new FileWriter("users.txt");
            BufferedWriter out = new BufferedWriter(fstreamWrite);
            out.write(fileContent.toString());
            out.close();
            //Close the input stream
//            in.close();
        } catch (Exception e) {//Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }
    }
//	
	public void changelastSong(String username, String lastSong){
        try {
            // Open the file that is the first
            // command line parameter
            FileInputStream fstream = new FileInputStream("users.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String strLine;
            StringBuilder fileContent = new StringBuilder();
            //Read File Line By Line
            while ((strLine = br.readLine()) != null) {
                // Print the content on the console
                System.out.println(strLine);
                String tokens[] = strLine.split("/");
                    // Here tokens[0] will have value of ID
                    if(tokens[0].equals(username)){
                    	String newLine = tokens[0] + "/" + tokens[1] + "/" + tokens[2] + "/" + tokens[3] + "/" + lastSong + "/" + tokens[5];
                        fileContent.append(newLine);
                    }
                    else{
                    	fileContent.append(strLine);
                    }
                        
                        fileContent.append("\n");
                    
            }
            // Now fileContent will have updated content , which you can override into file
            br.close();
            fstream.close();
            FileWriter fstreamWrite = new FileWriter("users.txt");
            BufferedWriter out = new BufferedWriter(fstreamWrite);
            out.write(fileContent.toString());
            out.close();
            //Close the input stream
//            in.close();
        } catch (Exception e) {//Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }
    }
//	
	public void changeCharacter(String username, String character){
        try {
            // Open the file that is the first
            // command line parameter
            FileInputStream fstream = new FileInputStream("users.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String strLine;
            StringBuilder fileContent = new StringBuilder();
            //Read File Line By Line
            while ((strLine = br.readLine()) != null) {
                // Print the content on the console
                System.out.println(strLine);
                String tokens[] = strLine.split("/");
                    // Here tokens[0] will have value of ID
                    if(tokens[0].equals(username)){
                    	String newLine = tokens[0] + "/" + tokens[1] + "/" + tokens[2] + "/" + tokens[3] + "/" + tokens[4] + "/" + character;
                        fileContent.append(newLine);
                    }
                    else{
                    	fileContent.append(strLine);
                    }
                        
                        fileContent.append("\n");
                    
            }
            // Now fileContent will have updated content , which you can override into file
            br.close();
            fstream.close();
            FileWriter fstreamWrite = new FileWriter("users.txt");
            BufferedWriter out = new BufferedWriter(fstreamWrite);
            out.write(fileContent.toString());
            out.close();
            //Close the input stream
//            in.close();
        } catch (Exception e) {//Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }
    }
	
}
