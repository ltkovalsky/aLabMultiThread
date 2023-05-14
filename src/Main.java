import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        /*
            Solution for situation when we have 2 different source of data (counters in threads) that must be merged into 1 resource (result.txt)

                                    source1     source2
                                        |          |
                                   processing  processing
                                         \        /
                                          \      /
                                            file

            For another solution look "/atomic" branch
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
            int num = 2;

            do {
                flag = true;

                for (int j = 2; j < num; j++) {
                    if ((num % j == 0)) {
                        flag = false;
                        break;
                    }
                }

                if (flag) {
                    int lastNumInFile = fu.readLastNumInFile(result);
                    if (lastNumInFile < num) {
                        if (fu.writeIfNotExists(num, result)) {
                            fu.append(num + " ", tFile);
                        }
                    } else {
                        num = lastNumInFile;
                    }
                }
                num++;
            } while (num < 1000000);
        };


        Thread t1 = new Thread(a, "Thread1");
        Thread t2 = new Thread(a, "Thread2");

        t1.start();
        t2.start();

    }


}