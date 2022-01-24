package functions.meta;

import functions.Function;

public class Shift implements Function{
    private Function fun;
    private double X,Y;

    public Shift(Function Fun, double x, double y){
        this.fun = Fun;
        this.X = x;
        this.Y = y;
    }

    public double getLeftDomainBorder() {return  X + fun.getLeftDomainBorder();}
    public double getRightDomainBorder() {return  X + fun.getRightDomainBorder();}

    public double getFunctionValue(double x) {return  Y + fun.getFunctionValue(x-X);}
}
