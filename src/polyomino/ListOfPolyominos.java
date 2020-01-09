package polyomino;
import java.awt.Color;
import java.util.*;
import java.io.*;


public class ListOfPolyominos {
private ArrayList<Polyomino> polyominos;
	
	public ListOfPolyominos(File fileName) {
		ArrayList<String> arrayList = new ArrayList<>();
		polyominos = new ArrayList<>();

		try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
		    while (reader.ready()) {
		        arrayList.add(reader.readLine());
		    }
		}
		catch (IOException e) {
		    System.out.println("Error");
		}
		
		for(int i=0; i<arrayList.size(); i++) {
			polyominos.add(new Polyomino(arrayList.get(i)));
		}
	}
	
	public int getWidth() {
		int width = 0;
		for(Polyomino p: polyominos) {
			width += p.getWidth()+1;
		}
		return(width-1);
	}
	
	public int getHeight() {
		int height = 0;
		for(Polyomino p: polyominos) {
			height = Math.max(height, p.getHeight());
		}
		return(height);
	}
	
	public void draw(int unit, int px, int py, Image2d img, Color color) {
		for(int i=0; i<polyominos.size(); i++) {
			Polyomino p = polyominos.get(i);
			p.draw(unit, px, py, img, color);
			px += (1+p.getWidth()) * unit;
		}
	}
	
	public void main(String[] args) {
		
	}
}
