package com.example.thakur.randomplayer.Loaders;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;


import com.example.thakur.randomplayer.Utilities.UtilityFun;
import com.example.thakur.randomplayer.items.Album;
import com.example.thakur.randomplayer.items.Artist;
import com.example.thakur.randomplayer.items.Search;
import com.example.thakur.randomplayer.items.Song;

import java.util.ArrayList;
import java.util.concurrent.Executors;

/**
 * Created by architjn on 11/12/15.
 */
public class ListSongs {

    private static ArrayList<String> foldersList=new ArrayList<>();
    //private static ArrayList<Song> songList = new ArrayList<>();


    public static ArrayList<Album> getAlbumList(Context context) {
        final ArrayList<Album> albumList = new ArrayList<>();

        System.gc();
        final String orderBy = MediaStore.Audio.Albums.ALBUM;
        Cursor musicCursor = context.getContentResolver().
                query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI, null, null, null, orderBy);

        if (musicCursor != null && musicCursor.moveToFirst()) {
            //get columns
            int titleColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Albums.ALBUM);
            int idColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Albums._ID);
            int artistColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Albums.ARTIST);
            int numOfSongsColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Albums.NUMBER_OF_SONGS);
            int albumArtColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Albums.ALBUM_ART);
            //add albums to list
            do {
                albumList.add(new Album(musicCursor.getLong(idColumn),
                        musicCursor.getString(titleColumn),
                        musicCursor.getString(artistColumn),
                        false, musicCursor.getString(albumArtColumn),
                        musicCursor.getInt(numOfSongsColumn)));
            }
            while (musicCursor.moveToNext());
        }
        return albumList;
    }



    public static Album getAlbumFromId(Context context, long albumId) {
        System.gc();
        final String where = MediaStore.Audio.Albums._ID + "='" + albumId + "'";
        Cursor musicCursor = context.getContentResolver().
                query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI, null, where, null, null);

        if (musicCursor != null && musicCursor.moveToFirst()) {
            //get columns
            int titleColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Albums.ALBUM);
            int idColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Albums._ID);
            int artistColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Albums.ARTIST);
            int numOfSongsColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Albums.NUMBER_OF_SONGS);
            int albumArtColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Albums.ALBUM_ART);
            //add albums to list
            return new Album(musicCursor.getLong(idColumn),
                    musicCursor.getString(titleColumn),
                    musicCursor.getString(artistColumn),
                    false, musicCursor.getString(albumArtColumn),
                    musicCursor.getInt(numOfSongsColumn));
        }
        return null;
    }

    public static ArrayList<Artist> getArtistList(Context context) {
        ArrayList<Artist> albumList = new ArrayList<>();
        System.gc();
        final String orderBy = MediaStore.Audio.Artists.ARTIST;
        Cursor musicCursor = context.getContentResolver().
                query(MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI, null, null, null, orderBy);

        if (musicCursor != null && musicCursor.moveToFirst()) {
            //get columns
            int titleColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Artists.ARTIST);
            int idColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Artists._ID);
            int numOfAlbumsColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Artists.NUMBER_OF_ALBUMS);
            int numOfTracksColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Artists.NUMBER_OF_TRACKS);
            //add albums to list
            do {
                albumList.add(new Artist(musicCursor.getLong(idColumn),
                        musicCursor.getString(titleColumn),
                        musicCursor.getInt(numOfAlbumsColumn),
                        musicCursor.getInt(numOfTracksColumn)));
            }
            while (musicCursor.moveToNext());
        }
        return albumList;
    }

    public static long getArtistIdFromName(Context context, String name) {
        System.gc();
        String where = MediaStore.Audio.Artists.ARTIST + "='" + name + "'";
        Cursor musicCursor = context.getContentResolver().
                query(MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI, null, where, null, null);

        if (musicCursor != null && musicCursor.moveToFirst()) {
            int idColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Artists._ID);
            return musicCursor.getLong(idColumn);
        }
        return 0;
    }


    public static ArrayList<Song> getSongList(Context context) {

        System.gc();
        ArrayList<Song> songList = new ArrayList<>();
        final String where = MediaStore.Audio.Media.IS_MUSIC + "=1";
        final String orderBy = MediaStore.Audio.Media.TITLE;
        Cursor musicCursor = context.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                null, where, null, orderBy);
        if (musicCursor != null && musicCursor.moveToFirst()) {
            int titleColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Media.TITLE);
            int idColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Media._ID);
            int artistColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Media.ARTIST);
            int pathColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Media.DATA);
            int albumIdColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Media.ALBUM_ID);
            int albumColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Media.ALBUM);
            int addedDateColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Media.DATE_ADDED);
            int songDurationColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Media.DURATION);
            do {
                songList.add(new Song(musicCursor.getLong(idColumn),
                        musicCursor.getString(titleColumn),
                        musicCursor.getString(artistColumn),
                        musicCursor.getString(pathColumn), false,
                        musicCursor.getLong(albumIdColumn),
                        musicCursor.getString(albumColumn),
                        musicCursor.getLong(addedDateColumn),
                        musicCursor.getLong(songDurationColumn)));
            }
            while (musicCursor.moveToNext());
        }
        return songList;
    }

    public static Song getSong(Context context, long songId) {
        System.gc();
        final String where = MediaStore.Audio.Media.IS_MUSIC + "=1 AND "
                + MediaStore.Audio.Media._ID + "='" + songId + "'";
        Cursor musicCursor = context.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                null, where, null, null);
        if (musicCursor != null && musicCursor.moveToFirst()) {
            int titleColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Media.TITLE);
            int idColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Media._ID);
            int artistColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Media.ARTIST);
            int pathColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Media.DATA);
            int albumIdColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Media.ALBUM_ID);
            int albumColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Media.ALBUM);
            int addedDateColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Media.DATE_ADDED);
            int songDurationColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Media.DATE_ADDED);
            return new Song(musicCursor.getLong(idColumn),
                    musicCursor.getString(titleColumn),
                    musicCursor.getString(artistColumn),
                    musicCursor.getString(pathColumn), false,
                    musicCursor.getLong(albumIdColumn),
                    musicCursor.getString(albumColumn),
                    musicCursor.getLong(addedDateColumn),
                    musicCursor.getLong(songDurationColumn));
        }
        return null;
    }

    public static ArrayList<Album> getAlbumListOfArtist(Context context, long artistId) {
        final ArrayList<Album> albumList = new ArrayList<>();
        System.gc();
        Cursor musicCursor = context.getContentResolver().
                query(MediaStore.Audio.Artists.Albums.getContentUri("external", artistId),
                        null, null, null, MediaStore.Audio.Albums.DEFAULT_SORT_ORDER);
        if (musicCursor != null && musicCursor.moveToFirst()) {
            //get columns
            int titleColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Artists.Albums.ALBUM);
            int idColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Artists._ID);
            int artistColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Artists.Albums.ARTIST);
            int numOfSongsColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Albums.NUMBER_OF_SONGS_FOR_ARTIST);
            int albumArtColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Albums.ALBUM_ART);
            //add albums to list
            do {
                albumList.add(new Album(musicCursor.getLong(idColumn),
                        musicCursor.getString(titleColumn),
                        musicCursor.getString(artistColumn),
                        false, musicCursor.getString(albumArtColumn),
                        musicCursor.getInt(numOfSongsColumn)));
            }
            while (musicCursor.moveToNext());
        }
        musicCursor.close();
        return albumList;
    }

    public static ArrayList<Song> getSongsListOfArtist(Context context, String artistName) {
        ArrayList<Song> songList = new ArrayList<>();
        System.gc();
        final String where = MediaStore.Audio.Media.IS_MUSIC + "=1 AND "
                + MediaStore.Audio.Media.ARTIST + "='" + artistName.replace("'", "''") + "'";
        final String orderBy = MediaStore.Audio.Media.TITLE;
        Cursor musicCursor = context.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                null, where, null, orderBy);
        if (musicCursor != null && musicCursor.moveToFirst()) {
            int titleColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Media.TITLE);
            int idColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Media._ID);
            int artistColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Media.ARTIST);
            int pathColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Media.DATA);
            int albumIdColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Media.ALBUM_ID);
            int albumColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Media.ALBUM);
            int addedDateColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Media.DATE_ADDED);
            int songDurationColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Media.DURATION);
            do {
                songList.add(new Song(musicCursor.getLong(idColumn),
                        musicCursor.getString(titleColumn),
                        musicCursor.getString(artistColumn),
                        musicCursor.getString(pathColumn), false,
                        musicCursor.getLong(albumIdColumn),
                        musicCursor.getString(albumColumn),
                        musicCursor.getLong(addedDateColumn),
                        musicCursor.getLong(songDurationColumn)));
            }
            while (musicCursor.moveToNext());
        }
        musicCursor.close();
        return songList;
    }

    public static Search getSearchResults(Context context, String sQuery) {
        System.gc();
        ArrayList<Song> songList = searchSong(context, sQuery);
        ArrayList<Album> albumList = searchAlbum(context, sQuery);
        ArrayList<Artist> artistList = searchArtist(context, sQuery);
        return new Search(songList, albumList, artistList);
    }

    private static ArrayList<Song> searchSong(Context context, String sQuery) {
        ArrayList<Song> songList = new ArrayList<>();
        final String where = MediaStore.Audio.Media.IS_MUSIC + "=1 AND "
                + MediaStore.Audio.Media.TITLE + " LIKE '%" + sQuery.replace("'", "''") + "%'";
        final String orderBy = MediaStore.Audio.Media.TITLE;
        Cursor musicCursor = context.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                null, where, null, orderBy);
        if (musicCursor != null && musicCursor.moveToFirst()) {
            int titleColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Media.TITLE);
            int idColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Media._ID);
            int artistColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Media.ARTIST);
            int pathColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Media.DATA);
            int albumIdColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Media.ALBUM_ID);
            int albumColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Media.ALBUM);
            int addedDateColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Media.DATE_ADDED);
            int songDurationColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Media.DURATION);
            do {
                songList.add(new Song(musicCursor.getLong(idColumn),
                        musicCursor.getString(titleColumn),
                        musicCursor.getString(artistColumn),
                        musicCursor.getString(pathColumn), false,
                        musicCursor.getLong(albumIdColumn),
                        musicCursor.getString(albumColumn),
                        musicCursor.getLong(addedDateColumn),
                        musicCursor.getLong(songDurationColumn)));
            }
            while (musicCursor.moveToNext());
        }
        return songList;
    }

    private static ArrayList<Album> searchAlbum(Context context, String sQuery) {
        ArrayList<Album> albumList = new ArrayList<>();
        final String where = MediaStore.Audio.Albums.ALBUM + " LIKE '%" + sQuery.replace("'", "''") + "%'";
        Cursor musicCursor = context.getContentResolver().
                query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI, null, where, null, null);

        if (musicCursor != null && musicCursor.moveToFirst()) {
            //get columns
            int titleColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Albums.ALBUM);
            int idColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Albums._ID);
            int artistColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Albums.ARTIST);
            int numOfSongsColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Albums.NUMBER_OF_SONGS);
            int albumArtColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Albums.ALBUM_ART);
            //add albums to list
            do {
                albumList.add(new Album(musicCursor.getLong(idColumn),
                        musicCursor.getString(titleColumn),
                        musicCursor.getString(artistColumn),
                        false, musicCursor.getString(albumArtColumn),
                        musicCursor.getInt(numOfSongsColumn)));
            }
            while (musicCursor.moveToNext());
        }
        return albumList;
    }


    private static ArrayList<Artist> searchArtist(Context context, String sQuery) {
        ArrayList<Artist> artistList = new ArrayList<>();
        final String where = MediaStore.Audio.Artists.ARTIST + " LIKE '%" + sQuery.replace("'", "''") + "%'";
        Cursor musicCursor = context.getContentResolver().
                query(MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI, null, where, null, null);

        if (musicCursor != null && musicCursor.moveToFirst()) {
            //get columns
            int titleColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Artists.ARTIST);
            int idColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Artists._ID);
            int numOfAlbumsColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Artists.NUMBER_OF_ALBUMS);
            int numOfTracksColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Artists.NUMBER_OF_TRACKS);
            //add albums to list
            do {
                artistList.add(new Artist(musicCursor.getLong(idColumn),
                        musicCursor.getString(titleColumn),
                        musicCursor.getInt(numOfAlbumsColumn),
                        musicCursor.getInt(numOfTracksColumn)));
            }
            while (musicCursor.moveToNext());
        }
        return artistList;
    }

    public static ArrayList<Song> getAlbumSongList(Context context, long albumId) {
        System.gc();
        Cursor musicCursor;
        ArrayList<Song> songs = new ArrayList<>();
        String where = MediaStore.Audio.Media.ALBUM_ID + "=?";
        String whereVal[] = {String.valueOf(albumId)};
        String orderBy = MediaStore.Audio.Media._ID;

        musicCursor = context.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                null, where, whereVal, orderBy);
        if (musicCursor != null && musicCursor.moveToFirst()) {
            int titleColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Media.TITLE);
            int idColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Media._ID);
            int artistColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Media.ARTIST);
            int pathColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Media.DATA);
            int albumIdColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Media.ALBUM_ID);
            int albumColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Media.ALBUM);
            int addedDateColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Media.DATE_ADDED);
            int albumSongDuration = musicCursor.getColumnIndex
                    (MediaStore.Audio.Media.DURATION);
            do {
                songs.add(new Song(musicCursor.getLong(idColumn),
                        musicCursor.getString(titleColumn),
                        musicCursor.getString(artistColumn),
                        musicCursor.getString(pathColumn), false,
                        musicCursor.getLong(albumIdColumn),
                        musicCursor.getString(albumColumn),
                        musicCursor.getLong(addedDateColumn),
                        musicCursor.getLong(albumSongDuration)));
            }
            while (musicCursor.moveToNext());
        }
        return songs;
    }

    public static String getAlbumArt(Context context, long albumdId) {
        Cursor cursor = context.getContentResolver().query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Audio.Albums._ID, MediaStore.Audio.Albums.ALBUM_ART},
                MediaStore.Audio.Albums._ID + "=?",
                new String[]{String.valueOf(albumdId)},
                null);
        String imagePath = "";
        if (cursor != null && cursor.moveToFirst()) {
            imagePath = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART));
        }
        return imagePath;
    }





    public static String getTitleFromFilePath(Context context ,String filePath){
        if(filePath.contains("\"")){
            filePath = UtilityFun.escapeDoubleQuotes(filePath);
        }
        ContentResolver cr = context.getContentResolver();
        Uri videosUri = MediaStore.Audio.Media.getContentUri("external");
        String[] projection = {MediaStore.Audio.Media.TITLE};
        Cursor cursor = cr.query(videosUri, projection, MediaStore.Audio.Media.DATA + " LIKE ?", new String[] { filePath }, null);
        if(cursor!=null && cursor.getCount()!=0) {
            cursor.moveToFirst();
            String id = "";
            try {
                id = cursor.getString(0);
            } catch (Exception e) {
                cursor.close();
                return null;
            }
            cursor.close();
            return id;
        }else {
            return null;
        }
    }

    public static int getIdFromTitle(Context context , String title){
        ContentResolver cr = context.getContentResolver();
        if(title.contains("'")){
            //title = ((char)34+title+(char)34);
            //fuck you bug
            //you bugged my mind
            title = title.replaceAll("'","''");
        }
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String selection =  MediaStore.Audio.Media.IS_MUSIC + "!= 0" + " AND "
                +MediaStore.Audio.Media.TITLE  + "= '" +   title  +"'";
        String[] projection = {
                MediaStore.Audio.Media._ID
        };
        Cursor cursor=cr.query(
                uri,
                projection,
                selection,
                null,
                MediaStore.Audio.Media.TITLE + " ASC");

        if(cursor!=null){
            cursor.moveToFirst();
            int id = cursor.getInt(0);
            cursor.close();
            return  id;
        }
        return 0;
    }
}
