package com.zenex.spacial_outcast;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Iterator;

public class Ship
{
    Sprite ship = new Sprite(new Texture("Ship.png"));
    float speed;
    int minOrbitDistance = 80;
    int maxOrbitDistance = 160;
    int orbitStepDistance = 10;
    int orbitSteps = (maxOrbitDistance-minOrbitDistance) / orbitStepDistance;
    int orbitDirection = 0;
    float orbitDistance;
    boolean orbiting = false;
    CollisionInformation colInfo = new CollisionInformation();
    boolean alive = true;
    int health = 255;

    ArrayList<Shot> shots = new ArrayList<Shot>();
    float shotDelay = 300;
    float shotTimer = 0;

    public Ship(final Vector2 pos, float speed)
    {
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

        ship.setColor(1.f, health / 255.f, health / 255.f, 1.f);

        if (orbiting)
        {
            if (shotTimer > shotDelay)
            {
                shotTimer = 0;
                shots.add(new Shot(Utilities.getOriginPosition(ship), ship.getRotation(), speed * 1.25f, Shot.Type.EnemyShip));
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
        health -= 40;
        if (health <= 0)
        {
            alive = false;
        }
    }

    public ArrayList<Shot> getShots()
    {
        return shots;
    }
}
