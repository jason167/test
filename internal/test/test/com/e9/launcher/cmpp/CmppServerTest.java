package test.com.e9.launcher.cmpp;

import com.e9.framework.channel.i.Server;
import com.e9.framework.channel.util.ChannelConfig;
import com.e9.launcher.cmpp.CmppServer;

/**
 * @project E9Framework
 * @date 2013-4-20
 * @version 1.0
 * @author Jason
 * 
 * @review_history
 * [2013-4-20] create by Jason
 */
public class CmppServerTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int port = 7890;
		Server serv = createCmppServer(port);
		if (serv != null) {
			serv.bind();
		}else{
			System.out.println("Server don't create!");
		}
		System.out.println("Done! listener port:"+port);
	}
	
	public static Server createCmppServer(int port){
		
		ChannelConfig channelConfig = new ChannelConfig(port);
		return new CmppServer(channelConfig);
	}

}
