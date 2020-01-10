/**
 * Homework 3 William Sayre , wjs9ej Sources : Big Java Book, Stack Overflow (Cited where used)
 */
import java.util.Comparator;
/**
 * Creates comparator class which allows user to compare Photographs based on Rating. If Rating of two argument Photographs is identical, Photographs are compared on basis of caption
 */
public class CompareByCaption implements Comparator<Photograph>{
    @Override
    public int compare (Photograph p1, Photograph p2) { 

        if ((p1.getCaption().compareTo(p2.getCaption())) != 0) {
            return p1.getCaption().compareTo(p2.getCaption());
        }
        return p2.getRating() - p1.getRating();

    }
}
