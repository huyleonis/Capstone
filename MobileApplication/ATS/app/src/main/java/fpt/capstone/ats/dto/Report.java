package fpt.capstone.ats.dto;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dovie on 11/15/2017.
 */

@IgnoreExtraProperties
public class Report {

    private String transactionId;
    private String username;
    private int isRead;
    private int isShow;


    public Report() {
    }

    public Report(String transactionId, String username, int isRead, int isShow) {
        this.transactionId = transactionId;
        this.username = username;
        this.isRead = isRead;
        this.isShow = isShow;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("transactionId", transactionId);
        result.put("username", username);
        result.put("isRead", isRead);
        result.put("isShow", isShow);

        return result;
    }
}
