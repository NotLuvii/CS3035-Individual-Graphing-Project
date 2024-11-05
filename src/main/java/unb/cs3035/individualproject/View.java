package unb.cs3035.individualproject;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class View
{
    private Model model;
    private Stage stage;

    public Model.GraphWidget graphWidget;
    private TextField xInput;
    private TextField yInput;
    public Button addDataButton;
    public Button updateDataButton;
    public Button deleteDataButton;

    public View(Model model, Stage stage)
    {
        this.model = model;
        this.stage = stage;
        initializeUI();
    }

    private void initializeUI()
    {
        graphWidget = new Model.GraphWidget(model);
        graphWidget.setDataPointSelectedListener(this::handleDataPointSelected);

        xInput = new TextField();
        yInput = new TextField();
        addDataButton = new Button("Add Data Point");
        updateDataButton = new Button("Update Data Point");
        deleteDataButton = new Button("Delete Data Point");

        HBox inputBox = new HBox(10);
        inputBox.getChildren().addAll(xInput, yInput, addDataButton, updateDataButton, deleteDataButton);

        VBox layout = new VBox(10);
        layout.getChildren().addAll(createMenuBar(), graphWidget, inputBox);

        Button showStatisticsButton = new Button("Show Statistics");
        showStatisticsButton.setOnAction(e -> showStatistics());
        layout.getChildren().add(showStatisticsButton);

        Scene scene = new Scene(layout, 600, 400);
        stage.setScene(scene);
        stage.setTitle("Data Charting Tool");

    }

    private MenuBar createMenuBar()
    {
        MenuBar menuBar = new MenuBar();

        Menu fileMenu = new Menu("File");
        MenuItem exitMenuItem = new MenuItem("Exit");
        exitMenuItem.setOnAction(e -> System.exit(0));
        fileMenu.getItems().addAll(exitMenuItem);

        Menu helpMenu = new Menu("Help");
        MenuItem helpMenuItem = new MenuItem("Help");
        helpMenuItem.setOnAction(e -> showHelpDialog());
        MenuItem aboutMenuItem = new MenuItem("About");
        aboutMenuItem.setOnAction(e -> showAboutDialog());
        MenuItem helpScreenMenuItem = new MenuItem("Help Screen");
        helpScreenMenuItem.setOnAction(e -> showHelpScreen());
        helpMenu.getItems().addAll(helpMenuItem, aboutMenuItem, helpScreenMenuItem);

        menuBar.getMenus().addAll(fileMenu, helpMenu);

        return menuBar;
    }

    public TextField getXInput()
    {
        return xInput;
    }

    public TextField getYInput()
    {
        return yInput;
    }


    private void showHelpDialog()
    {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Help");
        alert.setHeaderText(null);
        alert.setContentText("This is a simple data charting tool.");
        alert.showAndWait();
    }

    private void showHelpScreen()
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Help Screen");
        alert.setHeaderText("UNB CS3035 Individual Project");
        alert.setContentText("This tool allows you to chart data points on a line chart.\n"
                + "Enter X and Y values and click 'Add Data Point' to add a point to the chart.\n" + "Click the point, and click Delete point to delete the point.\n" +
                "Enter X and Y values and click 'Update Data Point' and select the data point you want to update.");

        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.showAndWait();
    }

    private void showAboutDialog()
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText("CS3035 individual project");
        alert.setContentText("Developed by Luvneet Bamrah");

        ImageView logo = new ImageView(new Image("unb-1-logo-png-transparent.png"));
        logo.setFitWidth(100);
        logo.setPreserveRatio(true);

        alert.setGraphic(logo);

        alert.showAndWait();
    }

    private void handleDataPointSelected(Model.DataPoint dataPoint)
    {
        xInput.setText(String.valueOf(dataPoint.getX()));
        yInput.setText(String.valueOf(dataPoint.getY()));
    }

    private void showStatistics()
    {
        View.StatisticsView statisticsView = new View.StatisticsView(model);
        statisticsView.show();
    }

    public void showErrorMessage(String message)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public Stage getStage()
    {
        return stage;
    }

    public Model.GraphWidget getGraphWidget()
    {
        return graphWidget;
    }


public static class StatisticsView
{
        private Model model;
        private Stage stage;

        public StatisticsView(Model model)
        {
            this.model = model;
            initializeUI();
        }

        private void initializeUI()
        {
            stage = new Stage();
            stage.setTitle("Statistics View");

            VBox layout = new VBox(10);
            layout.setPadding(new Insets(10));

            Label countLabel = new Label("Count: " + model.getDataPoints().size());
            Label averageLabel = new Label("Average Y: " + calculateAverageY());
            Label averageLabel2 = new Label("Average X: " + calculateAverageX());
            Label minLabel = new Label("Min Y: " + calculateMinY());
            Label maxLabel = new Label("Max Y: " + calculateMaxY());

            layout.getChildren().addAll(countLabel, averageLabel,averageLabel2, minLabel, maxLabel);

            Scene scene = new Scene(layout, 250, 150);
            stage.setScene(scene);
        }

        private double calculateAverageY()
        {
            double sum = 0;
            for (Model.DataPoint dataPoint : model.getDataPoints())
            {
                sum += dataPoint.getY();
            }
            return model.getDataPoints().isEmpty() ? 0 : sum / model.getDataPoints().size();
        }

        private double calculateAverageX()
        {
            double sum = 0;
            for (Model.DataPoint dataPoint : model.getDataPoints())
            {
                sum += dataPoint.getX();
            }
            return model.getDataPoints().isEmpty() ? 0 : sum / model.getDataPoints().size();
        }

        private double calculateMinY()
        {
            return model.getDataPoints().stream().mapToDouble(Model.DataPoint::getY).min().orElse(0);
        }

        private double calculateMaxY()
        {
            return model.getDataPoints().stream().mapToDouble(Model.DataPoint::getY).max().orElse(0);
        }

        public void show()
        {
            stage.show();
        }
    }
}
