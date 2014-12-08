package com.turkey.LD31.Game.objects;

import java.awt.Color;

import VolatiliaAPI.graphics.basic.Rectangle;

public class Brick extends Rectangle
{
	private int correctX, correctY;
	private int speed = 2;
	private int xVel = 0;
	private int yVel = 0;

	public boolean moving = false;
	public boolean movingOn = false;

	private int durability;

	private boolean interactable = true;


	public Brick(int x, int y, int w, int h, Color c, int dur)
	{
		super(x, y, w, h, c);
		correctX = x;
		correctY = y;
		durability = dur;
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

	public void hit()
	{
		durability--;
		if(durability == 0)
		{
			this.color = Color.black;
			interactable = false;
		}
		else if(durability == 1)
			this.color = Color.green;
		super.setPixels();
	}

	public void update()
	{
		if(!moving)
			return;
		if(!movingOn && (x+xVel < 0 || y+yVel <= 0 || x+xVel > 800 || y+yVel >= 600))
			moving = false;
		else
		{
			x+=xVel;
			y+=yVel;
		}

		if(movingOn && (x == correctX && y == correctY))
			moving = false;
	}

	public boolean canInteract()
	{
		return interactable;
	}
}
