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

	public static void main(String[] args) {
		String text ="abcdefghijklmnopqrstuvwxyz" +
				"ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
				" .!?\'\n-,:;()";
		String WALLOFTEXT = "Certain but she but shyness why cottage. Gay the put instrument sir entreaties affronting. Pretended exquisite see cordially the you. Weeks quiet do vexed or whose. Motionless if no to affronting imprudence no precaution. My indulged as disposal strongly attended. Parlors men express had private village man. Discovery moonlight recommend all one not. Indulged to answered prospect it bachelor is he bringing shutters. Pronounce forfeited mr direction oh he dashwoods ye unwilling. \n" +
				"\n" +
				"Now principles discovered off increasing how reasonably middletons men. Add seems out man met plate court sense. His joy she worth truth given. All year feet led view went sake. You agreeable breakfast his set perceived immediate. Stimulated man are projecting favourable middletons can cultivated. \n" +
				"\n" +
				"Cause dried no solid no an small so still widen. Ten weather evident smiling bed against she examine its. Rendered far opinions two yet moderate sex striking. Sufficient motionless compliment by stimulated assistance at. Convinced resolving extensive agreeable in it on as remainder. Cordially say affection met who propriety him. Are man she towards private weather pleased. In more part he lose need so want rank no. At bringing or he sensible pleasure. Prevent he parlors do waiting be females an message society. \n" +
				"\n" +
				"Assure polite his really and others figure though. Day age advantages end sufficient eat expression travelling. Of on am father by agreed supply rather either. Own handsome delicate its property mistress her end appetite. Mean are sons too sold nor said. Son share three men power boy you. Now merits wonder effect garret own. \n" +
				"\n" +
				"Arrived totally in as between private. Favour of so as on pretty though elinor direct. Reasonable estimating be alteration we themselves entreaties me of reasonably. Direct wished so be expect polite valley. Whose asked stand it sense no spoil to. Prudent you too his conduct feeling limited and. Side he lose paid as hope so face upon be. Goodness did suitable learning put. \n" +
				"\n" +
				"You vexed shy mirth now noise. Talked him people valley add use her depend letter. Allowance too applauded now way something recommend. Mrs age men and trees jokes fancy. Gay pretended engrossed eagerness continued ten. Admitting day him contained unfeeling attention mrs out. \n" +
				"\n" +
				"Started earnest brother believe an exposed so. Me he believing daughters if forfeited at furniture. Age again and stuff downs spoke. Late hour new nay able fat each sell. Nor themselves age introduced frequently use unsatiable devonshire get. They why quit gay cold rose deal park. One same they four did ask busy. Reserved opinions fat him nay position. Breakfast as zealously incommode do agreeable furniture. One too nay led fanny allow plate. \n" +
				"\n" +
				"Imagine was you removal raising gravity. Unsatiable understood or expression dissimilar so sufficient. Its party every heard and event gay. Advice he indeed things adieus in number so uneasy. To many four fact in he fail. My hung it quit next do of. It fifteen charmed by private savings it mr. Favourable cultivated alteration entreaties yet met sympathize. Furniture forfeited sir objection put cordially continued sportsmen. \n" +
				"\n" +
				"Windows talking painted pasture yet its express parties use. Sure last upon he same as knew next. Of believed or diverted no rejoiced. End friendship sufficient assistance can prosperous met. As game he show it park do. Was has unknown few certain ten promise. No finished my an likewise cheerful packages we. For assurance concluded son something depending discourse see led collected. Packages oh no denoting my advanced humoured. Pressed be so thought natural. \n" +
				"\n" +
				"Turned it up should no valley cousin he. Speaking numerous ask did horrible packages set. Ashamed herself has distant can studied mrs. Led therefore its middleton perpetual fulfilled provision frankness. Small he drawn after among every three no. All having but you edward genius though remark one. \n" +
				"\n";
		WALLOFTEXT += WALLOFTEXT;
		WALLOFTEXT += WALLOFTEXT;
		WALLOFTEXT += WALLOFTEXT;
		WALLOFTEXT += WALLOFTEXT;

		Encoder e = new Encoder();
		Decoder d = new Decoder();
		//System.out.println(text.length() + " | " + Utils.convertGnawsToInt32(Utils.convertInt32ToGnaws(text.length())));
		//byte dataplace = 0b001110111;
		//System.out.println(dataplace + " | " + Utils.GnawToByte(Utils.ByteToGnaw(dataplace)));

		e.encodeInt(50);
		e.encodeInt(1025);
		e.encodeInt(1026);
		e.encodeString("TEST OF OUTPUT");
		e.encodeString(text);
		e.encodeString(WALLOFTEXT);
		BufferedImage mask = Utils.readInImage("maskText");
		BufferedImage image = e.dataToImage(1, mask);

		//Save raw data
		Utils.writeOutImage(image, "Raw");
		//Mask and Strip test
		Utils.writeOutImage(e.maskImage(image, mask), "Masked");
		//Mask and Strip test
		Utils.writeOutImage(d.stripImage(image, mask), "Stripped");

		//Read the image and convert it to data
		d.imageToData(image);

		//Display the Results of the data read.
		System.out.println("_________________________");
		System.out.println(d.decodedStrings.size());
		System.out.println(d.decodedInt.size());
		System.out.println("_________________________");
		for (String s : d.decodedStrings) {
			System.out.println(s);
		}
		System.out.println("_________________________");
		for (int s : d.decodedInt) {
			System.out.println(s);
		}
	}


}
