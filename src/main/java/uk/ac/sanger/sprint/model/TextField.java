package uk.ac.sanger.sprint.model;

public class TextField extends Field {
    private Font font = Font.proportional;
    private float fontSize;

    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;
    }

    public float getFontSize() {
        return fontSize;
    }

    public void setFontSize(float fontSize) {
        this.fontSize = fontSize;
    }
}
