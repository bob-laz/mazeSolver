/*
    Contains other javascript functions used on the page
 */

var squareSize = 7; // size of square for canvas drawing
var context; // holds canvas context

/*
    Initializes canvas element and draws black and grey backgrounds
 */
function init(maze) {
    var canvas = document.getElementById("canvas");
    maze = maze.split('\n');
    var x = maze[0].length;
    var y = maze.length;
    canvas.width = squareSize * (x + 8);
    canvas.height = squareSize * (y + 8);
    if (canvas.getContext) {
        context = canvas.getContext("2d");
        context.fillStyle = "rgb(150,150,150)";
        context.fillRect(squareSize * 2, squareSize * 2, squareSize * (x + 4), squareSize * (y + 4));
    }
    for (var i = 0; i < x; i++) {
        for (var j = 0; j < y; j++) {
            context.fillStyle = "rgb(0,0,0)";
            context.fillRect(squareSize * (4 + i), squareSize * (4 + j), squareSize, squareSize);
        }
    }
}

/*
    Draws the initial maze one row at a time, recursion used to allow use of timeout function for effect. Works by
    drawing one row, removing that row from the array, waiting 50 ms and then calling itself with modified array
 */
function recursiveMazeDraw(mazeChrAry, currentRow) {
    if (mazeChrAry.length > 0) {
        for (var i = 0; i < mazeChrAry[0].length; i++) {
            if (mazeChrAry[0][i] === '.') {
                drawWhite(i, currentRow);
            }
            else if (mazeChrAry[0][i] === 'A') {
                drawGreen(i, currentRow);
            }
            else if (mazeChrAry[0][i] === 'B') {
                drawRed(i, currentRow);
            }
        }
        mazeChrAry.splice(0, 1);
        currentRow++;
        setTimeout(function () {
            recursiveMazeDraw(mazeChrAry, currentRow)
        }, 50);
    }
}

/*
    Solution coordinates are returned through json in a complicated map that was used in A* search. This helper function
    extracts the coordinates from the map and puts them in a list of 2-element xy pair lists. Coordinates are reversed
    due to the way A* algorithm calculates path.
 */
function getSolutionPathCords(solutionPath) {
    var xyCords = [];
    for (var i = 0; i < solutionPath.length; i++) {
        xyCords[i] = [solutionPath[i]['y'], solutionPath[i]['x']];
    }
    return xyCords;
}

/*
    Recursively draws the solution path on the canvas after initial maze has been drawn. Works by drawing one coordinate,
    removing that coordinate from the list, waiting 20 ms and then calling itself. Recursion used to allow use of timeout
    function.
 */
function recursiveSolutionDraw(xyCords) {
    if (xyCords.length > 1) {
        drawBlue(xyCords[0][0], xyCords[0][1]);
        xyCords.splice(0, 1);
        setTimeout(function () {
            recursiveSolutionDraw(xyCords)
        }, 20);
    }
}

/*
    Helper method to convert the maze string received from input into a 2D character array to make drawing easier.
 */
function stringToCharAry(mazeStr) {
    var mazeStrAry = mazeStr.split('\n');
    var mazeChrAry = [[]];
    for (var i = 0; i < mazeStrAry.length; i++) {
        mazeChrAry[i] = mazeStrAry[i].split('');
    }
    return mazeChrAry;
}

/*
    Draws one white square on the canvas. Used for the valid paths.
 */
function drawWhite(i, j) {
    context.fillStyle = "rgb(255,255,255)";
    context.fillRect(squareSize * (4 + i), squareSize * (4 + j), squareSize, squareSize);
}

/*
    Draws one green square on the canvas. Used for starting point.
 */
function drawGreen(i, j) {
    context.fillStyle = "rgb(0,255,0)";
    context.fillRect(squareSize * (4 + i), squareSize * (4 + j), squareSize, squareSize);
}

/*
    Draws one red square on the canvas. Used for ending point.
 */
function drawRed(i, j) {
    context.fillStyle = "rgb(255,0,0)";
    context.fillRect(squareSize * (4 + i), squareSize * (4 + j), squareSize, squareSize);
}

/*
    Draws one blue square on the canvas. Used for solution path.
 */
function drawBlue(i, j) {
    context.fillStyle = "rgb(0,0,255)";
    context.fillRect(squareSize * (4 + i), squareSize * (4 + j), squareSize, squareSize);
}


/*
    Function and jquery used for auto-resizing textarea to make it responsive to input
 */
function autoSize(textarea) {
    $(textarea).height(1);
    $(textarea).height($(textarea).prop("scrollHeight"));
}

$(document).ready(function () {
    $(document).on("input", "textarea", function () {
        autoSize(this);
    });
    $("textarea").each(function () {
        autoSize(this);
    });
});