package com.mygdx.game.entities;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.screens.GameComplete;
import com.mygdx.game.screens.GameOver;

public class Player extends Sprite implements InputProcessor {
	private int health = 100;
	private int decrement = 1;
	private int maxHealth = health;
	private HealthBar healthBar;
	private Vector2 velocity = new Vector2();
	private float speed = 60 * 5, gravity = 60 * 1.8f, animationTime = 0;
	private boolean canJump = true;
	private TiledMapTileLayer collisionLayer;
	private Animation still, left, right;

	private String blockedKey = "blocked";
	private String fireKey = "fire";
	private String goalKey = "goal";

	public Player(Animation still, Animation left, Animation right,
			TiledMapTileLayer collisionLayer) {
		super(still.getKeyFrame(0));
		this.still = still;
		this.left = left;
		this.right = right;
		this.collisionLayer = collisionLayer;
		setSize(getWidth() * 3, getHeight() * 3);
		healthBar = new HealthBar(this, new Texture("img/player/HealthFG.png"),
				new Texture("img/player/HealthBG.png"));
	}

	@Override
	public void draw(Batch spriteBatch) {
		update(Gdx.graphics.getDeltaTime());
		super.draw(spriteBatch);
		healthBar.render(spriteBatch);

	}

	public void update(float delta) {
		velocity.y -= gravity * delta;
		if (velocity.y > speed) {
			velocity.y = speed;
		} else if (velocity.y < -speed) {
			velocity.y = -speed;
		}

		float oldX = getX();
		float oldY = getY();

		boolean collisionX = false, collisionY = false;
		boolean fireCollisionX = false, fireCollisionY = false;
		boolean goalCollisionX = false, goalCollisionY = false;
		float tileWidth = collisionLayer.getTileWidth();
		float tileHeight = collisionLayer.getTileHeight();

		// move on x
		setX(getX() + velocity.x * delta);

		if (velocity.x < 0) {// going left
			collisionX = collidesLeft(blockedKey);
			fireCollisionX = collidesLeft(fireKey);
			goalCollisionX = collidesLeft(goalKey);
		} else if (velocity.x > 0) {// going right
			collisionX = collidesRight(blockedKey);
			fireCollisionX = collidesRight(fireKey);
			goalCollisionX = collidesRight(goalKey);
		}

		// react to x collision
		if (collisionX) {
			setX(oldX);
			velocity.x = 0;
		}

		// react to x fire collision
		if (fireCollisionX) {
			health -= decrement;
			if (health < 0)
				((Game) Gdx.app.getApplicationListener())
						.setScreen(new GameOver());
		}

		if (goalCollisionX) {
			((Game) Gdx.app.getApplicationListener())
					.setScreen(new GameComplete());
		}

		// move on y
		setY(getY() + velocity.y * delta * 5f);

		if (velocity.y < 0) {// going down
			canJump = collisionY = collidesBottom(blockedKey);
			fireCollisionY = collidesBottom(fireKey);
			goalCollisionY = collidesBottom(goalKey);
		} else if (velocity.y > 0) {// going up
			collisionY = collidesTop(blockedKey);
			fireCollisionY = collidesTop(fireKey);
			goalCollisionY = collidesTop(goalKey);
		}

		// react to y collision
		if (collisionY) {
			setY(oldY);
			velocity.y = 0;
		}

		if (fireCollisionY) {
			health -= decrement;
			if (health < 0)
				((Game) Gdx.app.getApplicationListener())
						.setScreen(new GameOver());
		}

		if (goalCollisionY) {
			((Game) Gdx.app.getApplicationListener())
					.setScreen(new GameComplete());
		}
		// update animation
		animationTime += delta;
		setRegion(velocity.x < 0 ? left.getKeyFrame(animationTime)
				: velocity.x > 0 ? right.getKeyFrame(animationTime) : still
						.getKeyFrame(animationTime));

		// update healthBar
		healthBar.update();
	}

	private boolean isCellBlocked(float x, float y, String blockedKey) {
		Cell cell = collisionLayer.getCell(
				(int) (x / collisionLayer.getTileWidth()),
				(int) (y / collisionLayer.getTileHeight()));
		return cell != null && cell.getTile() != null
				&& cell.getTile().getProperties().containsKey(blockedKey);
	}

	public boolean collidesRight(String key) {
		for (float step = 0; step < getHeight(); step += collisionLayer
				.getTileHeight() / 2)
			if (isCellBlocked(getX() + getWidth(), getY() + step, key))
				return true;
		return false;
	}

	public boolean collidesLeft(String key) {
		for (float step = 0; step < getHeight(); step += collisionLayer
				.getTileHeight() / 2)
			if (isCellBlocked(getX(), getY() + step, key))
				return true;
		return false;
	}

	public boolean collidesTop(String key) {
		for (float step = 0; step < getWidth(); step += collisionLayer
				.getTileWidth() / 2)
			if (isCellBlocked(getX() + step, getY() + getHeight(), key))
				return true;
		return false;

	}

	public boolean collidesBottom(String key) {
		for (float step = 0; step < getWidth(); step += collisionLayer
				.getTileWidth() / 2)
			if (isCellBlocked(getX() + step, getY(), key))
				return true;
		return false;
	}

	public Vector2 getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector2 velocity) {
		this.velocity = velocity;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public float getGravity() {
		return gravity;
	}

	public void setGravity(float gravity) {
		this.gravity = gravity;
	}

	public TiledMapTileLayer getCollisionLayer() {
		return collisionLayer;
	}

	public void setCollisionLayer(TiledMapTileLayer collisionLayer) {
		this.collisionLayer = collisionLayer;
	}

	@Override
	public boolean keyDown(int keycode) {
		switch (keycode) {
		case Keys.SPACE:
			if (this.canJump) {
				velocity.y = speed / 2;
				this.canJump = false;
			}
			break;
		case Keys.LEFT:
			velocity.x = -speed;
			animationTime = 0;
			break;
		case Keys.RIGHT:
			velocity.x = speed;
			animationTime = 0;
			break;
		}
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		switch (keycode) {
		case Keys.LEFT:
		case Keys.RIGHT:
			velocity.x = 0;
			animationTime = 0;
			break;
		}
		return true;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

	private class HealthBar extends Sprite {
		private Sprite healthBarFG;
		private Sprite healthBarBG;
		private Player targetPlayer;

		public HealthBar(Player targetPlayer, Texture FG, Texture BG) {
			this.targetPlayer = targetPlayer;
			healthBarFG = new Sprite(FG);
			healthBarBG = new Sprite(BG);

			healthBarFG.setX(targetPlayer.getX());
			healthBarFG.setY(targetPlayer.getY() + targetPlayer.getHeight());
			healthBarBG.setX(targetPlayer.getX());
			healthBarBG.setY(targetPlayer.getY() + targetPlayer.getHeight());
			healthBarFG.setOrigin(0, 0);
		}

		public void update() {
			healthBarFG.setX(targetPlayer.getX() + 7);
			healthBarFG.setY(targetPlayer.getY() + targetPlayer.getHeight());
			healthBarBG.setX(targetPlayer.getX() + 7);
			healthBarBG.setY(targetPlayer.getY() + targetPlayer.getHeight());
			// if health goes down it shortens
			healthBarFG.setScale(targetPlayer.health
					/ (float) targetPlayer.maxHealth, 1f);
		}

		public void render(Batch batch) {
			healthBarBG.draw(batch);
			healthBarFG.draw(batch);
		}
	}
}
