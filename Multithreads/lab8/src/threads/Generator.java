package threads;

import functions.basic.Log;

public class Generator extends Thread {

    private Task t;
    private Semaphore semaphore;
    private boolean isRun = false;

    public Generator(Task task, Semaphore sem) {
        this.t = task;
        this.semaphore = sem;
    }

    public void run() {
        isRun = true;
        for (int i = 0; i < t.getTasks() && isRun; ++i) {
            try {
                t.func = new Log(1 + (Math.random() * 9));
                semaphore.beginWrite();
                t.a = Math.random() * 100;
                t.b = 100 + Math.random() * 100;
                t.step = Math.random();
                semaphore.endWrite();
                System.out.println("Source left = " + t.a + " right border = " + t.b + " step = " + t.step);
            } catch (InterruptedException e) {
                System.out.println("Генератор прервали во время ожидания, работа завершена корректно");
            }
        }
    }

    public void interrupt() {
        super.interrupt();
        isRun = false;
    }

}
