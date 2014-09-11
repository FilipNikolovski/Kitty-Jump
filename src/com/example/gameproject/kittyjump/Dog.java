package com.example.gameproject.kittyjump;

import com.example.gameproject.gl.DynamicGameObject;

public class Dog extends DynamicGameObject {
	public static final float DOG_WIDTH = 1;
	public static final float DOG_HEIGHT = 0.6f;
	public static final float DOG_VELOCITY = 3f;
	float stateTime = 0;

	public Dog(float x, float y) {
		super(x, y, DOG_WIDTH, DOG_HEIGHT);
		velocity.set(DOG_VELOCITY, 0);
	}

	public void update(float deltaTime) {
		position.add(velocity.x * deltaTime, velocity.y * deltaTime);
		bounds.lowerLeft.set(position).sub(DOG_WIDTH / 2, DOG_HEIGHT / 2);
		
		if (position.x < DOG_WIDTH / 2) {
			position.x = DOG_WIDTH / 2;
			velocity.x = DOG_VELOCITY;
		}
		if (position.x > World.WORLD_WIDTH - DOG_WIDTH / 2) {
			position.x = World.WORLD_WIDTH - DOG_WIDTH / 2;
			velocity.x = -DOG_VELOCITY;
		}
		stateTime += deltaTime;
	}
}
