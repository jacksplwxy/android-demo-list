package com.jacksplwxy.start;


import androidx.annotation.NonNull;

public class AnimeBean {
    private String mAnimeName;
    private String mAnimeAuthor;
    private int mAnimeCoverImg;

    public AnimeBean(String animeName, String animeAuthor, int animeCoverImg) {
        this.mAnimeName = animeName;
        this.mAnimeAuthor = animeAuthor;
        this.mAnimeCoverImg = animeCoverImg;
    }

    public String getmAnimeName() {
        return this.mAnimeName;
    }

    public void setmAnimeName(String animeName) {
        this.mAnimeName = animeName;
    }

    public String getmAnimeAuthor() {
        return this.mAnimeAuthor;
    }

    public void setmAnimeAuthor(String animeAuthor) {
        this.mAnimeAuthor = animeAuthor;
    }

    public int getmAnimeCoverImg() {
        return this.mAnimeCoverImg;
    }

    public void setmAnimeCoverImg(int animeCoverImg) {
        this.mAnimeCoverImg = animeCoverImg;
    }

    @NonNull
    @Override
    public String toString() {
        return "mAnimeName:"+mAnimeName+";mAnimeAuthor:"+mAnimeAuthor+";mAnimeCoverImg:"+mAnimeCoverImg;
    }
}