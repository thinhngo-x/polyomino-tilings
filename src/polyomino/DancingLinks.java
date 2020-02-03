package polyomino;

import java.util.*;

public class DancingLinks {

	Node root;

	@SuppressWarnings("unchecked")
	public DancingLinks(LinkedList<Node> columns) {
		this.root = new Node("H");
		LinkedList<Node> c = (LinkedList<Node>) columns.clone();
		c.add(this.root);
		Node.ConnectLR(c);
	}

	@SuppressWarnings("unchecked")
	public DancingLinks(LinkedList<Node> X, HashSet<LinkedList<Node>> C) {
		this.root = new Node("H");
		LinkedList<Node> columnobject = (LinkedList<Node>) X.clone();
		columnobject.add(this.root);
		Node.ConnectLR(columnobject);
		for (LinkedList<Node> row : C) {
			this.AddDataRow(row);
		}
	}

	@SuppressWarnings("unchecked")
	public DancingLinks(Set<String> X, Set<Set<String>> C) {
		LinkedList<Node> X_ = new LinkedList<>();
		LinkedList<Node> Xname = new LinkedList<>();
		Set<LinkedList<Node>> C_ = new HashSet<>();
		Map<String, Node> map = new HashMap<>();
		for (String x : X) {
			Node temp = new Node(x);
			X_.add(temp);
			map.put(x, temp);
		}
		for (Set<String> c : C) {
			LinkedList<Node> c_ = new LinkedList<>();
			for (String cs : c) {
				if (!map.containsKey(cs)) {
					Node temp = new Node(cs);
					Xname.add(temp);
					map.put(cs, temp);
				}
				c_.add(map.get(cs));
			}
			C_.add(c_);
		}
		this.root = new Node("H");
		LinkedList<Node> columnobject = (LinkedList<Node>) X_.clone();
		columnobject.add(this.root);
		Node.ConnectLR(columnobject);
		for (LinkedList<Node> row : C_) {
			this.AddDataRow(row);
		}

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

	public HashSet<HashSet<Node>> exactCover() {
		HashSet<HashSet<Node>> P = new HashSet<HashSet<Node>>();
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
			for (HashSet<Node> sol : this.exactCover()) {
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

	public static HashSet<int[]> Generator(int n, int k) {
		HashSet<int[]> set = new HashSet<int[]>();
		int[] array = new int[k];
		DancingLinks.helper(set, array, 1, n, 0);
		return set;
	}

	public static void helper(HashSet<int[]> set, int[] array, int start, int end, int index) {
		if (array.length == index) {
			set.add(array.clone());
		} else if (start <= end) {
			array[index] = start;
			DancingLinks.helper(set, array, start + 1, end, index + 1);
			DancingLinks.helper(set, array, start + 1, end, index);
		}
	}
}
