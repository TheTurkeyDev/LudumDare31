package com.turkey.LD31.Entity;

import java.awt.Rectangle;

import VolatiliaAPI.entity.Entity;

import com.turkey.LD31.Game.GameMain;
import com.turkey.LD31.Resources.Resources;

public class Laser extends Entity
{

	public int speed = 5;
	private boolean move = false;
	
	GameMain gm;
	
	public Laser(GameMain g)
	{
		super(Resources.laser, 32, g);
		gm = g;
		isAlive = true;
	}
	
	public void update()
	{
		if(!move)
			return;
		y-=speed;
		
		if(y < 0)
		{
			move = false;
			isAlive = false;
		}
		
		for(Entity ent : gm.getEntities())
		{
			if(ent instanceof SIAlien)
			{
				Rectangle ar = new Rectangle(ent.getLocation().getX() + 5 , ent.getLocation().getY() - 7, ent.getWidth() - 10, ent.getHeight() - 14);
				Rectangle lr = new Rectangle(x+15,y-7,width - 30,height - 24);
				if(ar.intersects(lr))
				{
					isAlive = false;
					ent.kill();
					return;
				}
			}
			else if(ent instanceof Barrier)
			{
				Rectangle br = new Rectangle(ent.getLocation().getX() + 1 , ent.getLocation().getY() - 5, ent.getWidth() - 2, ent.getHeight() - 10);
				Rectangle lr = new Rectangle(x+15,y-7,width - 30,height - 24);
				if(br.intersects(lr))
				{
					isAlive = false;
					((Barrier)ent).hit();
					return;
				}
			}
		}
	}
	
	public void fire()
	{
		move = true;
	}
}
