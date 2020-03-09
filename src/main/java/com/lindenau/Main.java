package com.lindenau;

import com.lindenau.control.PomLoader;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        if (args.length == 0 || args[0].isEmpty()) {
            System.out.println("Specify project's directory to analyze.");
            return;
        }
        String directory = args[0];
        PomLoader pomLoader = new PomLoader(directory);
        File[] poms = pomLoader.getAllPoms();
    }
}
