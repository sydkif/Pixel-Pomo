package testPackage;

import java.nio.file.Paths;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class AudioPlayer extends AudioLibrary {

    private Media media;
    private MediaPlayer player;
    private AudioLibrary lib;
    private String path;
    private String type;
    private boolean playing;
    private int currentTrack;
    private double currentVolume = 1.0;

    public AudioPlayer() {

    }

    /**
     * Create audio player for specific type and track number
     * 
     * @param type        must be "AMBIENT" or "MUSIC" only!
     * @param trackNumber corresponding to audio list in the folder start from 0
     */
    public AudioPlayer(String type, int trackNumber) {
        this.type = type;
        currentTrack = trackNumber;
    }

    /**
     * Play audio method once.
     * 
     * After that will toggle between mute/ un-mute
     */
    public void play() {

        if (isPlaying()) {

            if (player.isMute()) {
                player.setMute(false);
            } else {
                player.setMute(true);
            }

        } else {
            lib = new AudioLibrary();

            if (getType() == "AMBIENT") {
                lib.setAudioFolder(lib.AMBIENT);
            } else if (getType() == "MUSIC") {
                lib.setAudioFolder(lib.MUSIC);
            } else { /** Default, incase of misspelled */
                lib.setAudioFolder(lib.AMBIENT);
            }

            path = lib.selectAudio(getCurrentTrack());
            media = new Media(Paths.get(path).toUri().toString());
            player = new MediaPlayer(media);
            player.setCycleCount(-1);
            player.play();
            player.setVolume(currentVolume);
            System.out.println(" Track : " + (1 + currentTrack) + " / " + lib.getTrackList());
            setPlaying(true);
        }
    }

    public void nextTrack() {

        stop();
        if ((currentTrack + 1) < lib.getTrackList()) {
            setCurrentTrack(++currentTrack);
        } else {
            currentTrack = 0;
        }

        play();

    }

    public void changeTrackTo(int selected) {
        stop();
        setCurrentTrack(selected);
        play();
    }

    public void stop() {
        player.stop();

        setPlaying(false);
    }

    public void changeVolume(double vol) {
        player.setVolume(vol);
        saveVolume(vol);
    }

    public void saveVolume(double vol) {
        setCurrentVolume(vol);
    }

    public void setCurrentVolume(double vol) {
        this.currentVolume = vol;
    }

    public int getCurrentTrack() {
        return currentTrack;
    }

    public void setCurrentTrack(int track) {
        this.currentTrack = track;
    }

    public String getType() {
        return type;
    }

    public boolean isPlaying() {
        return playing;
    }

    public void setPlaying(boolean play) {
        this.playing = play;
    }

}
