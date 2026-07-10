package com.game;

import java.util.Scanner;

public class Game {
    public static void main(String[] args) {
        GameEngine engine = new GameEngine();
        Scanner scanner = new Scanner(System.in);
        System.out.println(engine.getIntro());
        while (engine.isRunning()) {
            System.out.print("> ");
            String input = scanner.nextLine();
            String response = engine.processCommand(input);
            if (!response.isEmpty()) {
                System.out.println(response);
            }
        }
        scanner.close();
    }
}
