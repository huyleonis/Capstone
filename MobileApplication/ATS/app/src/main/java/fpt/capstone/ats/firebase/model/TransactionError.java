package fpt.capstone.ats.firebase.model;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dovie on 11/15/2017.
 */

@IgnoreExtraProperties
public class TransactionError {
    public String transactionId;
    public String username;


    public TransactionError() {
    }

    public TransactionError(String transactionId, String username) {
        this.transactionId = transactionId;
        this.username = username;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("transactionId", transactionId);
        result.put("username", username);

        return result;
    }
}
