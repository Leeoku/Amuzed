package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.drawable.Colors;
import com.mygdx.game.drawable.Rectangle;
import com.mygdx.game.drawable.Text;
import com.mygdx.game.drawable.enums.XAlignment;
import com.mygdx.game.drawable.enums.YAlignment;

import java.util.ArrayList;

public class GameManager implements InputProcessor {
    MyGdxGame main;
    Vector2 touchedDown;
    Vector2 mid;
    Vector2 position;
    int gridSize = 6;
    float offset; //calculate based on the grid size
    float padding = 0.1f;
    float paddingPixels;

    int width = Gdx.graphics.getWidth();
    int height = Gdx.graphics.getHeight();

    public Rectangle rectangle;
    public Rectangle background;
    public Rectangle gameArea;

    float shotTime = 2;
    float shotTimer = 0.3f;
    boolean first = true;
    boolean started = false;

    Color backgroundColor = Colors.Clouds;//DarkPurple;

    int lifes = 0;
    int shotsFired  = 0;

    Text title;
    Text scoreText;
    Text highscoreText;
    Text hintText;
    Text rateText;
    Text cheerText;

    GameManager(MyGdxGame main) {
        this.main = main;
        Gdx.input.setInputProcessor(this);
        mid = new Vector2(width/2, height/2);
        paddingPixels = (padding * width);

        int gameSize = (int)(width * (1 - 2 * padding));
        offset = gameSize / (float)gridSize;
        position = new Vector2();

        title = new Text(this.main.font50, MyGdxGame.Title, width/2, height - height/(height/50));
        scoreText = new Text(this.main.font20, "Shots Fired " + 0, paddingPixels - offset/2f, title.Position.y);
        scoreText.Position.y += scoreText.layout.height;
        highscoreText = new Text(this.main.font20, "Highscore " + 0, width - (paddingPixels - offset/2f), scoreText.Position.y);

        hintText = new Text(this.main.font20, "Swipe Any Direction To Begin", width/2f, paddingPixels * 3.4f);
        rateText = new Text(this.main.font16, "Swipe Down To Rate", width/2f, paddingPixels / 2);

        scoreText.setYAlignment(YAlignment.Top);
        scoreText.setXAlignment(XAlignment.Left);
        highscoreText.setYAlignment(YAlignment.Top);
        highscoreText.setXAlignment(XAlignment.Right);
        hintText.setXAlignment(XAlignment.Center);
        hintText.setYAlignment(YAlignment.Bottom);
        rateText.setXAlignment(XAlignment.Center);
        rateText.setYAlignment(YAlignment.Bottom);

        rectangle = new Rectangle(0, 0, offset, offset);
        rectangle.Color = Colors.Clouds;
        rectangle.FadeColor = Colors.WetAsphalt;
        rectangle.OrginOffset = new Vector2(offset/2, offset/2);
        rectangle.FadeTime = 0.4f;

        background = new Rectangle(0, 0, width, height);
        background.Color = Colors.DarkPurple;
        background.FadeColor = Colors.WetAsphalt;
        background.FadeTime = 0.4f;

        gameArea = new Rectangle(paddingPixels - offset/2f, height/2f - offset*(gridSize + 1)/2f, offset*(gridSize + 1), offset*(gridSize + 1));
        gameArea.Color = Colors.Purple;
        gameArea.FadeColor = Colors.MidnightBlue;
        gameArea.FadeTime = 0.4f;

    }
    void print(String title, Vector2 vector) {
        System.out.println(title + " x: " + vector.x + ", y: " + vector.y);
    }





    public void update(float delta) {
        rectangle.Update(delta);
        background.Update(delta);
        gameArea.Update(delta);
    }

    public void renderSquare(ShapeRenderer shapeRenderer) {
        rectangle.render(shapeRenderer, true);
    }
    public void renderGameArea(ShapeRenderer shapeRenderer) {
        gameArea.render(shapeRenderer, true);
    }


    public void renderText() {
        main.batch.begin();
        float xOffset = Gdx.graphics.getPpiX()/5f;
        float yOffset = height - Gdx.graphics.getPpiY()/5f;

        title.render(main.batch, true);
        highscoreText.setText("Highscore " + main.preferences.getInteger("highscore", 0));
        highscoreText.render(main.batch, true);

        scoreText.setText("Shots Fired " + shotsFired);
        scoreText.render(main.batch, true);
        if (!started) {
            hintText.render(main.batch, true);
            rateText.render(main.batch, true);
        }


        main.batch.end();
    }

    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        main.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        background.render(main.shapeRenderer, true);
        renderGameArea(main.shapeRenderer);
        main.shapeRenderer.end();

        renderText();

        main.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        renderSquare(main.shapeRenderer);
        main.shapeRenderer.end();

    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        touchedDown = new Vector2(screenX, screenY);
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}