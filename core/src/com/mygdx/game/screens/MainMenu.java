package com.mygdx.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class MainMenu implements Screen {
	private Stage stage;
	private TextureAtlas atlas;
	private Skin skin;
	private Table table;
	private TextButton buttonPlay, buttonExit;
	private BitmapFont white, black;
	private Label heading;

	public static int stageCount;

	// Fire Escaper background
	Texture splashTexture = new Texture("img/splash.png");
	private Image splashImage = new Image(splashTexture);

	@Override
	public void show() {
		stage = new Stage();

		// add background image for main menu
		stage.addActor(splashImage);

		// fonts
		white = new BitmapFont(
				Gdx.files.internal("font/arialBitmap_white.fnt"), false);
		black = new BitmapFont(
				Gdx.files.internal("font/arialBitmap_black.fnt"), false);

		atlas = new TextureAtlas("ui/button.pack");
		skin = new Skin(atlas);

		table = new Table(skin);
		table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		// Button style
		TextButtonStyle textButtonStyle = new TextButtonStyle();
		textButtonStyle.up = skin.getDrawable("button_up");
		textButtonStyle.down = skin.getDrawable("button_down");
		textButtonStyle.font = black;

		// create Play button
		buttonPlay = new TextButton("PLAY", textButtonStyle);
		// add ClickListener
		buttonPlay.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// if button is clicked, move to the game screen
				((Game) Gdx.app.getApplicationListener())
						.setScreen(new firstScreen());
			}
		});

		// create Exit Button
		buttonExit = new TextButton("EXIT", textButtonStyle);
		// add ClickListener
		buttonExit.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// if button is clicked, exit
				Gdx.app.exit();
			}
		});

		table.add(buttonPlay).padBottom(30).row();
		table.add(buttonExit).padBottom(30).row();
		table.setFillParent(true);
		stage.addActor(table);

		// make the buttons clickable
		Gdx.input.setInputProcessor(stage);

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		stage.act();
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void dispose() {
		stage.dispose();
	}

}
