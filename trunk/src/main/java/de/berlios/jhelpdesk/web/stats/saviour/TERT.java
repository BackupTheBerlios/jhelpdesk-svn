package de.berlios.jhelpdesk.web.stats.saviour;

import java.net.URLDecoder;

public class TERT {

	/**
	 * @param args
	 */
	public static void main( String[] args ) throws Exception {
		System.out.println(
			URLDecoder.decode( "U-%C5%BB", "utf-8" )
		);
	}
}