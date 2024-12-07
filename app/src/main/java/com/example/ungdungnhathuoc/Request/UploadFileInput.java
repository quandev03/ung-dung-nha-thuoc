package com.example.ungdungnhathuoc.Request;

import java.io.File;

public class UploadFileInput {
    private byte[] file;

    public UploadFileInput(byte[] file) {
        this.file = file;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    @Override
    public String toString() {
        return "UploadFileInput{" +
                "file=" + java.util.Arrays.toString(file) +
                '}';
    }
}