/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redneuronal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

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
        
        try{
            BufferedReader br=new BufferedReader( new FileReader (archivo));
            while((linea=br.readLine())!=null){
                System.out.println(linea);
                String[] valores = linea.split(",");
                double[] valoresD=new double[12];
                for(int i=0;i<valores.length;i++){
                    valoresD[i]=Double.parseDouble(valores[i]);
                }
                
                           
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        
    }
    
    
    
}
