package sk.tuke.gamestudio.game.connect4.core;

public class Player {
    private int score = 0;

    private final String name;

    private final Color color;

    private final Playfield playfield;

    /**
     * Creates instance of Player. They need to be exact two of this instance.
     * @param name name of player as user wishes
     * @param color color under which player will be playing game session
     * @param playfield playfield which game is played on
     */
    public Player(String name, final Color color, Playfield playfield) {
        this.name = name;
        this.color = color;
        this.playfield = playfield;
    }

    /**
     * Adds points to player's account.
     * @param amount how much points player earned
     */
    public void addPoints(int amount) {
        score += amount;
    }

    public int getScore() {
        return score;
    }

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }

    /**
     * Adds stone to playfield and stone falls down.
     * @param column which column player added to playfield
     * @return true if stone was successfully added, otherwise false
     */
    public boolean addStone(int column) {
        int rowPos = 0;
        Stone[][] stones = playfield.getTiles();

        if (stones[0][column] != null) {
            return false;
        }

        Stone stone = new Stone(color, column);

        while (rowPos < playfield.getHeight() - 1) {
            if (stones[rowPos + 1][column] == null) {
                rowPos++;
            } else {
                break;
            }
        }
        stones[rowPos][column] = stone;
        stones[rowPos][column].setRowPosition(rowPos);
        return true;
    }
}
