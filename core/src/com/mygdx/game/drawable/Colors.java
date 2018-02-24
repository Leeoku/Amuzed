package com.mygdx.game.drawable;

import com.badlogic.gdx.graphics.Color;
import com.mygdx.game.Library;

/**
 * Created by ballah on 2/24/2018.
 */

public class Colors {
    public static Color rgb(int r, int g, int b) {
        return new Color(r/255f, g/255f, b/255f, 1);
    }
    public static Color Purple = rgb(155, 89, 182);
    public static Color DarkPurple = rgb(142, 68, 173);
    public static Color Clouds = rgb(236, 240, 241);
    public static Color Silver = rgb(189, 195, 199);
    public static Color MidnightBlue = rgb(44, 62, 80);
    public static Color WetAsphalt = rgb(52, 73, 94);
    public static Color Blue = rgb(52, 152, 219);
    public static Color DarkBlue = rgb(41, 128, 185);
    public static Color Red = rgb(231, 76, 60);
    public static Color DarkRed = rgb(192, 57, 43);
    public static Color Green = rgb(46, 204, 113);
    public static Color DarkGreen = rgb(39, 174, 96);

    public static Color interpolate(Color start, Color end, float totalTime, float currentTime) {
        float fraction = currentTime / totalTime;
        float rExtra = end.r - start.r;
        float gExtra = end.g - start.g;
        float bExtra = end.b - start.b;
        float aExtra = end.a - start.a;

        Color currentColor = new Color(rExtra * fraction + start.r, gExtra * fraction + start.g, bExtra * fraction + start.b, aExtra * fraction + start.a);
        return currentColor;
    }

    public static boolean sameColors(Color a, Color b) {
        if (a.r != b.r) return false;
        if (a.g != b.g) return false;
        if (a.b != b.b) return false;
        if (a.a != b.a) return false;
        return true;
    }

    public static Color offsetColor(Color baseColor, float maxOffset){
        return new Color(baseColor.r + Library.random(0,maxOffset),baseColor.g + Library.random(0,maxOffset),baseColor.b + Library.random(0,maxOffset), 1);
    }

}