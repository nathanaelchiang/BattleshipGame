package cs3500.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;

/**
 * Coordinate class
 */
public class Coord {
  private int xcoord;
  private int ycoord;

  /**
   * Constructor for coordinates
   *
   * @param xcoord x-value
   * @param ycoord y-value
   */
  @JsonCreator
  public Coord(@JsonProperty("x") int xcoord,
               @JsonProperty("y") int ycoord) {
    this.xcoord = xcoord;
    this.ycoord = ycoord;
  }

  /**
   * Gets the x-value of a coordinate
   *
   * @return the x-value
   */
  public int getX() {
    return xcoord;
  }

  /**
   * Gets the y-value of a coordinate
   *
   * @return the y-value
   */
  public int getY() {
    return ycoord;
  }

  /**
   * Converts a coordinate to a string
   *
   * @return a coordinate as a string
   */
  @Override
  public String toString() {
    return "x = " + xcoord + ", y = " + ycoord;
  }

  /**
   * Compares two strings
   *
   * @param obj coordinate
   * @return true if they are equal
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    Coord other = (Coord) obj;
    return xcoord == other.xcoord && ycoord == other.ycoord;
  }

  /**
   * Returns the hash code value for this coordinate
   *
   * @return hash code value
   */
  @Override
  public int hashCode() {
    return Objects.hash(xcoord, ycoord);
  }
}
