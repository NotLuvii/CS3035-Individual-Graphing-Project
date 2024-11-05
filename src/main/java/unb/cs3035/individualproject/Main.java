package unb.cs3035.individualproject;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class Main extends Application
{
    private Stage splashStage;

    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage)
    {
        Platform.runLater(() ->
        {
            splashStage = new Stage();
            splashStage.initStyle(StageStyle.UNDECORATED);

            StackPane splashLayout = new StackPane();
            splashLayout.setStyle("-fx-background-color: white;");
            splashLayout.getChildren().addAll(new Label("Loading..."));

            Scene splashScene = new Scene(splashLayout, 200, 100);
            splashStage.setScene(splashScene);

            splashStage.show();
        });
        simulateInit();
        Model model = new Model();
        Controller controller = new Controller(model, primaryStage);
        primaryStage.show();;

        closeSplash();
    }

    private void simulateInit()
    {
        try
        {
            Thread.sleep(3000);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    private void closeSplash()
    {

        Platform.runLater(() ->
        {
            splashStage.close();
        });
    }
}
