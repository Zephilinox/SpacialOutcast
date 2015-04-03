package com.zenex.spacial_outcast;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;


public class Shot
{
    public enum Type
    {
        Default,
        EnemyShip,
        AllyTurret
    }

    public boolean alive = true;

    private Sprite shot;
    private float speed;
    private Type type = Type.Default;

    private CollisionInformation colInfo = new CollisionInformation();

    public Shot(final Vector2 pos, float rotation, float speed, Type type)
    {
        Texture shotTexture = new Texture("Shot.png");
        shot = new Sprite(shotTexture);
        shot.setOriginCenter();
        shot.setPosition(pos.x - shot.getOriginX(), pos.y - shot.getOriginY());
        shot.setRotation(rotation);
        this.speed = speed;
        colInfo.radius = 1;
        this.type = type;

        switch (type)
        {
            case Default:
            {
                break;
            }

            case EnemyShip:
            {
                shot.setColor(0.9f, 0.1f, 0.1f, 1.f);
                break;
            }

            case AllyTurret:
            {
                shot.setColor(0.1f, 0.9f, 0.3f, 1.f);
                break;
            }
        }
    }

    public void update()
    {
        colInfo.position = Utilities.getOriginPosition(shot);

        if (!Utilities.inWindow(shot))
        {
            alive = false;
        }

        Utilities.moveAlongRotation(shot, speed * Gdx.graphics.getDeltaTime());
    }

    public void draw(SpriteBatch batch)
    {
        shot.draw(batch);
    }

    public CollisionInformation getCollisionInformation()
    {
        return colInfo;
    }

    public void onCollision()
    {
        alive = false;
    }
}
