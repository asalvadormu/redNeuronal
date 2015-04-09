/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redneuronal;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author SAMUAN
 */
public class RedNeuronal {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        //coger archivo de entrenamiento
        //entrenar
        //devolver archivo con pesos entrenados.
        
        String archivoEntrada=args[0];
        String archivoSalida=args[1];
        
        File archivo=new File(archivoEntrada);
        String linea;
        LinkedList listaDatos=new LinkedList();
        try{
            BufferedReader br=new BufferedReader( new FileReader (archivo));
            while((linea=br.readLine())!=null){
                System.out.println(linea);
                String[] valores = linea.split(",");
                double[] valoresD=new double[12];
                for(int i=0;i<valores.length;i++){
                    valoresD[i]=Double.parseDouble(valores[i]);
                }
                listaDatos.add(valoresD);       
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        Red red=new Red();
        red.iniciarRed(1, 8, 7, 4);
        double[][] datosPaEntrena=new double[listaDatos.size()][12];
        for(int j=0;j<listaDatos.size();j++){
            datosPaEntrena[j]=(double[])listaDatos.get(j);
        }        
        red.setVector_datos(datosPaEntrena);
        red.entrenamiento();
        
        
        double[][] sinapsisA= red.getSinapsisA();
        double[][] sinapsisB= red.getSinapsisB();
        
        File archSalida=new File(archivoSalida);
      
        String textoArchivo="Pesos entrada capa oculta \r\n";
        for(int i=0;i<sinapsisA.length;i++) {
            for(int j=0;j<sinapsisA[0].length;j++){
                textoArchivo += ""+sinapsisA[i][j]+", " ;
            }
            textoArchivo += "\r\n";
        }
        textoArchivo += "Pesos capa oculta capa salida \r\n";
        for(int i=0;i<sinapsisB.length;i++) {
            for(int j=0;j<sinapsisB[0].length;j++){
                textoArchivo += ""+sinapsisB[i][j]+", " ;
            }
            textoArchivo += "\r\n";
        }
        
        
        //escritura en fichero nuevo
        try {
            PrintWriter writer = new PrintWriter(
                    new BufferedWriter(
                    new FileWriter(archSalida,false)));
            writer.println(textoArchivo);
            writer.close();  
        } catch (IOException ex) {
            ex.printStackTrace();
        }
            
                
    }
    
    
    
}
