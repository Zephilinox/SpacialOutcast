package com.zenex.spacial_outcast;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class PlayerBase
{
    Sprite base;
    ArrayList<Turret> turrets;

    public PlayerBase()
    {
        base = new Sprite(new Texture("Base.png"));
        Vector2 pos = Utilities.getScreenCenter(new Vector2(base.getOriginX(), base.getOriginY()));
        base.setPosition(pos.x, pos.y);
        turrets = new ArrayList<Turret>();
        turrets.add(new Turret(Utilities.getScreenCenter()));

        for (int i = 0; i < 4; ++i)
        {
            Vector2 randTurretPos;

            ArrayList<Vector2> invalidPositions = new ArrayList<Vector2>();
            invalidPositions.add(new Vector2(2, 2));
            boolean validPos = false;

            do
            {
                randTurretPos = new Vector2(Utilities.randInt(0, 4), Utilities.randInt(0, 4));

                for (int invPosI = 0; invPosI != invalidPositions.size(); ++invPosI)
                {
                    Vector2 invalidPos = invalidPositions.get(invPosI);
                    if (randTurretPos != invalidPos)
                    {
                        validPos = true;
                        invalidPositions.add(randTurretPos.cpy());
                        Vector2 tPos = new Vector2(pos.x + randTurretPos.x * 16 + 8, pos.y + randTurretPos.y * 16 + 8);
                        turrets.add(new Turret(tPos));
                        break;
                    }
                }
            } while (!validPos);

        }
    }

    public void update()
    {
        for (Turret t : turrets)
        {
            t.update();
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
}
