/**
 * Homework 4 William Sayre , wjs9ej Sources : Big Java Book, Stack Overflow (Cited where used) */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.Collections;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;




public class PhotoViewer extends JFrame{
    private PhotographContainer imageLibrary; //Stores Photographs in viewer
    JLabel testLibLabel; //Label displaying which photograph is currently showing
    JButton exitButton; //Button to exit viewer
    JButton previousButton; //Displays last photo
    JButton nextButton; //Displays next photo
    JButton sortDateButton; //Sorts photos by date
    JButton sortCaptionButton; //Sorts photos alphabetically by caption
    JButton sortRatingButton; //thumbnailOne label 
    JPanel panelOne; //Left panel which holds thumbnails 
    JPanel panelTwo; //Top panel which holds navigation buttons
    JPanel panelThree; //Bottom panel which holds rating buttons
    JPanel panelFour; //Right panel which displays current picture 
    ImageIcon thumbOneImage; //thumbnailOne image
    ImageIcon thumbTwoImage; //thumbnailTwo image
    ImageIcon thumbThreeImage; //thumbnailThree image
    ImageIcon thumbFourImage; //thumbnailFour image
    ImageIcon thumbFiveImage; //thumbnailFive image
    ImageIcon currentPhotoImage; //current image
    JLabel thumbOneLabel; //thumbnailOne label
    JLabel thumbTwoLabel; //thumbnailTwo label
    JLabel thumbThreeLabel; //thumbnailThree label
    JLabel thumbFourLabel; //thumbnailFour label
    JLabel thumbFiveLabel; //thumbnailFive label
    JLabel mainLabel; //label holding current image
    String thumbOneCaption; //thumbnailOne caption
    String thumbTwoCaption;  //thumbTwo Caption
    String thumbThreeCaption; //thumbThree Caption
    String thumbFourCaption; //thumbFour Caption
    String thumbFiveCaption;  //thumbFive Caption
    ButtonGroup radioGroup; //group of radio buttons
    JRadioButton ratingOneRadio; //Rates current photo 1
    JRadioButton ratingTwoRadio; //Rates current photo 2 
    JRadioButton ratingThreeRadio; //Rates current photo 3
    JRadioButton ratingFourRadio; //Rates current photo 4
    JRadioButton ratingFiveRadio; //Rates current photo 5
    Photograph currentPhoto; //Current photograph 
    int currentPhotoIndex = 0; //Index of current photograph in imageLibrary. Default is 0
    JLabel labelPressed; //True if label pressed, false if not
    private FlowLayout layout = new FlowLayout(); //Sets default layout of panels to FlowLayout 

    /**
     * Getter for ImageLibary
     * @return imageLibrary
     */

    public PhotographContainer getImageLibrary() {
        return this.imageLibrary;
    }

    /**
     * Setter for ImageLibary
     * @param newLibary; new ImageLibary
     */
    public void setImageLibrary(PhotographContainer newLibrary) {
        this.imageLibrary = newLibrary;
    }
    /**
     * Helper Method to update GUI afer action is performed
     * @param currentPhotoIndex passed in by ActionPerformed method which was called 
     */
    public void updateGUI(int currentPhotoIndex) {

        //MainPhoto
        currentPhoto = imageLibrary.getPhotos().get(currentPhotoIndex);
        currentPhotoImage = new ImageIcon(currentPhoto.getimageData());
        int cW = currentPhotoImage.getIconWidth() / 2;
        int cH = currentPhotoImage.getIconHeight() / 2;
        Image cS = currentPhotoImage.getImage().getScaledInstance(cW, cH, Image.SCALE_SMOOTH);
        currentPhotoImage = new ImageIcon(cS);
        mainLabel.setIcon(currentPhotoImage);

        //Thumbnails 

        //Thumbnail indices 
        int tOneI = 0;
        int tTwoI = 1;
        int tThreeI = 2;
        int tFourI = 3;
        int tFiveI = 4;



        //Generates thumbnail images and scales them to proper size 
        thumbOneImage = new ImageIcon(imageLibrary.getPhotos().get(tOneI).getimageData());
        int oneW = thumbOneImage.getIconWidth() / 15;
        int oneH = thumbOneImage.getIconHeight() / 15;
        Image oneS = thumbOneImage.getImage().getScaledInstance(oneW, oneH, Image.SCALE_SMOOTH);
        thumbOneImage = new ImageIcon(oneS);


        thumbTwoImage = new ImageIcon(imageLibrary.getPhotos().get(tTwoI).getimageData());
        int twoW = thumbTwoImage.getIconWidth() / 15;
        int twoH = thumbTwoImage.getIconHeight() / 15;
        Image twoS = thumbTwoImage.getImage().getScaledInstance(twoW, twoH, Image.SCALE_SMOOTH);
        thumbTwoImage = new ImageIcon(twoS);

        thumbThreeImage = new ImageIcon(imageLibrary.getPhotos().get(tThreeI).getimageData());
        int threeW = thumbThreeImage.getIconWidth() / 15;
        int threeH = thumbThreeImage.getIconHeight() / 15;
        Image threeS = thumbThreeImage.getImage().getScaledInstance(threeW, threeH, Image.SCALE_SMOOTH);
        thumbThreeImage = new ImageIcon(threeS);

        thumbFourImage = new ImageIcon(imageLibrary.getPhotos().get(tFourI).getimageData());
        int fourW = thumbFourImage.getIconWidth() / 15;
        int fourH = thumbFourImage.getIconHeight() / 15;
        Image fourS = thumbFourImage.getImage().getScaledInstance(fourW, fourH, Image.SCALE_SMOOTH);
        thumbFourImage = new ImageIcon(fourS);

        thumbFiveImage = new ImageIcon(imageLibrary.getPhotos().get(tFiveI).getimageData());
        int fiveW = thumbFiveImage.getIconWidth() / 15;
        int fiveH = thumbFiveImage.getIconHeight() / 15;
        Image fiveS = thumbFiveImage.getImage().getScaledInstance(fiveW, fiveH, Image.SCALE_SMOOTH);
        thumbFiveImage = new ImageIcon(fiveS);


        //Thumbnail captions 
        thumbOneCaption = imageLibrary.getPhotos().get(tOneI).getCaption() + "\n"  +"(" + imageLibrary.getPhotos().get(tOneI).getDateTaken() +")" + "\n" + "Rated:" + imageLibrary.getPhotos().get(tOneI).getRating();
        thumbTwoCaption = imageLibrary.getPhotos().get(tTwoI).getCaption() + "\n" +"(" + imageLibrary.getPhotos().get(tTwoI).getDateTaken() +")" + "\n" + "Rated:" + imageLibrary.getPhotos().get(tTwoI).getRating();
        thumbThreeCaption = imageLibrary.getPhotos().get(tThreeI).getCaption() + "\n" +"(" + imageLibrary.getPhotos().get(tThreeI).getDateTaken() +")" + "\n" + "Rated:" + imageLibrary.getPhotos().get(tThreeI).getRating();
        thumbFourCaption = imageLibrary.getPhotos().get(tFourI).getCaption() + "\n" +"(" + imageLibrary.getPhotos().get(tFourI).getDateTaken() +")" + "\n" + "Rated:" + imageLibrary.getPhotos().get(tFourI).getRating();
        thumbFiveCaption = imageLibrary.getPhotos().get(tFiveI).getCaption() + "\n" +"(" + imageLibrary.getPhotos().get(tFiveI).getDateTaken() +")" + "\n" + "Rated:" + imageLibrary.getPhotos().get(tFiveI).getRating();

        //updates thumbnails with proper caption 
        thumbOneLabel.setText(thumbOneCaption);
        thumbOneLabel.setIcon(thumbOneImage);

        thumbTwoLabel.setText(thumbTwoCaption);
        thumbTwoLabel.setIcon(thumbTwoImage);

        thumbThreeLabel.setText(thumbThreeCaption);
        thumbThreeLabel.setIcon(thumbThreeImage);

        thumbFourLabel.setText(thumbFourCaption);
        thumbFourLabel.setIcon(thumbFourImage);

        thumbFiveLabel.setText(thumbFiveCaption);
        thumbFiveLabel.setIcon(thumbFiveImage);

        testLibLabel.setText("Test Library Image " + (currentPhotoIndex + 1) + " of 5");




    }
    public void addComponentsToPane(Container pane) { 

        //Sets default displayed image to first image in library 
        currentPhoto = imageLibrary.getPhotos().get(currentPhotoIndex);
        currentPhotoImage = new ImageIcon(currentPhoto.getimageData());
        int cW = currentPhotoImage.getIconWidth() / 2;
        int cH = currentPhotoImage.getIconHeight() / 2;
        Image cS = currentPhotoImage.getImage().getScaledInstance(cW, cH, Image.SCALE_SMOOTH);
        currentPhotoImage = new ImageIcon(cS);


        //Panel 1 Thumbnails-  

        //constructs panelOne, sets layout to vertical box layout. Sets background to white
        panelOne = new JPanel();
        panelOne.setLayout( new BoxLayout(panelOne, BoxLayout.Y_AXIS) ); 
        panelOne.setBackground(Color.white);



        //Sets default Thumbnails to original order of library 

        int tOneI = 0;
        int tTwoI = 1;
        int tThreeI = 2;
        int tFourI = 3;
        int tFiveI = 4;


        thumbOneImage = new ImageIcon(imageLibrary.getPhotos().get(tOneI).getimageData());
        int oneW = thumbOneImage.getIconWidth() / 15;
        int oneH = thumbOneImage.getIconHeight() / 15;
        Image oneS = thumbOneImage.getImage().getScaledInstance(oneW, oneH, Image.SCALE_SMOOTH);
        thumbOneImage = new ImageIcon(oneS);


        thumbTwoImage = new ImageIcon(imageLibrary.getPhotos().get(tTwoI).getimageData());
        int twoW = thumbTwoImage.getIconWidth() / 15;
        int twoH = thumbTwoImage.getIconHeight() / 15;
        Image twoS = thumbTwoImage.getImage().getScaledInstance(twoW, twoH, Image.SCALE_SMOOTH);
        thumbTwoImage = new ImageIcon(twoS);

        thumbThreeImage = new ImageIcon(imageLibrary.getPhotos().get(tThreeI).getimageData());
        int threeW = thumbThreeImage.getIconWidth() / 15;
        int threeH = thumbThreeImage.getIconHeight() / 15;
        Image threeS = thumbThreeImage.getImage().getScaledInstance(threeW, threeH, Image.SCALE_SMOOTH);
        thumbThreeImage = new ImageIcon(threeS);

        thumbFourImage = new ImageIcon(imageLibrary.getPhotos().get(tFourI).getimageData());
        int fourW = thumbFourImage.getIconWidth() / 15;
        int fourH = thumbFourImage.getIconHeight() / 15;
        Image fourS = thumbFourImage.getImage().getScaledInstance(fourW, fourH, Image.SCALE_SMOOTH);
        thumbFourImage = new ImageIcon(fourS);

        thumbFiveImage = new ImageIcon(imageLibrary.getPhotos().get(tFiveI).getimageData());
        int fiveW = thumbFiveImage.getIconWidth() / 15;
        int fiveH = thumbFiveImage.getIconHeight() / 15;
        Image fiveS = thumbFiveImage.getImage().getScaledInstance(fiveW, fiveH, Image.SCALE_SMOOTH);
        thumbFiveImage = new ImageIcon(fiveS);



        thumbOneCaption = imageLibrary.getPhotos().get(tOneI).getCaption() + "\n"  +"(" + imageLibrary.getPhotos().get(tOneI).getDateTaken() +")" + "\n" + "Rated:" + imageLibrary.getPhotos().get(tOneI).getRating();
        thumbTwoCaption = imageLibrary.getPhotos().get(tTwoI).getCaption() + "\n" +"(" + imageLibrary.getPhotos().get(tTwoI).getDateTaken() +")" + "\n" + "Rated:" + imageLibrary.getPhotos().get(tTwoI).getRating();
        thumbThreeCaption = imageLibrary.getPhotos().get(tThreeI).getCaption() + "\n" +"(" + imageLibrary.getPhotos().get(tThreeI).getDateTaken() +")" + "\n" + "Rated:" + imageLibrary.getPhotos().get(tThreeI).getRating();
        thumbFourCaption = imageLibrary.getPhotos().get(tFourI).getCaption() + "\n" +"(" + imageLibrary.getPhotos().get(tFourI).getDateTaken() +")" + "\n" + "Rated:" + imageLibrary.getPhotos().get(tFourI).getRating();
        thumbFiveCaption = imageLibrary.getPhotos().get(tFiveI).getCaption() + "\n" +"(" + imageLibrary.getPhotos().get(tFiveI).getDateTaken() +")" + "\n" + "Rated:" + imageLibrary.getPhotos().get(tFiveI).getRating();

        thumbOneLabel = new JLabel(thumbOneCaption, thumbOneImage, JLabel.CENTER);
        thumbTwoLabel = new JLabel(thumbTwoCaption, thumbTwoImage, JLabel.CENTER);
        thumbThreeLabel = new JLabel(thumbThreeCaption, thumbThreeImage, JLabel.CENTER);
        thumbFourLabel = new JLabel(thumbFourCaption, thumbFourImage, JLabel.CENTER);
        thumbFiveLabel = new JLabel(thumbFiveCaption, thumbFiveImage, JLabel.CENTER);

        thumbOneLabel.setName("thumbOneLabel");
        thumbTwoLabel.setName("thumbTwoLabel");
        thumbThreeLabel.setName("thumbThreeLabel");
        thumbFourLabel.setName("thumbFourLabel");
        thumbFiveLabel.setName("thumbFiveLabel");



        //MouseListener which allows thumbnails to be interactive
        class MouseEvent implements MouseListener {
            boolean onButton = false; 

            @Override
            //onButton is set to true once mouse enters a thumbnail label
            public void mouseEntered(java.awt.event.MouseEvent e) {
                onButton = true;

            }

            @Override

            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (onButton ==true) {
                    JLabel labelPressed = (JLabel) e.getSource(); //determines which thumbnail label was pressed

                    //sets current photo index to index of photo in thumbnail which was pressed 

                    //Cite; Idea for this approach came from Stack Overflow 
                    switch(labelPressed.getName()) 
                    {
                        case "thumbOneLabel":
                            currentPhotoIndex = tOneI;
                            break;
                        case "thumbTwoLabel":
                            currentPhotoIndex = tTwoI;
                            break;
                        case "thumbThreeLabel":
                            currentPhotoIndex = tThreeI;
                            break;
                        case "thumbFourLabel":
                            currentPhotoIndex = tFourI;
                            break;
                        case "thumbFiveLabel":
                            currentPhotoIndex = tFiveI;
                            break;            


                    }
                    //Updates GUI based on new currentPhotoINdex
                    updateGUI(currentPhotoIndex);
                }


            }

            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {


            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent e) {


            }



            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                onButton = false; //onButton is false once mouse exits thumbnail 

            }

        }

        //adds mouse listeners to thumbnails 
        thumbOneLabel.addMouseListener(new MouseEvent());
        thumbTwoLabel.addMouseListener(new MouseEvent());
        thumbThreeLabel.addMouseListener(new MouseEvent());
        thumbFourLabel.addMouseListener(new MouseEvent());
        thumbFiveLabel.addMouseListener(new MouseEvent());





        //Adds thumbnails to panelOne
        panelOne.add(thumbOneLabel);
        panelOne.add(thumbTwoLabel);
        panelOne.add(thumbThreeLabel);
        panelOne.add(thumbFourLabel);
        panelOne.add(thumbFiveLabel);



        //Panel 2-Top Row

        //Button Listenerwhich adds functionality to exit buttons  
        class ButtonListener implements ActionListener{
            public void actionPerformed(ActionEvent e) {
                //Exit button exits system
                if (e.getActionCommand().equals("ExitClick")) { 
                    System.exit(0);


                }
                //previous button subtracts 1 from current index. If index is 0, sets it to end of library  
                if (e.getActionCommand().equals("PreviousClick")) {
                    if (currentPhotoIndex > 0) {
                        currentPhotoIndex -= 1;
                    }
                    else {
                        currentPhotoIndex = imageLibrary.numPhotographs() - 1 ;
                    }

                    updateGUI(currentPhotoIndex); 


                    // mainLabel = new JLabel("", currentPhotoImage, JLabel.CENTER);

                }
                //previous button adds 1 to current index. If already at end of library, sets it to 0 
                if (e.getActionCommand().equals("NextClick")) {
                    if (currentPhotoIndex < imageLibrary.numPhotographs() - 1 ) {
                        currentPhotoIndex += 1;

                    }
                    else {
                        currentPhotoIndex = 0;
                    }
                    updateGUI(currentPhotoIndex);
                }
                //sorts by Date. Resets index to first photo in newly sorted library 
                if (e.getActionCommand().equals("SBDClick")) {
                    Collections.sort(imageLibrary.getPhotos()); 
                    currentPhotoIndex = 0;
                    updateGUI(currentPhotoIndex);

                }
                //sorts by Caption. Resets index to first photo in newly sorted library
                if (e.getActionCommand().equals("SBCClick")) { 
                    Collections.sort(imageLibrary.getPhotos(), new CompareByCaption());
                    updateGUI(currentPhotoIndex);
                }
                //sorts by Rating. Resets index to first photo in newly sorted library
                if (e.getActionCommand().equals("SBRClick")) { 
                    Collections.sort(imageLibrary.getPhotos(), new CompareByRating());
                    updateGUI(currentPhotoIndex);
                }
                //sets rating to 1
                if (e.getActionCommand().equals("ratingOne")) { 
                    currentPhoto.setRating(1);
                    updateGUI(currentPhotoIndex);


                }
                //sets rating to 2
                if (e.getActionCommand().equals("ratingTwo")) { 
                    currentPhoto.setRating(2);
                    updateGUI(currentPhotoIndex);


                }
                //sets rating to 3
                if (e.getActionCommand().equals("ratingThree")) { 
                    currentPhoto.setRating(3);
                    updateGUI(currentPhotoIndex);


                }
                //sets rating to 4
                if (e.getActionCommand().equals("ratingFour")) { 
                    currentPhoto.setRating(4);
                    updateGUI(currentPhotoIndex);

                }
                //sets rating to 5
                if (e.getActionCommand().equals("ratingFive")) { 
                    currentPhoto.setRating(5);
                    updateGUI(currentPhotoIndex);


                }
            }

        }

        //constructs panelTwo, sets layout to default (flow) sets background to white , orients components from left to right

        panelTwo = new JPanel();

        panelTwo.setLayout( layout );
        panelTwo.setBackground(Color.white);
        layout.setAlignment(FlowLayout.CENTER);
        panelTwo.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);


        //adds named label & buttons to panelTwo 
        testLibLabel = new JLabel("Test Library Image " + (currentPhotoIndex + 1) + " of 5");
        exitButton = new JButton("Exit");
        previousButton = new JButton("Previous");
        nextButton = new JButton("Next");
        sortDateButton = new JButton("Sort By Date");
        sortCaptionButton = new JButton("Sort By Caption");
        sortRatingButton = new JButton("Sort By Rating");

        //sets unique action command for each button which tells ButtonListener which command to execute 
        exitButton.setActionCommand("ExitClick");
        previousButton.setActionCommand("PreviousClick");
        nextButton.setActionCommand("NextClick");
        sortDateButton.setActionCommand("SBDClick");
        sortCaptionButton.setActionCommand("SBCClick");
        sortRatingButton.setActionCommand("SBRClick");


        //Adds Button Listener to each button 
        exitButton.addActionListener(new ButtonListener());
        nextButton.addActionListener(new ButtonListener());
        previousButton.addActionListener(new ButtonListener());
        sortDateButton.addActionListener(new ButtonListener());
        sortCaptionButton.addActionListener(new ButtonListener());
        sortRatingButton.addActionListener(new ButtonListener());

        //Adds buttons and label to panelTwo
        panelTwo.add(testLibLabel);
        panelTwo.add(exitButton);
        panelTwo.add(previousButton);
        panelTwo.add(nextButton);
        panelTwo.add(sortDateButton);
        panelTwo.add(sortCaptionButton);
        panelTwo.add(sortRatingButton);





        //Panel 3 Radios

        //constructs panelThree, sets layout to default (flow) sets background to white , orients components from left to right
        panelThree = new JPanel();
        panelThree.setBackground(Color.white);
        panelThree.setLayout( layout ); 
        layout.setAlignment(FlowLayout.CENTER); 
        panelThree.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

        //Radios

        //Default value of each radioButton is false. Becomes true depending on rating of currentPhoto
        boolean radioOneB = false;
        boolean radioTwoB = false;
        boolean radioThreeB = false;
        boolean radioFourB = false;
        boolean radioFiveB = false;


        //Sets Radio button values based off rating of current photo
        if(currentPhoto.getRating() == 1) {
            radioOneB = true;
        }
        if(currentPhoto.getRating() == 2) {
            radioTwoB = true;
        }
        if(currentPhoto.getRating() == 3) {
            radioThreeB = true;
        }
        if(currentPhoto.getRating() == 4) {
            radioFourB = true;
        }
        if(currentPhoto.getRating() == 5) {
            radioFiveB = true;
        }

        //Constructs radio buttons with corresponding values. Sets action command which controls button listener
        ratingOneRadio = new JRadioButton("1", radioOneB); 
        ratingOneRadio.setActionCommand("ratingOne");
        ratingTwoRadio = new JRadioButton("2", radioTwoB);
        ratingTwoRadio.setActionCommand("ratingTwo");
        ratingThreeRadio = new JRadioButton("3", radioThreeB);
        ratingThreeRadio.setActionCommand("ratingThree");
        ratingFourRadio = new JRadioButton("4", radioFourB);
        ratingFourRadio.setActionCommand("ratingFour");
        ratingFiveRadio = new JRadioButton("5", radioFiveB);
        ratingFiveRadio.setActionCommand("ratingFive");

        //Gives each radio button a button listener 
        ratingOneRadio.addActionListener(new ButtonListener());
        ratingTwoRadio.addActionListener(new ButtonListener());
        ratingThreeRadio.addActionListener(new ButtonListener());
        ratingFourRadio.addActionListener(new ButtonListener());
        ratingFiveRadio.addActionListener(new ButtonListener());


        //Constructs radiogroup, adds each radio button to it
        radioGroup = new ButtonGroup();
        radioGroup.add(ratingOneRadio);
        radioGroup.add(ratingTwoRadio);
        radioGroup.add(ratingThreeRadio);
        radioGroup.add(ratingFourRadio);
        radioGroup.add(ratingFiveRadio);

        //Adds readio buttons to panelThree
        panelThree.add(ratingOneRadio);
        panelThree.add(ratingTwoRadio);
        panelThree.add(ratingThreeRadio);
        panelThree.add(ratingFourRadio);
        panelThree.add(ratingFiveRadio);


        //Panel 4 Main Picture

        //Constructs label which holds current photo. constructs panelFour, sets layout to default (flow) sets background to white , orients components from left to right
        mainLabel = new JLabel("", currentPhotoImage, JLabel.CENTER); 
        panelFour = new JPanel();
        panelFour.setBackground(Color.white);
        panelFour.setLayout(layout);
        layout.setAlignment(FlowLayout.CENTER);
        panelFour.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);   
        panelFour.add(mainLabel);





        //Adds panels to pane 
        pane.add(panelOne, BorderLayout.WEST);
        pane.add(panelTwo, BorderLayout.NORTH);
        pane.add(panelThree, BorderLayout.SOUTH);
        pane.add(panelFour, BorderLayout.EAST);




    }



    private void createAndShowGUI() {

        //Sets default sort of image library to be by Date 
        Collections.sort(this.getImageLibrary().getPhotos());

        //calls AddComponents to Pane, applies method to PhotoViewers content pane
        this.addComponentsToPane(this.getContentPane());
        this.pack();
        this.setVisible(true);


    }


    public static void main (String[] args) {

        PhotoViewer myViewer = new PhotoViewer();

        String imageDirectory = "images/";

        Photograph p1 = new Photograph("Van Gogh", imageDirectory + "img1.jpg", "2019-10-02", 5);
        Photograph p2 = new Photograph("Maldives", imageDirectory + "img2.jpg", "2019-11-03", 4);
        Photograph p3 = new Photograph("Romans", imageDirectory + "img3.jpg", "2019-11-01", 5);
        Photograph p4 = new Photograph("Space", imageDirectory + "img4.jpg", "2019-11-04", 5);
        Photograph p5 = new Photograph("Alps", imageDirectory + "img5.jpg", "2019-11-0", 4);

        myViewer.setImageLibrary( new PhotoLibrary("Test Library", 1));


        myViewer.getImageLibrary().addPhoto(p1);
        myViewer.getImageLibrary().addPhoto(p2);
        myViewer.getImageLibrary().addPhoto(p3);
        myViewer.getImageLibrary().addPhoto(p4);
        myViewer.getImageLibrary().addPhoto(p5);



        Collections.sort(myViewer.getImageLibrary().getPhotos());

        javax.swing.SwingUtilities.invokeLater(()->myViewer.createAndShowGUI());


    }
}
