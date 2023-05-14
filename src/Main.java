import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {

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
            for (int i = 2; i <= 1000000; i++) {
                flag = true;

                for (int j = 2; j < i; j++) {
                    if ((i % j == 0)) {
                        flag = false;
                        break;
                    }
                }

                if (flag) {
                    if (fu.writeIfNotExists(i, result)) {
                        fu.append(i + " ", tFile);
                    }
                }
            }
        };


        Thread t1 = new Thread(a, "Thread1");
        Thread t2 = new Thread(a, "Thread2");

        t1.start();
        t2.start();

    }


}