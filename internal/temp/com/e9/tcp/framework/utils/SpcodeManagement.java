package com.e9.tcp.framework.utils;

import com.e9.framework.channel.i.Session;
import com.e9.framework.util.algorithm.BinaryTree;
import com.e9.framework.util.algorithm.BinaryTree.TreeNode;

/**
 * @project E9Framework
 * @date 2013-1-18
 * @version 1.0
 * @author Jason
 * 
 * @review_history
 * [2013-1-18] create by Jason
 */
public class SpcodeManagement {

	
	private final static BinaryTree binaryTree;
	static{
		binaryTree = new BinaryTree();
	}

	public static void insert(String spcode, Session session) throws Exception{
		binaryTree.insert(spcode, session);
	}
	
	public static Session search(String spcode) throws Exception{
		TreeNode node = binaryTree.search(spcode);
		if (node != null) {
			return (Session) node.getValue();
		}
		return null;
	}
	
	public static void delete(String spcode){
		try {
			binaryTree.delete(spcode);
		} catch (Exception e) {}
	}
	
	
	
}
