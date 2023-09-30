# Colony Battle

## Overview

Colony Battle is a Java-based simulation game where players get to customize the settings of a colony war that ensues between four colonies. Each colony is distinguished by its characters: Farmers, Defenders, Warriors, and Wizards. The game offers various strategic elements, interactive game-play, and captivating visuals for an immersive gaming experience.

### Preview: 
[click here](https://github.com/Adam0s007/ColonyBattle/assets/109285249/0075f201-e7ed-40bc-84b7-2f5b0ee8b1b4).
## Game Setup

At the beginning of the game, users are prompted with default configurations:

**Initial Characters per colony**:
- Farmers: 3
- Defenders: 2
- Warriors: 4
- Wizards: 1

**Other Configurations**:
- Board Size: 25
- Max Obstacles: 10
- Spawning Time: 30
- Enable Magic (Checkbox - unchecked by default)


Users can customize these settings within certain upper and lower bounds.

## Game Mechanics

1. Once the game starts, a board displays with four colonies, each placed in a distinct quadrant.
2. Characters move around the board, with behaviors determined by their classes:
    - **Farmers**: Occupy tiles by converting them to their colony's color. New characters of their colony will occasionally spawn on these tiles. They're nimble, making quick movements. When their health drops, they flee from nearby enemies.
    - **Defenders**: They aim to protect their colony members by moving toward them.
    - **Warriors**: They engage in direct combat, seeking out the nearest enemy.
    - **Wizards**: Their behavior is dynamic. If their magic is enabled, they periodically heal allies and launch ranged attacks on the nearest foe. Without magic, they function similar to farmers but don't convert tiles.
3. Combat ensues when characters from opposing colonies meet:
    - Wizards and Defenders will actively seek and destroy enemy tiles (Defenders must be the last of their kind).
    - Farmers seek the nearest unoccupied or enemy tiles to convert them.
    - Warriors relentlessly pursue foes.
    - While Farmers transform tiles, other classes will revert enemy tiles to a neutral state.

4. Points are earned by eliminating foes, destroying enemy tiles, and creating colony tiles.
5. The game concludes when only one colony remains.

## Interactive Controls

- **Left-click** on characters: Displays character information on the top right.
- **Right-click** on tiles after selecting a character: Manually directs the character to that tile. Once there, the character reverts to its default behavior.
- Outside the main map, there's a board displaying all characters from a colony with their aggregate points. Clicking a character here will track its movement.
- A leaderboard showcases surviving characters ranked by their kills and survival days.

## Character Attributes

```java
package com.example.colonybattle.models.person.type;

public enum PersonType {
    
    FARMER(8, 8, 5, 20, 5),
    DEFENDER(20, 12, 10, 20, 3),
    WARRIOR(12, 15, 20, 20, 4),
    WIZARD(14, 20, 12, 20, 4);

    private final int health;
    private final int energy;
    private final int strength;
    private final int protection_energy;
    private final int landAppropriation;

    PersonType(int health, int energy, int strength,int landAppropriation,int protection_energy){
        this.health = health;
        this.energy = energy;
        this.strength = strength;
        this.landAppropriation = landAppropriation;
        this.protection_energy = protection_energy;
    }
    // protection_energy: Represents the minimal energy required for a character to absorb a basic amount of damage.
    // landAppropriation: Scaled between 0 to 20; where 20 implies instant tile conversion. If below 20, a character needs multiple visits to a tile for full conversion.
    // strength: Represents the amount of damage a character can inflict on an enemy.
    // energy: Represents the amount of energy a character has.
    // health: Represents the amount of health a character has. If health drops to 0, the character dies.
}
```
## GUI Description

In the game's graphical user interface, each character class is represented by a unique icon. Below are the icons for each character:

### Defender
<img src="https://github.com/Adam0s007/ColonyBattle/blob/main/src/main/resources/heads/defender.png" width="100" height="100" alt="Defender Icon">

### Warrior
<img src="https://github.com/Adam0s007/ColonyBattle/blob/main/src/main/resources/heads/warrior.png" width="100" height="100" alt="Warrior Icon">

### Wizard
<img src="https://github.com/Adam0s007/ColonyBattle/blob/main/src/main/resources/heads/wizard.png" width="100" height="100" alt="Wizard Icon">

### Farmer
<img src="https://github.com/Adam0s007/ColonyBattle/blob/main/src/main/resources/heads/farmer.png" width="100" height="100" alt="Farmer Icon">

## Character Outlines
Each character may have a colored outline to indicate a specific state or action:

- White Outline: Indicates the character has been attacked from a distance.
- Purple Outline: Denotes that the character has died.
- Light Green Outline: Signifies that the character is being tracked.
- Dark Blue Outline: Represents that the character is regenerating health or energy.

## Health and Energy Bars
<img src="healthAndEnergy.png">
The bars above the character represent their health and energy status:

- light-red bar: Represents the current health status of the character.
- light-yellow bar: Denotes the character's current energy level.

## How to Run the Project

### With IntelliJ IDEA:

1. Clone the repository or download the source code.
2. Open the project in IntelliJ IDEA.
3. Ensure you have the JavaFX SDK set up and added as a global library in your IntelliJ IDEA.
4. Navigate to `HelloApplication.java` in the `com.example.colonybattle` package.
5. Right-click and select 'Run'.

### Without IntelliJ IDEA (Using Maven):

Ensure you have Maven and Java 17 installed in your system.

1. Open your terminal or command prompt.
2. Navigate to the root directory of the project.
3. Run the following command:

```agsl
mvn clean javafx:run
```
This command will compile and run the JavaFX application using Maven.

**Note**: Ensure your system environment has the `JAVA_HOME` set to Java 17, and Maven is properly installed and included in the PATH. If you face any issues related to JavaFX, make sure the JavaFX SDK is correctly set up in your system and the `PATH_TO_FX` environment variable is set to the `lib` directory of the JavaFX SDK.
## License

This project is licensed under the terms of the MIT License. See the [LICENSE](LICENSE) file for the full text.
