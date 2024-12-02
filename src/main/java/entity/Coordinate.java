package entity;
//CreateTime: 2024-11-15 5:19 p.m.


public record Coordinate(int y, int x) {


    @Override
    public String toString() {
        return ((char) (x + BoardConstants.UPPERLEFTLETTER)) +
                "" + ((char) (BoardConstants.UPPERLEFTNUMBER - y));
    }

    /**
     * convert a string coordinate of the board to the x-y coordinate of 2D array
     * @param s : a string coordinate, examples : a5, f3
     * @return : a coordinate object
     */
    public static Coordinate fromString(String s) {
        if (s.length() != 2) return null;
        return new Coordinate(BoardConstants.UPPERLEFTNUMBER - s.charAt(1), s.charAt(0) - BoardConstants.UPPERLEFTLETTER);
    }


}
