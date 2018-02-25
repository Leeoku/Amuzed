package com.mygdx.game.drawable;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public abstract class Drawable {

        public Color color = Colors.MidnightBlue;
        public Color FadeColor = Colors.Purple;
        public float Transparency = 1;

        public Vector2 Position = new Vector2();
        public Vector2 Size = new Vector2();
        public Vector2 Velocity = new Vector2();
        public Vector2 OrginOffset = new Vector2();
        public float Life = 0;
        public float FadeTime = .5f;
        public boolean Alive = true;
        public boolean WasDead = false;
        public boolean Visible = true;
        public boolean Fade = false;

        public void Update(float delta) {
            Life += delta;
            Position.x = Position.x + Velocity.x * delta;
            Position.y = Position.y + Velocity.y * delta;
            color = GetCurrentColor();

    }

        public void reset() {
            Life = 0;
            Fade = false;
	        Color oldColor = color;
	        Life = 0;
        }

        public Color GetCurrentColor() {
        	if (!Fade) return color;

            if ((Life < FadeTime)) {
	            return Colors.interpolate(FadeColor, color, FadeTime, Life);
            } else {
	            reset();
	            color = Colors.interpolate(FadeColor, color, FadeTime, Life);
                return color;
            }

        }

        public Color GetTransparentColor() {
            Color c = GetCurrentColor();
            c.a = Transparency;
            return c;
        }

        public abstract void render(Object o, boolean started);

    }