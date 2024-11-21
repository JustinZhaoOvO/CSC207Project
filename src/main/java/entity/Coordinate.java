package entity;
//CreateTime: 2024-11-15 5:19 p.m.

public class Coordinate {

    public final int x;
    public final int y;

    public Coordinate(int y, int x) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Coordinate that = (Coordinate) o;
        return x == that.x && y == that.y;
    }


    @Override
    public String toString() {
        return ((char) (x + 'a')) + "" + ((char) ('8' - y));
    }

    public static Coordinate fromString(String s) {
        if (s.length() != 2) return null;
        return new Coordinate('8' - s.charAt(1), s.charAt(0) - 'a');
    }


}
