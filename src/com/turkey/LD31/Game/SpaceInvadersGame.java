package com.turkey.LD31.Game;

import java.awt.Color;
import java.util.ArrayList;

import VolatiliaAPI.graphics.Font;
import VolatiliaAPI.graphics.ImageSheet;
import VolatiliaAPI.graphics.Text;
import VolatiliaAPI.util.Location;

import com.turkey.LD31.Entity.Barrier;
import com.turkey.LD31.Entity.SIAlien;
import com.turkey.LD31.Entity.SILives;
import com.turkey.LD31.Game.objects.Wall;
import com.turkey.LD31.Resources.Resources;

public class SpaceInvadersGame
{
	private GameMain gm;

	private boolean loading = false;
	private boolean unloading = false;
	
	private Font font;
	private Text gameOverText;
	private Text tfpText;

	private ArrayList<SIAlien> aliens = new ArrayList<SIAlien>();

	private Barrier[] barriers = new Barrier[4];

	private SILives[] livesImages = new SILives[3];
	private int lives = 3;

	private int xshift = 0;
	private boolean right = true;

	private int delay = 0;
	private int speed = 37;

	private Wall botBar;
	
	private boolean won = false;

	public SpaceInvadersGame(GameMain game)
	{
		gm = game;
		font = new Font(new ImageSheet(PongGame.class, "/Font/LargeFont.png"), 40);
		gameOverText = new Text(font.getStringImage("YOU LOSE"), 250, 250);
		gameOverText.hide();
		tfpText = new Text(font.getStringImage("Thanks for playing!"), 50, 250);
		tfpText.hide();
		gm.screen.addText(gameOverText);
		gm.screen.addText(tfpText);
	}

	public void loadSpaceInvaders()
	{	
		loading = true;
		for(int row = 0; row < 5; row++)
		{
			for(int col = 0; col < 10; col++)
			{
				SIAlien alien;
				if(row < 1)
					alien = new SIAlien(gm, Resources.siAlien2, this, new Location(200+(col*35), 100 + (row*25)));
				else if(row < 3)
					alien = new SIAlien(gm, Resources.siAlien1, this, new Location(200+(col*35), 100 + (row*25)));
				else
					alien = new SIAlien(gm, Resources.siAlien3, this, new Location(200+(col*35), 100 + (row*25)));
				aliens.add(alien);
				alien.setLocation(new Location(200+(col*35),0));
				alien.moveOnScreen(1);
				gm.addEntity(alien);
			}
		}

		for(int i = 0; i < 4; i++)
		{
			Barrier b = new Barrier(gm);
			b.setLocation(new Location(240 + (100*i),475));
			barriers[i] = b;
			gm.addEntity(b);
		}

		botBar = new Wall(0,550,800,5,Color.green);
		botBar.setY(600);
		botBar.moveOnScreen(3);
		gm.addBasicObject(botBar);
	}

	public void unloadSpaceInvaders()
	{
		unloading = true;
		for(int i = 1; i <= livesImages.length; i++)
		{
			SILives sil = livesImages[i-1];
			sil.kill();
		}

		for(int i = 1; i <= aliens.size(); i++)
		{
			SIAlien a = aliens.get(i-1);
			a.moveOffScreen(i%4 + 1);
		}
		
		botBar.moveOffScreen(3);
	}

	public void update()
	{
		if(loading)
		{
			botBar.update();

			boolean done = true;

			if(botBar.moving)
				done= false;

			for(SIAlien alien: aliens)
			{
				if(alien.moving)
					done= false;
			}

			if(done)
			{
				loading = false;

				for(int i = 0; i < 3; i++)
				{
					SILives sil = new SILives(gm);
					sil.setLocation(new Location(50 + (50*i),570));
					livesImages[i] = sil;
					gm.addEntity(sil);
				}
			}
		}
		else if(unloading)
		{
			boolean done = true;
			
			botBar.update();

			if(botBar.moving)
				done= false;


			for(SIAlien alien: aliens)
			{
				alien.update();
				if(alien.moving)
					done = false;
			}
			
			if(done && won)
			{
				for(SILives sil: livesImages)
				{
					sil.kill();
				}
				for(Barrier b: barriers)
				{
					b.kill();
				}
				for(SIAlien a: aliens)
				{
					a.kill();
				}
				//gameOverText.hide();
				unloading = false;
				aliens.clear();
				tfpText.show();
				
			}
			else if(done && !won)
			{
				for(SILives sil: livesImages)
				{
					sil.kill();
				}
				for(Barrier b: barriers)
				{
					b.kill();
				}
				for(SIAlien a: aliens)
				{
					a.kill();
				}
				gameOverText.hide();
				aliens.clear();
				gm.clearBasicObjects();
				//gameOverText.hide();
				unloading = false;
				gm.restart();
			}
		}
		else
		{
			delay++;
			if(delay%speed == 0)
			{
				delay=0;
				boolean incY = false;

				if(right)
				{
					xshift++;
					if(xshift == 20)
					{
						right=false;
						incY=true;
					}
				}
				else
				{
					xshift--;
					if(xshift == 0)
					{
						right=true;
						incY=true;
					}
				}
				
				boolean done = true;

				for(SIAlien alien: aliens)
				{
					if(alien.isAlive())
						done = false;
					
					if(right)
						alien.setLocation(alien.getLocation().add(5, 0));
					else
						alien.setLocation(alien.getLocation().subtract(5, 0));
					if(incY)
						alien.setLocation(alien.getLocation().add(0, 5));
				}
				
				if(done)
				{
					unloadSpaceInvaders();
					unloading = true;
					won = true;
				}
			}
		}
	}

	public void playerHit()
	{
		lives--;
		if(lives < 0)
			return;
		if(lives == 0)
		{
			unloadSpaceInvaders();
			unloading = true;
			won = false;
			gameOverText.show();
			return;
		}
		livesImages[lives].kill();
	}
}
