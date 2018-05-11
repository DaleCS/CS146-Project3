public class RedBlackTree<Key extends Comparable<Key>> 
{	
		private RedBlackTree.Node<Key> root;

		public static class Node<Key extends Comparable<Key>> 
		{ //changed to static 
			
			  Key key;  		  
			  Node<Key> parent;
			  Node<Key> leftChild;
			  Node<Key> rightChild;
			  boolean isRed;
			  int color;					// 1 = red, 0 = black
			  
			  public Node(Key data)
			  {
				  this.key = data;
				  parent = null;
				  leftChild = null;
				  rightChild = null;
				  isRed = false;
				  color = 0;
			  }		
			  
			  public int compareTo(Node<Key> n)
			  { //this < that  <0
				  return key.compareTo(n.key);  	//this > that  >0
			  }
		}

		public boolean isLeaf(Node<Key> n)
		{
			  if (n.equals(root) && n.leftChild == null && n.rightChild == null) 
				  return true;
			  if (n.equals(root)) 
				  return false;
			  if (n.leftChild == null && n.rightChild == null)
			  {
				  return true;
			  }
			  return false;
		}
		
		public interface Visitor<Key extends Comparable<Key>> 
		{
			/**
			This method is called at each node.
			@param n the visited node
			*/
			void visit(Node<Key> n);  
		}
		
		public void visit(Node<Key> n)
		{
			System.out.println(n.key);
		}
		
		public void printTree()
		{  //preorder: visit, go left, go right
			Node<Key> currentNode = root;	
			printTree(currentNode);
		}
		
		public void printTree(Node<Key> node)
		{
			System.out.print(node.key);
			if (isLeaf(node))
			{
				return;
			}
			printTree(node.leftChild);
			printTree(node.rightChild);
		}
		
		// place a new node in the RB tree with data the parameter and color it red. 
		public Node<Key> addNode(Key data)
		{
			Node<Key> newNode = new Node(data);
			
			if (root == null)
			{
				root = newNode;
			}
			else
			{
				Node<Key> curNode = root;
				Node<Key> prevNode = null;
				
				while (curNode != null)
				{
					prevNode = curNode;
					if (newNode.compareTo(curNode) >= 0)
					{
						curNode = curNode.rightChild;
						if (curNode == null)
						{
							prevNode.rightChild = newNode;
						}
					}
					else
					{
						curNode = curNode.leftChild;
						if (curNode == null)
						{
							prevNode.leftChild = newNode;
						}
					}
					newNode.parent = prevNode;
				}
			}
			setColor(newNode, true);
			return newNode;
		}	

		public void insert(Key data)
		{
			Node<Key> curNode = addNode(data);
			if (curNode.compareTo(root) == 0)
			{
				setColor(curNode, false);
			}
			else if (curNode.parent.isRed == false)
			{
				
			}
			else
			{
				fixTree(curNode);
			}
		}
		
		public Node<Key> lookup(Key k)
		{
			if (root == null)
			{
				return null;				
			}
			else
			{
				Node<Key> curNode = root;
				while (curNode != null && curNode.key.compareTo(k) != 0)
				{
					if (k.compareTo(curNode.key) >= 0)
					{
						curNode = curNode.rightChild;
					}
					else
					{
						curNode = curNode.leftChild;
					}
				}
				return curNode;
			}
		}
	 	
		
		public Node<Key> getSibling(Node<Key> n)
		{
			if (n.parent == null)
			{
				return null;
			}
			else
			{
				if (isLeftChild(n.parent, n) == true)
				{
					return n.parent.rightChild;
				}
				else
				{
					return n.parent.leftChild;
				}
			}
		}
		
		
		public Node<Key> getAunt(Node<Key> n)
		{
			Node<Key> grandParent = getGrandparent(n);
			if (grandParent == null)
			{
				return null;
			}
			else
			{
				if (isLeftChild(grandParent, n.parent) == true)
				{
					return grandParent.rightChild;
				}
				else
				{
					return grandParent.leftChild;
				}
			}
		}
		
		public Node<Key> getGrandparent(Node<Key> n)
		{
			if (n.parent != null)
			{
				return n.parent.parent;
			}
			return null;
		}
		
		public void rotateLeft(Node<Key> n)
		{
			Node<Key> y = n.rightChild;
			n.rightChild = y.leftChild;
			if (y.leftChild != null)
			{
				y.leftChild.parent = n;
			}
			y.parent = n.parent;
			if (n.parent == null)
			{
				root = y;
			}
			else if (n.compareTo(n.parent.leftChild) == 0)
			{
				n.parent.leftChild = y;
			}
			else
			{
				n.parent.rightChild = y;
				y.leftChild = n;
				n.parent = y;
			}
		}
		
		public void rotateRight(Node<Key> n)
		{
			Node<Key> y = n.leftChild;
			n.leftChild = y.rightChild;
			if (y.rightChild != null)
			{
				y.rightChild.parent = n;
			}
			y.parent = n.parent;
			if (n.parent == null)
			{
				root = y;
			}
			else if (n.compareTo(n.parent.rightChild) == 0)
			{
				n.parent.rightChild = y;
			}
			else
			{
				n.parent.leftChild = y;
				y.rightChild = n;
				n.parent = y;
			}
		}
		
		public void fixTree(Node<Key> node)
		{
			Node<Key> curNode = node;
			Node<Key> aunt = getAunt(curNode);
			
			while (aunt != null && curNode.isRed == true && aunt.isRed == true)
			{
				aunt = getAunt(curNode);
				setColor(curNode.parent, false);
				setColor(aunt, false);
				setColor(curNode.parent.parent, true);
				curNode = getGrandparent(curNode);
			}
			
			if (isLeftChild(curNode.parent.parent, curNode.parent) == true)
			{
				if (isLeftChild(curNode.parent, curNode) == false)
				{
					curNode = curNode.parent;
					rotateLeft(curNode);
					
					setColor(curNode.parent, false);
					setColor(curNode.parent.parent, true);
					rotateRight(curNode.parent.parent);
				}
			}
			else
			{
				if (isLeftChild(curNode.parent, curNode) == true)
				{
					curNode = curNode.parent;
					rotateRight(curNode);
					
					setColor(curNode.parent, false);
					setColor(curNode.parent.parent, true);
					rotateLeft(curNode.parent.parent);
				}
			}
		}
		
		public boolean isEmpty(Node<Key> n)
		{
			if (n.key == null)
			{
				return true;
			}
			return false;
		}
		 
		public boolean isLeftChild(Node<Key> parent, Node<Key> child)
		{
			if (child.compareTo(parent) < 0 )
			{//child is less than parent
				return true;
			}
			return false;
		}

		public void preOrderVisit(Visitor<Key> v)
		{
		   	preOrderVisit(root, v);
		}
		 
		 
		private void preOrderVisit(Node<Key> n, Visitor<Key> v)
		{
		  	if (n == null) 
		  	{
		  		return;
		  	}
		  	v.visit(n);
		  	preOrderVisit(n.leftChild, v);
		  	preOrderVisit(n.rightChild, v);
		}
		
		private void setColor(Node<Key> node, boolean color)
		{
			if (color == true)
			{
				node.isRed = true;
				node.color = 1;
			}
			else
			{
				node.isRed = false;
				node.color = 0;
			}
		}
	}

