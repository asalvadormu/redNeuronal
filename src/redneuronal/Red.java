package redneuronal;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.ListIterator;

/**
 * Clase para representar redes neuronales, perceptrones multicapa.
 * 
 * La cantidad de capas y la cantidad de neuronas por capa son configurables.
 * 
 * Para cálculo y entrenamiento.
 * 
 * @author SAMUAN
 */
public class Red {
    
    LinkedList<Capa> lista=new LinkedList() ;
    double[] vectorEn;
    
    private static double momento;
    private static double ratioAprendizaje;
    
    private int epocas;
    private double[][] vector_datos;
    
    private double[] target;
    
    private int entradas;
    private int salidas;
    
    private double ep2; //error cuadrático medio, para cada muestra.
    private double et; //error total en cada ciclo
 
    public Red(){}
    
    /**
     * Constructor para entrenamiento de la red.
     */ 
    public Red(double momento, double ratioAprendizaje, int epocas){
        this.momento=momento;
        this.ratioAprendizaje=ratioAprendizaje;
        this.epocas=epocas;        
    }
    
    
    /** 
     * Calcula el valor de salida para una entrada concreta.
     * 
     */ 
    public void calcular() {               
        for(Capa capa:lista ){            
            capa.setVector_entrada(vectorEn);                        
            capa.calcular();            
            vectorEn=capa.getVector_salida();                     
        }
    }
    
    /**
     * Entrena la red a partir de una lista de muestras.
     * 
     */ 
    public void entrenar(){
        iniciarCapas();       
        for( int ciclo=0;ciclo<epocas;ciclo++){ 
            et=0;
            LinkedList listaMuestras=new LinkedList( Arrays.asList(vector_datos) );           
            while(listaMuestras.size()>0){
                int cual=(int) (Math.random() * listaMuestras.size()); //extraer muestra al azar
                double[] vector=(double[])listaMuestras.remove(cual);
                for(int j=0;j<vector.length;j++){
                     if(j<entradas){ //dividirla en entrada y target
                        vectorEn[j]=vector[j];
                    }else{
                        target[j-entradas]=vector[j];
                    }
                }  
                
                //calcular valor de salida
                double[] vEntrada=vectorEn;
                for(Capa capa:lista){
                    capa.setVector_entrada(vEntrada);
                    capa.calcular();
                    vEntrada=capa.getVector_salida();
                }
               
                //calcular error salida
                double[] errores=new double[vEntrada.length];
                for(int i=0;i<errores.length;i++){
                    errores[i]=target[i]-vEntrada[i];
                }
                
                //calcular error cuadrático medio.
                double ep=0;
                for(int i=0;i<errores.length;i++){
                   ep += Math.pow(errores[i],2);
                }
                ep2=ep/2;
                //System.out.println("error cuadratico medio = "+ep2);
                et +=ep2;
                
                //propagar error hacia atras    
                ListIterator iter=lista.listIterator(lista.size());
                while(iter.hasPrevious()){
                    Capa capa=(Capa)iter.previous();
                    capa.setVector_error_propio(errores);
                    //capa.modificarPesos();//<---
                    errores=capa.calculaErrorAnterior();            
                }
                
                //modificar pesos
                for(Capa capa:lista){
                    capa.modificarPesos();
                }
                
            }
            System.out.println("error total: "+et);
        }
      
    }
    
    /**
     * Método de inicio de valores para utilizar en entrenamiento.
     */ 
    private void iniciarCapas(){
        for(Capa capa:lista ){               
            capa.iniciarSinapsis();  
            capa.iniciarBias();
        }
    }
    
    /**
     *  Método para agregar capas cuando se va a realizar un calculo de red.
     */ 
    public void agregarCapa(double[][] sinapsis, double[] bias, IFuncionActivacion funcion){
        Capa capa=new Capa(sinapsis,bias, funcion);
        capa.imprimirSinapsis();
        lista.add( capa );
    }
    
    /**
     * Método para agragar capas cuando se va a realizar un entrenamiento de la red.
     */ 
    public void agregarCapa(int cuantasNeuronasAnterior, int cuantasNeuronas, IFuncionActivacion funcion){
        Capa capa=new Capa(cuantasNeuronasAnterior,cuantasNeuronas,funcion);
        lista.add(capa);
        
        if(lista.size()==1){ //si solo hay una es la de entrada.
            this.entradas=cuantasNeuronasAnterior;
            vectorEn=new double[entradas];
        }
        
        this.salidas=cuantasNeuronas; //para la que se añade modificar la salida.
        target=new double[salidas];
            
    }

    /******************** GETTER AND SETTER ****************************/
    
    public double[] getVectorEn() {
        return vectorEn;
    }

    public void setVectorEn(double[] vectorEn) {
        this.vectorEn = vectorEn;
    }
 
    public static double getMomento() {
        return momento;
    }

    public static double getRatioAprendizaje() {
        return ratioAprendizaje;
    }
    
    public void setVector_datos(double[][] vector_datos) {
        this.vector_datos = vector_datos;
    }

    public int getEntradas() {
        return entradas;
    }

    public void setEntradas(int entradas) {
        this.entradas = entradas;
    }

    public int getSalidas() {
        return salidas;
    }

    public void setSalidas(int salidas) {
        this.salidas = salidas;
    }
    
    
    /************* IMPRIMIR **************/

    public void imprimirVector(double[] vec){
        for(double dat:vec) System.out.print(dat+" ");
        System.out.println();
    }
    
    
}
