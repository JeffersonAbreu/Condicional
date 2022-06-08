import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class App extends Application {

    Label caption = new Label("We");

    @Override
    public void start(Stage stage) {
        Group root = new Group();
        Scene scene = new Scene(root, 500, 200);
        stage.setScene(scene);

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(2);
        grid.setHgap(5);

        scene.setRoot(grid);

        caption.setFont(Font.font("Verdana", 20));

        GridPane.setConstraints(caption, 0, 0);
        GridPane.setColumnSpan(caption, 8);
        grid.getChildren().add(caption);

        final Separator sepHor = new Separator();
        sepHor.setValignment(VPos.CENTER);
        GridPane.setConstraints(sepHor, 0, 1);
        GridPane.setColumnSpan(sepHor, 7);
        grid.getChildren().add(sepHor);

        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}