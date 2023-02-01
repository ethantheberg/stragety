package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Node {
    int type;
    Boolean occupied;
    Texture occupiedCone = new Texture("cone occupied.png");
    Texture unoccupiedCone = new Texture("cone unoccupied.png");
    Texture occupiedCube = new Texture("cube occupied.png");
    Texture unoccupiedCube = new Texture("cube unoccupied.png");
    Texture occupiedHybrid = new Texture("hybrid occupied.png");
    Texture unoccupiedHybrid = new Texture("hybrid unoccupied.png");
    Texture cursorSprite = new Texture("cursor.png");
    Texture algorithmCursor = new Texture("algorithmCursor.png");

    public Node(int t) {
        type = t;
        occupied = false;
    }

    public void draw(int x, int y, SpriteBatch b, boolean cursor, boolean recomendation) {
        switch (type) {
            case 0:
                b.draw(occupied ? occupiedCone : unoccupiedCone, x, y);
                break;
            case 1:
                b.draw(occupied ? occupiedCube : unoccupiedCube, x, y);
                break;
            case 2:
                b.draw(occupied ? occupiedHybrid : unoccupiedHybrid, x, y);
                break;
        }
        if (cursor) {
            b.draw(cursorSprite, x, y);
        }
        if (recomendation) {
            b.draw(algorithmCursor, x, y);
        }
    }
}
