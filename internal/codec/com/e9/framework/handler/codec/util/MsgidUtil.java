package com.e9.framework.handler.codec.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @date 2013-8-22
 * @author Jason
 */
public class MsgidUtil {

	private static Logger logger = LoggerFactory.getLogger(MsgidUtil.class);
	private static String smgpGwCode = "755061";
	private static int cmppGwCode = 755061;
	
	private static AtomicInteger smgpSequence = new AtomicInteger();
	private static AtomicInteger cmppSequence = new AtomicInteger();
	
	private final Object smgpSequenceLock = new Object();
	private final Object cmppSequenceLock = new Object();
	
	private final static String SMGP_SEQ_DATA = System.getProperty("user.dir") + "/SMGP_SEQ.CACHE";
	private final static String CMPP_SEQ_DATA = System.getProperty("user.dir") + "/CMPP_SEQ.CACHE";
	
	private static MsgidUtil util = new MsgidUtil();
	private final AtomicBoolean inited = new AtomicBoolean(false);
	
	private ThreadLocal<DecimalFormat> seqFormatThread = new ThreadLocal<DecimalFormat>() {
		protected DecimalFormat initialValue() {
			return new DecimalFormat("000000");
		};
	};
	private ThreadLocal<Calendar> nowThread = new ThreadLocal<Calendar>(){
		protected Calendar initialValue() {
			return Calendar.getInstance(Locale.CHINA);
		};
	};
	
	/**
	 * @throws Exception 
	 * 
	 */
	private MsgidUtil(){
		// TODO Auto-generated constructor stub
		logger.debug("MsgidUtil ...");
		if (inited.compareAndSet(false, true)) {
			try {
				init();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error("", e);
				System.exit(0);
			}
		}
	}
	
	public static MsgidUtil getInstance(){return util;};
	
	private byte[] setInt(int index, int   value) {
		byte[] array = new byte[4];
        array[0] = (byte) (value >>> 24);
        array[1] = (byte) (value >>> 16);
        array[2] = (byte) (value >>> 8);
        array[3] = (byte) value;
        return array;
    }
	
	private int getInt(byte[] array) {
        return  (array[0] & 0xff) << 24 |
                (array[1] & 0xff) << 16 |
                (array[2] & 0xff) <<  8 |
                 array[3] & 0xff;
    }
	
	private void init() throws Exception{
		logger.debug("MsgidUtil.init ...");
		Runtime.getRuntime().addShutdownHook(
				new Thread(
						new Runnable() {
			
							@Override
							public void run() {
								// TODO Auto-generated method stub
								// write cmpp seq:
								FileOutputStream out = null;
								try {
									out = new FileOutputStream(new File(CMPP_SEQ_DATA));
									synchronized(cmppSequenceLock){
										out.write(cmppSequence.get());
										out.flush();
									}
								} catch (Exception e) {
									// TODO: handle exception
									logger.error("", e);
								}
								finally{
									if (out != null) {
										try {
											out.close();
										} catch (IOException e) { }
									}
								}
								
								// write smgp seq:
								try {
									out = new FileOutputStream(new File(SMGP_SEQ_DATA));
									synchronized(smgpSequenceLock){
										out.write(smgpSequence.get());
										out.flush();
									}
								} catch (Exception e) {
									// TODO: handle exception
									logger.error("", e);
								}
								finally{
									if (out != null) {
										try {
											out.close();
										} catch (IOException e) { }
									}
								}
								
								inited.set(false);
							}
						}, 
				"MsgidUtil.Destory.Hook"
			)
		);
		
		
		// read cmpp seq:
		File file = new File(CMPP_SEQ_DATA);
		if (file.exists() && file.length() > 0) {
			FileInputStream cmppSeqReader = null;
			try {
				cmppSeqReader = new FileInputStream(file);
				byte[] array = new byte[4];
				cmppSeqReader.read(array);
				cmppSequence.set(getInt(array));
			} catch (Exception e) {
				// TODO: handle exception
				throw e;
			}
			finally{
				if (cmppSeqReader != null) {
					cmppSeqReader.close();
				}
			}
		}
		
		// read smgp seq:
		file = new File(SMGP_SEQ_DATA);
		if (file.exists() && file.length() > 0) {
			FileInputStream smgpSeqReader = null;
			try {
				smgpSeqReader = new FileInputStream(file);
				byte[] array = new byte[4];
				smgpSeqReader.read(array);
				smgpSequence.set(getInt(array));
			} catch (Exception e) {
				// TODO: handle exception
				throw e;
			}
			finally{
				if (smgpSeqReader != null) {
					smgpSeqReader.close();
				}
			}
		}
	}
	

	/**
	 * msgid = smgw_code + time + sequence (len:3 + 4 + 3 = 10bit)
	 * 
	 * <pre>
		7.2.26 MsgID
		短消息流水号，用来唯一标识一条短消息。
		该字段在短消息的转发处理流程中保持唯一。
		MsgId 字段包含以下三部分内容：
		一、SMGW 代码：3 字节（BCD 码），编码规则如下：3 位区号（不足前添 0）+2 位设备类别+1 位序号
			区号：所在省长途区号
			设备类别：SMGW 取 06
			序号：所在省的设备编码，例如第一个网关编号为 1
		二、时间：4 字节（BCD 码），格式为 MMDDHHMM（月日时分）
		三、序列号：3 字节（BCD 码），取值范围为 000000～999999，从 0 开始，顺序累加，步长为 1，循环使用。
		
		例如某 SMGW 的代码为 010061，在 2003 年 1 月 16 日下午 5 时 0 分收到一条短消息，
		这条短消息的 MsgID 为：0x01006101161700012345，其中 010061 表示 SMGW 代码，  01161700
		表示接收时间，012345 表示消息序列号。
	 * </pre>
	 * 
	 * @return msgid
	 */
	@Deprecated
	public String createSmgpMsgid(int smsCount) {
		String time = TimestampUtils.getStringTimestampForSmgpMsgid(new Date());
		int seqNum;
		synchronized(smgpSequenceLock){
			smsCount = smsCount > 100 ? 100 : smsCount;
			if ((smgpSequence.get() + smsCount) > 999999) {
				smgpSequence.set(0);
			}
			seqNum = smgpSequence.getAndAdd(smsCount);
		}
		return smgpGwCode + time + seqFormatThread.get().format(seqNum);
	}
	
	public String getSmgpMsgidSmgw(){
		return smgpGwCode;
	}
	
	public String getSmgpMsgidTime(){
		return TimestampUtils.getStringTimestampForSmgpMsgid(new Date());
	}
	
	@Deprecated
	public Integer SmgpMsgidSequenceBytes2Int(byte[] sequenceBytes){
		if (sequenceBytes == null || sequenceBytes.length != 3) {
			throw new IllegalArgumentException("SmgpMsgidSequenceBytes2Int: sequenceBytes length not be three!");
		}
		
		return (sequenceBytes[0] & 0xff) << 16 |
				(sequenceBytes[1] & 0xff) << 8 |
				(sequenceBytes[2] & 0xff);
	}
	
	public Integer getSmgpMsgidSequence(Integer destTermIdCount){
		destTermIdCount = destTermIdCount > 100 ? 100 : destTermIdCount;
		synchronized(smgpSequenceLock){
			if ((smgpSequence.get() + destTermIdCount) > 999999) {
				smgpSequence.set(0);
			}
			return smgpSequence.getAndAdd(destTermIdCount);
		}
	}

	/**
	 * <pre>
	 * 		信息标识，生成算法如下：
	 * 		采用64位（8字节）的整数：
	 * 		时间（格式为MMDDHHMMSS，即月日时分秒）：bit64~bit39，其中 (26bit)
	 * 		bit64~bit61：月份的二进制表示； (4bit)
	 * 		bit60~bit56：日的二进制表示；      (5bit)
	 * 		bit55~bit51：小时的二进制表示； (5bit)
	 * 		bit50~bit45：分的二进制表示；      (6bit)
	 * 		bit44~bit39：秒的二进制表示；      (6bit)
	 * 		短信网关代码：bit38~bit17，把短信网关的代码转换为整数填写到该字段中。(22bit)
	 * 		序列号：bit16~bit1，顺序增加，步长为1，循环使用。(16bit)
	 * 		各部分如不能填满，左补零，右对齐。
	 * 		
	 * 		（SP根据请求和应答消息的Sequence_Id一致性就可得到CMPP_Submit消息的Msg_Id）
	 * </pre>
	 * 
	 * @return
	 */
	public long createCmppMsgid(int smsCount) {
		Calendar c = nowThread.get();
		
		long lMsgid = (c.get(Calendar.MONTH) + 1) & 0xf;	// 4
		
		lMsgid <<= 5;
		lMsgid |= c.get(Calendar.DAY_OF_MONTH) & 0x1f;		// 5
		
		lMsgid <<= 5;
		lMsgid |= c.get(Calendar.HOUR_OF_DAY) & 0x1f;		// 5

		lMsgid <<= 6;
		lMsgid |= c.get(Calendar.MINUTE) & 0x3f;			// 6

		lMsgid <<= 6;
		lMsgid |= c.get(Calendar.SECOND) & 0x3f;			// 6

		lMsgid <<= 22;
		lMsgid |= cmppGwCode & 0x3fffff;					// 22		48
		
		synchronized(cmppSequenceLock){
			cmppSequence.compareAndSet(Short.MAX_VALUE - 200, 0);
			lMsgid <<= 16;
			lMsgid |= cmppSequence.getAndAdd(smsCount) & 0xffff;
		}
		
		return lMsgid;
	}
	
	public String getCmppMsgidPrefix(){
		Calendar c = nowThread.get();
		
		long lMsgid = (c.get(Calendar.MONTH) + 1) & 0xf;
		
		lMsgid <<= 5;
		lMsgid |= c.get(Calendar.DAY_OF_MONTH) & 0x1f;
		
		lMsgid <<= 5;
		lMsgid |= c.get(Calendar.HOUR_OF_DAY) & 0x1f;

		lMsgid <<= 6;
		lMsgid |= c.get(Calendar.MINUTE) & 0x3f;

		lMsgid <<= 6;
		lMsgid |= c.get(Calendar.SECOND) & 0x3f;

		lMsgid <<= 22;
		lMsgid |= cmppGwCode & 0x3fffff;
		
		return Long.toHexString(lMsgid);
	}
	
	public Integer getCmppMsgidSequence(int smsCount){
		smsCount = smsCount > 99 ? 99 : smsCount;
		synchronized(cmppSequenceLock){
			if (cmppSequence.get() + smsCount > Short.MAX_VALUE) {
				cmppSequence.set(0);
			}
			return cmppSequence.getAndAdd(smsCount);
		}
	}
	
	@Deprecated
	public long cmppHexMsgid2Long(String hexMsgid)throws Exception {
		int length = hexMsgid.length();
        byte[] bytes = new byte[length/2];
        int index = 0;
        for (int i=0; i<length;) {
        	bytes[index++] = (byte)Integer.parseInt(hexMsgid.substring(i, i += 2), 16);
        }
        
		int len = bytes.length < 8 ? bytes.length : 8;
		long num = 0;
		for (int i = 0; i < len; ++i) {
			num <<= 8;
			num |= 0xFFL & bytes[i];
		}
		return num;
	}
	
	/**
	 * 
	 * @param msgid
	 * @return 0828103450 755061 1
	 */
	@Deprecated
	public String parserCmppMsgid(long l){
		long seq = l & 0xffff;
		l >>>= 16;
		long gw = l & 0x3fffff;
		l >>>= 22;
		long second = l & 0x3f;
		l >>>= 6;
		long minute = l & 0x3f;
		l >>>= 6;
		long hour = l & 0x1f;
		l >>>= 5;
		long day = l & 0x1f;
		l >>>= 5;
		long month = l & 0xf;
		
		StringBuilder sf = new StringBuilder();
		sf.append(month < 10 ? "0"+month : month)
		.append(day < 10 ? "0"+day : day)
		.append(hour < 10 ? "0"+hour : hour)
		.append(minute < 10 ? "0"+minute : minute)
		.append(second < 10 ? "0"+second : second)
		.append(gw).append(seq);
		return sf.toString();
	}
	
}
