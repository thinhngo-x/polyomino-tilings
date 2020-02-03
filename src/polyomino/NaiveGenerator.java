package polyomino;

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
					if (!NaiveGenerator.containInListTranslation(PolyList, newP)) {
						PolyList.add(newP);
					}
				}
			}
			return PolyList;
		}
	}

	public static LinkedList<Polyomino> genFreePoly(int n) {
		LinkedList<Polyomino> PolyList = new LinkedList<Polyomino>();
		LinkedList<Polyomino> fixed = genFixedPoly(n);
		for (Polyomino P : fixed) {
			if (!NaiveGenerator.containInListSymmetry(PolyList, P)) {
				PolyList.add(P);
			}
		}
		return PolyList;
	}

	public static int enuFixedPoly(int n) {
		return genFixedPoly(n).size();
	}

	public static int enuFreePoly(int n) {
		return genFreePoly(n).size();
	}

	public static LinkedList<Polyomino> AddFriends(Polyomino P) {
		LinkedList<Polyomino> PolyList = new LinkedList<Polyomino>();
		for (int i = -P.getWidth(); i <= P.getWidth(); i++) {
			for (int j = -P.getHeight(); j <= P.getHeight(); j++) {
				if (P.contain(i, j)) {
					Point p = new Point(i, j);
					for (Point friend : p.friends()) {
						Integer X = friend.getX();
						Integer Y = friend.getY();
						if (!P.contain(X, Y)) {
							PolyList.add(Polyomino.addUnitPoint(P, X, Y));
						}
					}
				}
			}
		}
		return PolyList;
	}

	public static boolean containInListTranslation(LinkedList<Polyomino> PolyList, Polyomino P) {
		for (Polyomino Poly : PolyList) {
			if (NaiveGenerator.translationEquals(P, Poly))
				return true;
		}
		return false;
	}

	public static boolean containInListSymmetry(LinkedList<Polyomino> PolyList, Polyomino P) {
		for (Polyomino Poly : PolyList) {
			if (NaiveGenerator.translationEquals(P, Poly) || NaiveGenerator.translationEquals(P.reflection("H"), Poly)
					|| NaiveGenerator.translationEquals(P.reflection("V"), Poly)
					|| NaiveGenerator.translationEquals(P.reflection("A"), Poly)
					|| NaiveGenerator.translationEquals(P.reflection("D"), Poly)
					|| NaiveGenerator.translationEquals(P.rotation(), Poly)
					|| NaiveGenerator.translationEquals(P.rotation().rotation(), Poly)
					|| NaiveGenerator.translationEquals(P.rotation().rotation().rotation(), Poly)
					|| NaiveGenerator.translationEquals(P.rotation().rotation().rotation().rotation(), Poly)) {
				return true;
			}
		}
		return false;
	}

	public static boolean translationEquals(Polyomino P, Polyomino Poly) {
		return P.fixedEqualsTo(Poly);
	}

}
