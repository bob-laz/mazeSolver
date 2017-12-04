package com.trimble.mazesolver.backend;

import static java.lang.Math.abs;

/**
 * Simple class defining an XY coordinate pair. Provides basic operations such as adding and subtracting two points.
 *
 * @author blask
 */
public class Point implements Comparable<Point> {
    public int x, y;
    public int weight;

    Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point add(Point p2) {
        return new Point(this.x + p2.x, this.y + p2.y);
    }

    public int subAbs(Point p2) {
        return abs(this.x - p2.x) + abs(this.y - p2.y);
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                ", weight=" + weight +
                '}';
    }

    @Override
    public int compareTo(Point p2) {
        return this.weight - p2.weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Point point = (Point) o;

        return x == point.x && y == point.y;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }
}
