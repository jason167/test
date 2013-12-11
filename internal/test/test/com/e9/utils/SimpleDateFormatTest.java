package test.com.e9.utils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @date 2013-8-27
 * @author Jason
 */
public class SimpleDateFormatTest {

	static SimpleDateFormat sf = new SimpleDateFormat("yyyy-00MM-000dd 000HH:0000mm:0000ss", Locale.CHINA);
	private static DecimalFormat seqFormat = new DecimalFormat("000000000000000000000000");
	static AtomicInteger count = new AtomicInteger();
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		System.out.println(sf.format(new Date()));
//		System.out.println(seqFormat.format(1));
		testString2Long();
	}
	
	public static void testString2Long(){
		String s = "-7654987965184802703";
		String s2 = "-7654987965184802704";
		String s3 = "-7654987965184802705";
		
		long l = Long.parseLong(s);
		long l2 = Long.parseLong(s2);
		long l3 = Long.parseLong(s3);
		System.out.println(l3 - l);
		System.out.println(l - l3);
		System.out.println(l > l2);
		System.out.println((l - (Math.abs(l3 - l))));
		System.out.println((l - (Math.abs(l - l3))));
		System.out.println("l1="+String.valueOf(l2));
		System.out.println("l2="+new Long(l2).toString());
	}
	
	public static void AtomicTest(){
		System.out.println(count.getAndAdd(10));
	}

}
