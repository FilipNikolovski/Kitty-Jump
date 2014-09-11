package com.example.gameproject.kittyjump;

import com.example.gameproject.gl.Animation;
import com.example.gameproject.gl.Font;
import com.example.gameproject.gl.GLGame;
import com.example.gameproject.gl.TextureRegion;
import com.example.gameproject.graphics.Texture;
import com.example.gameproject.interfaces.Music;
import com.example.gameproject.interfaces.Sound;

public class Assets {
	public static Texture background;
	public static TextureRegion backgroundRegion;
	
	public static Texture atlas;
	public static Texture fontTexture;
	public static TextureRegion mainMenu;
	public static TextureRegion pauseMenu;
	public static TextureRegion ready;
	public static TextureRegion gameOver;
	public static TextureRegion highScores;
	public static TextureRegion logo;
	public static TextureRegion soundOn;
	public static TextureRegion soundOff;
	public static TextureRegion arrow;
	public static TextureRegion pause;
	public static TextureRegion spring;
	public static TextureRegion castle;
	public static TextureRegion catFood;
	public static TextureRegion catHit;
	public static TextureRegion platform;
	public static TextureRegion catJump;
	public static TextureRegion catFall;
	public static Animation dogFly;
	public static Animation breakingPlatform;
	
	public static Font font;
	
	public static Music music;
	public static Sound jumpSound;
	public static Sound highJumpSound;
	public static Sound hitSound;
	public static Sound foodSound;
	public static Sound clickSound;
	
	public static void load(GLGame game) {
		background = new Texture(game, "background.png");
		backgroundRegion = new TextureRegion(background, 0, 0, 720, 1280);
		
		atlas = new Texture(game, "atlas.png");
		
		//Menu assets
		mainMenu   = new TextureRegion(atlas, 320, 128, 192, 96);
		pauseMenu  = new TextureRegion(atlas, 128, 96, 192, 96);
		ready      = new TextureRegion(atlas, 320, 96, 192, 32);
		gameOver   = new TextureRegion(atlas, 192, 0, 192, 96);
		highScores = new TextureRegion(atlas, 320, 180, 192, 48);
		logo 	   = new TextureRegion(atlas, 0, 256, 480, 224);
		soundOn    = new TextureRegion(atlas, 0, 0, 64, 64);
		soundOff   = new TextureRegion(atlas, 64, 0, 64, 64);
		arrow 	   = new TextureRegion(atlas, 0, 64, 64, 64);
		pause      = new TextureRegion(atlas, 64, 64, 64, 64);
		
		//Game assets
		spring 	   = new TextureRegion(atlas, 128, 0, 32, 32);
		castle 	   = new TextureRegion(atlas, 128, 32, 64, 64);
		catFood    = new TextureRegion(atlas, 160, 0, 32, 32);
		catJump	   = new TextureRegion(atlas, 32, 128, 32, 32);
		catFall	   = new TextureRegion(atlas, 64, 128, 32, 32);
		catHit 	   = new TextureRegion(atlas, 0, 128, 32, 32);
		platform   = new TextureRegion(atlas, 64, 160, 64, 16);
		breakingPlatform = new Animation(0.2f, new TextureRegion(atlas, 64, 160, 64, 16),
											   new TextureRegion(atlas, 64, 176, 64, 16),
											   new TextureRegion(atlas, 64, 192, 64, 16),
											   new TextureRegion(atlas, 64, 208, 64, 16));
		dogFly 		     = new Animation(0.2f, new TextureRegion(atlas, 0, 160, 32, 32),
				   							   new TextureRegion(atlas, 32, 160, 32, 32));
		
		//Font
		fontTexture = new Texture(game, "font.png");
		font 		= new Font(fontTexture, 0, 0, 16, 16, 20);
				
		//Sound assets
		jumpSound 	  = game.getAudio().newSound("jump.wav");
		highJumpSound = game.getAudio().newSound("spring.wav");
		hitSound 	  = game.getAudio().newSound("hurt.wav");
		foodSound 	  = game.getAudio().newSound("food.wav");
		clickSound 	  = game.getAudio().newSound("select.wav");
		music = game.getAudio().newMusic("music.mp3");
		music.setLooping(true);
		music.setVolume(0.5f);
		if(Settings.soundEnabled)
			music.play();
	}
	
	public static void reload() {
		background.reload();
		atlas.reload();
		if(Settings.soundEnabled)
			music.play();
	}
	
	public static void playSound(Sound sound) {
		if(Settings.soundEnabled)
			sound.play(1);
	}
	
}
