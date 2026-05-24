package tetris.controller;

import tetris.model.TetrisModel;
import tetris.view.TetrisView;
import tetris.SoundPlayer;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.List;
import java.util.ArrayList;

public class TetrisController {
    private TetrisModel model;
    private TetrisView view;
    private Timer timer;
    private SoundPlayer musicPlayer;
    private final String HIGHSCORE_FILE = "highscores.txt";

    private boolean leftPressed = false;
    private boolean rightPressed = false;
    private boolean downPressed = false;

    public TetrisController(TetrisModel model, TetrisView view) {
        this.model = model;
        this.view = view;
        this.musicPlayer = new SoundPlayer();
        initController();
    }

    public void start() {
        view.showMenu();
        view.setVisible(true);
    }

    private void initController() {
        view.getStartButton().addActionListener(e -> startGame());
        view.getRulesButton().addActionListener(e -> showRules());
        view.getHighScoresButton().addActionListener(e -> showHighScores());
        view.getExitButton().addActionListener(e -> System.exit(0));

        timer = new Timer(500, e -> gameTick());

        view.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (!timer.isRunning() || model.isGameOver()) return;

                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT: leftPressed = true; break;
                    case KeyEvent.VK_RIGHT: rightPressed = true; break;
                    case KeyEvent.VK_DOWN: downPressed = true; break;
                    case KeyEvent.VK_UP:
                        model.rotate();
                        view.updateBoard();
                        break;
                    case KeyEvent.VK_SPACE:
                        model.dropDown();
                        view.updateBoard();
                        break;
                }
                processMovement();
            }

            @Override
            public void keyReleased(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT: leftPressed = false; break;
                    case KeyEvent.VK_RIGHT: rightPressed = false; break;
                    case KeyEvent.VK_DOWN: downPressed = false; break;
                }
            }
        });
    }

    private void processMovement() {
        if (downPressed && leftPressed) {
            model.moveDownLeft();
        } else if (downPressed && rightPressed) {
            model.moveDownRight();
        } else if (leftPressed) {
            model.moveLeft();
        } else if (rightPressed) {
            model.moveRight();
        } else if (downPressed) {
            model.moveDown();
        }
        view.updateBoard();
    }

    private void showRules() {
        String rules = "Управление:\n• Стрелки ВЛЕВО/ВПРАВО — движение\n• ВВЕРХ — поворот\n• ВНИЗ — ускорение\n• ПРОБЕЛ — падение\n\nКаждые 10 линий очки увеличиваются!";
        JOptionPane.showMessageDialog(view, rules, "Правила", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showHighScores() {
        List<String> scores = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(HIGHSCORE_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                scores.add(line);
            }
        } catch (IOException e) {
        }

        if (scores.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Рекордов пока нет. Будьте первым!", "Рекорды", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        scores.sort((s1, s2) -> {
            try {
                int score1 = Integer.parseInt(s1.split(":")[1].trim());
                int score2 = Integer.parseInt(s2.split(":")[1].trim());
                return Integer.compare(score2, score1);
            } catch (Exception e) {
                return 0;
            }
        });

        StringBuilder sb = new StringBuilder("Топ игроков:\n\n");
        for (int i = 0; i < Math.min(scores.size(), 10); i++) {
            sb.append(i + 1).append(". ").append(scores.get(i)).append("\n");
        }
        JOptionPane.showMessageDialog(view, sb.toString(), "Таблица рекордов", JOptionPane.INFORMATION_MESSAGE);
    }

    private void saveCurrentScore() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(HIGHSCORE_FILE, true))) {
            writer.write(model.getPlayerName() + ": " + model.getScore());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startGame() {
        String name = JOptionPane.showInputDialog(view, "Введите имя:", "Новая игра", JOptionPane.QUESTION_MESSAGE);
        if (name != null && !name.trim().isEmpty()) {
            model.setPlayerName(name.trim());
        } else {
            model.setPlayerName("Игрок");
        }

        model.clearBoard();
        view.showGame();
        timer.start();
        view.requestFocusInWindow();
        musicPlayer.playBackgroundMusic("theme.wav");
    }

    private void gameTick() {
        if (!model.isGameOver()) {
            model.moveDown();
            view.updateBoard();
        } else {
            timer.stop();
            musicPlayer.stopMusic();
            musicPlayer.playSoundEffect("gameover.wav");

            saveCurrentScore();

            JOptionPane.showMessageDialog(view, "Игра окончена!\n" + model.getPlayerName() + ", ваш счёт: " + model.getScore());
            view.showMenu();
        }
    }
}