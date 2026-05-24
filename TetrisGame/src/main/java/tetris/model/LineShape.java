package tetris.model;
public class LineShape extends Tetromino {
    public LineShape() {
        super(new Pair[] { new Pair(0, -1), new Pair(0, 0), new Pair(0, 1), new Pair(0, 2) });
    }
    @Override
    public TetrominoType getType() { return TetrominoType.LineShape; }
}