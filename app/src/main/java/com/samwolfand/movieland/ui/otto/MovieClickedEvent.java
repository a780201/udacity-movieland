package com.samwolfand.movieland.ui.otto;

/**
 * Created by wkh176 on 12/14/15.
 */
public class MovieClickedEvent {
    public String otto = "";

    public MovieClickedEvent() {

    }

    public MovieClickedEvent(String otto) {
        this.otto = otto;
    }

    public String getOtto() {
        return otto;
    }

    public void setOtto(String otto) {
        this.otto = otto;
    }
}
