README 

PLEASE RUN THE GAME LOCALLY; Codio did not work with my JUnit tests

I have made a redux of Minesweeper that is very similar to the original, but adds the
 ability to customize the number of mines (and thus the difficulty).  This done via
a JOptionPane which has a textfield that the user can type in.  I then store the 
user's input as String, and then change the number of mines .  I have also "added"
the ability to save and view highscores via Java's IO library, which is done via a 
dialog that asks the user for their username, and then saves all of the people who 
successfully beat the game.  There isan also option to view all of the people who
have beat the game in the past.  The user can also view the winners in order of
quickest time to beat the game.  

In terms of the game itself, I have have six classes: Game, GameSetup, GameTest, Instructions,RelTimer
and MenuScreen.  The Game class creates the frame to run the game in (and sizes it appropriately),
and also adds a new MenuScreen object to the frame.  The MenuScreen class presents the user
with the starting menu for the game, which includes the title as well as buttons to view
instructions and play.  One thing to note is that I used a custom font for the title 
for aesthetic purposes (and I consulted the source below).  Moving on, if the user clicks
the instructions button, they will be redirected (i.e. the frame will be updated to 
display the Instructions class), and they will see a simple label with the instructions
(note that HTML was used in order to achieve proper padding) as well as a back button.  The
back button will direct the user back to the menu screen.  If the user instead clicks the
play button, the GameSetup class is then called.  Lastly, if the user clicks on the view
winners button, the user will be able view all of people who have been the game, which 
is done via a txt file that is stored in the "files" folder.  This is handlded via the
Winners class.  Note that if two user's have the same name, the most recent high score
will be kept.

The RelTimer manages the clock that the user sees counting how long they have been playing the
game, and thus integrates with the user's "high" score.  A source was consulted  for some
of this code.  

The GameSetup class is the most complex class included, and includes the implementation of 
my 2D arrays and recursion.  I used 2D array's to store the state of the board (where the rows
of the 2D array represent rows of the board and the columns of the 2D array represent
the columns of the board) in two ways:a 2D array which contained buttons that the user
actually interacts with (i.e. buttonCells) and the 2D array (i.e. cellCounts) which stores
number of mines surrounding a clicked/given cell.  I needed to separate the state here
since it was essentially impossible to store both the state of button and of the 
cell counts in the same place.  The constructor for this class adds the reset button
(which works by reverting each button back to its default state in terms of color, text,
and ability to click and creating a new set of random mines (to be described later)) as well
as initializes the board.  The board is represented by the buttonCell 2D array of buttons, 
where each button represents a cell.  A simple for-loop both initializes and places each
button on the board as well as constructs each button's ActionListener.  Within the Action-
Listener, I check the corresponding cell in the cellCounts array to see the value for the 
clicked cell.  If the value is 100, then we have a mine, and the game is ended, which is
 done via the gameLost method which colors every mine red on the board red, displays a pop
 message, and displays the entire board (including the count of each cell).  If the value is
 0, then we need to clear the surrounding zeroes, which is done via the clearAfterZero
 method (to be described later).  We also disable the button for the cell and set the text
 of the cell to 0.  For the remaining cells, we simply disable the button and display the 
count (i.e. the number of mines surrounding that cell) via the setText method on button.  
If the clicked cell is not a mine, we also check if the user has won the game, which
is done via the didWin method, which simply loops through every enabled cell and checks
if any non-mines remain.  The didWin method also writes to the file menioned above that
stores all of the users who have won.  This is done via simple Java.IO, specifically a 
buffered writer.  It should also be noted that this game supports the right-click
functionality, but the user must still clear the remaining mines; essentially, the
right click serves as flag for the user, but the game must still be won regularly.  

The two lengthy methods in GameSetup are createMines and clearAfterZero.  createMines works
by first initializing an ArrayList where each value represents a given cell.  This is done
a bit awkwardly, however, since there is no easy way to convert a 2D array list to a 1D
list, so I opted to store the x-location in the cell in the thousdands/hundreds place, and
the y-location of the cell in the tens/ones place.  We needed an ArrayList in the first place
since we don't want to place two different mines in the cell same spot (and thus have 
fewer than the user-inputted number of mines), so when we loop through the arraylist and 
choose which random mines to place, we remove that cell and update the cellCounts cell
that represents the chosen mine.  Note that an easy way to obtain the x-location
for a value in the ArrayList was to divided by 100, and an easy way to obtain the y-location
for a value in the ArrayList was to mod by 100.  The createMines method also initiliazes the
count for each cell (i.e. the number of mines surrounding it ) which is done by looping
through every cell in the cellCounts array and checking whetehr the mines in the 8 surrounding
spots are mines.  If there is a mine in one of cells, then the nCount variable, which
represents the number of surrounding mines, is incremented, and the value cellCount array
for the checked cell is updated to reflect its number of surrounding mines.  

The other method is clearAfterZeroes.  This method checks whether the user has cliked on cell that 
is surrounded by cells (on all 8 sides) that have a cellCount of 0, and then clears those 0s.  This
done via recursion, and...
and 

The last class I included was the GameTest class, which simply runs germane JTests.  

One thing I would like to make clear is how I changed screens.  I passed the frame I initially
created in the Game class as a paramter to constructor of the Instruction class (if the 
user clicks the instructions button), and I used the same process for the play button/GameSetup
class.  Every time I change screens, I use the same frame, but I clear all of the components 
from the previous screen, and add all of the components for the current screen (such as the
board for GameSetup).  




The four concepts I implemented were: recursion, I/O, 2D Array's storing state, and JUnit 
testing, which I explained above.  In terms of what I changed, 2D arrays, IO, and JUnit have
remained unchanged, but I decided to forgo inheritance in favor of doing recursion (which
I have explained above).


The following sources were consulted:

Dialog box help: https://www.youtube.com/watch?v=oZUGMpGQxgQ
Menu scree help: https://www.youtube.com/watch?v=FZWX5WoGW00
Getting the source of an AcitonEvent: http://hajsoftutorial.com/actionevent-getsource/
Changing label fonts: http://www.java2s.com/Tutorial/Java/0261__2D-Graphics/javaawtFont.htm
RelTimer refrence: http://www.java2s.com/Tutorial/Java/0240__Swing/SwingTimeraction.htm
Sort TreeMap code found here: https://stackoverflow.com/questions/2864840/treemap-sort-by-value
