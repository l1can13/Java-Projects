import functions.*;
import functions.basic.Cos;
import functions.basic.Exp;
import functions.basic.Log;
import functions.basic.Sin;
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

    public static void simpleThreads() {
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

//        System.out.println("Задание 1");
//        TabulatedFunction func = new LinkedListTabulatedFunction(-1, 1, 5);
//        for (FunctionPoint p : func) {
//            System.out.println(p);
//        }
//
//        System.out.println("Задание 2");
//        Function cos = new Cos();
//        TabulatedFunction tabFun;
//        tabFun = TabulatedFunctions.tabulate(cos, 0, Math.PI, 11);
//        System.out.println(tabFun.getClass());
//        TabulatedFunctions.setTabulatedFunctionFactory(new LinkedListTabulatedFunction.LinkedListTabulatedFunctionFactory());
//        tabFun = TabulatedFunctions.tabulate(cos, 0, Math.PI, 11);
//        System.out.println(tabFun.getClass());
//        TabulatedFunctions.setTabulatedFunctionFactory(new ArrayTabulatedFunction.ArrayTabulatedFunctionFactory());
//        tabFun = TabulatedFunctions.tabulate(cos, 0, Math.PI, 11);
//        System.out.println(tabFun.getClass());

        System.out.println("Задание 3");
        TabulatedFunction f;
        f = TabulatedFunctions.createTabulatedFunction(ArrayTabulatedFunction.class, 0, 10, 3);
        System.out.println(f.getClass());
        System.out.println(f);
        f = TabulatedFunctions.createTabulatedFunction(ArrayTabulatedFunction.class, 0, 10, new double[] {0, 10});
        System.out.println(f.getClass());
        System.out.println(f);
        f = TabulatedFunctions.createTabulatedFunction(
                LinkedListTabulatedFunction.class,
                new FunctionPoint[] {
                        new FunctionPoint(0, 0),
                        new FunctionPoint(10, 10)
                }
        );
        System.out.println(f.getClass());
        System.out.println(f);
        f = TabulatedFunctions.tabulate(LinkedListTabulatedFunction.class, new Sin(), 0, Math.PI, 11);
        System.out.println(f.getClass());
        System.out.println(f);
//
//        TabulatedFunction f;
//        f = TabulatedFunctions.createTabulatedFunction(
//                ArrayTabulatedFunction.class, 0, 10, 3);
//        System.out.println(f.getClass());
//        System.out.println(f);
//        f = TabulatedFunctions.createTabulatedFunction(
//                ArrayTabulatedFunction.class, 0, 10, new double[] {0, 10});
//        System.out.println(f.getClass());
//        System.out.println(f);
//        f = TabulatedFunctions.createTabulatedFunction(
//                LinkedListTabulatedFunction.class,
//                new FunctionPoint[] {
//                        new FunctionPoint(0, 0),
//                        new FunctionPoint(10, 10)
//                }
//        );
//        System.out.println(f.getClass());
//        System.out.println(f);
//        f = TabulatedFunctions.tabulate(
//                LinkedListTabulatedFunction.class, new Sin(), 0, Math.PI, 11);
//        System.out.println(f.getClass());
//        System.out.println(f);

    }

}
