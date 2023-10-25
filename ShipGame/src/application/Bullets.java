package application;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.Group;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class Bullets extends Canvas {
    private double x;
    private double y;
    private double velocityX;
    private double velocityY;
    private double width;
    private double height;
    private Group root;
    GraphicsContext gc;
    private Timeline animation;

    public Bullets(Group root, double x, double y, double width, double height) {
        super(10, 10); // Vytvoření plátna pro náboj
        this.root = root;
        this.x = x;
        this.y = y;
        this.width = width; 
        this.height = height;
        double angle = Math.toRadians(Math.random() * 360);
        double speed = 5; 

        velocityX = Math.cos(angle) * speed; 
        velocityY = Math.sin(angle) * speed;

        gc = getGraphicsContext2D();
        
        animation = new Timeline(
                new KeyFrame(Duration.millis(50), event -> pohyb())
            );
            animation.setCycleCount(Timeline.INDEFINITE); 
            animation.play();

           this.toFront();
           root.getChildren().add(this);
    }

    public void pohyb() {
        x += velocityX;
        y += velocityY; 

            setLayoutX(x);
            setLayoutY(y);
            gc.setFill(Color.RED);
            gc.fillOval(0, 0, 10, 10);
        
    }
    
    public void clear() {
    	animation.stop();
    	root.getChildren().remove(this); 
    }
    
    public double pozX() {
    	return x;
    }
    
    public double pozY() {
    	return y;
    }
}
