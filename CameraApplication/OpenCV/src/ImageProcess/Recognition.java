/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ImageProcess;

import PlatesUnit.PossiblePlate;
import java.util.Collections;
import java.util.List;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 *
 * @author hp
 */
public class Recognition {

    public static final Scalar SCALAR_BLACK = new Scalar(0.0, 0.0, 0.0);
    public static final Scalar SCALAR_WHITE = new Scalar(255.0, 255.0, 255.0);
    public static final Scalar SCALAR_YELLOW = new Scalar(0.0, 255.0, 255.0);
    public static final Scalar SCALAR_GREEN = new Scalar(0.0, 255.0, 0.0);
    public static final Scalar SCALAR_RED = new Scalar(0.0, 0.0, 255.0);

    private boolean blnKNNTrainingSuccessful;

    public Recognition() {
        try {
            blnKNNTrainingSuccessful = CharsFinder.loadKNNDataAndTrainKNN();
        } catch (Exception e) {
            e.printStackTrace();
            blnKNNTrainingSuccessful = false;
        }

    }

    private void drawRedRectangleAroundPlate(Mat imgOriginalScene, PossiblePlate licPlate) {
        Point[] p2fRectPoints = new Point[4];

        // get 4 vertices of rotated rect
        licPlate.getRrLocationOfPlateInScene().points(p2fRectPoints);

        // draw 4 red lines
        for (int i = 0; i < 4; i++) {
            Imgproc.line(imgOriginalScene, p2fRectPoints[i], p2fRectPoints[(i + 1) % 4], SCALAR_RED, 2);
        }
    }

    public String process(Mat imgOriginalScene) {                
        
        if (!blnKNNTrainingSuccessful) {
            return "Cannot load KNN Training";
        }

        if (imgOriginalScene.empty()) {
            return "Error: Image is empty";
        }

        // detect plates
        List<PossiblePlate> vectorOfPossiblePlates = PlateFinder.detectPlatesInScene(imgOriginalScene);

        // detect characters in those plates and set data for them
        vectorOfPossiblePlates = CharsFinder.detectCharsInPlates(vectorOfPossiblePlates);

        // if no plates were found
        if (vectorOfPossiblePlates.isEmpty()) {             
            return "There is no plates found";
        } else {            
            // if we get in here vector of possible plates has at leat one plate
            // sort the vector of possible plates in DESCENDING order (most number of chars to least number of chars)
              Collections.sort(vectorOfPossiblePlates);

              // suppose the plate with the most recognized chars (the first plate in sorted by string length descending order) is the actual plate
              PossiblePlate licPlate = vectorOfPossiblePlates.get(0);


              if (licPlate.getStrChars().length() == 0) { // if no chars were found in the plate
                  return "There is no char in found plate";
              }

              // draw red rectangle around plate
              drawRedRectangleAroundPlate(imgOriginalScene, licPlate);                

              return licPlate.getStrChars();
        }                
    }

    public boolean isBlnKNNTrainingSuccessful() {
        return blnKNNTrainingSuccessful;
    }
    /*    
    private static List<Mat> result;
    private static Mat S2 = Imgproc.getStructuringElement(Imgproc.CV_SHAPE_RECT, new Size(6, 1), new Point(3, 0));
    private static Mat S1 = Imgproc.getStructuringElement(Imgproc.CV_SHAPE_RECT, new Size(3, 1), new Point(1, 0));
    public static List<Mat> processImage(Mat img) throws IOException {
        result = new ArrayList<>();

        //resize image
        Mat resizeImg = new Mat();
        Imgproc.resize(img, resizeImg, new Size(600, 800));

        Mat plate = findPlate(resizeImg);

        result.add(plate);
        return result;
    }

    private static Mat findPlate(Mat src) {
        Mat plate = null;

        Mat contourImg = new Mat(src.size(), CvType.CV_8UC1); //contour image  
        Mat grayImg = new Mat(src.size(), CvType.CV_8UC1); //gray image  
        Imgproc.cvtColor(src, grayImg, Imgproc.COLOR_RGB2GRAY);

        result.add(grayImg);

        Mat cloneImg = new Mat(src.size(), CvType.CV_8UC3);
        cloneImg = src.clone();

        // tiền xử lý ảnh
        grayImg.copyTo(contourImg);
        Core.normalize(contourImg, contourImg, 0, 255, Core.NORM_MINMAX);

        contourImg = ImageRestoration(contourImg);

        Mat rectImg = new Mat(src.size(), CvType.CV_8UC3);
        List<Mat> list = new ArrayList<>();
        list.add(contourImg);
        list.add(contourImg);
        list.add(contourImg);
        Core.merge(list, rectImg);

        return rectImg;
    }

    private static Mat ImageRestoration(Mat src) {
        double w = src.width();
        double h = src.height();

        Mat mImg = new Mat(new Size(w / 2, h / 2), CvType.CV_8UC1); // Anh su dung cho bien doi hinh thai hoc
        Mat srcPyrdown = new Mat(new Size(w / 2, h / 2), CvType.CV_8UC1);
        Mat thresholed = new Mat(new Size(w / 2, h / 2), CvType.CV_8UC1); // Anh nhi phan voi nguong
        Mat miniThresh = new Mat(new Size(w / 2, h / 2), CvType.CV_8UC1);
        Mat dst = Mat.zeros(new Size(w / 2, h / 2), CvType.CV_8UC1); // Anh lam ro vung bien so

        Imgproc.pyrDown(src, srcPyrdown);
        Imgproc.morphologyEx(srcPyrdown, mImg, Imgproc.MORPH_BLACKHAT, S2);
        Core.normalize(mImg, mImg, 0, 255, Core.NORM_MINMAX);

        // Nhị phân hóa ảnh mImg
        Imgproc.threshold(mImg, thresholed, (int) Core.mean(mImg).val[0] * 10, 255, Imgproc.THRESH_BINARY);
        thresholed.copyTo(miniThresh);
        result.add(miniThresh);

        // Sử dụng hình chữ nhật có size = 8x16 trượt trên toàn bộ ảnh
        int cnt;
        int nonZero1, nonZero2, nonZero3, nonZero4;
        Rect rect;

        for (int i = 0; i < miniThresh.width() - 33; i += 4) {
            for (int j = 0; j < miniThresh.height() - 17; j += 4) {
                Mat sub = null;

                rect = new Rect(i, j, 16, 8);

                sub = miniThresh.submat(rect);
                nonZero1 = Core.countNonZero(sub);

                rect = new Rect(i, j + 8, 16, 8);
                sub = miniThresh.submat(rect);
                nonZero2 = Core.countNonZero(sub);

                rect = new Rect(i + 16, j, 16, 8);
                sub = miniThresh.submat(rect);
                nonZero3 = Core.countNonZero(sub);

                rect = new Rect(i + 16, j + 8, 16, 8);
                sub = miniThresh.submat(rect);
                nonZero4 = Core.countNonZero(sub);

                cnt = (nonZero1 > 15 ? 1 : 0) + (nonZero2 > 15 ? 1 : 0)
                        + (nonZero3 > 15 ? 1 : 0) + (nonZero4 > 15 ? 1 : 0);

                if (cnt > 2) {
                    System.out.println("Found [i;j] = [" + i + ";" + j + "]");
                    rect = new Rect(i, j, 32, 16);
                    Mat threshSub = miniThresh.submat(rect);
                    threshSub.copyTo(dst);
                }

                if (sub != null) {
                    sub.release();
                }

            }
        }

        Imgproc.dilate(dst, dst, S1, new Point(1, 0), 10);
        Imgproc.erode(dst, dst, S1, new Point(1, 0), 10);
        Imgproc.pyrUp(dst, dst);

        return dst;
    }
     */
}
