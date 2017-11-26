package org.ananichev;

/**
 * Created by Леонид on 26.11.2017.
 */
public class Data {
    private final String text;
    private final int dst;
    private long startTime;
    private boolean finished;

    public Data(String text, int dst) {
        this.text = text;
        this.dst = dst;
    }

    public int getDst() {
        return dst;
    }

    public String getText() {
        return text;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }
}
