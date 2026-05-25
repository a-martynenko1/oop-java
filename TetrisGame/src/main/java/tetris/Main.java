package tetris;

import tetris.controller.TetrisController;
import tetris.model.TetrisModel;
import tetris.view.TetrisView;

public class Main {
    public static void main(String[] args) {
        TetrisModel model = new TetrisModel();
        TetrisView view = new TetrisView(model);

        TetrisController controller = new TetrisController(model, view);

        controller.start();
    }
}
