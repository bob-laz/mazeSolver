/*
    Main angular file to define component that makes rest calls and makes other functions available from within
    the HTML.
 */

var AppComponent = ng.core.Component({
        selector: 'app',
        templateUrl: 'app/app.component.html',
        styleUrl: 'app/app.component.css'
    }).Class({
        constructor: [ng.http.Http, function (http) {
            const self = this;
            self.maze = {maze: '', mazeSolved: '', solveTime: 0, errorMessage: '', bestPath: [], solved: false};
            this.solveMaze = function (mazeStr) {
                self.maze = {
                    maze: mazeStr,
                    mazeSolved: '',
                    solveTime: 0,
                    pathLength: 0,
                    errorMessage: '',
                    bestPath: [],
                    solved: false
                };
                // check if input is blank
                if (!isBlank(self.maze.maze)) {
                    // send post call to server with maze input data
                    http.post("/solve", self.maze).subscribe(function (response) {
                        self.maze = response.json(); // store json response in maze
                    }, function (error) { // error callback function
                        self.maze = error.json(); // maze object is returned with an error message
                        // display error message to user
                        alert("http status: " + error.status + '\nerror message: ' + self.maze.errorMessage);
                    });
                    // timeout used to wait for canvas object to load
                    setTimeout(function () {
                        init(self.maze.maze);
                        recursiveMazeDraw(stringToCharAry(self.maze.maze), 0);
                    }, 1500);
                }
                else {
                    // alert for blank input
                    alert("Input cannot be blank");
                }
            };

            // these 3 functions used to populate different maze texts from js/mazes.js into textarea
            this.populateMaze1 = function () {
                return maze1;
            };

            this.populateMaze2 = function () {
                return maze2;
            };

            this.populateMaze3 = function () {
                return maze3;
            };

            // makes autoResize function available for responsive textarea
            this.textAreaAutoSize = function () {
              autoSize('textarea');
            };

            // draws solution on canvas
            this.drawSolution = function (solutionPath) {
                var cords = getSolutionPathCords(solutionPath);
                recursiveSolutionDraw(cords);
            };
        }]
    })
;

function isBlank(str) {
    return (!str || str.length === 0 || !str.trim());
}


