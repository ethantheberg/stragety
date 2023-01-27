package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Grid {
    ArrayList<List<Node>> nodes;
    boolean coopbonus = false;

    public Grid() {
        nodes = new ArrayList<>();
        nodes.add(
                Arrays.asList(new Node(0), new Node(1), new Node(0), new Node(0), new Node(1), new Node(0), new Node(0),
                        new Node(1), new Node(0)));
        nodes.add(
                Arrays.asList(new Node(0), new Node(1), new Node(0), new Node(0), new Node(1), new Node(0), new Node(0),
                        new Node(1), new Node(0)));
        nodes.add(
                Arrays.asList(new Node(2), new Node(2), new Node(2), new Node(2), new Node(2), new Node(2), new Node(2),
                        new Node(2), new Node(2)));
    }

    public void occupyNode(int row, int column) {
        nodes.get(row - 1).get(column - 1).occupied = !nodes.get(row - 1).get(column - 1).occupied;
    }

    public int[] score() {
        int rp = 0;
        int score = 0;
        int linkAccum = 0;
        int nLinks = 0;
        for (int i = 0; i < nodes.size(); i++) {
            for (int j = 0; j < nodes.get(i).size(); j++) {
                if (nodes.get(i).get(j).occupied) {
                    linkAccum += 1;
                } else {
                    linkAccum = 0;
                }
                if (linkAccum == 3) {
                    score += 5;
                    nLinks += 1;
                    linkAccum = 0;
                }
                if (i == 0) {
                    score += nodes.get(i).get(j).occupied ? 5 : 0;
                } else if (i == 1) {
                    score += nodes.get(i).get(j).occupied ? 3 : 0;
                } else if (i == 2) {
                    score += nodes.get(i).get(j).occupied ? 2 : 0;
                }
            }
        }
        if (score >= 26) {
            rp += 1;
        }
        if (nLinks >= (coopbonus ? 4 : 5)) {
            rp += 1;
        }
        return new int[] { score, rp };
    }

    public void draw(SpriteBatch b) {
        for (int i = 0; i < nodes.size(); i++) {
            for (int j = 0; j < nodes.get(i).size(); j++) {
                nodes.get(i).get(j).draw(j * 147, 2 * 147 - (i * 147), b, j == stragety.column-1 && i == stragety.row-1);
            }
        }
    }
}
