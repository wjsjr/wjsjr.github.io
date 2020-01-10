import java.util.ArrayList;

/**
 * Binary Search Tree Class
 * 
 * The head class for a binary search tree implementation.
 * 
 * @author wjs9ej
 * @param <Comparable> Type of data to store in the binary tree
 */
public class BinarySearchTree<T extends Comparable<T>> {

    /**
     * A reference pointer to the root of the tree
     */
    private TreeNode<T> root;

    /**
     * Default constructor
     * 
     * Creates a binary tree object with null root note (empty tree)
     */
    public BinarySearchTree() {
        this(null);
    }

    /**
     * Constructor
     * 
     * Creates a binary tree object with the given node as root
     * 
     * @param newRoot The root of the tree
     */
    public BinarySearchTree(TreeNode<T> newRoot) {
        this.root = newRoot;
    }

    /**
     * Get the root of the tree
     * 
     * @return The root of the tree
     */
    public TreeNode<T> getRoot() {
        return root;
    }

    /**
     * Set the root of the tree
     * 
     * @param root  The new root of this tree
     */
    public void setRoot(TreeNode<T> root) {
        this.root = root;
    }

    /**
     * Find if an element exists
     * 
     * Checks to see if the value val appears in the
     * tree (recursively).  Returns true if it appears
     * and false otherwise.
     * 
     * @param val The value to find
     * @return True if the tree contains the value, false otherwise
     */
    public boolean find(T val) {

        if (this.root == null) {
            return false;
        }
        else {
            return this.root.find(val);
        }


    }

    /**
     * Insert an element
     * 
     * Inserts val into the tree where it should appear, returning
     * true on success and false otherwise
     * 
     * @param val The value to insert
     * @return True on success, false otherwise
     */
    public boolean insert(T val) {
        if (this.root == null) {
            TreeNode<T> newNode = new TreeNode<T>();
            this.root = newNode;
            newNode.setData(val);
            return true;
        }

        else {
            return this.root.insert(val);
        }





    }


    /**
     * Delete an element from the tree
     * 
     * Deletes val from the tree if it appears, returning
     * true on success and false otherwise
     * 
     * @param val The value to delete
     * @return True on success, false otherwise
     */
    public boolean delete(T val) {
        if (this.root == null) {
            return false;
        }

        if(this.root.getData().equals(val) && this.root.getLeft() == null && this.root.getRight() == null) {
            this.root = null;
            return true;
        }

        TreeNode<T> currentNode = this.root;
        TreeNode<T> parentNode = this.root;
        boolean correctIndex = false;
        while (correctIndex == false) {
            if (currentNode.getData().compareTo(val) > 0) {
                if (currentNode.getLeft() != null ) {
                    parentNode = currentNode;
                    currentNode = currentNode.getLeft();

                }
            }
            if (currentNode.getData().compareTo(val) < 0) {
                if (currentNode.getRight() != null ) {
                    parentNode = currentNode;
                    currentNode = currentNode.getRight();

                }

            }
            if (currentNode.getData().equals(val)) {
                correctIndex = true;
                break;
            }

            else if(currentNode.getRight() == null  && currentNode.getLeft() == null) {
                correctIndex = true; 
                return false;
            }
        }



        if (currentNode.getLeft() == null && currentNode.getRight() == null) {
            if (parentNode.getLeft() == null) {
                parentNode.setRight(null);
            }
            else if (parentNode.getRight() == null) {
                parentNode.setLeft(null);
            }
            else if (parentNode.getLeft().getData().equals(currentNode.getData())) {
                parentNode.setLeft(null);
            }
            else {
                parentNode.setRight(null);
            }



        }
        else if (currentNode.getLeft() == null) {
            if (parentNode.getLeft() == null) {
                parentNode.setRight(currentNode.getRight());
            }
            else if (parentNode.getRight() == null) {
                parentNode.setLeft(currentNode.getRight());
            }

            else if (parentNode.getLeft().equals(currentNode) ) {
                parentNode.setLeft(currentNode.getRight());
            }
            else {
                parentNode.setRight(currentNode.getRight());
            }

        }
        else if (currentNode.getRight() == null) {
            if (parentNode.getLeft() == null) {
                parentNode.setRight(currentNode.getLeft());
            }
            else if (parentNode.getRight() == null) {
                parentNode.setLeft(currentNode.getLeft());
            }
            else if (parentNode.getLeft().equals(currentNode) ) {
                parentNode.setLeft(currentNode.getLeft());
            }
            else {
                parentNode.setRight(currentNode.getLeft());
            }
        } 
        else {
            TreeNode<T> targetNode = currentNode;;
            parentNode = currentNode;
            currentNode = currentNode.getRight();
            while (currentNode.getLeft() != null) {
                parentNode = currentNode;
                currentNode = currentNode.getLeft();
            }

            targetNode.setData(currentNode.getData());

            if (parentNode.getLeft() == null) {
                parentNode.setRight(null);

            }

            else {
                parentNode.setLeft(null);
            }
        }
        return true;




    }


    /**
     * Build from a list
     * 
     * Build the tree from the given list, overwriting any tree data
     * previously stored in this tree.  Should read from beginning to
     * end of the list and repeatedly call insert() to build the tree.
     * 
     * @param list The list from which to build the tree
     * @return True if successfully built, false otherwise
     */

    public boolean buildFromList(ArrayList<T> list) {
        if (this.root != null) {
            this.root.setData(null);
        }

        for (T item : list) {
            if (insert(item) == false) {
                return false;
            }
        }

        return true;

    }


    /**
     * toString method
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return this.inOrder();
    }


    /**
     * Size
     * 
     * Returns the size of the tree. Returns 0 if the tree's root is null.
     * 
     * @return size of tree
     */
    public int size() {
        if (this.root != null) {
            return this.root.size();
        }
        return 0;

    }

    /**
     * height
     * 
     * Returns the height of the tree. Returns 0 if the tree's root is null.
     * 
     * @return height of tree. Returns 0 if tree's root is null
     * */
    public int height() {
        if (this.root != null) {
            return this.root.height();
        }
        return 0;
    }


    /**
     * Size
     * 
     * Performs an in-order traversal of the tree, returning a string representing data held at each node. 
     * Starts with left children, followed by the root, and finally traverse the right children
     * 
     * @return string showing results of in-order traversal
     * */
    public String inOrder() {
        if (root == null) {
            return "";
        }
        else {
            return this.root.inOrder();
        }
    }


    /**
     * postOrder
     * 
     * Performs an post-order traversal of the tree, returning a string representing data held at each node. 
     * Starts with left children, followed by the right children, and finally traverses the root. 
     * 
     * @return string showing results of post-order traversal
     * */
    public String postOrder() {
        if (root == null) {
            return "";
        }
        else {
            return this.root.postOrder();
        }
    }


    /**
     * Main method
     * 
     * For testing, etc
     * 
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        TreeNode<String> testNode = new TreeNode<String>("moab"); 
        BinarySearchTree<String> bst = new BinarySearchTree<String>(testNode);
        bst.insert("bryce");
        bst.insert("sequoia");
        bst.insert("zion");
        bst.insert("banff");
        bst.insert("norway");
        bst.insert("calgary");
        bst.insert("taos");
        bst.insert("tad");



        System.out.println("Test 1");
        System.out.println("pre-delete" + bst.toString());
        bst.delete("sequoia");



        String expectedS = "(banff)(bryce)(moab)(norway)(sequoia)(zion)";

        System.out.println("Expected:" + expectedS);
        System.out.println("Actual:" + bst.toString()); 

        System.out.println("Test 2");
        bst.delete("moab");
        System.out.println(bst.size());

    }
}