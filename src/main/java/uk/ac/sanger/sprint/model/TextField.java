package uk.ac.sanger.sprint.model;

/**
 * A piece of text on a label
 */
public class TextField extends Field {
    private Font font = Font.proportional;
    private float fontSize;

    /**
     * The type of font for this text field
     * @return the type of font for this text field
     */
    public Font getFont() {
        return font;
    }

    /**
     * Sets the font for this text field
     * @param font the new font
     */
    public void setFont(Font font) {
        this.font = font;
    }

    /**
     * Gets the designated height of the text, in mm.
     * @return the font size in mm
     */
    public float getFontSize() {
        return fontSize;
    }

    /**
     * Sets the designated height of the text, in mm
     * @param fontSize the font size in mm
     */
    public void setFontSize(float fontSize) {
        this.fontSize = fontSize;
    }

    @Override
    public String toString() {
        return stringHelper()
                .add("font", font)
                .add("fontSize", fontSize)
                .toString();
    }
}
