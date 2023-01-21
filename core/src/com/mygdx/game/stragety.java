package com.mygdx.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.ScreenUtils;

public class stragety extends ApplicationAdapter {

	static class node {
		int type;
		Boolean occupied;

		public node(int t) {
			type = t;
			occupied = false;
		}

		public void draw(int x, int y, SpriteBatch b) {
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
		}
	}

	static class Grid {
		ArrayList<List<node>> nodes;
		boolean coopbonus = false;

		public Grid() {
			nodes = new ArrayList<List<node>>();
			nodes.add(
					Arrays.asList(new node(0), new node(1), new node(0), new node(0), new node(1), new node(0), new node(0),
							new node(1), new node(0)));
			nodes.add(
					Arrays.asList(new node(0), new node(1), new node(0), new node(0), new node(1), new node(0), new node(0),
							new node(1), new node(0)));
			nodes.add(
					Arrays.asList(new node(2), new node(2), new node(2), new node(2), new node(2), new node(2), new node(2),
							new node(2), new node(2)));
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
					nodes.get(i).get(j).draw(j * 30, i * 30, b);
				}
			}
		}
	}

	SpriteBatch batch;
	Texture img;

	@Override
	public void create() {
		batch = new SpriteBatch();
	}

	@Override
	public void render() {
		ScreenUtils.clear(0, 0, 0, 1);
		ShapeRenderer sr = new ShapeRenderer();
		Grid grid = new Grid();
		batch.begin();
		grid.draw(batch);
		batch.end();
		sr.begin(ShapeType.Filled);
		sr.setColor(Color.RED);
		sr.rect(0, 0, 300, 20);
		sr.end();
	}

	@Override
	public void dispose() {
		batch.dispose();
		img.dispose();
	}

	public int findPlacement() {
		return 1;
	}
}