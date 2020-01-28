package polyomino;

import java.util.*;

public class ExactCover {


	public static <E> Set<Set<Set<E>>> solve(Set<E> X, Set< Set<E> > C) {
		Set<Set< Set<E> > > result = new HashSet<>();
		if(X.isEmpty()) {
			result.add(new HashSet<>());
			return(result);
		}
			
		Set<E> X_ = new HashSet<>(X);
		Set< Set<E> > C_ = new HashSet<>(C);
		
		E x = X_.iterator().next();
		for(Set<E> S: C) {
			if(!S.contains(x)) continue;
			for(E y: S) {
				X_.remove(y);
				for(Set<E> otherS: C) {
					if(otherS.contains(y))
						C_.remove(otherS);
				}
			}
			
			for(Set<Set<E>> P: solve(X_, C_)) {
				P.add(S);
				result.add(P);
			}
		}
		
		return result;
	}

	public static void main(String[] args) {
		ArrayList<Integer> X = new ArrayList<Integer>(Arrays.asList(1, 2, 3, 4, 5, 6, 7));
		ArrayList<ArrayList<Integer>> C = new ArrayList<ArrayList<Integer>>(Arrays.asList(
				new ArrayList<Integer>(Arrays.asList(3, 5, 6)), new ArrayList<Integer>(Arrays.asList(1, 4, 7)),
				new ArrayList<Integer>(Arrays.asList(2, 3, 6)), new ArrayList<Integer>(Arrays.asList(1, 4)),
				new ArrayList<Integer>(Arrays.asList(2, 7)), new ArrayList<Integer>(Arrays.asList(4, 5))));
		
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
