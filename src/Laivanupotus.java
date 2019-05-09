import java.util.Scanner;

public class Laivanupotus {

	public static void main(String[] args) {
		
		// declare variables and create a scanner
		Scanner input = new Scanner(System.in);
		int size = 10; // battle map size
		int[]  shipLengths = { 5, 4, 3, 3, 2, 1 };
		int shipCount = shipLengths.length;
		char[][] sea = new char[size][size];
		char[][][] ships = new char[shipCount][size][size];

		ships = createShips(shipCount, shipLengths, size);
		
		
		/*
		for (int c = 0; c < shipCount; c++) {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (ships[c][i][j] == '#') {
					sea[i][j] = '#';
				}
			}
		}
		}
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (sea[i][j] == '#') {
					System.out.print("# \t");
				} else {
					System.out.print(". \t");
				}
			}
			System.out.println();
		} */

		do {
		printSea(sea);
		sea = askCoordinates(ships, sea);
		sea = shipSinker(ships, sea, shipLengths);
		} while (shipChecker(ships, sea));
	}
		
	//Let's create the ships in a new method
	public static char[][][] createShips(int shipCount, int[] shipLengths, int size) {
		// declaring local variables 
		int x, y, dir;
		char[][][] temp = new char[shipCount][size][size];
		
		// looping through creating multiple ships
		for (int i = 0; i < shipCount; i++) {

			// choosing random starting point and a direction for every ship
			// checking if the ship would collide with another ship with the starting point and direction
			do {
				x = (int)(Math.random() * (size - 1));
				y = (int)(Math.random() * (size - 1));
				dir = (int)(Math.random() * 4);
				
			} while (checkCoordinates(x, y, dir, shipLengths[i],shipLengths, temp, i, size)); // while coordinates dont collide
			
			// then creating the ship from the given starting point with the direction
			for (int j = 0; j < shipLengths[i]; j++) {
				temp [i][y][x] = '#';
				switch (dir) {
				case 0: // up
					y--;
					break;
				case 1: // right
					x++;
					break;
				case 2: // down
					y++;
					break;
				case 3: // left
					x--;
					break;
				}
			}
		}
		return temp;
	}
	
	//Let's check if the ship can be placed
	public static boolean checkCoordinates(int x, int y, int dir, int shipLength, int[] shipLengths, char[][][] temp, int shipIndex, int size) {
		// going through ships created before the current ship
		for (int i = 0; i < shipLength; i++) {
			for (int j = 0; j < shipIndex; j++) {
				// if there is a ship block at a point where the new ship would have a ship block, return true
				if (temp[j][y][x] == '#') {
					return true;
				}
			}
			// change the x or y coordinate based on which direction the ship is build
			switch (dir) {
			case 0: // up
				if (y > (shipLengths[i] - 1)) 
					y--;
				else 
					return true;
				break;
			case 1: // right
				if (x < (size - 1 - shipLengths[i])) 
					x++;
				else 
					return true;
				break;
			case 2: // down
				if (y < (size - 1 - shipLengths[i]) ) 
					y++;
				else 
					return true;
				break;
			case 3: // left
				if (x > (shipLengths[i] - 1)) 
					x--;
				else 
					return true;
				break;
			}
		}
		// if there was no colliding coordinates, return false
		return false;
	}
	
	//Let's start asking the player for coordinates
	public static char[][] askCoordinates(char[][][] ships, char[][] sea) {
		Scanner input = new Scanner(System.in);
		int x;
		int y;
		
		System.out.println("Where do you wanna shoot?");
		do {
			System.out.print("Give X: ");
			x = input.nextInt() - 1;
		} while (x < 0 || x > sea.length - 1);
		// coz char starts at 0
		// sea.length so we can later maybe change the map size
		do {
			System.out.print("Give Y: ");
			y = input.nextInt() - 1;
		} while (y < 0 || y > sea.length - 1);
		
	//Let's check the game map for hit or miss
		
		for (int i = 0; i < ships.length; i++) {
			if (ships[i][y][x] == '#') {
				sea[y][x] = '#';
				System.out.println("You hit!");
				return sea;
				//If there is a hit for any ship then the loop ends
			} else {
				sea[y][x] = 'O';
			}
		}

		System.out.println("You missed!");
		return sea;		
	}
	
	//Let's print the sea
	public static void printSea(char[][] sea) {
		for (int i = 0; i < sea.length; i++) {
			for (int j = 0; j < sea.length; j++) {
				if (sea[i][j] == '#')
					System.out.print("# \t");
				else if (sea[i][j] == 'O')
					System.out.print("O \t");
				else if (sea[i][j] == 'X')
					System.out.print("X \t");
				else 
					System.out.print(". \t");
			}
			System.out.println();
		}
		
	}
	
	public static char[][] shipSinker(char[][][] ships, char[][] sea, int[] shipLengths) {
		int shipLength;
		// compare the battle map to every ships location. for every location that matches
		// add to the value of shown length of the ship. 
		for (int i = 0; i < ships.length; i++) {
			shipLength = 0;
			for (int y = 0; y < sea.length; y++) {
				for (int x = 0; x < sea[y].length; x++) {
					if (ships[i][y][x] == sea[y][x] && sea[y][x] == '#') {
						shipLength++;
					}
				}
			}
			// if the whole ship is on the battle map (every block of the ship is hit)
			// change the ships shown blocks to X
			if (shipLength == shipLengths[i]) {
				for (int y = 0; y < sea.length; y++) {
					for (int x = 0; x < sea[y].length; x++) {
						if (ships[i][y][x] == sea[y][x] && sea[y][x] == '#') {
							sea[y][x] = 'X';
						}
					}
				}
			}
		}
		
		return sea;
	}
	

	//Let's check if all the ships have been sinked
	public static boolean shipChecker(char[][][] ships, char[][] sea) {
		int hitblocks = 0;
		for(int i = 0; i < ships.length; i++) {
			for (int j = 0; j < ships[i].length; j++) {
				for(int k = 0; k < ships[i].length; k++) {
					if (ships[i][j][k] == sea[j][k])
						hitblocks++;
				}
			}
		}
		if (hitblocks == 18) 
			return false;
		else
			return true;
	}
	
	
}
