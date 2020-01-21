package polyomino;

public class ColumnObject extends DataObject {
	int S;
	int N;
	
	ColumnObject(){
		this.S = 0;
		this.N = -1;
		this.U = this.D = this.L = this.R = null;
		
	}
	
	<E> ColumnObject(E object){
		this.S = 0;
		this.N = object.hashCode();
		this.U = this.D = this.L = this.R = null;
	}
}
