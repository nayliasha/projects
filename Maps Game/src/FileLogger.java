import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileLogger implements Logger {
    private static final String FILE_LOGGER_NAME =  "StudentFileOutput.txt";

    static {

        //create a new File object for FILE_LOGGER_NAME if the file already exists, delete it first use try/catch block
        try {
            File file = new File(FILE_LOGGER_NAME);
            if (file.exists()) { //if file exists is true, then delete file then create new one
                file.delete();
            }
            file.createNewFile();

            if (!file.exists()) { //if file doesn't exist, then create a new file
                file.createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void log (String message) { //message being passed as a parameter
        //create a new FileWriter in append mode write the message to file check the ExpectedOutput files use try-with-resources/catch block
        try (FileWriter fileWriter = new FileWriter(FILE_LOGGER_NAME, true)) {
            fileWriter.write(String.format("%s%n", message));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
