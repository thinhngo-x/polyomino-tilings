package polyomino;
import java.util.*;

public class Enumeration {
	public static ListOfPolyominoes genFixedPolyominoes(int p){
		StackOfPoint2D untried = new StackOfPoint2D();
		untried.push(1, p-1); //[(0,0)]
		ListOfPolyominoes fixed = new ListOfPolyominoes();
		boolean[][] coords = new boolean[p][2*p-1];
		boolean[][] occupied = new boolean[p+1][2*p-1];
		for(int y = -p+1; y<p; y++) {
			occupied[-1 + 1][y + p - 1] = true;
		}
		for(int y = -p+1; y<0; y++) {
			occupied[0 + 1][y + p - 1] = true;
		}
		int size = 0;
		genFixedPolyominoes(p, size, occupied, untried, coords, fixed);
		return(fixed);
	}
	
	public static void genFixedPolyominoes(int p, int size, boolean[][] occupied_old,
			StackOfPoint2D untried_old,
			boolean[][] coords, ListOfPolyominoes fixed) {
		StackOfPoint2D untried = new StackOfPoint2D(untried_old);
		boolean[][] occupied = occupied_old.clone();
		while(!untried.empty()) {
			Integer x = untried.x.pop();
			Integer y = untried.y.pop();
//			if(occupied[x][y]) return;
			occupied[x][y] = true;
			coords[x-1][y] = true;
			size++;
			if(size < p) {
				int countNew = 0;
				if(!occupied[x][y-1]) {
					untried.push(x, y-1);
					occupied[x][y-1] = true;
					countNew++;
				}
				
				if(!occupied[x][y+1]) {
					untried.push(x, y+1);
					occupied[x][y+1] = true;
					countNew++;
				}
				
				if(!occupied[x-1][y]) {
					untried.push(x-1, y);
					occupied[x-1][y] = true;
					countNew++;
				}
				
				if(!occupied[x+1][y]) {
					untried.push(x+1, y);
					occupied[x+1][y] = true;
					countNew++;
				}
				genFixedPolyominoes(p, size, occupied, untried, coords, fixed);
				while(countNew-- != 0) 
					occupied[untried.x.pop()][untried.y.pop()] = false;
			}
			else {
				fixed.add(new Polyomino(coords, -p+1));
			}
//			occupied[x][y] = false;
			coords[x-1][y] = false;
			size--;
		}
	}
	
	public static int fixedPolyominoes(int p) {
		boolean[][] occupied = new boolean[p+1][2*p-1];
		StackOfPoint2D untried = new StackOfPoint2D();
		untried.push(1, p-1);
		for(int y = -p+1; y<p; y++) {
			occupied[-1 + 1][y + p - 1] = true;
		}
		for(int y = -p+1; y<0; y++) {
			occupied[0 + 1][y + p - 1] = true;
		}
		int size = 0;
		return(fixedPolyominoes(p, size, occupied, untried));
	}
	
	public static int fixedPolyominoes(int p, int size, boolean[][] occupied_old,
			StackOfPoint2D untried_old) {
		StackOfPoint2D untried = new StackOfPoint2D(untried_old);
		boolean[][] occupied = occupied_old.clone();
		int count = 0;
		while(!untried.empty()) {
			Integer x = untried.x.pop();
			Integer y = untried.y.pop();
			occupied[x][y] = true;
			size++;
			if(size < p) {
				int countNew = 0;
				if(!occupied[x][y-1]) {
					untried.push(x, y-1);
					occupied[x][y-1] = true;
					countNew++;
				}
				
				if(!occupied[x][y+1]) {
					untried.push(x, y+1);
					occupied[x][y+1] = true;
					countNew++;
				}
				
				if(!occupied[x-1][y]) {
					untried.push(x-1, y);
					occupied[x-1][y] = true;
					countNew++;
				}
				
				if(!occupied[x+1][y]) {
					untried.push(x+1, y);
					occupied[x+1][y] = true;
					countNew++;
				}
				count += fixedPolyominoes(p, size, occupied, untried);
				while(countNew-- != 0) 
					occupied[untried.x.pop()][untried.y.pop()] = false;
			}
			else {
				count++;
			}
			size--;
		}
		return count;
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


