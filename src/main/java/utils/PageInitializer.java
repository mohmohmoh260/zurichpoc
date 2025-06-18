package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PageInitializer {

    private static List<String> getClassNames(String folderPath, String packageName) {
        List<String> classNames = new ArrayList<>();
        File folder = new File(folderPath);

        if (!folder.exists() || !folder.isDirectory()) {
            throw new IllegalArgumentException("Invalid folder path: " + folderPath);
        }

        for (File file : folder.listFiles()) {
            if (file.isFile() && file.getName().endsWith(".java")) {
                String className = file.getName().replace(".java", "");
                classNames.add(packageName + "." + className);
            }
        }
        return classNames;
    }

    public static void pageFactoryInitializer(WebDriver driver, String folderPath, String packageName) throws ClassNotFoundException {
        for(String names :  getClassNames(folderPath, packageName)){
            PageFactory.initElements(driver, Class.forName(names));
        }
    }
}
