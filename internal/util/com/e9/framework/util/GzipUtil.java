/** Date:2013-1-30下午2:31:13 Copyright (c) 2013, 三三得玖 All Rights Reserved.
 */
package com.e9.framework.util;

/**
 * 简述:java对byte数组解压缩(zip,gzip) <br/>
 * 详细描述: (无)<br/>
 * date: 2013-1-30 下午2:31:13 <br/>
 *
 * @author lzx
 * @version 
 * @since JDK 1.6
 */
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class GzipUtil {
    /***
     * 压缩GZip
     * 
     * @param data
     * @return
     */
    public static byte[] gZip(byte[] data)
    {
        byte[] b = null;
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            GZIPOutputStream gzip = new GZIPOutputStream(bos);
            gzip.write(data);
            gzip.finish();
            gzip.close();
            b = bos.toByteArray();
            bos.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return b;
    }
    /***
     * 解压GZip
     * 
     * @param data
     * @return
     */
    public static byte[] unGZip(byte[] data)
    {
        byte[] b = null;
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(data);
            GZIPInputStream gzip = new GZIPInputStream(bis);
            byte[] buf = new byte[1024];
            int num = -1;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            while ((num = gzip.read(buf, 0, buf.length)) != -1) {
                baos.write(buf, 0, num);
            }
            b = baos.toByteArray();
            baos.flush();
            baos.close();
            gzip.close();
            bis.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return b;
    }

    /***
     * 压缩Zip
     * 
     * @param data
     * @return
     */
    public static byte[] zip(byte[] data)
    {
        byte[] b = null;
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ZipOutputStream zip = new ZipOutputStream(bos);
            ZipEntry entry = new ZipEntry("zip");
            entry.setSize(data.length);
            zip.putNextEntry(entry);
            zip.write(data);
            zip.closeEntry();
            zip.close();
            b = bos.toByteArray();
            bos.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return b;
    }

    /***
     * 解压Zip
     * 
     * @param data
     * @return
     */
    public static byte[] unZip(byte[] data)
    {
        byte[] b = null;
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(data);
            ZipInputStream zip = new ZipInputStream(bis);
            while (zip.getNextEntry() != null) {
                byte[] buf = new byte[1024];
                int num = -1;
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                while ((num = zip.read(buf, 0, buf.length)) != -1) {
                    baos.write(buf, 0, num);
                }
                b = baos.toByteArray();
                baos.flush();
                baos.close();
            }
            zip.close();
            bis.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return b;
    }

    /**
     * 把字节数组转换成16进制字符串
     * 
     * @param bArray
     * @return
     */
    public static String bytesToHexString(byte[] bArray)
    {
        StringBuffer sb = new StringBuffer(bArray.length);
        String sTemp;
        for (int i = 0; i < bArray.length; i++) {
            sTemp = Integer.toHexString(0xFF & bArray[i]);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }

    public static void main(String[] args)
    {
        String s = "this is a test";

        byte[] b1 = zip(s.getBytes());
        System.out.println("zip:" + bytesToHexString(b1));
        byte[] b2 = unZip(b1);
        System.out.println("unZip:" + new String(b2));

        byte[] b5 = gZip(s.getBytes());
        System.out.println("bZip2:" + bytesToHexString(b5));
        byte[] b6 = unGZip(b5);
        System.out.println("unBZip2:" + new String(b6));

    }
}
