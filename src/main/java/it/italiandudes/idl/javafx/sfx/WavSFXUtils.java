package it.italiandudes.idl.javafx.sfx;

import it.italiandudes.idl.logger.InfoFlags;
import it.italiandudes.idl.logger.Logger;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public final class WavSFXUtils {

    // Players
    public static MediaPlayer initLoopMediaPlayer(@NotNull final String sfxExternalForm) {
        MediaPlayer loopMediaPlayer = new MediaPlayer(new Media(sfxExternalForm));
        loopMediaPlayer.setOnEndOfMedia(() -> {
            loopMediaPlayer.seek(Duration.ZERO);
            loopMediaPlayer.play();
        });
        return loopMediaPlayer;
    }
    public static void switchLoopSFX(@NotNull final MediaPlayer loopMediaPlayer) {
        try {
            if (loopMediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
                loopMediaPlayer.pause();
            } else {
                loopMediaPlayer.seek(Duration.ZERO);
                loopMediaPlayer.play();
            }
        } catch (Exception e) {
            Logger.log("SFX Loop Switch Error", new InfoFlags(true, false, false , true));
            Logger.log(e);
        }
    }
    public static void stopWarningLoopSFX(@NotNull final MediaPlayer loopMediaPlayer) {
        try {
            if (loopMediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
                loopMediaPlayer.pause();
            }
        } catch (Exception e) {
            Logger.log("SFX Loop Stop Error", new InfoFlags(true, false, false , true));
            Logger.log(e);
        }
    }
    public static void playWarningLoopSFX(@NotNull final MediaPlayer loopMediaPlayer) {
        try {
            if (loopMediaPlayer.getStatus() != MediaPlayer.Status.PLAYING) {
                loopMediaPlayer.seek(Duration.ZERO);
                loopMediaPlayer.play();
            }
        } catch (Exception e) {
            Logger.log("SFX Loop Play Error", new InfoFlags(true, false, false , true));
            Logger.log(e);
        }
    }
    public static void playSFX(@NotNull final String sfxExternalForm) {
        try {
            new MediaPlayer(new Media(sfxExternalForm)).play();
        } catch (Exception e) {
            Logger.log("SFX Play Error", new InfoFlags(true, false, false , true));
            Logger.log(e);
        }
    }
}
