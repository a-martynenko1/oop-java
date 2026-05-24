package tetris.view;

import tetris.model.Tetromino;
import tetris.model.TetrisModel;
import tetris.model.Pair;
import tetris.model.TetrominoType;

import javax.swing.*;
import java.awt.*;

public class TetrisView extends JFrame {
    private TetrisModel model;
    private CardLayout cardLayout;
    private JPanel mainContainer;
    private BoardPanel boardPanel;

    private JLabel scoreLabel;
    private JLabel linesLabel;
    private NextPiecePanel nextPiecePanel;

    private JButton startButton;
    private JButton rulesButton;
    private JButton highScoresButton;
    private JButton exitButton;

    public TetrisView(TetrisModel model) {
        this.model = model;

        setTitle("Tetris MVC");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        cardLayout = new CardLayout();
        mainContainer = new JPanel(cardLayout);

        mainContainer.add(createMenuPanel(), "MENU");
        mainContainer.add(createGamePanel(), "GAME");

        add(mainContainer);
        pack();
        setLocationRelativeTo(null);
    }

    private JPanel createMenuPanel() {
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setBackground(new Color(40, 40, 40));

        JLabel titleLabel = new JLabel("ТЕТРИС");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 40));
        titleLabel.setForeground(Color.CYAN);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        startButton = new JButton("Начать игру");
        rulesButton = new JButton("Правила");
        highScoresButton = new JButton("Таблица рекордов");
        exitButton = new JButton("Выйти");

        JButton[] buttons = {startButton, rulesButton, highScoresButton, exitButton};
        for (JButton btn : buttons) {
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            btn.setFont(new Font("Arial", Font.BOLD, 16));
            btn.setMaximumSize(new Dimension(200, 40));
            btn.setFocusPainted(false);
        }

        menuPanel.add(Box.createVerticalStrut(100));
        menuPanel.add(titleLabel);
        menuPanel.add(Box.createVerticalStrut(60));
        for (JButton btn : buttons) {
            menuPanel.add(btn);
            menuPanel.add(Box.createVerticalStrut(15));
        }

        menuPanel.setPreferredSize(new Dimension(TetrisModel.BOARD_WIDTH * 30 + 150, TetrisModel.BOARD_HEIGHT * 30));
        return menuPanel;
    }

    private JPanel createGamePanel() {
        JPanel gamePanel = new JPanel(new BorderLayout());
        gamePanel.setBackground(Color.DARK_GRAY);

        boardPanel = new BoardPanel();

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBackground(Color.DARK_GRAY);
        rightPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        rightPanel.setPreferredSize(new Dimension(150, TetrisModel.BOARD_HEIGHT * 30));

        JLabel nextLabel = new JLabel("СЛЕДУЮЩАЯ");
        nextLabel.setForeground(Color.LIGHT_GRAY);
        nextLabel.setFont(new Font("Arial", Font.BOLD, 14));
        nextLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        nextPiecePanel = new NextPiecePanel();
        nextPiecePanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        scoreLabel = new JLabel("Счёт: 0");
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 18));
        scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        linesLabel = new JLabel("Линии: 0");
        linesLabel.setForeground(Color.WHITE);
        linesLabel.setFont(new Font("Arial", Font.BOLD, 18));
        linesLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        rightPanel.add(nextLabel);
        rightPanel.add(Box.createVerticalStrut(10));
        rightPanel.add(nextPiecePanel);
        rightPanel.add(Box.createVerticalStrut(40));
        rightPanel.add(scoreLabel);
        rightPanel.add(Box.createVerticalStrut(20));
        rightPanel.add(linesLabel);
        rightPanel.add(Box.createVerticalGlue());

        gamePanel.add(boardPanel, BorderLayout.CENTER);
        gamePanel.add(rightPanel, BorderLayout.EAST);

        return gamePanel;
    }

    public void showMenu() { cardLayout.show(mainContainer, "MENU"); }
    public void showGame() { cardLayout.show(mainContainer, "GAME"); }

    public void updateBoard() {
        boardPanel.repaint();
        nextPiecePanel.repaint();
        scoreLabel.setText("Счёт: " + model.getScore());
        linesLabel.setText("Линии: " + model.getLinesCleared());
    }

    public void updateScore(int score) {
        scoreLabel.setText("Счёт: " + score);
    }

    public JButton getStartButton() { return startButton; }
    public JButton getRulesButton() { return rulesButton; }
    public JButton getHighScoresButton() { return highScoresButton; }
    public JButton getExitButton() { return exitButton; }

    class NextPiecePanel extends JPanel {
        private final int CELL_SIZE = 25;

        public NextPiecePanel() {
            setBackground(Color.BLACK);
            setPreferredSize(new Dimension(5 * CELL_SIZE, 5 * CELL_SIZE));
            setMaximumSize(new Dimension(5 * CELL_SIZE, 5 * CELL_SIZE));
            setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            Tetromino nextShape = model.getNextShape();
            if (nextShape != null && nextShape.getType() != TetrominoType.NoShape) {
                Pair[] coords = nextShape.getCoords();

                int minX = 0, maxX = 0, minY = 0, maxY = 0;
                for (Pair coord : coords) {
                    minX = Math.min(minX, coord.x);
                    maxX = Math.max(maxX, coord.x);
                    minY = Math.min(minY, coord.y);
                    maxY = Math.max(maxY, coord.y);
                }

                int width = (maxX - minX + 1) * CELL_SIZE;
                int height = (maxY - minY + 1) * CELL_SIZE;

                int offsetX = (getWidth() - width) / 2 - minX * CELL_SIZE;
                int offsetY = (getHeight() - height) / 2 - minY * CELL_SIZE;

                for (Pair coord : coords) {
                    int x = offsetX + coord.x * CELL_SIZE;
                    int y = offsetY + coord.y * CELL_SIZE;
                    boardPanel.drawSquare(g2d, x, y, boardPanel.getColor(nextShape.getType()), false, CELL_SIZE);
                }
            }
        }
    }

    class BoardPanel extends JPanel {
        private final int CELL_SIZE = 30;

        public BoardPanel() {
            setBackground(Color.BLACK);
            setPreferredSize(new Dimension(TetrisModel.BOARD_WIDTH * CELL_SIZE, TetrisModel.BOARD_HEIGHT * CELL_SIZE));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            for (int i = 0; i < TetrisModel.BOARD_HEIGHT; i++) {
                for (int j = 0; j < TetrisModel.BOARD_WIDTH; j++) {
                    TetrominoType shape = model.shapeAt(j, i);
                    if (shape != TetrominoType.NoShape) {
                        drawSquare(g2d, j * CELL_SIZE, i * CELL_SIZE, getColor(shape), false, CELL_SIZE);
                    }
                }
            }

            if (model.getCurrentShape().getType() != TetrominoType.NoShape) {
                int ghostY = model.getGhostY();
                Pair[] coords = model.getCurrentCoords();

                for (int i = 0; i < 4; i++) {
                    int x_ghost = (model.getCurX() + coords[i].x) * CELL_SIZE;
                    int y_ghost = (ghostY + coords[i].y) * CELL_SIZE;
                    if (y_ghost >= 0) {
                        drawSquare(g2d, x_ghost, y_ghost, getColor(model.getCurrentShape().getType()), true, CELL_SIZE);
                    }
                }

                g2d.setColor(new Color(255, 255, 255, 60));
                g2d.setStroke(new BasicStroke(1));
                int curY = model.getCurY();

                for (int i = 0; i < 4; i++) {
                    int col = model.getCurX() + coords[i].x;
                    int rowInPiece = curY + coords[i].y;
                    int rowAtBottom = ghostY + coords[i].y;

                    boolean isBottomBlockInCol = true;
                    for(int k=0; k<4; k++) {
                        if(coords[k].x == coords[i].x && coords[k].y > coords[i].y) {
                            isBottomBlockInCol = false;
                            break;
                        }
                    }

                    if (isBottomBlockInCol && rowInPiece < rowAtBottom) {
                        int x1_pix = col * CELL_SIZE;
                        int x2_pix = (col + 1) * CELL_SIZE;
                        int y1_pix = (rowInPiece + 1) * CELL_SIZE;
                        int y2_pix = rowAtBottom * CELL_SIZE;

                        if (y1_pix < y2_pix) {
                            g2d.drawLine(x1_pix, y1_pix, x1_pix, y2_pix);
                            g2d.drawLine(x2_pix, y1_pix, x2_pix, y2_pix);
                        }
                    }
                }

                for (int i = 0; i < 4; i++) {
                    int x = model.getCurX() + coords[i].x;
                    int y = model.getCurY() + coords[i].y;
                    if (y >= 0) {
                        drawSquare(g2d, x * CELL_SIZE, y * CELL_SIZE, getColor(model.getCurrentShape().getType()), false, CELL_SIZE);
                    }
                }
            }
        }

        public void drawSquare(Graphics2D g2d, int x, int y, Color color, boolean isGhost, int size) {
            if (isGhost) {
                Color ghostColor = new Color(color.getRed(), color.getGreen(), color.getBlue(), 50);
                g2d.setColor(ghostColor);
                g2d.fillRect(x + 1, y + 1, size - 2, size - 2);

                g2d.setColor(new Color(255, 255, 255, 20));
                g2d.drawLine(x, y + size - 1, x, y);
                g2d.drawLine(x, y, x + size - 1, y);

                g2d.setColor(new Color(0, 0, 0, 20));
                g2d.drawLine(x + 1, y + size - 1, x + size - 1, y + size - 1);
                g2d.drawLine(x + size - 1, y + size - 1, x + size - 1, y + 1);
            } else {
                g2d.setColor(color);
                g2d.fillRect(x + 1, y + 1, size - 2, size - 2);

                g2d.setColor(color.brighter());
                g2d.drawLine(x, y + size - 1, x, y);
                g2d.drawLine(x, y, x + size - 1, y);

                g2d.setColor(color.darker());
                g2d.drawLine(x + 1, y + size - 1, x + size - 1, y + size - 1);
                g2d.drawLine(x + size - 1, y + size - 1, x + size - 1, y + 1);
            }
        }

        public Color getColor(TetrominoType shape) {
            switch (shape) {
                case ZShape: return Color.RED;
                case SShape: return Color.GREEN;
                case LineShape: return Color.CYAN;
                case TShape: return Color.MAGENTA;
                case SquareShape: return Color.YELLOW;
                case LShape: return Color.ORANGE;
                case MirroredLShape: return Color.BLUE;
                default: return Color.BLACK;
            }
        }
    }
}