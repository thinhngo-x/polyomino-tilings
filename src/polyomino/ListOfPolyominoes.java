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

	public ListOfPolyominoes(File fileName) {
		ArrayList<String> arrayList = new ArrayList<>();
		polyominoes = new HashSet<>();

		try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
			while (reader.ready()) {
				arrayList.add(reader.readLine());
			}
		} catch (IOException e) {
			System.out.println("Error");
		}

		for (int i = 0; i < arrayList.size(); i++) {
			polyominoes.add(new Polyomino(arrayList.get(i)));
		}
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
		for (Polyomino p: polyominoes) {
			Random rand = new Random();
			int r = rand.nextInt(255);
			int g = rand.nextInt(255);
			int b = rand.nextInt(255);
			Color randomColor = new Color(r, g, b);
			p.draw(unit, px, py, img, randomColor);
		}
	}
	
	public int getMinY() {
		Integer minY = Integer.MAX_VALUE;
		for(Polyomino p: polyominoes) {
			minY = Math.min(p.getMinY(), minY);
		}
		return minY;
	}

	public void draw(int unit, Image2d img) {
		int h = img.getHeight();
		int px = 0;
		int py = h;
		draw(unit, px, py, img);
		new Image2dViewer(img);
	}
	
	public String toString() {
		String rs = "";
		for(Polyomino p: polyominoes) {
			rs += p.toString();
			rs += "\n";
		}
		return rs;
	}
	
	public int size() {
		return polyominoes.size();
	}
	
	public void main(String[] args) {

	}
}
