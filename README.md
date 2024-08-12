# Tetris

This is a Java-based clone of the classic arcade game Tetris, developed using Java Swing for the graphical user
interface. The game follows the traditional Tetris mechanics, where blocks fall, and the player rotates and moves them
to create complete lines.

## Features

- **Classic Gameplay:** Recreate the nostalgic Tetris experience with falling tetrominoes.
- **Scoring and Levels:** Track your score and progress through increasingly difficult levels.
- **Customizable Key Bindings:** Modify the control scheme to your liking.
- **Simple GUI:** Built with Java Swing for a clean and responsive interface.
- **Pause/Resume Functionality:** Pause the game and resume later.

## Installation

1. **Clone the Repository:**
   ```bash
   git clone https://github.com/Princelad/Tetris.git
   cd Tetris
   ```

2. **Build the Project:**
   Use your preferred Java IDE to open and build the project using the provided source files.

3. **Run the Game:**
   Execute the main class (`Tetris.java`) to start playing the game.

## Controls

- **Move Left:** `Left Arrow`
- **Move Right:** `Right Arrow`
- **Rotate:** `Up Arrow`
- **Soft Drop:** `Down Arrow`
- **Pause/Resume:** `Esc` Key

## How It Works

The game uses a simple state machine to manage gameplay, including block generation, movement, collision detection, and
line clearing. The Swing library is used to render the game window and handle user inputs. The main loop updates the
game state based on player actions and automatically drops the tetrominoes over time.

## Contributing

Contributions are welcome! If you want to contribute:

1. Fork the repository.
2. Create a new branch.
3. Implement your feature or bug fix.
4. Submit a pull request with a clear description of the changes.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

## Author

- **Prince Lad** - [Princelad](https://github.com/Princelad)  
  Prince is a software developer with a passion for creating engaging and educational software projects. This Tetris
  clone was developed as part of a personal project to deepen understanding of game development principles using Java.

## Acknowledgments

- **Alexey Pajitnov:** Creator of the original Tetris game.
- **Java Swing Community:** For their contributions to the graphical interface.
- **Video Tutorial:** Special thanks to the tutorial [on YouTube](https://www.youtube.com/watch?v=N1ktYfszqnM&t=2365s)
  that inspired and guided the development of this project.
