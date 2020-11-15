/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hello3D.test;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Hasini
 */
public class PackageGenerator {
    /**
     * @param args
     */
    public static void main(String[] args) {

        Set<String> files=new HashSet<>();
        listOfPackage("src/",files);

        System.out.println(files);
    }

    public static void listOfPackage(String directoryName, Set<String> pack) {
        File directory = new File(directoryName);

        // get all the files from a directory
        File[] fList = directory.listFiles();
        for (File file : fList) {
            if (file.isFile()) {
                String path=file.getPath();
                System.out.println("path= "+path);
                String packName=path.substring(path.indexOf("src")+4, path.lastIndexOf('\\'));
                System.out.println(packName + " =packName");
                pack.add(packName.replace('\\', '.'));
            } else if (file.isDirectory()) {

                listOfPackage(file.getAbsolutePath(), pack);
            }
        }
    }
    
}
