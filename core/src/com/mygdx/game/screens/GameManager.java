package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Library;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.drawable.Button;
import com.mygdx.game.drawable.Colors;
import com.mygdx.game.drawable.Rectangle;
import com.mygdx.game.drawable.Text;

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

    boolean started = false;
    boolean countingDown = false;

	double life = 0;

    Color backgroundColor = Colors.rgb(251, 254, 249);
	Text title;
	Text counter;

    Button testButton = new Button(width/2, height/2, 80, 30);
    Rectangle[] rectangles = new Rectangle[9];
    Color[] colors = {
		    Colors.rgb(254, 215, 102),
		    Colors.rgb(42, 183, 202),
		    Colors.rgb(12, 98, 145),
		    Colors.rgb(254, 74, 73),
		    Colors.rgb(142, 40, 122)
    };

    boolean getFocused() {
    	return ((int)Library.random(0,100)%2) == 0;
    }

    GameManager(MyGdxGame main) {
        this.main = main;
        Gdx.input.setInputProcessor(this);
        mid = new Vector2(width/2, height/2);
        paddingPixels = (padding * width);

        int gameSize = (int)(width * (1 - 2 * padding));
        offset = gameSize / (float)gridSize;
        position = new Vector2();

	    title = new Text(this.main.font50, "Focus", width/2, height - height/(height/50));
	    title.color = Colors.rgb(42, 183, 202);
	    counter = new Text(this.main.font50, "3", width/2, height - 100);
	    counter.color = Colors.rgb(42, 183, 202);

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

    void setRandomRectangleColors() {
    }

    void restart() {
    	started = false;
    	countingDown = false;
    	life = 0;
	    testButton = new Button(width/2, height/2, 80, 30);
    }

    void startCountDown() {
    	countingDown = true;
    	counter.Visible = true;
    	testButton.Visible = false;
    	title.setText("Get Ready!");
    }
    void startGame() {
    	countingDown = false;
    	started = true;
    	counter.Visible = false;
    	title.setText("Focus");
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
	    }
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
        touchedDown = new Vector2(screenX, screenY);
	    testButton.checkForClick(screenX, height - screenY);
	    print("Mouse clicked ", new Vector2(screenX, screenY));
	    print("Min ", testButton.button.GetMin());
	    print("Max ", testButton.button.GetMax());
	    print("Position ", testButton.button.Position);

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