package functions;

import java.io.*;

public class TabulatedFunctions {

    private TabulatedFunctions(){}

    public static TabulatedFunction tabulate(Function function, double leftX, double rightX, int pointsCount) {
        if (leftX < function.getLeftDomainBorder() || rightX > function.getRightDomainBorder()) {
            throw new IllegalArgumentException();
        }

        FunctionPoint[] points = new FunctionPoint[pointsCount];
        points[0] = new FunctionPoint(leftX, function.getFunctionValue(leftX));

        double step = (rightX - leftX) / (pointsCount - 1);
        for (int i = 1; i < pointsCount; i++) {

            points[i] = new FunctionPoint(points[i - 1].x + step, function.getFunctionValue(points[i - 1].x + step));

        }

        return new ArrayTabulatedFunction(points);
    }

    public static void outputTabulatedFunction(TabulatedFunction function, OutputStream out) throws IOException{
        int sizeOfFunction = function.getPointsCount();
        DataOutputStream stream = new DataOutputStream(out);
        stream.writeInt(sizeOfFunction);

        for(int i = 0; i < sizeOfFunction; ++i){
            stream.writeDouble(function.getPointX(i));
            stream.writeDouble(function.getPointY(i));
        }
        stream.flush();
    }

    public static TabulatedFunction inputTabulatedFunction(InputStream in) throws IOException{
        DataInputStream stream = new DataInputStream(in);
        int sizeOfFunction = stream.readInt();

        FunctionPoint points[] = new FunctionPoint[sizeOfFunction];

        for(int i=0; i < sizeOfFunction; ++i){
            points[i] = new FunctionPoint(stream.readDouble(),stream.readDouble());
        }

        return new ArrayTabulatedFunction(points);
    }

    public static void writeTabulatedFunction(TabulatedFunction function, Writer out){
        PrintWriter writer = new PrintWriter(out);
        int sizeOfFunction = function.getPointsCount();

        writer.println(sizeOfFunction);

        for(int i = 0; i<sizeOfFunction; ++i){ /*Пробелы*/
            writer.println(function.getPointX(i));
            writer.println(function.getPointY(i));
        }
    }

    public static TabulatedFunction readTabulatedFunction(Reader in) throws IOException{
        StreamTokenizer tokenizer = new StreamTokenizer(in);
        tokenizer.nextToken();

        int sizeOfFunction = (int)tokenizer.nval;

        FunctionPoint points[] = new FunctionPoint[sizeOfFunction];
        double x,y;

        for(int i =0; i<sizeOfFunction; ++i){
            tokenizer.nextToken();
            x = tokenizer.nval;
            tokenizer.nextToken();
            y = tokenizer.nval;

            points[i] = new FunctionPoint(x,y);
        }

        return new ArrayTabulatedFunction(points);
    }
}
