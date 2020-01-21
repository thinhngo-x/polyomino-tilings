package polyomino;

public class DataObject {
	DataObject U,D,R,L;
	ColumnObject C;
	
	<E> DataObject(){
		this.U = this.D = this.R = this.L = null;
		this.C = null;
	}
}
