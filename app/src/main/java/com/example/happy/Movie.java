package com.example.happy;

public class Movie {
    private String itemId;
    private String name;
    private int imageDrawableId;
    private boolean saved;

    public Movie(String itemId, String name, int imageDrawableId, boolean saved){
        this.itemId = itemId;
        this.name = name;
        this.imageDrawableId = imageDrawableId;
        this.saved = saved;
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
}
