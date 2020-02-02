package polyomino;

import java.util.*;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.*;

public class test {
	public static void tiling1() throws IOException {
		Polyomino P = new Polyomino("0 9 0 10 1 7 1 8 1 9 1 10 " + "2 5 2 6 2 7 2 8 2 9 2 10 "
				+ "3 3 3 4 3 5 3 6 3 7 3 8 3 9 3 10 " + "4 1 4 2 4 3 4 4 4 5 4 6 4 7 4 8 4 9 4 10 "
				+ "5 0 5 1 5 2 5 3 5 4 5 5 5 6 5 7 5 8 5 9 " + "6 0 6 1 6 2 6 3 6 4 6 5 6 6 6 7 "
				+ "7 0 7 1 7 2 7 3 7 4 7 5 " + "8 0 8 1 8 2 8 3 " + "9 0 9 1");
		P = P.reflection("A");
		Set<ListOfPolyominoes> solutions = ExactCover.tilingsByFreePolyNoRep(P, 5);
		int unit = 50;
		int i = 0;
		System.out.println(solutions.size());
		for (ListOfPolyominoes sol : solutions) {
			i++;
			Image2d img = new Image2d(P.getWidth() * unit, P.getHeight() * unit);
			sol.draw(unit, img);
			Image2dComponent component = new Image2dComponent(img);
			component.setVisible(false);
			BufferedImage bi = ScreenImage.createImage(component);
			ScreenImage.writeImage(bi, "results/tiling1/" + i + ".png");
			System.out.println("Printing..." + i);
		}
	}

	public static void tiling2() throws IOException {
		Polyomino P = new Polyomino("0 0 1 0 2 0 3 0 4 0 5 0 6 0 7 0 8 0 9 0 "
				+ "0 1 1 1 2 1 3 1 4 1 5 1 6 1 7 1 8 1 9 1 " + "1 2 2 2 3 2 4 2 5 2 6 2 7 2 8 2 "
				+ "1 3 2 3 3 3 4 3 5 3 6 3 7 3 8 3 " + "2 4 3 4 4 4 5 4 6 4 7 4 " + "2 5 3 5 4 5 5 5 6 5 7 5 "
				+ "3 6 4 6 5 6 6 6 " + "3 7 4 7 5 7 6 7 " + "4 8 5 8 " + "4 9 5 9 ");
		Set<ListOfPolyominoes> solutions = ExactCover.tilingsByFreePolyNoRep(P, 5);
		int unit = 50;
		int i = 0;
		System.out.println(solutions.size());
		for (ListOfPolyominoes sol : solutions) {
			i++;
			Image2d img = new Image2d(P.getWidth() * unit, P.getHeight() * unit);
			sol.draw(unit, img);
			Image2dComponent component = new Image2dComponent(img);
			component.setVisible(false);
			BufferedImage bi = ScreenImage.createImage(component);
			ScreenImage.writeImage(bi, "results/tiling2/" + i + ".png");
			System.out.println("Printing..." + i);
		}
	}

	public static void tiling3() throws IOException {
		Polyomino P = new Polyomino("0 4 0 5 " + "1 3 1 4 1 5 1 6 " + "2 2 2 3 2 4 2 5 2 6 2 7 "
				+ "3 1 3 2 3 3 3 4 3 5 3 6 3 7 3 8 " + "4 0 4 1 4 2 4 3 4 4 4 5 4 6 4 7 4 8 4 9 "
				+ "5 0 5 1 5 2 5 3 5 4 5 5 5 6 5 7 5 8 5 9 " + "6 1 6 2 6 3 6 4 6 5 6 6 6 7 6 8 "
				+ "7 2 7 3 7 4 7 5 7 6 7 7 " + "8 3 8 4 8 5 8 6 " + "9 4 9 5");
		ListOfPolyominoes list = new ListOfPolyominoes();
		list.add(P);
		Image2d img = new Image2d(P.getWidth() * 50, P.getHeight() * 50);
		list.draw(50, img);
		Image2dComponent component = new Image2dComponent(img);
		component.setVisible(false);
		BufferedImage bi = ScreenImage.createImage(component);
		ScreenImage.writeImage(bi, "results/tiling3/" + 1 + ".png");
		System.out.println("Printing..." + 1);
		Set<ListOfPolyominoes> solutions = ExactCover.tilingsByFreePolyNoRep(P, 5);
		int unit = 50;
		int i = 0;
		System.out.println(solutions.size());
		/*
		 * for (ListOfPolyominoes sol : solutions) { i++; Image2d img = new
		 * Image2d(P.getWidth() * unit, P.getHeight() * unit); sol.draw(unit, img);
		 * Image2dComponent component = new Image2dComponent(img);
		 * component.setVisible(false); BufferedImage bi =
		 * ScreenImage.createImage(component); ScreenImage.writeImage(bi,
		 * "results/tiling3/" + i + ".png"); System.out.println("Printing..." + i);
		 * 
		 * }
		 */
	}
	
	public static void polyon(int n) throws IOException {
		Set<Polyomino> solutions = Enumeration.genFreePolyominoes(n);
		Set<ListOfPolyominoes> set = new HashSet<ListOfPolyominoes>();
		for (Polyomino sol : solutions) {
			ListOfPolyominoes list = new ListOfPolyominoes();
			list.add(sol);
			set.add(list);
		}
		int unit = 50;
		int i = 0;
		System.out.println(set.size());
		for (ListOfPolyominoes sol : set) {
			i++;
			Image2d img = new Image2d(n* unit, (n+2)* unit); //py = 5*50
			sol.draw(unit, img);
			Image2dComponent component = new Image2dComponent(img);
			component.setVisible(false);
			BufferedImage bi = ScreenImage.createImage(component);
			ScreenImage.writeImage(bi, "results/polyon/" + i + ".png");
			System.out.println("Printing..." + i);
		}
	}
	
	public static void rectiling(int n) throws IOException {
		String s = "";
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				s += i + " " + j + " ";
			}
		}
		System.out.println(s);
		Polyomino P = new Polyomino(s);
		Set<ListOfPolyominoes> solutions = ExactCover.tilingsByFixedPoly(P, n);
		int unit = 50;
		int i = 0;
		System.out.println(solutions.size());
		for (ListOfPolyominoes sol : solutions) {
			i++;
			Image2d img = new Image2d(P.getWidth() * unit, P.getHeight() * unit);
			sol.draw(unit, img);
			Image2dComponent component = new Image2dComponent(img);
			component.setVisible(false);
			BufferedImage bi = ScreenImage.createImage(component);
			ScreenImage.writeImage(bi, "results/rectiling/" + i + ".png");
			System.out.println("Printing..." + i);
		}
	}

	public static void main(String[] args) throws IOException {
		tiling3();
	}
}
