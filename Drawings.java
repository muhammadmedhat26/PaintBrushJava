package iti.Project;

import java.awt.*;

class Drawings {
    int x1, y1, x2, y2;
    String type;
    Color color;
    boolean filled;
    boolean dotted;
    public Drawings(int x1, int y1, int x2, int y2, String type, Color color, boolean filled, boolean dotted ) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.type = type;
        this.color = color;
        this.filled = filled;
        this.dotted = dotted;

    }
}