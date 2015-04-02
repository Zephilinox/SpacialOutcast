package com.zenex.spacial_outcast;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Shot
{
    Sprite shot;
    float speed;

    public Shot(final Vector2 pos, float rotation, float speed)
    {
        shot = new Sprite(new Texture("Shot.png"));
        shot.setOriginCenter();
        shot.setPosition(pos.x - shot.getOriginX(), pos.y - shot.getOriginY());
        shot.setRotation(rotation);
        this.speed = speed;
    }

    public void update()
    {
        Utilities.moveAlongRotation(shot, speed);
    }

    public void draw(SpriteBatch batch)
    {
        shot.draw(batch);
    }

    public boolean isAlive()
    {
        return Utilities.inWindow(shot);
    }
}
