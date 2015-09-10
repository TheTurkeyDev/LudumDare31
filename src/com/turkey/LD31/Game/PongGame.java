package com.turkey.LD31.Game;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

import VolatiliaAPI.graphics.Font;
import VolatiliaAPI.graphics.ImageSheet;
import VolatiliaAPI.graphics.Text;
import VolatiliaAPI.graphics.basic.Rectangle;

import com.turkey.LD31.Game.objects.MidLine;
import com.turkey.LD31.Game.objects.Wall;

public class PongGame
{

	private GameMain gm;

	private Wall leftWall;
	private Wall rightWall;
	private Wall topWall;
	private Wall botWall;

	private Wall[] walls = new Wall[4];

	private Font scoreFont;

	private Text youScoreText;
	private Text cpuScoreText;
	private Text youScore;
	private Text cpuScore;
	private Text gameOverText;

	private ArrayList<MidLine> mid = new ArrayList<MidLine>();

	private int scoreYou = 0;
	private int scoreCpu = 0;

	private int ballX = 400, ballY = 300;
	private int ballXVel = 2, ballYVel = 2;
	private Rectangle ball;

	private int cpuX = 400, cpuY = 76;
	private int cpuSpeed = 2;
	private Rectangle cpu;

	private boolean stop = false;
	private boolean unloading = false;
	private boolean won = false;

	private Random r = new Random();

	public PongGame(GameMain g)
	{
		gm = g;
		scoreFont = new Font(new ImageSheet(PongGame.class, "/Font/LargeFont.png"), 40);
		ball = new Rectangle(ballX, ballY, 10, 10, Color.white);
		cpu = new Rectangle(cpuX, cpuY, 32, 3, Color.white);
	}

	public void loadPongBoard()
	{
		scoreYou = 0;
		scoreCpu = 0;

		gm.addBasicObject(ball);
		gm.addBasicObject(cpu);

		leftWall = new Wall(200, 50, 5, 500, Color.white);
		rightWall = new Wall(600, 50, 5, 500, Color.white);
		gm.addBasicObject(leftWall);
		gm.addBasicObject(rightWall);

		topWall = new Wall(200, 50, 400, 5, Color.white);
		botWall = new Wall(200, 550, 400, 5, Color.white);
		gm.addBasicObject(topWall);
		gm.addBasicObject(botWall);

		walls[0] = botWall;
		walls[1] = rightWall;
		walls[2] = topWall;
		walls[3] = leftWall;

		for(int i = 207; i < 600; i += 20)
		{
			MidLine line = new MidLine(i, 300, 10, 5, Color.white);
			mid.add(line);
			gm.addBasicObject(line);
		}

		youScoreText = new Text(scoreFont.getStringImage("YOU"), 50, 200);
		gm.screen.addText(youScoreText);
		cpuScoreText = new Text(scoreFont.getStringImage("CPU"), 650, 200);
		gm.screen.addText(cpuScoreText);

		youScore = new Text(scoreFont.getStringImage("" + scoreYou), 90, 275);
		gm.screen.addText(youScore);
		cpuScore = new Text(scoreFont.getStringImage("" + scoreCpu), 690, 275);
		gm.screen.addText(cpuScore);

		gameOverText = new Text(scoreFont.getStringImage(""), 275, 250);
		gm.screen.addText(gameOverText);

		int xvelo = r.nextInt(5);
		ballXVel = xvelo - 3;
		ballYVel = 2;
	}

	public void unloadPong()
	{
		unloading = true;
		youScoreText.hide();
		cpuScoreText.hide();
		youScore.hide();
		cpuScore.hide();
		for(int i = 1; i <= walls.length; i++)
		{
			Wall w = walls[i - 1];
			w.moveOffScreen(i);
		}

		for(int i = 1; i <= mid.size(); i++)
		{
			MidLine m = mid.get(i - 1);
			m.moveOffScreen(i % 4 + 1);
		}
	}

	public void goal(boolean player)
	{
		if(player)
		{
			scoreYou++;
			if(scoreYou == 7)
			{
				stop = true;
				gameOverText.setText(scoreFont.getStringImage("YOU WIN"));
				won = true;
				unloadPong();
			}
			youScore.setText(scoreFont.getStringImage("" + scoreYou));
		}
		else
		{
			scoreCpu++;
			if(scoreCpu == 7)
			{
				stop = true;
				gameOverText.setText(scoreFont.getStringImage("YOU LOSE"));
				won = false;
				unloadPong();
			}
			cpuScore.setText(scoreFont.getStringImage("" + scoreCpu));
		}

		ballX = 400;
		ballY = 300;
		int xvelo = r.nextInt(5);
		ballXVel = xvelo - 3;

		Random r = new Random();
		int mult = r.nextInt(3) - 1;
		if(mult == 0) mult = 1;

		ballYVel *= mult;
	}

	public void upadate()
	{
		if(unloading)
		{
			boolean done = true;

			for(Wall w : walls)
			{
				w.update();
				if(w.moving) done = false;
			}

			for(MidLine m : mid)
			{
				m.update();
				if(m.moving) done = false;
			}
			if(done && won)
			{
				gameOverText.hide();
				unloading = false;
				stop = false;
				mid.clear();
				gm.nextGame();
			}
			else if(done && !won)
			{
				mid.clear();
				gm.clearBasicObjects();
				gameOverText.hide();
				unloading = false;
				stop = false;
				this.loadPongBoard();
			}
		}
		else
		{
			if(stop) return;
			if(cpuX + 16 < ballX) cpuX += cpuSpeed;
			else if(cpuX + 16 > ballX) cpuX -= cpuSpeed;
			cpu.setX(cpuX);

			if(gm.isWall(ballX + ballXVel, ballY))
			{
				ballXVel *= -1;
			}

			ball.setX(ballX += ballXVel);
			ball.setY(ballY += ballYVel);

			if((ballY == 526) && (ballX > gm.player.getLocation().getX() - 16 && ballX < gm.player.getLocation().getX() + 32))
			{
				ballYVel *= -1;
				if(ballX > gm.player.getLocation().getX() + 16) ballXVel++;
				else ballXVel--;
			}

			if((ballY == 76) && (ballX > cpuX && ballX < cpuX + 32))
			{
				ballYVel *= -1;
				if(ballX > cpuX + 16) ballXVel++;
				else ballXVel--;
			}

			if(ballY > 550) goal(false);
			else if(ballY < 50) goal(true);
		}
	}

	public void render()
	{

	}
}
