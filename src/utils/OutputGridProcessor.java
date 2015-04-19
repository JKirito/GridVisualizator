package utils;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import entities.CeldaGrilla;

public class OutputGridProcessor {
	String pathFile;
	double anchoCelda;
	int cantX;
	int cantY;
	double rendMax;

	public OutputGridProcessor(String path, double anchoCelda) {
		this.pathFile = path;
		this.anchoCelda = anchoCelda;
	}

	public Integer getMinFila(Set<Integer> filas) {
		return Collections.min(filas);
	}

	public Integer getMinColumna(Set<Integer> columnas) {
		return Collections.min(columnas);
	}

	public CeldaGrilla[][] getCeldaGrilla() throws IOException {
		File file = new File(this.pathFile);
		StringSetCreator output = new StringSetCreator();

		Set<String> valores = output.getStringSet(file);
		Set<Double> rendimientos = new HashSet<Double>();
		Set<Integer> filas = new HashSet<Integer>();
		Set<Integer> columnas = new HashSet<Integer>();
		// Busco valores min y max de las filas y columnas
		for (String linea : valores) {
			String[] datos = datosXLinea(linea);
			int col = Integer.valueOf(datos[0].split(",")[0]);
			int fila = Integer.valueOf(datos[0].split(",")[1]);
			double rend = Double.valueOf(datos[1]);
			rendimientos.add(rend);
			filas.add(fila);
			columnas.add(col);
		}
		this.rendMax = Collections.max(rendimientos);

		Integer minFila = getMinFila(filas);
		Integer maxFila = getMaxFila(filas);
		Integer minCol = getMinColumna(columnas);
		Integer maxCol = getMaxColumna(columnas);
		System.out.println("minFila: " + minFila);
		System.out.println("maxFila: " + maxFila);
		System.out.println("minCol: " + minCol);
		System.out.println("maxCol: " + maxCol);

		// Limpio variables
		rendimientos = null;
		filas = null;
		columnas = null;

		// Busco cantidad de filas y cantidad de columnas que hay
		this.cantX = new Double(Math.floor((maxCol - minCol))).intValue();
		this.cantY = new Double(Math.floor((maxFila - minFila))).intValue();
		System.out.println("cantCeldasX: " + cantX);
		System.out.println("cantCeldasY: " + cantY);

		// Sumo 1. Empieza en 0.
		CeldaGrilla[][] grilla = new CeldaGrilla[cantX + 1][cantY + 1];

		// Resto a las filas minFilas y a las columnas minCol para empezar en
		// <0,0>
		for (String linea : valores) {
			String[] datos = datosXLinea(linea);
			int col = Integer.valueOf(datos[0].split(",")[0]) - minCol;
			int fila = Integer.valueOf(datos[0].split(",")[1]) - minFila;
			double rend = Double.valueOf(datos[1]);
			grilla[col][fila] = new CeldaGrilla(rend);
		}

		return grilla;
	}

	private Integer getMaxColumna(Set<Integer> columnas) {
		return Collections.max(columnas);
	}

	private Integer getMaxFila(Set<Integer> filas) {
		return Collections.max(filas);
	}

	private String[] datosXLinea(String linea) {
		linea = linea.replace("<", "").replace(">", "");
		return linea.split("	");
	}

	public int getCantCeldasX() {
		return cantX;
	}

	public int getCantCeldasY() {
		return cantY;
	}

	public double getRendMax() {
		return rendMax;
	}

	public void setCantX(int cantX) {
		this.cantX = cantX;
	}

	public void setCantY(int cantY) {
		this.cantY = cantY;
	}

	public void setRendMax(double rendMax) {
		this.rendMax = rendMax;
	}

}
