/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flowlogix.arq.simple;

import static com.flowlogix.arq.simple.CreateFileTest.TMP_FILE_NAME;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

/**
 *
 * @author lprimak
 */
// ensure this runs last
public class XCheckFileTest {
    @Test
    void check() throws IOException {
        File tmpFile = new File(TMP_FILE_NAME);
        try (FileReader fileReader = new FileReader(tmpFile)) {
            char[] buf = new char[1000];
            int length = fileReader.read(buf);
            String contents = new String(buf, 0, length);
            assertEquals("before_all,before_each,test_one,after_each,before_each,test_two,after_each,after_all,", contents);
        }
        tmpFile.delete();
    }
}
