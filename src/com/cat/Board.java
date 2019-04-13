package com.cat;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.scene.canvas.GraphicsContext;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Board {
    private double x = 0;
    private double y = 0;
    private GraphicsContext graphicsContext;
    private int count = 1;
    private static int activeNumber;
    private List<Shape> shapeList = new ArrayList<>();

    public Board(GraphicsContext graphicsContext) {
        this.graphicsContext = graphicsContext;
    }

    public static int getActiveNumber() {
        return activeNumber;
    }

    public void chooseNextElement() {
        if (activeNumber + 1 < count) {
            activeNumber++;
        } else {
            activeNumber = 1;
        }
    }

    public void choosePreviousElement() {
        if (activeNumber - 1 > 0) {
            activeNumber--;
        } else if (count - 1 > 0) {
            activeNumber = count - 1;
        }
    }

    public void addBall() {
        shapeList.add(new Ball(x, y, graphicsContext, 30, count));
        count++;
        activeNumber = count - 1;
    }

    public void addSquare() {
        shapeList.add(new Square(x, y, graphicsContext, 30, count));
        count++;
        activeNumber = count - 1;
    }

    public void addTriangle() {
        shapeList.add(new Triangle(x, y, graphicsContext, 30, count));
        count++;
        activeNumber = count - 1;
    }

    public void draw() {
        clean();
        for (Shape shape : shapeList) {
            shape.draw();
        }
    }

    public void moveRIGHT() {
        for (Shape shape : shapeList) {
            if (shape.getCount() == activeNumber) {
                shape.moveRIGHT();
            }
        }
    }

    public void moveLEFT() {
        for (Shape shape : shapeList) {
            if (shape.getCount() == activeNumber) {
                shape.moveLEFT();
            }
        }
    }

    public void moveUP() {
        for (Shape shape : shapeList) {
            if (shape.getCount() == activeNumber) {
                shape.moveUP();
            }
        }
    }

    public void moveDOWN() {
        for (Shape shape : shapeList) {
            if (shape.getCount() == activeNumber) {
                shape.moveDOWN();
            }
        }
    }

    public void increase() {
        for (Shape shape : shapeList) {
            if (shape.getCount() == activeNumber) {
                shape.increase();
            }
        }
    }

    public void decrease() {
        for (Shape shape : shapeList) {
            if (shape.getCount() == activeNumber) {
                shape.decrease();
            }
        }
    }

    public void deleteObject() {
        for (Shape shape : shapeList) {
            if (shape.getCount() == activeNumber) {
                shapeList.remove(shape);
                count--;
                int timeCount = 1;
                for (Shape shapeTime : shapeList) {
                    shapeTime.setCount(timeCount);
                    timeCount++;
                }
                break;
            }
        }
        activeNumber = count - 1;
    }

    public void clean() {
        graphicsContext.clearRect(0, 0, graphicsContext.getCanvas().getWidth(), graphicsContext.getCanvas().getHeight());
    }

    public void readObjectsFromFile() {
        Gson gson = new Gson();
        String jsonString = "";
        try {
            jsonString = FileUtils.readFileToString(new File("saveObjects.txt"), "UTF-8");
            System.out.println(jsonString);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!jsonString.isEmpty()) {
            ReadObjectsFromFile readObjectsFromFile = gson.fromJson(jsonString, ReadObjectsFromFile.class);
            shapeList = readObjectsFromFile.createListOfShapes(graphicsContext);
            activeNumber = readObjectsFromFile.getActiveObjectNumber();
            count = shapeList.size() + 1;
        }
    }

    public void saveObjectsListToFile() {
        SaveObjects saveObjects = new SaveObjects(activeNumber, shapeList);
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
                .setPrettyPrinting()
                .create();
        String json = gson.toJson(saveObjects);
        try {
            FileUtils.writeStringToFile(new File("saveObjects.txt"), json, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
