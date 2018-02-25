package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.screens.GameScreen;

public class MyGdxGame extends Game {
	public static int Width = (int)(1080 * 0.3);
	public static int Height = (int)(1920 * 0.3);
	public static String Title = "Square Swipe";

	public SpriteBatch batch;
	public ShapeRenderer shapeRenderer;
	public GameScreen currentScreen;
	public BitmapFont font16, font20, font50;
	public Preferences preferences;

	public static final String GooglePlayLink = "https://play.google.com/store/apps/developer?id=iball";
	public void focusing(boolean isUserCalm) {
		if (currentScreen != null) {
			currentScreen.focusing(isUserCalm);
		}
	}

	@Override
	public void create () {
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();

		// freetype font
		FreeTypeFontGenerator openSans = new FreeTypeFontGenerator(Gdx.files.internal("OpenSans-Regular.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = (int)(16 * Gdx.graphics.getDensity());
		font16 = openSans.generateFont(parameter);

		parameter.size = Library.getFontSize(50);
		System.out.println("size density " + parameter.size);

		font50 = openSans.generateFont(parameter);

		parameter.size = (int)(20 * Gdx.graphics.getDensity());
		font20 = openSans.generateFont(parameter);

		preferences = Gdx.app.getPreferences(Title + " Preferences");

		currentScreen = new GameScreen(this);
		setScreen(currentScreen);
	}

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void dispose () {
		batch.dispose();
		shapeRenderer.dispose();
	}
}
