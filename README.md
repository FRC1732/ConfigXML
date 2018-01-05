# ConfigXML
Code for creating, sending over NetworkTables, and using XML files to configure a robot

I haven't finished putting together the jar that would run on the driverstation, but the code that would run on the robot is ready to go - it is all that you would need to add. This project doesn't have the network table libraries though, so that code is commented out.

By using XML it's a little wordier, but much more versatile - if you look at the example usage in the ConfigTest class in the Roborio folder, you can see what I mean. In order to add more configurations/more options, all you need to do is change the XML file on the driverstation and add to your code which parts of the config you want to search for. Very easy to add new components, new variables, etc. No need to write more parsing code or anything.

This isn't total production quality, but because this isn't a real example I didn't do some of the stuff you should to with the robot.
