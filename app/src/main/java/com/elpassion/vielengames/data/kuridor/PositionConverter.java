package com.elpassion.vielengames.data.kuridor;

import android.graphics.drawable.GradientDrawable;

/**
 * Created by mateusz on 16/06/2014.
 */
public class PositionConverter {

    public enum Orientation {
        hor,
        ver,
        none;

        @Override
        public String toString() {
            switch (this) {
                case hor:
                    return "h";
                case ver:
                    return "v";
                case none:
                    return "";
                default:
                    throw new IllegalArgumentException();
            }
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

            if ('h' == position.charAt(0))
                return Orientation.hor;

            else if ('v' == position.charAt(0))
                return Orientation.ver;

            return Orientation.none;
        }

        public static String getPosition(Orientation orientation, int x, int y) {
            return orientation.toString() + (char) ((int) 'a' + x) + (char) ((int) '8' - y);
        }
    }
