package functions.meta;

import functions.Function;

public class Composition implements Function {

    private Function func1, func2;

    public Composition(Function one, Function two) {
        this.func1 = one;
        this.func2 = two;
    }

    public double getLeftDomainBorder() {
        return func1.getLeftDomainBorder();
    }

    public double getRightDomainBorder() {
        return func1.getRightDomainBorder();
    }

    public double getFunctionValue(double x) {
        return func1.getFunctionValue(func2.getFunctionValue(x));
    }

}
