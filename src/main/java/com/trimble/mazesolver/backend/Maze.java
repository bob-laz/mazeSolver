package com.trimble.mazesolver.backend;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Class to hold maze data. Used as JSON object to send and receive information through rest calls.
 *
 * @author blask
 */
public class Maze implements Serializable {

    private String maze;
    private String mazeSolved;
    private long solveTime;
    private int pathLength;
    private ArrayList<Point> bestPath;
    private String errorMessage;

    public Maze() {
    }

    public ArrayList<Point> getBestPath() {
        return bestPath;
    }

    public void setBestPath(ArrayList<Point> bestPath) {
        this.bestPath = bestPath;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public int getPathLength() {
        return pathLength;
    }

    public void setPathLength(int pathLength) {
        this.pathLength = pathLength;
    }

    public long getSolveTime() {
        return solveTime;
    }

    public void setSolveTime(long solveTime) {
        this.solveTime = solveTime;
    }

    public String getMaze() {
        return maze;
    }

    public void setMaze(String maze) {
        this.maze = maze;
    }

    public String getMazeSolved() {
        return this.mazeSolved;
    }

    public void setMazeSolved(String mazeSolved) {
        this.mazeSolved = mazeSolved;
    }

    @Override
    public String toString() {
        return "Maze{" + "maze='" + maze + '\'' +
                ", mazeSolved='" + mazeSolved + '\'' +
                ", solveTime=" + solveTime +
                ", pathLength=" + pathLength +
                ", bestPath=" + bestPath +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }
}
