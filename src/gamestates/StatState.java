package gamestates;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

import main.GamePanel;

public class StatState extends GameState
{
	private Scanner statFileIn;
	private PrintWriter statFileOut;
	private final String SEPERATOR = System.getProperty("file.seperator");
	private final String DIRECTORY = System.getProperty("user.home")+"\\Documents\\ShrinkingSquares\\stats";
	
	private int totalScore;
	private int totalSquares;
	private int totalMisses;
	private int bestScore;
	private int bestLevel;
	
	private final int TXT_X = 15;
	private final int TXT_Y = 50;
	private final int TXT_Y_SPACE = 50;
	
	private Rectangle backButton;
	
	public StatState(GameStateManager gsm)
	{
		super();
		this.gsm = gsm;
		init();
	}
	
	protected void init()
	{	
		totalScore = 0;
		totalSquares = 0;
		totalMisses = 0;
		bestScore = 0;
		bestLevel = 0;
		
		backButton = new Rectangle(GamePanel.WIDTH-40, 5, 30, 30);
		ioInit();
	}

	@Override
	public void update() {}//nothing to update
	

	@Override
	public void draw(Graphics2D g)
	{
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		g.setColor(Color.WHITE);
		g.setFont(statFont);
		g.drawString("Best Level: "+bestLevel, TXT_X, TXT_Y);
		g.drawString("Best Score: "+bestScore, TXT_X, TXT_Y+(TXT_Y_SPACE));
		g.drawString("Total Score: "+totalScore, TXT_X, TXT_Y+(TXT_Y_SPACE*2));
		g.drawString("Total Squares Broken: "+totalSquares, TXT_X, TXT_Y+(TXT_Y_SPACE*3));
		g.drawString("Total Misses: "+totalMisses, TXT_X, TXT_Y+(TXT_Y_SPACE*4));
		
		g.setFont(UIFont);
		g.drawString("Back", GamePanel.WIDTH-40, 20);
		g.setColor(Color.BLUE);	
		g.draw(backButton);
		
	}
	
	//********************* IO **************************
	
	private void ioInit()
	{
		if(!Files.exists(Paths.get(DIRECTORY)))//if the directory does not exist
		{
			createDirectory();
			saveStats();//write initial stats to file
		}
		
		try
		{
			statFileIn = new Scanner(new File(DIRECTORY, "stats.txt"));
		} 
		catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		readStats();
		statFileIn.close();
	}
	
	/**
	 * This method creates a directory is the user's file system, and initializes stats.txt. This method is only called when the directory does not exist
	 */
	private void createDirectory()
	{
		try
		{
			Files.createDirectories(Paths.get(DIRECTORY));
		} 
		catch (IOException ioe)
		{
			System.err.print("***Error creating directory***");
			ioe.printStackTrace();
		}
	}
	
	/**
	 * This method reads statistics values from the file stats.txt
	 */
	private void readStats()
	{
		statFileIn.nextLine();//skip first line
		String line = statFileIn.nextLine();
		String[] tokens = line.split("-");
		
		assert(tokens.length == 5);
		
		setTotalScore(Integer.parseInt(tokens[0]));
		setTotalSquares(Integer.parseInt(tokens[1]));
		setTotalMisses(Integer.parseInt(tokens[2]));
		setBestScore(Integer.parseInt(tokens[3]));
		setBestLevel(Integer.parseInt(tokens[4]));
	}
	
	/**
	 * This method writes the current statistic values to a file stats.txt
	 */
	public void saveStats()
	{	
		try
		{	
			File myFile = new File(DIRECTORY+"/stats.txt");
			statFileOut = new PrintWriter(new FileWriter(myFile));
		
			statFileOut.println("/*totalscore, totalsquares, totalmisses, bestscore, bestlevel*/");
			statFileOut.print(totalScore+"-"+totalSquares+"-"+totalMisses+"-"+bestScore+"-"+bestLevel);
			statFileOut.close();
		} 
		catch (IOException e)
		{
			System.err.println("***Error writing to file***");
			e.printStackTrace();
		}
	}
	
	@Override
	public void mousePressed(MouseEvent e)
	{
		Point point = e.getPoint();
		
		if(backButton.contains(point))
			gsm.setCurrentState(GameStateManager.MENUSTATE);
	}

	//Getters and Setters
	public int getTotalScore() 
	{
		return totalScore;
	}

	public void setTotalScore(int totalScore)
	{
		assert(totalScore >= 0);
		this.totalScore = totalScore;
	}

	public int getTotalSquares()
	{
		return totalSquares;
	}

	public void setTotalSquares(int totalSquares)
	{
		assert(totalScore >= 0);
		this.totalSquares = totalSquares;
	}

	public int getTotalMisses()
	{
		return totalMisses;
	}

	public void setTotalMisses(int totalMisses)
	{
		assert(totalScore >= 0);
		this.totalMisses = totalMisses;
	}

	public int getBestScore()
	{
		return bestScore;
	}

	public void setBestScore(int bestScore)
	{
		assert(totalScore >= 0);
		this.bestScore = bestScore;
	}

	public int getBestLevel()
	{
		return bestLevel;
	}

	public void setBestLevel(int bestLevel)
	{
		assert(totalScore >= 0);
		this.bestLevel = bestLevel;
	}
	//End Getters and Setters

	//Unused overridden methods
	public void keyPressed(int k){}
	public void keyReleased(int k){}
	public void mouseClicked(MouseEvent e){}
	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
	public void mouseReleased(MouseEvent e){}
	public void mouseDragged(MouseEvent e){}
	public void mouseMoved(MouseEvent e){}
}
