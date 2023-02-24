package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Node {
    int type;
    int yOffset = 300;
    Boolean occupied;
    Texture occupiedCone = new Texture("cone occupied.png");
    Texture unoccupiedCone = new Texture("cone unoccupied.png");
    Texture occupiedCube = new Texture("cube occupied.png");
    Texture unoccupiedCube = new Texture("cube unoccupied.png");
    Texture occupiedHybrid = new Texture("hybrid occupied.png");
    Texture unoccupiedHybrid = new Texture("hybrid unoccupied.png");
    Texture cursorSprite = new Texture("cursor.png");
    Texture algorithmCursor = new Texture("algorithmCursor.png");
    Texture occupiedConeButton = new Texture("cone button occupied.png");
    Texture unoccupiedConeButton = new Texture("cone button unoccupied.png");
    Texture occupiedCubeButton = new Texture("cube button occupied.png");
    Texture unoccupiedCubeButton = new Texture("cube button unoccupied.png");




    public Node(int t) {
        type = t;
        occupied = false;
    }

    public void draw(int x, int y, SpriteBatch b, boolean cursor, boolean recomendation) {
        switch (type) {
            case 0:
                b.draw(occupied ? occupiedCone : unoccupiedCone, x, y + yOffset);
                break;
            case 1:
                b.draw(occupied ? occupiedCube : unoccupiedCube, x, y + yOffset);
                break;
            case 2:
                b.draw(occupied ? occupiedHybrid : unoccupiedHybrid, x, y + yOffset);
                break;
            case 3:
                b.draw(occupied ? occupiedConeButton : unoccupiedConeButton, x, y + yOffset);
                break;
            case 4:
                b.draw(occupied ? occupiedCubeButton : unoccupiedCubeButton, x, y + yOffset);
                break;
        }
        if (cursor) {
            b.draw(cursorSprite, x, y + yOffset);
        }
        if (recomendation) {
            b.draw(algorithmCursor, x, y + yOffset);
        }
    }
}
