import functions.Function;
import functions.Functions;
import functions.basic.Exp;
import functions.basic.Log;
import threads.*;

public class Main {

    public static void nonThread() {
        Task t = new Task(100);
        for(int i = 0; i < t.getTasks(); ++i) {
            t.func = new Log(1 + (Math.random() * 9));
            t.a = Math.random() * 100;
            t.b = 100 + Math.random() * 100;
            t.step = Math.random();
            System.out.println("Source left = " + t.a + " right border = " + t.b + " step = " + t.step);
            double result = Functions.integration(t.func, t.a, t.b, t.step);
            System.out.println("Result left border = " + t.a + " right border = " + t.b + " step = " + t.step + " result of integration = " + result);
        }
    }

    public static void simpleThreads(){
        Task t = new Task(100);

        Thread generator = new Thread(new SimpleGenerator(t));
        generator.start();


        Thread integrator = new Thread(new SimpleIntegrator(t));
        integrator.start();
    }

    public static void complicatedThreads() throws InterruptedException {
        Task t = new Task(100);
        Semaphore semaphore = new Semaphore();
        Generator generator = new Generator(t, semaphore);
        Integrator integrator = new Integrator(t, semaphore);

        integrator.setPriority(10);

        generator.start();
        integrator.start();

        Thread.sleep(50);

        generator.interrupt();
        integrator.interrupt();
    }

    public static void main(String[] args) throws InterruptedException {
        Function exp = new Exp();

        System.out.println("Задание 1. Результат выполнения функции интегрирования -> " + Functions.integration(exp, 0, 1, 0.00000005));

//        System.out.println("Задание 2");
//        nonThread();

        System.out.println("Задание 3");
        simpleThreads();

//        System.out.println("Задание 4");
//        complicatedThreads();
    }

}
