package io;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by 尹恬婧 on 2018/7/20.
 */
public class FileWriterTester {
    public static void main(String [] args) throws IOException {
        String fileName="D:\\Hello.txt";
        FileWriter writer=new FileWriter(fileName);
        writer.write("hello!");
        writer.close();
    }

}
