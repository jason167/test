package test.com.e9.launcher.string;

import com.e9.framework.channel.i.Server;
import com.e9.framework.channel.util.ChannelConfig;
import com.e9.launcher.string.StringServer;

/**
 * @project E9Framework
 * @date 2013-4-20
 * @version 1.0
 * @author Jason
 * 
 * @review_history
 * [2013-4-20] create by Jason
 */
public class StringServerTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int port = 8899;
		Server server = createStringServer(port);
		if (server != null) {
			server.bind();
			System.out.println("Listener port:"+port);
		}
		System.out.println("Done!");
	}

	private static Server createStringServer(int port){
		ChannelConfig channelConfig = new ChannelConfig(port);
		return new StringServer(channelConfig);
	}
}
