package cipher;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MainApp extends Application {
    private Operation operation;

    public static void main(String[] args) {
        launch(args);
        //new ConsoleDialogue(new CaesarCoder()).run();
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("File Encoder");

        // Главное меню
        Label mainMenuLabel = new Label("Choose an action:");
        Button encryptButton = new Button("Encrypt file");
        Button decryptButton = new Button("Decrypt file");
        Button bruteForceButton = new Button("Brute Force");
        Button exitButton = new Button("Exit");

        VBox mainMenu = new VBox(10, mainMenuLabel, encryptButton, decryptButton, bruteForceButton, exitButton);
        mainMenu.setPadding(new Insets(15));
        mainMenu.setStyle("-fx-alignment: center;");

        VBox inputPane = new VBox(10);
        inputPane.setPadding(new Insets(15));
        inputPane.setStyle("-fx-alignment: center;");

        Label filenameLabel = new Label("Enter filename which contains original text:");
        TextField filenameField = new TextField();

        Label resultFileLabel = new Label("Enter filename which will be used for result saving:");
        TextField resultFileField = new TextField();

        Label keyOrAnalysisLabel = new Label();
        TextField keyOrAnalysisField = new TextField();

        Button runButton = new Button("Run");
        Button backButton = new Button("Back");

        inputPane.getChildren().addAll(filenameLabel, filenameField, resultFileLabel, resultFileField, keyOrAnalysisLabel, keyOrAnalysisField, runButton, backButton);

        StackPane root = new StackPane(mainMenu);

        encryptButton.setOnAction(e -> {
            operation = Operation.ENCRYPTION;
            keyOrAnalysisLabel.setText("Enter key:");
            keyOrAnalysisField.setPromptText("Key");
            switchToPane(root, inputPane);
        });

        decryptButton.setOnAction(e -> {
            operation = Operation.DECRYPTION;
            keyOrAnalysisLabel.setText("Enter key:");
            keyOrAnalysisField.setPromptText("Key");
            switchToPane(root, inputPane);
        });

        bruteForceButton.setOnAction(e -> {
            operation = Operation.BRUTE_FORCE;
            keyOrAnalysisLabel.setText("Enter the file name that will be used to analyze the decryption:");
            keyOrAnalysisField.setPromptText("Analysis file name");
            switchToPane(root, inputPane);
        });

        exitButton.setOnAction(e -> primaryStage.close());

        backButton.setOnAction(e -> switchToPane(root, mainMenu));

        runButton.setOnAction(e -> {

            try {
                processOperation(filenameField.getText(), resultFileField.getText(), keyOrAnalysisField.getText());
                showAlert(Alert.AlertType.INFORMATION, "Success", "DONE");
                switchToPane(root, mainMenu);
            } catch (Exception exc) {
                showAlert(Alert.AlertType.ERROR, "Error", exc.getMessage());
            }
        });

        Scene scene = new Scene(root, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void switchToPane(StackPane root, Pane pane) {
        root.getChildren().clear();
        root.getChildren().add(pane);
    }

    private void processOperation(String inputFileName, String outputFileName, String keyOrAnalysisField) {
        switch (operation) {
            case ENCRYPTION -> processEncryptionOperation(inputFileName, outputFileName, keyOrAnalysisField);
            case DECRYPTION -> processDecryptionOperation(inputFileName, outputFileName, keyOrAnalysisField);
            case BRUTE_FORCE -> processBruteForceOperation(inputFileName, outputFileName, keyOrAnalysisField);
        }
    }

    private void processEncryptionOperation(String inputFileName, String outputFileName, String key) {

        int intKey = 0;

        try {
            intKey = Integer.parseInt(key);
        } catch (NumberFormatException exception) {
            throw new RuntimeException("Key \" " + key + " \" is not valid");
        }

        new CaesarCoder().encrypt(inputFileName, outputFileName, intKey);
    }

    private void processDecryptionOperation(String inputFileName, String outputFileName, String key) {
        int intKey = 0;

        try {
            intKey = Integer.parseInt(key);
        } catch (NumberFormatException exception) {
            throw new RuntimeException("Key \" " + key + " \" is not valid");
        }

        new CaesarCoder().decrypt(inputFileName, outputFileName, intKey);
    }

    private void processBruteForceOperation(String inputFileName, String outputFileName, String analysisFile) {
        new CaesarCoder().bruteForceDecrypt(inputFileName, outputFileName, analysisFile);
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
