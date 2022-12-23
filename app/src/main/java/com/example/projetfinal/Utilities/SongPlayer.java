package com.example.projetfinal.Utilities;

import android.content.Context;
import android.media.MediaPlayer;

import java.util.ArrayList;

/**
 * Encapsulate the capability to store and play songs
 */
public class SongPlayer {

    ArrayList<MediaPlayer> songs;


    /**
     * Constructor that initialises the song arrayList
     */
    public SongPlayer()
    {
        songs = new ArrayList<>();
    }


    /**
     * Add a song to the playlist
     * @param context
     * Reference to the context of the application
     * @param trackID
     * ID of the song
     */
    public void  addSong(Context context, int trackID)
    {
        try {
            songs.add(MediaPlayer.create(context, trackID));
            songs.get(songs.size()-1).setLooping(true);
        }
        catch (Exception e)
        {
            //Not a possible song
        }
    }

    /**
     * Play a specific song
     * @param index
     * index of the song
     */
    public void play(int index)
    {
        if(index >= 0 && index <songs.size())
        {
            if(!songs.get(index).isPlaying())
            {
                songs.get(index).start();
            }
        }
    }

    /**
     * Stop all the songs
     */
    public void reset()
    {
        for(int i =0; i < songs.size(); i++)
        {
            if(songs.get(i).isPlaying())
            {
                songs.get(i).pause();
            }
        }
    }

    public void cleanup()
    {
        for(int i =songs.size()-1; i >= 0; i--)
        {
            songs.get(i).release();
        }
    }

    public void changeValue(int soundValue)
    {

        float volume;
        switch (soundValue)
        {
            case 0:
                volume=0;
                break;

            case 1:
                volume= 0.01f;
                break;
            case 2:
                volume= 0.1f;
                break;
            case 3:
                volume= 0.3f;
                break;
            case 4:
                volume= 0.6f;
                break;
            default:
                volume= 1.0f;
                break;
        }
        for(int i =0; i < songs.size(); i++)
        {
            songs.get(i).setVolume(volume,volume);
        }
    }
}
