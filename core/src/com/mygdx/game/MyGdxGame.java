package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.screens.Splash;
import com.mygdx.game.screens.firstScreen;

public class MyGdxGame extends Game {
	SpriteBatch batch;
	Texture img;
	
	@Override
	public void create () {
		//setScreen(new firstScreen());
		setScreen(new Splash());
	}

	@Override
	public void render () {
		super.render();
	}
	
	public void dispose()
	{
		super.dispose();
	}
	
	public void resize(int width, int height)
	{
		super.resize(width, height);
	}
	
	public void pause()
	{
		super.pause();
	}
	
	public void resume()
	{
		super.resume();
	}
}
