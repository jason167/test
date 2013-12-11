package test.com.e9.framework.cache;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.e9.framework.cache.IQueueComparator;
import com.e9.framework.cache.util.MessageQueueComparator;
import com.e9.framework.cache.util.MessageQueueOrder;
import com.e9.framework.cache.util.MessageQueuePriority;

/**
 * @date 2013-8-1
 * @author Jason
 */
public class ComparatorTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<User> userList = new ArrayList<User>();
		userList.add(new User(MessageQueuePriority.ZERO, "101"));
		userList.add(new User(MessageQueuePriority.NINE, "104"));
		userList.add(new User(MessageQueuePriority.NINE, "103"));
		userList.add(new User(MessageQueuePriority.FIVE, "102"));
		Comparator<IQueueComparator> comparator = new MessageQueueComparator(MessageQueueOrder.DESC);
		Collections.sort(userList, comparator);
		
		for (User user : userList) {
			System.out.println(user);
		}
		
	}
	

}


class User implements IQueueComparator{
	
	MessageQueuePriority priority;
	String username;
	
	/**
	 * 
	 */
	public User(MessageQueuePriority priority, String username) {
		// TODO Auto-generated constructor stub
		this.priority = priority;
		this.username = username;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "User [priority=" + priority + ", username=" + username + "]";
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/* (non-Javadoc)
	 * @see com.e9.framework.cache.IQueueComparator#getPriority()
	 */
	@Override
	public MessageQueuePriority getPriority() {
		// TODO Auto-generated method stub
		return priority;
	}

	/* (non-Javadoc)
	 * @see com.e9.framework.cache.IQueueComparator#setPriority(com.e9.framework.cache.util.MessageQueuePriority)
	 */
	@Override
	public void setPriority(MessageQueuePriority priority) {
		// TODO Auto-generated method stub
		this.priority = priority;
	}

}
