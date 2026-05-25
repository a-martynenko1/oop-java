package tetris.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TetrisModel {
    public static final int BOARD_WIDTH = 10;
    public static final int BOARD_HEIGHT = 22;
    private TetrominoType[] board;
    private boolean isGameOver;
    private int score;
    private int linesCleared;
    private String playerName = "Игрок";

    private Tetromino currentShape;
    private Pair[] currentCoords;
    private int curX;
    private int curY;

    private List<Tetromino> pieceBag;

    public TetrisModel() {
        board = new TetrominoType[BOARD_WIDTH * BOARD_HEIGHT];
        currentCoords = new Pair[4];
        for (int i = 0; i < 4; i++) {
            currentCoords[i] = new Pair(0, 0);
        }
        pieceBag = new ArrayList<>();
        clearBoard();
    }

    private void refillBag() {
        pieceBag.clear();
        pieceBag.add(new ZShape());
        pieceBag.add(new SShape());
        pieceBag.add(new LineShape());
        pieceBag.add(new TShape());
        pieceBag.add(new SquareShape());
        pieceBag.add(new LShape());
        pieceBag.add(new MirroredLShape());
        Collections.shuffle(pieceBag);
    }

    public void clearBoard() {
        for (int i = 0; i < BOARD_HEIGHT * BOARD_WIDTH; i++) {
            board[i] = TetrominoType.NoShape;
        }
        isGameOver = false;
        score = 0;
        linesCleared = 0;
        pieceBag.clear();
        spawnPiece();
    }

    public void spawnPiece() {
        if (pieceBag.isEmpty()) {
            refillBag();
        }
        currentShape = pieceBag.remove(0);

        Pair[] shapeCoords = currentShape.getCoords();
        for (int i = 0; i < 4; i++) {
            currentCoords[i].x = shapeCoords[i].x;
            currentCoords[i].y = shapeCoords[i].y;
        }

        curX = BOARD_WIDTH / 2;
        curY = -2;

        while (!isValidMove(currentCoords, curX, curY) && curY > -6) {
            curY--;
        }
    }

    public boolean moveDown() {
        if (!isValidMove(currentCoords, curX, curY + 1)) {
            pieceDropped();
            return false;
        }
        curY++;
        return true;
    }

    public void moveLeft() {
        if (isValidMove(currentCoords, curX - 1, curY)) curX--;
    }

    public void moveRight() {
        if (isValidMove(currentCoords, curX + 1, curY)) curX++;
    }

    public void moveDownLeft() {
        if (isValidMove(currentCoords, curX - 1, curY + 1)) {
            curX--;
            curY++;
        }
    }

    public void moveDownRight() {
        if (isValidMove(currentCoords, curX + 1, curY + 1)) {
            curX++;
            curY++;
        }
    }

    public void rotate() {
        if (currentShape.getType() == TetrominoType.SquareShape || currentShape.getType() == TetrominoType.NoShape) {
            return;
        }

        Pair[] newCoords = new Pair[4];
        for (int i = 0; i < 4; i++) {
            newCoords[i] = new Pair(currentCoords[i].y, -currentCoords[i].x);
        }

        if (isValidMove(newCoords, curX, curY)) {
            currentCoords = newCoords;
            return;
        }

        int[] kicks = {1, -1, 2, -2};

        for (int kickOffset : kicks) {
            if (isValidMove(newCoords, curX + kickOffset, curY)) {
                curX += kickOffset;
                currentCoords = newCoords;
                return;
            }
        }
    }

    public void dropDown() {
        int newY = curY;
        while (isValidMove(currentCoords, curX, newY + 1)) {
            newY++;
        }
        curY = newY;
        pieceDropped();
    }

    private boolean isValidMove(Pair[] coords, int newX, int newY) {
        for (int i = 0; i < 4; i++) {
            int x = newX + coords[i].x;
            int y = newY + coords[i].y;

            if (x < 0 || x >= BOARD_WIDTH || y >= BOARD_HEIGHT) return false;

            if (y >= 0 && shapeAt(x, y) != TetrominoType.NoShape) return false;
        }
        return true;
    }

    private void pieceDropped() {
        for (int i = 0; i < 4; i++) {
            int x = curX + currentCoords[i].x;
            int y = curY + currentCoords[i].y;

            if (x >= 0 && x < BOARD_WIDTH && y >= 0 && y < BOARD_HEIGHT) {
                board[(y * BOARD_WIDTH) + x] = currentShape.getType();
            }
        }
        removeFullLines();

        if (curY < 0) {
            isGameOver = true;
        } else {
            spawnPiece();
        }
    }

    private void removeFullLines() {
        int numFullLines = 0;
        for (int i = BOARD_HEIGHT - 1; i >= 0; i--) {
            boolean lineIsFull = true;
            for (int j = 0; j < BOARD_WIDTH; j++) {
                if (shapeAt(j, i) == TetrominoType.NoShape) {
                    lineIsFull = false;
                    break;
                }
            }
            if (lineIsFull) {
                numFullLines++;
                for (int k = i; k > 0; k--) {
                    for (int j = 0; j < BOARD_WIDTH; j++) {
                        board[(k * BOARD_WIDTH) + j] = shapeAt(j, k - 1);
                    }
                }
                for (int j = 0; j < BOARD_WIDTH; j++) {
                    board[j] = TetrominoType.NoShape;
                }
                i++;
            }
        }

        if (numFullLines > 0) {
            for (int i = 0; i < numFullLines; i++) {
                linesCleared++;
                int levelIndex = (linesCleared - 1) / 10;
                int pointsForThisLine = (int) (100 * Math.pow(1.1, levelIndex));
                score += pointsForThisLine;
            }
        }
    }

    public int getGhostY() {
        if (currentShape.getType() == TetrominoType.NoShape) return curY;

        int ghostY = curY;
        while (isValidMove(currentCoords, curX, ghostY + 1)) {
            ghostY++;
        }
        return ghostY;
    }

    public Tetromino getNextShape() {
        if (pieceBag.isEmpty()) {
            refillBag();
        }
        return pieceBag.get(0);
    }

    public TetrominoType shapeAt(int x, int y) {
        return board[(y * BOARD_WIDTH) + x];
    }

    public int getScore() {
        return score;
    }

    public int getLinesCleared() {
        return linesCleared;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public Tetromino getCurrentShape() {
        return currentShape;
    }

    public int getCurX() {
        return curX;
    }

    public int getCurY() {
        return curY;
    }

    public Pair[] getCurrentCoords() {
        return currentCoords;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
}