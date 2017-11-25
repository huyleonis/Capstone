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
import org.opencv.imgproc.Imgproc;

/**
 *
 * @author tinpb
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
    
}
