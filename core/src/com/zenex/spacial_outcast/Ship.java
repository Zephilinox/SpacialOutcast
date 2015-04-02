package com.zenex.spacial_outcast;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

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
    CollisionInformation colInfo = new CollisionInformation();
    boolean alive = true;
    int health = 255;

    public Ship(final Vector2 pos, float speed)
    {
        ship.setOriginCenter();
        ship.setPosition(pos.x - ship.getOriginX(), pos.y - ship.getOriginY());
        this.speed = speed;
        orbitDistance = minOrbitDistance + (Utilities.randInt(0, orbitSteps) * orbitStepDistance);
        colInfo.radius = ship.getWidth()/2.f;

        if (pos.y > Gdx.graphics.getHeight()/2.f)
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

        Vector2 moveDir = Utilities.getDirectionVector(ship);
        moveDir.rotate(90);

        moveDir.scl(orbitDirection * speed * Gdx.graphics.getDeltaTime());
        Utilities.move(ship, moveDir);

        ship.setColor(1.f, health / 255.f, health / 255.f, 1.f);
    }

    public void draw(SpriteBatch batch)
    {
        ship.draw(batch);
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
}
