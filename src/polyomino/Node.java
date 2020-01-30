package polyomino;

import java.util.LinkedList;

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
