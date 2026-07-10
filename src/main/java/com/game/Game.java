package com.game;

import java.util.Scanner;

public class Game {
    private Player player;
    private Room currentRoom;
    private boolean running;

    public Game() {
        player = new Player();
        running = true;
        buildWorld();
    }

    private void buildWorld() {
        Room entrance = new Room("Castle Entrance",
            "You stand before a massive stone castle. Torches flicker on either side of a heavy iron door.");
        Room hall = new Room("Great Hall",
            "A long hall with tall pillars. Dusty banners hang from the ceiling. There are doors to the north and east.");
        Room kitchen = new Room("Kitchen",
            "A cold, dark kitchen. Old pots hang above a dead fireplace. A pantry door is to the west.");
        Room treasury = new Room("Treasury",
            "Gold coins and jewels are scattered across the floor! A chest sits in the corner.");
        Room armory = new Room("Armory",
            "Weapons line the walls: swords, shields, and bows. Racks of armor stand in rows.");
        Room dungeon = new Room("Dungeon",
            "Dark and damp. Chains rattle in the shadows. A cold draft flows from somewhere below.");

        entrance.addExit("north", hall);
        hall.addExit("south", entrance);
        hall.addExit("north", dungeon);
        hall.addExit("east", kitchen);
        kitchen.addExit("west", hall);
        kitchen.addExit("north", treasury);
        treasury.addExit("south", kitchen);
        hall.addExit("west", armory);
        armory.addExit("east", hall);

        Item key = new Item("rusty key", "A rusty iron key. It might open something.");
        Item gem = new Item("ruby", "A brilliant red gem that glows faintly.");
        Item sword = new Item("sword", "A sharp steel sword in good condition.");
        Item potion = new Item("health potion", "A vial of red liquid that smells herbal.");

        entrance.addItem(key);
        treasury.addItem(gem);
        armory.addItem(sword);
        kitchen.addItem(potion);

        entrance.addItem(new Item("torch", "A wooden torch with a flickering flame."));

        currentRoom = entrance;
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("=== TEXT ADVENTURE ===");
        System.out.println("Type 'help' for commands.\n");
        look();
        while (running) {
            System.out.print("> ");
            String input = scanner.nextLine().trim().toLowerCase();
            processCommand(input);
        }
        scanner.close();
    }

    private void processCommand(String input) {
        if (input.isEmpty()) return;
        String[] parts = input.split(" ", 2);
        String verb = parts[0];
        String noun = parts.length > 1 ? parts[1] : "";

        switch (verb) {
            case "help" -> help();
            case "look" -> look();
            case "go" -> go(noun);
            case "n", "north" -> go("north");
            case "s", "south" -> go("south");
            case "e", "east" -> go("east");
            case "w", "west" -> go("west");
            case "take" -> take(noun);
            case "drop" -> drop(noun);
            case "inventory", "i" -> inventory();
            case "quit", "exit" -> quit();
            default -> System.out.println("I don't understand that.");
        }
    }

    private void help() {
        System.out.println("Commands:");
        System.out.println("  look              - describe the room");
        System.out.println("  go <direction>    - move north/south/east/west");
        System.out.println("  n, s, e, w        - shorthand for directions");
        System.out.println("  take <item>       - pick up an item");
        System.out.println("  drop <item>       - drop an item");
        System.out.println("  inventory, i      - list your items");
        System.out.println("  quit              - exit the game");
    }

    private void look() {
        System.out.println(currentRoom.describe());
        if (!currentRoom.getItems().isEmpty()) {
            System.out.print("You see: ");
            for (int i = 0; i < currentRoom.getItems().size(); i++) {
                System.out.print(currentRoom.getItems().get(i).getName());
                if (i < currentRoom.getItems().size() - 1) System.out.print(", ");
            }
            System.out.println();
        }
        System.out.print("Exits: ");
        System.out.println(String.join(", ", currentRoom.getExits().keySet()));
    }

    private void go(String direction) {
        Room next = currentRoom.getExit(direction);
        if (next == null) {
            System.out.println("You can't go that way.");
            return;
        }
        currentRoom = next;
        look();
    }

    private void take(String itemName) {
        if (itemName.isEmpty()) {
            System.out.println("Take what?");
            return;
        }
        Item item = currentRoom.removeItem(itemName);
        if (item == null) {
            System.out.println("There is no " + itemName + " here.");
            return;
        }
        player.addItem(item);
        System.out.println("You take the " + itemName + ".");
    }

    private void drop(String itemName) {
        if (itemName.isEmpty()) {
            System.out.println("Drop what?");
            return;
        }
        Item item = player.removeItem(itemName);
        if (item == null) {
            System.out.println("You don't have a " + itemName + ".");
            return;
        }
        currentRoom.addItem(item);
        System.out.println("You drop the " + itemName + ".");
    }

    private void inventory() {
        if (player.getItems().isEmpty()) {
            System.out.println("You are carrying nothing.");
            return;
        }
        System.out.print("You are carrying: ");
        for (int i = 0; i < player.getItems().size(); i++) {
            System.out.print(player.getItems().get(i).getName());
            if (i < player.getItems().size() - 1) System.out.print(", ");
        }
        System.out.println();
    }

    private void quit() {
        System.out.println("Goodbye!");
        running = false;
    }

    public static void main(String[] args) {
        new Game().run();
    }
}
