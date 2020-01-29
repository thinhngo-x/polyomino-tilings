package polyomino;

import java.util.*;
class Node {

	Node L, R, U, D, C;
	int S;
	String N;

	/* Create an empty node right below a ColumnNode in a column */
	Node(Node C) {
		if (C.IsColumnNode()) {
			this.C = C;
			C.S++;
			this.D = C.D;
			this.U = C;
			C.D = this;
			this.D.U = this;
		} else {
			throw new IllegalArgumentException("Argument is not a column Node!");
		}
	}

	/* Create an empty column */
	Node(String name) {
		this.L = this;
		this.R = this;
		this.U = this;
		this.D = this;
		this.C = this;
		this.S = 0;
		this.N = name;
	}

	public boolean IsColumnNode() {
		return this.N != null;
	}

	public boolean IsDataNode() {
		return this.N == null;
	}

	public static void ConnectLR(LinkedList<Node> l) {
		Node current = l.poll();
		Node running = current;
		for (Node n : l) {
			running.R = n;
			n.L = running;
			running = n;
		}
		running.R = current;
		current.L = running;
	}

	public static void PrintLR(Node n) {
		System.out.print(n.C.N);
		for (Node x = n.R; x != n; x = x.R)
			System.out.print(" " + x.C.N);
		System.out.println();
	}

	public static void PrintAllCombination(Node root) {
		PrintLR(root);
		for (Node y = root.R; y != root; y = y.R) {
			for (Node x = y.D; x != y; x = x.D) {
				PrintLR(x);
			}
		}
	}

	public static void RemoveThisLR(Node n) {
		n.L.R = n.R;
		n.R.L = n.L;
	}

	public static void RecoverThisLR(Node n) {
		n.R.L = n;
		n.L.R = n;
	}

	public static void RemoveThisUD(Node n) {
		n.U.D = n.D;
		n.D.U = n.U;
	}

	public static void RecoverThisUD(Node n) {
		n.D.U = n;
		n.U.D = n;
	}
}

public class DancingLinks {

	Node root;

	@SuppressWarnings("unchecked")
	public DancingLinks(LinkedList<Node> columns) {
		this.root = new Node("H");
		LinkedList<Node> c = (LinkedList<Node>) columns.clone();
		c.add(this.root);
		Node.ConnectLR(c);
	}

	public void AddDataRow(LinkedList<Node> row) {
		LinkedList<Node> nodes = new LinkedList<Node>();
		for (Node c : row) {
			nodes.add(new Node(c));
		}
		Node.ConnectLR(nodes);
	}

	public void CoverColumn(Node x) {
		Node.RemoveThisLR(x.C);
		for (Node t = x.C.D; t != x.C; t = t.D) {
			for (Node y = t.R; y != t; y = y.R) {
				Node.RemoveThisUD(y);
				y.C.S--;
			}
		}
	}

	public void UnCoverColumn(Node x) {
		Node.RecoverThisLR(x.C);
		for (Node t = x.C.U; t != x.C; t = t.U) {
			for (Node y = t.L; y != t; y = y.L) {
				Node.RecoverThisUD(y);
				y.C.S++;
			}
		}
	}

	public Node MinColumn() {
		int size = Integer.MAX_VALUE;
		Node best = this.root.R;
		for (Node j = this.root.R; j != this.root; j = j.R) {
			if (j.S < size) {
				size = j.S;
				best = j;
			}
		}
		return best;
	}

	public Set<Set<Node>> ExactCover() {
		Set<Set<Node>> P = new HashSet<>();
		if (this.root.R == this.root) {
			P.add(new HashSet<Node>());
			return P;
		}
		Node x = this.MinColumn();
		this.CoverColumn(x);
		for (Node t = x.U; t != x; t = t.U) {
			for (Node y = t.L; y != t; y = y.L) {
				this.CoverColumn(y);
			}
			for (Set<Node> sol : this.ExactCover()) {
				sol.add(t);
				P.add(sol);
			}
			for (Node y = t.R; y != t; y = y.R) {
				this.UnCoverColumn(y);
			}
		}
		this.UnCoverColumn(x);
		return P;
	}

	public static Set<int[]> Generator(int n, int k) {
		Set<int[]> set = new HashSet<int[]>();
		int [] array = new int [k];
		DancingLinks.helper(set, array, 1, n, 0);
		return set;
	}

	public static void helper(Set<int[]> set, int [] array, int start, int end, int index) {
		if (array.length == index) {
			set.add(array.clone());
		} else if (start <= end) {
			array[index] = start;
			DancingLinks.helper(set, array, start+1, end, index+1);
			DancingLinks.helper(set, array, start+1, end, index);
		}
	}

	public static void main(String[] args) {

		Node A = new Node("A");
		Node B = new Node("B");
		Node C = new Node("C");
		Node D = new Node("D");
		Node E = new Node("E");
		Node F = new Node("F");
		Node G = new Node("G");
		LinkedList<Node> l = new LinkedList<Node>();
		l.addAll(Arrays.asList(A, B, C, D, E, F, G));
		DancingLinks DL = new DancingLinks(l);
		DL.AddDataRow((new LinkedList<Node>(Arrays.asList(C, E, F))));
		DL.AddDataRow((new LinkedList<Node>(Arrays.asList(A, D, G))));
		DL.AddDataRow((new LinkedList<Node>(Arrays.asList(B, C, F))));
		DL.AddDataRow((new LinkedList<Node>(Arrays.asList(A, D))));
		DL.AddDataRow((new LinkedList<Node>(Arrays.asList(B, G))));
		DL.AddDataRow((new LinkedList<Node>(Arrays.asList(D, E, G))));
		Set<Set<Node>> sol = DL.ExactCover();
		for (Set<Node> h : sol) {
			for (Node o : h) {
				Node.PrintLR(o);
			}
		}

		int n = 6, k = 3;
		LinkedList<Node> column = new LinkedList<Node>();
		for (int i = 1; i <= n; i++) {
			column.add(new Node(Integer.toString(i)));
		}
		DancingLinks DLSubsetCase = new DancingLinks(column);
		Set<int[]> set = DancingLinks.Generator(n, k);
		for (int[] array : set) {
			LinkedList<Node> row = new LinkedList<Node>();
			for (int i : array) {
				if (i != 0) {
					row.add(column.get(i-1));
				}
			}
			DLSubsetCase.AddDataRow(row);
		}
		Set<Set<Node>> AllPossibleSol = DLSubsetCase.ExactCover();
		System.out.println("Total number of solutions:");
		System.out.println(AllPossibleSol.size());
		System.out.println();
		for (Set<Node> h : AllPossibleSol) {
			for (Node o : h) {
				Node.PrintLR(o);
			}
			System.out.println();
		}
		
		
		
	}

}
