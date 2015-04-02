package com.zenex.spacial_outcast;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Ship
{
    Sprite ship;
    float speed;

    public Ship(final Vector2 pos, float speed)
    {
        ship = new Sprite(new Texture("Ship.png"));
        ship.setOriginCenter();
        ship.setPosition(pos.x - ship.getOriginX(), pos.y - ship.getOriginY());
        this.speed = speed;
    }

    public void update()
    {
        if (Utilities.distToMouse(ship) > speed)
        {
            Utilities.rotateToMouse(ship);
            Utilities.moveAlongRotation(ship, speed);
        }
    }

    public void draw(SpriteBatch batch)
    {
        ship.draw(batch);
    }
}
