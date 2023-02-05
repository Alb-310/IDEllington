package com.orion.pingv2.Feature;

import com.orion.pingv2.UI.Manager.Theme.ThemeManager;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import javax.sound.sampled.AudioFormat;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;

public class SoundEffects {
    private static void play(InputStream inputStream, float volume) {
        try {
            File yourFile;
            AudioInputStream stream;
            AudioFormat format;
            DataLine.Info info;
            Clip clip;

            InputStream bufferedIn = new BufferedInputStream(inputStream);
            stream = AudioSystem.getAudioInputStream(bufferedIn);
            format = stream.getFormat();
            info = new DataLine.Info(Clip.class, format);
            clip = (Clip) AudioSystem.getLine(info);
            clip.open(stream);

            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(20f * (float) Math.log10(volume));

            clip.start();
        }
        catch (Exception e) {
            System.err.println("Sound Effect from Failed");
            e.printStackTrace();
        }
    }

    public static void onStartSound()
    {
        try {
            InputStream inputStream = ThemeManager.class.getResourceAsStream("/sounds/StartSound.wav");
            play(inputStream, (float) 1);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void runSound()
    {
        try {
            InputStream inputStream = ThemeManager.class.getResourceAsStream("/sounds/RunSound.wav");
            play(inputStream, (float) 0.5);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void errorSound()
    {
        try {
            InputStream inputStream = ThemeManager.class.getResourceAsStream("/sounds/DidierErrorSound.wav");
            play(inputStream, (float) 0.4);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void successSound()
    {
        try {
            InputStream inputStream = ThemeManager.class.getResourceAsStream("/sounds/RunSound.wav");
            play(inputStream, (float) 0.4);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
