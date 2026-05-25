package tetris.model;
public class TShape extends Tetromino {
    public TShape() {
        super(new Pair[] { new Pair(-1, 0), new Pair(0, 0), new Pair(1, 0), new Pair(0, 1) });
    }
    @Override
    public TetrominoType getType() { return TetrominoType.TShape; }
}