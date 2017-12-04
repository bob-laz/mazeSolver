package com.trimble.mazesolver.spring;

import com.trimble.mazesolver.backend.Maze;
import com.trimble.mazesolver.backend.UtilityMethods;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class to map REST calls to specific functions. This rest server only accepts POST calls. A maze element
 * is expected in the body. See Maze class for object details.
 *
 * @author blask
 */
@RestController
public class MazeSolverController {

    /**
     * Solves mazes passed in through post calls. 200 status returned for valid input and a successful solution found.
     * 400 error with appropriate error message in Maze 'errorMessage' property returned for invalid input or no
     * solution found.
     * @param maze A Maze object as defined in Maze class
     * @return HTTP ResponseEntity of type Maze with a HTTP status code
     */
    @PostMapping("/solve")
    public ResponseEntity<Maze> home(@RequestBody Maze maze) {
        long start = System.currentTimeMillis(); // used to calculate run time
        maze = UtilityMethods.solveMaze(maze);
        HttpStatus status;
        if(maze == null || maze.getMazeSolved() == null || maze.getMazeSolved().equals("")){
            status = HttpStatus.BAD_REQUEST;
        }
        else{
            status = HttpStatus.OK;
            long end = System.currentTimeMillis();
            maze.setSolveTime(end - start);
        }
        return new ResponseEntity<>(maze, status);
    }
}
