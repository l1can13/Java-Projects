package functions;

import java.io.Serializable;

public interface TabulatedFunction extends Function, Serializable, Cloneable {

    public Object clone() throws CloneNotSupportedException;

    /*public double getLeftDomainBorder();

    public double getRightDomainBorder();

    public double getFunctionValue(double x);*/

    public FunctionPoint getPoint(int index);

    public int getPointsCount();

    public void setPoint(int index,FunctionPoint point) throws FunctionPointIndexOutBoundsException, InappropriateFunctionPointException;

    public double getPointX(int index) throws FunctionPointIndexOutBoundsException;

    public void setPointX(int index, double x) throws FunctionPointIndexOutBoundsException, InappropriateFunctionPointException;

    public double getPointY(int index) throws FunctionPointIndexOutBoundsException;

    public void setPointY(int index, double y) throws FunctionPointIndexOutBoundsException;

    public void deletePoint(int index) throws FunctionPointIndexOutBoundsException, IllegalStateException;

    public void addPoint(FunctionPoint point) throws InappropriateFunctionPointException;






}

