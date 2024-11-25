package entity;
//CreateTime: 2024-11-15 5:19 p.m.

public record Coordinate(int y, int x) {


    @Override
    public String toString() {
        return ((char) (x + BoardConstants.UPPERLEFTLETTER)) +
                "" + ((char) (BoardConstants.UPPERLEFTNUMBER - y));
    }

    public static Coordinate fromString(String s) {
        if (s.length() != 2) return null;
        return new Coordinate(BoardConstants.UPPERLEFTNUMBER - s.charAt(1), s.charAt(0) - BoardConstants.UPPERLEFTLETTER);
    }


}
