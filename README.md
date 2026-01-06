# 🎮 Pacman Game

A JavaFX implementation of the classic Pacman arcade game featuring authentic ghost AI, multiplayer support, and smooth animations

![Pacman Game Screenshot](path/to/screenshot.png)


## 📋 Table of Contents

- [About](#about)
- [Features](#features)
- [Demo](#demo)
- [Installation](#installation)

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


### Screenshots

![Main Menu](path/to/menu-screenshot.png)


![Gameplay](path/to/gameplay-screenshot.png)


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


## 🛠️ Technologies Used

- **Language**: Java
- **Framework**: JavaFX
- **Graphics**: JavaFX Canvas and GraphicsContext
- **Architecture**: Object-oriented with inheritance for ghost behaviors

---

