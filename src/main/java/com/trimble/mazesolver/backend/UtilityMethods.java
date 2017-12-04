package com.trimble.mazesolver.backend;

import java.util.*;


/**
 * Contains methods to facilitate solving of mazes and validation/formatting of data.
 *
 * @author blask
 */
public class UtilityMethods {

    private static final char BEGIN_CHAR = 'A', END_CHAR = 'B', WALL_CHAR = '#', OPEN_CHAR = '.', PATH_CHAR = '@';
    private static final ArrayList<Point> directions = new ArrayList<>();

    static {
        directions.add(new Point(1, 0));
        directions.add(new Point(0, 1));
        directions.add(new Point(-1, 0));
        directions.add(new Point(0, -1));
    }

    /**
     * Reconstructions the actual best solution path from the confusing return value of the A* algorithm
     *
     * @param cameFrom return value of A* search
     * @param begin    beginning point for traversal
     * @param end      ending point for traversal
     * @return Ordered list of best path from begin to end
     */
    private static ArrayList<Point> reconstructPath(Map<Point, Point> cameFrom, Point begin, Point end) {
        Point current = end;
        ArrayList<Point> bestPath = new ArrayList<>();
        while (!current.equals(begin)) {
            bestPath.add(current);
            current = cameFrom.get(current);
        }
        Collections.reverse(bestPath);
        return bestPath;
    }

    /**
     * Converts text-based maze and best path solution to a text-based drawing of the solution.
     *
     * @param maze     2D char array representing the input maze
     * @param bestPath List of points representing best path from begin to end
     * @return String drawing of maze with solution path
     */
    private static String solutionString(char[][] maze, ArrayList<Point> bestPath) {
        StringBuilder retVal = new StringBuilder();
        for (Point p : bestPath) {
            if (maze[p.x][p.y] != BEGIN_CHAR && maze[p.x][p.y] != END_CHAR) {
                maze[p.x][p.y] = PATH_CHAR;
            }
        }

        for (char[] chrAry : maze) {
            for (char chr : chrAry) {
                retVal.append(chr);
            }
            retVal.append('\n');
        }

        return retVal.toString().trim();
    }

    /**
     * Determines which elements in the maze are valid and which are walls. Valid means the point is not a wall and is
     * in bounds.
     *
     * @param maze 2D char array of the maze
     * @return List containing all the valid Points in the maze
     */
    private static ArrayList<Point> validNodes(char[][] maze) {
        ArrayList<Point> nodes = new ArrayList<>();
        Point begin, end;

        for (int row = 0; row < maze.length; row++) {
            for (int col = 0; col < maze[row].length; col++) {
                char c = maze[row][col];
                if (c == BEGIN_CHAR) {
                    begin = new Point(row, col);
                    nodes.add(begin);
                } else if (c == END_CHAR) {
                    end = new Point(row, col);
                    nodes.add(end);
                } else if (c == OPEN_CHAR) {
                    nodes.add(new Point(row, col));
                }
            }
        }
        return nodes;
    }

    /**
     * Determines neighbors of a point. Used for creation of graph to be traversed by A*
     *
     * @param index      point to determine neighbors for
     * @param validNodes list of all valid nodes for the graph
     * @return a list of up to 4 points that are valid neighbors of the passed in point
     */
    private static ArrayList<Point> determineNeighbors(Point index, ArrayList<Point> validNodes) {
        ArrayList<Point> validNeighbors = new ArrayList<>();
        for (Point dir : directions) {
            Point neighbor = dir.add(index);
            if (validNodes.contains(neighbor)) {
                validNeighbors.add(neighbor);
            }
        }
        return validNeighbors;
    }

    /**
     * Finds the indices of the beginning and ending characters
     *
     * @param maze 2D char array representing input maze
     * @return point array of length 2, index 0 is beginning and index 1 is end
     */
    private static Point[] determineBeginEnd(char[][] maze) {
        Point begin = null, end = null;
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                if (maze[i][j] == BEGIN_CHAR) {
                    begin = new Point(i, j);
                } else if (maze[i][j] == END_CHAR) {
                    end = new Point(i, j);
                }
            }
        }
        return new Point[]{begin, end};
    }

    /**
     * Converts a 1D string array to a 2D char array
     *
     * @param maze 1D string array separated by newline characters
     * @return 2D char array representing the original string input
     */
    private static char[][] stringArrayToCharArray(String[] maze) {
        char[][] retVal = new char[maze.length][];
        for (int i = 0; i < maze.length; i++) {
            retVal[i] = maze[i].toCharArray();
        }
        return retVal;
    }

    /**
     * Converts a raw input string to a 1D string array by splitting on new lines
     *
     * @param maze Raw string input of maze
     * @return 1D string array of maze
     */
    private static String[] stringToStringArray(String maze) {
        return maze.split("\n");
    }

    /**
     * Performs input validation. Ensures only valid characters are used, all lines are the same length and only one
     * begin and end symbol are present.
     *
     * @param charMaze 2D char array of maze
     * @return true if maze valid, false if problem found
     */
    private static boolean validateCharMaze(char[][] charMaze) {
        if (charMaze.length == 0) {
            return false;
        }
        int length = charMaze[0].length;

        int beginCount = 0, endCount = 0;
        for (char[] ary : charMaze) {
            if (ary.length != length) {
                return false;
            }
            for (char c : ary) {
                if (c == BEGIN_CHAR) {
                    beginCount++;
                } else if (c == END_CHAR) {
                    endCount++;
                }
                if (c != BEGIN_CHAR && c != END_CHAR && c != WALL_CHAR && c != OPEN_CHAR) {
                    return false;
                }
            }
            if (beginCount > 1 || endCount > 1) {
                return false;
            }
        }
        return true;
    }

    /**
     * Utility method that takes in maze object and performs all necessary operations to validate, solve and set appropriate maze attributes
     *
     * @param maze Maze object with input string initialized
     * @return Maze object with mazeSolved, pathLength and best path attributes set or errorMessage attribute set if problem found
     */
    public static Maze solveMaze(Maze maze) {
        if (maze == null) {
            return null;
        }
        if (maze.getMaze() == null) {
            maze.setErrorMessage("null input maze");
            return maze;
        }
        char[][] charMaze = stringArrayToCharArray(stringToStringArray(maze.getMaze()));
        if (!validateCharMaze(charMaze)) {
            maze.setErrorMessage("Ensure all lines are the same length, only valid characters are used and there is only one A and one B");
            return maze;
        }

        Point[] beginEnd = determineBeginEnd(charMaze);
        if (beginEnd[0] == null || beginEnd[1] == null) {
            maze.setErrorMessage("Begin or end character not found");
            return maze;
        }
        ArrayList<Point> allNodes = UtilityMethods.validNodes(charMaze);
        Map<Point, ArrayList<Point>> neighbors = new TreeMap<>(Comparator.<Point>comparingInt(o -> o.x).thenComparingInt(o -> o.y));

        for (Point p : allNodes) {
            neighbors.put(p, determineNeighbors(p, allNodes));
        }
        Graph g = new Graph(neighbors);
        Map<Point, Point> searchResult = AStarSearch.aStarSearch(g, beginEnd[0], beginEnd[1]);
        if (!searchResult.containsKey(beginEnd[1])) {
            maze.setErrorMessage("Unsolvable maze, no valid path from A to B");
            return maze;
        }
        ArrayList<Point> bestPath = reconstructPath(searchResult, beginEnd[0], beginEnd[1]);
        maze.setBestPath(bestPath);
        maze.setPathLength(bestPath.size());
        maze.setMazeSolved(solutionString(charMaze, bestPath));
        return maze;
    }
}
