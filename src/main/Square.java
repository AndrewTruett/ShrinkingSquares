package main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

public class Square
{
	private int x, y;
	private int width = 100, height = 100;
	private int shrinkAmount;
	
	private final short WAIT_TIME = 5;//this value slows down the rate at which the squares shrink
	private int time = 0;
	
	private Color color;
	
	boolean fiveTimesPoints;
	
	public Square(int x, int y, int shrinkAmount, boolean fiveTimesPoints)
	{
		this.x = x;
		this.y = y;
		this.shrinkAmount = shrinkAmount;
		this.fiveTimesPoints = fiveTimesPoints;
		
	}
	
	public void update()
	{
		
		
	}
	
	public void draw(Graphics2D g)
	{
		if(width > 75)
		{
			g.setColor(Color.GREEN);
			color = Color.GREEN;
		}
		else if(width > 25)
		{
			g.setColor(Color.YELLOW);
			color = Color.YELLOW;
		}
		else
		{
			g.setColor(Color.RED);
			color = Color.RED;
		}
		g.drawRect(x, y, width, height);
	}
	
	/***
	 * This method returns whether a specific point is contained within this object
	 * @param p the point in question
	 * @return tru if the point is within this square, false otherwise
	 */
	public boolean contains(Point p)
	{
		return ((p.getX() >= x && p.getX() <= (x+width)) && (p.getY() >= y && p.getY() <= (y+height)));
	}
	
	/***
	 * This method decreases the width and height variables by shrinkAmount.
	 * width and height will always be at least 1. 
	 */
	public void shrink()
	{
		if(time > WAIT_TIME)
		{
			time = 0;
			width-= shrinkAmount;
			width = Math.max(width, 1);
				
			height-= shrinkAmount;
			height = Math.max(height, 1);
			
			x+=shrinkAmount/2;
			y+=shrinkAmount/2;
		}
		time++;
	}

	//***GETTERS AND SETTERS***
	public int getX()
	{
		return x;
	}

	public void setX(int x)
	{
		this.x = x;
	}

	public int getY()
	{
		return y;
	}

	public void setY(int y)
	{
		this.y = y;
	}
	
	public int getWidth()
	{
		return width;
	}

	public void setWidth(int width)
	{
		this.width = width;
	}

	public int getHeight()
	{
		return height;
	}

	public void setHeight(int height)
	{
		this.height = height;
	}

	public double getshrinkAmount()
	{
		return shrinkAmount;
	}

	public void setshrinkAmount(int shrinkAmount)
	{
		this.shrinkAmount = shrinkAmount;
	}
	
	public Color getColor()
	{
		return color;
	}
	//***END GETTERS AND SETTERS***
}
