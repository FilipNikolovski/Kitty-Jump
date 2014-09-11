package com.example.gameproject.kittyjump;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.example.gameproject.math.OverlapTester;
import com.example.gameproject.math.Vector2;

public class World {
	public interface WorldListener {
		public void jump();

		public void highJump();

		public void hit();

		public void food();
	}

	public static final float WORLD_WIDTH 		   = 10;
	public static final float WORLD_HEIGHT 		   = 15 * 20;
	public static final int WORLD_STATE_RUNNING    = 0;
	public static final int WORLD_STATE_NEXT_LEVEL = 1;
	public static final int WORLD_STATE_GAME_OVER  = 2;
	
	public static final Vector2 gravity = new Vector2(0, -12);

	public final Cat kitty;
	public final List<Platform> platforms;
	public final List<Spring> springs;
	public final List<Dog> dogs;
	public final List<Food> foods;
	public Castle castle;

	public final WorldListener listener;
	public final Random rand;
	public float heightSoFar;
	public int score;
	public int state;

	public World(WorldListener listener) {
		this.kitty 		= new Cat(5, 1);
		this.platforms 	= new ArrayList<Platform>();
		this.springs 	= new ArrayList<Spring>();
		this.dogs 		= new ArrayList<Dog>();
		this.foods 		= new ArrayList<Food>();
		this.listener 	= listener;
		
		rand = new Random();

		generateLevel();

		this.heightSoFar = 0;
		this.score = 0;
		this.state = WORLD_STATE_RUNNING;
	}

	private void generateLevel() {
		float y = Platform.PLATFORM_HEIGHT / 2;
		float maxJumpHeight = Cat.CAT_JUMP_VELOCITY * Cat.CAT_JUMP_VELOCITY / (2 * -gravity.y);

		while (y < WORLD_HEIGHT - WORLD_WIDTH / 2) {
			int type = rand.nextFloat() > 0.8f ? Platform.PLATFORM_TYPE_MOVING : Platform.PLATFORM_TYPE_STATIC;
			float x  = rand.nextFloat() * (WORLD_WIDTH - Platform.PLATFORM_WIDTH) + Platform.PLATFORM_WIDTH / 2;

			Platform platform = new Platform(type, x, y);
			platforms.add(platform);

			if (rand.nextFloat() > 0.9f && type != Platform.PLATFORM_TYPE_MOVING) {
				Spring spring = new Spring(platform.position.x, platform.position.y + Platform.PLATFORM_HEIGHT / 2 + Spring.SPRING_HEIGHT / 2);
				springs.add(spring);
			}

			if (y > WORLD_HEIGHT / 3 && rand.nextFloat() > 0.8f) {
				Dog dog = new Dog(platform.position.x + rand.nextFloat(), platform.position.y + Dog.DOG_HEIGHT + rand.nextFloat() * 2);
				dogs.add(dog);
			}
			if (rand.nextFloat() > 0.6f) {
				Food food = new Food(platform.position.x + rand.nextFloat(), platform.position.y + Food.FOOD_HEIGHT + rand.nextFloat() * 3);
				foods.add(food);
			}
			y += (maxJumpHeight - 0.5f);
			y -= rand.nextFloat() * (maxJumpHeight / 3);
		}
		castle = new Castle(WORLD_WIDTH / 2, y);
	}

	public void update(float deltaTime, float accelX) {
		updateCat(deltaTime, accelX);
		updatePlatforms(deltaTime);
		updateDogs(deltaTime);
		if (kitty.state != Cat.CAT_STATE_HIT)
			checkCollisions();
		checkGameOver();
	}

	private void updateCat(float deltaTime, float accelX) {
		if (kitty.state != Cat.CAT_STATE_HIT && kitty.position.y <= 0.5f)
			kitty.hitPlatform();
		if (kitty.state != Cat.CAT_STATE_HIT)
			kitty.velocity.x = -accelX / 10 * Cat.CAT_MOVE_VELOCITY;

		kitty.update(deltaTime);
		heightSoFar = Math.max(kitty.position.y, heightSoFar);
	}

	private void updatePlatforms(float deltaTime) {
		int len = platforms.size();
		for (int i = 0; i < len; i++) {
			Platform platform = platforms.get(i);
			platform.update(deltaTime);
			if (platform.state == Platform.PLATFORM_STATE_PULVERIZING && platform.stateTime > Platform.PLATFORM_PULVERIZE_TIME) {
				platforms.remove(platform);
				len = platforms.size();
			}
		}
	}

	private void updateDogs(float deltaTime) {
		for (int i = 0; i < dogs.size(); i++) {
			Dog dog = dogs.get(i);
			dog.update(deltaTime);
		}
	}

	private void checkCollisions() {
		checkPlatformCollisions();
		checkDogsCollisions();
		checkItemCollisions();
		checkCastleCollisions();
	}

	private void checkPlatformCollisions() {
		if (kitty.velocity.y > 0)
			return;
		
		for (int i = 0; i < platforms.size(); i++) {
			Platform platform = platforms.get(i);
			if (kitty.position.y > platform.position.y) {
				if (OverlapTester.overlapRectangles(kitty.bounds, platform.bounds)) {
					kitty.hitPlatform();
					listener.jump();
					if (rand.nextFloat() > 0.5f) {
						platform.pulverize();
					}
					break;
				}
			}
		}
	}

	private void checkDogsCollisions() {
		for (int i = 0; i < dogs.size(); i++) {
			Dog dog = dogs.get(i);
			if (OverlapTester.overlapRectangles(dog.bounds, kitty.bounds)) {
				kitty.hitDog();
				listener.hit();
			}
		}
	}

	private void checkItemCollisions() {
		int len = foods.size();
		for (int i = 0; i < len; i++) {
			Food food = foods.get(i);
			if (OverlapTester.overlapRectangles(kitty.bounds, food.bounds)) {
				foods.remove(food);
				len = foods.size();
				listener.food();
				score += Food.FOOD_SCORE;
			}
		}
		if (kitty.velocity.y > 0)
			return;
		for (int i = 0; i < springs.size(); i++) {
			Spring spring = springs.get(i);
			if (kitty.position.y > spring.position.y) {
				if (OverlapTester.overlapRectangles(kitty.bounds, spring.bounds)) {
					kitty.hitSpring();
					listener.highJump();
				}
			}
		}
	}

	private void checkCastleCollisions() {
		if (OverlapTester.overlapRectangles(castle.bounds, kitty.bounds)) {
			state = WORLD_STATE_NEXT_LEVEL;
		}
	}

	private void checkGameOver() {
		if (heightSoFar - 7.5f > kitty.position.y) {
			state = WORLD_STATE_GAME_OVER;
		}
	}

}
