import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class PitchDataReader {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // Assuming the 'pitch_data_files' directory is in the parent directory of where the program runs.
        File dir = new File("../pitch_data_files");
        File[] files = dir.listFiles((d, name) -> name.endsWith(".csv"));

        for (int i = 0; i < files.length && i < 10; i++) {
            File file = files[i];
            System.out.println("Reading file: " + file.getName());

            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                // Read and store the data points in variables with suitable names
                String[] frontCloseCorner = br.readLine().split(",");
                String[] backCloseCorner = br.readLine().split(",");
                String[] point = br.readLine().split(",");
                String[] backFarCorner = br.readLine().split(",");
                String[] frontFarCorner = br.readLine().split(",");
                String[] shoulder1 = br.readLine().split(",");
                String[] shoulder2 = br.readLine().split(",");
                String[] knee1 = br.readLine().split(",");
                String[] knee2 = br.readLine().split(",");

                // Assuming the pitch data starts at line 10 and continues until the end of the file.
                String line;
                while ((line = br.readLine()) != null) {
                    String[] pitchData = line.split(",");
                    // Process pitch data. For example:
                    long timestamp = Long.parseLong(pitchData[0]);
                    int ballCenterX = Integer.parseInt(pitchData[1]);
                    int ballCenterY = Integer.parseInt(pitchData[2]);
                    int ballLeftX = Integer.parseInt(pitchData[3]);
                    int ballLeftY = Integer.parseInt(pitchData[4]);
                    int ballRightX = Integer.parseInt(pitchData[5]);
                    int ballRightY = Integer.parseInt(pitchData[6]);

                    // You can now use these variables as needed for further processing.
                }

                System.out.println("Finished processing file: " + file.getName());
            } catch (IOException e) {
                System.err.println("Error reading file: " + file.getName());
                e.printStackTrace();
            }

            System.out.println("Press Enter to continue to the next file...");
            scanner.nextLine();
        }

        scanner.close();
    }
}
