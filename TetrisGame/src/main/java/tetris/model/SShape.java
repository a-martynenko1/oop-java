package tetris.model;
public class SShape extends Tetromino {
    public SShape() {
        super(new Pair[] { new Pair(0, -1), new Pair(0, 0), new Pair(1, 0), new Pair(1, 1) });
    }
    @Override
    public TetrominoType getType() { return TetrominoType.SShape; }
}