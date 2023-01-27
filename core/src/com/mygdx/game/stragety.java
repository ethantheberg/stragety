package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class stragety extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	Grid grid;
	static int column = 0;
	static int row = 0;

	@Override
	public void create() {
		grid = new Grid();
		batch = new SpriteBatch();
		Gdx.input.setInputProcessor(new InputAdapter() {
			boolean tracking = false;
			@Override
			public boolean keyDown(int keycode) {
				if (keycode == Input.Keys.SPACE) {
					grid.occupyNode(row, column);
					System.out.println("points: " + Integer.toString(grid.score()[0]) + " rp: " + Integer.toString(grid.score()[1]));
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



