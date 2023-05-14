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
        boolean result = false;
        int lastNumInFile = readLastNumInFile(file);
        if (lastNumInFile < num) {
            System.out.println(Thread.currentThread().getName() + " trying to write " + num);
            append(num + " ", file);
            result = true;
        }
        return result;
    }

    public int readLastNumInFile(File file) {
        int lastNumInFile = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();

            if (!isNull(line)) {
                int lastWhiteSpaceIdx = line.stripTrailing().lastIndexOf(" ");
                String strForParseInt = lastWhiteSpaceIdx == -1 ? line.strip() : line.substring(lastWhiteSpaceIdx).strip();
                lastNumInFile = Integer.parseInt(strForParseInt);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return lastNumInFile;
    }

    public void append(String str, File file) {
        try (FileWriter writer = new FileWriter(file, true)) {
            writer.write(str);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
