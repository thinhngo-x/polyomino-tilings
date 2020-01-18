package polyomino;
import java.util.*;


public class ExactCover {
	public static Set<Set<Set<Integer>>> solve(Set<Integer> X, Set< Set<Integer> > C) {
		Set<Set< Set<Integer> > > result = new HashSet<>();
		if(X.isEmpty()) {
			result.add(new HashSet<>());
			return(result);
		}
			
		Set<Integer> X_ = new HashSet<>(X);
		Set< Set<Integer> > C_ = new HashSet<>(C);
		
		Integer x = X_.iterator().next();
		for(Set<Integer> S: C) {
			if(!S.contains(x)) continue;
			for(Integer y: S) {
				X_.remove(y);
				for(Set<Integer> otherS: C) {
					if(otherS.contains(y))
						C_.remove(otherS);
				}
			}
			
			for(Set<Set<Integer>> P: solve(X_, C_)) {
				P.add(S);
				result.add(P);
			}
		}
		
		return result;
	}
	
	public static void main(String[] args) {
		Set<Integer> X = new HashSet<>();
		for(int i=1; i<=7; i++)
			X.add(i);
		Set< Set<Integer> > C = new HashSet<>();
		C.add(new HashSet<Integer>(Arrays.asList(3,5,6)));
		C.add(new HashSet<Integer>(Arrays.asList(1,4,7)));
		C.add(new HashSet<Integer>(Arrays.asList(2,3,6)));
		C.add(new HashSet<Integer>(Arrays.asList(1,4)));
		C.add(new HashSet<Integer>(Arrays.asList(2,7)));
		C.add(new HashSet<Integer>(Arrays.asList(4,5,7)));
		long start = System.currentTimeMillis();
		Set<Set<Set<Integer>>> exactCover = ExactCover.solve(X, C);
		long end = System.currentTimeMillis();
		System.out.println(end-start);
		for(Set<Set<Integer>> P: exactCover) {
			for(Set<Integer> S: P) {
				for(Integer x: S)
					System.out.print(x + " ");
				System.out.print("\n");
			}
			System.out.println("\n");
		}
	}
}
