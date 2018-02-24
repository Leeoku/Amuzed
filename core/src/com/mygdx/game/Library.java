package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by ballah on 2/24/2018.
 */

public class Library {
    public static float random(float min, float max) {
        float random = (float)Math.random();
        float diff = max - min;
        float r = random * diff;
        return min + r;
    }
    public static float random() {
        return (float)Math.random();
    }

    //defualt 1920 x 1080
    public static int getFontSize(float fontSize) {
        float density = Gdx.graphics.getDensity();
        int size = (int)(fontSize * density);
        size = size <= 150 ? size : 150;
        return size;
    }

    public static Vector2 RandomVector(Vector2 min, Vector2 max) {
        Vector2 random = new Vector2(random(min.x, max.x), random(min.y, max.y));
        return random;
    }
}
