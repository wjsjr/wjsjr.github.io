import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class HW6Tests {

    @Test
    public void heightTest() {
        TreeNode<String> testNode = new TreeNode<String>("moab"); 
        TreeNode<String> testNodeTwo = new TreeNode<String>("bryce");
        TreeNode<String> testNodeThree = new TreeNode<String>("sequoia");
        TreeNode<String> testNodeFour = new TreeNode<String>("zion");
        TreeNode<String> testNodeFive = new TreeNode<String>("banff");
        TreeNode<String> testNodeSix = new TreeNode<String>("norway");
        TreeNode<String> testNodeSeven = new TreeNode<String>("calgary");
        TreeNode<String> testNodeEight = new TreeNode<String>("arizona");
        
        testNode.setLeft(testNodeTwo);
        testNode.setRight(testNodeThree);
        testNodeThree.setRight(testNodeFour);
        testNodeThree.setLeft(testNodeSix);
        testNodeTwo.setLeft(testNodeFive);
        testNodeTwo.setRight(testNodeSeven);
        testNodeFive.setLeft(testNodeEight);
        BinarySearchTree<String> bst = new BinarySearchTree<String>(testNode);
        int actual = bst.height(); 
        int expected = 4;
        assertEquals ("Error",
                expected, actual);
        
    }
    @Test
    public void heightTestNull() {
        BinarySearchTree<String> bst = new BinarySearchTree<String>();
        int actual = bst.height(); 
        int expected = 0;
        assertEquals ("Error",
                expected, actual);
        
    }
    @Test
    public void sizeTest() {
        TreeNode<String> testNode = new TreeNode<String>("moab"); 
        TreeNode<String> testNodeTwo = new TreeNode<String>("bryce");
        TreeNode<String> testNodeThree = new TreeNode<String>("sequoia");
        TreeNode<String> testNodeFour = new TreeNode<String>("zion");
        TreeNode<String> testNodeFive = new TreeNode<String>("banff");
        TreeNode<String> testNodeSix = new TreeNode<String>("norway");
        TreeNode<String> testNodeSeven = new TreeNode<String>("calgary");
        testNode.setLeft(testNodeTwo);
        testNode.setRight(testNodeThree);
        testNodeThree.setRight(testNodeFour);
        testNodeThree.setLeft(testNodeSix);
        testNodeTwo.setLeft(testNodeFive);
        testNodeTwo.setRight(testNodeSeven);
        BinarySearchTree<String> bst = new BinarySearchTree<String>(testNode);
        int actual = bst.size(); 
        int expected = 7;
        assertEquals ("Error",
                expected, actual);
        
    }
    @Test
    public void sizeTestTwo() {
        TreeNode<String> testNode = new TreeNode<String>("moab"); 
        TreeNode<String> testNodeTwo = new TreeNode<String>("bryce");
        TreeNode<String> testNodeThree = new TreeNode<String>("sequoia");
        TreeNode<String> testNodeFour = new TreeNode<String>("zion");
        TreeNode<String> testNodeFive = new TreeNode<String>("banff");
        TreeNode<String> testNodeSix = new TreeNode<String>("norway");
        TreeNode<String> testNodeSeven = new TreeNode<String>("calgary");
        TreeNode<String> testNodeEight = new TreeNode<String>("alabama");
        TreeNode<String> testNodeNine = new TreeNode<String>("apple");
        TreeNode<String> testNodeTen = new TreeNode<String>("ack");
        
        testNode.setLeft(testNodeTwo);
        testNode.setRight(testNodeThree);
        testNodeThree.setRight(testNodeFour);
        testNodeThree.setLeft(testNodeSix);
        testNodeTwo.setLeft(testNodeFive);
        testNodeTwo.setRight(testNodeSeven);
        testNodeFive.setLeft(testNodeNine);
        testNodeNine.setLeft(testNodeEight);
        testNodeEight.setLeft(testNodeTen);
        
        BinarySearchTree<String> bst = new BinarySearchTree<String>(testNode);
        int actual = bst.size();
        int expected = 10;
        assertEquals ("Error",
                expected, actual);
        
    }
    @Test
    public void findTTest() {
        TreeNode<String> testNode = new TreeNode<String>("moab"); 
        TreeNode<String> testNodeTwo = new TreeNode<String>("bryce");
        TreeNode<String> testNodeThree = new TreeNode<String>("sequoia");
        TreeNode<String> testNodeFour = new TreeNode<String>("zion");
        TreeNode<String> testNodeFive = new TreeNode<String>("banff");
        TreeNode<String> testNodeSix = new TreeNode<String>("norway");
        TreeNode<String> testNodeSeven = new TreeNode<String>("calgary");
        testNode.setLeft(testNodeTwo);
        testNode.setRight(testNodeThree);
        testNodeThree.setRight(testNodeFour);
        testNodeThree.setLeft(testNodeSix);
        testNodeTwo.setLeft(testNodeFive);
        testNodeTwo.setRight(testNodeSeven);
        BinarySearchTree<String> bst = new BinarySearchTree<String>(testNode);
        boolean actual = bst.find("calgary");
        boolean expected = true;
        assertEquals ("Error",
                expected, actual);
        
    }
    @Test
    public void findFTest() {
        BinarySearchTree<String> bst = new BinarySearchTree<String>();
        boolean actual = bst.find("moab");
        boolean expected = false;
        assertEquals ("Error",
                expected, actual);
        
    }

        
    
    @Test
    public void insertTTest() {
        TreeNode<String> testNode = new TreeNode<String>("moab"); 
        TreeNode<String> testNodeTwo = new TreeNode<String>("bryce");
        TreeNode<String> testNodeThree = new TreeNode<String>("sequoia");
        TreeNode<String> testNodeFour = new TreeNode<String>("zion");
        TreeNode<String> testNodeFive = new TreeNode<String>("banff");
        TreeNode<String> testNodeSix = new TreeNode<String>("norway");
        TreeNode<String> testNodeSeven = new TreeNode<String>("calgary");
        testNode.setLeft(testNodeTwo);
        testNode.setRight(testNodeThree);
        testNodeThree.setRight(testNodeFour);
        testNodeThree.setLeft(testNodeSix);
        testNodeTwo.setLeft(testNodeFive);
        testNodeTwo.setRight(testNodeSeven);
        BinarySearchTree<String> bst = new BinarySearchTree<String>(testNode);
        bst.insert("blackCanyon");
        
        String expectedS = "(banff)(blackCanyon)(bryce)(calgary)(moab)(norway)(sequoia)(zion)";
        String actualS = bst.inOrder();
        
        assertEquals ("Error",
                expectedS, actualS);
        
        //Might wanna do some sort of test to make sure blackCanyon is somewhere in the tree 
        
    }
    @Test
    public void insertFTest() {
        TreeNode<String> testNode = new TreeNode<String>("moab"); 
        TreeNode<String> testNodeTwo = new TreeNode<String>("bryce");
        TreeNode<String> testNodeThree = new TreeNode<String>("sequoia");
        TreeNode<String> testNodeFour = new TreeNode<String>("zion");
        TreeNode<String> testNodeFive = new TreeNode<String>("bryce");
        TreeNode<String> testNodeSix = new TreeNode<String>("norway");
        TreeNode<String> testNodeSeven = new TreeNode<String>("calgary");
        testNode.setLeft(testNodeTwo);
        testNode.setRight(testNodeThree);
        testNodeThree.setRight(testNodeFour);
        testNodeThree.setLeft(testNodeSix);
        testNodeTwo.setLeft(testNodeFive);
        testNodeTwo.setRight(testNodeSeven);
        BinarySearchTree<String> bst = new BinarySearchTree<String>(testNode);
        boolean actual = bst.insert("calgary");
        boolean expected = false; 
        assertEquals ("Error",
                expected, actual);

        
    }
    @Test
    public void deleteTTest() {
        TreeNode<String> testNode = new TreeNode<String>("moab"); 
        TreeNode<String> testNodeTwo = new TreeNode<String>("bryce");
        TreeNode<String> testNodeThree = new TreeNode<String>("sequoia");
        TreeNode<String> testNodeFour = new TreeNode<String>("zion");
        TreeNode<String> testNodeFive = new TreeNode<String>("banff");
        TreeNode<String> testNodeSix = new TreeNode<String>("norway");
        TreeNode<String> testNodeSeven = new TreeNode<String>("calgary");
        testNode.setLeft(testNodeTwo);
        testNode.setRight(testNodeThree);
        testNodeThree.setRight(testNodeFour);
        testNodeThree.setLeft(testNodeSix);
        testNodeTwo.setLeft(testNodeFive);
        testNodeTwo.setRight(testNodeSeven);
        BinarySearchTree<String> bst = new BinarySearchTree<String>(testNode);
        boolean actual = bst.delete("calgary");
        boolean expected = true;
        assertEquals ("Error",
                expected, actual);
        
        
        String expectedS = "(banff)(bryce)(moab)(norway)(sequoia)(zion)";
        String actualS = bst.toString(); 
        
        assertEquals ("Error",
                expectedS, actualS);
        
    }
    @Test
    public void deleteFTest() {
        TreeNode<String> testNode = new TreeNode<String>("moab"); 
        TreeNode<String> testNodeTwo = new TreeNode<String>("bryce");
        TreeNode<String> testNodeThree = new TreeNode<String>("sequoia");
        TreeNode<String> testNodeFour = new TreeNode<String>("zion");
        TreeNode<String> testNodeFive = new TreeNode<String>("banff");
        TreeNode<String> testNodeSix = new TreeNode<String>("norway");
        TreeNode<String> testNodeSeven = new TreeNode<String>("calgary");
        testNode.setLeft(testNodeTwo);
        testNode.setRight(testNodeThree);
        testNodeThree.setRight(testNodeFour);
        testNodeThree.setLeft(testNodeSix);
        testNodeTwo.setLeft(testNodeFive);
        testNodeTwo.setRight(testNodeSeven);
        BinarySearchTree<String> bst = new BinarySearchTree<String>(testNode);
        boolean actual = bst.delete("blackCanyon");
        boolean expected = false;
        assertEquals ("Error",
                expected, actual);

        
    }

    @Test
    public void inOrderTestOne() {
        TreeNode<String> testNode = new TreeNode<String>("moab"); 
        TreeNode<String> testNodeTwo = new TreeNode<String>("bryce");
        TreeNode<String> testNodeThree = new TreeNode<String>("sequoia");
        TreeNode<String> testNodeFour = new TreeNode<String>("zion");
        TreeNode<String> testNodeFive = new TreeNode<String>("banff");
        TreeNode<String> testNodeSix = new TreeNode<String>("norway");
        TreeNode<String> testNodeSeven = new TreeNode<String>("calgary");
        testNode.setLeft(testNodeTwo);
        testNode.setRight(testNodeThree);
        testNodeThree.setRight(testNodeFour);
        testNodeThree.setLeft(testNodeSix);
        testNodeTwo.setLeft(testNodeFive);
        testNodeTwo.setRight(testNodeSeven);
        BinarySearchTree<String> bst = new BinarySearchTree<String>(testNode);
        String actual = bst.inOrder();
        String expected= "(banff)(bryce)(calgary)(moab)(norway)(sequoia)(zion)";
        assertEquals ("Error",
                expected, actual);

        
    }
    @Test
    public void inOrderTestTwo() {
        BinarySearchTree<String> bst = new BinarySearchTree<String>();
        String actual = bst.inOrder();
        String expected = "";
        assertEquals ("Error",
                expected, actual);
        
    }
    @Test
    public void postOrderTestOne() {
        TreeNode<String> testNode = new TreeNode<String>("moab"); 
        TreeNode<String> testNodeTwo = new TreeNode<String>("bryce");
        TreeNode<String> testNodeThree = new TreeNode<String>("sequoia");
        TreeNode<String> testNodeFour = new TreeNode<String>("zion");
        TreeNode<String> testNodeFive = new TreeNode<String>("banff");
        TreeNode<String> testNodeSix = new TreeNode<String>("norway");
        TreeNode<String> testNodeSeven = new TreeNode<String>("calgary");
        BinarySearchTree<String> bst = new BinarySearchTree<String>(testNode);
        bst.insert("bryce");
        bst.insert("sequoia");
        bst.insert("zion");
        bst.insert("banff");
        bst.insert("norway");
        bst.insert("calgary");
        
        String actual = bst.postOrder();
        String expected= "(banff)(calgary)(bryce)(norway)(zion)(sequoia)(moab)";
        assertEquals ("Error",
                expected, actual);

        
    }
    @Test
    public void postOrderTestTwo() {
        BinarySearchTree<String> bst = new BinarySearchTree<String>();
        String actual = bst.postOrder();
        String expected = "";
        assertEquals ("Error",
                expected, actual);
        
    }
    
    @Test
    public void toStringTestOne() {
        TreeNode<String> testNode = new TreeNode<String>("moab"); 
        TreeNode<String> testNodeTwo = new TreeNode<String>("bryce");
        TreeNode<String> testNodeThree = new TreeNode<String>("sequoia");
        TreeNode<String> testNodeFour = new TreeNode<String>("zion");
        TreeNode<String> testNodeFive = new TreeNode<String>("banff");
        TreeNode<String> testNodeSix = new TreeNode<String>("norway");
        TreeNode<String> testNodeSeven = new TreeNode<String>("calgary");
        testNode.setLeft(testNodeTwo);
        testNode.setRight(testNodeThree);
        testNodeThree.setRight(testNodeFour);
        testNodeThree.setLeft(testNodeSix);
        testNodeTwo.setLeft(testNodeFive);
        testNodeTwo.setRight(testNodeSeven);
        BinarySearchTree<String> bst = new BinarySearchTree<String>(testNode);
        String actual = bst.toString();
        String expected= "(banff)(bryce)(calgary)(moab)(norway)(sequoia)(zion)";
        assertEquals ("Error",
                expected, actual);
        
    }
    
    @Test
    public void toStringTestTwo() {
        BinarySearchTree<String> bst = new BinarySearchTree<String>();
        String actual = bst.toString();
        String expected = "";
        assertEquals ("Error",
                expected, actual);
        
    }
    
    @Test
    public void buildFromListTestT() {
        ArrayList<String> testList = new ArrayList<String>();
        testList.add("calgary");
        testList.add("banff");
        testList.add("bryce");
        testList.add("zion");
        testList.add("moab");
        testList.add("norway");
        testList.add("sequioa");
        TreeNode<String> testNode = new TreeNode<String>("zion");
        BinarySearchTree<String> bst = new BinarySearchTree<String>(testNode);
        boolean actual = bst.buildFromList(testList);
        boolean expected = true;
        assertEquals ("Error",
                expected, actual);
        
        String actualS = bst.toString();
        String expectedS = "(banff)(bryce)(calgary)(moab)(norway)(sequioa)(zion)";
        
        assertEquals ("Error",
                expectedS, actualS);
        
    }
    
    @Test
    public void buildFromListTest2() {
        ArrayList<String> testList = new ArrayList<String>();
        testList.add("calgary");
        testList.add("banff");
        testList.add("bryce");
        testList.add("zion");
        testList.add("moab");
        testList.add("norway");
        testList.add("sequioa");
        testList.add("calgary");
        
        
        BinarySearchTree<String> bst = new BinarySearchTree<String>();
        boolean actual = bst.buildFromList(testList);
        boolean expected = false;
        assertEquals ("Error",
                expected, actual);
        
    }







}
 