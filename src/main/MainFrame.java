package main;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import utils.StringSetCreator;
import entities.CeldaGrilla;

public class MainFrame extends JFrame {


	public static BufferedImage drawOutline(int w, int h, Area area) {

		final BufferedImage result = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = result.createGraphics();

		g.setColor(Color.white);
		AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f);
		g.setComposite(ac);

		g.fillRect(0, 0, w, h);

		g.setClip(area);
		g.setColor(Color.green);
		g.fillRect(0, 0, w, h);
		g.setColor(Color.black);

		g.setClip(null);
		g.setStroke(new BasicStroke(1));
//		g.setColor(Color.blue);
		g.draw(area);

		return result;
	}

	public static void displayAndWriteImage(BufferedImage image, String fileName) throws Exception {

		 ImageIO.write(image, "png", new File(fileName));
//		JOptionPane.showMessageDialog(null, new JLabel(new ImageIcon(image)));
	}

	public static void main(String[] args) throws Exception {

//		mostrarGrillaEjemplo(15, 10);
//
//		if(true)
//			return;

		final double tamañoCelda = 0.0001;
		// "y" vendría a ser latitud
		double latMin = -37.5108465428739;
		double latMax = -36.3764335428739;

		// "x" vendría a ser longitud
		double longMin = -62.3161004401404;
		double longMax = -61.7818980760294;

		int cantY = new Double(Math.floor(((longMax - longMin) / tamañoCelda))).intValue();
		int cantX = new Double(Math.floor(((latMax - latMin) / tamañoCelda))).intValue();
		System.out.println("canX: "+cantX);
		System.out.println("canY: "+cantY);

		//TODO: definir!
		int tamañoCeldaPx = 1;

		Area area = new Area();

		// if (true)
		// return;

		// Sumo 1. Empieza en 0.
		CeldaGrilla[][] grilla = new CeldaGrilla[cantX + 1][cantY + 1];
		//TODO!
//		inicializar(grilla, cantY, cantX);

//		File file = new File("/home/pruebahadoop/Documentos/DataSets/monitores/output (copia) RendMedio ancho00001 OK!/part-r-00000");
		File file = new File("/home/pruebahadoop/Documentos/DataSets/monitores/output/part-r-00000");
//		File file = new File("output15x10");
		StringSetCreator output = new StringSetCreator();
		output.getStringSet(file);


		Set<String> valores = output.getStringSet(file);
		List<Double> rendimientos = new ArrayList<Double>();
		System.out.println("Celdas Existentes: "+valores.size());
		for (String linea : valores) {
			linea = linea.replace("<", "").replace(">", "");
			String[] datos = linea.split("	");
			int col = Integer.valueOf(datos[0].split(",")[0]);
			int fila = Integer.valueOf(datos[0].split(",")[1]);
			double rend = Double.valueOf(datos[1]);
			grilla[col][fila] = new CeldaGrilla(0);
			grilla[col][fila].setRend(rend);// = new CeldaGrilla(rend);
			Rectangle r = new Rectangle(col * tamañoCeldaPx, fila * tamañoCeldaPx, tamañoCeldaPx, tamañoCeldaPx);
			area.add(new Area(r));
			rendimientos.add(rend);
//			System.out.println("rend[" + col + "," + fila + "]: " + grilla[col][fila].getRend());
			// System.out.println("REND: "+rend);
		}
		Collections.sort(rendimientos);

		System.out.println("rend max: "+rendimientos.get(rendimientos.size()-1));

		////////////////////////////////////////////////
		///Dibujar y guardar imagen con AREA         //
		//////////////////////////////////////////////

//		BufferedImage result = drawOutline(cantY * tamañoCeldaPx, cantX * tamañoCeldaPx, area);
//
//		displayAndWriteImage(result, "ejemplo");
//
//		if (true)
//			return;

		////////////////////////////////////////////////
		///MOSTRAR GRILLA PANEL				         //
		//////////////////////////////////////////////

//		GrillaPanel gp = new GrillaPanel(cantX, cantY, tamañoCelda);
//		gp.setGrilla(grilla);
//		new MainFrame(gp).setVisible(true);
//		System.out.println(gp.getGrilla().length);

		saveImageGrilla(tamañoCeldaPx, cantX, cantY, grilla, rendimientos.get(rendimientos.size()-1));

	}

	private static void inicializar(CeldaGrilla[][] grilla, int cantY, int cantX) {
		for (int i = 0; i <= cantX; i++) {
			for (int j = 0; j <= cantY; j++) {
				System.out.println("<"+i+","+j+">	"+"1");
//				grilla[i][j] = new CeldaGrilla(0, true);
			}
		}
//		System.out.println(grilla);
	}

	private static void mostrarGrillaEjemplo(int ancho, int alto){
//		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < ancho; i++) {
			for (int j = 0; j < alto; j++) {
				if(i!=j)
				System.out.println("<"+i+","+j+">	"+"1");
			}
		}
	}

	private static void saveImageGrilla(int celdaSize, int ancho, int alto, CeldaGrilla[][] celdaGrilla, double maxRend) {

		float alpha = 0;
		int tamañoCeldapx = celdaSize;

		// Constructs a BufferedImage of one of the predefined image types.
		BufferedImage bufferedImage = new BufferedImage(ancho*tamañoCeldapx, alto*tamañoCeldapx, BufferedImage.TYPE_INT_RGB);

		// Create a graphics which can be used to draw into the buffered image
		Graphics2D g2 = bufferedImage.createGraphics();

		int cont = 0;
		for (int i = 0, ipx = 0; i < ancho; i++, ipx += tamañoCeldapx) {
			for (int j = 0, jpx = 0; j < alto; j++, jpx += tamañoCeldapx) {
				cont++;
//				if(celdaGrilla[i][j].isVacio() && false){
//					alpha = 0f;
//					System.out.println("<"+i+","+j+">");
//				}else {
					alpha = celdaGrilla[i][j] == null ? 0f : (float) celdaGrilla[i][j].getRend();
//				}
				alpha = (float) ((alpha) / maxRend);
				if(alpha > 1f){
					System.out.println("<"+i+","+j+"> con REND: "+alpha);
					alpha = 1f;
				}
//				System.out.println("alpha: " + alpha);
				//Fondo
				//por defecto fondo blanco
				g2.setColor(Color.white);
				g2.fillRect(ipx, jpx, tamañoCeldapx, tamañoCeldapx);

				//Más rojo = mayor rend en esa celda
				Color c = new Color(0.0f, 0.0f, 0.0f, alpha);  // Red with alpha = 0.5
				g2.setColor(c);
//				g2.drawRect(ipx, jpx, tamañoCeldapx, tamañoCeldapx);

				g2.fillRect(ipx, jpx, tamañoCeldapx, tamañoCeldapx);
//				if(alpha > 1){
//					alpha = 1f;
//				}
//				AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.XOR, alpha);
//				g2.setColor(Color.black);
//				g2.setComposite(ac);
//				g2.fillRect(ipx, jpx, tamañoCeldapx, tamañoCeldapx);
			}
		}
		System.out.println("Total Celdas(existan o no): " + cont);

		// Save as PNG
		File file = new File("myimage.png");
		try {
			ImageIO.write(bufferedImage, "png", file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
