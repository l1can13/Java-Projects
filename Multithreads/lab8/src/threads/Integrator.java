package threads;

import functions.Functions;

public class Integrator extends Thread {

    private Task t;
    private Semaphore semaphore;
    private boolean isRun = false;

    public Integrator(Task task, Semaphore sem) {
        this.t = task;
        this.semaphore = sem;
    }

    public void run() {
        double result;
        isRun = true;
        for (int i = 0; i < t.getTasks() && isRun; ++i) {
            try {
                semaphore.beginRead();
                result = Functions.integration(t.func, t.a, t.b, t.step);
                semaphore.endRead();
                System.out.println("Result left border = " + t.a + " right border = " + t.b + " step = " + t.step + " result of integration = " + result);
            } catch (InterruptedException e) {
                System.out.println("Интегратор прервали во время ожидания, работа завершена корректно");
            }
        }
    }

    public void interrupt() {
        super.interrupt();
        isRun = false;
    }

}
