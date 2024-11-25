package entity;
//CreateTime: 2024-11-20 4:07 p.m.

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CoordinateTest {

    @Test
    public void fromStringTest() {
        assertEquals(new Coordinate(7, 7), Coordinate.fromString("h1"));
        assertEquals(new Coordinate(7, 0), Coordinate.fromString("a1"));
        assertEquals(new Coordinate(0, 0), Coordinate.fromString("a8"));
        assertEquals(new Coordinate(0, 7), Coordinate.fromString("h8"));
        assertEquals(new Coordinate(2, 1), Coordinate.fromString("b6"));
        assertEquals(new Coordinate(3, 5), Coordinate.fromString("f5"));
    }

    @Test
    public void ToStringTest() {
        assertEquals(new Coordinate(7, 7).toString(), "h1");
        assertEquals(new Coordinate(7, 0).toString(), "a1");
        assertEquals(new Coordinate(0, 0).toString(), "a8");
        assertEquals(new Coordinate(0, 7).toString(), "h8");
        assertEquals(new Coordinate(2, 1).toString(), "b6");
        assertEquals(new Coordinate(3, 5).toString(), "f5");
    }
}
