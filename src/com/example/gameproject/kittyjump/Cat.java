package com.example.gameproject.kittyjump;

import com.example.gameproject.gl.DynamicGameObject;

public class Cat extends DynamicGameObject {
	public static final int CAT_STATE_JUMP 		= 0;
	public static final int CAT_STATE_FALL 		= 1;
	public static final int CAT_STATE_HIT 		= 2;
	public static final float CAT_JUMP_VELOCITY = 11;
	public static final float CAT_MOVE_VELOCITY = 20;
	public static final float CAT_WIDTH 		= 0.8f;
	public static final float CAT_HEIGHT 		= 0.8f;

	int state;
	float stateTime;

	public Cat(float x, float y) {
		super(x, y, CAT_WIDTH, CAT_HEIGHT);
		state = CAT_STATE_FALL;
		stateTime = 0;
	}

	public void update(float deltaTime) {
		velocity.add(World.gravity.x * deltaTime, World.gravity.y * deltaTime);
		position.add(velocity.x * deltaTime, velocity.y * deltaTime);
		bounds.lowerLeft.set(position).sub(bounds.width / 2, bounds.height / 2);
		if (velocity.y > 0 && state != CAT_STATE_HIT) {
			if (state != CAT_STATE_JUMP) {
				state = CAT_STATE_JUMP;
				stateTime = 0;
			}
		}
		if (velocity.y < 0 && state != CAT_STATE_HIT) {
			if (state != CAT_STATE_FALL) {
				state = CAT_STATE_FALL;
				stateTime = 0;
			}
		}
		if (position.x < 0)
			position.x = World.WORLD_WIDTH;
		if (position.x > World.WORLD_WIDTH)
			position.x = 0;
		stateTime += deltaTime;
	}

	public void hitDog() {
		velocity.set(0, 0);
		state = CAT_STATE_HIT;
		stateTime = 0;
	}

	public void hitPlatform() {
		velocity.y = CAT_JUMP_VELOCITY;
		state = CAT_STATE_JUMP;
		stateTime = 0;
	}

	public void hitSpring() {
		velocity.y = CAT_JUMP_VELOCITY * 1.5f;
		state = CAT_STATE_JUMP;
		stateTime = 0;
	}
}
