package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
		private ArrayList<List<node>> nodes;
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

		public Grid(ArrayList<List<node>> n) {
			nodes = n;
		}

		public void occupyNode(int row, int column) {
			nodes.get(row - 1).get(column - 1).occupied = !nodes.get(row - 1).get(column - 1).occupied;
		}

		public float[] relativeScore() {
			float rp = 0;
			float score = 0;
			int linkAccum = 0;
			int nLinks = 0;
			for (int i = 0; i < nodes.size(); i++) {
				for (int j = 0; j < nodes.get(i).size(); j++) {
					if (nodes.get(i).get(j).occupied) {
						if (i >= 3 && i <= 5) {
							rp += 0.1;
						}
						linkAccum += 1;
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
						score += (float) linkAccum / 3.0f * 5.0f;
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

		public int[] findPlacement() {
			float bestScore = 0;
			int bestX = 0;
			int bestY = 0;
			for (int i = 0; i < nodes.size(); i++) {
				for (int j = 0; j < nodes.get(i).size(); j++) {
					if (!nodes.get(i).get(j).occupied) {
						nodes.get(i).get(j).occupied = true;
						float[] test = this.relativeScore();
						nodes.get(i).get(j).occupied = false;
						if (test[0] > bestScore) {
							bestScore = test[0];
							bestX = i;
							bestY = j;
						}
					}
				}
			}
			return new int[] { bestX, bestY };
		}

		public Grid generateGrid(int row, int column) {
			Grid clone = new Grid(nodes);
			clone.nodes.get(row).get(column).occupied = true;
			return clone;
		}

		public void draw(SpriteBatch b) {
			for (int i = 0; i < nodes.size(); i++) {
				for (int j = 0; j < nodes.get(i).size(); j++) {
					nodes.get(i).get(j).draw(j * 147, 2 * 147 - (i * 147), b);
				}
			}
		}

		public int[] absoluteScore() {
			int rp = 0;
			int score = 0;
			int linkAccum = 0;
			int nLinks = 0;
			for (int i = 0; i < nodes.size(); i++) {
				for (int j = 0; j < nodes.get(i).size(); j++) {
					if (nodes.get(i).get(j).occupied) {
						linkAccum += 1;
						if (linkAccum == 3) {
							score += 5;
							nLinks += 0.6;
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
			return new int[] { score, rp };
		}
	}

	SpriteBatch batch;
	Texture img;
	Grid grid;
	int[] nextCoords = new int[2];

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
					System.out
							.println("points: " + Float.toString(grid.relativeScore()[0]) + " rp: "
									+ Float.toString(grid.relativeScore()[1]));
					row = 0;
					tracking = false;
					System.out.println(row + " " + column);
					nextCoords = grid.findPlacement();
					System.out.println((nextCoords[0]+1) + ", " + (nextCoords[1]+1));
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

}