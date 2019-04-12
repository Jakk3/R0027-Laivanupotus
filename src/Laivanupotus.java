import java.util.Scanner;

public class Laivanupotus {

	public static void main(String[] args) {
		
		// declare variables and create a scanner
		Scanner input = new Scanner(System.in);
		int size = 10; // battle map size
		int shipCount = 6;
		int  shipLength = 6;
		char[][] sea = new char[size][size];
		char[][][] ships = new char[shipCount][size][size];

		ships = createShips(shipCount, shipLength, size);
		
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (ships[4][i][j] == '#') {
					System.out.print("# \t");
				} else {
					System.out.print("~ \t");
				}
			}
			System.out.println();
		}

		
	}
	
	public static char[][][] createShips(int shipCount, int shipLength, int size) {
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
				
			} while (checkCoordinates(x, y, dir, shipLength, temp, i)); // while coordinates dont collide
			
			// then creating the ship from the given starting point with the direction
			for (int j = 0; j < shipLength; j++) {
				temp [i][x][y] = '#';
				switch (dir) {
				case 0: // up
					if (y > 0 ) 
						y--;
					break;
				case 1: // right
					if (x < 9 ) 
						x++;
					break;
				case 2: // down
					if (y < 9 ) 
						y++;
					break;
				case 3: // left
					if (x > 0 ) 
						x--;
					break;
				}
			}
		}
		return temp;
	}
	
	
	public static boolean checkCoordinates(int x, int y, int dir, int shipLength, char[][][] temp, int shipIndex) {
		// going through ships created before the current ship
		for (int i = 0; i < shipLength; i++) {
			for (int j = 0; j < shipIndex; j++) {
				// if there is a ship block at a point where the new ship would have a ship block, return true
				if (temp[j][x][y] == '#') {
					return true;
				}
			}
			// change the x or y coordinate based on which direction the ship is build
			switch (dir) {
			case 0: // up
				if (y > 0 ) 
					y--;
				break;
			case 1: // right
				if (x < 9 ) 
					x++;
				break;
			case 2: // down
				if (y < 9 ) 
					y++;
				break;
			case 3: // left
				if (x > 0 ) 
					x--;
				break;
			}
		}
		// if there was no colliding coordinates, return false
		return false;
	}

}
