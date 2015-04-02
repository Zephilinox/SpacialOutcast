package com.zenex.spacial_outcast;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Ship
{
    Sprite ship;
    float speed;
    int minOrbitDistance = 80;
    int maxOrbitDistance = 160;
    int orbitStepDistance = 10;
    int orbitSteps = (maxOrbitDistance-minOrbitDistance) / orbitStepDistance;
    float orbitDistance;

    public Ship(final Vector2 pos, float speed)
    {
        ship = new Sprite(new Texture("Ship.png"));
        ship.setOriginCenter();
        ship.setPosition(pos.x - ship.getOriginX(), pos.y - ship.getOriginY());
        this.speed = speed;
        orbitDistance = minOrbitDistance + (Utilities.randInt(0, orbitSteps) * orbitStepDistance);
    }

    public void update()
    {
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

        moveDir.scl(speed * Gdx.graphics.getDeltaTime());
        Utilities.move(ship, moveDir);
    }

    public void draw(SpriteBatch batch)
    {
        ship.draw(batch);
    }
}
