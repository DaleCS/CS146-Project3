public class RedBlackTree<Key extends Comparable<Key>> 
{	
		public Node<Key> root;

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
		
		/**
		 * Adds a new node using the given data and places it in the BST adhering to BST properties
		 * then colors the node red
		 * @param data
		 * @return
		 */
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
			return newNode;
		}	
		
		/**
		 * Adds a node to the RBTree and applies the properties of a RBT to the node
		 * @param data to be added to the RBTree
		 */
		public void insert(Key data)
		{
			Node<Key> curNode = addNode(data);
			
			if (curNode.compareTo(root) == 0)
			{
				setColor(curNode, false);
			}
			else if (curNode.parent.isRed == false)
			{
				// Do nothing
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
				setColor(y, false);
			}
			else if (n.compareTo(n.parent.leftChild) == 0)
			{
				n.parent.leftChild = y;
			}
			else
			{
				n.parent.rightChild = y;
			}
			y.leftChild = n;
			n.parent = y;
		}
		
		public void rotateRight(Node<Key> y)
		{
			Node<Key> x = y.leftChild;
			y.leftChild = x.rightChild;
			if (x.rightChild != null)
			{
				x.rightChild.parent = y;
			}
			
			x.parent = y.parent;
			if (y.parent == null)
			{
				root = x;
				setColor(x, false);
			}
			else if (y.compareTo(y.parent.rightChild) == 0)
			{
				y.parent.rightChild = x;
			}
			else
			{
				y.parent.leftChild = x;
			}
			x.rightChild = y;
			y.parent = x;
		}
		
		public void fixTree(Node<Key> node)
		{
			Node<Key> curNode = node;
			Node<Key> aunt = getAunt(node);
			
			while (curNode.compareTo(root) != 0 && (curNode.parent.isRed == true && aunt != null && aunt.isRed == true))
			{
				setColor(curNode.parent, false);
				setColor(aunt, false);
				setColor(curNode.parent.parent, true);
				curNode = curNode.parent.parent;
				
				if (curNode.compareTo(root) == 0)
				{
					setColor(curNode, false);
				}
				aunt = getAunt(curNode);
			}
			
			if (curNode.compareTo(root) != 0 && curNode.parent.isRed == true && (aunt == null || aunt.isRed == false))
			{
				if (isLeftChild(curNode.parent.parent, curNode.parent) == true)
				{
					if (curNode.parent.rightChild != null && curNode.compareTo(curNode.parent.rightChild) == 0)
					{
						curNode = curNode.parent;
						rotateLeft(curNode);
					}
					setColor(curNode.parent, false);
					setColor(curNode.parent.parent, true);
					rotateRight(curNode.parent.parent);
				}
				else
				{
					if (curNode.parent.leftChild != null && curNode.compareTo(curNode.parent.leftChild) == 0)
					{
						curNode = curNode.parent;
						rotateRight(curNode);
					}
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
			if (node != null)
			{
				node.isRed = color;
				if (color == true)
				{
					node.color = 0;
				}
				else
				{
					node.color = 1;
				}
			}
		}
	}

