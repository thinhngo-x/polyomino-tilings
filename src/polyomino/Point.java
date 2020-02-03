package polyomino;

import java.util.LinkedList;

public class Point implements Comparable<Point> {
	private int x;
	private int y;

	Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	@Override
	public int hashCode() {
		return (x + y * 71);
	}

	@Override
	public boolean equals(Object o) {
		if (o == null)
			return false;
		if (o.getClass() != getClass())
			return false;
		else {
			Point t = (Point) o;
			if (x != t.x || y != t.y)
				return false;
		}
		return true;
	}

	public Point hashCode(int hash) {
		Point result = new Point(hash % 71, hash / 71);
		return result;
	}

	@Override
	public int compareTo(Point point) {
		if (this.x < point.x)
			return -1;
		else if (this.x > point.x)
			return 1;
		else {
			if (this.y > point.y)
				return 1;
			else if (this.y < point.y)
				return -1;
			else
				return 0;
		}
	}

	public LinkedList<Point> friends() {
		LinkedList<Point> friends = new LinkedList<Point>();
		friends.add(new Point(this.x, this.y + 1));
		friends.add(new Point(this.x + 1, this.y));
		friends.add(new Point(this.x, this.y - 1));
		friends.add(new Point(this.x - 1, this.y));
		return friends;
	}

	public static boolean isPoint(String s) {
		if (s.split(",").length == 2)
			return true;
		return false;
	}
}
