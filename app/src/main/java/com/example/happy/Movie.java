package com.example.happy;

public class Movie {
    private int itemId;
    private String name;
    private int imageDrawableId;
    private boolean saved;

    public Movie(int itemId, String name, int imageDrawableId, boolean saved){
        this.itemId = itemId;
        this.name = name;
        this.imageDrawableId = imageDrawableId;
        this.saved = saved;
    }

    public int getItemId(){
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

    public void setSaved(){
        this.saved = saved;
    }
}
