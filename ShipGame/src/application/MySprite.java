package application;



import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import java.util.ArrayList;



public class MySprite extends ImageView {
    private Image[] sprites;  // zoznam obrazkov
    private int smer = 0; // -1 - bez smeru, 0 - hore, 1 - dole, 2 - doprava, 3 - dolava
    private int actImage = 0;
    private double width, height;
    private double x;
    private double y;
    public String horizontalne;
    public String vertikalne;
    public ArrayList<Bullets> bullets = new ArrayList<>();;
    Group root;
    private Timeline animation;
    private boolean casovacBezi = false;

    public MySprite(String nazov, int pocetSpritov, double xx,
                    double yy, double w, double h, Group root) {
        super(); width = w; height = h;
        this.x = xx;
        this.y = yy;
        this.root = root;
        
        sprites = new Image[pocetSpritov];
        
        for(int i = 0; i < pocetSpritov; i++) {
            sprites[i] = new Image(nazov+i+".png", w, h, false, false);

        }
        
        
      //smer pohybu nepriatelov
        if (Math.random() > 0.5) 
            this.horizontalne = "doprava";
        else
        	this.horizontalne = "dolava";

        if (Math.random() > 0.5) 
        	this.vertikalne = "hore";
        else
        	this.vertikalne = "dole";
        setImage(sprites[0]);
        setLayoutX(xx);
        setLayoutY(yy); 
    }

    public double getWidth() {
        return width;
    }
    
    public void casovac() {
        if (!casovacBezi) {
            long randomValue = (long) (Math.random() * (5000 - 1000 + 1) + 1000);
            animation = new Timeline(
                new KeyFrame(Duration.millis(randomValue), event -> vystrel()) 
            );
            animation.setCycleCount(Timeline.INDEFINITE);
            animation.play();
            casovacBezi = true;
        } else {
            animation.stop();
            casovacBezi = false;
        }
    }
    
    
    //bullet
    public void vystrel() {
    	 Bullets bul = new Bullets(root, x, y, width, height);
         bullets.add(bul);
       	
    }
    
  

    public double getHeight() {
        return height;
    }

    public void hore(double delta, double maxy) {
        setLayoutY(getLayoutY() - delta);
        y = y - delta;
        if (getLayoutY() < 20) {setLayoutY(maxy - 20);
        y = maxy - 20;  };
        smer = 0;   vykresli();
    }

    public void dole(double delta, double maxy) {
        setLayoutY(getLayoutY() + delta);
        y = y + delta;
        if (getLayoutY() > maxy - 20) { setLayoutY(20);
        y = 20;}
        smer = 1;  vykresli();
    }

    public void dolava(double delta, double maxx) {
        setLayoutX(getLayoutX() - delta);
        x = x - delta;
        if (getLayoutX() < 20) { setLayoutX(maxx - 20);
        x = maxx - 20; }
        smer = 2;  vykresli();
    }

    public void doprava(double delta, double maxx) {
        setLayoutX(getLayoutX() + delta);
        x = x + delta;
        if (getLayoutX() > maxx - 20) { setLayoutX(20);
        x = 20;
        }
        smer = 3;  vykresli();
    }

    public boolean intersectsSprite(MySprite otherSprite) {
        double d1 = getLayoutX() - otherSprite.getLayoutX();
        double d2 = getLayoutY() - otherSprite.getLayoutY();
        if (((Math.abs(d1)<getWidth()) && Math.abs(d2)<getHeight()))
        return true;
           else
        return false;
    }
    
    public boolean intersectsBullet(Bullets otherSprite) {
        double d1 = getLayoutX() - otherSprite.getLayoutX();
        double d2 = getLayoutY() - otherSprite.getLayoutY();
        if (((Math.abs(d1)<getWidth()) && Math.abs(d2)<getHeight()))
        return true;
           else
        return false;
    }
       

    public void nextImage(){
        if (smer == 0) actImage = (actImage + 1) % 2;
        if (smer == 1) actImage = 2+(actImage + 1) % 2;
        if (smer == 2) actImage = 4+(actImage + 1) % 2;
        if (smer == 3) actImage = 6+(actImage + 1) % 2;
    }

    private void vykresli() {
        nextImage();
        setImage(sprites[actImage]);
    }
    
    public double r_x() {
    	return x;
    }

    public double r_y() {
    	return y;
    }

}
