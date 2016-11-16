package com.gamingutils.source;

import com.gamingutils.source.core.Decoder;
import com.gamingutils.source.core.Encoder;
import com.gamingutils.source.utils.DecriptionTypes;
import com.gamingutils.source.utils.Utils;

import java.awt.image.BufferedImage;

/**
 * Created by Jordan Laptop on 9/08/2016.
 */
public class Source {

	public static void main(String[] args){

		String HACKATHON = "Get Hyped! \n" +
				"\n" +
				"Thrilled to announce Code Networks Winter Hackathon. Lets make it huge. We will be kitting out J Block for the event. Prizes, sponsor announcements and more details will be coming soon. \n" +
				"\n" +
				"Last years Hackathon was a great result, but we intentionally kept it smaller than we wanted because it was our first time hosting such an event. We have a lot more space, and hopefully budget, this time around so go HAM with inviting your friends, enemies and even people you disagree with Tabs VS Spaces. \n" +
				"\n" +
				"http://www.eventbrite.com/e/the-code-network-winter-hackathon-tickets-26368697484?aff=fb\n";

		int hl = HACKATHON.length();
		Encoder e = new Encoder();
		Decoder d = new Decoder();

		HACKATHON += HACKATHON;
		HACKATHON += HACKATHON;
		HACKATHON += HACKATHON;
		HACKATHON += HACKATHON;
		HACKATHON += HACKATHON;
		HACKATHON += HACKATHON;
		HACKATHON += HACKATHON;
		HACKATHON += HACKATHON;
		HACKATHON += HACKATHON;
		HACKATHON += HACKATHON;
		HACKATHON += HACKATHON;
		HACKATHON += HACKATHON;
		HACKATHON = HACKATHON.substring(0,2105260);//*/
		e.encodeInt(42);
		e.encodeString(HACKATHON);
		BufferedImage mask = Utils.readInImage("hackathon");
		//BufferedImage mask = Utils.readInImage("maskText");
		//BufferedImage mask = Utils.readInImage("starmask");
		BufferedImage image = e.dataToImage(1, mask);

		//Save raw data
		//Utils.writeOutImage(image, "Raw");
		//Mask and Strip test
				//Utils.writeOutImage(image, "HackathonCN"); //e.maskImage(image, mask), "HackathonCN");
				//d.imageToData(image);
				Utils.writeOutImage(e.maskImage(image, mask), "HackathonCN");
		//Mask and Strip test
		//Utils.writeOutImage(d.stripImage(image, mask), "Stripped");

		//Display the Results of the data read.

		System.out.println("_________________________");
		System.out.println(d.decodedStrings.size());
		System.out.println(d.decodedInt.size());
		System.out.println("_________________________");
		for (String s : d.decodedStrings) {
			System.out.println(s.substring(0, hl));
		}
		System.out.println("_________________________");
		for (int s : d.decodedInt) {
			System.out.println(s);
		}//*/
	}


}
