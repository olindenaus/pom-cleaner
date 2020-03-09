package com.lindenau;

import com.lindenau.control.PomFactory;
import com.lindenau.control.PomLoader;
import com.lindenau.entity.Pom;

import java.io.File;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        if (args.length == 0 || args[0].isEmpty()) {
            System.out.println("Specify project's directory to analyze.");
            return;
        }
        String directory = args[0];
        PomLoader pomLoader = new PomLoader(directory);
        File[] pomFiles = pomLoader.getAllPoms();
        PomFactory pomFactory = new PomFactory();
        Map<String, Pom> poms = pomFactory.mapToPoms(pomFiles);
        System.out.println("Finished cleaning pom files");
    }
}
