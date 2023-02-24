package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

public class Hud {
    Texture divider = new Texture("divider.png");
    boolean ConeSorting = true;
    boolean CubeSorting = true;
    boolean[] RowSorting =  {true, true, true};
    Node ConeButton = new Node(3);
    Node CubeButton = new Node(4);

    List<Integer> allowedTypes = new ArrayList<>(2);

    public void draw(SpriteBatch b, BitmapFont font) {
        b.begin();
        b.draw(divider, 0, 200);
        ConeButton.occupied = ConeSorting;
        ConeButton.draw(60, -200, b, false, false);
        font.draw(b, "Toggle cone sorting", 65, 90);
        font.draw(b, "(Press A)", 65, 75);
        CubeButton.occupied = CubeSorting;
        CubeButton.draw(260, -200, b, false, false);
        font.draw(b, "Toggle cube sorting", 265, 90);
        font.draw(b, "(Press S)", 265, 75);
        if(ConeSorting){ allowedTypes.add(0); }
        if(CubeSorting){ allowedTypes.add(1); }
        renderRowsDiagram(RowSorting, b);
        font.draw(b, "Toggle rows sorting", 495, 90);
        font.draw(b, "(Press R)", 630, 240);
        font.draw(b, "(Press F)", 660, 190);
        font.draw(b, "(Press V)", 690, 140);
        b.end();
    }

    public void renderRowsDiagram(boolean[] RowSorting, SpriteBatch b) {
        Vector2 coords = new Vector2(0, 0);
        Texture rowDiagramFilled = new Texture("rowDiagramFilled.png");
        Texture rowDiagramEmpty = new Texture("rowDiagramEmpty.png");
        Texture rowDiagramCurrent;
        for(int i = 1; i <= 3; i++) {
            coords.x = 430 + (i*30);
            coords.y = 200 - (i*50);
            if(RowSorting[i-1]) {
                rowDiagramCurrent = rowDiagramFilled;
            } else {
                rowDiagramCurrent = rowDiagramEmpty;
            }
            b.draw(rowDiagramCurrent, coords.x, coords.y);
        }
    }
}
