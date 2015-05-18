package redneuronal;

/**
 * Ejecuta la función softmax como función de activación.
 * @author SAMUAN
 */
public class FuncionSoftmax implements IFuncionActivacion {

    @Override
    public double[] activar(double[] val) {
        double[] respuesta=new double[val.length];
        double suma=0;
        for(int i=0;i<val.length;i++){
            suma=suma+Math.pow(Math.E,val[i]);              
        }
        for(int i=0;i<val.length;i++){
            respuesta[i]=Math.pow(Math.E,val[i])/suma;
        }
        return respuesta;
    }
    
    
}
