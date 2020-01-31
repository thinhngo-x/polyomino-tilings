package polyomino;

import java.util.Set;

public class test {
	public static void tiling1() {
		Polyomino P = new Polyomino("0 9 0 10 1 7 1 8 1 9 1 10 "
				+ "2 5 2 6 2 7 2 8 2 9 2 10 "
				+ "3 3 3 4 3 5 3 6 3 7 3 8 3 9 3 10 "
				+ "4 1 4 2 4 3 4 4 4 5 4 6 4 7 4 8 4 9 4 10 "
				+ "5 0 5 1 5 2 5 3 5 4 5 5 5 6 5 7 5 8 5 9 "
				+ "6 0 6 1 6 2 6 3 6 4 6 5 6 6 6 7 "
				+ "7 0 7 1 7 2 7 3 7 4 7 5 "
				+ "8 0 8 1 8 2 8 3 "
				+ "9 0 9 1");
		P = P.reflection("A");
		Set<ListOfPolyominoes> solutions = ExactCover.tilingsByFreePolyNoRep(P, 5);
		int i = solutions.size()/2;
		int j=0;
		int unit = 50;
		System.out.println(solutions.size());
		for(ListOfPolyominoes sol: solutions) {
			if(j == i) {
				Image2d img = new Image2d(P.getWidth()*unit, P.getHeight()*unit);
				sol.draw(unit, img);
				System.out.println(sol.toString());
			}
			j++;
		}
	}
	
	public static void main(String[] args) {
		tiling1();
	}
}
