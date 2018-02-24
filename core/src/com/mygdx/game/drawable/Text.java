package com.mygdx.game.drawable;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.drawable.enums.XAlignment;
import com.mygdx.game.drawable.enums.YAlignment;

/**
 * Created by isaiah on 2016-05-02.
 */
public class Text extends Drawable{
    BitmapFont Font;
    String text;
    XAlignment xAlignment = XAlignment.Center;
    YAlignment yAlignment = YAlignment.Top;
    public GlyphLayout layout;


    public Text (BitmapFont font, String text, float x, float y) {
        this.Font = font;
        this.Position = new Vector2(x, y);
        this.Color = Colors.Clouds;
        layout = new GlyphLayout(); //dont do this every frame! Store it as member
        setText(text);
    }
    public void resetLayout() {
        layout.setText(Font, text);
    }
    public void setText(String text) {
        this.text = text;
        resetLayout();
    }
    public void setXAlignment(XAlignment xAlignment) {
        this.xAlignment = xAlignment;
    }
    public void setYAlignment(YAlignment yAlignment) {
        this.yAlignment = yAlignment;
    }
    public Vector2 getAlignmentPosition() {
        float x, y;
        x = Position.x;
        y = Position.y;

        switch (xAlignment) {
            case Left:
                break;
            case Center:
                x -= layout.width/2f;
                break;
            case Right:
                x -= layout.width;
                break;
        }

        switch (yAlignment) {
            case Top:
                y -= layout.height;
                break;

            case Center:
                y -= layout.height/2f;
                break;

            case Bottom:
                break;
        }
        return new Vector2(x, y);
    }

    @Override
    public void render(Object object, boolean started) {
        SpriteBatch batch = (SpriteBatch) object;
        if (!started)
            batch.begin();

        batch.setColor(GetCurrentColor());
//        font.setColor(getCurrentColor());
        Vector2 p = getAlignmentPosition();

        Font.setColor(Color);
        Font.draw(batch, text, p.x, p.y);
        if (!started)
            batch.end();
    }
}