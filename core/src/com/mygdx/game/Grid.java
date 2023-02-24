package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Grid {
    private ArrayList<List<Node>> Nodes;
    boolean coopbonus = false;
    public int bestX = 0;
    public int bestY = 3;
    private stragety strategy;

    public void FillNodes() {
        Nodes = new ArrayList<List<Node>>();
        Nodes.add(
                Arrays.asList(new Node(0), new Node(1), new Node(0), new Node(0), new Node(1), new Node(0), new Node(0),
                        new Node(1), new Node(0)));
        Nodes.add(
                Arrays.asList(new Node(0), new Node(1), new Node(0), new Node(0), new Node(1), new Node(0), new Node(0),
                        new Node(1), new Node(0)));
        Nodes.add(
                Arrays.asList(new Node(2), new Node(2), new Node(2), new Node(2), new Node(2), new Node(2), new Node(2),
                        new Node(2), new Node(2)));
    }

    public void occupyNode(int row, int column) {
        Nodes.get(row - 1).get(column - 1).occupied = !Nodes.get(row - 1).get(column - 1).occupied;
    }

    public Grid(stragety s) {
        this.strategy = s;
    }

    public int[] findPlacement(List allowedTypes, boolean[] RowSorting) {
        float bestScore = 0;
        float bestRP = 0;
        for (int i = 0; i < Nodes.size(); i++) {
            for (int j = 0; j < Nodes.get(i).size(); j++) {
                if (!Nodes.get(i).get(j).occupied && allowedTypes.contains(Nodes.get(i).get(j).type) && RowSorting[i]) {
                    Nodes.get(i).get(j).occupied = true;
                    float[] test = this.relativeScore();
                    Nodes.get(i).get(j).occupied = false;
                    if (test[2] > 1) {
                        bestX = i;
                        bestY = j;
                    }
                    if (test[0] > bestScore) {
                        bestScore = test[0];
                        bestX = i;
                        bestY = j;
                    }
                    if (test[0] >= bestScore && test[1] > bestRP) {
                        bestRP = test[1];
                        bestX = i;
                        bestY = j;
                    }
                }
            }
                }
        return new int[] { bestX, bestY };
    }
    public float[] absoluteScore() {
        int rp = 0;
        int score = 0;
        int linkAccum = 0;
        int nLinks = 0;
        for (int i = 0; i < Nodes.size(); i++) {
            for (int j = 0; j < Nodes.get(i).size(); j++) {
                if (Nodes.get(i).get(j).occupied) {
                    linkAccum += 1;
                    if (linkAccum == 3) {
                        score += 5;
                        nLinks += 1;
                        linkAccum = 0;
                    }
                    switch (i) {
                        case 0:
                            score += 5;
                            break;
                        case 1:
                            score += 3;
                            break;
                        case 2:
                            score += 2;
                            break;
                    }
                } else {
                    linkAccum = 0;
                }
            }
            linkAccum = 0;
        }
        if (score >= 26) {
            rp += 1;
        }
        if (nLinks >= (coopbonus ? 4 : 5)) {
            rp += 1;
        }
        return new float[] { score, rp };
    }

    public float[] relativeScore() {
        float rp = 0;
        float score = 0;
        int linkAccum = 0;
        int nLinks = 0;
        for (int i = 0; i < Nodes.size(); i++) {
            for (int j = 0; j < Nodes.get(i).size(); j++) {
                if (Nodes.get(i).get(j).occupied) {
                    linkAccum += 1;
                    switch (linkAccum) {
                        case 2:
                            score += 6;
                            break;
                        case 3:
                            score += 5;
                            nLinks += 1;
                            linkAccum = 0;
                            break;
                    }
                    switch (i) {
                        case 0:
                            score += 5;
                            break;
                        case 1:
                            score += 3;
                            break;
                        case 2:
                            score += 2;
                            break;
                    }
                    if (j >= 3 && j <= 5) {
                        rp += 0.5;
                    }
                } else {
                    linkAccum = 0;

                }
            }
            linkAccum = 0;
        }
        if (score >= 26) {
            rp += 1;
        }
        if (nLinks >= (coopbonus ? 4 : 5)) {
            rp += 1;
        }
        return new float[] { score, rp, linkAccum };
    }

    public void clear() {
        for(int i = 1; i <= 3; i++) {
            for(int j = 1; j <= 9; j++) {
                if(Nodes.get(i - 1).get(j - 1).occupied) {
                    occupyNode(i, j);
                }
            }
        }
    }


    public void draw(SpriteBatch b) {
        b.begin();
        for (int i = 0; i < Nodes.size(); i++) {
            for (int j = 0; j < Nodes.get(i).size(); j++) {
                Nodes.get(i).get(j).draw(j * 147, 2 * 147 - (i * 147), b, j == strategy.column-1 && i == strategy.row-1, j == bestY && i == bestX);
            }
        }
        b.end();
    }
}
