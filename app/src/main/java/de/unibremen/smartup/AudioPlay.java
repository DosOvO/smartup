package de.unibremen.smartup;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;

public class AudioPlay {

    public static MediaPlayer mediaPlayer;
    public static boolean isAudioPlaying = false;

    public static void playAudio(Context context, Uri uri) {
        mediaPlayer = MediaPlayer.create(context , uri);
        if (!mediaPlayer.isPlaying()) {
            isAudioPlaying = true;
            mediaPlayer.setLooping(true);
            mediaPlayer.start();
        }
    }

    public static void stopAudio() {
        isAudioPlaying = false;
        mediaPlayer.stop();
    }

    public static void pauseAudio() {
        mediaPlayer.pause();
    }

    public static void resumeAudio() {
        mediaPlayer.start();
    }
}
