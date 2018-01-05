import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

/*
 * This is the code that would run somewhere on the roborio
 * 
 * It is meant to simulate loading a config file. This could be in any part of the code, it's not restricted to anywhere.
 */
public class ConfigTest {

	public static final String pathToSavedConfig = "Roborio/";
	public static final String savedConfigName = "SavedConfig";
	public static final String tableName = "XML Configs";
	public static final String configTableEntry = "Robot Config";

	public static void main(String[] args) throws TransformerFactoryConfigurationError, TransformerException {

		/*
		 * DON'T SKIP OVER THE COMMENTS READ THEM IN ORDER!
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 */

		// wpi added a button for the dashboard so we can use that to trigger loading
		// of the config file from network tables

		// so imagine someone presses the button and it triggers this code

		// this code will change settings for motors and stuff so if we do something
		// like this we would have to make sure no other code is trying to use the
		// objects that are getting reset. Probably just only run this in disabled mode.
		// Will need to structure code so that this isn't a problem. That will probably
		// help code quality anyway, by forcing us to decouple more of the program.

		// we'll also pretend that there is already an xml config document saved on the
		// roborio. I'm not sure how we will set up this initial xml config document.
		// Maybe the code will wait to be provided with one if it doesn't have one.

		// I don't have the network tables library on here so this will all be commented
		// out

		// String savedRobotConfig = DOMutils.fileToString(pathToSavedConfig +
		// savedConfigName);
		// String robotConfig =
		// NetworkTableInstance.getDefault().getTable(tableName).getEntry("configTableEntry).getString(savedConfig);

		// ^ that method requires a default value as the argument to getString()

		// now pretend that getting the config String worked. The below lines are just
		// meant to simulate it working by grabbing the config file from the
		// DriverStation folder. They wouldn't be a part of the code on the roborio
		String choice = "";
		try {
			BufferedReader reader = new BufferedReader(new FileReader("DriverStation/ConfigFileChoice"));
			choice = reader.readLine();
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String pathToFile = "DriverStation/" + choice;
		String robotConfig = "";
		try {
			robotConfig = ConfigUtils.fileToString(pathToFile);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// so now pretend that we got the String 'robotConfig' from the NetworkTables

		Document robotDoc = null;
		try {
			robotDoc = ConfigUtils.makeDOMdoc(robotConfig);
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
			System.err.println("ERROR! Check your xml file and try again");
			return; // this would just end the loading process, pretend we're in a method somewhere
					// in robot code
		}

		Drivetrain drivetrain = new Drivetrain(); // pretend this was created earlier and now that the config file is
													// ready we are configuring the drive train

		Element drivetrainNode = (Element) robotDoc.getDocumentElement().getElementsByTagName("Drivetrain").item(0);
		drivetrain.configure(drivetrainNode);

		System.out.println("Drivetrain constructed from XML config:");
		System.out.println(drivetrain);

		// save the config on the roborio

		ConfigUtils.saveDom(robotDoc, pathToSavedConfig, savedConfigName);
	}

}