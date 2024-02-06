package fr.eris.erisutils.utils.file;

import org.checkerframework.checker.units.qual.A;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {

    public static File getRawFile(File directory, String fileName) {
        return new File(directory, fileName);
    }

    public static File createDirectory(File directory, String directoryName) {
        File file = getRawFile(directory, directoryName);
        file.mkdir();
        return file;
    }

    public static File getOrCreateFile(File directory, String fileName) {
        if(!directory.exists()) directory.mkdir();
        File file = getRawFile(directory, fileName);

        try {
            if (fileName.contains("."))
                file.createNewFile();
            else file.mkdir();
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return file;
    }
    
    public static void writeFile(File file, String newFileContent) {
        if(file.isDirectory()) throw new IllegalArgumentException("The file cannot be a directory ! " + file.getName());

        try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8)) {
            writer.write(newFileContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void appendToFile(File file, String toAppend) {
        if(file.isDirectory()) throw new IllegalArgumentException("The file cannot be a directory ! " + file.getName());

        try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8)) {
            writer.append(toAppend);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String readFile(File file) {
        return readFile(file, true);
    }
    
    public static String readFile(File file, boolean withLineSeparator) {
        if(file.isDirectory()) return "";
        
        StringBuilder currentContent = new StringBuilder();
        
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(file), StandardCharsets.UTF_8));
            String currentLine;
            while((currentLine = reader.readLine()) != null) {
                currentContent.append(currentLine);
                if(withLineSeparator) currentContent.append(System.lineSeparator());
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return currentContent.toString();
    }

    public static List<File> getAllFiles(File directory, String fileExtension) {
        List<File> foundFile = new ArrayList<>();
        for(File file : getAllFiles(directory)) {
            if(file.getName().endsWith("." + fileExtension))
                foundFile.add(file);
        }
        return foundFile;
    }

    public static List<File> getAllFiles(File directory) {
        File[] allFile = directory.listFiles();
        if(allFile == null) 
            return new ArrayList<>();
        return List.of(allFile);
    }
    
    public static void deleteFile(File directory, String fileName) {
        File target = getRawFile(directory, fileName);
        if(!target.exists()) return;
        target.delete();
    }
    
    public static boolean isExist(File directory, String fileName) {
        return getRawFile(directory, fileName).exists();
    }
}
