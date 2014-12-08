package com.turkey.LD31.Game.objects;

import java.awt.Color;

import VolatiliaAPI.graphics.basic.Rectangle;

public class Wall extends Rectangle
{
	int correctX, correctY;
	private int speed = 2;
	private int xVel = 0;
	private int yVel = 0;

	public boolean moving = false;
	public boolean movingOn = false;
	

	public Wall(int x, int y, int w, int h, Color c)
	{
		super(x, y, w, h, c);
		correctX = x;
		correctY = y;
	}

	public void moveOffScreen(int dir)
	{
		moving = true;
		movingOn = false;

		if(dir == 1)
			yVel = speed;
		else if(dir == 2)
			xVel = speed;
		else if(dir == 3)
			yVel = -speed;
		else if(dir == 4)
			xVel = -speed;
	}
	
	public void moveOnScreen(int dir)
	{
		moving = true;
		movingOn = true;

		if(dir == 1)
			yVel = speed;
		else if(dir == 2)
			xVel = speed;
		else if(dir == 3)
			yVel = -speed;
		else if(dir == 4)
			xVel = -speed;
	}

	public void update()
	{
		if(!moving)
			return;
		x+=xVel;
		y+=yVel;
		if(!movingOn && (x == 0 || y == 0 || x == 800 || y == 600))
			moving = false;
		else if(movingOn && (x == correctX && y == correctY))
			moving = false;
	}
}
