package com.e9.framework.util.algorithm;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 基于二叉树的思路实现的“十叉树”，用于存储一些临时性的数据，并提供一些查询接口
 * @project E9Framework
 * @date 2013-1-11
 * @version 1.0
 * @author Jason
 * 
 * @review_history
 * [2013-1-14] create by Jason
 */
public class BinaryTree {
	
	// 树的根结点
	private final TreeNode root;
	
	/**
	 * 
	 */
	public BinaryTree() {
		// TODO Auto-generated constructor stub
		root = new TreeNode(0);
	}
	
	public class TreeNode {
		
		private int key; 
		private Object value;
		private TreeNode[] nodes = new TreeNode[10];
		
		
		public TreeNode(int key) {
			// TODO Auto-generated constructor stub
			this(key, null);
		}
		
		public TreeNode(int key, Object value) {
			this.key = key;
			this.value = value;
		}	
		
		
		public TreeNode setChildTreeNode(int key, Object value){
			
			synchronized(this){
				TreeNode treeNode = nodes[key];
				if (treeNode == null) {
					nodes[key] = new TreeNode(key);
				}
				getChildTreeNode(key).setValue(value);
			}
			
			return getChildTreeNode(key);
			
		}
		
		public TreeNode setChildTreeNode(int key){
			synchronized(this){
				TreeNode treeNode = nodes[key];
				if (treeNode == null) {
					nodes[key] = new TreeNode(key);
				}
			}
			
			return getChildTreeNode(key);
		}
		
		public TreeNode getChildTreeNode(int key){
			return nodes[key];
		}
		
		public int getKey() {
			return key;
		}
		
		public Object getValue(){
			return value;
		}
		
		public void removeValue(){
			this.value = null;
		}
		
		public void setValue(Object value){
			this.value = value;
		}
		
		public String toString()
		{
			
			return "(" + key + " , " + value + " )";
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
			
			TreeNode t_treeNode = treeNode.getChildTreeNode(key);
			if (t_treeNode == null) {
				
				// 该节点尚未创建：
				return null;
			}else if (t_treeNode.getValue() != null) {
				
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
				TreeNode t_treeNode = treeNode.getChildTreeNode(key);
				
				if (t_treeNode == null) {
					
					// 创建一个新的子节点：
					t_treeNode = treeNode.setChildTreeNode(key);
				}else{
					
					if (t_treeNode.getValue() != null && s_key_count != s_key_length) {
						
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
	 * 删除指定key的节点
	 * @param s_key
	 * @throws Exception
	 */
	public void delete(String s_key) throws Exception
	{
		TreeNode node = search(s_key);
		if (node != null) {
			node.removeValue();
			node = null;
		}
		
	}
	
	
	public String toString()
	{
		return "";
	}
	
	
	public TreeNode getRoot() {
		return root;
	}
	
	
	
	// test:
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
	
	
	
	
    public static void main(String[] args) 
    {
    	try {
	    	BinaryTree bt = new BinaryTree();
	    	
	    	String key = "223";
	    	String msg = "hello jason";
	    	bt.insert(key, msg);
	    	
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
	    	
	    	
	    	System.out.println("Done!");
	    	
    	} catch (Exception e) {
    		System.out.println(e.getMessage());
    		e.printStackTrace();
    	}
    }
	
	
}