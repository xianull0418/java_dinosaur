/**
 * @author 杨晨
 * @studentId 1230204886
 */
package com.dinosaur.service;

import java.io.FileNotFoundException;

public class Sound {
    static final String DIR = "music/";
    static final String BACKGROUD = "background.wav";
    static final String JUMP = "jump.wav";
    static final String HIT = "hit.wav";

    public Sound() {
    }

    private static void play(String file, boolean circulate) {
        try {
            MusicPlayer player = new MusicPlayer(file, circulate);
            player.play();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public static void jump() {

        play(DIR+JUMP, false);
    }

    public static void hit() {
        play(DIR+HIT, false);
    }

    public static void backgroud() {
        play(DIR+BACKGROUD, true);
    }
}
