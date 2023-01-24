package com.mygdx.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
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
					nodes.get(i).get(j).draw(j * 147, 2 * 147 - (i * 147), b);
				}
			}
		}
	}

	SpriteBatch batch;
	Texture img;
	Grid grid;

	@Override
	public void create() {
		grid = new Grid();
		batch = new SpriteBatch();
		Gdx.input.setInputProcessor(new InputAdapter() {
			int column = 0;
			int row = 0;
			boolean tracking = false;

			@Override
			public boolean keyDown(int keycode) {
				if (keycode == Keys.SPACE) {
					grid.occupyNode(row, column);

					row = 0;
					tracking = false;
					System.out.println(row + " " + column);
				} else if (keycode - 7 >= 1 && keycode - 7 <= 9) {
					if (tracking) {
						column = keycode - 7;
						row += 1;
						if (row >= 4) {
							row = 1;
						}
						System.out.println(row + " " + column);
					} else {
						column = keycode - 7;
						tracking = true;
						row = 1;
						System.out.println(row + " " + column);
					}
				}
				return true; // return true to indicate the event was handled
			}
		});
	}

	@Override
	public void render() {
		ScreenUtils.clear(0, 0, 0, 1);
		batch.begin();
		grid.draw(batch);
		batch.end();
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