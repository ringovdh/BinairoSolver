package be.yorian.game;

import java.util.ArrayList;
import java.util.List;

public class Solver {
	
	private Game game;
	private int rows;
	private int columns;
	private List<Game> pogingen;
	int poging = 0;
	
	public Solver(Game originalGame) {
		this.game = originalGame;
		this.rows = originalGame.getRows();
		this.columns = originalGame.getColumns();
		this.pogingen = new ArrayList<>();
	}



	public Game solveGame() {	
		
		solveSimpleRules(game);
		game.validate();
		if (!game.isSolved) {
			// bewaar spelleke
			pogingen.add(poging, copyGame());
			
			if (game.isValid) {			
				poging ++;
				game.makeGuess("0");
				solveGame();			
			} 
			
			if (!game.isValid) {
				poging --;
				game.cloneArray(pogingen.get(poging).getValues());
				game.makeGuess("1");
				solveGame();
			}
		}
		
		return game;
	}
	
	private Game copyGame() {
		Game original = new Game();
		original.cloneArray(game.getValues());
		return original;
	}
	
	private Game solveSimpleRules(Game game) {
		
		while(game.isChanged) {
			game.setChanged(false);
			
			// dubbels checken horizontaal
			solveDoublesHorizontal(game);
			// splits checken horizontaal
			solveSplitsHorizontal(game);
			// dubbels checken verticaal
			solveDoublesVertical(game);
			// splits checken verticaal
			solveSplitsVertical(game);
			// vul rijen aan horizontaal
			solveByCountHorizontal(game);
			// vul rijen aan verticaal
			solveByCountVertical(game);
			//game.print();
		}
		
		return game;
	}

	private Game solveDoublesHorizontal(Game game) {
		// check per rij
		for (int y = 0; y < rows; y++) {
			// check per kolom
			for (int i = 0; i < columns - 1; i++) {
				if (game.getValues()[y][i].equals(game.getValues()[y][i + 1]) && !game.getValues()[y][i].equals(".")) {
					if (i == 0) {
						checkAchter(game, y, i);
					} else if (i == columns - 2) {
						checkvoor(game, y, i);
					} else {
						checkAchter(game, y, i);
						checkvoor(game, y, i);
					}
				}
			}
		}
		
		return game;
	}

	private Game solveSplitsHorizontal(Game game) {
		// check per rij
		for (int y = 0; y < rows; y++) {
			// check per kolom
			for (int i = 0; i < columns - 2; i++) {
				if (game.getValues()[y][i].equals(game.getValues()[y][i + 2]) && !game.getValues()[y][i].equals(".")) {
					checkTussenHorizontal(game, y, i);
				}
			}
		}

		return game;
	}

	private Game solveDoublesVertical(Game game) {
		// check per kolom
		for (int i = 0; i < columns; i++) {
			// check per rij
			for (int y = 0; y < rows - 1; y++) {
				if (game.getValues()[y][i].equals(game.getValues()[y + 1][i]) && !game.getValues()[y][i].equals(".")) {
					if (y == 0) {
						checkOnder(game, y, i);
					} else if (y == rows - 2) {
						checkBoven(game, y, i);
					} else {
						checkOnder(game, y, i);
						checkBoven(game, y, i);
					}
				}
			}
		}

		return game;
	}

	private Game solveSplitsVertical(Game game) {
		// check per kolom
		for (int i = 0; i < columns; i++) {
			// check per rij
			for (int y = 0; y < rows - 2; y++) {
				if (game.getValues()[y][i].equals(game.getValues()[y + 2][i]) && !game.getValues()[y][i].equals(".")) {
					checkTussenVertical(game, y, i);
				}
			}
		}
		
		return game;
	}

	private Game solveByCountHorizontal(Game game) {

		int countOnes;
		int countZeros;
		
		// check per rij
		for (int y = 0; y < rows; y++) {
			countOnes = 0;
			countZeros = 0;
			// check per kolom
			for (int i = 0; i < columns; i++) {
				if (game.getValues()[y][i].equals("1")) {
					countOnes++;
				}
				if (game.getValues()[y][i].equals("0")) {
					countZeros++;
				}
			}
			if (countOnes == columns / 2) {
				fillEmptysWithZeroHorizontal(game, y);

			}
			if (countZeros == columns / 2) {
				fillEmptysWithOneHorizontal(game, y);
			}
		}

		return game;
	}

	private Game solveByCountVertical(Game game) {

		int countOnes;
		int countZeros;
		
		// check per kolom
		for (int i = 0; i < columns; i++) {
			countOnes = 0;
			countZeros = 0;
			// check per rij
			for (int y = 0; y < rows; y++) {
				if (game.getValues()[y][i].equals("1")) {
					countOnes++;
				}
				if (game.getValues()[y][i].equals("0")) {
					countZeros++;
				}
			}
			if (countOnes == rows / 2) {
				fillEmptysWithZeroVertical(game, i);

			}
			if (countZeros == rows / 2) {
				fillEmptysWithOneVertical(game, i);
			}
		}
		
		return game;
	}
	
	private void checkvoor(Game game, int y, int i) {
		if (game.getValues()[y][i - 1].equals(".")) {
			game.getValues()[y][i - 1] = setvalue(game.getValues()[y][i]);
			game.setChanged(true);
		}
	}

	private void checkAchter(Game game, int y, int i) {
		if (game.getValues()[y][i + 2].equals(".")) {
			game.getValues()[y][i + 2] = setvalue(game.getValues()[y][i]);
			game.setChanged(true);
		}
	}
	
	private void checkTussenVertical(Game game, int y, int i) {
		if (game.getValues()[y + 1][i].equals(".")) {
			game.getValues()[y + 1][i] = setvalue(game.getValues()[y][i]);
			game.setChanged(true);
		}
	}

	private void checkTussenHorizontal(Game game, int y, int i) {
		if (game.getValues()[y][i + 1].equals(".")) {
			game.getValues()[y][i + 1] = setvalue(game.getValues()[y][i]);
			game.setChanged(true);
		}
	}
	
	private void checkBoven(Game game, int y, int i) {
		if (game.getValues()[y - 1][i].equals(".")) {
			game.getValues()[y - 1][i] = setvalue(game.getValues()[y][i]);
			game.setChanged(true);
		}
	}

	private void checkOnder(Game game, int y, int i) {
		if (game.getValues()[y + 2][i].equals(".")) {
			game.getValues()[y + 2][i] = setvalue(game.getValues()[y][i]);
			game.setChanged(true);
		}
	}
	
	private void fillEmptysWithOneVertical(Game game, int i) {
		for (int y = 0; y < rows; y++) {
			if (game.getValues()[y][i].equals(".")) {
				game.getValues()[y][i] = "1";
				game.setChanged(true);
			}
		}
	}

	private void fillEmptysWithZeroVertical(Game game, int i) {
		for (int y = 0; y < rows; y++) {
			if (game.getValues()[y][i].equals(".")) {
				game.getValues()[y][i] = "0";
				game.setChanged(true);
			}
		}
	}
	
	private void fillEmptysWithOneHorizontal(Game game, int y) {
		for (int i = 0; i < columns; i++) {
			if (game.getValues()[y][i].equals(".")) {
				game.getValues()[y][i] = "1";
				game.setChanged(true);
			}
		}
	}

	private void fillEmptysWithZeroHorizontal(Game game, int y) {
		for (int i = 0; i < columns; i++) {
			if (game.getValues()[y][i].equals(".")) {
				game.getValues()[y][i] = "0";
				game.setChanged(true);
			}
		}
	}
	
	private String setvalue(String value) {
		if (value.equals("0")) {
			return "1";
		} else if (value.equals("1")) {
			return "0";
		} else {
			return value;
		}
	}
}
