package main;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;

public class Sound 
{
    private Clip clip;

    public static Sound mouseHover = new Sound("/res/sounds/sfx/hover_sound.wav");
  
    public Sound (String fileName) 
    {
        try 
        {
            AudioInputStream ais = AudioSystem.getAudioInputStream(Sound.class.getResource(fileName));
            clip = AudioSystem.getClip();
            clip.open(ais);
            
			/*AudioFormat format = ais.getFormat();
			DataLine.Info info = new DataLine.Info(Clip.class, format);
			Clip clip = (Clip) AudioSystem.getLine(info);
			clip.open(ais);
			FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			gainControl.setValue(-10);
			clip.start();*/
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }

    public void play()
    {
        try 
        {
            if (clip != null) 
            {
                new Thread() 
                {
                    public void run() 
                    {
                        synchronized (clip) 
                        {
                            clip.stop();
                            clip.setFramePosition(0);
                            clip.start();
                        }
                    }
                }.start();
            }
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }

    }

    public void stop()
    {
        if(clip == null) return;

        clip.stop();
    }

    public void loop() 
    {
        try 
        {
            if (clip != null) 
            {
                new Thread()
                {
                   public void run() 
                   {
                        synchronized (clip) 
                        {
                            clip.stop();
                            clip.setFramePosition(0);
                            clip.loop(Clip.LOOP_CONTINUOUSLY);
                        }
                    }
                }.start();
            }
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }

    }

    public boolean isActive()
    {
        return clip.isActive();
    }
    
    /**
     * This method returns whether this sound is finished playing or not
     * @return true if the sound is currently playing
     * 		   false if the sound is not playing
     */
    public boolean isPlaying()
    {
    	return (clip.getMicrosecondPosition() < clip.getMicrosecondLength() && clip.getMicrosecondPosition() != 0);
    }
}