package com.zenex.spacial_outcast;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Iterator;

public class Turret
{
    Sprite turret;
    ArrayList<Shot> shots;
    float shotSpeed;

    public Turret(final Vector2 pos)
    {
        Vector2 newPos = pos.cpy();
        turret = new Sprite(new Texture("Turret.png"));
        turret.setOrigin(8, 8);
        newPos.x -= turret.getOriginX();
        newPos.y -= turret.getOriginY();
        turret.setPosition(newPos.x, newPos.y);
        shots = new ArrayList<Shot>();
        shotSpeed = 20;
    }

    public void update()
    {
        Utilities.rotateToMouse(turret);

        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT))
        {
            Vector2 pos = new Vector2(turret.getX() + turret.getOriginX(), turret.getY() + turret.getOriginY());
            shots.add(new Shot(pos, turret.getRotation(), shotSpeed));
        }

        Iterator<Shot> it = shots.iterator();

        while (it.hasNext())
        {
            Shot s = it.next();

            if (s.isAlive())
            {
                s.update();
            }
            else
            {
                it.remove();
            }
        }
    }

    public void draw(SpriteBatch batch)
    {
        turret.draw(batch);

        for (Shot s : shots)
        {
            s.draw(batch);
        }
    }
}
