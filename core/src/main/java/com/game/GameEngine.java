package com.game;

import java.util.List;

public class GameEngine {
    private Player player;
    private Room currentRoom;
    private boolean running;

    public GameEngine() {
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

        entrance.addItem(new Item("torch", "A wooden torch with a flickering flame."));
        entrance.addItem(new Item("rusty key", "A rusty iron key. It might open something."));
        treasury.addItem(new Item("ruby", "A brilliant red gem that glows faintly."));
        armory.addItem(new Item("sword", "A sharp steel sword in good condition."));
        kitchen.addItem(new Item("health potion", "A vial of red liquid that smells herbal."));

        currentRoom = entrance;
    }

    public String processCommand(String input) {
        if (input == null || input.trim().isEmpty()) return "";
        String[] parts = input.trim().toLowerCase().split(" ", 2);
        String verb = parts[0];
        String noun = parts.length > 1 ? parts[1] : "";

        return switch (verb) {
            case "help" -> getHelp();
            case "look" -> look();
            case "go" -> go(noun);
            case "n", "north" -> go("north");
            case "s", "south" -> go("south");
            case "e", "east" -> go("east");
            case "w", "west" -> go("west");
            case "take" -> take(noun);
            case "drop" -> drop(noun);
            case "inventory", "i" -> getInventory();
            case "quit", "exit" -> quit();
            default -> "I don't understand that.";
        };
    }

    private String getHelp() {
        return """
Commands:
  look              - describe the room
  go <direction>    - move north/south/east/west
  n, s, e, w        - shorthand for directions
  take <item>       - pick up an item
  drop <item>       - drop an item
  inventory, i      - list your items
  quit              - exit the game""";
    }

    private String look() {
        StringBuilder sb = new StringBuilder();
        sb.append("== ").append(currentRoom.getName()).append(" ==\n");
        sb.append(currentRoom.getDescription());
        List<Item> items = currentRoom.getItems();
        if (!items.isEmpty()) {
            sb.append("\nYou see: ");
            for (int i = 0; i < items.size(); i++) {
                sb.append(items.get(i).getName());
                if (i < items.size() - 1) sb.append(", ");
            }
        }
        sb.append("\nExits: ");
        sb.append(String.join(", ", currentRoom.getExits().keySet()));
        return sb.toString();
    }

    private String go(String direction) {
        if (direction.isEmpty()) return "Go where?";
        Room next = currentRoom.getExit(direction);
        if (next == null) return "You can't go that way.";
        currentRoom = next;
        return look();
    }

    private String take(String itemName) {
        if (itemName.isEmpty()) return "Take what?";
        Item item = currentRoom.removeItem(itemName);
        if (item == null) return "There is no " + itemName + " here.";
        player.addItem(item);
        return "You take the " + itemName + ".";
    }

    private String drop(String itemName) {
        if (itemName.isEmpty()) return "Drop what?";
        Item item = player.removeItem(itemName);
        if (item == null) return "You don't have a " + itemName + ".";
        currentRoom.addItem(item);
        return "You drop the " + itemName + ".";
    }

    private String getInventory() {
        List<Item> items = player.getItems();
        if (items.isEmpty()) return "You are carrying nothing.";
        StringBuilder sb = new StringBuilder("You are carrying: ");
        for (int i = 0; i < items.size(); i++) {
            sb.append(items.get(i).getName());
            if (i < items.size() - 1) sb.append(", ");
        }
        return sb.toString();
    }

    private String quit() {
        running = false;
        return "Goodbye!";
    }

    public String getIntro() {
        return "=== TEXT ADVENTURE ===\nType 'help' for commands.\n" + look();
    }

    public boolean isRunning() {
        return running;
    }
}
