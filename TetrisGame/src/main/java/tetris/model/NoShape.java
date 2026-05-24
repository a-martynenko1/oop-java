package tetris.model;
public class NoShape extends Tetromino {
    public NoShape() {
        super(new Pair[] { new Pair(0, 0), new Pair(0, 0), new Pair(0, 0), new Pair(0, 0) });
    }
    @Override
    public TetrominoType getType() { return TetrominoType.NoShape; }
}