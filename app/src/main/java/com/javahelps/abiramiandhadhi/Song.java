package com.javahelps.abiramiandhadhi;

/**
 * Created by gobinath on 8/28/15.
 */
public class Song {
    private int number;
    private String song;
    private String description;
    private boolean bookmarked;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getSong() {
        return song;
    }

    public void setSong(String song) {
        this.song = song;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isBookmarked() {
        return bookmarked;
    }

    public void setBookmarked(boolean bookmarked) {
        this.bookmarked = bookmarked;
    }

    @Override
    public String toString() {
        return song.substring(0, 20) + "...";
    }
}
