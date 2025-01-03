/**
 * @author 杨晨
 * @studentId 1230204886
 */
package com.dinosaur.service;

import javax.sound.sampled.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MusicPlayer implements Runnable {
    File soundFile;
    Thread thread;
    boolean circulate;

    public MusicPlayer(String filepath, boolean circulate) throws FileNotFoundException {
        this.circulate = circulate;
        this.soundFile = new File(filepath);
        if (!this.soundFile.exists()) {
            throw new FileNotFoundException(filepath + "未找到");
        }
    }

    public void run() {
        byte[] auBuffer = new byte[1024*128];//创建一个128KB缓冲区

        do {
            AudioInputStream audioInputStream = null;
            SourceDataLine auline = null;

            try {
                audioInputStream = AudioSystem.getAudioInputStream(this.soundFile);
                AudioFormat format = audioInputStream.getFormat();
                DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
                auline = (SourceDataLine)AudioSystem.getLine(info);
                auline.open(format);
                auline.start();
                int byteCount = 0;

                while(byteCount != -1) {
                    byteCount = audioInputStream.read(auBuffer, 0, auBuffer.length);
                    if (byteCount >= 0) {
                        auline.write(auBuffer, 0, byteCount);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (UnsupportedAudioFileException e) {
                e.printStackTrace();
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            } finally {
                auline.drain();
                auline.close();
            }
        } while(this.circulate);

    }

    public void play() {
        this.thread = new Thread(this);
        this.thread.start();
    }

    public void stop() {
        this.thread.stop();
    }
}