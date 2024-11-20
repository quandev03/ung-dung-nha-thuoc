package com.example.ungdungnhathuoc.Response;

public class InfoFile {
    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "InfoFile{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
