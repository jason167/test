package test.com.e9.utils;

import com.e9.framework.util.Common;

/**
 * @date 2013-8-29
 * @author Jason
 */
public class BitSequenceTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int i = 123;
		byte[] bytes = new byte[4];
		
		// 1
		System.out.println("1");
		i2b(i, bytes, 0, 4);
		System.out.println(Common.toHex(bytes));
		System.out.println(b2i(bytes, 0, 4));
		
		// bigEndian
		System.out.println();
		System.out.println("bigEndian");
		setBitEndianInt(bytes, 0, i);
		System.out.println(Common.toHex(bytes));
		System.out.println(getBigEndianInt(bytes, 0));
		
		// littleEndian
		System.out.println();
		System.out.println("littleEndian");
		setLittleEndianInt(bytes, 0, i);
		System.out.println(Common.toHex(bytes));
		System.out.println(getLittleEndianInt(bytes, 0));
	}
	
   public static int getBigEndianInt(byte[] array, int index) {
        return  (array[index]     & 0xff) << 24 |
                (array[index + 1] & 0xff) << 16 |
                (array[index + 2] & 0xff) <<  8 |
                array[index + 3] & 0xff;
    }
   
   public static void setBitEndianInt(byte[] array, int index, int   value) {
       array[index]     = (byte) (value >>> 24);
       array[index + 1] = (byte) (value >>> 16);
       array[index + 2] = (byte) (value >>> 8);
       array[index + 3] = (byte) value;
   }
	
	 public static void setLittleEndianInt(byte[] array, int index, int   value) {
	        array[index]     = (byte) value;
	        array[index + 1] = (byte) (value >>> 8);
	        array[index + 2] = (byte) (value >>> 16);
	        array[index + 3] = (byte) (value >>> 24);
	    }
	
	 public static int getLittleEndianInt(byte[] array, int index) {
	        return array[index] & 0xff |
	               (array[index + 1] & 0xff) <<  8 |
	               (array[index + 2] & 0xff) << 16 |
	               (array[index + 3] & 0xff) << 24;
	    }
	
	public static void i2b(int iData, byte[] b, int iFrom, int iLen)
	{
		int id = iData;
		int iOff = iLen - 1;
		if (iOff < 0)
		{
			iOff = 0;
		}
		for (int i = iFrom; i < iFrom + iLen && i < b.length; i++)
		{
			if (i - iFrom < 4)
			{
				// b[i]=(byte)((id >> ((i-iFrom)*8) ) & 0xFF);
				b[i] = (byte) ((id >> ((iOff - (i - iFrom)) * 8)) & 0xFF);
			} else
			{
				b[i] = 0;
			}
		}
	}
	
	public static int b2i(byte[] b, int iFrom, int iLen)
	{
		int iRet = 0;
		int iTep;
		for (int i = iFrom; i < iFrom + iLen && i < b.length; i++)
		{
			iTep = b[i];
			if (iTep < 0)
			{
				iTep += 256;
			}
			// iTep = iTep << (iLen-1-(i-iFrom)*8);
			// iRet = iRet | iTep;

			iRet = (iRet << 8) | iTep;
		}
		return iRet;
	}

}
