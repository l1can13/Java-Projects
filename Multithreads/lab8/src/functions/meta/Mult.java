package functions.meta;

import functions.Function;

public class Mult implements Function{
    Function one, two;

    public Mult(Function fun1, Function fun2) {
        this.one = fun1;
        this.two = fun2;
    }

    public double getLeftDomainBorder() {return Math.max(one.getLeftDomainBorder(), two.getLeftDomainBorder());}
    public double getRightDomainBorder() {return  Math.min(one.getRightDomainBorder(), two.getRightDomainBorder());}

    public double getFunctionValue(double x) {return one.getFunctionValue(x) * two.getFunctionValue(x);}
}
