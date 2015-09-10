package com.turkey.LD31.Resources;

import VolatiliaAPI.graphics.Image;
import VolatiliaAPI.graphics.ImageSheet;

public class Resources
{
	public static ImageSheet playerImages;
	public static ImageSheet EntityImages;
	
	public static Image player_Pong;
	
	public static Image player_SIMorph1;
	public static Image player_SIMorph2;
	public static Image player_SIMorph3;
	public static Image player_SIMorph4;
	public static Image player_SIMorph5;
	public static Image player_SIMorph6;
	public static Image player_SIMorph7;
	
	public static Image siAlien1;
	public static Image siAlien2;
	public static Image siAlien3;
	public static Image laser;
	public static Image alienLaser1;
	public static Image alienLaser2;
	public static Image barrier1;
	public static Image barrier2;
	public static Image barrier3;
	public static Image barrier4;
	public static Image barrier5;
	
	public static Image pong_Board;
	
	public static void loadResources()
	{
		playerImages = new ImageSheet(Resources.class, "/Images/Player Sprites.png");
		EntityImages = new ImageSheet(Resources.class, "/Images/EntitySheet.png");
		
		player_Pong = new Image(playerImages, 0, 0, 32, 16);
		
		player_SIMorph1 = new Image(playerImages, 32, 0, 32, 16);
		player_SIMorph2 = new Image(playerImages, 64, 0, 32, 16);
		player_SIMorph3 = new Image(playerImages, 96, 0, 32, 16);
		player_SIMorph4 = new Image(playerImages, 128, 0, 32, 16);
		player_SIMorph5 = new Image(playerImages, 0, 16, 32, 16);
		player_SIMorph6 = new Image(playerImages, 32, 16, 32, 16);
		player_SIMorph7 = new Image(playerImages, 64, 16, 32, 16);
		
		siAlien1 = new Image(EntityImages, 0, 0, 32, 32);
		siAlien2 = new Image(EntityImages, 32, 0, 32, 32);
		siAlien3 = new Image(EntityImages, 64, 0, 32, 32);
		laser = new Image(EntityImages, 128, 0, 32, 32);
		alienLaser1 = new Image(EntityImages, 0, 64, 32, 32);
		alienLaser2 = new Image(EntityImages, 32, 64, 32, 32);
		barrier1 = new Image(EntityImages, 0, 32, 32, 32);
		barrier2 = new Image(EntityImages, 32, 32, 32, 32);
		barrier3 = new Image(EntityImages, 64, 32, 32, 32);
		barrier4 = new Image(EntityImages, 96, 32, 32, 32);
		barrier5 = new Image(EntityImages, 128, 32, 32, 32);
		
		
		
		pong_Board = new Image(new ImageSheet(Resources.class, "/Images/Pong Board.png"));
	}
}
