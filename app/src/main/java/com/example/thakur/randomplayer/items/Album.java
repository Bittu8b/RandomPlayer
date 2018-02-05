package com.example.thakur.randomplayer.items;

/**
 * Created by architjn on 29/11/15.
 */
public class Album {

    private long albumId;
    private String albumTitle, albumArtist, albumArtPath;
    private boolean fav;
    private int songNumber;
    private long artistId;
    private int minyear;

    public Album() {
        super();
    }

    public Album(long albumId, String albumTitle, String albumArtist, long artistId, int songNumber, int minyear, String albumArtPath) {
        this.albumId = albumId;
        this.albumTitle = albumTitle;
        this.albumArtist = albumArtist;
        this.albumArtPath = albumArtPath;
        this.songNumber = songNumber;
        this.artistId = artistId;
        this.minyear = minyear;
    }

    public Album(long albumId, String albumTitle, String albumArtist, long artistId, int songNumber , int minyear) {
        this.albumId = albumId;
        this.albumTitle = albumTitle;
        this.albumArtist = albumArtist;
        this.songNumber = songNumber;
        this.artistId = artistId;
        this.minyear = minyear;
    }

    public Album(long albumId, String albumTitle, String albumArtist, boolean fav, String albumArtPath, int songNumber) {
        this.albumId = albumId;
        this.albumTitle = albumTitle;
        this.albumArtist = albumArtist;
        this.fav = fav;
        this.albumArtPath = albumArtPath;
        this.songNumber = songNumber;
    }

    public long getAlbumId() {
        return albumId;
    }

    public String getAlbumArtist() {
        return albumArtist;
    }

    public String getAlbumTitle() {
        return albumTitle;
    }

    public int getSongNumber() {
        return songNumber;
    }

    public String getAlbumArtPath() {
        return albumArtPath;
    }

    public boolean isFav() {
        return fav;
    }
}
