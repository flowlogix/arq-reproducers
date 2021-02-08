/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flowlogix.arq.simple;

import com.flowlogix.arq.simple.CreateFileTest.RunsWhere;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

/**
 *
 * @author lprimak
 */
public class CreateFileTest {
    static final String TMP_FILE_NAME = "/tmp/arqBeforeTest-5123";
    enum RunsWhere {
        CLIENT,
        SERVER,
    }

    @Test
    void createTmpFile() throws IOException {
        File tmpFile = new File(TMP_FILE_NAME);
        tmpFile.delete();
        tmpFile.createNewFile();
    }

    static void appendToFile(String str) {
        try (FileWriter fw = new FileWriter(TMP_FILE_NAME, true)) {
            fw.append(str + ",");
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    static void checkRunsWhere(RunsWhere expected) {
        boolean onServer = false;
        try {
            new InitialContext().lookup("java:comp/env");
            onServer = true;
        } catch (NamingException ex) {
        }
        assertEquals(expected, onServer ? RunsWhere.SERVER : RunsWhere.CLIENT);
    }
}
