package com.e9.framework.util.algorithm;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 基于二叉树的思路实现的“十叉树”，用于存储一些临时性的数据，并提供一些查询接口
 * @project E9Framework
 * @date 2013-1-11
 * @version 1.0
 * @author Jason
 * 
 * @review_history
 * [2013-1-14] create by Jason
 * [2013-6-4] modify by Jason
 * BinaryTree支持一个节点拥有多个value，承载value的容器采用CopyOnWriteArrayList类
 */
public class BinaryTree_v2 {
	
	private final Logger logger =
            LoggerFactory.getLogger(BinaryTree_v2.class);
	// 树的根结点
	private final TreeNode root;
	
	/**
	 * 
	 */
	public BinaryTree_v2() {
		// TODO Auto-generated constructor stub
		root = new TreeNode(0);
	}
	
	public class TreeNode {
		
		private final int key; 
		private List<Object> objectList = null;
		private TreeNode[] nodes = new TreeNode[10];
		
		
		public TreeNode(int key) {
			// TODO Auto-generated constructor stub
			this.key = key;
		}
		
		public TreeNode setChildTreeNode(int key){
			synchronized(this){
				TreeNode treeNode = nodes[key];
				if (treeNode == null) {
					nodes[key] = new TreeNode(key);
				}
			}
			
			return nodes[key];
		}
		
		public TreeNode next(int key){
			return nodes[key];
		}
		
		public void clearValues(){
			if (objectList != null) {
				objectList.clear();
				objectList = null;
			}
		}
		
		public int getKey() {
			return key;
		}
		
		public boolean valueIsEmpty(){
			return this.objectList == null || this.objectList.size() == 0 ? true : false;
		}
		
		public Object getValue(){
			while (!valueIsEmpty()) {
				Object value = objectList.get(0);
				if (value != null) {
					return value;
				}else{
					objectList.remove(0);
				}
			}
			return null;
		}
		
		/**
		 * 仅移除指定的value
		 * @param value
		 * @return boolean
		 */
		public boolean removeValue(Object value){
			return removeValue(value, false);
		}
		
		/**
		 * 移除指定的value，当allValue为true时，value失效，同时会移除该节点下的所有value。
		 * @param value
		 * @param allValue
		 * @return boolean
		 */
		public synchronized boolean removeValue(Object value, boolean allValue){
			if (valueIsEmpty()) {
				return false;
			}
			
			if (allValue) {
				this.clearValues();
				return true;
			}
			
			for (Object srcObject : this.objectList) {
				if (srcObject == null) {
					this.objectList.remove(srcObject);
				}else {
					if (srcObject instanceof String) {
						if (srcObject.equals(value)) {
							logger.info("TreeNode removeValue is ok srcObject:{}, value:{}",srcObject, value);
							this.objectList.remove(value);
							return true;
						}
					}else{
						if (srcObject == value) {
							logger.info("TreeNode removeValue is ok srcObject:{}, value:{}",srcObject, value);
							this.objectList.remove(value);
							return true;
						}
					}
				}
			}
			return false;
		}
		
		public void setValue(Object value){
			synchronized(this){
				if (this.objectList == null) {
					this.objectList = new CopyOnWriteArrayList<Object>();
				}
			}
			this.objectList.add(value);
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "TreeNode [key=" + key + ", objectList=" + objectList + "]";
		}
		
		
		
	}
	
	/**
	 * 若为空，返回 true ，否则返回 false . 
	 */
	public boolean isEmpty()
	{
		if (root == null) {
			return true;
		} else {
			return false;
		}			
	}
	
	
	public void TreeEmpty() throws Exception 
	{
		if (isEmpty()) {
			throw new Exception("树为空!");
		}
	}
	
	
	
	/**
	 * 查找指定s_key对应的节点
	 * @param s_key
	 * @return TreeNode
	 * @throws Exception
	 */
	public TreeNode search(String s_key) throws Exception 
	{
		if (s_key == null || s_key.trim().equals("")) {
			return null;
		}
		
		if (getRoot() == null) {
			throw new Exception("root node is null");
		}
		
		TreeNode treeNode = getRoot();
		
		char[] key_array = s_key.toCharArray();
		
		// 根据给定的s_key，遍历tree node的绝对路径，且以最短匹配的规则检索；
		// 命中后返回对应的tree node，否则返回Null。
		for (char skey : key_array) {
			
			int key = skey & 0xf;
//			System.out.print(key+"->");
			
			TreeNode t_treeNode = treeNode.next(key);
			if (t_treeNode == null) {
				
				// 该节点尚未创建：
				return null;
			}else if (!t_treeNode.valueIsEmpty()) {
				
				// 命中：
				return t_treeNode;
			}else{
				
				// 否则，将遍历下一个节点：
				treeNode = t_treeNode;
			}
			
		}
		
		// 循环体之外，将视未命中：
		return null;
		
	}
	
	
	/**
	 * 将指定的s_key放入节点，并设置对应的value
	 * @param s_key
	 * @param value
	 * @throws Exception 
	 */
	public void insert(String s_key, Object value) throws Exception{
		
		if (s_key == null || s_key.trim().equals("") || value == null) {
			return;
		}
		TreeNode treeNode = getRoot();
		
		char[] key_array = s_key.toCharArray();
		int s_key_length = key_array.length;
		int s_key_count = 1;
		/*
		 * 根据给定的key_array的长度，查找节点、布置节点并得到最后一个节点的tree node，然后赋值value。
		 */
		synchronized(this){
			for (char skey : key_array) {
				
				int key = skey & 0xf;
				
				// 取某节点下，N个子节点中的一个分支：
				TreeNode t_treeNode = treeNode.next(key);
				
				if (t_treeNode == null) {
					
					// 创建一个新的子节点：
					t_treeNode = treeNode.setChildTreeNode(key);
				}else{
					
					if (!t_treeNode.valueIsEmpty() && s_key_count != s_key_length) {
						logger.error("BinaryTree save value is error!! key:[{}], value:{}", s_key, value);
						treeNode = null;
						break;
					}
				}
				
				// 该节点已经存在，节点交换：
				treeNode = t_treeNode;
				
				s_key_count++;
			}
		}
		
		if (treeNode != null) {
			
			// 为最后一个节点赋值：
			treeNode.setValue(value);
		}
		
		
	}
	
	
	/**
	 * 删除指定key的节点，且value相等
	 * @param s_key
	 * @throws Exception
	 */
	public void delete(String s_key, Object value) throws Exception
	{
		delete(s_key, value, false);
	}
	
	/**
	 * 删除指定key的节点，且value相等；
	 * 当allValue为true时，value失效。
	 * @param s_key
	 * @param value
	 * @param allValue		是否删除该节点下的所有value
	 * @throws Exception
	 */
	public void delete(String s_key, Object value, boolean allValue)throws Exception{
		TreeNode node = search(s_key);
		if (node != null) {
			node.removeValue(value, allValue);
			if (node.valueIsEmpty()) {
				node = null;
			}
		}
	}
	
	
	public TreeNode getRoot() {
		return root;
	}
	
	
	
	// test:
	@SuppressWarnings("unused")
	private static ConcurrentHashMap<String,Object> keysGenerator(){
		int count = 10 * 10000;
		Random r = new Random();
		ConcurrentHashMap<String,Object> map = new ConcurrentHashMap<String, Object>();
		while(count != 0){
			long key = r.nextLong();
//			System.out.println(key);
			if (key < 10000) {
				key += 10000;
			}
			Object absent = map.putIfAbsent(String.valueOf(key & 0xffffff), key);
			if (absent == null) {
				
				count--;
			}
		}
		
		System.out.println(map.size());
		
		return map;
		
	}
	
	public static void binaryTreeTest(){
		try {
			BinaryTree_v2 bt = new BinaryTree_v2();
	    	
	    	String key = "223";
	    	String msg = "hello";
	    	bt.insert(key, msg);
	    	bt.insert(key, "hi");
	    	
	    	System.out.println("search 223");
	    	TreeNode node = bt.search(key);
	    	if (node != null) {
	    		System.out.println(node.getValue());
	    	}
	    	
	    	System.out.println("search 223001");
	    	node = bt.search("223001");
	    	if (node != null) {
	    		System.out.println(node.getValue());
	    	}
	    	
	    	System.out.println("删除key:"+key);
	    	bt.delete(key, msg);
	    	System.out.println("search 223");
	    	node = bt.search(key);
	    	if (node != null) {
	    		System.out.println(node.getValue());
	    	}else{
	    		System.out.println("没找到");
	    	}
	    	
	    	System.out.println("Done!");
	    	
    	} catch (Exception e) {
    		System.out.println(e.getMessage());
    		e.printStackTrace();
    	}
	}
	
    public static void main(String[] args) 
    {
    	binaryTreeTest();
    }
	
	
}