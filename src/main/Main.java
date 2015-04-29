package main;

import java.io.IOException;
import java.util.Date;

import utils.ImageGridCreator;
import utils.OutputGridProcessor;
import entities.CeldaGrilla;

public class Main {

	public static void main(String[] args) throws IOException {
		long init = new Date().getTime();

		boolean formatoBinario = true;
		final double tamañoCelda = 0.0001;
		int tamañoCeldaPx = 4;

		// Path input con UN monitor SIn poda
		// String pathInput = "/home/pruebahadoop/Documentos/DataSets/monitores/outputSinOutliersLONGLAT_SINPODA/part-r-00000";

		// Path input con UN monitor (outliers,  con poda)
		//   String pathInput = "/home/pruebahadoop/Documentos/DataSets/monitores/outputSinOutliersLONGLAT/part-r-00000";

		// Path input con UN monitor (CloseGaps)
		// String pathInput = "/home/pruebahadoop/Documentos/DataSets/monitores/CloseGapsx5/part-r-00000";

		// Path input con UN monitor (Smoothing)
		 String pathInput = "/home/pruebahadoop/Documentos/DataSets/monitores/Smoothingx5/part-r-00000";

		// Path input con 20 monitores
		// String pathInput =
		// "/home/pruebahadoop/Documentos/DataSets/monitores/output (copia) RendMedio ancho00001 OK!/part-r-00000";

		OutputGridProcessor outputProcessor = new OutputGridProcessor(pathInput, tamañoCelda, formatoBinario);
		CeldaGrilla[][] grilla = outputProcessor.getCeldaGrilla();

		int cantCeldasX = outputProcessor.getCantCeldasX();
		int cantCeldasY = outputProcessor.getCantCeldasY();

		double maxRend = outputProcessor.getRendMax();

		ImageGridCreator imageCreator = new ImageGridCreator(tamañoCeldaPx, cantCeldasX, cantCeldasY, grilla, maxRend,
				formatoBinario);

		// TODO!
		String pathSave = "";
		String nameSave = "Grilla";

		imageCreator.saveImageGrilla(pathSave, nameSave);

		long fin = new Date().getTime();
		System.out.println("Tardó en procesar: " + (fin - init) / 1000 + "segs");

	}

}
