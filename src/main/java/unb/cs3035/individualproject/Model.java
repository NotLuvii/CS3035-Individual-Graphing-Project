package unb.cs3035.individualproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class Model
{
    private ObservableList<DataPoint> dataPoints;

    public Model()
    {
        dataPoints = FXCollections.observableArrayList();
    }

    public ObservableList<DataPoint> getDataPoints()
    {
        return dataPoints;
    }

    public void addDataPoint(DataPoint dataPoint)
    {
        dataPoints.add(dataPoint);
    }

    public void updateDataPoint(Model.DataPoint oldDataPoint, Model.DataPoint newDataPoint)
    {
        int index = dataPoints.indexOf(oldDataPoint);
        System.out.println("Change Index: " + index);
        if (index != -1)
        {
            dataPoints.set(index, newDataPoint);
        }
    }
    public void removeDataPoint(DataPoint dataPoint)
    {
        int index = dataPoints.indexOf(dataPoint);
        System.out.println("Delete Index: " + index);
        if (index != -1)
        {
            dataPoints.remove(dataPoint);
        }
    }


    public static class DataPoint
    {
        private double x;
        private double y;

        public DataPoint(double x, double y)
        {
            this.x = x;
            this.y = y;
        }

        public double getX()
        {
            return x;
        }

        public double getY()
        {
            return y;
        }

        public boolean equals(Object obj)
        {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            DataPoint dataPoint = (DataPoint) obj;
            return Double.compare(dataPoint.x, x) == 0 && Double.compare(dataPoint.y, y) == 0;
        }

    }

    public static class GraphWidget extends LineChart<Number, Number>
    {
        private Model model;
        private DataPoint selectedDataPoint;
        private DataPointSelectedListener dataPointSelectedListener;

        public GraphWidget(Model model)
        {
            super(new NumberAxis(), new NumberAxis());
            this.model = model;
            updateChart();

            setCreateSymbols(true);
            setLegendVisible(false);


            setOnMouseClicked(event -> handleDataPointSelection(event.getX(), event.getY()));
        }

        public void updateChart()
        {
            getData().clear();

            XYChart.Series<Number, Number> series = new XYChart.Series<>();
            for (Model.DataPoint dataPoint : model.getDataPoints())
            {
                series.getData().add(new XYChart.Data<>(dataPoint.getX(), dataPoint.getY()));
            }

            getData().add(series);
        }

        private void handleDataPointSelection(double mouseX, double mouseY)
        {
            ObservableList<XYChart.Data<Number, Number>> data = ((XYChart.Series<Number, Number>) getData().get(0)).getData();
            System.out.println("Clicked at: (" + mouseX + ", " + mouseY + ")");
            double distanceThreshold = 6;

            for (XYChart.Data<Number, Number> dataPoint : data)
            {
                double xPos = getXAxis().getDisplayPosition(dataPoint.getXValue());
                double yPos = getYAxis().getDisplayPosition(dataPoint.getYValue());


                Point2D scenePoint = localToScene(xPos, yPos);

                System.out.println("Data point at: (" + scenePoint.getX() + ", " + scenePoint.getY() + ")");

                double distance = Math.sqrt(Math.pow(mouseX - scenePoint.getX(), 2) + Math.pow(mouseY - scenePoint.getY(), 2));

                if (distance <= distanceThreshold)
                {

                    selectedDataPoint = new DataPoint(dataPoint.getXValue().doubleValue(), dataPoint.getYValue().doubleValue());


                    if (dataPointSelectedListener != null)
                    {
                        dataPointSelectedListener.onDataPointSelected(selectedDataPoint);
                    }

                    break;
                }
            }
        }



        public DataPoint getSelectedDataPoint()
        {
            return selectedDataPoint;
        }

        public void setDataPointSelectedListener(DataPointSelectedListener listener)
        {
            this.dataPointSelectedListener = listener;
        }

        public interface DataPointSelectedListener
        {
            void onDataPointSelected(Model.DataPoint dataPoint);
        }

    }

}
