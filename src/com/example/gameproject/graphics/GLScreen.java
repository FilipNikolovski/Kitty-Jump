package com.example.gameproject.graphics;

import com.example.gameproject.gl.GLGame;
import com.example.gameproject.interfaces.Game;

public abstract class GLScreen extends Screen {
	protected final GLGraphics glGraphics;
	protected final GLGame glGame;

	public GLScreen(Game game) {
		super(game);
		glGame = (GLGame) game;
		glGraphics = glGame.getGLGraphics();
	}
}
