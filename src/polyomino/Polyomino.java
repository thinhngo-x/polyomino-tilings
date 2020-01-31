package polyomino;
import java.awt.Color;
import java.util.*;


public class Polyomino {
	private ArrayList<Integer> xcoords;
	private ArrayList<Integer> ycoords;
	
	public Polyomino(ArrayList<Integer> xcoords, ArrayList<Integer> ycoords) {
		this.xcoords = new ArrayList<Integer>(xcoords);
		this.ycoords = new ArrayList<Integer>(ycoords);
	}
	
	public Polyomino(boolean[][] coords, int minY) {
		this.xcoords = new ArrayList<Integer>();
		this.ycoords = new ArrayList<Integer>();
		for(int x=0; x<coords.length; x++)
			for(int y=0; y<coords[0].length; y++)
				if(coords[x][y]) {
					this.xcoords.add(Integer.valueOf(x));
					this.ycoords.add(Integer.valueOf(y+minY));
				}
	}
	
	public Polyomino(String s) {
		//Example: s = [(0,0), (0,4), (1,0)]
		String[] splits = s.replaceAll("[\\[()\\]]","").replace(","," ").split(" ");
		this.xcoords = new ArrayList<Integer>();
		this.ycoords = new ArrayList<Integer>();
		for(int i=0; i<splits.length; i++) {
			if(i%2 == 0) this.xcoords.add(Integer.valueOf(splits[i]));
			else this.ycoords.add(Integer.valueOf(splits[i]));
		}
	}
	
	public int getWidth() {
		int start_x = Integer.MAX_VALUE;
		int end_x = Integer.MIN_VALUE;
		for(Integer x: xcoords) {
			start_x = Math.min(start_x, x);
			end_x = Math.max(end_x, x);
		}
		return(end_x-start_x+1);
	}
	
	public int getHeight() {
		int start_y = Integer.MAX_VALUE;
		int end_y = Integer.MIN_VALUE;
		for(Integer y: ycoords) {
			start_y = Math.min(start_y, y);
			end_y = Math.max(end_y, y);
		}
		return(end_y-start_y+1);
	}
	
	public Integer getMinY() {
		int minY = Integer.MAX_VALUE;
		for(Integer y: ycoords) {
			minY = Math.min(minY, y);
		}
		return(minY);
	}
	
	public Integer getMinX() {
		int minX = Integer.MAX_VALUE;
		for(Integer x: xcoords) {
			minX = Math.min(minX, x);
		}
		return(minX);
	}
	
	public Polyomino translateToOrigin() {
		Integer x = getMinX();
		Integer y = Integer.MAX_VALUE;
		for(int i=0; i<xcoords.size(); i++) {
			if(xcoords.get(i) == x)
				y = Math.min(ycoords.get(i), y);
		}
		return(this.translation(-x, -y));
	}
	
	public Polyomino translation(int delta_x, int delta_y) {
		ArrayList<Integer> new_ycoords = new ArrayList<>();
		ArrayList<Integer> new_xcoords = new ArrayList<>();
		for(Integer x: xcoords) {
			new_xcoords.add(x+delta_x);
		}
		for(Integer y: ycoords) {
			new_ycoords.add(y+delta_y);
		}
		return(new Polyomino(new_xcoords, new_ycoords));
	}
	
	public Polyomino dilation(int rate) {
		ArrayList<Integer> new_ycoords = new ArrayList<>();
		ArrayList<Integer> new_xcoords = new ArrayList<>();
		for(Integer x: xcoords) {
			Integer i = x*rate;
			while(i <= (x+1)*rate-1) {
				int k = 1;
				while(k++ <= rate) new_xcoords.add(i);
				i++;
			}
		}
		
		for(Integer y: ycoords) {
			int k = 1;
			while(k++ <= rate) {
				Integer i = y*rate;
				while(i <= (y+1)*rate-1) 
					new_ycoords.add(i++);
			}
		}
		return(new Polyomino(new_xcoords, new_ycoords));
	}
	
	public Polyomino reflection(String axis) {
		if(axis == "H") {
			ArrayList<Integer> new_xcoords = new ArrayList<>();
			for(Integer x: xcoords) {
				new_xcoords.add(-x);
			}
			return(new Polyomino(new_xcoords, ycoords));
		}
		else if(axis == "V") {
			ArrayList<Integer> new_ycoords = new ArrayList<>();
			for(Integer y: ycoords) {
				new_ycoords.add(-y);
			}
			return(new Polyomino(xcoords, new_ycoords));
		}
		else if(axis == "A") {
			return(new Polyomino(ycoords, xcoords));
		}
		else if(axis == "D") {
			return(reflection("H").reflection("A").reflection("H"));
		}
		else return null;
	}
	
	//left quarter-turn
	public Polyomino rotation() {
		Polyomino p = new Polyomino(this.ycoords, this.xcoords);
		p = p.reflection("H");
		return(p);
	}
	
	public void drawUnitSquare(int unit, int px, int py, int x, int y, Image2d img, Color color) {
		int left = px + x*unit;
		int right = px + (x+1)*unit;
		int down = py - (y+1)*unit;
		int up = py - y*unit;
		int[] xcoords = {left, right, right, left};
		int[] ycoords = {up, up, down, down};
		img.addPolygon(xcoords, ycoords, color);
	}
	
	public void draw(int unit, int px, int py, Image2d img, Color color) {
		int len = xcoords.size();
		for(int i=0; i<len; i++) {
			drawUnitSquare(unit, px, py, xcoords.get(i), ycoords.get(i), img, color);
		}
		Set<Point> points = toSet();
		for(Point p: points) {
			int x = p.x;
			int y = p.y;
			if(!points.contains(new Point(x, y+1)))
				img.addEdge(px+x*unit, py-(y+1)*unit, px+(x+1)*unit, py-(y+1)*unit, unit/10);
			if(!points.contains(new Point(x, y-1)))
				img.addEdge(px+x*unit, py-y*unit, px+(x+1)*unit, py-y*unit, unit/10);
			if(!points.contains(new Point(x+1, y)))
				img.addEdge(px+(x+1) *unit, py-(y+1)*unit, px+(x+1)*unit, py-y*unit, unit/10);
			if(!points.contains(new Point(x-1, y)))
				img.addEdge(px+x*unit, py-(y+1)*unit, px+x*unit, py-y*unit, unit/10);
			
		}
	}
	
	public TreeSet<Point> toSet(){
		TreeSet<Point> squares = new TreeSet<>();
		for(int i=0; i<xcoords.size(); i++) {
			squares.add(new Point(xcoords.get(i), ycoords.get(i)));
		}
		return squares;
	}
	@Override
	public String toString() {
		TreeSet<Point> squares = toSet();
		String rs = "";
		for(Point p: squares) {
			rs += p.x;
			rs += " ";
			rs += p.y;
			rs += " ";
		}
		return rs;
	}
	
	@Override
	public int hashCode() {
		return toString().hashCode();
	}
	
	@Override
	public boolean equals(Object o) {
		if(o == null || o.getClass() != getClass())
			return false;
		Polyomino p = (Polyomino) o;
		if(!equalsTo(p))
			return false;
		return true;
	}
	
	public Set<String> toSetOfStrings(){
		Set<String> result = new HashSet<>();
		String space = " ";
		for(int i=0; i<xcoords.size(); i++)
			result.add(xcoords.get(i)+ space + ycoords.get(i) + space);
		return result;
	}
	
	public Set<String> toSetOfStrings(String name){
		Set<String> result = new HashSet<>();
		String space = " ";
		for(int i=0; i<xcoords.size(); i++)
			result.add(xcoords.get(i)+ space + ycoords.get(i)+space);
		result.add(name);
		return result;
	}
	
	public boolean isCoveredBy(Polyomino p) {
		TreeSet<Point> squares = this.toSet();
		TreeSet<Point> squaresOfp = p.toSet();
		for(Point square: squares) {
			if(!squaresOfp.contains(square))
				return false;
		}
		return true;
	}
	
	public boolean equalsTo(Polyomino p) {
		TreeSet<Point> squares = this.toSet();
		TreeSet<Point> squaresOfp = p.toSet();
		for(Point square: squaresOfp) {
			if(!squares.contains(square))
				return false;
		}
		for(Point square: squares) {
			if(!squaresOfp.contains(square))
				return false;
		}
		return true;
	}
	
	public boolean fixedEqualsTo(Polyomino p) {
		TreeSet<Point> squares = this.translateToOrigin().toSet();
		TreeSet<Point> squaresOfp = p.translateToOrigin().toSet();
		for(Point square: squaresOfp) {
			if(!squares.contains(square))
				return false;
		}
		for(Point square: squares) {
			if(!squaresOfp.contains(square))
				return false;
		}
		return true;
	}
	
	public boolean isR() {
		if(this.rotation().rotation().fixedEqualsTo(this))
			return true;
		else return false;
	}
	
	public boolean isR2() {
		if(this.rotation().fixedEqualsTo(this))
			return true;
		else return false;
	}
	
	public boolean isH() {
		if(this.reflection("H").fixedEqualsTo(this))
			return true;
		else return false;
	}
	
	public boolean isV() {
		if(this.reflection("V").fixedEqualsTo(this))
			return true;
		else return false;
	}
	
	public boolean isA() {
		if(this.reflection("A").fixedEqualsTo(this))
			return true;
		else return false;
	}
	
	public boolean isD() {
		if(this.reflection("D").fixedEqualsTo(this))
			return true;
		else return false;
	}
	
	public boolean isHVR() {
		if(isH() && isV())
			return true;
		else return false;
	}
	
	public boolean isADR() {
		if(isA() && isD())
			return true;
		else return false;
	}
	
	public boolean isHVADR2() {
		if(isHVR() && isADR())
			return true;
		else return false;
	}
	
	public static void main(String[] args) {
		/*
		Image2d img = new Image2d(100,200);
		String s = "[(0,0), (0,1), (0,2), (0,3), (0,4), (1,2), (1,3), (2,1), (2,2), (3,0), (3,1), (3,2), (3,3), (3,4)]";
		Polyomino test = new Polyomino(s);
		test.draw(10, 10, 200-10, img, Color.RED);
		new Image2dViewer(img);
		System.out.println(test.getWidth());
		System.out.println(test.getHeight());
		*/
		//
		/*
		File fileName = new File("polyominoesINF421.txt");
		ListOfpolyominoes ps = new ListOfpolyominoes(fileName);
		int unit = 10;
		Image2d img = new Image2d(ps.getWidth()*unit, ps.getHeight()*unit);
		int px = 0;
		int py = ps.getHeight()*unit;
		ps.draw(unit, px, py, img, Color.red);
		new Image2dViewer(img);
		*/
		//
		/*
		Image2d img = new Image2d(100,200);
		String s = "[(0,1), (0,2), (0,3), (0,4), (1,1), (2,0), (2,1), (2,2)]";
		Polyomino test = new Polyomino(s);
		Polyomino test_reflection = test.translateToOrigin();
		test_reflection.draw(10, 50, 90, img, Color.RED);
		test.draw(10,50, 90, img, Color.YELLOW);
		new Image2dViewer(img);
		System.out.println(test.translateToOrigin().toString());
		System.out.println(test_reflection.translateToOrigin().toString());
		System.out.println(test.fixedEqualsTo(test_reflection));
		*/
		/*
		Image2d img = new Image2d(100,200);
		int[][] matrix = {{1,0,0}, {0,1,0}, {0,0,1}};
		Polyomino test = new Polyomino(matrix);
		test.draw(10, 50, 50, img, Color.RED);
		new Image2dViewer(img);
		*/
		/*
		for(int p=15; p<=16; p++) {
			long start = System.currentTimeMillis();
			System.out.println(Enumeration.freePolyominoes(p));
			long end = System.currentTimeMillis();
			System.out.println(end-start);
		}
		*/
		
		Set<Polyomino> list = Enumeration.genFreePolyominoes(11);
		System.out.print(list.size());
		

		/*
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
		for(Set<Set<Integer>> P: ExactCover.solve(X, C)) {
			for(Set<Integer> S: P) {
				for(Integer x: S)
					System.out.print(x + " ");
				System.out.print("\n");
			}
			System.out.println("\n");
		}
		*/
	}
}




=======
package polyomino;
import java.awt.Color;
import java.io.File;
import java.util.*;


public class Polyomino {
	private ArrayList<Integer> xcoords;
	private ArrayList<Integer> ycoords;
	
	public Polyomino(ArrayList<Integer> xcoords, ArrayList<Integer> ycoords) {
		this.xcoords = new ArrayList<Integer>(xcoords);
		this.ycoords = new ArrayList<Integer>(ycoords);
	}
	
	public Polyomino(boolean[][] coords, int minY) {
		this.xcoords = new ArrayList<Integer>();
		this.ycoords = new ArrayList<Integer>();
		for(int x=0; x<coords.length; x++)
			for(int y=0; y<coords[0].length; y++)
				if(coords[x][y]) {
					this.xcoords.add(Integer.valueOf(x));
					this.ycoords.add(Integer.valueOf(y+minY));
				}
	}
	
	public Polyomino(String s) {
		//Example: s = [(0,0), (0,4), (1,0)]
		String[] splits = s.replaceAll("[\\[( )\\]]","").replace(","," ").split(" ");
		this.xcoords = new ArrayList<Integer>();
		this.ycoords = new ArrayList<Integer>();
		for(int i=0; i<splits.length; i++) {
			if(i%2 == 0) this.xcoords.add(Integer.valueOf(splits[i]));
			else this.ycoords.add(Integer.valueOf(splits[i]));
		}
	}
	
	public int getWidth() {
		int start_x = Integer.MAX_VALUE;
		int end_x = Integer.MIN_VALUE;
		for(Integer x: xcoords) {
			start_x = Math.min(start_x, x);
			end_x = Math.max(end_x, x);
		}
		return(end_x-start_x+1);
	}
	
	public int getHeight() {
		int start_y = Integer.MAX_VALUE;
		int end_y = Integer.MIN_VALUE;
		for(Integer y: ycoords) {
			start_y = Math.min(start_y, y);
			end_y = Math.max(end_y, y);
		}
		return(end_y-start_y+1);
	}
	
	public Integer getMinY() {
		int minY = Integer.MAX_VALUE;
		for(Integer y: ycoords) {
			minY = Math.min(minY, y);
		}
		return(minY);
	}
	
	public Integer getMinX() {
		int minX = Integer.MAX_VALUE;
		for(Integer x: xcoords) {
			minX = Math.min(minX, x);
		}
		return(minX);
	}
	
	public Polyomino translation(int delta_x, int delta_y) {
		ArrayList<Integer> new_ycoords = new ArrayList<>();
		ArrayList<Integer> new_xcoords = new ArrayList<>();
		for(Integer x: xcoords) {
			new_xcoords.add(x+delta_x);
		}
		for(Integer y: ycoords) {
			new_ycoords.add(y+delta_y);
		}
		return(new Polyomino(new_xcoords, new_ycoords));
	}
	
	public Polyomino dilation(int rate) {
		ArrayList<Integer> new_ycoords = new ArrayList<>();
		ArrayList<Integer> new_xcoords = new ArrayList<>();
		for(Integer x: xcoords) {
			Integer i = x*rate;
			while(i <= (x+1)*rate-1) {
				int k = 1;
				while(k++ <= rate) new_xcoords.add(i);
				i++;
			}
		}
		
		for(Integer y: ycoords) {
			int k = 1;
			while(k++ <= rate) {
				Integer i = y*rate;
				while(i <= (y+1)*rate-1) 
					new_ycoords.add(i++);
			}
		}
		return(new Polyomino(new_xcoords, new_ycoords));
	}
	
	public Polyomino reflection(String axis) {
		if(axis == "y") {
			ArrayList<Integer> new_xcoords = new ArrayList<>();
			for(Integer x: xcoords) {
				new_xcoords.add(-x-1);
			}
			return(new Polyomino(new_xcoords, ycoords));
		}
		else if(axis == "x") {
			ArrayList<Integer> new_ycoords = new ArrayList<>();
			for(Integer y: ycoords) {
				new_ycoords.add(-y-1);
			}
			return(new Polyomino(xcoords, new_ycoords));
		}
		else return null;
	}
	
	//left quarter-turn
	public Polyomino rotation() {
		Polyomino p = new Polyomino(this.ycoords, this.xcoords);
		p = p.reflection("y");
		return(p);
	}
	
	public void drawUnitSquare(int unit, int px, int py, int x, int y, Image2d img, Color color) {
		int left = px + x*unit;
		int right = px + (x+1)*unit;
		int down = py - (y+1)*unit;
		int up = py - y*unit;
		int[] xcoords = {left, right, right, left};
		int[] ycoords = {up, up, down, down};
		img.addPolygon(xcoords, ycoords, color);
	}
	
	public void draw(int unit, int px, int py, Image2d img, Color color) {
		int len = xcoords.size();
		for(int i=0; i<len; i++) {
			drawUnitSquare(unit, px, py, xcoords.get(i), ycoords.get(i), img, color);
		}
	}
	
	public String toString() {
		String rs = "[";
		int len = xcoords.size();
		for(int i=0; i<len; i++) {
			rs += "(" + xcoords.get(i) + "," + ycoords.get(i) + "), ";
		}
		rs += "]";
		return rs;
	}
	
	public static boolean equal(Polyomino a, Polyomino b) {
		int w = a.getWidth();
		int h = a.getHeight();
		if(h != b.getHeight()) return false;
		if(w != b.getWidth()) return false;
		boolean[][] coords = new boolean[w][h];
		int len = a.xcoords.size();
		Integer MinY = a.getMinY();
		for(int i=0; i<len; i++)
			coords[a.xcoords.get(i)][a.ycoords.get(i)-MinY] = true;
		len = b.xcoords.size();
		MinY = b.getMinY();
		for(int i=0; i<len; i++)
			if(!coords[b.xcoords.get(i)][b.ycoords.get(i)-MinY])
				return false;
		return true;
	}
	
	public boolean contain(int x, int y) {
		int len = xcoords.size();
		for(int i=0; i<len; i++) {
			if (xcoords.get(i) == x && ycoords.get(i) == y) {
				return true;
			}
		}
		return false;
	}
	
	public static Polyomino addUnitPoint(Polyomino P, Integer x, Integer y) {
		ArrayList<Integer> newxcoords = (ArrayList<Integer>) P.xcoords.clone();
		ArrayList<Integer> newycoords = (ArrayList<Integer>) P.ycoords.clone();
		newxcoords.add(x);
		newycoords.add(y);
		return new Polyomino(newxcoords,newycoords);
	}
	
	public static void main(String[] args) {
		/*
		Image2d img = new Image2d(100,200);
		String s = "[(0,0), (0,1), (0,2), (0,3), (0,4), (1,2), (1,3), (2,1), (2,2), (3,0), (3,1), (3,2), (3,3), (3,4)]";
		Polyomino test = new Polyomino(s);
		test.draw(10, 10, 200-10, img, Color.RED);
		new Image2dViewer(img);
		System.out.println(test.getWidth());
		System.out.println(test.getHeight());
		*/
		//
		/*
		File fileName = new File("polyominoesINF421.txt");
		ListOfPolyominoes ps = new ListOfPolyominoes(fileName);
		int unit = 10;
		Image2d img = new Image2d(ps.getWidth()*unit, ps.getHeight()*unit);
		int px = 0;
		int py = ps.getHeight()*unit;
		ps.draw(unit, px, py, img, Color.red);
		new Image2dViewer(img);
		*/
		//
		/*
		Image2d img = new Image2d(100,200);
		String s = "[(0,1), (0,2), (0,3), (0,4), (1,1), (2,0), (2,1), (2,2)]";
		Polyomino test = new Polyomino(s);
		Polyomino test_reflection = test.rotation();
		test_reflection.draw(10, 50, 90, img, Color.RED);
		test.draw(10,50, 90, img, Color.YELLOW);
		new Image2dViewer(img);
		*/
		/*
		Image2d img = new Image2d(100,200);
		int[][] matrix = {{1,0,0}, {0,1,0}, {0,0,1}};
		Polyomino test = new Polyomino(matrix);
		test.draw(10, 50, 50, img, Color.RED);
		new Image2dViewer(img);
		*/
		
		ListOfPolyominoes Fixed = Enumeration.genFixedPolyominoes(9);
		System.out.println(Enumeration.fixedPolyominoes(17));
//		Fixed.draw(10, Color.RED);
//		Image2d img = new Image2d(1000, 500);
//		Fixed.draw(10, 10, 100, img , Color.RED);
//		new Image2dViewer(img);
//		check.draw(10, Color.RED);
	}
}
