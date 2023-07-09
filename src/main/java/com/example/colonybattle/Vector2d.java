package com.example.colonybattle;

public class Vector2d {
    private int x;
    private int y;
    private Colony membership;
    private int appropriationRate;

    public Vector2d(int x, int y, Colony membership, int appropriationRate) {
        this.x = x;
        this.y = y;
        this.membership = membership;
        this.appropriationRate = appropriationRate;
    }
    public Vector2d(int x, int y) {
        this.x = x;
        this.y = y;
        this.membership = null;
        this.appropriationRate = 0;
    }

    public Vector2d newVector(int x, int y) {
        return new Vector2d(x, y, this.membership, this.appropriationRate);
    }

    public Vector2d addVector(Vector2d vector) {
        return new Vector2d(this.x + vector.x, this.y + vector.y, this.membership, this.appropriationRate);
    }
    // odejmij vector
    public Vector2d subtractVector(Vector2d vector) {
        return new Vector2d(this.x - vector.x, this.y - vector.y, this.membership, this.appropriationRate);
    }

    public Vector2d oppositeVector() {
        return new Vector2d(-this.x, -this.y, this.membership, this.appropriationRate);
    }

    // Override hashcode oraz equals
    @Override
    public int hashCode() {
        return this.x + this.y;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (!(other instanceof Vector2d))
            return false;
        Vector2d that = (Vector2d) other;
        return this.x == that.x && this.y == that.y && this.membership == that.membership && this.appropriationRate == that.appropriationRate;
    }
    // Override toString
    @Override
    public String toString() {
        return "(" + this.x + "," + this.y + ")";
    }


    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Colony getMembership() {
        return membership;
    }

    public void setMembership(Colony membership) {
        this.membership = membership;
    }

    public int getAppropriationRate() {
        return appropriationRate;
    }

    public void setAppropriationRate(int appropriationRate) {
        this.appropriationRate = appropriationRate;
    }

}

