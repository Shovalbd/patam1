package test;
import java.util.*;

public class Word {
    private Tile[] tiles;
    private int row;
    private int col;
    private boolean vertical;
    public Word(Tile[] tiles, int row, int col, boolean vertical) {
        this.tiles = tiles;
        this.row = row;
        this.col = col;
        this.vertical = vertical;
    }
    public Tile[] getTiles() {
        return tiles;
    }
    public int getRow() {
        return row;
    }
    public int getCol() {
        return col;
    }
    public boolean isVertical() {
        return vertical;
    }
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass()!= obj.getClass()) return false;
        Word word = (Word) obj;
        if (row == word.row && col == word.col && vertical == word.vertical && Arrays.equals(tiles, word.tiles)){
            return true;
        }
        return false;

    }


}
