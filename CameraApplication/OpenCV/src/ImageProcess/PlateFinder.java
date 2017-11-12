/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ImageProcess;

import PlatesUnit.PossibleChar;
import PlatesUnit.PossiblePlate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.RotatedRect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

/**
 *
 * @author hp
 */
public class PlateFinder {
    
    //this map for show image step by step
    public static Map<String, Mat> steps = new HashMap<String, Mat>();
    
    private static final double PLATE_WIDTH_PADDING_FACTOR = 1.3;
    private static final double PLATE_HEIGHT_PADDING_FACTOR = 1.5;        
    

    //List<PossiblePlate> detectPlatesInScene(cv::Mat &imgOriginalScene);
    public static List<PossiblePlate> detectPlatesInScene(Mat imgOriginalScene) {
        
        //debug step
        steps.put("0", imgOriginalScene);
        
        Mat imgGrayscaleScene = new Mat();
        Mat imgThreshScene = new Mat();
        
        Preprocesser.preprocess(imgOriginalScene, imgGrayscaleScene, imgThreshScene);
        
        //debug step
        steps.put("1a", imgGrayscaleScene);
        steps.put("1b", imgThreshScene);
        
        /* find all possible chars in the scene,
         this function first finds all contours, then only includes contours that could be chars (without comparison to other chars yet) */
        List<PossibleChar> vectorOfPossibleCharsInScene = findPossibleCharsInScene(imgThreshScene);
        
        //debug step
        Mat imgContours = new Mat(imgOriginalScene.size(), CvType.CV_8UC3, Recognition.SCALAR_BLACK);
        List<MatOfPoint> contours = new ArrayList<>();

        for (PossibleChar possibleChar : vectorOfPossibleCharsInScene) {
            contours.add(possibleChar.getContour());
        }
        Imgproc.drawContours(imgContours, contours, -1, Recognition.SCALAR_WHITE);
        steps.put("2", imgContours);
        //end debug
        
        /* given a vector of all possible chars, find groups of matching chars
         in the next steps each group of matching chars will attempt to be recognized as a plate */
        List<List<PossibleChar>> vectorOfVectorsOfMatchingCharsInScene = 
                CharsFinder.findVectorOfVectorsOfMatchingChars(vectorOfPossibleCharsInScene);
                        
        //debug step
        imgContours = new Mat(imgOriginalScene.size(), CvType.CV_8UC3, Recognition.SCALAR_BLACK);
        for (List<PossibleChar> vectorOfMatchingChars : vectorOfVectorsOfMatchingCharsInScene) {
            
            int intRandomBlue = (int)(Math.random()*256);
            int intRandomGreen = (int)(Math.random()*256);
            int intRandomRed = (int)(Math.random()*256);           

            contours.clear();
            for (PossibleChar matchingChar : vectorOfMatchingChars) {
                contours.add(matchingChar.getContour());
            }
            Imgproc.drawContours(imgContours, contours, -1, new Scalar((double)intRandomBlue, (double)intRandomGreen, (double)intRandomRed));
        }        
        steps.put("3", imgContours);
        //end debug
        
        List<PossiblePlate> vectorOfPossiblePlates = new ArrayList<>();
        for (List<PossibleChar> vectorOfMatchingChars : vectorOfVectorsOfMatchingCharsInScene) { // for each group of matching chars
            PossiblePlate possiblePlate = extractPlate(imgOriginalScene, vectorOfMatchingChars); // attempt to extract plate

            if (!possiblePlate.getImgPlate().empty()) {                                              // if plate was found
                vectorOfPossiblePlates.add(possiblePlate);                                        // add to vector of possible plates
            }
        }
        
        //debug step
        for (int i = 0; i < vectorOfPossiblePlates.size(); i++) {
            Point[] p2fRectPoints = new Point[4];

            vectorOfPossiblePlates.get(i).getRrLocationOfPlateInScene().points(p2fRectPoints);

            for (int j = 0; j < 4; j++) {
                //cv::line(imgContours, p2fRectPoints[j], p2fRectPoints[(j + 1) % 4], SCALAR_RED, 2);
                Imgproc.line(imgContours, p2fRectPoints[j], p2fRectPoints[(j + 1) % 4], Recognition.SCALAR_RED, 2);
            }
            steps.put("4a", imgContours);

            steps.put("4b", vectorOfPossiblePlates.get(i).getImgPlate());            
        }
        //end debug
        
        return vectorOfPossiblePlates;
    }

    //List<PossibleChar> findPossibleCharsInScene(cv::Mat &imgThresh);
    private static List<PossibleChar> findPossibleCharsInScene(Mat imgThresh) {
        List<PossibleChar> vectorOfPossibleChars = new ArrayList<>();            // this will be the return value

        //Mat imgContours = new Mat(imgThresh.size(), CvType.CV_8UC3, Recognition.SCALAR_BLACK);
        int intCountOfPossibleChars = 0;

        Mat imgThreshCopy = imgThresh.clone();

        List<MatOfPoint> contours = new ArrayList<>();

        // find all contours
        Imgproc.findContours(imgThreshCopy, contours, new Mat(), Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);

        for (int i = 0; i < contours.size(); i++) {                // for each contour
            PossibleChar possibleChar = new PossibleChar(contours.get(i));

            if (CharsFinder.checkIfPossibleChar(possibleChar)) {    // if contour is a possible char, note this does not compare to other chars (yet) . . .
                intCountOfPossibleChars++;                          // increment count of possible chars
                vectorOfPossibleChars.add(possibleChar);      // and add to vector of possible chars
            }
        }
        
        return vectorOfPossibleChars;
    }
    
    //PossiblePlate extractPlate(cv::Mat &imgOriginal, List<PossibleChar> &vectorOfMatchingChars);
    private static PossiblePlate extractPlate(Mat imgOriginal, List<PossibleChar> vectorOfMatchingChars) {
        PossiblePlate possiblePlate = new PossiblePlate();    // this will be the return value
        
        // sort chars from left to right based on x position
        Collections.sort(vectorOfMatchingChars, (PossibleChar o1, PossibleChar o2) -> o1.getIntCenterX() - o2.getIntCenterX());

        // calculate the center point of the plate
        double dblPlateCenterX = (double)(vectorOfMatchingChars.get(0).getIntCenterX() + vectorOfMatchingChars.get(vectorOfMatchingChars.size() - 1).getIntCenterX()) / 2.0;
        double dblPlateCenterY = (double)(vectorOfMatchingChars.get(0).getIntCenterY() + vectorOfMatchingChars.get(vectorOfMatchingChars.size() - 1).getIntCenterY()) / 2.0;
        Point p2dPlateCenter = new Point(dblPlateCenterX, dblPlateCenterY);

        // calculate plate width and height
        int intPlateWidth = (int)((vectorOfMatchingChars.get(vectorOfMatchingChars.size() - 1).getBoundingRect().x 
                + vectorOfMatchingChars.get(vectorOfMatchingChars.size() - 1).getBoundingRect().width 
                - vectorOfMatchingChars.get(0).getBoundingRect().x) * PLATE_WIDTH_PADDING_FACTOR);
        
        double intTotalOfCharHeights = 0;

        for (PossibleChar matchingChar : vectorOfMatchingChars) {
            intTotalOfCharHeights = intTotalOfCharHeights + matchingChar.getBoundingRect().height;
        }

        double dblAverageCharHeight = (double)intTotalOfCharHeights / vectorOfMatchingChars.size();

        int intPlateHeight = (int)(dblAverageCharHeight * PLATE_HEIGHT_PADDING_FACTOR);

        // calculate correction angle of plate region
        double dblOpposite = vectorOfMatchingChars.get(vectorOfMatchingChars.size() - 1).getIntCenterY() - vectorOfMatchingChars.get(0).getIntCenterY();
        double dblHypotenuse = CharsFinder.distanceBetweenChars(vectorOfMatchingChars.get(0), vectorOfMatchingChars.get(vectorOfMatchingChars.size() - 1));
        double dblCorrectionAngleInRad = Math.asin(dblOpposite / dblHypotenuse);
        double dblCorrectionAngleInDeg = dblCorrectionAngleInRad * (180.0 / Math.PI);

        // assign rotated rect member variable of possible plate
        possiblePlate.setRrLocationOfPlateInScene(new RotatedRect(p2dPlateCenter, new Size((float)intPlateWidth, (float)intPlateHeight), (float)dblCorrectionAngleInDeg));

        // final steps are to perform the actual rotation        
        // get the rotation matrix for our calculated correction angle
        Mat rotationMatrix = Imgproc.getRotationMatrix2D(p2dPlateCenter, dblCorrectionAngleInDeg, 1.0);
        Mat imgRotated = new Mat();
        Mat imgCropped = new Mat();

        // rotate the entire image
        Imgproc.warpAffine(imgOriginal, imgRotated, rotationMatrix, imgOriginal.size());            

        // crop out the actual plate portion of the rotated image
        Imgproc.getRectSubPix(imgRotated, possiblePlate.getRrLocationOfPlateInScene().size, possiblePlate.getRrLocationOfPlateInScene().center, imgCropped);

        // copy the cropped plate image into the applicable member variable of the possible plate
        possiblePlate.setImgPlate(imgCropped);
        
        possiblePlate.setImgThresh(new Mat(imgCropped.size(), CvType.CV_8UC1));

        return(possiblePlate);
                
    }
}
