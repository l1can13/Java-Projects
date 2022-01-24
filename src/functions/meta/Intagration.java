package functions.meta;

import functions.Function;

public class Intagration implements Function {

    private Function f;
    private double a, b;

    public Intagration(Function func, double A, double B) {
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

    public double intagrate(Function func, double a, double b) {
        double area = 0.0;
        double e = 0.01;

        for (int i = 0; i < (b - a) / e; ++i) {
            area += e * (0.5 * (func.getFunctionValue(a + i * e) + func.getFunctionValue(a + (i + 1) * e)));
        }

        return area;
    }
}
