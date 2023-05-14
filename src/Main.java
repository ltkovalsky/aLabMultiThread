import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    static AtomicInteger num = new AtomicInteger(1);

    public static void main(String[] args) {
        /*
            Solution for situation where different threads have common iteration counter:
                                counter
                                  /\
                                 /  \
                                /    \
                               /      \
                        processing  processing
                               \      /
                                \    /
                                 file
         */

        FileUtil fu = new FileUtil();
        File result = fu.getFile();

        Runnable a = () -> {
            File tFile = new File(Thread.currentThread().getName() + ".txt");
            if (!tFile.exists()) {
                try {
                    tFile.createNewFile();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            boolean flag;
            int threadLocalValue;
            do {
                threadLocalValue = num.incrementAndGet();
                flag = true;

                for (int j = 2; j < threadLocalValue; j++) {
                    if ((threadLocalValue % j == 0)) {
                        flag = false;
                        break;
                    }
                }

                if (flag) {
                    fu.writeIfNotExists(threadLocalValue, result);
                    fu.append(threadLocalValue + " ", tFile);
                }
            } while (threadLocalValue < 1000000);
        };

        Thread t1 = new Thread(a, "Thread1");
        Thread t2 = new Thread(a, "Thread2");

        t1.start();
        t2.start();

    }


}