# Superweapons Mod

This is a Fabric mod for Minecraft 1.21 that adds custom elemental superweapons, each with a unique right-click ability.

## Features:
-   **Fire Sword**: Shoots a fireball when right-clicked, sets enemies on fire on hit.
-   **Ice Blade**: Creates a localized frost field when right-clicked, applies slowness to enemies on hit.
-   **Thunder Axe**: Summons a lightning bolt at the targeted location when right-clicked.
-   **Earth Hammer**: Creates a non-destructive explosion that knocks back enemies when right-clicked.
-   **Superweapon Core**: A central component for crafting all superweapons.

## Building the Mod

To build this mod, you will need Java Development Kit (JDK) 21 installed on your system.

1.  **Clone the repository (or extract the project files):**
    ```bash
    git clone [repository-url]
    cd Superweapons
    ```

2.  **Run the Gradle build command:**
    On Windows:
    ```bash
    ./gradlew.bat build
    ```
    On Linux/macOS:
    ```bash
    chmod +x gradlew
    ./gradlew build
    ```

3.  **Find the mod JAR:**
    The compiled mod `.jar` file will be located in the `build/libs/` directory.

## Running the Mod in Development

To test the mod in a development environment:

1.  **Set up the development environment:**
    On Windows:
    ```bash
    ./gradlew.bat genSources idea eclipse
    ```
    On Linux/macOS:
    ```bash
    ./gradlew genSources idea eclipse
    ```
    (Use `idea` for IntelliJ IDEA or `eclipse` for Eclipse).

2.  **Run the client:**
    On Windows:
    ```bash
    ./gradlew.bat runClient
    ```
    On Linux/macOS:
    ```bash
    ./gradlew runClient
    ```

Enjoy the superweapons!
