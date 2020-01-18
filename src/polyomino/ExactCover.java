package polyomino;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;

public class ExactCover {

	public static Random R = new Random();

	public static boolean MethodExactCover(ArrayList<Integer> X, ArrayList<ArrayList<Integer>> C) {
		if (X.isEmpty()) {
			return true;
		}
		/* choose random element x */
		int r = R.nextInt(X.size());
		Integer x = X.get(r);
		/* initialize result ArrayList */
		HashSet<ArrayList<Integer>> P = new HashSet<ArrayList<Integer>>();
		for (int i = 0; i < C.size(); i++) {
			/* choose S such that x in S */
			ArrayList<Integer> S = C.get(i);
			if (S.contains(x)) {
				/* copy X */
				P.add(S);
				ArrayList<Integer> X_ = new ArrayList<Integer>();
				X_.addAll(X);
				/* copy C */
				ArrayList<ArrayList<Integer>> C_ = new ArrayList<ArrayList<Integer>>();
				int a = 0;
				while (C_.size() != C.size()) {
					C_.add(new ArrayList<Integer>());
					C_.get(a).addAll(C.get(a));
					a++;
				}
				for (int j = 0; j < S.size(); j++) {
					/* all y in S */
					Integer y = S.get(j);
					X_.remove(y);
					for (int k = 0; k < C.size(); k++) {
						/* choose T such that y in T */
						ArrayList<Integer> T = C.get(k);
						if (T.contains(y)) {
							C_.remove(T);
						}
					}
				}
				if (!MethodExactCover(X_, C_)) {
					P.remove(S);
				} else {
					System.out.println(P);
					return true;
				}
			}
		}
		return false;
	}
	
	public static boolean FasterMethodExactCover(ArrayList<Integer> X, ArrayList<ArrayList<Integer>> C) {
		if (X.isEmpty()) {
			return true;
		}
		int min = Integer.MAX_VALUE;
		Integer x = Integer.MAX_VALUE;
		for (int i = 0; i < C.size(); i++) {
			min = Math.min(min, C.get(i).size());
		}
		for (int i = 0; i < C.size(); i++) {
			if (C.get(i).size() == min) {
				int r = R.nextInt(min);
				x = C.get(i).get(r);
			}
		}
		/* initialize result ArrayList */
		HashSet<ArrayList<Integer>> P = new HashSet<ArrayList<Integer>>();
		for (int i = 0; i < C.size(); i++) {
			/* choose S such that x in S */
			ArrayList<Integer> S = C.get(i);
			if (S.contains(x)) {
				/* copy X */
				P.add(S);
				ArrayList<Integer> X_ = new ArrayList<Integer>();
				X_.addAll(X);
				/* copy C */
				ArrayList<ArrayList<Integer>> C_ = new ArrayList<ArrayList<Integer>>();
				int a = 0;
				while (C_.size() != C.size()) {
					C_.add(new ArrayList<Integer>());
					C_.get(a).addAll(C.get(a));
					a++;
				}
				for (int j = 0; j < S.size(); j++) {
					/* all y in S */
					Integer y = S.get(j);
					X_.remove(y);
					for (int k = 0; k < C.size(); k++) {
						/* choose T such that y in T */
						ArrayList<Integer> T = C.get(k);
						if (T.contains(y)) {
							C_.remove(T);
						}
					}
				}
				if (!MethodExactCover(X_, C_)) {
					P.remove(S);
				} else {
					System.out.println(P);
					return true;
				}
			}
		}
		return false;
	}

	public static void main(String[] args) {
		ArrayList<Integer> X = new ArrayList<Integer>(Arrays.asList(1, 2, 3, 4, 5, 6, 7));
		ArrayList<ArrayList<Integer>> C = new ArrayList<ArrayList<Integer>>(Arrays.asList(
				new ArrayList<Integer>(Arrays.asList(3, 5, 6)), new ArrayList<Integer>(Arrays.asList(1, 4, 7)),
				new ArrayList<Integer>(Arrays.asList(2, 3, 6)), new ArrayList<Integer>(Arrays.asList(1, 4)),
				new ArrayList<Integer>(Arrays.asList(2, 7)), new ArrayList<Integer>(Arrays.asList(4, 5, 7))));
		
		long t1 = System.currentTimeMillis();
		System.out.println("The Partitions are formed by:");
		MethodExactCover(X, C);
		long t2 = System.currentTimeMillis();
		System.out.println("Time Required:");
		System.out.println(t2-t1);
		
		long t3 = System.currentTimeMillis();
		System.out.println("The Partitions are formed by:");
		FasterMethodExactCover(X, C);
		long t4 = System.currentTimeMillis();
		System.out.println("Time Required:");
		System.out.println(t4-t3);
	}

}
