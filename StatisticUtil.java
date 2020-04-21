package br.com.codenation;

import java.lang.Math;

public class StatisticUtil {
	
	public static int average(int[] elements) {
		int soma = 0;
		for (int i=0; i<elements.length; i++) {
			soma+=elements[i];
		}
		int media = Math.round(soma/elements.length);
		return media;
	}

	public static int mode(int[] elements) {
		int contadorFinal = 0;
		int moda = 0;
		for (int i = 0; i < elements.length; i++) {
	        int contador = 0;
	        for (int j = 0; j < elements.length; j++) {
	            if (elements[j] == elements[i]) {
	            	contador++;
	            }
	        } 
	        if (contador > contadorFinal) {
	            contadorFinal = contador;
	            moda = elements[i];
	        }
	    }
		return moda;
	}

	public static int median(int[] elements) {
		ordenarNumeros(elements);
		int mediana;
		int div = Math.round(elements.length/2);
		if (elements.length%2 != 0) {
			mediana = elements[div];
		}else {
			mediana = Math.round((elements[div-1] + elements[div])/2);
		}
		return mediana;
	}
	
	public static int[] ordenarNumeros(int[] elements) {
		int i, j, n;
		int aux = elements.length-1;
	    	for (i = 0; i <= aux; i++){
	      		for (j = 0; j < (aux - i); j++){
	        		if (elements[j] > elements[j+1]){
	         			n = elements[j];
	          			elements[j] = elements[j+1];
	          			elements[j+1] = n;
	        		}
	      		}
	    	}
	    	return elements;
	}
	
}
