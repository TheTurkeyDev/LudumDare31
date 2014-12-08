package com.turkey.LD31.Game;

import java.awt.Color;
import java.util.ArrayList;

import VolatiliaAPI.graphics.Font;
import VolatiliaAPI.graphics.ImageSheet;
import VolatiliaAPI.graphics.Text;
import VolatiliaAPI.graphics.basic.Rectangle;

import com.turkey.LD31.Game.objects.Brick;
import com.turkey.LD31.Game.objects.Wall;

public class BreakoutGame
{
	private GameMain gm;

	private int lives = 3;
	private Text livesText;
	private Text livesnumberText;
	private Font livesFont;
	
	private boolean won = false;
	private boolean stop = true;

	private Wall[] walls = new Wall[3];
	private Wall topWall;
	private Wall leftWall;
	private Wall rightWall;

	private ArrayList<Brick> bricks = new ArrayList<Brick>(); 

	private int ballX = 300, ballY = 300;
	private int ballXVel = 2, ballYVel = 2;
	private Rectangle ball;

	private boolean loading = false;
	private boolean unloading = false;

	public BreakoutGame(GameMain game)
	{
		gm = game;
		
		leftWall = new Wall(200,50,5,500,Color.white);
		rightWall = new Wall(600,50,5,500,Color.white);
		topWall = new Wall(200,50,400,5,Color.white);
		walls[2] = leftWall;
		walls[1] = topWall;
		walls[0] = rightWall;

		livesFont = new Font(new ImageSheet(PongGame.class, "/Font/LargeFont.png", 640, 240), 40);

		livesText = new Text(livesFont.getStringImage("LIVES"), 0, 200);
		livesText.hide();
		gm.screen.addText(livesText);
		livesnumberText = new Text(livesFont.getStringImage("" + lives), 75, 275);
		livesnumberText.hide();
		gm.screen.addText(livesnumberText);
		
		ball = new Rectangle(ballX, ballY, 10, 10, Color.white);
		ballXVel = 2;
		ballYVel = 2;
	}

	public void loadBreakout()
	{

		loading = true;
		unloading = false;
		
		lives = 3;
		livesnumberText.setText(livesFont.getStringImage("" + lives));
		livesText.show();
		livesnumberText.show();
		
		leftWall.setX(0);
		topWall.setY(0);
		rightWall.setX(800);
		leftWall.moveOnScreen(2);
		rightWall.moveOnScreen(4);
		topWall.moveOnScreen(1);
		gm.addBasicObject(leftWall);
		gm.addBasicObject(rightWall);
		gm.addBasicObject(topWall);

		
		for(int y = 110; y < 175; y+=15)
		{
			for(int x = 210; x < 600; x+=30)
			{
				int dur = 1;
				if(y < 130)
					dur = 2;
				Brick b = new Brick(x, y, 25, 10, dur==2?Color.orange:Color.green, dur);
				bricks.add(b);
			}
		}
		
		for(Brick b: bricks)
		{
			if(b.getX() < 400)
			{
				b.setX(0);
				b.moveOnScreen(2);
			}
			else
			{
				b.setX(800);
				b.moveOnScreen(4);
			}
			gm.addBasicObject(b);
		}

		gm.addBasicObject(ball);
	}
	
	public void unloadBreakout()
	{
		unloading = true;
		stop = true;
		livesText.hide();
		livesnumberText.hide();
		for(int i = 1; i <= walls.length; i++)
		{
			Wall w = walls[i-1];
			w.moveOffScreen(i+1);
		}
		
		for(int i = 1; i <= bricks.size(); i++)
		{
			Brick b = bricks.get(i-1);
			b.moveOffScreen(i%4 + 1);
		}
	}

	public void update()
	{
		if(loading)
		{
			boolean done = true;

			for(Wall w: walls)
			{
				w.update();
				if(w.moving)
					done = false;
			}
			for(Brick b: bricks)
			{
				b.update();
				if(b.moving)
					done = false;
			}

			if(done)
			{
				stop = false;
				loading = false;
			}
		}
		else if(unloading)
		{
			boolean done = true;

			for(Wall w: walls)
			{
				w.update();
				if(w.moving)
					done = false;
			}
			for(Brick b: bricks)
			{
				b.update();
				if(b.moving)
					done = false;
			}
			if(done)
			{
				bricks.clear();
				if(!won)
					gm.restart();
				else
					gm.nextGame();
				unloading = false;
			}
		}
		else
		{
			if(stop)
				return;
			if(gm.isWall(ballX + ballXVel, ballY))
			{
				ballXVel*=-1;
			}
			if(gm.isWall(ballX, ballY+ ballYVel))
			{
				ballYVel*=-1;
			}

			ball.setX(ballX+=ballXVel);
			ball.setY(ballY+=ballYVel);

			if((ballY == 526) && (ballX > gm.player.getLocation().getX() - 16 && ballX < gm.player.getLocation().getX() + 32))
			{
				ballYVel*=-1;
				if(ballX > gm.player.getLocation().getX() + 16)
					ballXVel++;
				else
					ballXVel--;
			}

			if(ballY > 600)
			{
				lives--;
				livesnumberText.setText(livesFont.getStringImage("" + lives));
				ball.setX(300);
				ballX=300;
				ball.setY(300);
				ballY=300;
				ballXVel = 2;
				ballYVel = 2;
				if(lives == 0)
				{
					unloading = true;
					won = false;
					unloadBreakout();
				}

			}
			boolean done = true;
			for(Brick b: bricks)
			{
				if(b.canInteract())
					done = false;
			}
			if(done)
			{
				unloading = true;
				won = true;
				unloadBreakout();
			}
		}
	}
}
