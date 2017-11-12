/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ImageProcess;

import PlatesUnit.PossibleChar;
import PlatesUnit.PossiblePlate;
import Util.FileUtils;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.ml.KNearest;
import org.opencv.ml.Ml;

/**
 *
 * @author hp
 */
public class CharsFinder {

    // constants for checkIfPossibleChar, this checks one possible char only (does not compare to another char)
    private static final int MIN_PIXEL_WIDTH = 2;
    private static final int MIN_PIXEL_HEIGHT = 8;

    private static final double MIN_ASPECT_RATIO = 0.25;
    private static final double MAX_ASPECT_RATIO = 1.0;

    private static final int MIN_PIXEL_AREA = 80;

    // constants for comparing two chars
    private static final double MIN_DIAG_SIZE_MULTIPLE_AWAY = 0.3;
    private static final double MAX_DIAG_SIZE_MULTIPLE_AWAY = 5.0;

    private static final double MAX_CHANGE_IN_AREA = 0.5;

    private static final double MAX_CHANGE_IN_WIDTH = 0.8;
    private static final double MAX_CHANGE_IN_HEIGHT = 0.2;

    private static final double MAX_ANGLE_BETWEEN_CHARS = 12.0;

    // other constants
    private static final int MIN_NUMBER_OF_MATCHING_CHARS = 3;

    private static final int RESIZED_CHAR_IMAGE_WIDTH = 20;
    private static final int RESIZED_CHAR_IMAGE_HEIGHT = 30;

    private static final int MIN_CONTOUR_AREA = 100;

    //internal global variables     
    private static final KNearest kNearest = KNearest.create(); //extern cv::Ptr<cv::ml::KNearest>  kNearest;

    public static boolean loadKNNDataAndTrainKNN() throws Exception {
        // 1.read in training classifications 

        // we will read the classification numbers into this variable as though it is a vector
        Mat matClassificationInts = new Mat();

        // open the classifications file                
        File fsClassifications = new File("./src/Resources/classifications.xml");

        if (!(fsClassifications.exists() && fsClassifications.canRead())) {
            throw new Exception("error, unable to open training classifications file, exiting program"
                    + " [exist:" + fsClassifications.exists() + "] - "
                            + "[readable: " + fsClassifications.canRead() + "]" );
        }

        // read classifications section into Mat classifications variable              
        matClassificationInts = FileUtils.parseXmlFile(fsClassifications, "classifications");

        // 2.read in training images 
        // we will read multiple images into this single image variable as though it is a vector
        Mat matTrainingImagesAsFlattenedFloats = new Mat();

        // open the training images file
        File fsTrainingImages = new File("./src/Resources/images.xml");

        if (!(fsTrainingImages.exists() && fsTrainingImages.canRead())) {
            throw new Exception("error, unable to open training images file, exiting program");
        }

        // read images section into Mat training images variable
        matTrainingImagesAsFlattenedFloats = FileUtils.parseXmlFile(fsTrainingImages, "images");

        // 3.train
        // finally we get to the call to train, note that both parameters have to be of type Mat (a single Mat)
        // even though in reality they are multiple images / numbers
        kNearest.setDefaultK(1);

        kNearest.train(matTrainingImagesAsFlattenedFloats, Ml.ROW_SAMPLE, matClassificationInts);

        return true;
    }

    public static List<PossiblePlate> detectCharsInPlates(List<PossiblePlate> vectorOfPossiblePlates) {
        List<MatOfPoint> contours = new ArrayList<>();
        
        if (vectorOfPossiblePlates.isEmpty()) {
            return vectorOfPossiblePlates;
        }
        
        // at this point we can be sure vector of possible plates has at least one plate
        
        // for each possible plate, this is a big for loop that takes up most of the function
        for (PossiblePlate possiblePlate : vectorOfPossiblePlates) {
            
            // preprocess to get grayscale and threshold images
            Preprocesser.preprocess(possiblePlate.getImgPlate(), 
                    possiblePlate.getImgGrayscale(), possiblePlate.getImgThresh());

            // upscale size by 60% for better viewing and character recognition
            Imgproc.resize(possiblePlate.getImgThresh(), possiblePlate.getImgThresh(), new Size(), 1.6, 1.6, Imgproc.INTER_LINEAR);

            // threshold again to eliminate any gray areas
            Imgproc.threshold(possiblePlate.getImgThresh(), possiblePlate.getImgThresh(), 
                    0.0, 255.0, Imgproc.THRESH_OTSU);


            // find all possible chars in the plate,
            // this function first finds all contours, then only includes contours that could be chars (without comparison to other chars yet)
            List<PossibleChar> vectorOfPossibleCharsInPlate = 
                    findPossibleCharsInPlate(possiblePlate.getImgGrayscale(), possiblePlate.getImgThresh());


            // given a vector of all possible chars, find groups of matching chars within the plate
            List<List<PossibleChar>> vectorOfVectorsOfMatchingCharsInPlate = 
                    findVectorOfVectorsOfMatchingChars(vectorOfPossibleCharsInPlate);


            if (vectorOfVectorsOfMatchingCharsInPlate.isEmpty()) {                
                // if no groups of matching chars were found in the plate
                // set plate string member variable to empty string
                // go back to top of for loop
                possiblePlate.setStrChars("");            
                continue;                               
            }

            
            // for each vector of matching chars in the current plate
            for (List<PossibleChar> vectorOfMatchingChars : vectorOfVectorsOfMatchingCharsInPlate) {
                // sort the chars left to right
                Collections.sort(vectorOfMatchingChars);      
                vectorOfMatchingChars = removeInnerOverlappingChars(vectorOfMatchingChars);                                     // and eliminate any overlapping chars
            }

            // within each possible plate, suppose the longest vector of potential matching chars is the actual vector of chars
            int intLenOfLongestVectorOfChars = 0;
            int intIndexOfLongestVectorOfChars = 0;
            
            // loop through all the vectors of matching chars, get the index of the one with the most chars
            for (int i = 0; i < vectorOfVectorsOfMatchingCharsInPlate.size(); i++) {
                if (vectorOfVectorsOfMatchingCharsInPlate.get(i).size() > intLenOfLongestVectorOfChars) {
                    intLenOfLongestVectorOfChars = vectorOfVectorsOfMatchingCharsInPlate.get(i).size();
                    intIndexOfLongestVectorOfChars = i;
                }
            }
            
            // suppose that the longest vector of matching chars within the plate is the actual vector of chars
            List<PossibleChar> longestVectorOfMatchingCharsInPlate = 
                    vectorOfVectorsOfMatchingCharsInPlate.get(intIndexOfLongestVectorOfChars);


            // perform char recognition on the longest vector of matching chars in the plate
            possiblePlate.setStrChars(recognizeCharsInPlate(possiblePlate.getImgThresh(), longestVectorOfMatchingCharsInPlate));
        } // end for each possible plate big for loop that takes up most of the function


        return vectorOfPossiblePlates;
    }

    private static List<PossibleChar> findPossibleCharsInPlate(Mat imgGrayscale,
            Mat imgThresh) {

        // this will be the return value
        List<PossibleChar> vectorOfPossibleChars = new ArrayList<>();

        // make a copy of the thresh image, this in necessary b/c findContours modifies the image
        Mat imgThreshCopy = imgThresh.clone();

        List<MatOfPoint> contours = new ArrayList<>();

        // find all contours in plate
        Imgproc.findContours(imgThreshCopy, contours, new Mat(), Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);

        for (MatOfPoint contour : contours) {
            PossibleChar possibleChar = new PossibleChar(contour);

            // if contour is a possible char, note this does not compare to other chars (yet) . . .
            if (checkIfPossibleChar(possibleChar)) {                
                // add to vector of possible chars
                vectorOfPossibleChars.add(possibleChar);      
            }
        }

        return vectorOfPossibleChars;        
    }

    public static boolean checkIfPossibleChar(PossibleChar possibleChar) {
        // this function is a 'first pass' that does a rough check on a contour to see if it could be a char,
        // note that we are not (yet) comparing the char to other chars to look for a group
        if (possibleChar.getBoundingRect().area() > MIN_PIXEL_AREA
                && possibleChar.getBoundingRect().width > MIN_PIXEL_WIDTH
                && possibleChar.getBoundingRect().height > MIN_PIXEL_HEIGHT
                && MIN_ASPECT_RATIO < possibleChar.getDblAspectRatio() && possibleChar.getDblAspectRatio() < MAX_ASPECT_RATIO) {
            return true;
        } else {
            return false;
        }
    }

    public static List<List<PossibleChar>> findVectorOfVectorsOfMatchingChars(
            final List<PossibleChar> vectorOfPossibleChars) {
        // with this function, we start off with all the possible chars in one big vector
        // the purpose of this function is to re-arrange the one big vector of chars into a vector of vectors of matching chars,
        // note that chars that are not found to be in a group of matches do not need to be considered further

        // this will be the return value
        List<List<PossibleChar>> vectorOfVectorsOfMatchingChars = new ArrayList<>();

        for (PossibleChar possibleChar : vectorOfPossibleChars) { // for each possible char in the one big vector of chars

            // find all chars in the big vector that match the current char
            List<PossibleChar> vectorOfMatchingChars
                    = findVectorOfMatchingChars(possibleChar, vectorOfPossibleChars);

            // also add the current char to current possible vector of matching chars
            vectorOfMatchingChars.add(possibleChar);

            // if current possible vector of matching chars is not long enough to constitute a possible plate
            if (vectorOfMatchingChars.size() < MIN_NUMBER_OF_MATCHING_CHARS) {
                // jump back to the top of the for loop and try again with next char, note that it's not necessary
                // to save the vector in any way since it did not have enough chars to be a possible plate
                continue;
            }

            // if we get here, the current vector passed test as a "group" or "cluster" of matching chars
            // so add to our vector of vectors of matching chars
            vectorOfVectorsOfMatchingChars.add(vectorOfMatchingChars);

            // remove the current vector of matching chars from the big vector so we don't use those same chars twice,
            // make sure to make a new big vector for this since we don't want to change the original big vector
            List<PossibleChar> vectorOfPossibleCharsWithCurrentMatchesRemoved = new ArrayList<>();

            for (PossibleChar possChar : vectorOfPossibleChars) {
                if (!vectorOfMatchingChars.contains(possChar)) {
                    vectorOfPossibleCharsWithCurrentMatchesRemoved.add(possChar);
                }
            }

            // declare new vector of vectors of chars to get result from recursive call
            List<List<PossibleChar>> recursiveVectorOfVectorsOfMatchingChars;

            // recursive call
            recursiveVectorOfVectorsOfMatchingChars = findVectorOfVectorsOfMatchingChars(vectorOfPossibleCharsWithCurrentMatchesRemoved);	// recursive call !!

            for (List<PossibleChar> recursiveVectorOfMatchingChars : recursiveVectorOfVectorsOfMatchingChars) {      // for each vector of matching chars found by recursive call
                vectorOfVectorsOfMatchingChars.add(recursiveVectorOfMatchingChars);               // add to our original vector of vectors of matching chars
            }
            break;		// exit for loop
        }

        return (vectorOfVectorsOfMatchingChars);
    }

    private static List<PossibleChar> findVectorOfMatchingChars(final PossibleChar possibleChar,
            final List<PossibleChar> vectorOfChars) {
        // the purpose of this function is, given a possible char and a big vector of possible chars,
        // find all chars in the big vector that are a match for the single possible char, and return those matching chars as a vector

        // this will be the return value
        List<PossibleChar> vectorOfMatchingChars = new ArrayList<PossibleChar>();

        // for each char in big vector
        for (PossibleChar possibleMatchingChar : vectorOfChars) {

            // if the char we attempting to find matches for is 
            // the exact same char as the char in the big vector we are currently checking
            if (possibleMatchingChar == possibleChar) {
                // then we should not include it in the vector of matches b/c that would end up double including the current char
                // so do not add to vector of matches and jump back to top of for loop
                continue;
            }

            // compute stuff to see if chars are a match
            double dblDistanceBetweenChars = distanceBetweenChars(possibleChar, possibleMatchingChar);
            double dblAngleBetweenChars = angleBetweenChars(possibleChar, possibleMatchingChar);
            double dblChangeInArea = (double) Math.abs(possibleMatchingChar.getBoundingRect().area() - possibleChar.getBoundingRect().area()) / (double) possibleChar.getBoundingRect().area();
            double dblChangeInWidth = (double) Math.abs(possibleMatchingChar.getBoundingRect().width - possibleChar.getBoundingRect().width) / (double) possibleChar.getBoundingRect().width;
            double dblChangeInHeight = (double) Math.abs(possibleMatchingChar.getBoundingRect().height - possibleChar.getBoundingRect().height) / (double) possibleChar.getBoundingRect().height;

            // check if chars match
            if (dblDistanceBetweenChars < (possibleChar.getDblDiagonalSize() * MAX_DIAG_SIZE_MULTIPLE_AWAY)
                    && dblAngleBetweenChars < MAX_ANGLE_BETWEEN_CHARS
                    && dblChangeInArea < MAX_CHANGE_IN_AREA
                    && dblChangeInWidth < MAX_CHANGE_IN_WIDTH
                    && dblChangeInHeight < MAX_CHANGE_IN_HEIGHT) {
                vectorOfMatchingChars.add(possibleMatchingChar);      // if the chars are a match, add the current char to vector of matching chars
            }
        }

        return (vectorOfMatchingChars);          // return result
    }

    public static double distanceBetweenChars(final PossibleChar firstChar,
            final PossibleChar secondChar) {
        // use Pythagorean theorem to calculate distance between two chars
        int intX = Math.abs(firstChar.getIntCenterX() - secondChar.getIntCenterX());
        int intY = Math.abs(firstChar.getIntCenterY() - secondChar.getIntCenterY());

        return Math.sqrt(Math.pow(intX, 2) + Math.pow(intY, 2));
    }

    private static double angleBetweenChars(final PossibleChar firstChar,
            final PossibleChar secondChar) {
        // use basic trigonometry(SOH CAH TOA) to calculate angle between chars
        double dblAdj = Math.abs(firstChar.getIntCenterX() - secondChar.getIntCenterX());
        double dblOpp = Math.abs(firstChar.getIntCenterY() - secondChar.getIntCenterY());

        double dblAngleInRad = Math.atan(dblOpp / dblAdj);

        double dblAngleInDeg = dblAngleInRad * (180.0 / Math.PI);

        return (dblAngleInDeg);

    }

    private static List<PossibleChar> removeInnerOverlappingChars(List<PossibleChar> vectorOfMatchingChars) {
        // if we have two chars overlapping or to close to each other to possibly be separate chars, remove the inner (smaller) char,
        // this is to prevent including the same char twice if two contours are found for the same char,
        // for example for the letter 'O' both the inner ring and the outer ring may be found as contours, but we should only include the char once

        List<PossibleChar> vectorOfMatchingCharsWithInnerCharRemoved = new ArrayList<>();
        //Clone new vectorOfMatchingChars, this will be the return value
        for (PossibleChar pc : vectorOfMatchingChars) {
            PossibleChar possibleChar = new PossibleChar(pc);
            vectorOfMatchingCharsWithInnerCharRemoved.add(possibleChar);
        }

        for (PossibleChar currentChar : vectorOfMatchingChars) {
            for (PossibleChar otherChar : vectorOfMatchingChars) {
                // if current char and other char are not the same char...
                if (currentChar != otherChar) {                         
                    
                    // if current char and other char have center points at almost the same location . . .
                    if (distanceBetweenChars(currentChar, otherChar) < (currentChar.getDblDiagonalSize() * MIN_DIAG_SIZE_MULTIPLE_AWAY)) {
                        // if we get in here we have found overlapping chars
                        // next we identify which char is smaller, then if that char was not already removed on a previous pass, remove it

                        // if current char is smaller than other char
                        if (currentChar.getBoundingRect().area() < otherChar.getBoundingRect().area()) {
                            
                            // look for char in vector with an iterator
                            int currentCharIterator = vectorOfMatchingCharsWithInnerCharRemoved.indexOf(currentChar);
                                                        
                            // if iterator did not get to end, then the char was found in the vector
                            if (currentCharIterator >= 0) {
                                // so remove the char
                                vectorOfMatchingCharsWithInnerCharRemoved.remove(currentCharIterator);       
                            }
                        } else {        // else if other char is smaller than current char
                            // look for char in vector with an iterator
                            int otherCharIterator = vectorOfMatchingCharsWithInnerCharRemoved.indexOf(otherChar);
                            // if iterator did not get to end, then the char was found in the vector
                            if (otherCharIterator >= 0) {
                                vectorOfMatchingCharsWithInnerCharRemoved.remove(otherCharIterator);         // so remove the char
                            }
                        }
                    }
                }
            }
        }

        return vectorOfMatchingCharsWithInnerCharRemoved;
    }

    private static String recognizeCharsInPlate(Mat imgThresh, List<PossibleChar>  vectorOfMatchingChars) {
        StringBuilder strChars = new StringBuilder("");

        Mat imgThreshColor = new Mat();

        // sort chars from left to right
        Collections.sort(vectorOfMatchingChars);

        // make color version of threshold image so we can draw contours in color on it
        Imgproc.cvtColor(imgThresh, imgThreshColor, Imgproc.COLOR_GRAY2BGR);

        for (PossibleChar currentChar : vectorOfMatchingChars) {
            // draw green box around the char
            Point p1 = currentChar.getBoundingRect().tl();
            Point p2 = currentChar.getBoundingRect().br();
            Imgproc.rectangle(imgThreshColor, p1, p2, Recognition.SCALAR_GREEN, 2);

            // get ROI image of bounding rect
            Mat imgROItoBeCloned = new Mat(imgThresh, currentChar.getBoundingRect());                 

            // clone ROI image so we don't change original when we resize
            Mat imgROI = imgROItoBeCloned.clone();

            Mat imgROIResized = new Mat();
            // resize image, this is necessary for char recognition
            Imgproc.resize(imgROI, imgROIResized, new Size(RESIZED_CHAR_IMAGE_WIDTH, RESIZED_CHAR_IMAGE_HEIGHT));

            Mat matROIFloat = new Mat();

            // convert Mat to float, necessary for call to findNearest
            imgROIResized.convertTo(matROIFloat, CvType.CV_32FC1);         

            // flatten Matrix into one row
            Mat matROIFlattenedFloat = matROIFloat.reshape(1, 1);       

            // declare Mat to read current char into, this is necessary b/c findNearest requires a Mat
            Mat matCurrentChar = new Mat(0, 0, CvType.CV_32F);                   

            // finally we can call find_nearest !!!
            kNearest.findNearest(matROIFlattenedFloat, 1, matCurrentChar);     

            // convert current char from Mat to float C++: matCurrentChar.at<float>(0, 0)
            double dblCurrentChar = (double) matCurrentChar.get(0, 0)[0];       
            

            // append current char to full string
            strChars.append(Character.toChars((int)dblCurrentChar));        
        }


        return strChars.toString();           
        
    }
    
}
