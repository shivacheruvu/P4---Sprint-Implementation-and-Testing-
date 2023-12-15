import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.channels.ScatteringByteChannel;
import java.util.Scanner;

public class PitchDataReader {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // Assuming the 'pitch_data_files' directory is in the parent directory of where the program runs.
        File dir = new File("test_cases");
        File[] files = dir.listFiles((d, name) -> name.endsWith(".csv"));
        // ...
        
        if (!dir.exists() || !dir.isDirectory()) {

            System.out.println("Directory does not exist or is not a directory: " + dir.getAbsolutePath());
             scanner.close();
             return;
            }
            
    
            if (files == null) {
                System.out.println("No CSV files found in directory: " + dir.getAbsolutePath());
                 scanner.close();
                 return;
                }



// Proceed with file processing...

// ...

      
// places batter and strikezone 
        for (int i = 0; i < files.length && i < 10; i++) {
            File file = files[i];
            System.out.println("Reading file: " + file.getName());

            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                // Read and store the data points in variables with suitable names
                String[] frontCloseCorner = br.readLine().split(",");
                int frontCloseCornerX = Integer.parseInt(frontCloseCorner[0].trim());
                int frontCloseCornerY = Integer.parseInt(frontCloseCorner[1].trim());
                
                String[] backCloseCorner = br.readLine().split(",");
                int backCloseCornerX = Integer.parseInt(backCloseCorner[0].trim());
                int backCloseCornerY = Integer.parseInt(backCloseCorner[1].trim());
                
                String[] point = br.readLine().split(",");
                int pointX = Integer.parseInt(point[0].trim());
                int pointY = Integer.parseInt(point[1].trim());
                
                String[] backFarCorner = br.readLine().split(",");
                int backFarCornerX = Integer.parseInt(backFarCorner[0].trim());
                int backFarCornerY = Integer.parseInt(backFarCorner[1].trim());
                
                String[] frontFarCorner = br.readLine().split(",");
                int frontFarCornerX = Integer.parseInt(frontFarCorner[0].trim());
                int frontFarCornerY = Integer.parseInt(frontFarCorner[1].trim());
                
                String[] shoulder1 = br.readLine().split(",");
                int shoulder1X = Integer.parseInt(shoulder1[0].trim());
                int shoulder1Y = Integer.parseInt(shoulder1[1].trim());
                
                String[] shoulder2 = br.readLine().split(",");
                int shoulder2X = Integer.parseInt(shoulder2[0].trim());
                int shoulder2Y = Integer.parseInt(shoulder2[1].trim());
                
                String[] knee1 = br.readLine().split(",");
                int knee1X = Integer.parseInt(knee1[0].trim());
                int knee1Y = Integer.parseInt(knee1[1].trim());
                
                String[] knee2 = br.readLine().split(",");
                int knee2X = Integer.parseInt(knee2[0].trim());
                int knee2Y = Integer.parseInt(knee2[1].trim());

                
                

                // Future test cases batter over plate calculation
                if((!(Math.abs(knee1X - pointX) <= 3))){
                    
                    int adjustment_val = Math.abs(knee1X - pointX);
                    if(knee1X > pointX){
                        knee1X = knee1X - adjustment_val;
                        knee2X = knee2X - adjustment_val;
                        shoulder1X = shoulder1X -adjustment_val;
                        shoulder2X = shoulder2X - adjustment_val;
                    }else if(knee1X < pointX){
                        knee1X = knee1X + adjustment_val;
                        knee2X = knee2X + adjustment_val;
                        shoulder1X = shoulder1X +adjustment_val;
                        shoulder2X = shoulder2X + adjustment_val;
                    }

                }


                // Assuming the pitch data starts at line 10 and continues until the end of the file.
                String line;
                Boolean checker = false;
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
                     //                    309       316             316           351            221            283          283             299 Pitch 5 pass Line 19
                     //                    309       314             314           351            221            320         320         299 Pitch 1 Fail Line 22

                     
                    boolean outside_pixel_threshold = Math.abs(ballLeftX - ballRightX) != 9;

                    boolean PureStrike = (knee2X <= ballCenterX && ballCenterX <= knee1X) && (shoulder1Y <= ballCenterY &&  ballCenterY <= knee1Y)  && !(outside_pixel_threshold);

                    boolean edgePresent = (((knee2X <= ballLeftX && ballLeftX <= knee1X) && (shoulder1Y <= ballLeftY &&  ballLeftY <= knee1Y)) || ((knee2X <= ballRightX && ballRightX <= knee1X) && (shoulder1Y <= ballRightY &&  ballRightY <= knee1Y))) && !(outside_pixel_threshold);
                    
                    if(PureStrike || edgePresent){
                        checker = true;
                        break;
                    }
                    

            }

                if(checker){
                    System.out.println("Strike");
                }else{
                    System.out.println("Ball");
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
