package model;

import javafx.geometry.Dimension2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

import java.lang.reflect.InvocationTargetException;


public abstract class StaticObjects {

    private double x, y;
    private javafx.geometry.Dimension2D dimension;
    private Pane layer;
    private ImageView imageView;
    private Image imageStatic;

    public StaticObjects(double x, double y, String pathStatic, Pane layer) {
        this.layer = layer;
        loadImage(pathStatic);
        setLocation(x, y);
        imageStatic = new Image(pathStatic);
        dimension = new javafx.geometry.Dimension2D(imageStatic.getWidth(), imageStatic.getHeight());
    }

    public void setLocation(double x, double y) {
        setX(x);
        setY(y);
        imageView.relocate(x, y);
    }


    private void loadImage(String path) {
        imageView = new ImageView(new Image(path));
        this.imageView.relocate(this.getX(), this.getY());
        this.addToLayer();
    }


    public void addToLayer() {
        this.layer.getChildren().add(this.imageView);
    }
    public void removeFromLayer() {
        this.layer.getChildren().remove(this.imageView);
    }

    public double getX() {
        return x;
    }
    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }
    public void setY(double y) {
        this.y = y;
    }

    public Pane getLayer() {
        return layer;
    }
    public void setLayer(Pane layer) {
        this.layer = layer;
    }

    public ImageView getImageView() {
        return imageView;
    }
    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public javafx.geometry.Dimension2D getDimension() {
        return dimension;
    }
    public void setDimension(Dimension2D dimension) {
        this.dimension = dimension;
    }

    public Rectangle getBounds() { return new Rectangle((int) x, (int) y, dimension.getWidth(), dimension.getHeight()); }

    public Rectangle getUpBounds() {
        Rectangle topBounds = new Rectangle();
        topBounds.setX(x);
        topBounds.setY(y);
        topBounds.setWidth(dimension.getWidth());
        topBounds.setHeight(dimension.getHeight()/4);
        topBounds.setArcHeight(dimension.getHeight()/8);
        topBounds.setArcWidth(dimension.getHeight()/8);

        return topBounds;
    }
    public Rectangle getDownBounds() {
        Rectangle downBounds = new Rectangle();
        downBounds.setX(x);
        downBounds.setY(y + dimension.getHeight());
        downBounds.setWidth(dimension.getWidth());
        downBounds.setHeight(dimension.getHeight()/4);
        downBounds.setArcHeight(dimension.getHeight()/8);
        downBounds.setArcWidth(dimension.getHeight()/8);

        return downBounds;
    }
    public Rectangle getLeftBounds() {
        Rectangle leftBounds = new Rectangle();
        leftBounds.setX(x);
        leftBounds.setY(y);
        leftBounds.setWidth(dimension.getWidth()/4);
        leftBounds.setHeight(dimension.getHeight());
        leftBounds.setArcHeight(dimension.getHeight()/8);
        leftBounds.setArcWidth(dimension.getHeight()/8);

        return leftBounds;
    }
    public Rectangle getRightBounds() {
        Rectangle rightBounds = new Rectangle();
        rightBounds.setX(x + dimension.getHeight());
        rightBounds.setY(y);
        rightBounds.setWidth(dimension.getWidth()/4);
        rightBounds.setHeight(dimension.getHeight());
        rightBounds.setArcHeight(dimension.getHeight()/8);
        rightBounds.setArcWidth(dimension.getHeight()/8);

        return rightBounds;
    }

    public Image getImageStatic() {
        return imageStatic;
    }
    public void setImageStatic(Image imageStatic) {
        this.imageStatic = imageStatic;
    }


}
