package threads;

import functions.Functions;

public class SimpleIntegrator implements Runnable{

    private Task t;

    public SimpleIntegrator(Task task) {
        this.t = task;
    }

    public void run() {
        for (int i = 0; i < t.getTasks(); ++i) {
            if (t.func == null) {
                continue;
            }
            synchronized (t) {
                double result = Functions.integration(t.func, t.a, t.b, t.step);
                System.out.println("Result left border = " + t.a + " right border = " + t.b + " step = " + t.step + " result of integration = " + result);
            }
        }
    }

}
