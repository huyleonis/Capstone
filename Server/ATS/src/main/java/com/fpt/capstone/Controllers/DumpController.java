package com.fpt.capstone.Controllers;

import com.fpt.capstone.Dtos.PhotoDTO;
import com.fpt.capstone.Services.TransactionService;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;
import javax.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping(value = "/photoDump")
public class DumpController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private ServletContext context;

    static final int ACTIVE = 10;

    /**
     * Hiển thị trang photoDump.jsp
     *
     * @return photoDump view
     */
    @RequestMapping
    public ModelAndView viewDump() {
        ModelAndView m = new ModelAndView("photoDump");
        m.addObject("currSelected", ACTIVE);
        m.addObject("currTitle", "Unknown Photo Management");
        return m;
    }

    /**
     * Danh sách Hình trong thư mục DUMP
     *
     * @return danh sách Hình
     */
    @RequestMapping(value = "/getDumpToPhoto")
    public List<PhotoDTO> getDumpToPhoto() {

        String basePath = context.getRealPath(".");
        File folder = new File(basePath + "/WEB-INF/images/dumps");
        File[] listOfFile = folder.listFiles();
        List<PhotoDTO> listFile = new ArrayList<>();
        if (folder.isDirectory()) {
            for (int i = 0; i < listOfFile.length; i++) {
                if (listOfFile[i].isFile()) {

                    String str = listOfFile[i].getName();

                    StringTokenizer st = new StringTokenizer(str, "_.");
                    String licensePlate = st.nextToken();
                    String createdTime = st.nextToken();

                    Date date = new Date(Long.parseLong(createdTime));
                    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                    PhotoDTO dto = new PhotoDTO(str, licensePlate, df.format(date));

                    listFile.add(dto);
                }
            }
        }
        return listFile;
    }

    /**
     * Cập nhật Hình ảnh trong thư mục DUMPS vào thư mục PLATES Cập nhật
     * transaction ứng với hình ảnh và biển số xe tìm được
     *
     * @param transId Transaction ID
     * @param photo
     * @return Kết quả thực hiện
     */
    @RequestMapping(value = "/updatePhoto")
    public String updatePhoto(@RequestParam(name = "transId") String transId,
            @RequestParam(name = "photo") String photo) {

        boolean isSuccessful;

        isSuccessful = transactionService.updatePhoto(transId, photo);
        if (isSuccessful) {
            InputStream is = null;
            OutputStream os = null;
            String basePath = context.getRealPath(".");
            File folderSrc = new File(basePath + "/WEB-INF/images/dumps/" + photo + ".jpg");
            File folderPlate = new File(basePath + "/WEB-INF/images/plates/" + photo + ".jpg");
            try {
                is = new FileInputStream(folderSrc);
                os = new FileOutputStream(folderPlate);

                byte[] buffer = new byte[1024];
                int length;

                while ((length = is.read(buffer)) > 0) {
                    os.write(buffer, 0, length);
                }

                is.close();
                os.close();

                folderSrc.delete();

                System.out.println("File transfer is successfuly");
            } catch (IOException e) {

                System.out.println("Cannot transfer file dump");
            }

            return "success";
        }
        return "fail";
    }
}
