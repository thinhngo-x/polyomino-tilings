package polyomino;

import java.awt.Color;
import java.util.*;
import java.io.*;

public class ListOfPolyominoes {
	private LinkedList<Polyomino> polyominoes;
	private int length;

	public ListOfPolyominoes() {
		polyominoes = new LinkedList<Polyomino>();
		length = 0;
	}
	
	public ListOfPolyominoes(LinkedList<Polyomino> list) {
		polyominoes = list;
		length = list.size();
	}

	public ListOfPolyominoes(File fileName) {
		ArrayList<String> arrayList = new ArrayList<>();
		polyominoes = new LinkedList<>();
		length = 0;

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
	
	public int length() {
		return length;
	}
	
	public Polyomino get(int index) {
		return(polyominoes.get(index));
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
		length++;
	}

	public void draw(int unit, int px, int py, Image2d img, Color color) {
		for (int i = 0; i < polyominoes.size(); i++) {
			Polyomino p = polyominoes.get(i);
			p.draw(unit, px, py, img, color);
			px += (1 + p.getWidth()) * unit;
		}
	}
	
	public int getMinY() {
		Integer minY = Integer.MAX_VALUE;
		for(Polyomino p: polyominoes) {
			minY = Math.min(p.getMinY(), minY);
		}
		return minY;
	}

	public void draw(int unit, Color color) {
		int w = getWidth();
		int h = getHeight()+2;
		Image2d img = new Image2d(w*unit, h*unit);
		int px = 0;
		int py = h*unit-getMinY();
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
	
	public boolean check() {
		ListOfPolyominoes equiv = new ListOfPolyominoes();
		for(int i=0; i<length-1; i++) {
			for(int j=i+1; j<length; j++) {
				if(Polyomino.equal(polyominoes.get(i), polyominoes.get(j))) {
					equiv.add(polyominoes.get(i));
					equiv.add(polyominoes.get(j));
					System.out.println(i+" "+j);
				}
			}
		}
		equiv.add(polyominoes.get(1597));
		equiv.draw(10, Color.RED);
		return true;
	}
	
	public void main(String[] args) {

	}
}
