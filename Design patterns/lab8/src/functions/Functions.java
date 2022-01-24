package functions;

import functions.meta.*;

public class Functions {

    private Functions() {}

    public static Function shift(Function f, double shiftX, double shiftY) { return new Shift(f, shiftX, shiftY); }

    public static Function scale(Function f, double scaleX, double scaleY) { return new Scale(f, scaleX, scaleY); }

    public static Function power(Function f, double power) { return new Power(f,power); }

    public static Function sum(Function f1, Function f2) { return new Sum(f1,f2); }

    public static Function mult(Function f1, Function f2) { return new Mult(f1,f2); }

    public static Function composition(Function f1, Function f2) { return new Composition(f1,f2); }

    public static double integration(Function func, double a, double b, double step) {
        if (a < func.getLeftDomainBorder() || b > func.getRightDomainBorder()) {
            throw new IllegalArgumentException();
        }
        double area = 0.0;

        for (int i = 0; i < (b - a) / step; ++i) {
            area += step * (0.5 * (func.getFunctionValue(a + i * step) + func.getFunctionValue(a + (i + 1) * step)));
        }

        return area;
    }

}
