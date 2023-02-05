package com.orion.pingv2.Utils;

import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;
import java.io.FileInputStream;

public class AudioFilePlayer implements Runnable {
    private AdvancedPlayer player;
    private int pausedOnByte = 0;
    private String musicPath;

    public void pause() {
        player.stop();
    }

    public void stop() {
        player.stop();
        pausedOnByte = 0;
    }

    @Override
    public void run() {
        try {
            FileInputStream stream = new FileInputStream(musicPath);
            player = new AdvancedPlayer(stream);
            player.setPlayBackListener(new PlaybackListener() {
                @Override
                public void playbackFinished(PlaybackEvent event) {
                    pausedOnByte += event.getFrame() / 24;
                }
            });

            player.play(pausedOnByte, Integer.MAX_VALUE);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setMusicPath(String value) {
        this.musicPath = value;
    }
}