import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;

public class SoundPlayerUsingClip implements LineListener {
//https://www.baeldung.com/java-play-sound
//https://docs.oracle.com/javase/8/docs/api/javax/sound/sampled/package-summary.html
    boolean isPlaybackCompleted;

    @Override
    public void update(LineEvent event) {
        if (LineEvent.Type.START == event.getType()) {
            System.out.println("Playback started.");
        } else if (LineEvent.Type.STOP == event.getType()) {
            isPlaybackCompleted = true;
            System.out.println("Playback completed.");
        }
    }
}