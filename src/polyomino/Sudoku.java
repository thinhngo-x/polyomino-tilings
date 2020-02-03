package polyomino;

import java.util.*;

public class Sudoku {
	int[][] grid;

	Sudoku(int[][] pre) {
		this.grid = pre.clone();
	}

	public int numberOfElements(int c) {
		int size = grid.length;
		int count = 0;
		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++) {
				if (grid[i][j] == c) {
					count += 1;
				}
			}
		return count;
	}

	public Set<String> createGround() {
		Set<String> X = new HashSet<>();
		int size = grid.length;
		for (int i = 1; i <= size; i++) {
			for (int number = 1; number <= size; number++) {
				X.add(("N" + i) + number);
				X.add(("C" + i) + number);
				X.add(("R" + i) + number);
				X.add(("B" + i) + number);
			}
		}
		int sizeOfBox = (int) Math.sqrt(size);
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (grid[i][j] == 0)
					continue;
				int box = (i / sizeOfBox) * sizeOfBox + (j / sizeOfBox) + 1;
				X.remove(("C" + (j + 1)) + grid[i][j]);
				X.remove(("R" + (i + 1)) + grid[i][j]);
				X.remove(("B" + box) + grid[i][j]);
			}
		}
		for (int j = 1; j <= size; j++) {
			for (int k = size - numberOfElements(j) + 1; k <= size; k++) {
				X.remove(("N" + k + j));
			}
		}
		return X;
	}

	public Set<Set<String>> createCollection(Set<String> X) {
		int size = grid.length;
		Set<Set<String>> C = new HashSet<>();
		int sizeOfBox = (int) Math.sqrt(size);
		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++) {
				if (grid[i][j] != 0)
					continue;
				for (int number = 1; number <= size; number++) {
					int box = (i / sizeOfBox) * sizeOfBox + (j / sizeOfBox) + 1;
					if (!X.contains(("C" + (j + 1)) + number) || !X.contains(("R" + (i + 1)) + number)
							|| !X.contains(("B" + box) + number))
						continue;
					Set<String> subset = new HashSet<>();
					subset.add(("C" + (j + 1)) + number);
					subset.add(("R" + (i + 1)) + number);
					subset.add(("B" + box) + number);
					for (int k = 1; k <= size - numberOfElements(number); k++) {
						Set<String> subsubset = new HashSet<>();
						for (String s : subset) {
							subsubset.add(s);
						}
						subsubset.add(("N" + k) + number);
						C.add(subsubset);
					}
				}
			}
		return C;
	}

	public void solve() {
		Set<String> X = createGround();
		Set<Set<String>> C = createCollection(X);
		DancingLinks dl = new DancingLinks(X, C);
		for (Set<Node> solution : dl.exactCover()) {
			int completedGrid[][] = grid.clone();
			for (Node h : solution) {
				int column = 0;
				int row = 0;
				int number = 0;
				Node x = h;
				do {
					String s = x.C.N;
					switch (s.charAt(0)) {
					case 'C':
						column = Character.getNumericValue(s.charAt(1)) - 1;
						break;
					case 'R':
						row = Character.getNumericValue(s.charAt(1)) - 1;
						break;
					}
					number = Character.getNumericValue(s.charAt(2));
					x = x.R;
				} while (x != h);
				completedGrid[row][column] = number;
			}
			System.out.println(completedGrid.toString());
			System.out.println();
		}
	}

	public void solveWithoutDL() {
		Set<String> X = createGround();
		Set<Set<String>> C = createCollection(X);
		for (Set<Set<String>> solution : ExactCover.fastersolve(X, C)) {
			int completedGrid[][] = grid.clone();
			for (Set<String> coord : solution) {
				int column = 0;
				int row = 0;
				int number = 0;
				for (String s : coord) {
					switch (s.charAt(0)) {
					case 'C':
						column = Character.getNumericValue(s.charAt(1)) - 1;
						break;
					case 'R':
						row = Character.getNumericValue(s.charAt(1)) - 1;
						break;
					}
					number = Character.getNumericValue(s.charAt(2));
				}
				completedGrid[row][column] = number;
			}
			System.out.println(Arrays.toString(completedGrid[0]));
			System.out.println(Arrays.toString(completedGrid[1]));
			System.out.println(Arrays.toString(completedGrid[2]));
			System.out.println(Arrays.toString(completedGrid[3]));
			System.out.println();
		}
	}

	public static void main(String[] args) {
		int[][] grid = { { 1, 0, 4, 0 }, { 0, 3, 1, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 1 } };
		;
		Sudoku sudoku = new Sudoku(grid);
		sudoku.solveWithoutDL();
	}

}