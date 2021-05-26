package com.Udemy.tankWar;

import java.awt.*;

public enum Direction {

    UP("U"),
    DOWN("D"),
    LEFT("L"),
    RIGHT("R"),
    LEFT_UP("LU"),
    RIGHT_UP("RU"),
    LEFT_DOWN("LD"),
    RIGHT_DOWN("RD");
    private String imageType;

    Direction(String imageType) {
        this.imageType = imageType;
    }

    Image getImage(String prefix){
        return Tools.getImage(prefix+ imageType + ".gif");
    }
}
