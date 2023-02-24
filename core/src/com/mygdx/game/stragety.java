package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class stragety extends ApplicationAdapter {
	Grid grid;
	Hud hud;
	int column = 0;
	int row = 0;
	SpriteBatch batch;
	BitmapFont font;


	@Override
	public void create() {
		batch = new SpriteBatch();
		font = new BitmapFont();
		grid = new Grid(this);
		grid.FillNodes();
		hud = new Hud();
		Gdx.input.setInputProcessor(new InputAdapter() {
			boolean tracking = false;

			@Override
			public boolean keyDown(int keycode) {
				if (keycode == Keys.SPACE && tracking) {
					grid.occupyNode(row, column);
					System.out
							.println("points: " + grid.relativeScore()[0] + " rp: "
									+ grid.relativeScore()[1]);
					row = 0;
					tracking = false;
					System.out.println(row + " " + column);
				} else if (keycode == Keys.ENTER) {
					tracking = true;
					row = grid.bestX + 1;
					column = grid.bestY + 1;
					System.out.println(grid.bestX + "; " + grid.bestY);
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
				} else if (keycode == Keys.A) {
					hud.ConeSorting = !hud.ConeSorting;
				} else if (keycode == Keys.S) {
					hud.CubeSorting = !hud.CubeSorting;
				} else if (keycode == Keys.R) {
					hud.RowSorting[0] = !hud.RowSorting[0];
				} else if (keycode == Keys.F) {
					hud.RowSorting[1] = !hud.RowSorting[1];
				} else if (keycode == Keys.V) {
					hud.RowSorting[2] = !hud.RowSorting[2];
				} else if (keycode == Keys.L) {
					grid.clear();
				}
				return true; // return true to indicate the event was handled
			}
		});
	}

	@Override
	public void render() {
		ScreenUtils.clear(0, 0, 0, 1);
		grid.draw(batch);
		hud.draw(batch, font);
		grid.findPlacement(hud.allowedTypes, hud.RowSorting);
		hud.allowedTypes.clear();
		hud.allowedTypes.add(2);
	}

	@Override
	public void dispose() {

	}

}



