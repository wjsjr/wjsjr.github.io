/**
 * Homework 2 William Sayre , wjs9ej Sources : Big Java Book, Stack Overflow (Cited where used)
 */

import java.util.*;

public class Album extends PhotographContainer {


    /**
     * Constructs new instance of Album class Class. Takes name of
     * new Album as its argument. Sets default value of new Album's
     * Photos, to empty HashSet.
     */
    public Album(String name) {
        super(name);
    }

    
    /**
     * Overrides the Object Class default equals() method. Checks whether two Albums are identical.
     * First tests whether argument o is an instance of Album class. Then casts o into Album class, renaming
     * it other finally compares name of this Album and other.
     * Returns true if both instances of Album have identical names, returns false otherwise. 
     * @param o, Object we are comparing this Album to
     * @return Whether o is a Album with name identical to this Album
     */
    public boolean equals(Object o) {
        if (o instanceof Album) {
            Album other = (Album) o;
            if (other.name == this.name) { //compares fields of this Album and o
                return true;
            }
        }
        return false;
    
    }
}