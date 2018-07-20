package io;

import java.io.File;

/**
 * Created by 尹恬婧 on 2018/7/20.
 */
public class FileDemo {
    public static void main(String [] args){
        File file=new File("a");
        String path=file.getAbsolutePath();
        System.out.println(path);
        file.mkdirs();
        if (file.isDirectory()){
            System.out.println("目录");
        }
        if (file.isFile()){
            System.out.println("文件");
        }
        if (file.exists()){
            System.out.println("file exists:true");
        }else {
            System.out.println("file exists:false");
        }
    }
}
