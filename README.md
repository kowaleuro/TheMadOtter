# The Mad Otter
> In order to escape from the dungeon you as a "Mad Otter" must defeat various enemies, avoid traps and look for new weapons and powerups.
## Table of Contents
* [General Info](#general-information)
* [Technologies Used](#technologies-used)
* [Features](#features)
* [Screenshots](#screenshots)
* [Setup](#setup)
* [Acknowledgements](#acknowledgements)


## General Information

"The Mad Otter" is a rougelike type game with shooting elements created in order to:
- understand JavaFX better and to understand it's structure
- experiment with mechanics based on RNG (map generation)
- learn simple animations



## Technologies Used
- JavaFx - version 15.0.1
- FXML


## Features
- 15 weapons like shotgun, poison daggers and many more
- various enemies (following the player, creating copies of them themselvses when destroyed and more)
- time-limited powerups and animations
- randomly generated map
- 4 floors
- every floor has 25 rooms
- every room is full of various blocks (bush, spikes, walls, exploding barells, etc) 
- special room "shop", where you can buy new weapons
- static objects destruction with bombs and explosions
- hiding in bush
- map and interface


## Screenshots
![Example screenshot](./img/screen1.png)


## Setup
1.File -> Project Structure -> Libraries -> Add (+) -> javafx-sdk-15.0.1/lib -> OK

2.File -> Project Structure -> Modules -> lib -> OK

3.Run -> Edit configuration -> VM options and paste:

Windows: --module-path "..\TheGame\javafx-sdk-15.0.1\lib" --add-modules javafx.controls,javafx.fxml



## Acknowledgements
- Project was created in cooperation with Dawid Kwapisz (https://github.com/dkwapisz).
- This project was inspired by "The Binding of Isaac"
- Movenemnt handling was based on https://github.com/ashish2199/Aidos
