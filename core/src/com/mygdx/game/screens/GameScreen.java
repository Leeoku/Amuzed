package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.mygdx.game.MyGdxGame;

public class GameScreen implements Screen {
    public GameManager gameManager;
    public MyGdxGame main;
    float step = 1/60f;
    float accum = 0;
    float second = 1;
    float accum2 = 0;
    float fps = 0;

    public GameScreen(MyGdxGame main) {
        this.main = main;
        gameManager = new GameManager(main);
        fps = Math.round(1/ Gdx.graphics.getDeltaTime());
    }

    @Override
    public void show() {}

    void update (float delta) {
        accum += delta;
        if (accum >= step) {
            accum -= step;
            gameManager.update(step);
        }
        accum2 += delta;
        if (accum2 >= second) {
            accum2 -= second;
            fps = Math.round(1/ Gdx.graphics.getDeltaTime());
        }
    }
    public void renderFPS() {
        main.batch.begin();
        main.font16.draw(main.batch, "FPS: "+String.valueOf(fps), 50, 50);
        main.batch.end();
    }

    @Override
    public void render(float delta) {
        update(delta);
        gameManager.render();
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {}
}