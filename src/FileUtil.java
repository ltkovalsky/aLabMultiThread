import java.io.*;

import static java.util.Objects.isNull;

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

    public synchronized boolean writeIfNotExists(int num, File file) {

        boolean doWrite;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();
            int lastNumInFile;
            if (!isNull(line)) {
                int lastWhiteSpaceIdx = line.stripTrailing().lastIndexOf(" ");
                String strForParseInt = lastWhiteSpaceIdx == -1 ? line.strip() : line.substring(lastWhiteSpaceIdx).strip();
                lastNumInFile = Integer.parseInt(strForParseInt);
            } else {
                lastNumInFile = 0;
            }

            doWrite = isNull(line) || lastNumInFile < num;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (doWrite) {
            System.out.println(Thread.currentThread().getName() + " trying to write " + num);
            append(num + " ", file);
        }
        return doWrite;
    }

    public void append(String str, File file) {
        try (FileWriter writer = new FileWriter(file, true)) {

            writer.write(str);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public FileUtil FileUtil() {
        return this;
    }
}
