package test.com.e9.launcher.cmpp;

import java.util.Date;

import com.e9.framework.channel.i.Client;
import com.e9.framework.channel.i.Session;
import com.e9.framework.channel.util.ChannelConfig;
import com.e9.framework.handler.codec.cmpp.message.CmppConnect;
import com.e9.framework.handler.codec.util.SequenceGenerator;
import com.e9.framework.handler.codec.util.TimestampUtils;
import com.e9.launcher.cmpp.CmppClient;

/**
 * @project E9Framework
 * @date 2013-4-20
 * @version 1.0
 * @author Jason
 * 
 * @review_history
 * [2013-4-20] create by Jason
 */
public class CmppClientTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Client client = createCmppClient();
		Session session = client.connect();
		if (session != null && session.isConnected()) {
			CmppConnect message = new CmppConnect();
			message.setAuthenticatorSource("password");
			message.setSequenceId(SequenceGenerator.next());
			message.setSource_Addr("33e9");
			message.setTimestamp(TimestampUtils.getIntTimestamp(new Date()));
			message.setVersion(Integer.parseInt("20", 16));
			session.send(message);
		}else{
			System.out.println("Connection is failed...");
		}
		System.out.println("Done!");
	}
	
	private static Client createCmppClient(){
		String host = "127.0.0.1";
		int port = 7890;
		
		ChannelConfig channelConfig = new ChannelConfig(host, port);
		return new CmppClient(channelConfig);
	}

}
