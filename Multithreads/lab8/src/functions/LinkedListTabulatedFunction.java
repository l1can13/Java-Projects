package functions;

import java.io.Serializable;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class LinkedListTabulatedFunction implements TabulatedFunction, Serializable, Cloneable {

    public static class LinkedListTabulatedFunctionFactory implements TabulatedFunctionFactory {

        public TabulatedFunction createTabulatedFunction(FunctionPoint[] points) {
            return new LinkedListTabulatedFunction(points);
        }

        public TabulatedFunction createTabulatedFunction(double leftX, double rightX, int pointsCount) {
            return new LinkedListTabulatedFunction(leftX, rightX, pointsCount);
        }

        public TabulatedFunction createTabulatedFunction(double leftX, double rightX, double[] values) {
            return new LinkedListTabulatedFunction(leftX, rightX, values);
        }
    }

    public Iterator<FunctionPoint> iterator() {
        return new Iterator<FunctionPoint>() {
            private FunctionNode node = head;

            public boolean hasNext() {
                return node != headMain;
            }

            public FunctionPoint next() {
                if (node == headMain) {
                    throw new NoSuchElementException();
                }
                FunctionNode result = node;
                node = node.next;
                return (FunctionPoint) result.point.clone();
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    private class FunctionNode {
        private FunctionPoint point = null;
        private FunctionNode prev = null, next = null;
    }

    private int size, currentIndex;
    private FunctionNode headMain = new FunctionNode(), head, tail, current; {
        headMain.next = headMain;
        headMain.prev = headMain;
        head = headMain;
        tail = headMain;
        current = headMain;
    }

    public LinkedListTabulatedFunction(double leftX, double rightX, int pointsCount) throws IllegalArgumentException
    {
        if (leftX >= rightX || pointsCount < 2) {
            throw new IllegalArgumentException("The left border is greater than the right border or the number of points is less than 2");
        }

        head = new FunctionNode();
        headMain.next = head;
        current = head;
        head.prev = headMain;

        double line = (rightX - leftX) / (pointsCount - 1);
        double X = leftX;

        for (int i = 0; i < pointsCount; i++) {
            current.point = new FunctionPoint(X, 0);
            current.next = new FunctionNode();
            current.next.prev = current;
            current = current.next;
            currentIndex++;
            X += line;
        }

        size = pointsCount;
        tail = current.prev;
        tail.next = headMain;
        headMain.prev = tail;
    }

    public LinkedListTabulatedFunction(double leftX, double rightX, double values[]) throws IllegalArgumentException
    {
        if (leftX >= rightX && values.length < 2) {
            throw new IllegalArgumentException("The left border is greater than the right border or the number of points is less than 2");
        }

        head = new FunctionNode();
        headMain.next = head;
        current = head;
        head.prev = null;

        double line = (rightX - leftX) / (values.length - 1);
        double X = leftX;

        for (int i = 0; i < values.length; i++) {
            current.point = new FunctionPoint(X, values[i]);
            current.next = new FunctionNode();
            current.next.prev = current;
            current = current.next;
            currentIndex++;
            X += line;
        }

        size = values.length;
        tail = current.prev;
        tail.next = headMain;
    }

    public LinkedListTabulatedFunction(FunctionPoint[] Arr) throws IllegalArgumentException
    {
        if (Arr.length < 2)
            throw new IllegalArgumentException();


        for (int i = 1; i < Arr.length; ++i)
        {

            if (Arr[i - 1].x >= Arr[i].x)
            {
                headMain.next = headMain;
                headMain.prev = headMain;
                head = headMain;
                tail = headMain;
                current = headMain;

                throw new IllegalArgumentException();

            }
            else
            {
                current.next = new FunctionNode();
                current.next.prev = current;
                current.next.point = new FunctionPoint(Arr[i - 1].x, Arr[i - 1].y);
                current = current.next;
            }
        }
        current.next = new FunctionNode();
        current.next.prev = current;
        current.next.point = new FunctionPoint(Arr[Arr.length-1].x, Arr[Arr.length-1].y);
        current = current.next;
        size = Arr.length;
        head = headMain.next;
        headMain.prev = current;
        tail = current;
        tail.next = headMain;
    }

    public String toString() {
        StringBuilder buffer = new StringBuilder("{");

        currentIndex = 0;
        current = head;

        while (current != headMain) {
            buffer.append(current.point.toString()).append(", ");
            currentIndex++;
            current = current.next;
        }

        buffer.deleteCharAt(buffer.length() - 1).deleteCharAt(buffer.length() - 1);
        buffer.append("}");
        return buffer.toString();
    }

    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (o instanceof TabulatedFunction) {
            if (o instanceof LinkedListTabulatedFunction) {
                LinkedListTabulatedFunction func = (LinkedListTabulatedFunction) o;
                if (func.size != this.size) {
                    return false;
                }
                currentIndex = 0;
                this.current = this.head;
                FunctionNode funcCurrent = func.head;
                while (this.current != this.headMain) {
                    if (this.current.point.x != funcCurrent.point.x) {
                        return false;
                    }
                    if (this.current.point.y != funcCurrent.point.y) {
                        return false;
                    }
                    this.current = this.current.next;
                    funcCurrent = funcCurrent.next;
                    ++currentIndex;
                }
                return true;
            } else {
                TabulatedFunction func = (TabulatedFunction) o;
                if (func.getPointsCount() != this.size) {
                    return false;
                }
                for (int i = 0; i < this.size; i++) {
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
        currentIndex = 0;
        current = head;

        int result = 0;

        while (current != headMain) {
            result += current.point.hashCode();
            currentIndex++;
            current = current.next;
        }

        return size + result;
    }

    FunctionNode getNodeByIndex(int index) {
        int fromTail = size - index -1;
        int fromHead = index;
        int fromCurrent = Math.abs(currentIndex - index);

        if(fromTail < fromHead){
            current = tail;
            currentIndex = size -1;
        }
        else
        {
            if(fromHead < fromCurrent)
            {
                current = head;
                currentIndex = 0;
            }
        }

        if(index < currentIndex)
        {
            while(currentIndex != index)
            {
                current = current.prev;
                currentIndex--;
            }
        }
        else
        {
            while(currentIndex != index)
            {
                current = current.next;
                currentIndex++;
            }
        }
        return  current;
    }

    FunctionNode addNodeToTail(){
        tail.next = new FunctionNode();
        tail.next.prev = tail;
        tail.next.next = headMain;
        tail = tail.next;
        size++;
        headMain.prev = tail;
        return  tail;
    }

    FunctionNode addNodeByIndex(int index) throws FunctionPointIndexOutBoundsException
    {
        if (index < 0 || index > size) {
            throw new FunctionPointIndexOutBoundsException("addNodeByIndex exception: index is out of bounds.");
        }
        if(index == size){
            return addNodeToTail();
        }
        getNodeByIndex(index);
        FunctionNode tempNode = new FunctionNode();
        tempNode.next = current;
        tempNode.prev = current.prev;
        current.prev.next = tempNode;
        current.prev = tempNode;

        current = tempNode;
        size++;

        return  current;
    }

    FunctionNode deleteNodeByIndex(int index) throws FunctionPointIndexOutBoundsException
    {
        if (index < 0 || index >= size) {
            throw new FunctionPointIndexOutBoundsException("deleteNodeByIndex exception: index is out of bounds.");
        }
        FunctionNode node;
        if(index == 0){
            node = head;
            head = head.next;
            tail.next = head;
        }
        else if(index == size-1){
            getNodeByIndex(index);
            node = current;
            current.prev.next = current.next;
            current.next.prev = current.prev;
            current = current.prev;

            head = headMain.next;
            tail = headMain.prev;
        }else{
            getNodeByIndex(index);
            node = current;
            current.prev.next = current.next;
            current.next.prev = current.prev;
        }

        current = current.prev;
        /*getNodeByIndex(index);
        FunctionNode node = current;
        current.prev.next = current.next;
        current.next.prev = current.prev;
        current = current.prev;
        --currentIndex;

        --size;
        head = headMain.next;
        tail = headMain.prev;
        */
        --currentIndex;

        --size;
        return node;
    }

    public FunctionPoint getPoint(int index) {
        getNodeByIndex(index);
        return new FunctionPoint(current.point);
    }

    @Override
    public double getLeftDomainBorder(){return head.point.x;} //+

    @Override
    public double getRightDomainBorder(){return tail.point.x;} //+

    @Override
    public int getPointsCount(){
        return size;
    } //+

    @Override
    public void setPoint(int index, FunctionPoint point) throws InappropriateFunctionPointException, FunctionPointIndexOutBoundsException //+
    {

        if (index < 0 || index >= size) {
            throw new FunctionPointIndexOutBoundsException("setPoint exception: index out of bounds.");
        }

        if(getPointX(index -1 )  > point.x || getPointX(index + 1)  < point.x){
            throw new InappropriateFunctionPointException("setPoint exception: x is out of bounds.");
        }

        FunctionNode node = getNodeByIndex(index);

        node.point = new FunctionPoint(point);
    }

    @Override
    public double getPointX(int index) throws FunctionPointIndexOutBoundsException //+
    {
        if (index < 0 || index >= size) {
            throw new FunctionPointIndexOutBoundsException("getPointX exception: index is out of bounds.");
        }
        return getNodeByIndex(index).point.x;
    }


    @Override
    public void setPointX(int index, double x) throws InappropriateFunctionPointException, FunctionPointIndexOutBoundsException //+
    {
        if (index < 0 || index > size - 1)
            throw new FunctionPointIndexOutBoundsException("setPointX exception: index is out of bounds.");

        if (getPointX(index -1 )  > x || getPointX(index + 1)  < x)
            throw new InappropriateFunctionPointException("setPointX exception: x is out of bounds.");

        FunctionNode node = getNodeByIndex(index);
        node.point.x = x;
        /*if (size == 0) {
            throw new IllegalStateException();
        }
        if (index < 0 || index >= size) {
            throw new FunctionPointIndexOutBoundsException();
        }

        double left = Double.MIN_VALUE, right = Double.MAX_VALUE;

        FunctionNode node = getNodeByIndex(index);

        if (node.prev != null) {
            left = node.prev.point.x;
        }
        if (node.next != null) {
            right = node.next.point.x;
        }

        if (left > x || right < x) {
            throw new InappropriateFunctionPointException();
        }

        node.point.x = x;*/
    }

    @Override
    public double getPointY(int index) throws FunctionPointIndexOutBoundsException //+
    {
        if (index < 0 || index > size -1)
            throw new FunctionPointIndexOutBoundsException("getPointY exception: index is out of bounds.");

        return getNodeByIndex(index).point.y;
    }

    @Override
    public void setPointY(int index, double y) throws FunctionPointIndexOutBoundsException //+
    {
        if (index < 0 || index > size -1)
            throw new FunctionPointIndexOutBoundsException("setPointY exception: index is out of bounds.");

        getNodeByIndex(index).point.y = y;
    }

    @Override
    public double getFunctionValue(double x) //+
    {
       if(x>= getLeftDomainBorder() && x <= getRightDomainBorder())
               return ((x-getLeftDomainBorder())*(tail.point.y -head.point.y))/(getRightDomainBorder() - getLeftDomainBorder())+ head.point.y;
       return Double.NaN;
    }

    @Override
    public void deletePoint(int index) throws IllegalStateException, FunctionPointIndexOutBoundsException
    {
        if (size < 3) {
            throw new IllegalStateException("deletePoint exception: number of points less then 3.");
        }
        if (index < 0 || index > size-1) {
            throw new FunctionPointIndexOutBoundsException("deletePoint exception: index is out of bounds.");
        }

        deleteNodeByIndex(index);
    }

    @Override
    public void addPoint(FunctionPoint point) throws InappropriateFunctionPointException
    {
            if (point.x > this.getRightDomainBorder() )
            {
                tail = addNodeToTail();
                tail.point = new FunctionPoint(point);
            }
            else
            {
                if (point.x < this.getLeftDomainBorder()){

                    addNodeByIndex(0).point = new FunctionPoint(point);
                }
                else
                {
                    int i;
                    for (i = 0; point.x > getNodeByIndex(i).point.x; ++i);
                    if (point.x == getNodeByIndex(i).point.x) throw new InappropriateFunctionPointException("addPoint exception: X is already in the set.");
                    else
                    {
                        addNodeByIndex(i).point = new FunctionPoint(point);
                    }
                }
            }
    }
    /*public void addPoint(FunctionPoint point) throws InappropriateFunctionPointException {
        if (point.x < head.point.x || point.x > tail.point.x) {
            throw new InappropriateFunctionPointException("addPoint exception: X is out of bounds.");
        }
        if (current.point.x == point.x) {
            throw new InappropriateFunctionPointException("addPoint exception: X is already in the set.");
        }
        current = head;
        currentIndex = 0;
        while (current.point.x < point.x) {
            current = current.next;
            currentIndex++;
        }
        addNodeByIndex(currentIndex).point = point;
    }*/



    public Object clone() throws CloneNotSupportedException {
        FunctionPoint points[] = new FunctionPoint[size];

        currentIndex = 0;
        current = head;

        while (current != headMain) {
            points[currentIndex] = current.point;
            currentIndex++;
            current = current.next;
        }

        return new LinkedListTabulatedFunction(points);

    }
}
