package polyomino;

import java.awt.Color;
import java.util.LinkedList;

public class NaiveGenerator {
	
	public static LinkedList<Polyomino> genFixedPoly(int n) {
		if (n == 1) {
			LinkedList<Polyomino> PolyList = new LinkedList<Polyomino>();
			PolyList.add(new Polyomino("[(0,0)]"));
			return PolyList;
		} else {
			LinkedList<Polyomino> PrevPolyList = genFixedPoly(n - 1);
			LinkedList<Polyomino> PolyList = new LinkedList<Polyomino>();
			for (Polyomino P : PrevPolyList) {
				for (Polyomino newP : NaiveGenerator.AddFriends(P)) {
					if (!NaiveGenerator.contain(PolyList, newP)){
						PolyList.add(newP);
					}
				}
			}
			return PolyList;
		}
	}
	
	public static LinkedList<Polyomino> AddFriends(Polyomino P) {
		LinkedList<Polyomino> PolyList = new LinkedList<Polyomino>();
		for (int i = 0; i < P.getWidth(); i++) {
			for (int j = 0; j < P.getHeight(); j++) {
				if (P.contain(i, j)) {
					Pair p = new Pair(i, j);
					for (Pair friend : p.friends()) {
						Integer X = friend.getX();
						Integer Y = friend.getY();
						if (!P.contain(X,Y)) {
							PolyList.add(Polyomino.addUnitPoint(P, X, Y));
						}
					}
				}
			}
		}
		return PolyList;
	}

	public static boolean contain(LinkedList<Polyomino> PolyList, Polyomino P) {
		for (Polyomino Poly : PolyList) {
			if (NaiveGenerator.translationEquals(P, Poly))
				return true;
		}
		return false;
	}
	
	public static boolean translationEquals(Polyomino P, Polyomino Poly) {
		int minx_1 = P.getMinX();
		int miny_1 = P.getMinY();
		int minx_2 = Poly.getMinX();
		int miny_2 = Poly.getMinY();
		return Polyomino.equal(P.translation(-minx_1, -miny_1), Poly.translation(-minx_2, -miny_2));
	}

	public static void main(String[] args) {
		LinkedList<Polyomino> l = NaiveGenerator.genFixedPoly(5);
		System.out.println(l.size());
		for (Polyomino P : l) {
			System.out.println(P);
//			Image2d img = new Image2d(100,100);
//			P.draw(10, 50, 50, img, Color.RED);
//			new Image2dViewer(img);
		}
	}
}

class Pair {
	private Integer x;
	private Integer y;

	public Pair(Integer x, Integer y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		Pair that = (Pair) obj;
		return this.x == that.x && this.y == that.y;
	}

	@Override
	public int hashCode() {
		return this.x * 11 + this.y * 13;
	}
	
	public Integer getX() {
		return this.x;
	}
	
	public Integer getY() {
		return this.y;
	}

	public String toString() {
		return "(" + this.x + "," + this.y + ") ";
	}
	
	public LinkedList<Pair> friends() {
		LinkedList<Pair> friends = new LinkedList<Pair>();
		friends.add(new Pair(this.x, this.y + 1));
		friends.add(new Pair(this.x + 1, this.y));
		friends.add(new Pair(this.x, this.y - 1));
		friends.add(new Pair(this.x - 1, this.y));
		return friends;
	}
}