package polyomino;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class NaiveGenerator {

	public static HashSet<Pair[]> NaiveGenFixedPolyominoes(int n) {
		Pair[] sol = new Pair[n];
		HashSet<Pair[]> allsol = new HashSet<Pair[]>();
		Integer k = 0;
		Integer xcoords = 0;
		Integer ycoords = 0;
		Pair p = new Pair(xcoords, ycoords);
		HashMap<Integer, Pair> possible = new HashMap<Integer, Pair>();
		HashSet<Pair> indication = new HashSet<Pair>();
		indication.add(p);
		possible.put(k++, p);
		helper(allsol, sol, possible, indication, 0, 0, 2 * n + 2, k);
		/*check translation*/
		return allsol;
	}

	public static void helper(HashSet<Pair[]> allsol, Pair[] sol, HashMap<Integer, Pair> possible,
			HashSet<Pair> indication, int start, int index, int end, int k) {
		if (sol.length == index) {
			allsol.add(sol.clone());
		} else if (start <= end) {
			Pair p = possible.get(start);
			sol[index] = p;
			if (p != null) {
				Integer xcoords = p.x;
				Integer ycoords = p.y;
				Pair q = new Pair(xcoords, ycoords + 1);
				Pair r = new Pair(xcoords + 1, ycoords);
				Pair s = new Pair(xcoords, ycoords - 1);
				Pair t = new Pair(xcoords - 1, ycoords);
				int k_copy = k;
				if (indication.add(q)) {
					possible.put(k++, q);
				}
				if (indication.add(r)) {
					possible.put(k++, r);
				}
				if (indication.add(s)) {
					possible.put(k++, s);
				}
				if (indication.add(t)) {
					possible.put(k++, t);
				}
				helper(allsol, sol, possible, indication, start + 1, index + 1, end, k);
				while (k_copy < k) {
					indication.remove(possible.get(k_copy));
					possible.remove(k_copy++);
				}
				helper(allsol, sol, possible, indication, start + 1, index, end, k);
			}
		}
	}/*renumber*/

	public static void main(String[] args) {
		HashSet<Pair[]> allsol = NaiveGenerator.NaiveGenFixedPolyominoes(2);
		System.out.println(allsol.size());
		for (Pair[] array : allsol) {
			for (Pair p : array) {
				System.out.println(p);
			}
			System.out.println();
		}

	}
}

class Pair {
	Integer x;
	Integer y;

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

	public String toString() {
		return "(" + this.x + "," + this.y + ") ";
	}
}