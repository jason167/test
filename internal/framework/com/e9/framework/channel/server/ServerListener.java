package com.e9.framework.channel.server;

import com.e9.framework.channel.DefaultListener;
import com.e9.framework.channel.i.IMessageCoreController;
import com.e9.framework.channel.util.ChannelConfig;


/**
 * 服务器端消息监听器
 * @project E9Framework
 * @date 2012-12-20
 * @version 1.0
 * @author Jason
 * 
 * @review_history
 * [2012-12-20] create by Jason
 */
public class ServerListener extends DefaultListener{

	/**
	 * @param messageProcessor
	 */
	public ServerListener(IMessageCoreController messageController, ChannelConfig channelConfig) {
		super(messageController, channelConfig);
		// TODO Auto-generated constructor stub
	}

}
