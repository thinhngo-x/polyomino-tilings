package polyomino;

import java.util.*;

import polyomino.Enumeration;

public class ExactCover {

	public static <E> Set<Set<Set<E>>> solve(Set<E> X, Set<Set<E>> C) {
		Set<Set<Set<E>>> result = new HashSet<>();
		if (X.isEmpty()) {
			result.add(new HashSet<>());
			return (result);
		}

		E x = X.iterator().next();

		for (Set<E> S : C) {

			Set<E> X_ = new HashSet<>(X);
			Set<Set<E>> C_ = new HashSet<>(C);

			if (!S.contains(x))
				continue;
			for (E y : S) {
				X_.remove(y);
				for (Set<E> otherS : C) {
					if (otherS.contains(y))
						C_.remove(otherS);
				}
			}

			for (Set<Set<E>> P : solve(X_, C_)) {
				P.add(S);
				result.add(P);
			}
		}

		return result;
	}

	public static <E> E repWithMinNumElement(Set<E> X, Set<Set<E>> C) {
		int min = Integer.MAX_VALUE;
		for (Set<E> S : C) {
			if (S.size() < min)
				min = S.size();
		}
		for (Set<E> S : C) {
			if (S.size() == min)
				return S.iterator().next();
		}
		return null;
	}

	public static <E> Set<Set<Set<E>>> fastersolve(Set<E> X, Set<Set<E>> C) {
		Set<Set<Set<E>>> result = new HashSet<>();
		if (X.isEmpty()) {
			result.add(new HashSet<>());
			return (result);
		}

		E x = repWithMinNumElement(X, C);

		for (Set<E> S : C) {

			Set<E> X_ = new HashSet<>(X);
			Set<Set<E>> C_ = new HashSet<>(C);

			if (!S.contains(x))
				continue;
			for (E y : S) {
				X_.remove(y);
				for (Set<E> otherS : C) {
					if (otherS.contains(y))
						C_.remove(otherS);
				}
			}

			for (Set<Set<E>> P : solve(X_, C_)) {
				P.add(S);
				result.add(P);
			}
		}

		return result;
	}

	public static Set<ListOfPolyominoes> tilingsByFixedPoly(Polyomino P, int n) {
		Set<Polyomino> fixed = Enumeration.genFixedPolyominoes(n);
		Set<Set<String>> C = new HashSet<>();
		for (Polyomino p : fixed) {
			for (Point point : P.toSet()) {
				Polyomino newp = p.translation(point.getX(), point.getY());
				if (newp.isCoveredIn(P))
					C.add(newp.toSetOfStrings());
			}
		}

		Set<String> X = P.toSetOfStrings();
		DancingLinks dl = new DancingLinks(X, C);

		Set<ListOfPolyominoes> result = new HashSet<>();
		for (Set<Node> solution : dl.exactCover()) {
			Set<Polyomino> list = new HashSet<>();
			for (Node h : solution) {
				String p = "";
				p += h.C.N;
				for (Node x = h.R; x != h; x = x.R)
					p += x.C.N;
				list.add(new Polyomino(p));
			}
			result.add(new ListOfPolyominoes(list));
		}

		return result;
	}

	public static Set<ListOfPolyominoes> tilingsByFixedPolyNoRep(Polyomino P, int n) {
		Set<Polyomino> fixed = Enumeration.genFixedPolyominoes(n);
		Set<Set<String>> C = new HashSet<>();
		Set<String> X = P.toSetOfStrings();
		for (Polyomino p : fixed) {
			for (Point point : P.toSet()) {
				Polyomino newp = p.translation(point.getX(), point.getY());
				if (newp.isCoveredIn(P)) {
					C.add(newp.toSetOfStrings(newp.translateToOrigin().toString()));
				}
			}
		}

		DancingLinks dl = new DancingLinks(X, C);

		Set<ListOfPolyominoes> result = new HashSet<>();
		for (Set<Node> solution : dl.exactCover()) {
			Set<Polyomino> list = new HashSet<>();
			for (Node h : solution) {
				String p = "";
				if (h.C.N.length() == 4)
					p += h.C.N;
				for (Node x = h.R; x != h; x = x.R) {
					if (x.C.N.length() == 4)
						p += x.C.N;
				}
				list.add(new Polyomino(p));
			}
			result.add(new ListOfPolyominoes(list));
		}

		return result;
	}

	public static Set<ListOfPolyominoes> tilingsByFreePolyNoRep(Polyomino P, int n) {
		Set<Polyomino> free = Enumeration.genFreePolyominoes(n);
		Set<Set<String>> C = new HashSet<>();
		Set<String> X = P.toSetOfStrings();
		for (Polyomino p : free) {
			for (Point point : P.toSet()) {
				int x = point.getX();
				int y = point.getY();
				Polyomino newp = p.translation(x, y);
				if (newp.rotation().translation(x, y).isCoveredIn(P)) {
					C.add(newp.rotation().translation(x, y).toSetOfStrings(newp.translateToOrigin().toString()));
				}
				if (newp.rotation().rotation().translation(x, y).isCoveredIn(P)) {
					C.add(newp.rotation().rotation().translation(x, y)
							.toSetOfStrings(newp.translateToOrigin().toString()));
				}
				if (newp.rotation().rotation().rotation().translation(x, y).isCoveredIn(P)) {
					C.add(newp.rotation().rotation().rotation().translation(x, y)
							.toSetOfStrings(newp.translateToOrigin().toString()));
				}
				if (newp.reflection("H").translation(x, y).isCoveredIn(P)) {
					C.add(newp.reflection("H").translation(x, y).toSetOfStrings(newp.translateToOrigin().toString()));
				}
				if (newp.reflection("D").translation(x, y).isCoveredIn(P)) {
					C.add(newp.reflection("D").translation(x, y).toSetOfStrings(newp.translateToOrigin().toString()));
				}
				if (newp.reflection("A").translation(x, y).isCoveredIn(P)) {
					C.add(newp.reflection("A").translation(x, y).toSetOfStrings(newp.translateToOrigin().toString()));
				}
				if (newp.reflection("V").translation(x, y).isCoveredIn(P)) {
					C.add(newp.reflection("V").translation(x, y).toSetOfStrings(newp.translateToOrigin().toString()));
				}
				if (newp.isCoveredIn(P)) {

					C.add(newp.toSetOfStrings(newp.translateToOrigin().toString()));
				}
			}
		}

		DancingLinks dl = new DancingLinks(X, C);

		Set<ListOfPolyominoes> result = new HashSet<>();
		for (Set<Node> solution : dl.exactCover()) {
			Set<Polyomino> list = new HashSet<>();
			for (Node h : solution) {
				String p = "";
				if (Point.isPoint(h.C.N))
					p += h.C.N;
				for (Node x = h.R; x != h; x = x.R) {
					if (Point.isPoint(x.C.N))
						p += x.C.N;
				}
				list.add(new Polyomino(p));
			}
			result.add(new ListOfPolyominoes(list));
		}

		return result;
	}

	public static Map<Polyomino, Set<ListOfPolyominoes>> tilingOfDilate(int n, int k) {
		Set<Polyomino> free = Enumeration.genFreePolyominoes(n);
		Map<Polyomino, Set<ListOfPolyominoes>> map = new HashMap<>();

		for (Polyomino p : free) {
			Polyomino P = p.dilation(k);
			Set<String> X = P.toSetOfStrings();
			Set<Set<String>> C = new HashSet<>();
			for (Point point : P.toSet()) {
				int x = point.getX();
				int y = point.getY();
				Polyomino newp = p.translation(x, y);
				if (newp.rotation().translation(x, y).isCoveredIn(P)) {
					C.add(newp.rotation().translation(x, y).toSetOfStrings());
				}
				if (newp.rotation().rotation().translation(x, y).isCoveredIn(P)) {
					C.add(newp.rotation().rotation().translation(x, y).toSetOfStrings());
				}
				if (newp.rotation().rotation().rotation().translation(x, y).isCoveredIn(P)) {
					C.add(newp.rotation().rotation().rotation().translation(x, y).toSetOfStrings());
				}
				if (newp.reflection("H").translation(x, y).isCoveredIn(P)) {
					C.add(newp.reflection("H").translation(x, y).toSetOfStrings());
				}
				/*
				 * if(newp.reflection("D").translation(x, y).isCoveredIn(P)) {
				 * C.add(newp.reflection("D").translation(x, y). toSetOfStrings()); }
				 * if(newp.reflection("A").translation(x, y).isCoveredIn(P)) {
				 * C.add(newp.reflection("A").translation(x, y). toSetOfStrings()); }
				 */
				if (newp.reflection("V").translation(x, y).isCoveredIn(P)) {
					C.add(newp.reflection("V").translation(x, y).toSetOfStrings());
				}
				if (newp.isCoveredIn(P)) {
					C.add(newp.toSetOfStrings());
				}
			}

			DancingLinks dl = new DancingLinks(X, C);

			Set<ListOfPolyominoes> solutions = new HashSet<>();
			for (Set<Node> solution : dl.exactCover()) {
				Set<Polyomino> list = new HashSet<>();
				for (Node h : solution) {
					String s = "";
					if (Point.isPoint(h.C.N))
						s += h.C.N;
					for (Node x = h.R; x != h; x = x.R) {
						if (Point.isPoint(x.C.N))
							s += x.C.N;
					}
					list.add(new Polyomino(s));
				}
				solutions.add(new ListOfPolyominoes(list));
			}
			if (!solutions.isEmpty())
				map.put(P, solutions);
		}

		return map;
	}

}
