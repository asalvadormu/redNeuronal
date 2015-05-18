package redneuronal;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.ListIterator;

/**
 *
 * @author SAMUAN
 */
public class RedNeuronal {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //coger archivo de entrenamiento
        //entrenar
        //devolver archivo con pesos entrenados.
        
        String archivoEntrada=args[0];
        String archivoSalida=args[1];
        
        int lonEntrada=8;   //8 7 4     // 8 7 13 4
        int lonSalida=4; 
        double ratioAprendizaje=0.01;
        double momento=1;
        int ciclosAEntrenar=3000;
        
        long tiempoAntes=0;
        long tiempoDespues=0;
        
        File archivo=new File(archivoEntrada);
        String linea;
        LinkedList listaDatos=new LinkedList();
        try{
            BufferedReader br=new BufferedReader( new FileReader (archivo));
            while((linea=br.readLine())!=null){
                //System.out.println(linea);
                
                if(!linea.trim().equals("") && !linea.contains("//")  ){ //para evitar lineas vacias 7 comentarios
                  
                    String[] valores = linea.split(",");
                    double[] valoresD=new double[valores.length];
                    for(int i=0;i<valores.length;i++){
                        valoresD[i]=Double.parseDouble(valores[i]);
                    }
                    listaDatos.add(valoresD);   
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        //construcción de la red inicial.
        Red red=new Red( momento,ratioAprendizaje,ciclosAEntrenar );
        red.agregarCapa(8,7,new FuncionSigmoidal());
        red.agregarCapa(7,13,new FuncionSigmoidal());
        red.agregarCapa(13,4,new FuncionSoftmax());
        
        //añadido de datos de muestra con los que realizar el entrenamiento.
        double[][] datosPaEntrena=new double[listaDatos.size()][lonEntrada+lonSalida];
        for(int j=0;j<listaDatos.size();j++){
            datosPaEntrena[j]=(double[])listaDatos.get(j);
        }        
        red.setVector_datos(datosPaEntrena);        
        
        //proceso de entrenamiento
        tiempoAntes=System.currentTimeMillis();
        red.entrenar();        
        tiempoDespues=System.currentTimeMillis();
        
        System.out.println("tiempo ejecucion: "+(tiempoDespues-tiempoAntes));
        
        
        String textoArchivo="";
        LinkedList lascapas=red.lista;
        ListIterator iterador = lascapas.listIterator();
        int contador=0;
        while(iterador.hasNext()){
            contador++;
            Capa capa= (Capa)iterador.next();
            double[][] sinap=capa.getSinapsis();          
            textoArchivo += "#Pesos de la capa "+contador;
            textoArchivo += "\r\n";
            textoArchivo += "DATA"+contador;
            textoArchivo += "\r\n";
            for(int i=0;i<sinap.length;i++) {
                for(int j=0;j<sinap[0].length;j++){
                    textoArchivo += ""+sinap[i][j];
                    if(j<sinap[0].length-1) textoArchivo +=", ";
                }
                textoArchivo += "\r\n";
            }
            textoArchivo += "\r\n";
        }
        
        iterador=lascapas.listIterator();
        int contadorcapa=0;
        while(iterador.hasNext()){
            contador++; contadorcapa++;
            Capa capa= (Capa)iterador.next();
            double[] bias =capa.getBias();
            textoArchivo += "\r\n";
            textoArchivo += "Bias de la capa "+contadorcapa;
            textoArchivo += "\r\n";
             textoArchivo += "DATA"+contador;
            textoArchivo += "\r\n";
            for(int k=0;k<bias.length;k++){
                textoArchivo += bias[k];
                if(k<bias.length-1) textoArchivo +=", ";
            }
            textoArchivo += "\r\n";
        }       
        
       
        File archSalida=new File(archivoSalida);
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
