package com.example.colonybattle.board.position;

public enum Direction {
    NORTH(new Point2d(0, 1)),
    NORTHEAST(new Point2d(1, 1)),
    EAST(new Point2d(1, 0)),
    SOUTHEAST(new Point2d(1, -1)),
    SOUTH(new Point2d(0, -1)),
    SOUTHWEST(new Point2d(-1, -1)),
    WEST(new Point2d(-1, 0)),
    NORTHWEST(new Point2d(-1, 1));

    private final Point2d vector;

    Direction(Point2d vector) {
        this.vector = vector;
    }

    public Point2d getVector() {
        return this.vector;
    }
    //funkcja zwraca tablicę vectorow2d przekonwertowanych z enuma
    public static Point2d[] getVectors(){
        Point2d[] vectors = new Point2d[8];
        for(Direction direction : Direction.values()){
            vectors[direction.ordinal()] = direction.getVector();
        }
        return vectors;
    }
}

