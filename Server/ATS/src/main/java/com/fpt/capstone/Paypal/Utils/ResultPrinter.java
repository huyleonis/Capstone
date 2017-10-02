package com.fpt.capstone.Paypal.Utils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ResultPrinter {
    public static void addResult(HttpServletRequest req, HttpServletResponse res,
                                 String message, String request, String response, String error) {

        addDataToAttributeList(req, "message", message);
        addDataToAttributeList(req, "requests", request);
        response = (response != null) ? response : error;
        addDataToAttributeList(req, "responses", response);
        addDataToAttributeList(req, "errors", error);
        if (error != null) {
            try {
                req.getRequestDispatcher("response.jsp").forward(req, res);
            } catch (ServletException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @SuppressWarnings("unchecked")
    private static void addDataToAttributeList(HttpServletRequest req,
                                               String listName, String data) {

        List<String> list;
        if ((list = (List<String>) req.getAttribute(listName)) == null) {
            list = new ArrayList<String>();
        }
        list.add(data);
        req.setAttribute(listName, list);
    }
}
