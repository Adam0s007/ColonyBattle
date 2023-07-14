package com.example.colonybattle.quadtree;

public class BoundingBox {
    public final int x0, y0, x1, y1;

    public BoundingBox(int x0, int y0, int x1, int y1) {
        this.x0 = x0;
        this.y0 = y0;
        this.x1 = x1;
        this.y1 = y1;
    }
    public boolean contains(int x, int y) {
        return x >= x0 && x < x1 && y >= y0 && y < y1;
    }
    public boolean intersects(BoundingBox other) {
        return other.x0 < x1 && other.x1 > x0 && other.y0 < y1 && other.y1 > y0;
    }
}

