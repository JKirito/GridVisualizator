package utils;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import entities.CeldaGrilla;

public class ImageGridCreator {
	private int tamañoCeldapx;
	private int cantCeldasX;
	private int cantCeldasY;
	private CeldaGrilla[][] celdaGrilla;
	private double maxRend;

	public ImageGridCreator(int tamañoCeldapx, int cantCeldasX, int cantCeldasY, CeldaGrilla[][] celdaGrilla,
			double maxRend) {
		super();
		this.tamañoCeldapx = tamañoCeldapx;
		this.cantCeldasX = cantCeldasX;
		this.cantCeldasY = cantCeldasY;
		this.celdaGrilla = celdaGrilla;
		this.maxRend = maxRend;
	}

	public void saveImageGrilla(String pathSave, String nameSave) {
		System.out.println("Comenzando proceso y guardado de imagen...");
		float alpha = 0;

		// Constructs a BufferedImage of one of the predefined image types.
		BufferedImage bufferedImage = new BufferedImage(cantCeldasX * tamañoCeldapx, cantCeldasY * tamañoCeldapx,
				BufferedImage.TYPE_INT_RGB);

		// Create a graphics which can be used to draw into the buffered image
		Graphics2D g2 = bufferedImage.createGraphics();

		for (int i = 0, ipx = 0; i < cantCeldasX; i++, ipx += tamañoCeldapx) {
			for (int j = 0, jpx = 0; j < cantCeldasY; j++, jpx += tamañoCeldapx) {
				alpha = celdaGrilla[i][j] == null ? 0f : (float) celdaGrilla[i][j].getRend();
				alpha = (float) ((alpha) / maxRend);
				if (alpha > 1f) {
					System.out.println("<" + i + "," + j + "> con REND: " + alpha);
					alpha = 1f;
				}
				// por defecto fondo blanco
				g2.setColor(Color.white);
				g2.fillRect(ipx, jpx, tamañoCeldapx, tamañoCeldapx);

				// Más negro = mayor rend en esa celda
				Color c;

				// Si no había nada en la celda, poner en otro color?
				if (celdaGrilla[i][j] == null) {
					alpha = 1f;
					// Color rojo si no hay nada en la celda
					c = new Color(1f, 0.0f, 0.0f, alpha);
				} else {
					c = new Color(0.0f, 0.0f, 0.0f, alpha);
				}

				g2.setColor(c);
				g2.fillRect(ipx, jpx, tamañoCeldapx, tamañoCeldapx);
			}
		}

		// Save as PNG
		String path = pathSave != null && !pathSave.isEmpty() ? pathSave + nameSave : nameSave;
		File file = new File(path);
		try {
			ImageIO.write(bufferedImage, "png", file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
