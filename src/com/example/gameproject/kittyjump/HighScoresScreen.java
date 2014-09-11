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

public class HighScoresScreen extends GLScreen {
	Camera2D guiCam;
	SpriteBatcher batcher;
	Rectangle backBounds;
	Vector2 touchPoint;
	String[] highScores;
	float xOffset = 0;

	public HighScoresScreen(Game game) {
		super(game);

		guiCam = new Camera2D(glGraphics, 720, 1280);
		backBounds = new Rectangle(0, 0, 64, 64);
		touchPoint = new Vector2();
		batcher = new SpriteBatcher(glGraphics, 100);
		highScores = new String[5];
		for (int i = 0; i < 5; i++) {
			highScores[i] = (i + 1) + ". " + Settings.highscores[i];
			xOffset = Math.max(highScores[i].length() * Assets.font.glyphWidth, xOffset);
		}
		xOffset = 360 - xOffset / 2;
	}

	@Override
	public void update(float deltaTime) {
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		game.getInput().getKeyEvents();

		for (int i = 0; i < touchEvents.size(); i++) {
			TouchEvent event = touchEvents.get(i);
			touchPoint.set(event.x, event.y);
			guiCam.touchToWorld(touchPoint);
			if (event.type == TouchEvent.TOUCH_UP) {
				if (OverlapTester.pointInRectangle(backBounds, touchPoint)) {
					game.setScreen(new MainMenuScreen(game));
					return;
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
			batcher.drawSprite(360, 360, 192, 48, Assets.highScores);
			batcher.drawSprite(32, 32, 64, 64, Assets.arrow);
		batcher.endBatch();
		
		batcher.beginBatch(Assets.fontTexture);
			float y = 240;
			for (int i = 4; i >= 0; i--) {
				Assets.font.drawText(batcher, highScores[i], xOffset, y);
				y += Assets.font.glyphHeight;
			}
		batcher.endBatch();
		
		gl.glDisable(GL10.GL_BLEND);
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
	}
}
