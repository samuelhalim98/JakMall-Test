package com.example.jakmall2;

public class ListActivity {
    private String type;
    private String desc;

    public ListActivity(String id, String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }
}
