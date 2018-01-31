package gamestates;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import main.GamePanel;
import main.Square;

public class PlayState extends GameState
{
	List<Square> squares;//all the squares on screen
	private Random rand;
	
	private int spawnDelay;
	private int tickCounter;
	private int ticksTillNextDraw;
	
	private int level;
	
	//totalBrokenSquares: total number of squares broken throughout the duration of this playState, 
	//does not account for the score bonus when multiple squares are broken at the same time
	private int totalBrokenSquares;
	private int score;
	private int misses;
	
	private Rectangle backButton;
	
	public PlayState(GameStateManager gsm)
	{
		super();
		this.gsm = gsm;
		init();
	}
	
	protected void init()
	{
		squares = new LinkedList<Square>();//L.L. used here since squares will often be added and removed, and there will be no need for random access
		rand = new Random();
		spawnDelay = 300;
		tickCounter = 0;
		ticksTillNextDraw = 1;
		level = 1;
		totalBrokenSquares = 0;
		score = 0;
		misses = 0;
		backButton = new Rectangle(GamePanel.WIDTH-40, 5, 30, 30);
	}
	
	public void addObject(Square object)
	{
		this.squares.add(object);
	}
	
	public void removeObject(Square object)
	{
		this.squares.remove(object);
	}
	
	@Override
	public void update()
	{
		int index = 0;
		int size = squares.size();
		while(index < size)
		{
			Square tempObj = squares.get(index);
			tempObj.shrink();
			if(tempObj.getWidth() <= 1)//if square has decreased to a point
			{
				misses++;
				removeObject(tempObj);
				size--;
				
				if(misses > 5)//game over
				{
					quitGame();
					return;//game is over
				}
				continue;//don't increment index since were removing from a list while traversing it
			}
			index++;
		}
		
		calcLevel();
	}
	
	/**
	 * This method sets the level based on the score
	 */
	private void calcLevel()
	{
		
		tickCounter++;
		
		if(score <= 10)
		{
			level = 1;
			spawnDelay = 200;
		}
		else if(score <= 20)
		{
			level = 2;
			spawnDelay = 200;
		}
		else if(score <= 40)
		{
			level = 3;
			spawnDelay = 175;
		}
		else if(score <= 80)
		{
			level = 4;
			spawnDelay = 150;
		}
		else if(score <= 160)
		{
			level = 5;
			spawnDelay = 125;
		}
		else if(score <= 320)
		{
			level = 6;
			spawnDelay = 100;
		}
		else if(score <= 640)
		{
			level = 7;
			spawnDelay = 75;
		}
		else if(score <= 1280)
		{
			level = 8;
			spawnDelay = 50;
		}
		else if(score <= 2560)
		{
			level = 9;
			spawnDelay = 25;
		}
		
		if(tickCounter > ticksTillNextDraw)	
		{
			tickCounter = 0;
			ticksTillNextDraw = rand.nextInt(spawnDelay)+1;
			int numToDraw = rand.nextInt(level)+1;
			for(int i = 0; i <numToDraw; i++)
				addObject(new Square(rand.nextInt(GamePanel.WIDTH-100)+1, rand.nextInt(GamePanel.HEIGHT-100)+1, level, false));
		}
	}

	@Override
	public void draw(Graphics2D g)
	{
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		
		for(Square tempObj : squares)
		{
			tempObj.draw(g);
		}
		
		g.setColor(Color.BLUE);
		g.draw(backButton);
		g.setColor(Color.WHITE);
		g.setFont(UIFont);
		g.drawString("Quit", GamePanel.WIDTH-40, 20);
		g.drawString("Level: "+level+"     Score: "+score+"     Misses: "+misses, 5, 15);
		
	}

	@Override
	public void mousePressed(MouseEvent e)
	{
		Point point = e.getPoint();
		
		if(backButton.contains(point))
			quitGame();
		
		int squaresDestroyed = 0;//this is the number of squares destroyed in one mouse press
		int i = 0;
		int size = squares.size();
		while(i < size)
		{
			Square gameObj = squares.get(i);
			
			if(gameObj.contains(point))//if the point of the mouse click was inside gameObj
			{
				removeObject(gameObj);
				size--;
				squaresDestroyed++;	
				totalBrokenSquares++;
				continue;//don't increment i since I'm removing from a list while traversing it
			}
			i++;
		}
		if(squaresDestroyed == 1)
			score++;
		else
			score+=(2*squaresDestroyed);
		
	}
	
	private void quitGame()
	{
		gsm.setCurrentState(GameStateManager.ENDSTATE);
		EndState e = (EndState) (gsm.getCurrentState());
		e.setFinalScore(score);//tell EndState the final score
		
		//add current values (score, misses, squares broken) to previous values
		StatState s = (StatState)(gsm.getState(GameStateManager.STATSTATE));//shallow copy
		s.setTotalScore(s.getTotalScore() + score);
		s.setTotalMisses(s.getTotalMisses() + misses);
		s.setTotalSquares(s.getTotalSquares() + totalBrokenSquares);
		
		//check for new best score and level
		if(level > s.getBestLevel())
			s.setBestLevel(level);
		if(score > s.getBestScore())
			s.setBestScore(score);
		
		s.saveStats();
		
		init();//reset for next play
	}

	//Getters and Setters
	public int getScore()
	{
		return score;
	}
	
	public void setScore(int newScore)
	{
		assert(newScore>=0);
		score = newScore;
	}
	//End Getters and Setters
	
	//Unused overridden methods
	public void mouseClicked(MouseEvent e){}
	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
	public void mouseReleased(MouseEvent e){}
	public void mouseDragged(MouseEvent e){}
	public void mouseMoved(MouseEvent e){}
}
