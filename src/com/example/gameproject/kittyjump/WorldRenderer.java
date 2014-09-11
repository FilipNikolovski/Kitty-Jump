package com.example.gameproject.kittyjump;

import javax.microedition.khronos.opengles.GL10;

import com.example.gameproject.gl.Animation;
import com.example.gameproject.gl.Camera2D;
import com.example.gameproject.gl.SpriteBatcher;
import com.example.gameproject.gl.TextureRegion;
import com.example.gameproject.graphics.GLGraphics;

public class WorldRenderer {
	static final float FRUSTUM_WIDTH  = 10;
	static final float FRUSTUM_HEIGHT = 15;
	
	GLGraphics glGraphics;
	World world;
	Camera2D cam;
	SpriteBatcher batcher;

	public WorldRenderer(GLGraphics glGraphics, SpriteBatcher batcher, World world) {
		this.glGraphics = glGraphics;
		this.world = world;
		this.cam = new Camera2D(glGraphics, FRUSTUM_WIDTH, FRUSTUM_HEIGHT);
		this.batcher = batcher;
	}

	public void render() {
		if (world.kitty.position.y > cam.position.y)
			cam.position.y = world.kitty.position.y;
		cam.setViewportAndMatrices();
		
		renderBackground();
		renderObjects();
	}

	public void renderBackground() {
		batcher.beginBatch(Assets.background);
		batcher.drawSprite(cam.position.x, cam.position.y, FRUSTUM_WIDTH, FRUSTUM_HEIGHT, Assets.backgroundRegion);
		batcher.endBatch();
	}

	public void renderObjects() {
		GL10 gl = glGraphics.getGL();
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		
		batcher.beginBatch(Assets.atlas);
			renderKitty();
			renderPlatforms();
			renderItems();
			renderDogs();
			renderCastle();
		batcher.endBatch();
		
		gl.glDisable(GL10.GL_BLEND);
	}

	private void renderKitty() {
		TextureRegion keyFrame;
		switch (world.kitty.state) {
		case Cat.CAT_STATE_FALL:
			keyFrame = Assets.catFall;
			break;
		case Cat.CAT_STATE_JUMP:
			keyFrame = Assets.catJump;
			break;
		case Cat.CAT_STATE_HIT:
		default:
			keyFrame = Assets.catHit;
		}
		float side = world.kitty.velocity.x < 0 ? -1 : 1;
		batcher.drawSprite(world.kitty.position.x, world.kitty.position.y, side * 1, 1, keyFrame);
	}

	private void renderPlatforms() {
		int len = world.platforms.size();
		for (int i = 0; i < len; i++) {
			Platform platform = world.platforms.get(i);
			TextureRegion keyFrame = Assets.platform;
			if (platform.state == Platform.PLATFORM_STATE_PULVERIZING) {
				keyFrame = Assets.breakingPlatform.getKeyFrame(platform.stateTime, Animation.ANIMATION_NONLOOPING);
			}
			batcher.drawSprite(platform.position.x, platform.position.y, 2, 0.5f, keyFrame);
		}
	}

	private void renderItems() {
		int len = world.springs.size();
		for (int i = 0; i < len; i++) {
			Spring spring = world.springs.get(i);
			batcher.drawSprite(spring.position.x, spring.position.y, 1, 1, Assets.spring);
		}
		len = world.foods.size();
		for (int i = 0; i < len; i++) {
			Food food = world.foods.get(i);
			TextureRegion keyFrame = Assets.catFood;
			batcher.drawSprite(food.position.x, food.position.y, 1, 1, keyFrame);
		}
	}

	private void renderDogs() {
		int len = world.dogs.size();
		for (int i = 0; i < len; i++) {
			Dog dog = world.dogs.get(i);
			TextureRegion keyFrame = Assets.dogFly.getKeyFrame(dog.stateTime, Animation.ANIMATION_LOOPING);
			float side = dog.velocity.x < 0 ? -1 : 1;
			batcher.drawSprite(dog.position.x, dog.position.y, side * 1, 1, keyFrame);
		}
	}

	private void renderCastle() {
		Castle castle = world.castle;
		batcher.drawSprite(castle.position.x, castle.position.y, 2, 2, Assets.castle);
	}

}
