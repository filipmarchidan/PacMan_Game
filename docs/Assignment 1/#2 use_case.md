# Use Case Descriptions.
## Use Cases
#### Register:
`Author:` Paul Adriaanse

`Date:` 26/11/2019

`Purpose:` Add a new user to the Database.

`Overview:`

    The player sees a menu and chooses to register.
    They then fill in the in the username and password fields, together with a second password field for verification, and press the register button to confirm their choices. The system then validates the input, according to the 'Validate' use case.
    If this is succesful, the combination is registered in the database and the player is notified he has been registered and is directed to the main menu.
    If this is not succesful, the player is notified the username is already taken, or a field was left empty.

`Cross-Reference:` Requirement 1.1 a

#### Validate:
`Author:` Paul Adriaanse

`Date:` 26/11/2019

`Purpose:` Validate the register input fields.

`Overview:`

    Once the player presses the register button after having filled in the username and two password fields, the system validates the two password fields contain the same input, and it validates the username is not already registered in the database.
    If either of these two are not succesfully validated, the player is notified of what was wrong.
    If both validate correctly, the player should be registered as described in the 'Register' use case.

`Cross-Reference:` Requirement 1.1 a

#### Log in:
`Author:` Paul Adriaanse

`Date:` 26/11/2019

`Purpose:` Log in as an existing user with the Database.

`Overview:`

    The player sees a menu and chooses to log in.
    He then fills in their username & password, and presses the login button. The system then tries to authenticate the user according to the 'Authenticate' use case.
    If this is succesful, and the player is logged in and shown the main menu.
    If this is not succesful, the player is notified the combination is not correct.

`Cross-Reference:` Requirement 1.1 a

#### Authenticate:
`Author:` Paul Adriaanse

`Date:` 26/11/2019

`Purpose:` Check the username & password fields have been registered with the database.

`Overview:`

    Once the player presses the login button after having filled in his username and password, the system validates username & password are registered in the database.
    If this is the case the player should be logged in as described in the 'Log in' use case, along whith being shown the main menu.
    If this is not the case, the player is notified the given username and password combination was incorrect.

`Cross-Reference:` Requirement 1.1 a

#### Start Game:
`Author:` Paul Adriaanse

`Date:` 25/11/2019

`Purpose:` Start a game session.

`Overview:`

    Once the player is in the main menu, he can press the start button to start the game. This will show him the board with all its ghosts, pellets and walls and with pacman. At this point pacman will start to move forward on a timely basis, and the 4 ghosts will start moving according to their individually defined behaviors formalized below, see 'Ghost behavioral definitions'.

`Cross-Reference:` Requirements 1.1 e,f,g

#### Move:
`Author:` Paul Adriaanse

`Date:` 25/11/2019

`Purpose:` Move pacman around on the board.

`Overview:`

    If the player has started the game, and the game has not ended yet, the player can press they keys (w,a,s,d) to try to move pacman in the (up,left,down,right) directions.
    If pacman tries to move into a wall, this attempt fails and nothing happens.
    If pacman tries to move into a ghost, pellet or empty square, this attempt is succesful, and pacman's direction is changed accordingly,
    which will make him move in this direction in the same timely manner as before.
    Once pacman is actually moved, the use cases 'Collide with Pellet' and 'Collide with Ghost' use cases may apply.
    If pacman tries to move outside the board, wraparound is used and it's treated as if he tried to move to the square on the other side of the board.

`Cross-Reference:` Requirements 1.1 f,j,k

#### Collide with Pellet:
`Author:` Paul Adriaanse

`Date:` 26/11/2019

`Purpose:` Earn points by eating pellets.

`Overview:`

    If pacman collides with a pellet, the pellet is removed (eaten) and the score is increased, as described by the 'Increase Score' use case.
    If the last pellet was eaten, the game ends as described by the 'Win Game' use case.

`Cross-Reference:` Requirements 1.1 g,h

#### Collide with Ghost:
`Author:` Paul Adriaanse

`Date:` 26/11/2019

`Purpose:` Lose when ghosts collide with pacman.

`Overview:`

    If the player collides with a ghost, pacman dies and the game ends as described by the 'Lose Game' use case.

`Cross-Reference:` Requirement 1.1 i

#### Increase Score:
`Author:` Paul Adriaanse

`Date:` 26/11/2019

`Purpose:` Increase the players current session score.

`Overview:`

    If the 'Collide with Pellet' use case is applicable, the player's score is increased by 10.

`Cross-Reference:` Requirement 1.1 g

#### Win Game:
`Author:` Paul Adriaanse

`Date:` 26/11/2019

`Purpose:` Win & end the game.

`Overview:`

    If the player collides with the last pallet on the board, the game is won and the player is notified of this, after which the game ends according to the 'End Game' use case.

`Cross-Reference:` Requirement 1.1 h

#### Lose Game:
`Author:` Paul Adriaanse

`Date:` 26/11/2019

`Purpose:` Lose & end the game.

`Overview:`

    If the 'Collide with Ghost' use case is applicable, the game is lost and the player is notified of this, after which the game ends according to the 'End Game' use case.

`Cross-Reference:` Requirement 1.1 i

#### End Game:
`Author:` Paul Adriaanse

`Date:` 26/11/2019

`Purpose:` End the game when the player wins/loses.

`Overview:`

    If the game is won, according to the 'Win Game' use case, or is lost, according to the 'Lose Game' use case,
    the player and ghosts are no longer able to move, and the leaderboard is shown, according to the 'View Leaderboard' use case.

`Cross-Reference:` Requirements 1.1 h,i

#### View Leaderboard:
`Author:` Paul Adriaanse

`Date:` 26/11/2019

`Purpose:` Show the player the best scores achieved.

`Overview:`

    Once the game has ended, according to the 'End Game' use case, the player is shown the top 5 scores that were achieved, along with the names of the users that achieved them.
    The player can then decide to save his score, together with a name, according to the 'Save Score' use case.

`Cross-Reference:` Requirement 1.1 d

#### Save Score
`Author:` Paul Adriaanse

`Date:` 26/11/2019

`Purpose:` The player can save his session score together with a name.

`Overview:`

    If the game has ended, and the player decided to save his score, he can fill in a name to be saved along with his score in the database.
    If this was high enough it might even be shown on the leaderboard, as described in the 'View Leaderboard' use case.

`Cross-Reference:` Requirement 1.1 c

---
## Ghost behavioral definitions.
`Author:` Paul Adriaanse

`Date:` 20/11/2019

- _The ghosts have 3 modes of behavior, which are listed below._
- _On a timely basis, the ghosts are switched between chase & scatter mode._
- _Frightened mode is only applicable under special, currently nonexistant, circumstances._
#### Chase mode:
##### _Blinky_:
- Blinky tries to collide with pacman (shortest path).
##### _Inky_:
- Inky tries to go to the tile that is exactly opposite from him, of the tile 2 tiles ahead of pacman.
##### _Pinky_:
- Pinky tries to move to the tile a few tiles(4) ahead of pacman.
##### _Clyde_:
- Fif clyde is more than 8 tiles away from pacman, he uses pinkys behavior.
- Ff clyde is closer, he uses his scatter behavior.
#### Scatter mode:
- The ghosts each try to move to 'their corner' of the maze, circling around there.
#### Frightened mode:
- The ghosts keep moving forward, and choose random directions at intersections.