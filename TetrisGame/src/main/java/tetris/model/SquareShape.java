package tetris.model;
public class SquareShape extends Tetromino {
    public SquareShape() {
        super(new Pair[] { new Pair(0, 0), new Pair(1, 0), new Pair(0, 1), new Pair(1, 1) });
    }
    @Override
    public TetrominoType getType() { return TetrominoType.SquareShape; }
}