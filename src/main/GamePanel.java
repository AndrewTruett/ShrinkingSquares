package main;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import gamestates.GameStateManager;

public class GamePanel extends JPanel implements Runnable, MouseListener, MouseMotionListener{
	
	public static final int WIDTH = 400;
	public static final int HEIGHT = 400;
	
	private Thread thread;
	
	private BufferedImage image;
	private Graphics2D g;
	private boolean running;
	
	List<Sound> musicTrack;
	Sound currentSong;
	int songIndex;
	
	private GameStateManager gsm;
	
	//Game Options
	public static boolean playMusic;
	public static boolean devOptions;
	
	public GamePanel() 
	{
		super();
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setFocusable(true);
		requestFocus();
	}
	
	public void addNotify() 
	{
		super.addNotify();
		if(thread == null) 
		{
			addMouseListener(this);
			addMouseMotionListener(this);
			thread = new Thread(this);
			thread.start();
		}
	}
	
	private void init() 
	{	
		running = true;
		
		playMusic = false;//this option will eventually be remembered when the game loads
		devOptions = false;
		
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		
		g = (Graphics2D) image.getGraphics();
		gsm = new GameStateManager();

		trackListInit();
	}
	
	
	private void trackListInit()
	{
		songIndex = 0;
		musicTrack = new ArrayList<Sound>();
		
		try
		{	
			musicTrack.add(new Sound("/res/sounds/music/DM Galaxy - Paralyzed (feat. Tyler Fiore) [NCS Release].wav"));
			musicTrack.add(new Sound("/res/sounds/music/DJ ASSASS1N - Frag Out [NCS Release].wav"));
			musicTrack.add(new Sound("/res/sounds/music/[Drumstep] - Tristam  Braken - Flight [Monstercat Release].wav"));
			musicTrack.add(new Sound("/res/sounds/music/RetroVision - Puzzle [NCS Release].wav"));
		}
		catch(NullPointerException npe)
		{
			npe.printStackTrace();
		}
		
		Collections.shuffle(musicTrack);
		currentSong = musicTrack.get(songIndex);//first song
	}
	
	// the "main" function
	public void run() 
	{
		
		init();
		
		int FPS = 60;
		int targetTime = 1000 / FPS;
		
		long start;
		long elapsed;
		long wait;
		
		while(running) 
		{
			start = System.nanoTime();
			
			update();
			draw();
			drawToScreen();
			
			elapsed = (System.nanoTime() - start) / 1000000;
			
			wait = targetTime - elapsed;
			if(wait < 0) wait = 5;
			
			try 
			{
				Thread.sleep(wait);
			}
			catch(Exception e) 
			{
				e.printStackTrace();
			}	
		}	
	}
	
	// updates the game
	private void update() {
		gsm.update();
		
		if(playMusic)//user wants music
		{
			if(!currentSong.isActive())//current song is not playing
			{
				nextSong();
				currentSong.play();
			}
		}
		else//(!playMusic)
		{
			if(currentSong.isActive())
			{
				currentSong.stop();
			}
		}
	}
	
	// draws the game onto an off-screen buffered image
	private void draw() 
	{
		gsm.draw(g);
	}
	
	// draws the off-screen buffered image to the screen
	private void drawToScreen() 
	{
		Graphics g2 = getGraphics();
		g2.drawImage(image, 0, 0, null);
		g2.dispose();
	}
	
	
	/**
	 * This method sets the currentSong to the next song in musicTrack.
	 * This method does not play the currentSong. 
	 */
	private void nextSong()
	{
		songIndex = (songIndex+1) % musicTrack.size();
		currentSong = musicTrack.get(songIndex);
	}
	
	@Override
	public void mousePressed(MouseEvent e)
	{
		gsm.mousePressed(e);
	}

	@Override
	public void mouseMoved(MouseEvent e)
	{
		try
		{
			gsm.mouseMoved(e);
		}
		catch(NullPointerException npe)
		{
			
		}
	}

	//Unused Overridden Methods
	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
	public void mouseClicked(MouseEvent e){}
	public void mouseReleased(MouseEvent e){}
	public void mouseDragged(MouseEvent e){}

}