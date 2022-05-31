# program purpose:
- a game
- interacting with a Rubik's Cube (n x n x n, where n = number of cubes per side) based on a two-dimensional (rotatable) perspective
- array manipulation
- data saving = record the user's past game(s) and best performance(s)
- possible web API: let users research the hash code for any emoji or stuff that they want to replace the colored blocks with




## public class Side
- a side has a color / token
- colors include: yellow, red, green, blue, orange --> purple (cannot find orange ANSI code), white
    - yellow = \u001b[33m
    - red = \u001b[31m
    - green = \u001b[32m
    - blue = \u001b[34m
    - purple = \u001b[35m
    - white = \u001b[37m
    - reset = \u001b[0m
      > source: https://www.lihaoyi.com/post/BuildyourownCommandLinewithANSIescapecodes.html
      
      > alternative is to use the emoji in Tester.java directly
- can allow the user to change the colored blocks into other things they like? (API)
### instance variables:
- private String representation
### methods:
- accessor methods
- mutator methods




## public class Cube
- a cube has 6 sides, but can only have 0/1/2/3 visible sides
- each side of a cube has a token or representation (unless not visible)
### instance variables:
- private Side front // the front view is always the one that is shown based on the 2D perspective
- private Side left // all other orientations are based on the so-called front view
- private Side right
- private Side top
- private Side bottom
- private Side back
- private Side[] sides // for easy access
### methods:
- accessor methods
- mutator methods
- public void setCorrespondingVisibility(int r, int c, int x, int size) --> make different sides visible or not, based on its location in the Rubik's Cube





## public class RubiksCube
- a Rubik's Cube has six sides, each can have >= 4 color blocks (i.e. n >= 2 when the entire Rubik's Cube has a size of n * n * n number of Cube objects)
- it is associated with a player
### instance variables:
- private Cube[][][] cubes
- private int size (n) (can initialize the cubes array with "new Cube[size][size][size]")
- private Player player
- private Side[][] frontView
- private Side[][] backView
- private Side[][] leftView
- private Side[][] rightView
- private Side[][] topView
- private Side[][] bottomView
### methods:
- accessor methods
- mutator methods
- private void createRubiksCube() --> creates a Rubik's Cube (randomized by different rotations)
- private void resetRubiksCube() --> make the rubiksCube a solvable one (all sides are solved, before randomly rotated)
- private void updateFrontView() --> updates the front view of the cube (i.e. the view presented to the user)
- public void displayFrontView() --> prints out the front view of the Rubik's Cube
- private void updateBackView() --> returns the back view of the cube
- private void updateTopView() --> returns the top view of the cube
- private void updateBottomView() --> returns the bottom view of the cube
- private void updateLeftView() --> returns the left view of the cube
- private void updateRightView() --> returns the right view of the cube
> Rotation methods (face rotation methods await implementations)
- public void rowRotationLeft(int rowNum)
- public void rowRotationRight(int rowNum)
- public void colRotationUp(int colNum)
- public void colRotationDown(int colNum)
> Perspective changing methods
- public void rotatePerspectiveLeftward()
- public void rotatePerspectiveRightward()
- public void rotatePerspectiveUpward()
- public void rotatePerspectiveDownward()
> check for winning methods
- private boolean checkIfWin() --> returns true if the game is won, false if not won yet
- private boolean checkOneSideForVictory(Side[][] oneSide) --> oneSide will always be one of the six instance variables with matching data type
  --> checks one side of the Rubik's Cube to see if the game is won partially





## public class Player
- has a ArrayList object (import) that contains Record objects
- stores information about a user
- stores their preferred tokens??
### instance variables:
- private ArrayList<Record> records
- private String userName
- private String[] tokens
- private int gameCount
### methods:
- accessor methods
- mutator methods // everything besides records and colors and gameCount
- public void incrementGameCount() --> add gameCount by 1





## public class Record
- a Record object = a record of one game played by the user, storing info such as number of actions
### instance variables:
- private int rubiksCubeSize
- private int actionCount // not including change in perspective
### methods:
- accessor methods
- public void incrementActionCount() --> add actionCount by 1



## public class GamePlayManager
- manipulates all the data and all the classes
### instance variables:
- private String data --> for saving everything by the end of the program
- private Player player
- private Scanner inputReader
- private File file
- private RubiksCube rubiksCube
### methods:
- public void playGame() --> starts the game and plays it
- private void initiatePlayer() 
  - initializes the player while reading inside a file to see if the player is new or return
  - used in the constructor
  - asks the user for name
- private void constructRubiksCube()
  - initializes the rubiksCube by asking for size
  - used in the constructor
- private void processChoice(String choice, Record record) 
  - process the user input and perform corresponding action
  - if the action is performed, the actionCount in record is incremented
- private void endGame(Record record)
  - end the game
  - store all info about this round of game into the file, based on the record




##Runner class --> public class GamePlay
- initializes a GamePlayManager object; this object will handle all input, output, and logics
- calls the GamePlayManager method playGame(), which calls all other necessary methods for the game to operate



