package functions.meta;

import functions.Function;

public class Integration implements Function {

    private Function f;
    private double a, b;

    public Integration(Function func, double A, double B) {
        this.f = func;
        this.a = A;
        this.b = B;
    }

    public double getLeftDomainBorder() {
        return f.getLeftDomainBorder();
    }

    public double getRightDomainBorder() {
        return f.getRightDomainBorder();
    }

    public double getFunctionValue(double x) {
        return f.getFunctionValue(x);
    }
}
