/**
 * ES234317-Algorithm and Data Structures
 * Semester Ganjil, 2024/2025
 * Group Capstone Project
 * Group #1
 * 1 - 5026231105 - Mirza Fathi Taufiqurrahman
 * 2 - 5026231209 - Adityo Rafi Wardhana
 * 3 - 5026231198 - Muhammad Fikri Khalilullah
 */

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

public enum SoundEffect {
    dog_barking("mixkit-dog-barking-twice-1.wav"),
    chick("short-chick-sound-171389.wav"),
    game_over("game-over-2-sound-effect-230463.wav"),

    game_draw("game_draw.wav");

    public static enum Volume {
        MUTE, LOW, MEDIUM, HIGH
    }

    public static Volume volume = Volume.LOW;
    private Clip clip;

    SoundEffect(String soundFileName) {
        try {
            URL url = getClass().getClassLoader().getResource(soundFileName);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void play() {
        if (volume != Volume.MUTE) {
            if (clip.isRunning())
                clip.stop();
            clip.setFramePosition(0);
            clip.start();
        }
    }

    static {
        values(); // Preload all sounds
    }
}