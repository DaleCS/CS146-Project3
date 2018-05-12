public class Node<Key extends Comparable<Key>>
{
	public Key key;  		  
	public Node<Key> parent;
	public Node<Key> leftChild;
	public Node<Key> rightChild;
	public boolean isRed;
	public int color;					// 1 = black, 0 = red
	  
	public Node(Key data)
	{
		this.key = data;
		parent = null;
		leftChild = null;
		rightChild = null;
		isRed = true;
		color = 0;
	}		
	  
	public int compareTo(Node<Key> n)
	{ //this < that  <0
		return key.compareTo(n.key);  	//this > that  >0
	}
}
