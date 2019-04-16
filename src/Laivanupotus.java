import java.util.Scanner;

public class Laivanupotus {

	public static void main(String[] args) {
		
		// declare variables and create a scanner
		Scanner input = new Scanner(System.in);
		int size = 10; // battle map size
		int[]  shipLengths = { 1, 2, 3, 3, 4, 5 };
		int shipCount = 6;
		char[][] sea = new char[size][size];
		char[][][] ships = new char[shipCount][size][size];

		ships = createShips(shipCount, shipLengths, size);
		
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
					System.out.print("~ \t");
				}
			}
			System.out.println();
		}
		
	}
		
	
	public static char[][][] createShips(int shipCount, int[] shipLengths, int size) {
		// declaring local variables 
		int x, y, dir;
		char[][][] temp = new char[shipCount][size][size];
		
		// looping through creating multiple ships
		for (int i = 0; i < shipCount; i++) {

			// choosing random starting point and a direction for every ship
			// checking if the ship would collide with another ship with the starting point and direction
			do {
				System.out.println("uudet randomit");
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
			System.out.println("shiplength " + shipLength);
			System.out.println("x = " + x + " y = " + y);
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

}
