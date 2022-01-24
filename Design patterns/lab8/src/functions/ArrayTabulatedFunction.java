package functions;
import functions.FunctionPoint;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayTabulatedFunction implements TabulatedFunction, Serializable, Cloneable {

    private FunctionPoint points[];
    private int count;

    private int k;

    public static class ArrayTabulatedFunctionFactory implements TabulatedFunctionFactory {

        public TabulatedFunction createTabulatedFunction(FunctionPoint[] points) {
            return new ArrayTabulatedFunction(points);
        }

        public TabulatedFunction createTabulatedFunction(double leftX, double rightX, int pointsCount) {
            return new ArrayTabulatedFunction(leftX, rightX, pointsCount);
        }

        public TabulatedFunction createTabulatedFunction(double leftX, double rightX, double[] values) {
            return new ArrayTabulatedFunction(leftX, rightX, values);
        }

    }

    public Iterator<FunctionPoint> iterator() {
        return new Iterator<FunctionPoint>() {

            private int currentIndex = 0;

            public boolean hasNext() {
                return currentIndex < count;
            }

            public FunctionPoint next() {
                if (currentIndex >= count) {
                    throw new NoSuchElementException();
                }
                return new FunctionPoint(points[currentIndex++]);
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    public ArrayTabulatedFunction(double leftX, double rightX, int pointsCount) throws IllegalArgumentException
    {
        if (leftX >= rightX || pointsCount < 2)
            throw new IllegalArgumentException("The left border is greater than the right border or the number of points is less than 2");

        points = new FunctionPoint[pointsCount];
        double line = (rightX - leftX) / (pointsCount - 1);
        for(int i = 0; i < pointsCount; ++i){
            points[i] = new FunctionPoint(leftX + line * i, 0);
        }
        k = pointsCount;
    }

    public ArrayTabulatedFunction(double leftX, double rightX, double[] values) throws IllegalArgumentException
    {
        if (leftX >= rightX || values.length < 2)
            throw new IllegalArgumentException("The left border is greater than the right border or the number of points is less than 2");
        points = new FunctionPoint[values.length];
        double line = (rightX - leftX) / (values.length - 1);
        for (int i = 0; i < values.length; ++i) {
            points[i] = new FunctionPoint(leftX + line * i, values[i]);
        }
        k = values.length;
    }

    public ArrayTabulatedFunction(FunctionPoint[] Arr) throws IllegalArgumentException
    {
        if(Arr.length < 2)
            throw new IllegalArgumentException("number of points is less than 2");

        k = Arr.length;

        for(int i = 1; i<Arr.length; ++i){
            if(Arr[i-1].x >= Arr[i].x)
                throw new IllegalArgumentException("Exception: Incorrect border");
        }
        points = new FunctionPoint[Arr.length + Arr.length/2];
        System.arraycopy(Arr, 0, points, 0, Arr.length);
    }

    public String toString(){
        StringBuilder buffer = new StringBuilder("{");
        for(int i = 0; i < this.k; ++i){
            buffer.append(this.points[i].toString()).append(", ");
        }
        buffer.deleteCharAt(buffer.length()-1).deleteCharAt(buffer.length()-1);
        buffer.append("}");
        return buffer.toString();
    }

    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (o instanceof TabulatedFunction) {
            if (o instanceof ArrayTabulatedFunction) {
                ArrayTabulatedFunction func = (ArrayTabulatedFunction) o;
                if (func.k != this.k) {
                    return false;
                }
                for (int i = 0; i < k; i++) {
                    if (func.points[i].x != this.points[i].x) {
                        return false;
                    }
                    if (func.points[i].y != this.points[i].y) {
                        return false;
                    }
                }
                return true;
            } else {
                TabulatedFunction func = (TabulatedFunction) o;
                if (func.getPointsCount() != this.k) {
                    return false;
                }
                for (int i = 0; i < this.k; i++) {
                    if (!(this.getPoint(i).equals(func.getPoint(i)))) {
                        return false;
                    }
                }
                return true;
            }
        } else {
            return false;
        }

    }

    public int hashCode() {
        int hash = 17;
        /*hash = 37 * hash + points.hashCode();*/
        hash = 37 * hash + Arrays.deepHashCode(this.points);
        hash = 37 * hash + this.k;
        return hash;
    }

    public Object clone() throws CloneNotSupportedException {
        FunctionPoint points[] = new FunctionPoint[k];
        for (int i = 0; i < this.k; i++) {
            points[i] = (FunctionPoint) this.points[i].clone();

        }
        return new ArrayTabulatedFunction(points);
    }

    public double getLeftDomainBorder() {return this.points[0].x;}

    public double getRightDomainBorder() {return this.points[this.getPointsCount()-1].x;}

    public double getFunctionValue(double x) {
        if (x >= points[0].x && x <= points[k-1].x){
            return ((x - points[0].x)*(points[k-1].y - points[0].y))/(points[k-1].x - points[0].x) + points[0].y;
        }
        return Double.NaN;
        /*if (x >= this.getLeftDomainBorder() && x <= this.getRightDomainBorder()) {
            int end = this.getPointsCount();
            int begin = 0;
            int middle = (begin + end) / 2;
            if (points.length == 0 || points[0].x > x || points[this.getPointsCount() - 1].x < x) {
                return Double.NaN;
            }
            while (begin < end) {
                if (x <= points[middle].x) {
                    end = middle;
                }
                else {
                    begin = middle + 1;
                }
                middle = (begin + end) / 2;
            }
            if (points[end].x == x) { //если введеный x равен найденому, то выводим значение функции из массива
                return points[end].y;
            }
            else
            {
                double k = (points[end].y - points[end - 1].y) / (points[end].x - points[end - 1].x);
                double b = points[end].y - k * points[end].x;
                return k * x + b; //находим уравнение прямой и возвращаем значение в точке
            }
        } else
        {
            return Double.NaN;
        }*/
    }

    public int getPointsCount() {return k;}

    public FunctionPoint getPoint(int index) throws FunctionPointIndexOutBoundsException // -1>
    {
        if(index > points.length-1 || index < 0)
            throw  new FunctionPointIndexOutBoundsException("GetPoint Err");
        return this.points[index];
    }

    public void setPoint(int index, FunctionPoint point)  throws FunctionPointIndexOutBoundsException, InappropriateFunctionPointException
    {
        if (index < 0 || index > k)
            throw new FunctionPointIndexOutBoundsException("setPoint Err");
        if (point.x < this.points[0].x || point.x > this.points[points.length - 1].x)
            throw new InappropriateFunctionPointException("setPoint exception: Out of X Bounds");
        this.points[index].x = point.x;
        this.points[index].y = point.y;
    }

    public double getPointX(int index) throws FunctionPointIndexOutBoundsException
    {
        if (index > points.length - 1 || index < 0)
            throw new FunctionPointIndexOutBoundsException("getPointX Err");
        return this.points[index].x;
    }

    public void setPointX(int index, double x) throws FunctionPointIndexOutBoundsException, InappropriateFunctionPointException
    {
        if (index < 0 || index > points.length - 1)
            throw new FunctionPointIndexOutBoundsException("setPointX exception");
        if (x < points[0].x || x > points[points.length - 1].x )
            throw new InappropriateFunctionPointException("setPointX exception: Out of X Bounds");
        points[index].x = x;

        for (int i = this.points.length - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                if (this.points[j].x > this.points[j + 1].x) {
                    FunctionPoint tmp = this.points[j];
                    this.points[j] = this.points[j + 1];
                    this.points[j + 1] = tmp;
                }
            }
        }
    }

    public double getPointY(int index) throws FunctionPointIndexOutBoundsException
    {
        if (index < 0 || index > points.length -1)
            throw new FunctionPointIndexOutBoundsException("getPointY exception");

        return new FunctionPoint(this.points[index]).y;
    }

    public void setPointY(int index, double y) throws FunctionPointIndexOutBoundsException
    {
        if (index < 0 || index > points.length -1)
            throw new FunctionPointIndexOutBoundsException("setPointY exception");

        this.points[index].y = y;

    }

    public void deletePoint(int index) throws FunctionPointIndexOutBoundsException, IllegalStateException
    {
        if(points.length < 3)
            throw new IllegalStateException("deletePoint exception: number of points less then 3");
        if (index < 0 || index > points.length -1)
            throw new FunctionPointIndexOutBoundsException("deletePoint exception");
        System.arraycopy(this.points, index, this.points, index - 1, this.getPointsCount() - index);
        --k;

    }

    public void addPoint(FunctionPoint point) throws InappropriateFunctionPointException
    {
        for(int i = 0; i < points.length; ++i) {
            if(point.x == points[i].x)
                throw new InappropriateFunctionPointException("addPoint exception: X is already in the set");
        }

        FunctionPoint[] ArrTemp = new FunctionPoint[this.points.length+1];

        System.arraycopy(this.points,0,ArrTemp,0,this.points.length);

        ArrTemp[ArrTemp.length-1]=point;
        this.points = ArrTemp;

        for (int i = this.points.length - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                if (this.points[j].x > this.points[j + 1].x) {
                    FunctionPoint tmp = this.points[j];
                    this.points[j] = this.points[j + 1];
                    this.points[j + 1] = tmp;
                }
            }
        }
        ++k;
    }

}
