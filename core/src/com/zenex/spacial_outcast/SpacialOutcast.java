package com.zenex.spacial_outcast;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Iterator;

public class SpacialOutcast extends ApplicationAdapter
{
	SpriteBatch batch;
	PlayerBase base;
	ArrayList<Ship> ships;
	int spawnDelay = 1000;
	int spawnTimer = 0;

	@Override
	public void create ()
	{
		batch = new SpriteBatch();
		base = new PlayerBase();
		ships = new ArrayList<Ship>();
	}

	@Override
	public void render ()
	{
		update();
		draw();
	}

	public void update()
	{
		if (spawnTimer > spawnDelay)
		{
			spawnTimer = 0;
			int side = Utilities.randInt(0, 3);
			int offscreenOffset = 64;
			switch (side)
			{
				case 0:
				{
					ships.add(new Ship(new Vector2(-offscreenOffset, Utilities.randInt(-offscreenOffset, Gdx.graphics.getHeight() + offscreenOffset)), generateShipSpeed()));
					break;
				}

				case 1:
				{
					ships.add(new Ship(new Vector2(Gdx.graphics.getWidth() + offscreenOffset, Utilities.randInt(-offscreenOffset, Gdx.graphics.getHeight() + offscreenOffset)), generateShipSpeed()));
					break;
				}

				case 2:
				{
					ships.add(new Ship(new Vector2(Utilities.randInt(-offscreenOffset, Gdx.graphics.getWidth() + offscreenOffset), Gdx.graphics.getHeight() + offscreenOffset), generateShipSpeed()));
					break;
				}

				case 3:
				{
					ships.add(new Ship(new Vector2(Utilities.randInt(-offscreenOffset, Gdx.graphics.getWidth() + offscreenOffset), -offscreenOffset), generateShipSpeed()));
					break;
				}
			}
		}
		else
		{
			spawnTimer += (int)(Gdx.graphics.getDeltaTime() * 1000.f);
			spawnDelay--;
		}

		base.update();

		Iterator<Ship> it = ships.iterator();

		while (it.hasNext())
		{
			Ship s = it.next();

			if (s.alive)
			{
				s.update();
			}
			else
			{
				it.remove();
			}
		}

		base.collisionCheck(ships);
	}

	public void draw()
	{
		Gdx.gl.glClearColor(40.f / 255.f, 40.f / 255.f, 40.f / 255.f, 255.f / 255.f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();

		base.draw(batch);

		for (Ship s : ships)
		{
			s.draw(batch);
		}

		batch.end();
	}

	public int generateShipSpeed()
	{
		return Utilities.randInt(80, 160);
	}
}
