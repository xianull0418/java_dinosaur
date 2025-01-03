/**
 * @author 杨晨
 * @studentId 1230204886
 */
package com.dinosaur.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;

public class ScoreRecorder {
    private static final String SCOREFILE = "data/source";
    private static int[] scores = new int[3];

    public ScoreRecorder() {
    }

    public static void init() {
        File f = new File(SCOREFILE);
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;

        }

            FileInputStream fis = null;
            InputStreamReader isr = null;
            BufferedReader br = null;

            try {
                fis = new FileInputStream(f);
                isr = new InputStreamReader(fis);
                br = new BufferedReader(isr);
                String value = br.readLine();
                if (value != null && !"".equals(value)) {
                    String[] vs = value.split(",");
                    if (vs.length < 3) {
                        Arrays.fill(scores, 0);
                    } else {
                        for(int i = 0; i < 3; ++i) {
                            scores[i] = Integer.parseInt(vs[i]);
                        }
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    isr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        }


    public static void saveScore() {
        String value = scores[0] + "," + scores[1] + "," + scores[2];
        FileOutputStream fos = null;
        OutputStreamWriter osw = null;
        BufferedWriter bw = null;

        try {
            fos = new FileOutputStream(SCOREFILE);
            osw = new OutputStreamWriter(fos);
            bw = new BufferedWriter(osw);
            bw.write(value);
            bw.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                osw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    public static void addNewScore(int score) {
        int[] tmp = Arrays.copyOf(scores, 4);
        tmp[3] = score;
        Arrays.sort(tmp);
        scores = Arrays.copyOfRange(tmp, 1, 4);
    }

    public static int[] getScores() {
        return scores;
    }
}
