package tetris.model;

public abstract class Tetromino {
    private final Pair[] coords;

    public Tetromino(Pair[] coords) {
        this.coords = coords;
    }

    public Pair[] getCoords() {
        return coords;
    }

    public abstract TetrominoType getType();
}