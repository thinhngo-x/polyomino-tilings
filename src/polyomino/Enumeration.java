package polyomino;
import java.util.*;

public class Enumeration {
	public static ListOfPolyominoes genFreePolyominoes(int p){
		StackOfPoint2D untried = new StackOfPoint2D();
		untried.push(1, p-1); //[(0,0)]
		ListOfPolyominoes free = new ListOfPolyominoes();
		boolean[][] coords = new boolean[p][2*p-1];
		boolean[][] occupied = new boolean[p+1][2*p-1];
		for(int y = -p+1; y<p; y++) {
			occupied[-1 + 1][y + p - 1] = true;
		}
		for(int y = -p+1; y<0; y++) {
			occupied[0 + 1][y + p - 1] = true;
		}
		int size = 0;
		genFreePolyominoes(p, size, coords, untried, free, occupied);
		return(free);
	}
	
	public static void genFreePolyominoes(int p, int size, boolean[][] coords,
			StackOfPoint2D untried_old, ListOfPolyominoes free,
			boolean[][] occupied) {
		StackOfPoint2D untried = new StackOfPoint2D(untried_old);
		Integer x = untried.x.pop();
		Integer y = untried.y.pop();
		occupied[x][y] = true;
		coords[x-1][y] = true;
		size++;
		if(size < p) {
			StackOfPoint2D newNeighbors = new StackOfPoint2D();
			
			if(!occupied[x][y-1]) {
				newNeighbors.push(x, y-1);
				untried.push(x, y-1);
			}
			
			if(!occupied[x][y+1]) {
				newNeighbors.push(x, y+1);
				untried.push(x, y+1);
			}
			
			if(!occupied[x-1][y]) {
				newNeighbors.push(x-1, y);
				untried.push(x-1, y);
			}
			
			if(!occupied[x+1][y]) {
				newNeighbors.push(x+1, y);
				untried.push(x+1, y);
			}
			
			while(!untried.empty()) {
				genFreePolyominoes(p, size, coords, untried, free, occupied);
				untried.x.pop();
				untried.y.pop();
			}
			
			while(!newNeighbors.empty())
				occupied[newNeighbors.x.pop()][newNeighbors.y.pop()] = false;
			
		}
		else {
			free.add(new Polyomino(coords, -p+1));
		}
		
		coords[x-1][y] = false;
	}
}

@SuppressWarnings("unchecked")
class StackOfPoint2D {
	public Stack<Integer> x;
	public Stack<Integer> y;
	
	public StackOfPoint2D(StackOfPoint2D old) {
		this.x = (Stack<Integer>)old.x.clone();
		this.y = (Stack<Integer>)old.y.clone();
		
	}
	
	public StackOfPoint2D() {
		this.x = new Stack<>();
		this.y = new Stack<>();
	}
	
	public boolean empty() {
		return this.x.empty();
	}
	
	public void push(Integer x, Integer y) {
		this.x.push(x);
		this.y.push(y);
	}
}



