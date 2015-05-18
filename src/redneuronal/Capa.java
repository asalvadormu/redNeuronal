package redneuronal;

/**
 * Representa a una capa, constituida por la matriz de pesos y la función de activación.
 * Se pueden crear objetos tanto para cálculos como para entrenamiento.
 * 
 * @author SAMUAN
 */
public class Capa {
    private double[] vector_entrada; //entrada a la capa
    private double[] vector_parcial; //calculo de pesos
    private double[] vector_salida; // salida de la capa

    private double[][] sinapsis; //relaciona la capa anterior con esta. i número nodos de esta capa j número nodos capa anterior
    private double[] bias;
    private IFuncionActivacion funcion; //funcion de activación que se utiliza en esta capa.
    
    private double[] vector_error_propio; //vectores de errores para backpropagation
    private double[] vector_error_anterior;
     
    /**
     * Constructor para cálculos.
     */ 
    public Capa(double[][] sinapsis, double[] bias, IFuncionActivacion funcion) {
        this.sinapsis = sinapsis;
        this.funcion = funcion;
        this.bias= bias;
        
        vector_parcial=new double[sinapsis.length];
        vector_salida=new double[sinapsis.length];
        
        vector_error_propio=new double[ sinapsis.length  ]; //un error por cada nodo de esta capa
        vector_error_anterior=new double[ sinapsis[0].length ]; //un error por cada nodo de la red anterior.
    }

    /**
     * Constructor para entrenamiento
     */ 
    public Capa(int cuantasNeuronasAnterior, int cuantasNeuronas, IFuncionActivacion funcion) {
       this.sinapsis=new double[cuantasNeuronas][cuantasNeuronasAnterior];
       this.bias=new double[cuantasNeuronas];
       this.funcion=funcion;
       
       vector_entrada=new double[sinapsis[0].length];
       vector_parcial=new double[sinapsis.length];
       vector_salida=new double[sinapsis.length];
        
       vector_error_propio=new double[ sinapsis.length  ]; //un error por cada nodo de esta capa
       vector_error_anterior=new double[ sinapsis[0].length ];
    }
        
    /**
     * Realiza el cálculo de pesos por entrada y aplica la función de activación.
     * 
     */
    public void calcular(){
        calculoSumatorioPesos();
        calculoActivacion();
    }
    
    /**
     * Realiza la  operación de sumatorio de pesos por valores.
     */ 
    private void calculoSumatorioPesos(){
        double suma=0;
        for(int i=0;i<sinapsis.length;i++){ //i es cada neurona de esta capa
            suma=0;
            for(int j=0;j<sinapsis[i].length;j++){ //j es el número de entradas que llegan a la neurona.
                suma += vector_entrada[j]*sinapsis[i][j];
            }
            suma=suma+bias[i];
            vector_parcial[i]=suma;          
        }   
    }
    
    /**
     * Realiza la operación de la función de activación.
     * Le paso el vector con los valores z y me devuelve el vector con los valores a.
     */
    private void calculoActivacion(){
        vector_salida=funcion.activar(vector_parcial);
    }
    
    
    /**
     * Modifica los pesos de esta capa.
     * 
     */
    public void modificarPesos(){     
        //modifica pesos entre neuronas
        for(int i=0;i<sinapsis.length;i++){
            for(int j=0;j<sinapsis[0].length;j++){
                sinapsis[i][j]=(Red.getMomento()*sinapsis[i][j])+(Red.getRatioAprendizaje()*vector_error_propio[i]*vector_entrada[j]);
            }
        }
        //modifica bias
        for(int i=0;i<bias.length;i++){
            bias[i]=(Red.getMomento()*bias[i])+(Red.getRatioAprendizaje()*vector_error_propio[i]*1);
        }
    }
    
    /**
     * Calcula el error de la capa anterior propagando hacia atras el error de esta capa.
     * δA = outA (1 – outA) (δα WAα + δβ WAβ)
     * δB = outB (1 – outB) (δα WBα + δβ WBβ)
     * δC = outC (1 – outC) (δα WCα + δβ WCβ)
     */
    public double[] calculaErrorAnterior(){
        double suma=0;
        for(int i=0;i<sinapsis[0].length;i++){ //para cada neurona  de capa anterior    
            suma=0;
            for(int j=0;j<sinapsis.length;j++) { //calculo la aportación de error a cada neurona de destino. Por eso lo recorro en columna.
                suma=suma+vector_error_propio[j]*sinapsis[j][i];
            }            
            vector_error_anterior[i]=vector_entrada[i]*(1-vector_entrada[i])*suma;
        }
        return vector_error_anterior;
    }
    
    /**
     * Inicia las sinapsis con valores aleatorios entre -0.5 y 0.5
     */
    void iniciarSinapsis() {
        for(int i=0;i<sinapsis.length;i++){
            for(int j=0;j<sinapsis[0].length;j++){
                sinapsis[i][j]=Math.random()-0.5;
            }
        }
    }
    
    /**
     * Inicia los valores de bias con valores aleatorios entre -0.5 y 0.5
     */ 
    void iniciarBias(){
        for(int i=0;i<bias.length;i++){
            bias[i]=Math.random()-0.5;
        }
    }
    
    /************   GETTER AND SETTER *******************/

    public double[] getVector_entrada() {
        return vector_entrada;
    }

    public void setVector_entrada(double[] vector_entrada) {
        this.vector_entrada = vector_entrada;
    }

    public double[] getVector_parcial() {
        return vector_parcial;
    }

    public void setVector_parcial(double[] vector_parcial) {
        this.vector_parcial = vector_parcial;
    }

    public double[] getVector_salida() {
        return vector_salida;
    }

    public void setVector_salida(double[] vector_salida) {
        this.vector_salida = vector_salida;
    }

    public double[][] getSinapsis() {
        return sinapsis;
    }

    public void setSinapsis(double[][] sinapsis) {
        this.sinapsis = sinapsis;
    }

    public IFuncionActivacion getFuncion() {
        return funcion;
    }

    public void setFuncion(IFuncionActivacion funcion) {
        this.funcion = funcion;
    }

    public double[] getVector_error_propio() {
        return vector_error_propio;
    }

    public void setVector_error_propio(double[] vector_error_propio) {
        this.vector_error_propio = vector_error_propio;
    }

    public double[] getVector_error_anterior() {
        return vector_error_anterior;
    }

    public void setVector_error_anterior(double[] vector_error_anterior) {
        this.vector_error_anterior = vector_error_anterior;
    }

    public double[] getBias() {
        return bias;
    }

    public void setBias(double[] bias) {
        this.bias = bias;
    }
    
    
    
    /**************** IMPRIMIR *********************************/
    
    public void imprimirSinapsis(){
        for(int i=0;i<sinapsis.length;i++) {
            String linea="";
            for (int j = 0; j < sinapsis[0].length; j++) {
                linea += " "+sinapsis[i][j];
            }
            System.out.println("SINAPSIS "+linea);
        }
    }
    
    public void imprimirVector(double[] vec){
        for(double dat:vec) System.out.print(dat+" ");
        System.out.println();
    }

  

  
}
