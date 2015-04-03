package com.zenex.spacial_outcast;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Iterator;

public class PlayerBase
{
    private Sprite base = new Sprite(new Texture("Base.png"));
    private ArrayList<Turret> turrets = new ArrayList<Turret>();

    public PlayerBase()
    {
        Vector2 pos = Utilities.getScreenCenter(new Vector2(base.getOriginX(), base.getOriginY()));
        base.setPosition(pos.x, pos.y);

        generateTurrets(pos);
    }

    public void update()
    {
        Iterator<Turret> it = turrets.iterator();

        while (it.hasNext())
        {
            Turret t = it.next();

            if (t.alive)
            {
                t.update();
            }
            else
            {
                it.remove();
            }
        }
    }

    public void draw(SpriteBatch batch)
    {
        base.draw(batch);
        for (Turret t : turrets)
        {
            t.draw(batch);
        }
    }

    public void collisionCheck(final ArrayList<Ship> ships)
    {
        for (Turret t : turrets)
        {
            for (Ship s : ships)
            {
                t.collisionCheck(s);
                t.collisionCheck(s.getShots());
            }
        }
    }

    private void generateTurrets(final Vector2 pos)
    {
        ArrayList<Vector2> invalidPositions = new ArrayList<Vector2>();
        turrets.add(new Turret(Utilities.getScreenCenter()));
        invalidPositions.add(new Vector2(2, 2));

        while (turrets.size() < 5)
        {
            Vector2 randPos = new Vector2(Utilities.randInt(0, 4), Utilities.randInt(0, 4));
            boolean randPosValid = true;

            for (Vector2 invalidPos : invalidPositions)
            {
                if (randPos.x == invalidPos.x && randPos.y == invalidPos.y)
                {
                    randPosValid = false;
                }
            }

            if (randPosValid)
            {
                invalidPositions.add(randPos);
                turrets.add(new Turret(new Vector2(pos.x + (randPos.x * 16) + 8, pos.y + (randPos.y * 16) + 8)));
            }
        }
    }

    public int getTurretCount()
    {
        return turrets.size();
    }

    public Vector2 getRandomTurretPosition()
    {
        return turrets.get(Utilities.randInt(0, turrets.size() - 1)).getOriginPosition();
    }
}
