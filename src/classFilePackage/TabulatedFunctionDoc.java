package classFilePackage;

import FXMLFilePackage.FXMLMainFormController;
import functions.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class TabulatedFunctionDoc extends FXMLMainFormController implements Initializable {
    private TabulatedFunction tabulatedFunction;
    private String name;
    private FXMLMainFormController controller;
    private boolean Saved = false;
    private boolean modified = false, fileNameAssigned = false;

    public void registerRedrawFunctionController(FXMLMainFormController controller) {
        this.controller = controller;
        callRedraw();
    }

    public void setLink(TabulatedFunction link){
        this.tabulatedFunction = link;
    }

    public TabulatedFunction getLink() {
        return this.tabulatedFunction;
    }

    public void newFunction(double leftX, double rightX, int pointsCount) {
        this.tabulatedFunction = new ArrayTabulatedFunction(leftX, rightX, pointsCount);
        modified = true;
        Saved = false;
        callRedraw();
    }

    public void tabulateFunction(Function function, double leftX, double rightX, int pointsCount) {
        this.tabulatedFunction = TabulatedFunctions.tabulate(function, leftX, rightX, pointsCount);
        modified = true;
        Saved = false;
        callRedraw();
    }

    public void saveFunctionAs(String fileName) {
        name = fileName;
        fileNameAssigned = true;
        try {
            saveFunction();
        }
        catch (IOException error) {
            System.out.println("IOException error");
        }
        Saved = true;
    }

    public void saveFunction() throws IOException {
        JSONArray Link = new JSONArray();
        for(int i = 0; i < this.tabulatedFunction.getPointsCount(); ++i) {
            JSONObject point = new JSONObject();
            point.put("X",this.tabulatedFunction.getPointX(i));
            point.put("Y",this.tabulatedFunction.getPointY(i));
            JSONObject points = new JSONObject();
            points.put("Точки",point);
            Link.add(points);
        }
        FileWriter fileWriter = new FileWriter(name);
        fileWriter.write(Link.toJSONString());
        fileWriter.flush();
        modified = false;
        Saved = true;
    }

    public void loadFunction(String fileName) throws FileNotFoundException, IOException {
        name = fileName;
        fileNameAssigned = true;
        //JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(fileName)) {
            //Read JSON file
            Object obj = jsonParser.parse(reader);
            JSONArray employeeList = (JSONArray) obj;
            FunctionPoint[] array = new FunctionPoint[employeeList.size()];
            System.out.println(employeeList);
            for(int i = 0; i < employeeList.size(); ++ i) {
                JSONObject employeeObject = (JSONObject)((JSONObject)employeeList.get(i)).get("Точки");
                Double X = (Double) employeeObject.get("X");
                //System.out.println(X);
                Double Y = (Double) employeeObject.get("Y");
                //System.out.println(Y);
                array[i] = new FunctionPoint(X,Y);
            }
            this.tabulatedFunction = new ArrayTabulatedFunction(array);
            //for(int i = 0; i < employeeList.size(); ++ i);
            //employeeList.forEach( emp -> parseEmployeeObject( (JSONObject) emp ) );
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Saved = false;
        modified = false;
        callRedraw();
    }

    public int getPointsCount() {
        return this.tabulatedFunction.getPointsCount();
    }

    public void setPointY(int index, double y) {
        this.tabulatedFunction.setPointY(index, y);
        modified = true;
        callRedraw();
    }

    public double getPointY(int index) {
        return this.tabulatedFunction.getPointY(index);
    }

    public double getPointX(int index) {
        return this.tabulatedFunction.getPointX(index);
    }

    public double getFunctionValue(double x) {
        return this.tabulatedFunction.getFunctionValue(x);
    }

    public void addPoint(FunctionPoint point) throws InappropriateFunctionPointException {
        modified = true;
        this.tabulatedFunction.addPoint(point);
        Saved = false;
        callRedraw();
    }

    public void setPoint(int index, FunctionPoint point) throws InappropriateFunctionPointException {
        modified = true;
        this.tabulatedFunction.setPoint(index, point);
        Saved = false;
        callRedraw();
    }

    public void setPointX(int index, double x) throws InappropriateFunctionPointException {
        modified = true;
        this.tabulatedFunction.setPointX(index, x);
        Saved = false;
        callRedraw();
    }

    public void deletePoint(int index) {
        modified = true;
        this.tabulatedFunction.deletePoint(index);
        Saved = false;
        callRedraw();
    }

    public double getRightDomainBorder() {
        return this.tabulatedFunction.getRightDomainBorder();
    }

    public double getLeftDomainBorder() {
        return this.tabulatedFunction.getLeftDomainBorder();
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        TabulatedFunctionDoc clone = new TabulatedFunctionDoc();
        clone.tabulatedFunction = (TabulatedFunction) tabulatedFunction.clone();
        clone.name = name;
        clone.modified = modified;
        clone.fileNameAssigned = fileNameAssigned;
        return clone();
    }

    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder("{");
        for (int i = 0; i < this.tabulatedFunction.getPointsCount(); i++) {
            buffer.append(this.tabulatedFunction.getPoint(i).toString()).append(", ");
        }
        buffer.deleteCharAt(buffer.length() - 1).deleteCharAt(buffer.length() - 1);
        buffer.append("}");
        return buffer.toString();
    }

    public FunctionPoint getPoint(int index) {
        if (index >= 0 && index < this.getPointsCount()) {
            return new FunctionPoint(this.tabulatedFunction.getPoint(index));
        } else {
            throw new FunctionPointIndexOutBoundsException();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (o instanceof TabulatedFunction) {
            TabulatedFunction func = (TabulatedFunction) o;
            if (func.getPointsCount() != this.getPointsCount()) {
                return false;
            }
            for (int i = 0; i < this.getPointsCount(); i++) {
                if (!(this.getPoint(i).equals(func.getPoint(i)))) {
                    return false;
                }
            }
            return true;
        }
        else return false;
    }

    public void callRedraw() { redraw(); }

    public boolean isSaved() {
        return Saved;
    }
}
