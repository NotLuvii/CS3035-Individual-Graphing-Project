# CS3035 – Course Project Description

## Description of Your Project

This project focuses on Data Charting. It is a simple tool implemented using JavaFX, which allows the user to add, update and delete points on a line chart.
The project follows MVC Architecture. I chose this project because I have an interest in statistics and graphing, I have also worked on another project where
I was in charge of the graphing, so I have a keen interest in this topic.
Features:
1. Users can input X and Y values and add them to the chart.
2. Selected data points can be updated or deleted.
3. Users can view basic statistics about the data set, such as count, average Y value, average X value, minimum Y value, and maximum Y value.

Behind the Scenes:
1. Model: Manages the data points and provides methods to add, update, and delete them.
2. View: Displays the line chart, input fields, buttons, and statistics. It uses JavaFX components for the user interface.
3. Handles user input, communicates with the model, and updates the view accordingly.

## Requirements

- How/What different views did you provide for some aspect of your model?
There is only one main view, but there are several "branch" views. Some notable ones include the statistics view, which displays the count, average X and Y values and minimum and maximum Y values.
There is a help view which displays what this project does.
There is a help screen which goes into more detail on what the project is doing.
There is an about view, which is responsible for showing the credits.


- What custom widget did you create in your application?
The custom widget I used is the graph. It is implemented in the View class

- What are the different domain objects that can be created/edited in
  your application?
Only the data points can be created/edited and deleted in the application

- What parts of the application/project did you find particularly challenging?
  And, what would you have liked to improve?
One part I found challenging was the selection of the points. I still haven't figured it out, as you can see in the demo. The functionality is present, only the selection can be awkward at times.

- Any  other comments on the course project?
N/A

- Provide a link to your video that is easily viewable.
  https://youtu.be/qHmMMeWLiNo
If this link does not work, please email me at lbamrah@unb.ca




