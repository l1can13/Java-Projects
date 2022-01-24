package functions.meta;

import functions.Function;

public class Scale implements Function{
    private Function fun;
    private double X,Y;

    public Scale(Function Fun, double x, double y){
        this.fun = Fun;
        this.X = x;
        this.Y = y;
    }

    public double getLeftDomainBorder(){
        if(X >= 0){
            return X * fun.getLeftDomainBorder();
        }
        else{
            return fun.getLeftDomainBorder() / X;
        }
    }
    public double getRightDomainBorder(){
        if (X >= 0) {
            return X * fun.getRightDomainBorder();
        }
        else{
            return fun.getRightDomainBorder() / X;
        }
    }

    public double getFunctionValue(double x){
        if(Y >= 0){
            return Y * fun.getFunctionValue(x/X);
        }
        else{
            return fun.getFunctionValue(x/X) / Y;
        }
    }
}
