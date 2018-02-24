package com.mygdx.game.drawable;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by ballah on 2/24/2018.
 */

public class Button extends Drawable {
	Rectangle button = new Rectangle(0,0,50,20);
	Rectangle shadow = new Rectangle(0, 0, 30, 5);
	Rectangle background = new Rectangle(0, 0, 30, 5);
	Color color;
	Runnable runnable;

	public Button(int x, int y, int width, int height) {
		setSize(width, height);
		setPosition(x, y);
		setColor(Colors.Blue);
	}
	void setColor(Color color) {
		this.color = color;
		button.color = color;
		shadow.color = Colors.interpolate(color, Color.BLACK, 1, .2f);
		background.color = Colors.rgb(200,200,200);
	}
	void setPosition(int x, int y) {
		Vector2 half = new Vector2(background.Size.x/2, 0);
		background.Position.set(x,y).sub(half);
		button.Position.set(x,y).sub(half);
		shadow.Position.set(x,y).sub(half);
	}

	void setSize(int x, int y) {
		button.Size = new Vector2(x,y);
		shadow.Size = new Vector2(x,5);
		background.Size = new Vector2(x+10, y);

		shadow.OrginOffset = (new Vector2(0,button.Size.y/2 -15));
		background.OrginOffset = (new Vector2(5, 5));
		setPosition((int)Size.x, (int)Size.y);
	}

	public void checkForClick(int x, int y) {
		if (button.isCollide(x, y)) {
			runnable.run();
		}
	}

	@Override
	public void render(Object o, boolean started) {
		background.render(o, started);
		button.render(o, started);
		shadow.render(o, started);

	}
}
