package com.example.colonybattle;

public class Calculator {

    public static int sgn(double x) {
        //x < 0 x > 0 x == 0 - Math.ceil math floor
        if(x > 0) return 1;
        else if(x < 0) return -1;
        else return 0;
    }

    public static Vector2d calculateDirection(Vector2d fromVec, Vector2d toVec) {
        int xDirection = toVec.getX() - fromVec.getX();
        int yDirection = toVec.getY() - fromVec.getY();
        //System.out.println("xDirection: " + xDirection + " yDirection: " + yDirection);
        double magnitude = Math.sqrt(xDirection * xDirection + yDirection * yDirection);
        //System.out.println("magnitude: " + magnitude);
        if (magnitude != 0) {
            double unitVectorX = xDirection / magnitude;
            double unitVectorY = yDirection / magnitude;

            //System.out.println("unitVectorX: " + unitVectorX + " unitVectorY: " + unitVectorY);
            return new Vector2d(sgn(unitVectorX), sgn(unitVectorY));
        } else {
            return new Vector2d(0, 0);
        }
    }



}
