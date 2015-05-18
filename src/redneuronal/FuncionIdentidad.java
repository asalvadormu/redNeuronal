package redneuronal;
/**
 * Ejecuta la función identidad como función de activación.
 * Devuelve el mismo valor que le llega.
 * @author SAMUAN
 */
public class FuncionIdentidad implements IFuncionActivacion{

    @Override
    public double[] activar(double[] val) {
        return val;
    }    
    
}
