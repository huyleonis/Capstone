package fpt.capstone.ats.firebase.model;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by dovie on 11/10/2017.
 */

@IgnoreExtraProperties
public class User {
    public String name;
    public String email;

    public User() {
    }

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
