package com.zenex.spacial_outcast;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class SpacialOutcast extends ApplicationAdapter
{
	SpriteBatch batch;
	PlayerBase base;
	Ship ship1;

	@Override
	public void create ()
	{
		batch = new SpriteBatch();
		base = new PlayerBase();
		ship1 = new Ship(new Vector2(0, 0), 120);
	}

	@Override
	public void render ()
	{
		update();
		draw();
	}

	public void update()
	{
		base.update();
		ship1.update();
	}

	public void draw()
	{
		Gdx.gl.glClearColor(40.f / 255.f, 40.f / 255.f, 40.f / 255.f, 255.f / 255.f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();

		base.draw(batch);
		ship1.draw(batch);

		batch.end();
	}
}
