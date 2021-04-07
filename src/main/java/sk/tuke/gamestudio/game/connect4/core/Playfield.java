package sk.tuke.gamestudio.game.connect4.core;

public class Playfield {
    private final Stone[][] stones;

    private final int width;

    private final int height;

    /**
     * Creates a playfield made of Stone instances. This class holds attributes and check for win condition.
     * @param width width of playfield
     * @param height height of playfield
     */
    public Playfield(final int width, final int height) {
        this.width = width;
        this.height = height;
        stones = new Stone[height][width];
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Stone[][] getTiles() {
        return stones;
    }

    /**
     * Checks playfield for 4 stones in row one by one and check all 8 directions if possible
     * @return true if game is won, otherwise false
     */
    public boolean checkForWin() {
        for (int rowIndex = 0; rowIndex < height; rowIndex++) {
            for (int columnIndex = 0; columnIndex < width; columnIndex++) {
                if (stones[rowIndex][columnIndex] != null){
                    int stoneRowPosition = stones[rowIndex][columnIndex].getRowPosition();
                    int stoneColumnPosition = stones[rowIndex][columnIndex].getColumnPosition();
                    Color color = stones[rowIndex][columnIndex].getTileState();
                    for (int deltaX = -1; deltaX <= 1; deltaX++) {
                        for (int deltaY = -1; deltaY <= 1; deltaY++) {
                            if (deltaX == 0 && deltaY != -1) { continue; }
                            if (checkDirection(stoneRowPosition, stoneColumnPosition, color, deltaX, deltaY)) { return true; }
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean checkDirection(final int stoneRowPosition, final int stoneColumnPosition, final Color color,final int dx, final int dy) {
        if ((dx == 1 && stoneColumnPosition > width - 4) || (dx == -1 && stoneColumnPosition < 3)
            || (dy == -1 && stoneRowPosition < 3) || (dy == 1 && stoneRowPosition > height - 4)) {
            return false;
        }

        for (int i = 1; i <= 3; i++) {
            for (int j = 1; j <= 3; j++) {
                if (stones[stoneRowPosition + dy * j][stoneColumnPosition + dx * j] != null) {
                    if (stones[stoneRowPosition + dy * j][stoneColumnPosition + dx * j].getTileState() != color) {
                        return false;
                    }
                } else { return false; }
            }
        }
        return true;
    }
}
