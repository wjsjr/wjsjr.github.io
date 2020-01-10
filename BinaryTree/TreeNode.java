
/**
 * Binary Tree Node
 * 
 * Tree node that has two children: left and right
 * 
 * @author YOURID
 * @param <Comparable> The type of data this tree node stores
 */
public class TreeNode<T extends Comparable<T>> {

    /**
     * Reference pointer to the left subtree
     */
    private TreeNode<T> left;

    /**
     * Reference pointer to the right subtree
     */
    private TreeNode<T> right;

    /**
     * Data stored at this node
     */
    private T data;

    /**
     * Default Constructor
     * 
     * Creates a binary tree node with null data and null children
     */
    public TreeNode(){
        this(null,null,null);
    }

    /**
     * Data-only Constructor
     * 
     * Creates a binary tree node with the given data and null children
     * 
     * @param theData The data to store at this node
     */
    public TreeNode(T theData){
        this(theData,null,null);
    }


    /**
     * Full Constructor
     * 
     * Creates a binary tree node with the given data and child reference pointers
     * 
     * @param theData The data to store at this node
     * @param leftChild A reference pointer to the left subtree
     * @param rightChild A reference pointer to the right subtree
     */
    public TreeNode(T theData, TreeNode<T> leftChild, TreeNode<T> rightChild){
        data = theData;
        left = leftChild;
        right = rightChild;
    }


    /**
     * Left Child/Subtree getter
     * 
     * @return A reference pointer to the root of the left subtree
     */
    public TreeNode<T> getLeft() {
        return left;
    }

    /**
     * Left Child/Subtree Setter
     * 
     * @param left A reference pointer to the new left subtree's root node
     */
    public void setLeft(TreeNode<T> left) {
        this.left = left;
    }

    /**
     * Right Child/Subtree getter
     * 
     * @return A reference pointer to the root of the right subtree
     */
    public TreeNode<T> getRight() {
        return right;
    }

    /**
     * Right Child/Subtree Setter
     * 
     * @param left A reference pointer to the new right subtree's root node
     */
    public void setRight(TreeNode<T> right) {
        this.right = right;
    }

    /**
     * Get the data at this node
     * 
     * @return The data stored at this node
     */
    public T getData() {
        return data;
    }

    /**
     * Set the data at this node
     * 
     * @param data The data to be stored at this node
     */
    public void setData(T data) {
        this.data = data;
    }

    /**
     * toString method
     */
    @Override
    public String toString() {
        String retVal = "";
        if (this.left != null)
            retVal += this.left.toString(); 
        if (this.right != null) 
            retVal += this.right.toString();
        retVal += "("+this.data+")"; 
        return retVal;
    }



    public String inOrder() {
        String output = ""; 
        if (left != null && right != null) {
            output += left.inOrder() + "(" + this.getData().toString() + ")" + right.inOrder();
        }
        else if (left != null) {
            output += left.inOrder() + "(" + this.getData().toString() + ")";
        }
        
        else if(right != null) {
            output += "(" + this.getData().toString() + ")" + right.inOrder();
        }
        
        else {
            output += "(" + this.getData().toString() + ")";
        }
        
        
        return output;
            
       
    }


    public String postOrder() {
        if (left != null && right != null) {
            return left.postOrder() + right.postOrder() + "(" + this.getData().toString() + ")";
        }
        else if (left != null) {
            return left.postOrder() + "(" + this.getData().toString() + ")";
        }
        
        else if(right != null) {
            return right.postOrder() + "(" + this.getData().toString() + ")";
        }
        
        else {
            return "(" + this.getData().toString() + ")";
        }
    }
    
    public boolean find (T val) {
        TreeNode<T> next = null;
        if (this.data != null) {
        if (this.data.equals(val)) {
            return true;
        }
        
        else if (this.getData().compareTo(val) > 0) {
            next = this.left;
        }
        
        else if (this.getData().compareTo(val) < 0) {
            next = this.right;
        }
        
        if (next == null) {
            return false;
        }
        return next.find(val);
        /* boolean output = false;
        if (this.data != null) {
            
        if (this.data.equals(val)) {
            output = true;
            return output;    
         }
         
         else if (this.getData().compareTo(val) > 0) {
             if (this.getLeft() != null) {
                 return this.getLeft().find(val);
             }
                     
         }
         else if (this.getRight() != null) {
             return this.getRight().find(val);
             
         }
        }
        return output; */
        }
        return false;
    }


    /**
     * Size
     * 
     * Node-level size method. Determines size by counting up node and its children. 
     * 
     * @return size of tree formed by node
     */
    public int size() {
        int s = 0;
        
        if(this.getLeft() != null && this.getRight() != null) {
            s += left.size();
            s += right.size();
        }
        
        else if(this.getRight() != null) {
            s += right.size();
        }
        
        else if(this.getLeft() != null) {
            s += left.size();
        }

        
        return s + 1;

    }
    /**
     * Height
     * 
     * Node-level height method. Determines height of tree which this node forms. 
     * 
     * @return size of tree formed by node
     */
    public int height() {
        int lh = 1;
        int rh = 1;
        if(this.left!= null) { 
            lh = this.left.height() +1;
            }
        if(this.right!= null) { 
            rh = this.right.height() + 1;
                }
            
        if(lh > rh) { //Returns the greater of left and right side
            return lh;
        } else {
            return rh;
        }
            
        
    }
    
    
    public boolean insert(T val) {
        if (this.data == null) {
            this.setData(val);
            return true; 
        }
        
        if (this.find(val)) {
            return false;
        }
        
        if (this.data.compareTo(val) > 0) {
            if (this.left == null) {
                TreeNode<T> newNode = new TreeNode<T>(val);
                this.setLeft(newNode);
                return true;
            }
            else {
                this.left.insert(val);
            }
        }
        
        if (this.data.compareTo(val) < 0) {
            if (this.right == null) {
                TreeNode<T> newNode = new TreeNode<T>(val);
                this.setRight(newNode);
                return true;
            }
            else {
                this.right.insert(val);
            }
        }
        return true;

    
    }


    /**
     * Main method
     * 
     * For main method testing, etc
     * 
     * @param args Command-line arguments
     * 
     */
    public static void main(String[] args) {

    }

}