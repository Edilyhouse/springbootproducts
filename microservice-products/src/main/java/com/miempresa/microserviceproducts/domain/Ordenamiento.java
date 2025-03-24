package com.miempresa.microserviceproducts.domain;

import java.util.Arrays;  // Necesario para Arrays.toString()

public class Ordenamiento {

    public static void main(String[] args) {
        // Definición del arreglo
        int[] arr = {64, 34, 25, 12, 22, 11, 900};
        int longitud = arr.length;

        // Mostrar arreglo sin ordenar
        System.out.println("Arreglo sin ordenar: " + Arrays.toString(arr));

        // Algoritmo de ordenamiento (burbuja)
        for (int i = 0; i < longitud - 1; i++) {
            for (int j = 0; j < longitud - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    // Intercambiar los elementos
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }

        // Mostrar arreglo ordenado
        System.out.println("Arreglo ordenado: " + Arrays.toString(arr));
    }
}
