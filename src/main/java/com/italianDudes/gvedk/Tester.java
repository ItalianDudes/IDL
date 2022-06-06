package com.italianDudes.gvedk;

import com.italianDudes.gvedk.common.StringHandler;

import java.io.File;

public class Tester {

    public static void main(String[] args) {

        File f = new File("files/file.txt");

        System.out.println(f.getPath());

        System.out.println(StringHandler.getFileExtension(f));

    }

}
