package com.example.colonybattle.board.position;

public enum Direction {
    NORTH(new Vector2d(0, 1)),
    NORTHEAST(new Vector2d(1, 1)),
    EAST(new Vector2d(1, 0)),
    SOUTHEAST(new Vector2d(1, -1)),
    SOUTH(new Vector2d(0, -1)),
    SOUTHWEST(new Vector2d(-1, -1)),
    WEST(new Vector2d(-1, 0)),
    NORTHWEST(new Vector2d(-1, 1));

    private final Vector2d vector;

    Direction(Vector2d vector) {
        this.vector = vector;
    }

    public Vector2d getVector() {
        return this.vector;
    }
    //funkcja zwraca tablicę vectorow2d przekonwertowanych z enuma
    public static Vector2d[] getVectors(){
        Vector2d[] vectors = new Vector2d[8];
        for(Direction direction : Direction.values()){
            vectors[direction.ordinal()] = direction.getVector();
        }
        return vectors;
    }
}

