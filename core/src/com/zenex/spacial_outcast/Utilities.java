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

    public static float distToVec(Sprite sprite, Vector2 vec)
    {
        Vector2 spritePos = new Vector2(sprite.getX() + sprite.getOriginX(), sprite.getY() + sprite.getOriginY());
        return vec.dst(spritePos);
    }

    public static float distToMouse(Sprite sprite)
    {
        Vector2 mousePos = new Vector2(Gdx.input.getX(0), Gdx.graphics.getHeight() - Gdx.input.getY(0));
        return distToVec(sprite, mousePos);
    }

    public static float getAngleBetween(Vector2 a, Vector2 b)
    {
        Vector2 dif = vectorDifference(a, b);
        dif.setLength(1);
        return MathUtils.radiansToDegrees * MathUtils.atan2(dif.x, -dif.y);
    }

    public static void rotateToVec(Sprite sprite, Vector2 vec)
    {
        Vector2 spritePos = new Vector2(sprite.getX() + sprite.getOriginX(), sprite.getY() + sprite.getOriginY());
        sprite.setRotation(getAngleBetween(spritePos, vec));
    }

    public static void rotateToMouse(Sprite sprite)
    {
        Vector2 mousePos = new Vector2(Gdx.input.getX(0), Gdx.graphics.getHeight() - Gdx.input.getY(0));
        rotateToVec(sprite, mousePos);
    }

    public static Vector2 getDirectionVector(Sprite sprite)
    {
        return new Vector2(-1 * MathUtils.sinDeg(sprite.getRotation()), MathUtils.cosDeg(sprite.getRotation()));
    }

    public static void moveAlongRotation(Sprite sprite, float speed)
    {
        Vector2 movement = getDirectionVector(sprite);
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

    public static float randFloat(float min, float max)
    {
        Random rand = new Random();
        return (rand.nextFloat() * (max - min)) + min;
    }
}
