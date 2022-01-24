package FXMLFilePackage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ClassLoaderForFunction extends ClassLoader {
    public Class loadClassFromFile(File file) throws IOException {
        FileInputStream in = new FileInputStream(file);
        byte[] fileContent = new byte[in.available()];
        in.read(fileContent);
        in.close();
        return defineClass(null, fileContent, 0, fileContent.length);
    }
}
