package com.vielengames.data.kuridor;

/**
 * Created by mateusz on 16/06/2014.
 */
public class PositionConverter {

    public enum Orientation {
        hor("h"),
        ver("v"),
        none("");

        private final String str;

        private Orientation(String str) {
            this.str = str;
        }

        @Override
        public String toString() {
            return str;
        }
    }

    public static int getX(String position) {
        int i = getOrientation(position) == Orientation.none ? 0 : 1;

        if (position == null || position.length() <= i)
            return 0;

        return Math.abs(((int) 'a') - ((int) position.charAt(i)));
    }

    public static int getY(String position) {
        int i = getOrientation(position) == Orientation.none ? 1 : 2;

        if (position == null || position.length() <= i)
            return 0;

        return ((int) '9') - ((int) position.charAt(i));
    }

    public static Orientation getOrientation(String position) {
        if (position == null || position.length() < 1)
            return Orientation.none;

        if ('h' == position.charAt(0) && position.length() > 2)
            return Orientation.hor;

        else if ('v' == position.charAt(0))
            return Orientation.ver;

        return Orientation.none;
    }

    public static String getPosition(Orientation orientation, int x, int y) {
        return (orientation.toString() + (char) ((int) 'a' + x) + (char) ((int) '9' - y));
    }
}
