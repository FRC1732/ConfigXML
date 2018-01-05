import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

/*
 * This is the code that puts the xml config into the network tables
 * 
 * This code would be in an excutable jar in a folder on the driver station desktop.
 * 
 * To use it, you make sure the xml config is correct, then select your choice in the "ConfigFileChoice" file, then run the jar (double click it on windows)
 * 
 */

public class UploadCongurations {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		String choice = new BufferedReader(new FileReader("DriverStation/ConfigFileChoice")).readLine();
		String pathToFile = "DriveStation/" + choice;
		String file = fileToString(pathToFile);
		// if there's an error, maybe make a graphical display eventually

		// now to upload it to the network tables:

		// NetworkTableInstance.getDefault().startClientTeam(1732);
		// NetworkTableInstance.getDefault().getTable("XML Configs").getEntry("Robot
		// Config").setString(file);

		// ^ this line makes a new table so we can view it on its own tab in the
		// shuffleboard dashboard. This also puts the file String into the NetworkTable

		// (It's commented out because I don't have the network tables library added
		// yet)
	}

	public static String fileToString(String pathToFile) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(pathToFile));
		return new String(encoded, Charset.defaultCharset());
	}
}
