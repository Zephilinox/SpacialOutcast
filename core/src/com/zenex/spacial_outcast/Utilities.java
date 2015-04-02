package com.zenex.spacial_outcast;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;


public class Utilities
{
    public static Vector2 vectorDifference(final Vector2 a, final Vector2 b)
    {
        Vector2 dif = a.cpy();
        dif.sub(b);
        return dif;
    }

    public static float distToMouse(Sprite sprite)
    {
        Vector2 mousePos = new Vector2(Gdx.input.getX(0), Gdx.graphics.getHeight() - Gdx.input.getY(0));
        Vector2 shipPos = new Vector2(sprite.getX() + sprite.getOriginX(), sprite.getY() + sprite.getOriginY());
        return mousePos.dst(shipPos);
    }

    public static void rotateToMouse(Sprite sprite)
    {
        Vector2 mousePos = new Vector2(Gdx.input.getX(0), Gdx.graphics.getHeight() - Gdx.input.getY(0));
        Vector2 spritePos = new Vector2(sprite.getX() + sprite.getOriginX(), sprite.getY() + sprite.getOriginY());
        Vector2 dif = vectorDifference(spritePos, mousePos);
        dif.setLength(1);

        sprite.setRotation(MathUtils.radiansToDegrees * MathUtils.atan2(dif.x, -dif.y));
    }

    public static void moveAlongRotation(Sprite sprite, float speed)
    {
        Vector2 movement = new Vector2(-1 * MathUtils.sinDeg(sprite.getRotation()), MathUtils.cosDeg(sprite.getRotation()));
        movement.scl(speed);

        move(sprite, movement);
    }

    public static void move(Sprite sprite, final Vector2 movement)
    {
        sprite.setPosition(sprite.getX() + movement.x, sprite.getY() + movement.y);
    }

    public static Vector2 getScreenCenter()
    {
        return new Vector2(Gdx.graphics.getWidth()/2.f, Gdx.graphics.getHeight()/2.f);
    }

    public static Vector2 getScreenCenter(final Vector2 offset)
    {
        return new Vector2(getScreenCenter().x - offset.x, getScreenCenter().y - offset.y);
    }

    public static boolean inWindow(final Sprite sprite)
    {
        if (sprite.getX() < 0 || sprite.getX() > Gdx.graphics.getWidth() ||
                sprite.getY() < 0 || sprite.getY() > Gdx.graphics.getHeight())
        {
            return false;
        }

        return true;
    }

    public static int randInt(int min, int max)
    {
        Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }
}
