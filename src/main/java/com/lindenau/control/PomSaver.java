package com.lindenau.control;

import com.lindenau.entity.Pom;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class PomSaver {

        public void overWritePom(Pom pom, String content) {

            PrintWriter pw = null;
            try {
                pw = new PrintWriter(pom.getFilePath());
                pw.write(content);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                if (pw != null) {
                    pw.close();
                }
            }
        }
}
