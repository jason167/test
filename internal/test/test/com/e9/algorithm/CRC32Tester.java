package test.com.e9.algorithm;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.e9.framework.util.algorithm.CRC32;

/**
 * @project E9Framework
 * @date 2013-1-16
 * @version 1.0
 * @author Jason
 * 
 * @review_history
 * [2013-1-16] create by Jason
 */
public class CRC32Tester {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link com.e9.framework.util.algorithm.CRC32#crc32(byte[])}.
	 */
	@Test
	public void testCrc32ByteArray() {
		
		assertEquals("e8b7be43", Integer.toHexString(CRC32.crc32("a".getBytes())));
	}

	/**
	 * Test method for {@link com.e9.framework.util.algorithm.CRC32#crc32(int, byte[], int, int)}.
	 */
	@Test
	public void testCrc32IntByteArrayIntInt() {
		byte[] buffer = "a".getBytes();
		String hexString = Integer.toHexString(CRC32.crc32(0, buffer, 0, buffer.length));
		
		System.out.println(hexString);
		
		assertEquals("e8b7be43", hexString);
	}
	

}
