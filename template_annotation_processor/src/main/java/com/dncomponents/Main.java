package com.dncomponents;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author nikolasavic
 */
public class Main {
    public static void main(String[] args) {
        String file = args[0] + File.separator + "Temp.java";
        FileWriter fw;
        try {
            fw = new FileWriter(new File(file));
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("//used solely to force running compilation and annotation processor on html files changes.\n" +
                    "public class Temp{}");
            bw.close();
        } catch (IOException e) {
            System.err.println("Can't create Temp.java!");
            e.printStackTrace();
        }
    }
}
