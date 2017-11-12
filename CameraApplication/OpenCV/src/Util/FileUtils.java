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

    public static Mat parseXmlFile(File f, String nodeName) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(f);

        doc.getDocumentElement().normalize();

        Element img = (Element) doc.getElementsByTagName(nodeName).item(0);
        int rows = Integer.parseInt(img.getElementsByTagName("rows").item(0).getTextContent().trim());
        int cols = Integer.parseInt(img.getElementsByTagName("cols").item(0).getTextContent().trim());
        String sData = img.getElementsByTagName("data").item(0).getTextContent().trim();
        String dt = img.getElementsByTagName("dt").item(0).getTextContent().trim();

        Mat result;

        if (dt.equals("i")) {
            result = new Mat(rows, cols, CvType.CV_32SC1);
            Scanner sc = new Scanner(sData);
            int[] data;
            for (int i = 0; i < rows; i++) {
                data = new int[cols];
                for (int j = 0; j < cols; j++) {
                    data[j] = sc.nextInt();
                }

                result.put(i, 0, data);
            }
        } else if (dt.equals("f")) {
            result = new Mat(rows, cols, CvType.CV_32FC1);
            Scanner sc = new Scanner(sData);
            float[] data;
            for (int i = 0; i < rows; i++) {
                data = new float[cols];
                for (int j = 0; j < cols; j++) {
                    data[j] = sc.nextFloat();
                }
                result.put(i, 0, data);
            }
        } else {
            result = new Mat();
        }

        //= Converters.vector_int_to_Mat(data);                        
        return result;

    }

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
