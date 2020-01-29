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