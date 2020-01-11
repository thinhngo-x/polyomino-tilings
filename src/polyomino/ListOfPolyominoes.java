package polyomino;

import java.awt.Color;
import java.util.*;
import java.io.*;

public class ListOfPolyominoes {
	private ArrayList<Polyomino> polyominoes;

	public ListOfPolyominoes() {
		polyominoes = new ArrayList<Polyomino>();
	}

	public ListOfPolyominoes(File fileName) {
		ArrayList<String> arrayList = new ArrayList<>();
		polyominoes = new ArrayList<>();

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

	public void draw(int unit, int px, int py, Image2d img, Color color) {
		for (int i = 0; i < polyominoes.size(); i++) {
			Polyomino p = polyominoes.get(i);
			p.draw(unit, px, py, img, color);
			px += (1 + p.getWidth()) * unit;
		}
	}

	public void draw(int unit, Color color) {
		int w = getWidth();
		int h = getHeight();
		Image2d img = new Image2d(w*unit, h*unit);
		int px = 0;
		int py = h*unit;
		draw(unit, px, py, img, color);
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
	
	public void main(String[] args) {

	}
}
