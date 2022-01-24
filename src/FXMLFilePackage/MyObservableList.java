package FXMLFilePackage;

import functions.Function;
import functions.TabulatedFunction;
import javafx.collections.ObservableListBase;
import java.io.File;

public class MyObservableList extends ObservableListBase {
    MyObservableList(){}
    protected static TabulatedFunction f;
    protected static Function function = null;
    protected static File file = null;
    protected static boolean saved = false;

    @Override
    public Object get(int index) {
        return f.getPoint(index);
    }

    @Override
    public int size() {
        return f.getPointsCount();
    }
}
