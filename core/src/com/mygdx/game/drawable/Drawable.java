package com.mygdx.game.drawable;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public abstract class Drawable {

        public Color Color = Colors.MidnightBlue;
        public Color FadeColor = Colors.Purple;
        public float Transparency = 1;

        public Vector2 Position;
        public Vector2 Size;
        public Vector2 Velocity;
        public Vector2 OrginOffset;
        public float Life = 0;
        public float FadeTime = 2;
        public boolean Alive = true;
        public boolean WasDead = false;

        public void Update(float delta) {
            Life += delta;
            Position.x = Position.x + Velocity.x * delta;
            Position.y = Position.y + Velocity.y * delta;
        }

        public void reset() {
            Life = 0;
        }

        public Color GetCurrentColor() {
            if (Life < FadeTime)
                return Colors.interpolate(FadeColor, Color, FadeTime, Life);
            else
                return Color;
        }

        public Color GetTransparentColor() {
            Color c = GetCurrentColor();
            c.a = Transparency;
            return c;
        }

        public abstract void render(Object o, boolean started);

    }