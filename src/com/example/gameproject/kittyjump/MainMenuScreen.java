package com.example.gameproject.kittyjump;

import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import com.example.gameproject.gl.Camera2D;
import com.example.gameproject.gl.SpriteBatcher;
import com.example.gameproject.graphics.GLScreen;
import com.example.gameproject.interfaces.Game;
import com.example.gameproject.interfaces.Input.TouchEvent;
import com.example.gameproject.math.OverlapTester;
import com.example.gameproject.math.Rectangle;
import com.example.gameproject.math.Vector2;

public class MainMenuScreen extends GLScreen {
	Camera2D guiCam;
	SpriteBatcher batcher;
	Rectangle soundBounds;
	Rectangle playBounds;
	Rectangle highscoresBounds;
	Vector2 touchPoint;

	public MainMenuScreen(Game game) {
		super(game);
		guiCam 			 = new Camera2D(glGraphics, 720, 1280);
		batcher 		 = new SpriteBatcher(glGraphics, 100);
		soundBounds 	 = new Rectangle(0, 0, 64, 64);
		playBounds 		 = new Rectangle(360 - 150, 280 + 24, 192, 48);
		highscoresBounds = new Rectangle(360 - 150, 280 - 36, 192, 48);
		touchPoint 		 = new Vector2();
	}

	@Override
	public void update(float deltaTime) {
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		game.getInput().getKeyEvents();

		for (int i = 0; i < touchEvents.size(); i++) {
			TouchEvent event = touchEvents.get(i);
			if (event.type == TouchEvent.TOUCH_UP) {
				touchPoint.set(event.x, event.y);
				guiCam.touchToWorld(touchPoint);

				if (OverlapTester.pointInRectangle(playBounds, touchPoint)) {
					Assets.playSound(Assets.clickSound);
					game.setScreen(new GameScreen(game));
					return;
				}
				if (OverlapTester.pointInRectangle(highscoresBounds, touchPoint)) {
					Assets.playSound(Assets.clickSound);
					game.setScreen(new HighScoresScreen(game));
					return;
				}
				if (OverlapTester.pointInRectangle(soundBounds, touchPoint)) {
					Assets.playSound(Assets.clickSound);
					Settings.soundEnabled = !Settings.soundEnabled;
					if (Settings.soundEnabled)
						Assets.music.play();
					else
						Assets.music.pause();
				}
			}
		}
	}

	@Override
	public void present(float deltaTime) {
		GL10 gl = glGraphics.getGL();
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		guiCam.setViewportAndMatrices();
		
		gl.glEnable(GL10.GL_TEXTURE_2D);
		
		batcher.beginBatch(Assets.background);
		batcher.drawSprite(360, 640, 720, 1280, Assets.backgroundRegion);
		batcher.endBatch();
		
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		
		batcher.beginBatch(Assets.atlas);
		batcher.drawSprite(360, 820 - 10 - 71, 480, 224, Assets.logo);
		batcher.drawSprite(360, 280, 256, 128, Assets.mainMenu);
		batcher.drawSprite(32, 32, 64, 64, Settings.soundEnabled ? Assets.soundOn : Assets.soundOff);
		batcher.endBatch();
		
		gl.glDisable(GL10.GL_BLEND);
	}

	@Override
	public void pause() {
		Settings.save(game.getFileIO());
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
	}
}
