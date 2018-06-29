package com.rungene.firebasedatasavededitetext;

/**
 * Created by SAM on 9/28/2017.
 */

public class Upload {
    public String avata;
    public String email;


    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)
    public Upload() {
    }

    public Upload(String avata, String email) {
        this.email = email;
        this.avata= avata;
    }


    public String getAvata() {
        return avata;
    }
    public String getEmail() {
        return email;
    }
}

