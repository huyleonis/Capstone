/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PlatesUnit;

import org.opencv.core.Mat;
import org.opencv.core.RotatedRect;

/**
 *
 * @author tinpb
 */
public class PossiblePlate implements Comparable<PossiblePlate> {
    private Mat imgPlate;
    private Mat imgGrayscale;
    private Mat imgThresh;
    
    private RotatedRect rrLocationOfPlateInScene;
    private String strChars;

    public PossiblePlate() {
    }

    public PossiblePlate(Mat imgPlate, Mat imgGrayscale, Mat imgThresh, RotatedRect rrLocationOfPlateInScene) {
        this.imgPlate = imgPlate;
        this.imgGrayscale = imgGrayscale;
        this.imgThresh = imgThresh;
        this.rrLocationOfPlateInScene = rrLocationOfPlateInScene;
    }

    public Mat getImgPlate() {
        return imgPlate;
    }

    public void setImgPlate(Mat imgPlate) {
        this.imgPlate = imgPlate;
    }

    public Mat getImgGrayscale() {
        return imgGrayscale;
    }

    public void setImgGrayscale(Mat imgGrayscale) {
        this.imgGrayscale = imgGrayscale;
    }

    public Mat getImgThresh() {
        return imgThresh;
    }

    public void setImgThresh(Mat imgThresh) {
        this.imgThresh = imgThresh;
    }

    public RotatedRect getRrLocationOfPlateInScene() {
        return rrLocationOfPlateInScene;
    }

    public void setRrLocationOfPlateInScene(RotatedRect rrLocationOfPlateInScene) {
        this.rrLocationOfPlateInScene = rrLocationOfPlateInScene;
    }

    public String getStrChars() {
        return strChars;
    }

    public void setStrChars(String strChars) {
        this.strChars = strChars;
    }

    @Override
    public int compareTo(PossiblePlate o) {
        return this.getStrChars().length() - o.getStrChars().length();
    }
}
