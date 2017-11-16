package fpt.capstone.ats.firebase.model;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dovie on 11/10/2017.
 */

@IgnoreExtraProperties
public class Message {
    public String author;
    public String body;
    public String time;

    public Message() {
    }

    public Message(String author, String body, String time) {
        this.author = author;
        this.body = body;
        this.time = time;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("author", author);
        result.put("body", body);
        result.put("time", time);

        return result;
    }
}
