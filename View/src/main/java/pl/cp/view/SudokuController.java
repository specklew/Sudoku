package pl.cp.view;

import javafx.beans.binding.Bindings;
import javafx.beans.property.adapter.JavaBeanIntegerProperty;
import javafx.beans.property.adapter.JavaBeanIntegerPropertyBuilder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.util.converter.NumberStringConverter;
import pl.cp.model.SudokuBoard;
import pl.cp.model.dao.Dao;
import pl.cp.model.dao.SudokuBoardDaoFactory;
import pl.cp.model.exceptions.DaoException;
import pl.cp.model.exceptions.WriteException;
import pl.cp.model.solver.BacktrackingSudokuSolver;

import java.io.IOException;
import java.util.*;
import java.util.function.UnaryOperator;

public class SudokuController {

    private SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());

    public static List<Integer> listLoad = new ArrayList<>();

    public static List<Integer> list = new ArrayList<>();

    private final ResourceBundle resourceBundle = ResourceBundle.getBundle("bundles.language");

    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    public MenuButton difficultyButton;
    @FXML
    public MenuItem difficultyEasy;
    @FXML
    public MenuItem difficultyMedium;
    @FXML
    public MenuItem difficultyHard;
    private Difficulty selectedDifficulty = Difficulty.MEDIUM;

    @FXML
    public MenuButton languageButton;

    @FXML
    public Button startButton;
    @FXML
    public TextField textFileName;

    @FXML
    void easyButton(ActionEvent event) {
        selectedDifficulty = Difficulty.EASY;
    }

    @FXML
    void mediumButton(ActionEvent event) {
        selectedDifficulty = Difficulty.MEDIUM;
    }

    @FXML
    void hardButton(ActionEvent event) {
        selectedDifficulty = Difficulty.HARD;
    }

    @FXML
    void languagePLButton(ActionEvent event) throws IOException {
        Locale locale = new Locale("pl");
        setLanguage(ResourceBundle.getBundle("bundles.language", locale));
    }

    @FXML
    void languageENGButton(ActionEvent event) throws IOException {
        Locale locale = new Locale("en");
        setLanguage(ResourceBundle.getBundle("bundles.language", locale));
    }

    private void setLanguage(ResourceBundle resourceBundle) {
        difficultyButton.setText(resourceBundle.getString("difficulty"));
        difficultyEasy.setText(resourceBundle.getString("difficulty_easy"));
        difficultyMedium.setText(resourceBundle.getString("difficulty_medium"));
        difficultyHard.setText(resourceBundle.getString("difficulty_hard"));


        languageButton.setText(resourceBundle.getString("language"));
    }

    @FXML
    public void startButton(ActionEvent event) throws IOException {

        Stage stageGame = new Stage();
        listLoad.add(0);
        loadBoard(stageGame, selectedDifficulty);
    }

    public void loadSavingScreen() {

        StackPane r = new StackPane();
        GridPane grid = new GridPane();
        r.getChildren().add(grid);

        BorderPane root = new BorderPane();
        root.setCenter(grid);

        Button save = new Button("save");
        BorderPane bottom = new BorderPane();
        bottom.setRight(save);
        root.setBottom(bottom);
        Label label1 = new Label("please enter sudoku name: ");

        label1.setFont(Font.font("Verdana", 15));
        root.setLeft(label1);

        TextField textField = new TextField();
        textField.setPrefSize(200, 25);
        textField.setFont(Font.font("Verdana", 15));
        textField.setAlignment(Pos.CENTER);
        root.setRight(textField);

        save.setFont(Font.font("Verdana", 15));
        root.setPadding(new Insets(20));

        Stage stage = new Stage();
        stage.setScene(new Scene(root, 500, 150));

        save.setOnAction(event -> stage.close());
        save.setOnAction(event -> {
            String sudokuName = textField.getText();
            try {
                Dao<SudokuBoard> sudokuBoardDao = SudokuBoardDaoFactory.getFileDao(sudokuName);
                sudokuBoardDao.write(sudokuBoard);


            } catch (WriteException | DaoException e) {
                e.printStackTrace();
            }

            stage.close();

        });

        stage.show();
    }

    public void loadBoard(Stage stage, Difficulty difficulty) {

        sudokuBoard.solveGame();
        difficulty.setBoardDifficulty(sudokuBoard);

        if (!Objects.equals(textFileName.getText(), "")) {
            try (Dao<SudokuBoard> sudokuBoardDao = SudokuBoardDaoFactory.getFileDao(textFileName.getText())) {

                sudokuBoard = sudokuBoardDao.read();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try {
            displayBoard(stage);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    private void displayBoard(Stage stage) throws NoSuchMethodException {
        BorderPane root = new BorderPane();
        GridPane grid = new GridPane();

        root.setCenter(grid);
        stage.setTitle("Sudoku");
        stage.setScene(new Scene(root, 900, 900));
        stage.setResizable(false);

        BorderPane bottom = new BorderPane();

        Button saveButton = new Button("SAVE");
        Button lol = saveButton;
        saveButton.setOnAction(event -> loadSavingScreen());
        bottom.setRight(saveButton);
        root.setBottom(bottom);
        saveButton.setFont(Font.font("Verdana", 15));

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                TextField txt = new TextField(String.valueOf(sudokuBoard.get(i, j)));
                txt.setPrefSize(666, 666);
                txt.setFont(Font.font("Calibri", 30));
                txt.setAlignment(Pos.CENTER);
                if (sudokuBoard.get(i, j) == 0) {
                    txt.clear();
                    UnaryOperator<TextFormatter.Change> textFilter = c -> {

                        if (c.getText().matches("[1-9]")) {
                            c.setRange(0, txt.getText().length());
                            return c;
                        } else if (c.getText().isEmpty()) {
                            return c;
                        }
                        return null;
                    };

                    TextFormatter<Integer> formatter = new TextFormatter<>(stringConverter, 0, textFilter);

                    txt.setTextFormatter(formatter);

                    JavaBeanIntegerProperty intProperty =
                            JavaBeanIntegerPropertyBuilder
                                    .create()
                                    .bean(sudokuBoard.getField(i, j))
                                    .name("value")
                                    .build();

                    txt.setText(String.valueOf(sudokuBoard.get(i, j))); //postaraj się zrobić binding elegancko a nie set text
                    Bindings.bindBidirectional(txt.textProperty(), intProperty,
                            new NumberStringConverter()); //nie obsługuje 0
                    grid.add(txt, i, j);
                } else {
                    txt.setEditable(false);
                    grid.add(txt, i, j);
                }
            }
        }
        stage.show();
    }

    StringConverter<Integer> stringConverter = new StringConverter<>() {

        @Override
        public String toString(Integer object) {
            if (object == null || object == 0) {
                return "";
            }
            return object.toString();
        }

        @Override
        public Integer fromString(String string) {
            if (string == null || string.isEmpty()) {
                return 0;
            }
            return Integer.parseInt(string);
        }
    };
}