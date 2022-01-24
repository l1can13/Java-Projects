package threads;

import functions.basic.Log;

public class SimpleGenerator implements Runnable {

    private Task t;

    public SimpleGenerator(Task task) {
        this.t = task;
    }

    public void run() {
        for (int i = 0; i < t.getTasks(); ++i) {
            synchronized (t) {
                t.func = new Log(1 + (Math.random() * 9));
                t.a = Math.random() * 100;
                t.b = 100 + Math.random() * 100;
                t.step = Math.random();
                System.out.println("Source left = " + t.a + " right border = " + t.b + " step = " + t.step);
            }
        }
    }
}
