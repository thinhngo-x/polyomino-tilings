package polyomino;

import java.awt.Color;
import java.util.*;
import java.io.*;

public class ListOfPolyominoes {
	private Set<Polyomino> polyominoes;

	public ListOfPolyominoes() {
		polyominoes = new HashSet<Polyomino>();
	}

	public ListOfPolyominoes(Set<Polyomino> list) {
		polyominoes = list;
	}

	public static LinkedList<Polyomino> readFiles(File fileName) {
		ArrayList<String> arrayList = new ArrayList<>();
		LinkedList<Polyomino> polyominoes = new LinkedList<>();

		try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
			while (reader.ready()) {
				arrayList.add(reader.readLine());
			}
		} catch (IOException e) {
			System.out.println("Error");
		}

		for (int i = 0; i < arrayList.size(); i++) {
			polyominoes.add(new Polyomino(arrayList.get(i).replaceAll("[\\[( )\\]]", "").replace(",", " ")));
		}
		return polyominoes;
	}

	public int getWidth() {
		int width = 0;
		for (Polyomino p : polyominoes) {
			width += p.getWidth() + 1;
		}
		return (width - 1);
	}

	public int getHeight() {
		int height = 0;
		for (Polyomino p : polyominoes) {
			height = Math.max(height, p.getHeight());
		}
		return (height);
	}

	public void add(Polyomino p) {
		polyominoes.add(p);
	}

	public void draw(int unit, int px, int py, Image2d img) {
		for (Polyomino p : polyominoes) {
			Random rand = new Random();
			float r = rand.nextFloat();
			float g = rand.nextFloat();
			float b = rand.nextFloat();
			Color randomColor = new Color(r, g, b);
			p.draw(unit, px, py, img, randomColor);
		}
	}

	public int getMinY() {
		Integer minY = Integer.MAX_VALUE;
		for (Polyomino p : polyominoes) {
			minY = Math.min(p.getMinY(), minY);
		}
		return minY;
	}

	public static void olddraw(LinkedList<Polyomino> poly, int unit, int px, int py, Image2d img) {
		Color[] color = new Color[] { Color.BLACK, Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW, Color.GRAY };
		for (int i = 0; i < poly.size(); i++) {
			Polyomino p = poly.get(i);
			p.draw(unit, px, py, img, color[i]);
			px += (1 + p.getWidth()) * unit;
		}
	}

	public void draw(int unit, Image2d img) {
		int h = img.getHeight();
		int px = 0;
		int py = h;
		draw(unit, px, py, img);
	}

	public void draw(int unit, Image2d img, int maxY) {
		int px = 0;
		int py = (maxY + 1) * unit;
		draw(unit, px, py, img);
	}

	@Override
	public String toString() {
		String rs = "";
		for (Polyomino p : polyominoes) {
			rs += p.toString();
			rs += "\n";
		}
		return rs;
	}

	public int size() {
		return polyominoes.size();
	}

	public Polyomino getFirst() {
		return polyominoes.iterator().next();
	}

	public void main(String[] args) {

	}
}
