package com.samwolfand.movieland.ui.adapter.spinner;

public class ModeSpinnerItem {
    boolean isHeader;
    String mode, title;
    boolean indented;

    ModeSpinnerItem(boolean isHeader, String mode, String title, boolean indented) {
        this.isHeader = isHeader;
        this.mode = mode;
        this.title = title;
        this.indented = indented;
    }
}
