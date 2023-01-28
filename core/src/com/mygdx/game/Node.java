package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Node {
    int type;
    Boolean occupied;

    public Node(int t) {
        type = t;
        occupied = false;
    }

    public void draw(int x, int y, SpriteBatch b, boolean cursor, boolean recomendation) {
        switch (type) {
            case 0:
                b.draw(new Texture(occupied ? "cone occupied.png" : "cone unoccupied.png"), x, y);
                break;
            case 1:
                b.draw(new Texture(occupied ? "cube occupied.png" : "cube unoccupied.png"), x, y);
                break;
            case 2:
                b.draw(new Texture(occupied ? "hybrid occupied.png" : "hybrid unoccupied.png"), x, y);
                break;
        }
        if (cursor) {
            b.draw(new Texture("cursor.png"), x, y);
        }
        if (recomendation) {
            b.draw(new Texture("algorithmCursor.png"), x, y);
        }
    }
}
