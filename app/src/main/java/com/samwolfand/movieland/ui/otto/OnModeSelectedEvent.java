package com.samwolfand.movieland.ui.otto;

/**
 * Created by wkh176 on 12/15/15.
 */
public class OnModeSelectedEvent {

    public String mode;

    public OnModeSelectedEvent(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }
}
