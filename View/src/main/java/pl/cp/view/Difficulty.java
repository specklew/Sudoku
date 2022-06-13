package pl.cp.view;

import javafx.util.Pair;
import pl.cp.model.SudokuBoard;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public enum Difficulty {
    EASY(6),
    MEDIUM(10),
    HARD(12);

    private final int fieldsCleared;

    Difficulty(int fieldsCleared) {
        this.fieldsCleared = fieldsCleared;
    }

    public void setBoardDifficulty(SudokuBoard sudokuBoard) {

        Random generator = new Random();
        List<Pair<Integer, Integer>> fieldNumbers = new ArrayList<>();

        for (int i = 0; i < fieldsCleared; ) {

            int valueX = generator.nextInt(0, 9);
            int valueY = generator.nextInt(0, 9);

            if (!fieldAvailable(new Pair<>(valueX, valueY), fieldNumbers)) continue;

            fieldNumbers.add(new Pair<>(valueX, valueY));
            i++;
        }

        for (Pair<Integer, Integer> pair : fieldNumbers) {
            sudokuBoard.set(pair.getKey(), pair.getValue(), 0);
        }
    }

    private boolean fieldAvailable(Pair<Integer, Integer> position, List<Pair<Integer, Integer>> fieldNumbers) {

        for (Pair<Integer, Integer> pair : fieldNumbers) {

            if (Objects.equals(pair.getKey(), position.getKey()) && Objects.equals(pair.getValue(), position.getValue())) {
                return false;
            }
        }
        return true;
    }
}
