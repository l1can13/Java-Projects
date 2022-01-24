package functions.meta;

import functions.Function;

public class Power implements Function{
    private Function fun;
    private double deg;

    public Power(Function Fun, double Deg){
        this.fun = Fun;
        this.deg = Deg;
    }

    public double getLeftDomainBorder() {return fun.getLeftDomainBorder();}
    public double getRightDomainBorder() {return fun.getRightDomainBorder();}

    public double getFunctionValue(double x) {return Math.pow(fun.getFunctionValue(x), deg);}
}
