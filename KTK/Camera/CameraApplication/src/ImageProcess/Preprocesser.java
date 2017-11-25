/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ImageProcess;

import java.util.ArrayList;
import java.util.List;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

/**
 *
 * @author tinpb
 */
public class Preprocesser {
    
    private static final Size GAUSSIAN_SMOOTH_FILTER_SIZE = new Size(5, 5);
    private static final int ADAPTIVE_THRESH_BLOCK_SIZE = 19;
    private static final int ADAPTIVE_THRESH_WEIGHT = 9;
    
    public static void preprocess(Mat imgOriginal, Mat imgGrayscale, Mat imgThresh) {
        imgGrayscale = extractValue(imgOriginal);                           // extract value channel only from original image to get imgGrayscale

        Mat imgMaxContrastGrayscale = maximizeContrast(imgGrayscale);       // maximize contrast with top hat and black hat

        Mat imgBlurred = new Mat();

        // gaussian blur
        //cv::GaussianBlur(imgMaxContrastGrayscale, imgBlurred, GAUSSIAN_SMOOTH_FILTER_SIZE, 0);          
        Imgproc.GaussianBlur(imgMaxContrastGrayscale, imgBlurred, GAUSSIAN_SMOOTH_FILTER_SIZE, 0);
        
        // call adaptive threshold to get imgThresh
        //cv::adaptiveThreshold(imgBlurred, imgThresh, 255.0, CV_ADAPTIVE_THRESH_GAUSSIAN_C, CV_THRESH_BINARY_INV, ADAPTIVE_THRESH_BLOCK_SIZE, ADAPTIVE_THRESH_WEIGHT);
        Imgproc.adaptiveThreshold(imgBlurred, imgThresh, 255.0, Imgproc.ADAPTIVE_THRESH_GAUSSIAN_C, Imgproc.THRESH_BINARY_INV, ADAPTIVE_THRESH_BLOCK_SIZE, ADAPTIVE_THRESH_WEIGHT);        
    }
    
    private static Mat extractValue(Mat imgOriginal) {
        Mat imgHSV = new Mat();
        List<Mat> vectorOfHSVImages = new ArrayList<>();        

        Imgproc.cvtColor(imgOriginal, imgHSV, Imgproc.COLOR_BGR2HSV);

        Core.split(imgHSV, vectorOfHSVImages);

        Mat imgValue = vectorOfHSVImages.get(2);

        return imgValue;
    }
    
    private static Mat maximizeContrast(Mat imgGrayscale) {
        Mat imgTopHat = new Mat();
        Mat imgBlackHat = new Mat();
        Mat imgGrayscalePlusTopHat = new Mat();
        Mat imgGrayscalePlusTopHatMinusBlackHat = new Mat();

        Mat structuringElement = Imgproc.getStructuringElement(Imgproc.CV_SHAPE_RECT, new Size(3, 3));

        Imgproc.morphologyEx(imgGrayscale, imgTopHat, Imgproc.MORPH_TOPHAT, structuringElement);
        Imgproc.morphologyEx(imgGrayscale, imgBlackHat, Imgproc.MORPH_BLACKHAT, structuringElement);

        //imgGrayscalePlusTopHat = imgGrayscale + imgTopHat;
        Core.add(imgGrayscale, imgTopHat, imgGrayscalePlusTopHat);
        //imgGrayscalePlusTopHatMinusBlackHat = imgGrayscalePlusTopHat - imgBlackHat;
        Core.subtract(imgGrayscalePlusTopHat, imgBlackHat, imgGrayscalePlusTopHatMinusBlackHat);

        return imgGrayscalePlusTopHatMinusBlackHat;
    }
}
