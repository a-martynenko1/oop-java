package tetris.model;
public class LShape extends Tetromino {
    public LShape() {
        super(new Pair[] { new Pair(-1, -1), new Pair(0, -1), new Pair(0, 0), new Pair(0, 1) });
    }
    @Override
    public TetrominoType getType() { return TetrominoType.LShape; }
}