package com.zenex.spacial_outcast;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import java.util.Iterator;

public class Ship
{
    public boolean alive = true;

    private Sprite ship;
    private float speed;
    private float health = 255.f;

    private int minOrbitDistance = 80;
    private int maxOrbitDistance = 160;
    private int orbitStepDistance = 10;
    private int orbitSteps = (maxOrbitDistance-minOrbitDistance) / orbitStepDistance;
    private int orbitDirection = 0;
    private float orbitDistance;
    private boolean orbiting = false;

    private CollisionInformation colInfo = new CollisionInformation();

    private Array<Shot> shots = new Array<Shot>();
    private float shotDelay = 300;
    private float shotTimer = 0;

    PlayerBase base;

    public Ship(final Vector2 pos, float speed, final PlayerBase base)
    {
        Texture shipTexture = new Texture("Ship.png");
        shipTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        ship = new Sprite(shipTexture);

        this.base = base;
        ship.setOriginCenter();
        ship.setPosition(pos.x - ship.getOriginX(), pos.y - ship.getOriginY());
        this.speed = speed;
        orbitDistance = minOrbitDistance + (Utilities.randInt(0, orbitSteps) * orbitStepDistance);
        colInfo.radius = ship.getWidth()/2.f;

        if (Utilities.randInt(0, 1) == 0)
        {
            orbitDirection = -1;
        }
        else
        {
            orbitDirection = 1;
        }
    }

    public void update()
    {
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

        colInfo.position = Utilities.getOriginPosition(ship);

        Utilities.rotateToVec(ship, Utilities.getScreenCenter());

        float distToCenter = Utilities.distToVec(ship, Utilities.getScreenCenter());

        if (distToCenter > orbitDistance)
        {
            float screenSize = (Gdx.graphics.getWidth() + Gdx.graphics.getHeight()) / 2.f;
            float dist = screenSize - orbitDistance;
            float percentage = Math.min(distToCenter, dist) / dist;
            //System.out.print(distToCenter + " | " + percentage + " | " + speed * percentage * Gdx.graphics.getDeltaTime() + "\n");
            Utilities.moveAlongRotation(ship, speed * percentage * Gdx.graphics.getDeltaTime());
        }
        else
        {
            orbiting = true;
        }

        Vector2 moveDir = Utilities.getDirectionVector(ship);
        moveDir.rotate(90);

        moveDir.scl(orbitDirection * speed * Gdx.graphics.getDeltaTime());
        Utilities.move(ship, moveDir);

        float healthAsColour = Math.max(health / 255.f, 0.2f) - 0.2f;
        ship.setColor(1.f, 0.2f + healthAsColour, 0.2f + healthAsColour, 1.f);

        if (orbiting)
        {
            if (shotTimer > shotDelay)
            {
                shotTimer = 0;

                float shotRot;
                if (base.getTurretCount() > 0)
                {
                    shotRot = Utilities.getAngleBetween(Utilities.getOriginPosition(ship), base.getRandomTurretPosition());

                    Vector2 shotPos = Utilities.getDirectionVector(ship);
                    shotPos.scl(ship.getHeight() / 2.f);
                    shotPos.add(Utilities.getOriginPosition(ship));

                    shots.add(new Shot(shotPos, shotRot, speed * 1.25f, Shot.Type.EnemyShip));
                }
            }
            else
            {
                shotTimer += (int)(Gdx.graphics.getDeltaTime() * 1000.f);
            }
        }
    }

    public void draw(SpriteBatch batch)
    {
        ship.draw(batch);
        for (Shot s : shots)
        {
            s.draw(batch);
        }
    }

    public CollisionInformation getCollisionInformation()
    {
        return colInfo;
    }

    public void onCollision()
    {
        health -= 20;
        if (health <= 0)
        {
            alive = false;
        }
    }

    public Array<Shot> getShots()
    {
        return shots;
    }
}
