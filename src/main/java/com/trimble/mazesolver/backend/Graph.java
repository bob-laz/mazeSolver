package com.trimble.mazesolver.backend;

import java.util.ArrayList;
import java.util.Map;


/**
 * Simple class defining a graph data structure. A graph consists of a map of points as keys and all that points
 * neighbors as values
 *
 * @author blask
 */
public class Graph {
    private Map<Point, ArrayList<Point>> edges;

    Graph(Map<Point, ArrayList<Point>> edges){
        this.edges = edges;
    }

    public ArrayList<Point> neighbors(Point index){
        return this.edges.get(index);
    }


}
