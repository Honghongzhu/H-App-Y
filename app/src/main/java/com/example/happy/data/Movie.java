package com.example.happy.data;

import java.util.ArrayList;
import java.util.List;

public class Movie {
    private String itemId;
    private String name;
    private int imageDrawableId;
    private boolean saved;
    private String cs;

    public Movie(String itemId, String name, int imageDrawableId, boolean saved, String cs){
        this.itemId = itemId;
        this.name = name;
        this.imageDrawableId = imageDrawableId;
        this.saved = saved;
        this.cs = cs;
    }

    public String getItemId(){
        return itemId;
    }

    public String getName(){
        return name;
    }

    public int getImageDrawableId() {
        return imageDrawableId;
    }

    public boolean isSaved(){
        return saved;
    }

    public void setSaved(boolean saved){
        this.saved = saved;
    }

    public String getCs() {
        return cs;
    }


}
