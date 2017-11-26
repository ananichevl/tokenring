package org.ananichev;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Леонид on 26.11.2017.
 */
public class TokenRing {

    private static final int numberPackages = 1000000;
    private static final int numberThreads = 10;

    public static void main(String[] args) {
        List<TokenRingParticipant> participants = new ArrayList<TokenRingParticipant>();
        List<Data> data = new ArrayList<Data>(numberPackages);

        for (int i = 0; i < numberPackages; i++) {
            double r = numberThreads * Math.random();
            int random = (int) r;
            data.add(new Data("test" + i, random));
        }

        for (int i = 0; i < numberThreads; i++) {
            TokenRingParticipant participant = new TokenRingParticipant(i, participants);
            participants.add(participant);
            new Thread(participant).start();
        }

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long starttime = System.nanoTime();
        for (int i = 0; i < numberPackages; i++) {
            //System.out.println(System.nanoTime() - starttime);
            if (System.nanoTime() - starttime > 100000000L) {
                synchronized (data) {
                    for (int j=0; j < numberPackages; j++) {
                        if (!data.get(j).isFinished()) {
                            System.out.println(j);
                            break;
                        }
                    }
                }
                System.exit(1);
            }
            participants.get(data.get(i).getDst()).sendData(data.get(i));
        }

        System.out.println("Finished");
    }
}
