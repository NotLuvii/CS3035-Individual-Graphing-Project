package unb.cs3035.individualproject;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.util.Optional;

public class Controller
{
    private Model model;

    private View view;

    public Controller(Model model, Stage stage)
    {
        this.model = model;
        this.view = new View(model, stage);
        view.addDataButton.setOnAction(e -> handleAddDataPoint());
        view.updateDataButton.setOnAction(e -> handleUpdateDataPoint());
        view.deleteDataButton.setOnAction(e -> handleDeleteDataPoint());
        view.getGraphWidget().setDataPointSelectedListener(this::handleDataPointSelected);
    }

    private void handleAddDataPoint()
    {
        try
        {
            double x = Double.parseDouble(view.getXInput().getText());
            double y = Double.parseDouble(view.getYInput().getText());
            System.out.println("Added at x :" + x + " Added at y: " + y);
            model.addDataPoint(new Model.DataPoint(x, y));
            view.getGraphWidget().updateChart();
        }
        catch (NumberFormatException e)
        {
            view.showErrorMessage("Invalid input. Please enter numeric values for X and Y.");
        }
    }

    private void handleUpdateDataPoint()
    {
        try
        {
            double x = Double.parseDouble(view.getXInput().getText());
            double y = Double.parseDouble(view.getYInput().getText());
            System.out.println("New X:" + x + " New Y: " + y);
            Model.DataPoint selectedDataPoint = view.getGraphWidget().getSelectedDataPoint();
            if (selectedDataPoint != null)
            {
                Model.DataPoint newDataPoint = new Model.DataPoint(x, y);
                model.updateDataPoint(selectedDataPoint, newDataPoint);
                view.getGraphWidget().updateChart();
            }
            else
            {
                view.showErrorMessage("Please select a data point to update.");
            }
        }
        catch (NumberFormatException e)
        {
            view.showErrorMessage("Invalid input. Please enter numeric values for X and Y.");
        }
    }

    private void handleDeleteDataPoint()
    {
        Model.DataPoint selectedDataPoint = view.getGraphWidget().getSelectedDataPoint();
        if (selectedDataPoint != null)
        {
            Alert confirmDelete = new Alert(Alert.AlertType.CONFIRMATION);
            confirmDelete.setTitle("Confirm Deletion");
            confirmDelete.setHeaderText("Are you sure you want to delete this data point?");

            double x = selectedDataPoint.getX();
            double y = selectedDataPoint.getY();
            System.out.println("Deleted X:" + x + " Deleted Y: " + y);
            Optional<ButtonType> result = confirmDelete.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK)
            {
                model.removeDataPoint(selectedDataPoint);
                view.getGraphWidget().updateChart();
            }
        }
        else
        {
            view.showErrorMessage("Please select a data point to delete.");
        }
    }

    private void handleDataPointSelected(Model.DataPoint dataPoint)
    {
        view.getXInput().setText(String.valueOf(dataPoint.getX()));
        view.getYInput().setText(String.valueOf(dataPoint.getY()));
    }
}
