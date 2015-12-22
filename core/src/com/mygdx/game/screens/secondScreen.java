package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.mygdx.game.entities.Player;

public class secondScreen implements Screen {

	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
	private OrthographicCamera camera;
	private Player player;

	private TextureAtlas playerAtlas;

	@Override
	public void show() {

		//Setting the current stage count
		MainMenu.stageCount = 2;
		
		//Loading the map
		map = new TmxMapLoader().load("maps/NewOneTwo.tmx");
		renderer = new OrthogonalTiledMapRenderer(map);

		//Loading the orthographic camera so that it follows the player
		camera = new OrthographicCamera(Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());
		camera.translate(camera.viewportWidth / 2, camera.viewportHeight / 2);

		//Creating the player sprint
		playerAtlas = new TextureAtlas("img/player/player.pack");
		Animation still, left, right;
		
		//Setting frames for animation in different direction
		still = new Animation(1 / 2f, playerAtlas.findRegions("still"));
		left = new Animation(1 / 6f, playerAtlas.findRegions("left"));
		right = new Animation(1 / 6f, playerAtlas.findRegions("right"));
		still.setPlayMode(PlayMode.LOOP);
		left.setPlayMode(PlayMode.LOOP);
		right.setPlayMode(PlayMode.LOOP);

		//Starting a new game with the player and the map
		player = new Player(still, left, right, (TiledMapTileLayer) map
				.getLayers().get(0));
		player.setPosition(11 * player.getCollisionLayer().getTileWidth(),
				(player.getCollisionLayer().getHeight() - 14)
						* player.getCollisionLayer().getTileHeight());

		Gdx.input.setInputProcessor(player);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		//Setting the initial camera position to the position of the player
		camera.position.set(player.getX() + player.getWidth() / 2,
				player.getY() + player.getHeight() / 2, 0);
		camera.update();

		renderer.setView(camera);
		renderer.render();

		//Rendering the sprite(player)
		renderer.getBatch().begin();
		player.draw(renderer.getBatch());
		renderer.getBatch().end();
	}

	@Override
	public void resize(int width, int height) {
		camera.viewportHeight = height;
		camera.viewportWidth = width;
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		dispose();

	}

	@Override
	public void dispose() {
		map.dispose();
		renderer.dispose();
	}

}
