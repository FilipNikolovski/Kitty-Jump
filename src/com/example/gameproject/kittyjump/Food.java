package com.example.gameproject.kittyjump;

import com.example.gameproject.gl.GameObject;

public class Food extends GameObject {
	public static final float FOOD_WIDTH = 0.6f;
	public static final float FOOD_HEIGHT = 0.6f;
	public static final int FOOD_SCORE = 10;

	public Food(float x, float y) {
		super(x, y, FOOD_WIDTH, FOOD_HEIGHT);
	}
}
