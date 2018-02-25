package com.mygdx.game.drawable;

/**
 * Created by ballah on 2/24/2018.
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class Rectangle extends Drawable {
    public Vector2 Position;
    public Vector2 Size;
    public Vector2 Velocity;
    public Vector2 OrginOffset;

    public Rectangle(float x, float y, float width, float height) {
        Position = new Vector2(x, y);
        Size = new Vector2(width, height);
        Velocity = new Vector2();
        OrginOffset = new Vector2();
    }

    public Rectangle(Vector2 position, Vector2 size) {
        this.Position = position;
        this.Size = size;
        Velocity = new Vector2();
        OrginOffset = new Vector2();
    }

    public Vector2 GetMin() {
        return new Vector2(Position.x - OrginOffset.x, Position.y - OrginOffset.y);
    }

    public Vector2 GetMax() { return new Vector2(Position.x - OrginOffset.x + Size.x, Position.y - OrginOffset.y + Size.y);}

	public boolean isCollide(Rectangle rect) {
		if (rect.GetMax().y < GetMin().y) return false;
		if (rect.GetMax().x < GetMin().x) return false;
		if (rect.GetMin().y > GetMax().y) return false;
		if (rect.GetMin().x > GetMax().x) return false;
		return true;
	}

	public boolean isCollide(int x, int y) {
		if (y < GetMin().y) return false;
		if (x < GetMin().x) return false;
		if (y > GetMax().y) return false;
		if (x > GetMax().x) return false;
		return true;
	}

	public void roundedRect(Object object) {
    	float x = Position.x;
    	float y = Position.y;
    	float width = Size.x;
    	float height = Size.y;
    	float radius = 15;
		ShapeRenderer shapeRenderer = (ShapeRenderer)object;



		// Central rectangle
		shapeRenderer.rect(x + radius, y + radius, width - 2*radius, height - 2*radius);

		// Four side rectangles, in clockwise order
		shapeRenderer.rect(x + radius, y, width - 2*radius, radius);
		shapeRenderer.rect(x + width - radius, y + radius, radius, height - 2*radius);
		shapeRenderer.rect(x + radius, y + height - radius, width - 2*radius, radius);
		shapeRenderer.rect(x, y + radius, radius, height - 2*radius);

		// Four arches, clockwise too
		shapeRenderer.arc(x + radius, y + radius, radius, 180f, 90f);
		shapeRenderer.arc(x + width - radius, y + radius, radius, 270f, 90f);
		shapeRenderer.arc(x + width - radius, y + height - radius, radius, 0f, 90f);
		shapeRenderer.arc(x + radius, y + height - radius, radius, 90f, 90f);
	}

    public void render(Object object, boolean started) {
	    if (!Visible) return;

	    ShapeRenderer shapeRenderer = (ShapeRenderer)object;

        if (Transparency < 1) {
            if (started) {
                shapeRenderer.end();
                Gdx.gl.glEnable(GL20.GL_BLEND);
                Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
                shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            } else {
                Gdx.gl.glEnable(GL20.GL_BLEND);
                Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            }
        }

        if (!started)
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);


        shapeRenderer.setColor(GetTransparentColor());
//        shapeRenderer.rect(Position.x - OrginOffset.x, Position.y - OrginOffset.y, Size.x, Size.y);
	    roundedRect(object);

        if (!started)
            shapeRenderer.end();

        if (Transparency < 1) {
            if (shapeRenderer.isDrawing()) {
                shapeRenderer.end();
                Gdx.gl.glDisable(GL20.GL_BLEND);
                shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            } else {
                Gdx.gl.glDisable(GL20.GL_BLEND);
            }

        }

    }
}