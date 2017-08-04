import java.util.ArrayDeque;
import java.util.Stack;  
/*
 * 学习完链表，决定再重温一下树（二叉树）结构的相关实现。
 * 07/07/2016
 * 树结合了两种数据结构的有点：一种是有序数组，树在查找数据项的速度和在有序数组中查找一样快；
 * 另一种是链表，树在插入数据和删除数据项的速度和链表一样。既然这样，我就要好好去学了....
 */
class BinaryTreeNode {// 节点类
	int data;
	BinaryTreeNode leftNode = null, rightNode = null;

	public void setBinaryTreeNode(int data) {
		this.data = data;
	}

	public void setLeftNode(BinaryTreeNode leftNode) {
		this.leftNode = leftNode;
	}

	public void setRightNode(BinaryTreeNode rightNode) {
		this.rightNode = rightNode;
	}
}

class BinaryTree {
	BinaryTreeNode[] btn;
	BinaryTreeNode rooNode;// 根节点
	int NodeSize;

	// 1、构建树
	public BinaryTree(int[] arrayNode) {
		NodeSize = arrayNode.length;
		btn = new BinaryTreeNode[NodeSize];
		// 把arrayNode元素转化为节点
		for (int i = 0; i < NodeSize; i++) {
			btn[i] = new BinaryTreeNode();
			btn[i].setBinaryTreeNode(arrayNode[i]);
			if (i == 0)
				rooNode = btn[i];
		}
		// 把二叉树的左右子树节点补全
		for (int i = 0; i < (NodeSize - 2) / 2; i++) {
			btn[i].setLeftNode(btn[2 * i + 1]);
			btn[i].setRightNode(btn[2 * i + 2]);
			System.out.print("left = " + btn[2 * i + 1].data + " right = "
					+btn[2 * i + 2].data);
		}
	}

	// 2、递归方法前序遍历（根-左-右）
	void preOrder(BinaryTreeNode btn) {
		BinaryTreeNode root = btn;
		if (root != null) {
			printNode(root);
			preOrder(root.leftNode);
			preOrder(root.rightNode);
		}else
			return;
	}
	/*
	 * 树的深度优先遍历，因为没有parent指针，所有非递归形式一定要借助栈；相反，如果二叉树的节点有parent指针，那么就不需要栈了。
	 * 先让根进栈。只要栈不为空，就可以弹栈。每次弹出一个节点，要把它的左右节点进栈（右节点先进栈）。
	 */
	// 2.1 非递归方法前序遍历（根-左-右）
	void preOrder1(BinaryTreeNode btn) {
		BinaryTreeNode root = btn;
		if(root == null)
			return;
		Stack <BinaryTreeNode> stack = new Stack<BinaryTreeNode>();
		stack.push(root);
		while(stack.isEmpty() != true){
			root = stack.pop();
			printNode(root);
			if(root.rightNode != null)
				stack.push(root.rightNode);
			if(root.leftNode != null)
				stack.push(root.leftNode);
		}
	}
		
	// 3、递归方法中序遍历（左-根-右）
	void inOrder(BinaryTreeNode btn) {
		BinaryTreeNode root = btn;
		if (root != null) {
			inOrder(root.leftNode);
			printNode(root);	
			inOrder(root.rightNode);
		}else
			return;
	}
	/*
	 * 对于中序遍历来说，非递归的算法比递归算法的效率要高的多。其中序遍历算法的实现的过程如下：
	 * (1)初始化栈，根结点进栈；
	 * (2)若栈非空，则栈顶结点的左孩子结点相继进栈，直到null(到叶子结点时)退栈；访问栈顶结点(执行visit操作)
	 * 并使栈顶结点的右孩子结点进栈成为栈顶结点。
	 * (3)重复执行(2)，直至栈为空。
	 */
	// 3.1 非递归方法中序遍历（左-根-右）
	void inOrder1(BinaryTreeNode btn) {
		BinaryTreeNode root = btn;
		if(root == null)
			return;
		Stack <BinaryTreeNode> stack = new Stack<BinaryTreeNode>();
		while(root != null || stack.isEmpty() != true){
			while(root != null){
			stack.push(root);
			root = root.leftNode;
			}
			if(root == null){
				BinaryTreeNode node = stack.pop();
				printNode(node);
				root = node.rightNode;
			}
		}	
	}
		
	// 4、递归方法后序遍历（左-右-根）
	void postOrder(BinaryTreeNode btn) {
		BinaryTreeNode root = btn;
		if (root != null) {
			postOrder(root.leftNode);
			postOrder(root.rightNode);
			printNode(root);
		}
	}
	
	// 4.1 非递归方法后序遍历（左-右-根）
	void postOrder1(BinaryTreeNode btn) {
		BinaryTreeNode root = btn;
		if(root == null)
			return;
		Stack <BinaryTreeNode> stack = new Stack<BinaryTreeNode>();
		while(root != null || stack.isEmpty() != true){
			while(root != null){
				if(root.rightNode != null)
					stack.push(root.rightNode);
				stack.push(root);
				root = root.leftNode;
			}
			// 既然出栈了，该节点肯定没有左孩子
			root = stack.pop();
			if(root.rightNode != null&& !stack.isEmpty()&&root.rightNode == stack.peek()){
				stack.pop();// 出栈右孩子
				stack.push(root);
				root = root.rightNode;
			}else {
				printNode(root);
				root = null;
			}		
		}
	}

	// 5、插入节点
	void insertNode(){	
	}
	
	// 6、删除节点
	void delNode(){		
	}
	
	// 7、查找节点
	void findNode(){	
	}
	
	// 8、判空树
	boolean emptyTree(BinaryTreeNode btn){
		BinaryTreeNode root = btn;
		if(root == null)
			return true;
		return false;
	}
	
	// 9、置空树
	void clearTree(BinaryTreeNode btn){
		BinaryTreeNode root = btn;
		root = null;
		printNode(btn);
	}
	
	// 10、求树的深度
	public int depthTree(BinaryTreeNode btn){
		BinaryTreeNode root = btn;
		if(root == null)
			return -1;
		int leftDepth = depthTree(root.leftNode);// 计算该节点左孩子的高度
		int rightDepth = depthTree(root.rightNode);// 计算该节点右孩子的高度
		return Math.max(leftDepth, rightDepth) + 1;
	}
	
	// 11、计算二叉树叶子总数
	public int countLeafs(BinaryTreeNode btn){
		BinaryTreeNode root = btn;
		if(root != null){
			if(root.leftNode == null && root.rightNode == null)
				return 1;
			return countLeafs(root.leftNode) + countLeafs(root.rightNode);
		}
		return 0;
	}
	
	// 12、判断是否为叶子节点
	public boolean isLeaf(BinaryTreeNode btn){
		BinaryTreeNode root = btn;
		if(root.leftNode == null && root.rightNode == null)
			return true;
		return false;
	}
	
	// 13、求某节点的高度
	/*
	 * 计算高度的时候，利用递归,从父节点到子节点，直至叶子节点，设置叶子节点的高度是0。
	 * 再从叶子回到父节点，直至跟根节点，height(parentnode) = max(height(left),height(son))+1
	 * 树的高度等于根节点高度+1
	 */
	int heightTree(BinaryTreeNode btn){
		BinaryTreeNode root = btn;
		if(root == null)
			return -1;
		int leftHeight = heightTree(root.leftNode);// 计算该节点左孩子的高度
		int rightHeight = heightTree(root.rightNode);// 计算该节点右孩子的高度
		return Math.max(leftHeight, rightHeight) + 1;
	}
	
	// 14、 非递归实现层序遍历
	void levelOrder1(BinaryTreeNode btn){
		BinaryTreeNode root = btn;
		ArrayDeque<BinaryTreeNode> queue = new ArrayDeque<BinaryTreeNode>();
		if(root == null)
			return;
		queue.add(root);
		while(queue.isEmpty() != true){
			root = queue.remove();
			printNode(root);
			if(root.leftNode != null)
				queue.add(root.leftNode);
			if(root.rightNode != null)
				queue.add(root.rightNode);
		}
	}
	// 用于判断平衡二叉树
	int heightTreeForJudge(BinaryTreeNode btn){
		BinaryTreeNode root = btn;
		if(root == null)
			return 0;
		int leftHeight = heightTree(root.leftNode);// 计算该节点左孩子的高度
		int rightHeight = heightTree(root.rightNode);// 计算该节点右孩子的高度
		if(leftHeight < 0 || rightHeight < 0 || Math.abs(leftHeight - rightHeight) > 0)
			return -1;
		return Math.max(leftHeight, rightHeight) + 1;
	}
	
	// 15、判断平衡二叉树
	boolean isBalanced(BinaryTreeNode btn){
		return heightTreeForJudge(btn)>=0;
	}
	
	//16、根据中序+后序遍历结果重构二叉树
	public static BinaryTreeNode buildTree(int postOrder[], int pend, int inOrder[], int iend, int length) {

		if (postOrder == null || postOrder.length == 0 || inOrder == null
				|| inOrder.length == 0 || postOrder.length != inOrder.length) {
			return null;
		}
		BinaryTreeNode root = new BinaryTreeNode();
		int value = postOrder[pend];
		root.data = value;
		if (length == 1)
			return root;
		int i = 0;
		//找到中序遍历根节点的索引
		while (inOrder[iend - i] != value) {
			i++;
		}
		root.rightNode = buildTree(postOrder, pend - 1, inOrder, iend, i);
		root.leftNode = buildTree(postOrder, pend - i - 1, inOrder, iend - i - 1, length - i - 1);
		return root;
	}
	
	// 17、根据前序+中序遍历结果重构二叉树
	public static BinaryTreeNode buildTree2(int[] preOrder, int start, int[] inOrder, int end, int length) {
		// 思路： 前序遍历的每一个节点都是当前子树的根节点。同时，以对应的节点为边界，就会把中序遍历的结果分为左子树和右子树。
		if (preOrder == null || preOrder.length == 0 || inOrder == null
				|| inOrder.length == 0 || length <= 0) {
			return null;
		}

		// 根据 前序遍历的第一个元素建立树根节点
		int value = preOrder[start];
		BinaryTreeNode root = new BinaryTreeNode();
		root.data = value;

		// 递归终止条件：子树只有一个节点
		if (length == 1)
			return root;

		// 根据 前序遍历的第一个元素在中序遍历中的位置分拆树的左子树和右子树
		int i = 0;
		while (i < length) {
			if (value == inOrder[end - i]) {
				break;
			}
			i++;
		}

		// 建立子树的左子树
		root.leftNode = buildTree(preOrder, start + 1, inOrder, end - i - 1, length - 1 - i);
		// 建立子树的右子树
		root.rightNode = buildTree(preOrder, start + length - i, inOrder, end, i);
		return root;
	}
	
	public static void printNode(BinaryTreeNode btn) {
		System.out.print(btn.data + " " );		
	}
	
}
public class binaryTree {

	public static void main(String[] args) {
		
		int[] arrayNode = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		BinaryTree bt = new BinaryTree(arrayNode);
		System.out.println("递归中序遍历: ");
		bt.inOrder(bt.rooNode);
		System.out.println();
		System.out.println("非递归中序遍历: ");
		bt.inOrder1(bt.rooNode);
		System.out.println();
		System.out.println("递归前序遍历: ");
		bt.preOrder(bt.rooNode);
		System.out.println();
		System.out.println("非递归前序遍历: ");
		bt.preOrder1(bt.rooNode);
		System.out.println();
		System.out.println("递归后序遍历: ");
		bt.postOrder(bt.rooNode);
		System.out.println();
		System.out.println("非递归后序遍历: ");
		bt.postOrder1(bt.rooNode);
		System.out.println();
		System.out.println("非递归层序遍历: ");
		bt.levelOrder1(bt.rooNode);
		System.out.println();
		System.out.println("空树否: " + bt.emptyTree(bt.rooNode));
		System.out.println("树的高度: " + bt.heightTree(bt.rooNode));
		System.out.println("树的深度: " + bt.depthTree(bt.rooNode));
		System.out.println("是否为平衡二叉树: " + bt.isBalanced(bt.rooNode));
		System.out.println("叶子结点个数: " + bt.countLeafs(bt.rooNode));
//		bt.clearTree(bt.rooNode);
	}
}
