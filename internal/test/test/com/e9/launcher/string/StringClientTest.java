package test.com.e9.launcher.string;

import java.util.concurrent.TimeUnit;

import com.e9.framework.channel.i.Client;
import com.e9.framework.channel.i.Session;
import com.e9.framework.channel.util.ChannelConfig;
import com.e9.framework.handler.codec.string.message.Hello;
import com.e9.framework.handler.codec.util.SequenceGenerator;
import com.e9.launcher.string.StringClient;

/**
 * @project E9Framework
 * @date 2013-4-20
 * @version 1.0
 * @author Jason
 * 
 * @review_history
 * [2013-4-20] create by Jason
 */
public class StringClientTest {

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		Client client = createStringClient();
		if (client != null) {
			Session session = client.connect();
			if (session != null && session.isConnected()) {
				Hello hello = new Hello();
				hello.setMsg("你好啊中国");
				hello.setSequenceId(SequenceGenerator.next());
				int count = 20;
				while(count > 0){
					System.out.println("send state:"+session.send(hello));
					
					TimeUnit.SECONDS.sleep(10);
					
					count--;
				}
			}
		}
		System.out.println("Done!");
	}

	private static Client createStringClient(){
		String host = "127.0.0.1";
		int port = 8899;
		
		ChannelConfig channelConfig = new ChannelConfig(host, port);
		return new StringClient(channelConfig);
	}
}
