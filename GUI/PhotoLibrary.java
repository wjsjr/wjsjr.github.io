/**
 * /**
 * Homework 3 William Sayre , wjs9ej Sources : Big Java Book, Stack Overflow (Cited where used)
 */


import java.util.*;


public class PhotoLibrary extends PhotographContainer {

    /**
     * Holds unique ID which identifies each PhotoLibrary
     */
    private final int id;
    /**
     * Holds photos which make up each PhotoLibrary
     */

    private HashSet<Album> albums;

    /**
     * Constructs new instance of PhotoLibrary Class. Takes name and id of
     * new PhotoLibrary as its arguments. Sets default value of new PhotoLibraries
     * Photos, to empty list.
     */
    public PhotoLibrary(String name, int id) {
        super(name);
        this.photos = photos;
        this.name = name;
        this.id = id;
        HashSet<Album> albums = new HashSet<Album>();
        this.albums = albums;
    }



    /**
     * "Getter" which returns the unique of this photo library
     * @return this.id ID # of this photo library

     */
    public int getId() {
        return this.id;
    }

    /**
     * "Getter" which returns the albums contained in this photo library
     * @return this.albums hash set of alubms contained in this library 

     */
    public HashSet<Album> getAlbums() {
        return this.albums;
    }


    /** 
     * This tests whether an instance of Photograph class is in this photo library,
     * and removes that instance if it is found. It also checks this libraries albums, and removes
     * Photograph from any Albums which contain it First tests to see if photograph p is
     * in library. If so, this instance is removed from the library, and true is returned.
     * If no instance of p is found in the photo library, returns false
     * @param p Photograph which is removed from library and albums if found
     * @return whether or not p was found and removed

     */


    public boolean removePhoto(Photograph p) {
        boolean output = false;
        if (this.hasPhoto(p)) {
            photos.remove(p);
            for (Album a : this.albums) {
                if (a.hasPhoto(p)) {
                    a.removePhoto(p);
                }
            }
            output = true;


        }
        else {
            for (Album a : this.albums) {
                if (a.hasPhoto(p)) {
                    a.removePhoto(p);
                    output = true;
                }

            }


        }

        return output;
    }

    /**
     * Overrides the Object Class default equals() method. Checks whether two photo libraries are identical.
     * First tests whether argument o is an instance of PhotoLibrary class. Then casts o into PhotoLibrary class, renaming
     * it other. Then compares the unique ID number of this photo library class and other
     * Returns true if both instances of PhotoLibrary have identical ids, returns false otherwise. 
     * @param o, Object we are comparing this PhotoLibrary to
     * @return Whether o is a Photolibrary with id field identical to this Photolibrary
     */
    public boolean equals(Object o) {
        if (o instanceof PhotoLibrary) {
            PhotoLibrary other = (PhotoLibrary) o;
            if (this.id == other.id) { // Make sure this works. If not, probably issue with Arraylist.equals
                return true;
            }
        }
        return false;
    }
    /**
     * Overrides the Object Class default toString() method. Returns string containing this photo libraries name, id, and photos
     * fields.
     * @return this.caption this.fileName this.photos, these are the fields of this Photograph
     */
    public String toString() {
        return ("Name: " + this.name + "\n" + "ID: " + this.id + "\n" + "Photos: " + this.photos + "\n" + "Albums" + this.albums ); // Might need to do something
        // to get photos to look right

    }
    /**
     * Compares two photo libraries, and creates an array list of all photographs found in both 
     * libraries. First determines which of the two photo libraries has less photos in it. 
     * Then iterates through this shorter list of photos, and compares each photo in the list 
     * to the photos contained in the other library. Any photo from the iterated list, which
     * is found in the other library is added to the output list. The output list is finally returned
     * after iterating through the shorter list of photos
     * @param a the first photo library which is tested
     * @param b the second photo library whichis tested
     * @return Output array list which contains all photos found in both libraries 
     * 
     */
    public static ArrayList<Photograph> commonPhotos(PhotoLibrary a, PhotoLibrary b) {
        ArrayList<Photograph> Output = new ArrayList<Photograph>();
        int trials = a.photos.size();
        if (trials > b.photos.size()) { //tests to see if a or b is longer, adjusts number of trials accordingly
            trials = b.photos.size();
            for (int i = 0; i < trials; i++) { //if b is longer than a, iterates through b
                if (a.hasPhoto(b.photos.get(i))) {
                    Output.add(b.photos.get(i));
                }
            }
        } else {
            for (int i = 0; i < trials; i++) { //if a is longer than b iterates through a
                if (b.hasPhoto(a.photos.get(i))) {
                    Output.add(a.photos.get(i));
                }
            }
        }
        return Output;
    }
    /**
     * Compares two photo libraries, finds number of photos common to both libraries
     * and divides that number by the length of the smaller of the two libraries
     * First creates array list containing each photo found in both libraries,
     * then finds the length of each of the three libraries (two arguments and common library)
     * If either of the two argument libraries contain no photos, returns 0.0. Otherwise
     * divides the length of the common library by the length of the shorter of the two argument libraries
     * returns this difference.
     * @param a the first photo library which is tested
     * @param b the second photo library which is tested
     * @return cnum / denom this is number of photographs found in both argument libraries (cnum) divided
     * by the length of the shorter argument library (denom)
     * 
     */
    public static double similarity(PhotoLibrary a, PhotoLibrary b) { 
        ArrayList<Photograph> Common = new ArrayList<Photograph>();
        Common = commonPhotos(a, b);
        int anum = a.photos.size();
        int bnum = b.photos.size();
        int cnum = Common.size();
        if (anum == 0 || bnum == 0) { //if b and a are both empty, returns o
            return 0.0;
        }
        double denom = anum; //default denominator is a length 
        double output;
        if (bnum < anum) { // if b is shorter than a, sets denominator to b length 
            denom = bnum;
        }

        return cnum / denom;
    }


    /**
     * Creates new Album, with the name passed in as an argument. Checks if Library already contains Album
     * with this name. If not, adds new album to this libraries hashSet of albums. Returns true if new
     * Album succesfully added to this.albums. Returns false otherwise. 
     * @param albumName name of new album which is created
     * @return returns true if adding new album to set of albums is succesful, returns false otherwise  
     * 
     */
    public boolean createAlbum (String albumName) {
        Album newAlbum = new Album(albumName);
        if (!this.albums.contains(newAlbum)) { //tests whether albums contains this new album 
            albums.add(newAlbum);
            return true;
        }
        return false;

    }

    /**
     * Creates new Album, with the name passed in as an argument. Checks if Library already contains Album
     * with this name. If not, returns false. If it does, returns true and removes Album from libraries set of albums.
     * @param albumName name of new album which is created
     * @return returns true if removal of album from set of albums is successful, returns false otherwise  
     * 
     */

    public boolean removeAlbum (String albumName) {
        Album testAlbum = new Album(albumName); //using this due to structure of album.equals()
        if (this.albums.contains(testAlbum)) {
            albums.remove(testAlbum);
            return true;
        }
        return false;

    }
    /**
     * Creates new Album, with the name passed in as an argument. Checks if Library contains Album with this name
     * and photograph which was also passed in as argument. If so, iterates through set of albums, when album
     * with albumName is found, photograph p is added to that album. Returns true if p successfully added to album named albumName,
     * returns false otherwise.
     * @param albumName name of destination album, Photograph p is new photograph which is added to destination album 
     * @return returns true if addition of p is successful, returns false otherwise  
     * 
     */
    public boolean addPhotoToAlbum (Photograph p, String albumName) {
        Album testAlbum = new Album(albumName);
        if (this.hasPhoto(p) && this.albums.contains(testAlbum)) { //tests whether library has photo and album arguments
            for (Album a : this.albums ) { //iterates through set of albums
                if (a.equals(testAlbum)) {
                    a.addPhoto(p);
                    return true;
                }
            }

        }
        return false;  
    }



    /**
     * Creates new Album, with the name passed in as an argument. Checks if Library contains Album
     * with this name. If not, returns false. If it does, iterates through set of albums in the library,
     * tests whether each album is named albumName and contains photograph p. If albumName is found and contains
     * photograph p, photograph p is removed from Album and output is set to true. Otherwise output remains false.
     * Returns output.
     * @return returns true if removal of photo from album is successful, returns false otherwise  
     * 
     */

    public boolean removePhotoFromAlbum (Photograph p, String albumName) {
        Album testAlbum = new Album(albumName);
        boolean output = false;
        if (this.albums.contains(testAlbum)) {

            for (Album a : this.albums) { //iterates through albums in this library 
                if (a.equals(testAlbum) && (a.hasPhoto(p))){
                    a.removePhoto(p);
                    output = true;
                }
            }



        }
        return output;

    }




    /**
     * Creates new Album, with the name passed in as an argument. Checks if Library contains Album
     * with this name. If not, returns false. If it does, iterates through set of albums in the library,
     * tests whether each album is named albumName and contains photograph p. If albumName is found and contains
     * photograph p, photograph p is removed from Album and output is set to true. Otherwise output remains false.
     * Returns output.
     * @return returns true if removal of photo from album is successful, returns false otherwise  
     * 
     */    
    private Album getAlbumByName(String albumName) {
        Album testAlbum = new Album(albumName);
        if (this.albums.contains(testAlbum)) { //test to see if library contains albumName
            Iterator<Album> iter = this.albums.iterator(); 
            while (iter.hasNext()) { //iterates through albums, runs while there is still another album in set
                if (iter.next().equals(testAlbum)) {
                    return iter.next();
                }
            }

        }
        return null; 

    }








    public static void main(String args[]) {

    }
}
