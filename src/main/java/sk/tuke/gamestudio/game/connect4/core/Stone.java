package sk.tuke.gamestudio.game.connect4.core;


public class Stone {
    private final Color tileState;

    private int rowPosition;

    private final int columnPosition;

    /**
     * Create new Stone that act like a stone or empty tile
     *
     * @param color          color of stone
     * @param columnPosition column to which stone is added
     */
    public Stone(Color color, int columnPosition) {
        this.tileState = color;
        this.columnPosition = columnPosition;
    }

    public Color getTileState() {
        return tileState;
    }

    public int getColumnPosition() {
        return columnPosition;
    }

    public int getRowPosition() {
        return rowPosition;
    }

    public void setRowPosition(int rowPosition) {
        this.rowPosition = rowPosition;
    }
}
