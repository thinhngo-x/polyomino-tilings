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
		for (int x = 0; x < coords.length; x++)
			for (int y = 0; y < coords[0].length; y++)
				if (coords[x][y]) {
					this.xcoords.add(Integer.valueOf(x));
					this.ycoords.add(Integer.valueOf(y + minY));
				}
	}

	public Polyomino(String s) {
		// Example: s = [(0,0), (0,4), (1,0)]
		String[] splits = s.replaceAll("[\\[()\\]]", "").replace(",", " ").split("\\s", 0);
		this.xcoords = new ArrayList<Integer>();
		this.ycoords = new ArrayList<Integer>();
		for (int i = 0; i < splits.length; i++) {
			if (i % 2 == 0)
				this.xcoords.add(Integer.valueOf(splits[i]));
			else
				this.ycoords.add(Integer.valueOf(splits[i]));
		}
	}

	public int getWidth() {
		return (getMaxX() - getMinX() + 1);
	}

	public int getHeight() {
		return (getMaxY() - getMinY() + 1);
	}

	public Integer getMinY() {
		int minY = Integer.MAX_VALUE;
		for (Integer y : ycoords) {
			minY = Math.min(minY, y);
		}
		return (minY);
	}

	public Integer getMinX() {
		int minX = Integer.MAX_VALUE;
		for (Integer x : xcoords) {
			minX = Math.min(minX, x);
		}
		return (minX);
	}

	public Integer getMaxY() {
		int maxY = Integer.MIN_VALUE;
		for (Integer y : ycoords) {
			maxY = Math.max(maxY, y);
		}
		return (maxY);
	}

	public Integer getMaxX() {
		int maxX = Integer.MIN_VALUE;
		for (Integer x : xcoords) {
			maxX = Math.max(maxX, x);
		}
		return (maxX);
	}

	public Polyomino translateToOrigin() {
		return (this.translation(0, 0));
	}

	public Polyomino translation(int toX, int toY) {
		ArrayList<Integer> new_ycoords = new ArrayList<>();
		ArrayList<Integer> new_xcoords = new ArrayList<>();
		Integer x = getMinX();
		Integer y = Integer.MAX_VALUE;
		for (int i = 0; i < xcoords.size(); i++) {
			if (xcoords.get(i) == x)
				y = Math.min(ycoords.get(i), y);
		}
		for (Integer xc : xcoords) {
			new_xcoords.add(xc + toX - x);
		}
		for (Integer yc : ycoords) {
			new_ycoords.add(yc + toY - y);
		}
		return (new Polyomino(new_xcoords, new_ycoords));
	}

	public Polyomino dilation(int rate) {
		ArrayList<Integer> new_ycoords = new ArrayList<>();
		ArrayList<Integer> new_xcoords = new ArrayList<>();
		for (Integer x : xcoords) {
			Integer i = x * rate;
			while (i <= (x + 1) * rate - 1) {
				int k = 1;
				while (k++ <= rate)
					new_xcoords.add(i);
				i++;
			}
		}

		for (Integer y : ycoords) {
			int k = 1;
			while (k++ <= rate) {
				Integer i = y * rate;
				while (i <= (y + 1) * rate - 1)
					new_ycoords.add(i++);
			}
		}
		return (new Polyomino(new_xcoords, new_ycoords));
	}

	public Polyomino reflection(String axis) {
		if (axis == "H") {
			ArrayList<Integer> new_xcoords = new ArrayList<>();
			for (Integer x : xcoords) {
				new_xcoords.add(-x);
			}
			return (new Polyomino(new_xcoords, ycoords));
		} else if (axis == "V") {
			ArrayList<Integer> new_ycoords = new ArrayList<>();
			for (Integer y : ycoords) {
				new_ycoords.add(-y);
			}
			return (new Polyomino(xcoords, new_ycoords));
		} else if (axis == "A") {
			return (new Polyomino(ycoords, xcoords));
		} else if (axis == "D") {
			return (reflection("H").reflection("A").reflection("H"));
		} else
			return null;
	}

	// left quarter-turn
	public Polyomino rotation() {
		Polyomino p = new Polyomino(this.ycoords, this.xcoords);
		p = p.reflection("H");
		return (p);
	}

	public void drawUnitSquare(int unit, int px, int py, int x, int y, Image2d img, Color color) {
		int left = px + x * unit;
		int right = px + (x + 1) * unit;
		int up = py - (y + 1) * unit;
		int down = py - y * unit;
		int[] xcoords = { left, right, right, left };
		int[] ycoords = { down, down, up, up };
		img.addPolygon(xcoords, ycoords, color);
	}

	public void draw(int unit, int px, int py, Image2d img, Color color) {
		int len = xcoords.size();
		for (int i = 0; i < len; i++) {
			drawUnitSquare(unit, px, py, xcoords.get(i), ycoords.get(i), img, color);
		}
		Set<Point> points = toSet();
		for (Point p : points) {
			int x = p.getX();
			int y = p.getY();
			if (!points.contains(new Point(x, y + 1)))
				img.addEdge(px + x * unit, py - (y + 1) * unit, px + (x + 1) * unit, py - (y + 1) * unit, unit / 10);
			if (!points.contains(new Point(x, y - 1)))
				img.addEdge(px + x * unit, py - y * unit, px + (x + 1) * unit, py - y * unit, unit / 10);
			if (!points.contains(new Point(x + 1, y)))
				img.addEdge(px + (x + 1) * unit, py - (y + 1) * unit, px + (x + 1) * unit, py - y * unit, unit / 10);
			if (!points.contains(new Point(x - 1, y)))
				img.addEdge(px + x * unit, py - (y + 1) * unit, px + x * unit, py - y * unit, unit / 10);

		}
	}

	public TreeSet<Point> toSet() {
		TreeSet<Point> squares = new TreeSet<>();
		for (int i = 0; i < xcoords.size(); i++) {
			squares.add(new Point(xcoords.get(i), ycoords.get(i)));
		}
		return squares;
	}

	@Override
	public String toString() {
		TreeSet<Point> squares = toSet();
		String rs = "";
		for (Point p : squares) {
			rs += p.getX();
			rs += ",";
			rs += p.getY();
			rs += ",";
		}
		return rs;
	}

	@Override
	public int hashCode() {
		return toString().hashCode();
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || o.getClass() != getClass())
			return false;
		Polyomino p = (Polyomino) o;
		if (!equalsTo(p))
			return false;
		return true;
	}

	public Set<String> toSetOfStrings() {
		Set<String> result = new HashSet<>();
		String space = ",";
		for (int i = 0; i < xcoords.size(); i++)
			result.add(xcoords.get(i) + space + ycoords.get(i) + space);
		return result;
	}

	public Set<String> toSetOfStrings(String name) {
		Set<String> result = new HashSet<>();
		String space = ",";
		for (int i = 0; i < xcoords.size(); i++)
			result.add(xcoords.get(i) + space + ycoords.get(i) + space);
		result.add(name);
		return result;
	}

	public boolean isCoveredIn(Polyomino p) {
		TreeSet<Point> squares = this.toSet();
		TreeSet<Point> squaresOfp = p.toSet();
		for (Point square : squares) {
			if (!squaresOfp.contains(square))
				return false;
		}
		return true;
	}

	public boolean equalsTo(Polyomino p) {
		TreeSet<Point> squares = this.toSet();
		TreeSet<Point> squaresOfp = p.toSet();
		for (Point square : squaresOfp) {
			if (!squares.contains(square))
				return false;
		}
		for (Point square : squares) {
			if (!squaresOfp.contains(square))
				return false;
		}
		return true;
	}

	public boolean contain(int x, int y) {
		int len = xcoords.size();
		for (int i = 0; i < len; i++) {
			if (xcoords.get(i) == x && ycoords.get(i) == y) {
				return true;
			}
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	public static Polyomino addUnitPoint(Polyomino P, Integer x, Integer y) {
		ArrayList<Integer> newxcoords = (ArrayList<Integer>) P.xcoords.clone();
		ArrayList<Integer> newycoords = (ArrayList<Integer>) P.ycoords.clone();
		newxcoords.add(x);
		newycoords.add(y);
		return new Polyomino(newxcoords, newycoords);
	}

	public boolean fixedEqualsTo(Polyomino p) {
		TreeSet<Point> squares = this.translateToOrigin().toSet();
		TreeSet<Point> squaresOfp = p.translateToOrigin().toSet();
		for (Point square : squaresOfp) {
			if (!squares.contains(square))
				return false;
		}
		for (Point square : squares) {
			if (!squaresOfp.contains(square))
				return false;
		}
		return true;
	}

	public boolean isR() {
		if (this.rotation().rotation().fixedEqualsTo(this))
			return true;
		else
			return false;
	}

	public boolean isR2() {
		if (this.rotation().fixedEqualsTo(this))
			return true;
		else
			return false;
	}

	public boolean isH() {
		if (this.reflection("H").fixedEqualsTo(this))
			return true;
		else
			return false;
	}

	public boolean isV() {
		if (this.reflection("V").fixedEqualsTo(this))
			return true;
		else
			return false;
	}

	public boolean isA() {
		if (this.reflection("A").fixedEqualsTo(this))
			return true;
		else
			return false;
	}

	public boolean isD() {
		if (this.reflection("D").fixedEqualsTo(this))
			return true;
		else
			return false;
	}

	public boolean isHVR() {
		if (isH() && isV())
			return true;
		else
			return false;
	}

	public boolean isADR() {
		if (isA() && isD())
			return true;
		else
			return false;
	}

	public boolean isHVADR2() {
		if (isHVR() && isADR())
			return true;
		else
			return false;
	}

}
