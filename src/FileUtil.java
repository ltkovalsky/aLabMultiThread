import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtil {

    public File getFile() {
        File result = new File("Result.txt");
        if (!result.exists()) {
            try {
                result.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return result;
    }

    public synchronized void writeIfNotExists(int num, File file) {
        System.out.println(Thread.currentThread().getName() + " trying to write " + num);
        append(num + " ", file);
    }

    public void append(String str, File file) {
        try (FileWriter writer = new FileWriter(file, true)) {
            writer.write(str);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
