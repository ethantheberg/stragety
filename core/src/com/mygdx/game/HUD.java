package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class HUD {
    Texture divider = new Texture("divider.png");
    public void draw() {
        SpriteBatch b = new SpriteBatch();
        b.begin();
        b.draw(divider, 147 * 9 / 2, 200);
    }
}
