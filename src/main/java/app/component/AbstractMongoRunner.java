package app.component;

import java.util.concurrent.atomic.AtomicBoolean;

public abstract class AbstractMongoRunner implements Runnable {

    private AtomicBoolean running = new AtomicBoolean(true);

    private static int WAIT_MILISECONDS = 100;

    public void setRunning(boolean running) {
        setRunning(new AtomicBoolean(running));
    }

    public void setRunning(AtomicBoolean running) {
        this.running = running;
    }

    public boolean isRunning() {
        return running.get();
    }

    protected void threadBabySleep() {
        try {
            Thread.sleep(WAIT_MILISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
