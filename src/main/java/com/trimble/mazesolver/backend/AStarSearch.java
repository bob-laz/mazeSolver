package com.trimble.mazesolver.backend;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Implementation of A* search algorithm.
 *
 * @author blask
 */
public class AStarSearch {

    /**
     * Calculates absolute value of the distance between two points
     *
     * @param p1 First Point object
     * @param p2 Second Point object
     * @return A positive integer representing the absolute value of distance between the two points passed in
     */
    private static int heuristic(Point p1, Point p2) {
        return p1.subAbs(p2);
    }

    /**
     * Implementation of A* search algorithm for finding the shortest path between two points on a graph. Uses the
     * distance to the goal and the weight of each neighbor to determine next move intelligently.
     * <p>
     * See http://www.geeksforgeeks.org/a-search-algorithm/ for more information
     *
     * @param g A Graph object containing a list of points and all of their neighbors
     * @param begin Starting point for Graph traversal
     * @param end Ending point for Graph traversal
     * @return A Map of points where each point maps to the next ideal move
     */
    public static Map<Point, Point> aStarSearch(Graph g, Point begin, Point end) {
        Queue<Point> frontier = new PriorityQueue<>();
        begin.weight = 0;
        frontier.add(begin);
        Map<Point, Point> cameFrom = new HashMap<>();
        Map<Point, Integer> costSoFar = new HashMap<>();
        costSoFar.put(begin, 0);

        while (frontier.size() != 0) {
            Point current = frontier.poll();

            if (current.equals(end)) {
                break;
            }

            for (Point p : g.neighbors(current)) {
                int newCost = costSoFar.get(current) + 1;
                if (!costSoFar.containsKey(p) || newCost < costSoFar.get(p)) {
                    costSoFar.put(p, newCost);
                    p.weight = newCost + heuristic(end, p);
                    frontier.add(p);
                    cameFrom.put(p, current);
                }
            }
        }
        return cameFrom;
    }


}
