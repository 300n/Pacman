# 🎮 Pacman Game

A JavaFX implementation of the classic Pacman arcade game featuring authentic ghost AI, multiplayer support, and smooth animations.

![Pacman Game Screenshot](path/to/screenshot.png)
*Add your game screenshot here*

## 📋 Table of Contents

- [About](#about)
- [Features](#features)
- [Demo](#demo)
- [Installation](#installation)
- [How to Play](#how-to-play)
- [Game Mechanics](#game-mechanics)
- [Project Structure](#project-structure)
- [Contributing](#contributing)

## 🎯 About

This is a recreation of the classic Pacman arcade game built with JavaFX. The game faithfully recreates the original gameplay mechanics including intelligent ghost AI with distinct personalities, power pellet mechanics, and supports up to 4 players simultaneously.

## ✨ Features

- **Authentic Ghost AI**: Each ghost has unique behavior patterns
  - Blinky (Red): Directly chases Pacman
  - Pinky (Pink): Tries to ambush by targeting ahead of Pacman
  - Inky (Cyan): Uses complex strategy based on Blinky's position
  - Clyde (Orange): Alternates between chasing and fleeing
- **Multiplayer Support**: Play with 1-4 players simultaneously
- **Chase/Scatter Modes**: Ghosts alternate between hunting and patrolling
- **Power Pellets**: Turn the tables and eat ghosts for bonus points
- **High Score System**: Tracks top scores with timestamps
- **Smooth Animations**: Interpolated movement and mouth animations
- **Lives System**: 3 lives per player
- **Custom Controls**: Configurable keyboard controls for each player

## 🎬 Demo

### Gameplay Video

![Gameplay Video](path/to/gameplay-video.gif)
*Add your gameplay video/GIF here*

### Screenshots

![Main Menu](path/to/menu-screenshot.png)
*Main Menu*

![Gameplay](path/to/gameplay-screenshot.png)
*4-Player Gameplay*

## 🚀 Installation

### Prerequisites

- Java JDK 8 or higher
- JavaFX SDK (if not bundled with your JDK)

### Setup Instructions

1. Clone the repository:
```bash
git clone https://github.com/300n/Pacman.git
cd Pacman
```

2. Ensure you have the required resources:
   - Custom font: `/ressources/Jaro.ttf`
   - Images: `/ressources/Pacman_icon2.png`, `/ressources/Pacman_main_logo.png`

3. Create the high scores directory:
```bash
mkdir -p ../Users_Highscore
touch ../Users_Highscore/Highscores3.txt
```

4. Compile and run:
```bash
javac MainMenu.java
java MainMenu
```

## 🎮 How to Play

1. Launch the game and select "Nouvelle Partie" (New Game)
2. In Options, choose the number of players (1-4)
3. Customize controls for each player
4. Navigate the maze, eat all dots to win
5. Avoid ghosts or eat power pellets to turn the tables
6. Complete the level by collecting all dots

## 🕹️ Default Controls

### Player 1
- **Z**: Move Up
- **S**: Move Down  
- **Q**: Move Left
- **D**: Move Right

### Player 2
- **↑**: Move Up
- **↓**: Move Down
- **←**: Move Left
- **→**: Move Right

### Player 3
- **T**: Move Up
- **G**: Move Down
- **F**: Move Left
- **H**: Move Right

### Player 4
- **I**: Move Up
- **K**: Move Down
- **J**: Move Left
- **L**: Move Right

*Controls can be customized in the Options menu*

## 🎲 Game Mechanics

### Scoring System
- **Small dot**: 10 points
- **Power pellet**: 50 points
- **Ghost (1st)**: 200 points
- **Ghost (2nd)**: 400 points
- **Ghost (3rd)**: 800 points
- **Ghost (4th)**: 1600 points

### Ghost Modes
The game alternates between two main modes:

- **Scatter Mode** (7s): Ghosts patrol their corners
- **Chase Mode** (20s): Ghosts actively pursue Pacman
- **Frightened Mode** (15s): Triggered by power pellets, ghosts become vulnerable
- **Eaten Mode**: Ghost returns to spawn after being eaten

### Lives
- Each player starts with 3 lives
- Collision with a ghost in chase/scatter mode loses a life
- Game ends when all lives are lost

## 📁 Project Structure

```
Pacman/
├── MainMenu.java          # Main menu and application entry point
├── Pacman__.java          # Core game logic and rendering
├── Pacman.java            # Pacman character class
├── Ghost.java             # Base ghost class
├── Blinky.java            # Red ghost (aggressive chaser)
├── Pinky.java             # Pink ghost (ambusher)
├── Inky.java              # Cyan ghost (strategic)
├── Clyde.java             # Orange ghost (random)
├── Vector2.java           # 2D vector utility
├── Timer.java             # Game timer
├── Touches_joueur.java    # Player control configuration
├── Buffer_Input.java      # Input buffering system
└── ressources/
    ├── Jaro.ttf           # Custom font
    ├── Pacman_icon2.png   # Window icon
    └── Pacman_main_logo.png
```

## 🛠️ Technologies Used

- **Language**: Java
- **Framework**: JavaFX
- **Graphics**: JavaFX Canvas and GraphicsContext
- **Architecture**: Object-oriented with inheritance for ghost behaviors

## 🤝 Contributing

Contributions are welcome! To contribute:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📄 License

This project is available for educational purposes. The original Pacman game is owned by Bandai Namco Entertainment.

## 🙏 Acknowledgments

- Original Pacman game by Namco (1980)
- JavaFX community for framework support

---

**Contact**: [@300n](https://github.com/300n) | [Repository](https://github.com/300n/Pacman)

⭐ Star this repository if you enjoyed the game!
