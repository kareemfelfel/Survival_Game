package managers;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.io.File;

public class GameAudio implements Runnable {

    private File music;
    private AudioInputStream audioInput;
    private Clip clip;
    private Boolean loop;

    public GameAudio(String Path, Boolean pLoop)
    {
        music = new File(Path);
        Thread audioloop = new Thread(this);
        audioloop.start();
        loop = pLoop;
    }
    @Override
    public void run() {
        //music = new File("Audio/TangoMusic (1).wav");
        try {
            audioInput = AudioSystem.getAudioInputStream(music);
            clip = AudioSystem.getClip();
            clip.open(audioInput);
            clip.start();
            if(loop)
                clip.loop(Clip.LOOP_CONTINUOUSLY);



        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null,"Music messed up " + ex.getMessage());
        }
    }
}
