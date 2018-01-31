package main;

import javax.swing.JFrame;

public class Game 
{

	public static void main(String[] args) 
	{	
		/*System.out.println(System.getProperty("user.dir"));
		System.out.println(System.getProperty("user.home"));
		System.out.println(System.getProperty("os.name"));*/
		
		
		JFrame window = new JFrame();
		window.setTitle("Shrinking Squares Beta 2.0");
		window.setLocationRelativeTo(null);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		window.setContentPane(new GamePanel());
		
		window.setResizable(false);
		window.pack();
		window.setVisible(true);
		
	}
}