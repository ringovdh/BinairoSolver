package be.yorian.main;

import be.yorian.game.Game;
import be.yorian.game.Solver;

public class Main {

	public static void main(String[] args) {
		
		// init game
		Game game = new Game();
		Solver solver = new Solver(game);
		
		// start een moeilijk spel
		game.initExpertGame();
		// start een makkelijk spel
		// game.initSimpleGame();
		
		// start de tijd
		long startTime = System.nanoTime();
		solver.solveGame();
		
		// druk de oplossing af
		game.print();
		
		// bereken en print de tijd
		long estimatedTime = System.nanoTime() - startTime;
		System.out.println("solved in: " + (double) estimatedTime / 1000000.0 + " msec");
	}


}
