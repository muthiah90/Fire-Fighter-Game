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
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class GameOver implements Screen {

	private Stage stage;
	private TextureAtlas atlas;
	private Skin skin;
	private Table table;
	private TextButton buttonMenu, buttonRestart;
	private BitmapFont white, black;

	// Game complete background
	Texture completeTexture = new Texture("img/GameOver.png");
	private Image completeImage = new Image(completeTexture);

	@Override
	public void show() {
		stage = new Stage();

		// add background image for main menu
		stage.addActor(completeImage);

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

		// create Restart Button
		buttonRestart = new TextButton("RESTART", textButtonStyle);
		// add ClickListener
		buttonRestart.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// if button is clicked, restart the game
				if (MainMenu.stageCount == 1) {
					((Game) Gdx.app.getApplicationListener())
							.setScreen(new firstScreen());
				} else {
					((Game) Gdx.app.getApplicationListener())
							.setScreen(new secondScreen());
				}
			}

		});

		// create Main Menu button
		buttonMenu = new TextButton("MAIN MENU", textButtonStyle);
		// add ClickListener
		buttonMenu.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// if button is clicked, move to the Main Menu screen
				((Game) Gdx.app.getApplicationListener())
						.setScreen(new MainMenu());
			}
		});

		table.add(buttonRestart).padTop(30).row();
		table.add(buttonMenu).padTop(60).row();
		table.setFillParent(true);
		// table.debug();
		stage.addActor(table);

		// make the buttons clickable
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// Table.drawDebug(stage);

		stage.act();
		stage.draw();

	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

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
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		stage.dispose();
	}
}
