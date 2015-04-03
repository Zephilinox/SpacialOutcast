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
    Sprite turret = new Sprite(new Texture("Turret.png"));
    ArrayList<Shot> shots = new ArrayList<Shot>();
    float shotSpeed = 360;
    int spread = 15;
    int shotDelay = 300;
    int shotTimer = 0;
    CollisionInformation colInfo = new CollisionInformation();
    float health = 255.f;
    boolean alive = true;

    public Turret(final Vector2 pos)
    {
        Vector2 newPos = pos.cpy();
        turret.setOrigin(8, 8);
        newPos.x -= turret.getOriginX();
        newPos.y -= turret.getOriginY();
        turret.setPosition(newPos.x, newPos.y);
        colInfo.radius = 8;
        shotDelay = Utilities.randInt(100, 200);
        shotSpeed = Utilities.randInt(240, 480);
        spread = Utilities.randInt(0, 15);
    }

    public void update()
    {
        colInfo.position = Utilities.getOriginPosition(turret);

        Utilities.rotateToMouse(turret);

        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT) && shotTimer > shotDelay)
        {
            shotTimer = 0;
            shots.add(new Shot(Utilities.getOriginPosition(turret), turret.getRotation() + (Utilities.randInt(-spread, spread)), shotSpeed, Shot.Type.AllyTurret));
            shots.add(new Shot(Utilities.getOriginPosition(turret), turret.getRotation() + (Utilities.randInt(-spread, spread)), shotSpeed, Shot.Type.AllyTurret));
            shots.add(new Shot(Utilities.getOriginPosition(turret), turret.getRotation() + (Utilities.randInt(-spread, spread)), shotSpeed, Shot.Type.AllyTurret));
            shots.add(new Shot(Utilities.getOriginPosition(turret), turret.getRotation() + (Utilities.randInt(-spread, spread)), shotSpeed, Shot.Type.AllyTurret));
        }
        else
        {
            shotTimer += (int)(Gdx.graphics.getDeltaTime() * 1000.f);
        }

        Iterator<Shot> it = shots.iterator();

        while (it.hasNext())
        {
            Shot s = it.next();

            if (s.alive)
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

    public void collisionCheck(final Ship ship)
    {
        for (Shot shot : shots)
        {
            if (Utilities.isColliding(shot.getCollisionInformation(), ship.getCollisionInformation()))
            {
                shot.onCollision();
                ship.onCollision();
            }
        }
    }

    public void collisionCheck(final ArrayList<Shot> shots)
    {
        for (Shot shot : shots)
        {
            if (Utilities.isColliding(shot.getCollisionInformation(), colInfo))
            {
                shot.onCollision();
                onCollision();
            }
        }
    }

    public void onCollision()
    {
        health -= 5;
        turret.setColor(1.f, 1.f, 1.f, 0.5f + (health / 255.f / 2));

        if (health <= 0)
        {
            alive = false;
        }
    }
}
