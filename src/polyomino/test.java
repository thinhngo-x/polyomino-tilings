package polyomino;

import java.util.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class test {

	public static void testElementaryOperations() {
		Image2d img = new Image2d(1200, 500);
		String s = "0,0,1,0,2,0,3,0,4,0,2,1,2,2,2,3,0,4,1,4,2,4,3,4,4,4,6,0,6,1,6,2,6,3,6,4,7,3,8,2,9,1,10,0,10,1,10,2,10,3,10,4,12,0,12,1,12,2,12,3,12,4,13,4,14,4,15,4,16,4,13,2,14,2";
		Polyomino test = new Polyomino(s);
		LinkedList<Polyomino> ps = new LinkedList<Polyomino>();
		ps.add(test);
		ps.add(test.translation(2, 2));
		ps.add(test.rotation().rotation().rotation());
		ps.add(test.reflection("V"));
		ps.add(test.dilation(2));
		ListOfPolyominoes.olddraw(ps, 10, 100, 250, img);
		new Image2dViewer(img);
	}

	public static void testTxtFile() {
		File fileName = new File("polyominoesINF421.txt");
		LinkedList<Polyomino> ps = ListOfPolyominoes.readFiles(fileName);
		int unit = 10;
		int px = 0;
		int py = 10 * unit;
		Image2d img = new Image2d(30 * unit, py);
		ListOfPolyominoes.olddraw(ps, unit, px, py, img);
		new Image2dViewer(img);
	}

	public static void compareNaiveRedelmeier() {
		int n = 5;

		long time1 = System.currentTimeMillis();
		Enumeration.fixedPolyominoes(n);
		long time2 = System.currentTimeMillis();
		System.out.println("Time required for enumeration of fixed polyominoes (Redelmeier): ");
		System.out.println(time2 - time1);

		long time3 = System.currentTimeMillis();
		NaiveGenerator.enuFixedPoly(n);
		long time4 = System.currentTimeMillis();
		System.out.println("Time required for enumeration of fixed polyominoes (Naive): ");
		System.out.println(time4 - time3);

		long time5 = System.currentTimeMillis();
		Enumeration.freePolyominoes(n);
		long time6 = System.currentTimeMillis();
		System.out.println("Time required for enumeration of free polyominoes (Redelmeier): ");
		System.out.println(time6 - time5);

		long time7 = System.currentTimeMillis();
		NaiveGenerator.enuFreePoly(n);
		long time8 = System.currentTimeMillis();
		System.out.println("Time required for enumeration of free polyominoes (Naive): ");
		System.out.println(time8 - time7);
	}

	public static void polyon(int n) throws IOException {
		Set<Polyomino> solutions = Enumeration.genFreePolyominoes(n);
		Set<ListOfPolyominoes> set = new HashSet<ListOfPolyominoes>();
		for (Polyomino sol : solutions) {
			ListOfPolyominoes list = new ListOfPolyominoes();
			list.add(sol);
			set.add(list);
		}
		int unit = 50;
		int i = 0;
		System.out.println("Number of Free Polyominoes of size " + n + ": ");
		System.out.println(set.size());
		for (ListOfPolyominoes sol : set) {
			i++;
			Image2d img = new Image2d(n * unit, (n + 2) * unit);
			sol.draw(unit, 0, 5 * unit, img);
			Image2dComponent component = new Image2dComponent(img);
			component.setVisible(false);
			BufferedImage bi = ScreenImage.createImage(component);
			ScreenImage.writeImage(bi, "results/polyon/" + i + ".png");
			System.out.println("Printing..." + i);
		}
	}

	public static void compareExactCoverDancingLinksOnExampleGiven() {
		Set<Integer> X_ = new HashSet<>();
		for (int i = 1; i <= 7; i++)
			X_.add(i);
		Set<Set<Integer>> C_ = new HashSet<>();
		C_.add(new HashSet<Integer>(Arrays.asList(3, 5, 6)));
		C_.add(new HashSet<Integer>(Arrays.asList(1, 4, 7)));
		C_.add(new HashSet<Integer>(Arrays.asList(2, 3, 6)));
		C_.add(new HashSet<Integer>(Arrays.asList(1, 4)));
		C_.add(new HashSet<Integer>(Arrays.asList(2, 7)));
		C_.add(new HashSet<Integer>(Arrays.asList(4, 5, 7)));

		long start1 = System.currentTimeMillis();
		ExactCover.solve(X_, C_);
		long end1 = System.currentTimeMillis();
		System.out.println("Time required for normal exact cover: ");
		System.out.println(end1 - start1);

		long start2 = System.currentTimeMillis();
		ExactCover.fastersolve(X_, C_);
		long end2 = System.currentTimeMillis();
		System.out.println("Time required for faster exact cover (with choice of column): ");
		System.out.println(end2 - start2);

		Node A = new Node("A");
		Node B = new Node("B");
		Node C = new Node("C");
		Node D = new Node("D");
		Node E = new Node("E");
		Node F = new Node("F");
		Node G = new Node("G");
		LinkedList<Node> X = new LinkedList<Node>();
		X.addAll(Arrays.asList(A, B, C, D, E, F, G));
		HashSet<LinkedList<Node>> Collection = new HashSet<LinkedList<Node>>();
		Collection.addAll(Arrays.asList(new LinkedList<Node>(Arrays.asList(C, E, F)),
				new LinkedList<Node>(Arrays.asList(A, D, G)), new LinkedList<Node>(Arrays.asList(B, C, F)),
				new LinkedList<Node>(Arrays.asList(A, D)), new LinkedList<Node>(Arrays.asList(B, G)),
				new LinkedList<Node>(Arrays.asList(D, E, G))));
		DancingLinks DL = new DancingLinks(X, Collection);
		long start = System.currentTimeMillis();
		DL.exactCover();
		long end = System.currentTimeMillis();
		System.out.println("Time required for Dancing Links: ");
		System.out.println(end - start);
	}

	public static void compareExactCoverDancingLinksOnProblemNK(int n, int k) {
		LinkedList<Node> column = new LinkedList<Node>();
		for (int i = 1; i <= n; i++) {
			column.add(new Node(Integer.toString(i)));
		}
		DancingLinks DLSubsetCase = new DancingLinks(column);
		HashSet<int[]> set = DancingLinks.Generator(n, k);
		for (int[] array : set) {
			LinkedList<Node> row = new LinkedList<Node>();
			for (int i : array) {
				if (i != 0) {
					row.add(column.get(i - 1));
				}
			}
			DLSubsetCase.AddDataRow(row);
		}

		Set<Integer> X = new HashSet<Integer>();
		for (int i = 1; i <= n; i++) {
			X.add(i);
		}

		Set<Set<Integer>> C = new HashSet<Set<Integer>>();
		for (int[] array : set) {
			Set<Integer> temp = new HashSet<Integer>();
			for (int i : array) {
				if (i != 0) {
					temp.add(i);
				}
			}
			C.add(temp);
		}

		long start1 = System.currentTimeMillis();
		ExactCover.solve(X, C);
		long end1 = System.currentTimeMillis();
		System.out.println("Time required for normal exact cover: ");
		System.out.println(end1 - start1);

		long start2 = System.currentTimeMillis();
		ExactCover.fastersolve(X, C);
		long end2 = System.currentTimeMillis();
		System.out.println("Time required for faster exact cover (with choice of column): ");
		System.out.println(end2 - start2);

		long start3 = System.currentTimeMillis();
		DLSubsetCase.exactCover();
		long end3 = System.currentTimeMillis();
		System.out.println("Time required for Dancing Links: ");
		System.out.println(end3 - start3);
	}

	public static void tiling1() throws IOException {
		Polyomino P = new Polyomino("0,9,0,10,1,7,1,8,1,9,1,10," + "2,5,2,6,2,7,2,8,2,9,2,10,"
				+ "3,3,3,4,3,5,3,6,3,7,3,8,3,9,3,10," + "4,1,4,2,4,3,4,4,4,5,4,6,4,7,4,8,4,9,4,10,"
				+ "5,0,5,1,5,2,5,3,5,4,5,5,5,6,5,7,5,8,5,9," + "6,0,6,1,6,2,6,3,6,4,6,5,6,6,6,7,"
				+ "7,0,7,1,7,2,7,3,7,4,7,5," + "8,0,8,1,8,2,8,3," + "9,0,9,1");
		P = P.reflection("A");
		Set<ListOfPolyominoes> solutions = ExactCover.tilingsByFreePolyNoRep(P, 5);
		int unit = 50;
		int i = 0;
		System.out.println("Number of solutions: ");
		System.out.println(solutions.size());
		for (ListOfPolyominoes sol : solutions) {
			i++;
			Image2d img = new Image2d(P.getWidth() * unit, P.getHeight() * unit);
			sol.draw(unit, img, P.getMaxY());
			Image2dComponent component = new Image2dComponent(img);
			component.setVisible(false);
			BufferedImage bi = ScreenImage.createImage(component);
			ScreenImage.writeImage(bi, "results/tiling1/" + i + ".png");
			System.out.println("Printing..." + i);
		}
	}

	public static void tiling2() throws IOException {
		Polyomino P = new Polyomino(
				"0,0,1,0,2,0,3,0,4,0,5,0,6,0,7,0,8,0,9,0,0,1,1,1,2,1,3,1,4,1,5,1,6,1,7,1,8,1,9,1,1,2,2,2,3,2,4,2,5,2,6,2,7,2,8,2,1,3,2,3,3,3,4,3,5,3,6,3,7,3,8,3,2,4,3,4,4,4,5,4,6,4,7,4,2,5,3,5,4,5,5,5,6,5,7,5,3,6,4,6,5,6,6,6,3,7,4,7,5,7,6,7,4,8,5,8,4,9,5,9");
		Set<ListOfPolyominoes> solutions = ExactCover.tilingsByFreePolyNoRep(P, 5);
		int unit = 50;
		int i = 0;
		System.out.println("Number of solutions: ");
		System.out.println(solutions.size());
		for (ListOfPolyominoes sol : solutions) {
			i++;
			Image2d img = new Image2d(P.getWidth() * unit, P.getHeight() * unit);
			sol.draw(unit, img);
			Image2dComponent component = new Image2dComponent(img);
			component.setVisible(false);
			BufferedImage bi = ScreenImage.createImage(component);
			ScreenImage.writeImage(bi, "results/tiling2/" + i + ".png");
			System.out.println("Printing..." + i);
		}
	}

	public static void tiling3() throws IOException {
		Polyomino P = new Polyomino(
				"0,4,0,5,1,3,1,4,1,5,1,6,2,2,2,3,2,4,2,5,2,6,2,7,3,1,3,2,3,3,3,4,3,5,3,6,3,7,3,8,4,0,4,1,4,2,4,3,4,4,4,5,4,6,4,7,4,8,4,9,5,0,5,1,5,2,5,3,5,4,5,5,5,6,5,7,5,8,5,9,6,1,6,2,6,3,6,4,6,5,6,(6,6,7,6,8,7,2,7,3,7,4,7,5,7,6,7,7,8,3,8,4,8,5,8,6,9,4,9,5");
		int unit = 50;
		ListOfPolyominoes list = new ListOfPolyominoes();
		list.add(P);
		Set<ListOfPolyominoes> solutions = ExactCover.tilingsByFreePolyNoRep(P, 5);
		System.out.println("Number of solutions: ");
		System.out.println(solutions.size());
		Image2d img = new Image2d(P.getWidth() * unit, P.getHeight() * unit);
		list.draw(unit, img);
		Image2dComponent component = new Image2dComponent(img);
		component.setVisible(false);
		BufferedImage bi = ScreenImage.createImage(component);
		ScreenImage.writeImage(bi, "results/tiling3/" + 1 + ".png");
		System.out.println("Printing..." + 1);
	}

	public static void rectiling(String indication, int n) throws IOException {
		int number = 0;
		if (indication == "fixed") {
			number = Enumeration.fixedPolyominoes(n);
		} else if (indication == "free") {
			number = (int) Enumeration.freePolyominoes(n);
		}

		int area = n * number;
		int maxside = (int) Math.sqrt(area);
		LinkedList<Point> possibleSize = new LinkedList<Point>();

		for (int i = 1; i < maxside; i++) {
			if (area % i == 0) {
				possibleSize.add(new Point(i, area / i));
			}
		}
		while (!possibleSize.isEmpty()) {
			Point p = possibleSize.pop();
			int x = p.getX();
			int y = p.getY();
			System.out.println("Rectangle with sides: " + "(" + y + "," + x + ")");
			String s = "";
			for (int i = 0; i < y; i++) {
				for (int j = 0; j < x; j++) {
					s += i + " " + j + " ";
				}
			}
			Polyomino P = new Polyomino(s);
			Set<ListOfPolyominoes> solutions = null;
			if (indication == "fixed") {
				solutions = ExactCover.tilingsByFixedPolyNoRep(P, n);
			} else if (indication == "free") {
				solutions = ExactCover.tilingsByFreePolyNoRep(P, n);
			}
			int unit = 50;
			int i = 0;
			System.out.println("Number of solutions: ");
			System.out.println(solutions.size());
			for (ListOfPolyominoes sol : solutions) {
				i++;
				Image2d img = new Image2d(P.getWidth() * unit, P.getHeight() * unit);
				sol.draw(unit, img);
				Image2dComponent component = new Image2dComponent(img);
				component.setVisible(false);
				BufferedImage bi = ScreenImage.createImage(component);
				ScreenImage.writeImage(bi,
						"results/rectiling/" + "Size " + n + " " + "(" + y + "," + x + ")" + i + ".png");
				System.out.println("Printing..." + i);
				if (i == 100) {
					break;
				}
			}
		}
	}

	public static void tilingOfDilate(int n, int k) throws IOException {
		Map<Polyomino, Set<ListOfPolyominoes>> solutions = ExactCover.tilingOfDilate(n, k);
		System.out.println("Number of solutions: ");
		System.out.println(solutions.size());
		int unit = 50;
		int i = 0;
		for (Map.Entry<Polyomino, Set<ListOfPolyominoes>> entry : solutions.entrySet()) {
			i++;
			Polyomino P = entry.getKey();
			Set<ListOfPolyominoes> tiling = entry.getValue();
			Image2d img = new Image2d(P.getWidth() * unit, P.getHeight() * unit);
			ListOfPolyominoes firstSol = tiling.iterator().next();
			firstSol.draw(unit, img, P.getMaxY());
			Image2dComponent component = new Image2dComponent(img);
			component.setVisible(false);
			BufferedImage bi = ScreenImage.createImage(component);
			ScreenImage.writeImage(bi, "results/tilingOfDilate/" + i + ".png");
			System.out.println("Printing..." + i);
		}
	}

	public static void main(String[] args) throws IOException {
		testElementaryOperations();
		testTxtFile();
		compareNaiveRedelmeier();
		polyon(5);
		compareExactCoverDancingLinksOnExampleGiven();
		compareExactCoverDancingLinksOnProblemNK(6, 3);
		tiling1();
		tiling2();
		tiling3();
		rectiling("free", 5);
		tilingOfDilate(8, 4);
	}
}
