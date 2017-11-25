/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Scanner;
import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.utils.Converters;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/**
 *
 * @author hp
 */
public class FileUtils {

    public static InputStream convertImageToInputStream(Mat img) {
        Mat tmp = img.clone();
        MatOfByte imgMem = new MatOfByte();

        Imgcodecs.imencode(".bmp", tmp, imgMem);
        InputStream is = new ByteArrayInputStream(imgMem.toArray());
        return is;
    }

    public static byte[] convertImageToByteArray(Mat img) {
        Mat tmp = img.clone();
        MatOfByte imgMem = new MatOfByte();

        Imgcodecs.imencode(".bmp", tmp, imgMem);
        byte[] imgBytes = imgMem.toArray();       
        
        return imgBytes;
    }
}
