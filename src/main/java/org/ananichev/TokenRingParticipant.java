package org.ananichev;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by Леонид on 25.11.2017.
 */
public class TokenRingParticipant implements Runnable {
    private final int id;
    private final Queue<Data> deque;
    private final List<TokenRingParticipant> participants;


    public TokenRingParticipant(int id, List<TokenRingParticipant> participants) {
        this.id = id;
        this.participants = participants;
        deque = new ConcurrentLinkedQueue();
    }

    public void updateDeque(Data data) {
        deque.add(data);
    }

    public void sendData(Data data) {
        data.setStartTime(System.nanoTime());
        participants.get((id + 1) % participants.size()).updateDeque(data);
        //System.out.println("my id " + id + " send " + data.getText() + " to " + (id + 1) % participants.size());
    }

    public void run() {
        System.out.println("Runned " + id);
        while (true) {
            synchronized (this) {
                if (!deque.isEmpty()) {
                    Data data = deque.poll();
                    if (data.getDst() == id) {
                        long time = System.nanoTime() - data.getStartTime();
                        data.setFinished(true);
                        //System.out.println("Finished " + data.getText() + " with time " + time);
                    } else {
                        sendData(data);
                    }
                }
            }
        }
    }
}
