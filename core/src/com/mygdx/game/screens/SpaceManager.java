package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Library;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.drawable.Button;
import com.mygdx.game.drawable.Colors;
import com.mygdx.game.drawable.Rectangle;
import com.mygdx.game.drawable.Text;

public class SpaceManager implements InputProcessor {
    MyGdxGame main;

    String TITLE = "Spaceman";
    int width = Gdx.graphics.getWidth();
    int height = Gdx.graphics.getHeight();

    public Rectangle background;

    boolean started = false;
    boolean countingDown = false;

    Color backgroundColor = Colors.MidnightBlue;
	Text title;
	Text counter;

    Button testButton = new Button(width/2, height - height/3, width/3, width/10);

    //Level and game data variables
	int points = 1;
	boolean t = false;
	double life = 0;

	Sprite character;
	Vector2 velocity = new Vector2();

    SpaceManager(MyGdxGame main) {
        this.main = main;
        Gdx.input.setInputProcessor(this);
        Texture texture = new Texture("spaceman.png");

        character = new Sprite(texture);

	    title = new Text(this.main.font50, TITLE, width/2, height - height*.11f);
	    title.color = Colors.rgb(42, 183, 202);
	    counter = new Text(this.main.font50, "3", width/2, height - 100);
	    counter.color = Colors.rgb(42, 183, 202);
	    counter.Visible = false;

	    background = new Rectangle(0, 0, width, height);
        background.color = backgroundColor;
        Runnable runnable = new Runnable() {
	        @Override
	        public void run() {
		        startCountDown();
	        }
        };
        testButton.setRunnable(runnable);
    }

    void restart() {}

    void startCountDown() {
    	countingDown = true;
    	counter.Visible = true;
    	testButton.Visible = false;
    	title.setText("Get Ready!");
    	life = 0;
    }


    void startGame() {
    	countingDown = false;
    	started = true;
    	counter.Visible = false;
    	title.setText(TITLE);
    }

    void print(String title, Vector2 vector) {
        System.out.println(title + " x: " + vector.x + ", y: " + vector.y);
    }

    public void update(float delta) {
    	if (countingDown && !started) {
    		life += delta;
    		if (life < 4) {
    			counter.setText(""+(3 - (int)life));
		    } else {
    			startGame();
		    }
	    } else {}
    }

    public void renderText() {
        main.batch.begin();
        title.render(main.batch, true);
        counter.render(main.batch, true);
        main.batch.end();
    }

    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        main.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        //rendering some shapes
        background.render(main.shapeRenderer, true);
	    testButton.render(main.shapeRenderer, true);

        main.shapeRenderer.end();

        renderText();

        main.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
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
	    testButton.checkForClick(screenX, height - screenY);
	    t = false;
	    return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        t = true;
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