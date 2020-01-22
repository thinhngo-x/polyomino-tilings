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
	
	public static <E> Set<Set<Set<E>>> solveByDancingLinks(Set<E> X, Set<Set<E>> C){
		DancingLinks dancingLinks = new DancingLinks(X, C);
		Set<Set<DataObject>> P = solveByDancingLinks(dancingLinks);
		Set<Set<Set<E>>> result = new HashSet<>();
		for(Set<DataObject> p: P) {
			Set<Set<E>> solution = new HashSet<>();
			for(DataObject head: p) {
				Set<E> subset = new HashSet<>();
				DataObject iteratorRow = head;
				do {
					subset.add(dancingLinks.getObject(iteratorRow.C.N));
					iteratorRow = iteratorRow.R;
				}while(iteratorRow != head);
				solution.add(subset);
			}
			result.add(solution);
		}
		return result;
			
	}
	
	public static Set<Set<DataObject>> solveByDancingLinks(DancingLinks H){
		Set<Set<DataObject>> result = new HashSet<>();
		if(H.head.R == H.head) {
			result.add(new HashSet<DataObject>());
			return result;
		}
		
		ColumnObject x = (ColumnObject) H.head.R;
		ColumnObject iterator = (ColumnObject) H.head.R;
		int minS = iterator.S;
		while(iterator != H.head) {
			if(minS > iterator.S) {
				x = iterator;
				minS = x.S;
			}
			iterator = (ColumnObject) iterator.R;
		}
		//Finish choosing x with minimal size
		H.coverColumn(x);
		DataObject t = x.U;
		while(t != x) {
			DataObject y = t.L;
			while(y != t) {
				H.coverColumn(y.C);
				y = y.L;
			}
			
			for(Set<DataObject> P: solveByDancingLinks(H)) {
				P.add(t);
				result.add(P);
			}
			
			y = t.R;
			while(y != t) {
				H.uncoverColumn(y.C);
				y = y.R;
			}
			
			t = t.U;
		}
		H.uncoverColumn(x);
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
		/*
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
		*/
		
/*		DancingLinks dl = new DancingLinks(X,C);
		ColumnObject x = (ColumnObject) dl.head.R.R;
		dl.coverColumn(x);
		dl.uncoverColumn(x);
		ColumnObject column = (ColumnObject) dl.head.R;
		while(column != dl.head) {
			System.out.println(column.S);
			column = (ColumnObject) column.R;
		}
*/
		Set<Set<Set<Integer>>> exactCover = solveByDancingLinks(X,C);
		for(Set<Set<Integer>> P: exactCover) {
			for(Set<Integer> S: P) {
				for(Integer y: S)
					System.out.print(y + " ");
				System.out.print("\n");
			}
			System.out.println("\n");
		}
	}
}
