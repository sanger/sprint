package uk.ac.sanger.sprint.model;

/**
 * The rotation of a {@link Field} on a label.
 * <ul>
 *     <li>{@code north}&mdash;the field is the right way up</li>
 *     <li>{@code east}&mdash;the field is rotated 90&deg; clockwise</li>
 *     <li>{@code south}&mdash;the field is rotated 180&deg;</li>
 *     <li>{@code west}&mdash;the field is rotated 90&deg; anticlockwise</li>
 * </ul>
 */
public enum Rotation {
    north, east, south, west
}
