/**
 * Homework 3 William Sayre , wjs9ej Sources : Big Java Book, Stack Overflow (Cited where used)
 */
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;

import org.junit.Test;

public class JUnitTestCase {
    /**
     * Tests getPhotos method, with empty photoLibrary
     * @ShouldReturn empty ArrayList

     */
    @Test
    public void testGetPhotosEmpty() {
        //inputs
        String name1 = ("Banff Trip");
        int id1 = 19494;
        PhotoLibrary testLibrary1 = new PhotoLibrary(name1, id1);
        int rating = 1;


        //actual
        ArrayList<Photograph> actual = testLibrary1.getPhotos(rating); 


        //expected
        ArrayList<Photograph> expected = new ArrayList<Photograph>();


        assertEquals ("Error",
                expected, actual);
    }
    /**
     * Tests getPhotos method, with one valid photo in photoLibrary
     * @ShouldReturn ArrayList containing testPhoto

     */
    @Test
    public void testGetPhotosValid() {
        //inputs
        String testCaption = ("Parker Ridge");
        String testfileName = ("TestPhoto1");
        Photograph testPhoto = new Photograph(testCaption, testfileName);
        int newrating = 3;
        testPhoto.setRating(newrating);
        String name1 = ("Banff Trip");
        int id1 = 19494;
        PhotoLibrary testLibrary1 = new PhotoLibrary(name1, id1);
        testLibrary1.addPhoto(testPhoto);


        //actual
        int rating = 2; 
        ArrayList<Photograph> actual = testLibrary1.getPhotos(rating);


        //expected
        ArrayList<Photograph> expected = new ArrayList<Photograph>();
        expected.add(testPhoto);

        assertEquals ("Error",
                expected, actual);
    }


    /**
     * Tests getPhotos method, with one photo in photoLibrary, 
     * that has too low of a rating to pass test.
     * @ShouldReturn empty ArrayList

     */

    @Test
    public void testGetPhotosLowRating() {
        //inputs
        String testCaption = ("Parker Ridge");
        String testfileName = ("TestPhoto1");
        Photograph testPhoto = new Photograph(testCaption, testfileName);
        int newrating = 1;
        testPhoto.setRating(newrating);
        String name1 = ("Banff Trip");
        int id1 = 19494;
        PhotoLibrary testLibrary1 = new PhotoLibrary(name1, id1);
        testLibrary1.addPhoto(testPhoto);


        //actual
        int rating = 2; 
        ArrayList<Photograph> actual = testLibrary1.getPhotos(rating);


        //expected
        ArrayList<Photograph> expected = new ArrayList<Photograph>();

        assertEquals ("Error",
                expected, actual);

    }
    /**
     * Tests getPhotosInMonth method, with empty photoLibrary
     * @ShouldReturn empty ArrayList

     */
    @Test
    public void testGetPhotosInMonthEmpty() {
        //inputs
        String name1 = ("Banff Trip");
        int id1 = 19494;
        PhotoLibrary testLibrary1 = new PhotoLibrary(name1, id1);
        int month = 1;
        int year = 2000;


        //actual
        ArrayList<Photograph> actual = testLibrary1.getPhotosInMonth(month, year); 


        //expected
        ArrayList<Photograph> expected = new ArrayList<Photograph>();


        assertEquals ("Error",
                expected, actual);
    }
    /**
     * Tests getPhotosInMonth method, with valid photo in photoLibrary
     * @ShouldReturn ArrayList containing testPhoto

     */
    @Test
    public void testGetPhotosInMonthValid() {
        //inputs
        String testCaption = ("Parker Ridge");
        String testfileName = ("TestPhoto1");
        int newRating = 3;
        String dateTaken = "2000-01-01";
        Photograph testPhoto = new Photograph(testCaption, testfileName, dateTaken, newRating);
        testPhoto.setRating(newRating);
        String name1 = ("Banff Trip");
        int id1 = 19494;
        PhotoLibrary testLibrary1 = new PhotoLibrary(name1, id1);
        testLibrary1.addPhoto(testPhoto);
        int month = 1;
        int year = 2000;



        //actual 
        ArrayList<Photograph> actual = testLibrary1.getPhotosInMonth(month, year);


        //expected
        ArrayList<Photograph> expected = new ArrayList<Photograph>();
        expected.add(testPhoto);

        assertEquals ("Error",
                expected, actual);
    }
    /**
     * Tests getPhotosInMonth method, with photo in photoLibrary
     * that has the wrong month.
     * @ShouldReturn empty ArrayList

     */
    @Test
    public void testGetPhotosInMonthWrongM() {
        //inputs
        String testCaption = ("Parker Ridge");
        String testfileName = ("TestPhoto1");
        int newRating = 3;
        String dateTaken = "2000-01-01";
        Photograph testPhoto = new Photograph(testCaption, testfileName, dateTaken, newRating);
        testPhoto.setRating(newRating);
        String name1 = ("Banff Trip");
        int id1 = 19494;
        PhotoLibrary testLibrary1 = new PhotoLibrary(name1, id1);
        testLibrary1.addPhoto(testPhoto);
        int month = 2;
        int year = 2000;



        //actual
        ArrayList<Photograph> actual = testLibrary1.getPhotosInMonth(month, year);


        //expected
        ArrayList<Photograph> expected = new ArrayList<Photograph>();

        assertEquals ("Error",
                expected, actual);
    }
    /**
     * Tests getPhotosInMonth method, with photo in photoLibrary
     * that has the wrong year.
     * @ShouldReturn empty ArrayList

     */
    @Test
    public void testGetPhotosInMonthWrongY() {
        //inputs
        String testCaption = ("Parker Ridge");
        String testfileName = ("TestPhoto1");
        int newRating = 3;
        String dateTaken = "2000-01-01";
        Photograph testPhoto = new Photograph(testCaption, testfileName, dateTaken, newRating);
        testPhoto.setRating(newRating);
        String name1 = ("Banff Trip");
        int id1 = 19494;
        PhotoLibrary testLibrary1 = new PhotoLibrary(name1, id1);
        testLibrary1.addPhoto(testPhoto);
        int month = 2;
        int year = 2001;



        //actual
        ArrayList<Photograph> actual = testLibrary1.getPhotosInMonth(month, year);


        //expected
        ArrayList<Photograph> expected = new ArrayList<Photograph>();

        assertEquals ("Error",
                expected, actual);
    }
    /**
     * Tests getPhotosInMonth method, with an invalid date 
     * passed in as argument.  
     * @ShouldReturn null

     */
    @Test
    public void testGetPhotosInMonthBadDate() {
        //inputs
        String testCaption = ("Parker Ridge");
        String testfileName = ("TestPhoto1");
        int newRating = 3;
        String dateTaken = "2000-01-01";
        Photograph testPhoto = new Photograph(testCaption, testfileName, dateTaken, newRating);
        testPhoto.setRating(newRating);
        String name1 = ("Banff Trip");
        int id1 = 19494;
        PhotoLibrary testLibrary1 = new PhotoLibrary(name1, id1);
        testLibrary1.addPhoto(testPhoto);
        int month = 1;
        int year = 200;



        //actual
        ArrayList<Photograph> actual = testLibrary1.getPhotosInMonth(month, year);


        //expected
        ArrayList<Photograph> expected = null;

        assertEquals ("Error",
                expected, actual);
    }
    /**
     * Tests getPhotosBetween method, with an invalid end date 
     * passed in as argument.  
     * @ShouldReturn null

     */
    @Test 
    public void testGetPhotosBetweenInvalidDate() {
        //inputs
        String testCaption = ("Parker Ridge");
        String testfileName = ("TestPhoto1");
        int newRating = 3;
        String dateTaken = "2000-01-01";
        Photograph testPhoto = new Photograph(testCaption, testfileName, dateTaken, newRating);
        testPhoto.setRating(newRating);
        String name1 = ("Banff Trip");
        int id1 = 19494;
        PhotoLibrary testLibrary1 = new PhotoLibrary(name1, id1);
        testLibrary1.addPhoto(testPhoto);
        String begDate = "1999-01-02";
        String endDate = "200-01-013";



        //actual
        ArrayList<Photograph> actual = testLibrary1.getPhotosBetween(begDate, endDate);


        //expected
        ArrayList<Photograph> expected = null;

        assertEquals ("Error",
                expected, actual);
    }
    /**
     * Tests getPhotosBetween method, with an invalid beginning date 
     * passed in as argument.  
     * @ShouldReturn null

     */
    @Test 
    public void testGetPhotosBetweenInvalidDate2() {
        //inputs
        String testCaption = ("Parker Ridge");
        String testfileName = ("TestPhoto1");
        int newRating = 3;
        String dateTaken = "2000-01-01";
        Photograph testPhoto = new Photograph(testCaption, testfileName, dateTaken, newRating);
        testPhoto.setRating(newRating);
        String name1 = ("Banff Trip");
        int id1 = 19494;
        PhotoLibrary testLibrary1 = new PhotoLibrary(name1, id1);
        testLibrary1.addPhoto(testPhoto);
        String begDate = "199--01-02";
        String endDate = "2000-01-013";



        //actual
        ArrayList<Photograph> actual = testLibrary1.getPhotosBetween(begDate, endDate);


        //expected
        ArrayList<Photograph> expected = null;

        assertEquals ("Error",
                expected, actual);
    }
    /**
     * Tests getPhotosBetween method, with valid beginning/end dates, where the
     * beginning date is after the end date.
     * @ShouldReturn null

     */
    @Test 
    public void testGetPhotosBetweenBadDates() {
        //inputs
        String testCaption = ("Parker Ridge");
        String testfileName = ("TestPhoto1");
        int newRating = 3;
        String dateTaken = "2003-01-02";
        Photograph testPhoto = new Photograph(testCaption, testfileName, dateTaken, newRating);
        testPhoto.setRating(newRating);
        String name1 = ("Banff Trip");
        int id1 = 19494;
        PhotoLibrary testLibrary1 = new PhotoLibrary(name1, id1);
        testLibrary1.addPhoto(testPhoto);
        String begDate = "2004-01-02";
        String endDate = "2003-01-01";



        //actual
        ArrayList<Photograph> actual = testLibrary1.getPhotosBetween(begDate, endDate);


        //expected
        ArrayList<Photograph> expected = null;

        assertEquals ("Error",
                expected, actual);
    }
    /**
     * Tests getPhotosBetween method, with valid date range, where the only photo in
     * the library falls after the date range 
     * @ShouldReturn empty arrayList

     */
    @Test 
    public void testGetPhotosBetweenTooLate() {
        //inputs
        String testCaption = ("Parker Ridge");
        String testfileName = ("TestPhoto1");
        int newRating = 3;
        String dateTaken = "2005-01-02";
        Photograph testPhoto = new Photograph(testCaption, testfileName, dateTaken, newRating);
        testPhoto.setRating(newRating);
        String name1 = ("Banff Trip");
        int id1 = 19494;
        PhotoLibrary testLibrary1 = new PhotoLibrary(name1, id1);
        testLibrary1.addPhoto(testPhoto);
        String begDate = "2003-01-01";
        String endDate = "2004-01-01";



        //actual
        ArrayList<Photograph> actual = testLibrary1.getPhotosBetween(begDate, endDate);


        //expected
        ArrayList<Photograph> expected = new ArrayList<Photograph>();

        assertEquals ("Error",
                expected, actual);
    }
    /**
     * Tests getPhotosBetween method, with valid date range, where the only photo in
     * the library falls before the date range 
     * @ShouldReturn empty arrayList

     */
    @Test 
    public void testGetPhotosBetweenTooEarly() {
        //inputs
        String testCaption = ("Parker Ridge");
        String testfileName = ("TestPhoto1");
        int newRating = 3;
        String dateTaken = "2002-01-02";
        Photograph testPhoto = new Photograph(testCaption, testfileName, dateTaken, newRating);
        testPhoto.setRating(newRating);
        String name1 = ("Banff Trip");
        int id1 = 19494;
        PhotoLibrary testLibrary1 = new PhotoLibrary(name1, id1);
        testLibrary1.addPhoto(testPhoto);
        String begDate = "2003-01-01";
        String endDate = "2004-01-01";



        //actual
        ArrayList<Photograph> actual = testLibrary1.getPhotosBetween(begDate, endDate);


        //expected
        ArrayList<Photograph> expected = new ArrayList<Photograph>();

        assertEquals ("Error",
                expected, actual);
    }
    /**
     * Tests getPhotosBetween method, with valid date range, where the photo in
     * the library falls within the date range 
     * @ShouldReturn arrayList containing testPhoto

     */
    @Test 
    public void testGetPhotosValid1() {
        //inputs
        String testCaption = ("Parker Ridge");
        String testfileName = ("TestPhoto1");
        int newRating = 3;
        String dateTaken = "2003-01-02";
        Photograph testPhoto = new Photograph(testCaption, testfileName, dateTaken, newRating);
        testPhoto.setRating(newRating);
        String name1 = ("Banff Trip");
        int id1 = 19494;
        PhotoLibrary testLibrary1 = new PhotoLibrary(name1, id1);
        testLibrary1.addPhoto(testPhoto);
        String begDate = "2003-01-01";
        String endDate = "2003-01-03";



        //actual
        ArrayList<Photograph> actual = testLibrary1.getPhotosBetween(begDate, endDate);


        //expected
        ArrayList<Photograph> expected = new ArrayList<Photograph>();
        expected.add(testPhoto);

        assertEquals ("Error",
                expected, actual);
    }
    /**
     * Tests getPhotosBetween method, with valid date range, where the photo in
     * the library falls on upper edge of the date range 
     * @ShouldReturn arrayList containing testPhoto

     */
    @Test 
    public void testGetPhotosValid2() {
        //inputs
        String testCaption = ("Parker Ridge");
        String testfileName = ("TestPhoto1");
        int newRating = 3;
        String dateTaken = "2003-01-03";
        Photograph testPhoto = new Photograph(testCaption, testfileName, dateTaken, newRating);
        testPhoto.setRating(newRating);
        String name1 = ("Banff Trip");
        int id1 = 19494;
        PhotoLibrary testLibrary1 = new PhotoLibrary(name1, id1);
        testLibrary1.addPhoto(testPhoto);
        String begDate = "2003-01-01";
        String endDate = "2003-01-03";



        //actual
        ArrayList<Photograph> actual = testLibrary1.getPhotosBetween(begDate, endDate);


        //expected
        ArrayList<Photograph> expected = new ArrayList<Photograph>();
        expected.add(testPhoto);

        assertEquals ("Error",
                expected, actual);
    }
    /**
     * Tests getPhotosBetween method, with valid date range, where the photo in
     * the library falls on the lower edge of the date range 
     * @ShouldReturn arrayList containing testPhoto

     */
    @Test 
    public void testGetPhotosValid3() {
        //inputs
        String testCaption = ("Parker Ridge");
        String testfileName = ("TestPhoto1");
        int newRating = 3;
        String dateTaken = "2003-01-01";
        Photograph testPhoto = new Photograph(testCaption, testfileName, dateTaken, newRating);
        testPhoto.setRating(newRating);
        String name1 = ("Banff Trip");
        int id1 = 19494;
        PhotoLibrary testLibrary1 = new PhotoLibrary(name1, id1);
        testLibrary1.addPhoto(testPhoto);
        String begDate = "2003-01-01";
        String endDate = "2003-01-03";



        //actual
        ArrayList<Photograph> actual = testLibrary1.getPhotosBetween(begDate, endDate);


        //expected
        ArrayList<Photograph> expected = new ArrayList<Photograph>();
        expected.add(testPhoto);

        assertEquals ("Error",
                expected, actual);
    }
    /**
     * Tests similarity method, where two PhotoLibraries have identical photos within them
     * @ShouldReturn 1.0

     */
    @Test 
    public void testSimilarity1() {
        //inputs
        String testCaption = ("Parker Ridge");
        String testfileName = ("TestPhoto1");
        String testCaption2 = ("Bryce Canyon");
        int newRating = 3;
        String testfileName2 = ("TestPhoto2");
        Photograph testPhoto2 = new Photograph(testCaption2, testfileName2);
        String dateTaken = "2003-01-01";
        Photograph testPhoto = new Photograph(testCaption, testfileName, dateTaken, newRating);
        testPhoto.setRating(newRating);
        String name1 = ("Banff Trip");
        String name2 = ("4th Year");
        int id1 = 19494;
        int id2 = 15485;
        PhotoLibrary testLibrary1 = new PhotoLibrary(name1, id1);
        PhotoLibrary testLibrary2 = new PhotoLibrary(name2, id2);
        testLibrary1.addPhoto(testPhoto);
        testLibrary1.addPhoto(testPhoto2);
        testLibrary2.addPhoto(testPhoto);

        //actual
        double actual =  PhotoLibrary.similarity(testLibrary1, testLibrary2);


        //expected
        double expected = 1.0;


        assertEquals ("Error",
                expected, actual, 0.0001);
    }
    /**
     * Tests similarity method, where one of the argument PhotoLibraries is empty
     * @ShouldReturn 0.0

     */
    @Test 
    public void testSimilarityEmpty() {
        //inputs
        String testCaption = ("Parker Ridge");
        String testfileName = ("TestPhoto1");
        String testCaption2 = ("Bryce Canyon");
        int newRating = 3;
        String testfileName2 = ("TestPhoto2");
        Photograph testPhoto2 = new Photograph(testCaption2, testfileName2);
        String dateTaken = "2003-01-01";
        Photograph testPhoto = new Photograph(testCaption, testfileName, dateTaken, newRating);
        testPhoto.setRating(newRating);
        String name1 = ("Banff Trip");
        String name2 = ("4th Year");
        int id1 = 19494;
        int id2 = 15485;
        PhotoLibrary testLibrary1 = new PhotoLibrary(name1, id1);
        PhotoLibrary testLibrary2 = new PhotoLibrary(name2, id2);
        testLibrary1.addPhoto(testPhoto);
        testLibrary1.addPhoto(testPhoto2);

        //actual
        double actual =  PhotoLibrary.similarity(testLibrary1, testLibrary2);


        //expected
        double expected = 0.0;


        assertEquals ("Error",
                expected, actual, 0.0001);
    }



    /**
     * Tests getYear method, where the argument string date is valid
     * @ShouldReturn 1911

     */
    @Test
    public void testYrValid() {
        //inputs
        String validdate = "1911-01-01";
        Date testDate = new Date(validdate);

        //actual
        int actual = testDate.getYear();

        //expected
        int expected = 1911;

        assertEquals ("Error",
                expected, actual);

    }
    /**
     * Tests getDay method, where the argument string date is valid
     * @ShouldReturn 1

     */
    @Test
    public void testDayValid() {
        //inputs
        String validdate = "1911-10-01";
        Date testDate = new Date(validdate);

        //actual
        int actual = testDate.getDay();

        //expected
        int expected = 1;

        assertEquals ("Error",
                expected, actual);

    }
    /**
     * Tests getMonth method, where the argument string date is valid
     * @ShouldReturn 10

     */
    @Test
    public void testMonthValid() {
        //inputs
        String validdate = "1911-10-01";
        Date testDate = new Date(validdate);

        //actual
        int actual = testDate.getMonth();

        //expected
        int expected = 10;

        assertEquals ("Error",
                expected, actual);

    }
    /**
     * Tests intDate method, where the argument string date is valid
     * @ShouldReturn 1912001

     */
    @Test
    public void testIntValid() {
        //inputs
        String validdate = "1911-10-01";
        Date testDate = new Date(validdate);

        //actual
        int actual = testDate.intDate;

        //expected
        int expected = 1912001;

        assertEquals ("Error",
                expected, actual);

    }

    /**
     * Tests isValidDate method, where the argument string date is empty
     * @ShouldReturn false

     */
    @Test
    public void testValidInvalid() {
        //inputs
        String invaliddate = "";
        Date testDate = new Date(invaliddate);

        //actual
        boolean actual = testDate.isValidDate();

        //expected
        boolean expected = false;

        assertEquals ("Error",
                expected, actual);

    }
    /**
     * Tests isValidDate method, where the argument string date is the proper
     * length but is missing the first dash
     * @ShouldReturn false

     */
    @Test
    public void testValidInvalid2() {
        //inputs
        String invaliddate = "1911001-01";
        Date testDate = new Date(invaliddate);

        //actual
        boolean actual = testDate.isValidDate();

        //expected
        boolean expected = false;

        assertEquals ("Error",
                expected, actual);

    }
    /**
     * Tests isValidDate method, where the argument string date is the proper
     * length but is missing the second dash
     * @ShouldReturn false

     */
    @Test
    public void testValidInvalid3() {
        //inputs
        String invaliddate = "1911-01001";
        Date testDate = new Date(invaliddate);

        //actual
        boolean actual = testDate.isValidDate();

        //expected
        boolean expected = false;

        assertEquals ("Error",
                expected, actual);

    }
    /**
     * Tests isValidDate method, where the argument string date is valid
     * @ShouldReturn true

     */
    @Test
    public void testValidValid() {
        //inputs
        String validdate = "1911-01-01";
        Date testDate = new Date(validdate);

        //actual
        boolean actual = testDate.isValidDate();

        //expected
        boolean expected = true;

        assertEquals ("Error",
                expected, actual);

    }
    /**
     * Tests isValidDate method, where the argument string date is invalid,
     * since 2019 is not a leap year
     * @ShouldReturn false

     */
    @Test
    public void leapTestnotLeapiv() {
        //inputs
        String validdate = "2019-02-29";
        Date testDate = new Date(validdate);

        //actual
        boolean actual = testDate.isValidDate();

        //expected
        boolean expected = false;

        assertEquals ("Error",
                expected, actual);

    }
    /**
     * Tests isValidDate method, where the argument string date is valid,
     * since 202 is a leap year
     * @ShouldReturn true

     */
    @Test
    public void leapTestLeapv() {
        //inputs
        String validdate = "2020-02-29";
        Date testDate = new Date(validdate);

        //actual
        boolean actual = testDate.isValidDate();

        //expected
        boolean expected = true;

        assertEquals ("Error",
                expected, actual);

    }

    /**
     * Tests erasePhoto method, where the photo library contains the argument photo.
     * @ShouldReturn true 

     */

    @Test
    public void TestRemovePhotoValidOutput() {
        //inputs
        String testCaption = ("Parker Ridge");
        String testfileName = ("TestPhoto1");
        String dateTaken = "2003-01-01";
        String albumName = "Banff";
        int rating = 2;
        Photograph testPhoto = new Photograph(testCaption, testfileName, dateTaken, rating);
        String name1 = ("Banff Trip");
        int id1 = 19494;
        PhotoLibrary testLibrary1 = new PhotoLibrary(name1, id1);
        testLibrary1.addPhoto(testPhoto);
        testLibrary1.createAlbum(albumName);
        testLibrary1.addPhotoToAlbum(testPhoto, albumName);    



        //actual
        boolean actual = testLibrary1.removePhoto(testPhoto); 

        //expected
        boolean expected = true;

        assertEquals ("Error",
                expected, actual);

    }
    /**
     * Tests erasePhoto method, where the photo library does not contain the argument photo.
     * @ShouldReturn false 

     */
    @Test
    public void testRemovePhotoNotFound() {
        //inputs
        String testCaption = ("Parker Ridge");
        String testfileName = ("TestPhoto1");
        String testfileName2 = ("TestPhoto2");
        String dateTaken = "2003-01-01";
        String albumName = "Banff";
        int rating = 2;
        Photograph testPhoto = new Photograph(testCaption, testfileName, dateTaken, rating);
        Photograph testPhoto2 = new Photograph(testCaption, testfileName2, dateTaken, rating);
        String name1 = ("Banff Trip");
        int id1 = 19494;
        PhotoLibrary testLibrary1 = new PhotoLibrary(name1, id1);
        testLibrary1.addPhoto(testPhoto);
        testLibrary1.createAlbum(albumName);
        testLibrary1.addPhotoToAlbum(testPhoto, albumName);    



        //actual
        boolean actual = testLibrary1.removePhoto(testPhoto2); 

        //expected
        boolean expected = false;

        assertEquals ("Error",
                expected, actual);
    }
    /**Tests Comparable in Photograph class, with unique DateTaken values*/
    @Test
    public void compareToTestDate() {
        //inputs
        String testCaption1 = ("Parker Ridge");
        String testCaption2 = ("Bryce Canyon");
        String testfileName1 = ("TestPhoto1");
        String testfileName2 = ("TestPhoto2");
        String dateTaken1 = "2003-01-01";
        String dateTaken2 = "2004-01-01";
        int rating = 5;
        Photograph p1 = new Photograph(testCaption1, testfileName1, dateTaken1, rating);
        Photograph p2 = new Photograph(testCaption2, testfileName2, dateTaken2, rating);
        //actual
        int actual = p1.compareTo(p2);

        //expected
        int expected = -1;
        assertEquals ("compareToError",
                expected, actual);
    }
    /**Tests Comparable in Photograph class, with identical DateTaken values and unique captions*/
    @Test
    public void compareToTestCaption() {
        //inputs
        String testCaption1 = ("Parker Ridge");
        String testCaption2 = ("Bryce Canyon");
        String testfileName1 = ("TestPhoto1");
        String testfileName2 = ("TestPhoto2");
        String dateTaken1 = "2003-01-01";
        String dateTaken2 = "2004-01-01";
        int rating = 5;
        Photograph p1 = new Photograph(testCaption1, testfileName1, dateTaken1, rating);
        Photograph p2 = new Photograph(testCaption2, testfileName2, dateTaken1, rating);
        //actual
        int actual = p1.compareTo(p2);
        assertTrue(actual > 0);



    } 
    /**Tests Comparable in Photograph class, with identical DateTaken values and identical captions*/
    @Test
    public void compareToTestZero() {
        //inputs
        String testCaption1 = ("Parker Ridge");
        String testCaption2 = ("Bryce Canyon");
        String testfileName1 = ("TestPhoto1");
        String testfileName2 = ("TestPhoto2");
        String dateTaken1 = "2003-01-01";
        String dateTaken2 = "2004-01-01";
        int rating = 5;
        Photograph p1 = new Photograph(testCaption1, testfileName1, dateTaken1, rating);
        Photograph p2 = new Photograph(testCaption1, testfileName2, dateTaken1, rating);
        //actual
        int actual = p1.compareTo(p2);

        //expected
        int expected = 0;
        assertEquals ("compareToError",
                expected, actual);
    }
    /**Tests compareCaption comparable, with unique captions*/
    @Test
    public void compareCaptionTest1() {
        //inputs
        String testCaption1 = ("Parker Ridge");
        String testCaption2 = ("Bryce Canyon");
        String testfileName1 = ("TestPhoto1");
        String testfileName2 = ("TestPhoto2");
        String dateTaken1 = "2003-01-01";
        String dateTaken2 = "2004-01-01";
        int rating = 5;
        Photograph p1 = new Photograph(testCaption1, testfileName1, dateTaken1, rating);
        Photograph p2 = new Photograph(testCaption2, testfileName2, dateTaken2, rating);
        PhotoLibrary pl = new PhotoLibrary(testfileName1, 0);
        pl.addPhoto(p1);
        pl.addPhoto(p2);
        //actual
        Collections.sort(pl.photos, new CompareByCaption());
        ArrayList<Photograph> actual = pl.getPhotos();
        ArrayList<Photograph> expected = pl.getPhotos();
        pl.addPhoto(p2);
        pl.addPhoto(p1);


        assertEquals ("compareToError",
                expected, actual);
    }
    /**Tests compareCaption comparable, with identical captions and unique ratings*/
    @Test
    public void compareCaptionTest2() {
        //inputs
        String testCaption1 = ("Parker Ridge");
        String testCaption2 = ("Bryce Canyon");
        String testfileName1 = ("TestPhoto1");
        String testfileName2 = ("TestPhoto2");
        String dateTaken1 = "2003-01-01";
        String dateTaken2 = "2004-01-01";
        int rating = 4;
        int rating2 = 5;
        Photograph p1 = new Photograph(testCaption1, testfileName1, dateTaken1, rating);
        Photograph p2 = new Photograph(testCaption1, testfileName2, dateTaken2, rating2);
        PhotoLibrary pl = new PhotoLibrary(testfileName1, 0);
        pl.addPhoto(p1);
        pl.addPhoto(p2);
        //actual
        Collections.sort(pl.photos, new CompareByCaption());
        ArrayList<Photograph> actual = pl.getPhotos();
        ArrayList<Photograph> expected = pl.getPhotos();
        pl.addPhoto(p2);
        pl.addPhoto(p1);


        assertEquals ("compareToError",
                expected, actual);
    }
    /**Tests compareRating comparable, with ratings*/
    @Test
    public void compareRatingTest1() {
        //inputs
        String testCaption1 = ("Parker Ridge");
        String testCaption2 = ("Bryce Canyon");
        String testfileName1 = ("TestPhoto1");
        String testfileName2 = ("TestPhoto2");
        String dateTaken1 = "2003-01-01";
        String dateTaken2 = "2004-01-01";
        int rating = 4;
        int rating2 = 5;
        Photograph p1 = new Photograph(testCaption1, testfileName1, dateTaken1, rating);
        Photograph p2 = new Photograph(testCaption1, testfileName2, dateTaken2, rating2);
        PhotoLibrary pl = new PhotoLibrary(testfileName1, 0);
        pl.addPhoto(p1);
        pl.addPhoto(p2);
        //actual
        Collections.sort(pl.photos, new CompareByRating());
        ArrayList<Photograph> actual = pl.getPhotos();
        ArrayList<Photograph> expected = pl.getPhotos();
        pl.addPhoto(p2);
        pl.addPhoto(p1);


        assertEquals ("compareToError",
                expected, actual);
    }
    /**Tests compareRating comparable, with identical ratings and unique captions*/
    @Test
    public void compareRatingTest2() {
        //inputs
        String testCaption1 = ("Parker Ridge");
        String testCaption2 = ("Bryce Canyon");
        String testfileName1 = ("TestPhoto1");
        String testfileName2 = ("TestPhoto2");
        String dateTaken1 = "2003-01-01";
        String dateTaken2 = "2004-01-01";
        int rating = 5;
        Photograph p1 = new Photograph(testCaption1, testfileName1, dateTaken1, rating);
        Photograph p2 = new Photograph(testCaption2, testfileName2, dateTaken2, rating);
        PhotoLibrary pl = new PhotoLibrary(testfileName1, 0);
        pl.addPhoto(p1);
        pl.addPhoto(p2);
        //actual
        Collections.sort(pl.photos, new CompareByRating());
        ArrayList<Photograph> actual = pl.getPhotos();
        ArrayList<Photograph> expected = pl.getPhotos();
        pl.addPhoto(p2);
        pl.addPhoto(p1);


        assertEquals ("compareToError",
                expected, actual);
    }

}