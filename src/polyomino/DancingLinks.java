package polyomino;
import java.util.*;

//The idea is to contain DataObject and ColumnObject in a data structure called DancingLinks
//One trouble is to initialize a DataObject which points to itself. We cannot do it in a construct function
//so I initialize an DataObject pointing to null first then, after initializing, I modify the links so that
//it points to itself.
public class DancingLinks {
	HashMap<Integer, ColumnObject> columns;
	ColumnObject head;
	
	
	<E> DancingLinks(Set<E> X, Set<Set<E>> C){
		columns = new HashMap<>();
		head = new ColumnObject();
		head.U = head.D = head.L = head.R = head;
		for(E x: X) {
			ColumnObject column = new ColumnObject(x);
			column.U = column.D = column.L = column.R = column;
			add(column);
		}
		//finish adding columns
		for(Set<E> subset: C) {
			add(subset);
		}
	}
	
	public void add(ColumnObject column){
		this.columns.put(column.N, column);
		addToRow(column, head);
	}
	
	public <E> void add(Set<E> subset){
		//Create a sub head as a head of the new row, then add elements to this row through head
		//Finally, remove the sub head
		
		DataObject subHead = new DataObject();
		addToColumn(subHead, head);
		subHead.R = subHead.L = subHead;
		for(E node: subset) {
			int hash = node.hashCode();
			DataObject d = new DataObject();
			ColumnObject column = columns.get(hash);
			addToColumn(d, column);
			addToRow(d, subHead);
		}
		remove(subHead);
	}
	
	public void addToColumn(DataObject node, ColumnObject column) {
		node.U = column.U;
		node.D = column;
		column.U.D = node;
		column.U = node;
		column.S ++;
	}
	
	public void addToRow(DataObject node, DataObject head) {
		node.L = head.L;
		node.R = head;
		head.L.R = node;
		head.L = node;
	}
	
	public void remove(DataObject node) {
		node.U.D = node.D;
		node.D.U = node.U;
		node.L.R = node.R;
		node.R.L = node.L;
		node.U = node.D = node.L = node.R = null;
	}
	
}