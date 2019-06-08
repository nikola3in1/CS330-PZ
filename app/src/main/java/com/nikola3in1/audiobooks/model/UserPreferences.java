package com.nikola3in1.audiobooks.model;

import java.io.Serializable;

public class UserPreferences implements Serializable {
    public static final String BUNDLE = "USER_PREFERENCES";
    // Value used for fast forwarding & rewinding
    private Integer mediaSkipValue = 10;

    public Integer getMediaSkipValue() {
        return mediaSkipValue;
    }

    public void setMediaSkipValue(Integer mediaSkipValue) {
        this.mediaSkipValue = mediaSkipValue;
    }

    @Override
    public String toString() {
        return "UserPreferences{" +
                "mediaSkipValue=" + mediaSkipValue +
                '}';
    }
}
