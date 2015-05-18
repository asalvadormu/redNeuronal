package redneuronal;
/**
 *
 * @author SAMUAN
 */
public class FuncionIdentidad implements IFuncionActivacion{

    @Override
    public double[] activar(double[] val) {
        return val;
    }    
    
}
