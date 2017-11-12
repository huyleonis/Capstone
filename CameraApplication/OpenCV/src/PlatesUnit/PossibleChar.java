/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PlatesUnit;

import java.util.List;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;

/**
 *
 * @author hp
 */
public class PossibleChar implements Comparable<PossibleChar> {

    //std::vector<cv::Point> contour;
    private MatOfPoint contour;

    //cv::Rect boundingRect;
    private Rect boundingRect;

    private int centerX;
    private int centerY;

    private double diagonalSize;
    private double aspectRatio;

    public PossibleChar() {
    }

    public PossibleChar(MatOfPoint contour) {
        this.contour = contour;        
        this.boundingRect = Imgproc.boundingRect(contour);

        centerX = (boundingRect.x + boundingRect.x + boundingRect.width) / 2;
        centerY = (boundingRect.y + boundingRect.y + boundingRect.height) / 2;

        diagonalSize = Math.sqrt(Math.pow(boundingRect.width, 2) + Math.pow(boundingRect.height, 2));

        aspectRatio = (double) boundingRect.width / (double) boundingRect.height;
    }
    
    public PossibleChar(PossibleChar pc) {
        this.contour = pc.getContour();
        this.boundingRect = pc.getBoundingRect().clone();
        this.centerX = pc.getIntCenterX();
        this.centerY = pc.getIntCenterY();
        this.aspectRatio = pc.getDblAspectRatio();
        this.diagonalSize = pc.getDblDiagonalSize();
                
    }

    public MatOfPoint getContour() {
        return contour;
    }

    public void setContour(MatOfPoint contour) {
        this.contour = contour;
    }

    public Rect getBoundingRect() {
        return boundingRect;
    }

    public void setBoundingRect(Rect boundingRect) {
        this.boundingRect = boundingRect;
    }

    public int getIntCenterX() {
        return centerX;
    }

    public void setIntCenterX(int centerX) {
        this.centerX = centerX;
    }

    public int getIntCenterY() {
        return centerY;
    }

    public void setIntCenterY(int centerY) {
        this.centerY = centerY;
    }

    public double getDblDiagonalSize() {
        return diagonalSize;
    }

    public void setDblDiagonalSize(double diagonalSize) {
        this.diagonalSize = diagonalSize;
    }

    public double getDblAspectRatio() {
        return aspectRatio;
    }

    public void setDblAspectRatio(double aspectRatio) {
        this.aspectRatio = aspectRatio;
    }

    public static boolean sortCharsLeftToRight(PossibleChar pcLeft, PossibleChar pcRight) {
        //return(pcLeft.centerX < pcRight.centerX);
        return pcLeft.getIntCenterX() < pcRight.getIntCenterX();
    }

    @Override
    public int compareTo(PossibleChar o) {
        return this.getIntCenterX() - o.getIntCenterX();
    }
}
