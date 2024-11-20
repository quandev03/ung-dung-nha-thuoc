package com.example.ungdungnhathuoc.Response;

public class ResponseUpdateImage {
    private InfoFile infoFile;
    private String link;

    public InfoFile getInfoFile() {
        return this.infoFile;
    }

    public void setInfoFile(InfoFile infoFile) {
        this.infoFile = infoFile;
    }

    public String getLink() {
        return this.link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public String toString() {
        return "ResponseUpdateImage{" +
                "infoFile=" + infoFile +
                ", link='" + link + '\'' +
                '}';
    }
}
