package main;

import java.io.IOException;
import java.util.Date;

import utils.ImageGridCreator;
import utils.OutputGridProcessor;
import entities.CeldaGrilla;

public class Main {

	public static void main(String[] args) throws IOException {
		long init = new Date().getTime();

		final double tamañoCelda = 0.0001;
		// TODO: definir!
		int tamañoCeldaPx = 1;
		String path = "/home/pruebahadoop/Documentos/DataSets/monitores/outputUNMONITOR/part-r-00000";
//		String path = "/home/pruebahadoop/Documentos/DataSets/monitores/output (copia) RendMedio ancho00001 OK!/part-r-00000";
		OutputGridProcessor outputProcessor = new OutputGridProcessor(path, tamañoCelda);
		CeldaGrilla[][] grilla = outputProcessor.getCeldaGrilla();

		int cantCeldasX = outputProcessor.getCantCeldasX();
		int cantCeldasY = outputProcessor.getCantCeldasY();

		double maxRend = outputProcessor.getRendMax();

		ImageGridCreator imageCreator = new ImageGridCreator(tamañoCeldaPx, cantCeldasX, cantCeldasY, grilla, maxRend);

		// TODO!
		String pathSave = "";
		String nameSave = "ejemploGrilla";

		imageCreator.saveImageGrilla(pathSave, nameSave);

		long fin = new Date().getTime();
		System.out.println("Tardó en procesar: " + (fin - init) / 1000 + "segs");

	}

}
