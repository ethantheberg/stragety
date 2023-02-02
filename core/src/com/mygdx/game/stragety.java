package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;

public class stragety extends ApplicationAdapter {
	Texture img;
	Grid grid;
	static int column = 0;
	static int row = 0;

	@Override
	public void create() {
		grid = new Grid();
		Gdx.input.setInputProcessor(new InputAdapter() {
			boolean tracking = false;

			@Override
			public boolean keyDown(int keycode) {
				if (keycode == Keys.ENTER && tracking) {
					grid.occupyNode(row, column);
					System.out
							.println("points: " + grid.relativeScore()[0] + " rp: "
									+ grid.relativeScore()[1]);
					row = 0;
					tracking = false;
					System.out.println(row + " " + column);
				} else if (keycode == Keys.SPACE) {
					tracking = true;
					row = Grid.bestX + 1;
					column = Grid.bestY + 1;
					System.out.println(Grid.bestX + "; " + Grid.bestY);
				} else if (keycode - 7 >= 1 && keycode - 7 <= 9) {
					if (tracking) {
						if (column == keycode - 7) {
							row += 1;
						} else {
							row = 1;
							column = keycode - 7;
						}
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
		grid.draw();
		grid.findPlacement();
	}

	@Override
	public void dispose() {
		img.dispose();
	}

}



