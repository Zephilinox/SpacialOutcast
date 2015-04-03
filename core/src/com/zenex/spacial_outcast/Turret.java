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
    public boolean alive = true;

    private Sprite turret;
    private float health = 255.f;

    private ArrayList<Shot> shots = new ArrayList<Shot>();
    private float shotSpeed = 360;
    private int spread = 15;
    private int shotDelay = 300;
    private int shotTimer = 0;
    private int shotCount = 1;

    private CollisionInformation colInfo = new CollisionInformation();

    public Turret(final Vector2 pos)
    {
        Texture turretTex = new Texture("Turret.png");
        turretTex.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        turret= new Sprite(turretTex);
        Vector2 newPos = pos.cpy();
        turret.setOrigin(8, 8);
        newPos.x -= turret.getOriginX();
        newPos.y -= turret.getOriginY();
        turret.setPosition(newPos.x, newPos.y);
        colInfo.radius = 8;
        shotDelay = Utilities.randInt(100, 200);
        shotSpeed = Utilities.randInt(400, 600);
        shotCount = Utilities.randInt(1, 6);
        spread = shotCount;
    }

    public void update()
    {
        colInfo.position = Utilities.getOriginPosition(turret);

        Utilities.rotateToMouse(turret);

        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT) && shotTimer > shotDelay)
        {
            shotTimer = 0;
            for (int i = 0; i < shotCount; ++i)
            {
                Vector2 shotPos = Utilities.getDirectionVector(turret);
                shotPos.scl(turret.getHeight() / 2.f);
                shotPos.add(Utilities.getOriginPosition(turret));
                float shotRot = turret.getRotation() + (Utilities.randInt(-spread, spread));
                shots.add(new Shot(shotPos, shotRot, shotSpeed, Shot.Type.AllyTurret));
            }
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
        float healthAsColour = Math.max(health / 255.f, 0.2f) - 0.2f;
        turret.setColor(0.2f + healthAsColour, 0.2f + healthAsColour, 0.2f + healthAsColour, 1.f);

        if (health <= 0)
        {
            alive = false;
        }
    }

    public Vector2 getOriginPosition()
    {
        return Utilities.getOriginPosition(turret);
    }
}
