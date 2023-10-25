package application;


import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class Game extends Group {
    final int SPRITESIZE = 30;     // veľkosť obrázka
    final int HEROSPEED = 250;      // rýchlosť hráča
    final int ENEMYSPEED = 5;      // rýchlosť nepriateľa
    private ImageView background;  // obrázok pozadia
    private ArrayList<MySprite> enemies;
    private MySprite hero;
    private String input = ""; // stlaceny klaves
    private double width, height;
    private int total_enemiesl;
    Text counterText;
    Text live;
    Group root;
    static int score = 0;
    static int lives = 5;
    static boolean end = false;
    
    
    public Game(int w, int h, String pozadie, int enem, Group root) {
        width = w;
        this.root = root;
        height = h;
        this.total_enemiesl = enem;
        
        
        Image bg = new Image(pozadie, w, h, false, false);
        background = new ImageView(bg);
  
        setOnKeyPressed(evt -> input = evt.getCode().toString());

        counterText = new Text("score: " + score);
        live = new Text("Life: " + lives);
        counterText.setLayoutX(10);
        counterText.setLayoutY(30);
        live.setLayoutX(10);
        live.setLayoutY(50);
        counterText.toFront();
        root.getChildren().addAll(background,counterText, live);
        
        enemies = new ArrayList<>();
        for (int i = 0; i < enem; i++) {
        	
            MySprite ms = new MySprite("images/monster",  
                    8,          
                    Math.random() * w, 
                    Math.random() * h, 
                    SPRITESIZE,     
                    SPRITESIZE, root);       
            enemies.add(ms);     
            ms.casovac();
            getChildren().add(ms);
        }
        hero = new MySprite("images/ship", 8, Math.random() * w,
                Math.random() * h, SPRITESIZE, SPRITESIZE, root);
        getChildren().add(hero); 

        setFocusTraversable(true);
        setFocused(true); 
    }

    public void update(double deltaTime) {
        switch (input) { 
            case "LEFT":
                hero.dolava(deltaTime / 1000000000 * HEROSPEED, width);
                break;
            case "UP":
                hero.hore(deltaTime / 1000000000 * HEROSPEED, height);
                break;
            case "RIGHT":
                hero.doprava(deltaTime / 1000000000 * HEROSPEED, width);
                break;
            case "DOWN":
                hero.dole(deltaTime / 1000000000 * HEROSPEED, height);
                break;
        }



        // zmena polohy Enemies – pohnem všetkými naraz
        for (int i = 0; i < enemies.size(); i++) {
            MySprite enemy = enemies.get(i); 
            if (enemy.horizontalne.equals("doprava")) 
                enemy.doprava(deltaTime / 1000000000 * ENEMYSPEED, width);
            else
                enemy.dolava(deltaTime / 1000000000 * ENEMYSPEED, width);

            if (enemy.horizontalne.equals("hore")) 
                enemy.hore(deltaTime / 1000000000 * ENEMYSPEED, height);
            else
                enemy.dole(deltaTime / 1000000000 * ENEMYSPEED, height);
            
           if (enemy.bullets != null) {
           for (int j = 0; j < enemy.bullets.size(); j++) {
            	if (hero.intersectsBullet(enemy.bullets.get(j))) { 
            		            		
            		enemy.bullets.get(j).clear();
            		
                    getChildren().remove(enemy.bullets.get(j)); 
                    enemy.bullets.remove(enemy.bullets.get(j));                    
                    
                    lives--;                   
                    
                    if(lives == 0) {
                		end = true;
                	    root.getChildren().clear();
                	    
                	    for (int i1 = enemies.size() - 1; i1 >= 0; i1--) {
                	    getChildren().remove(enemies.get(i1)); 
                        enemies.get(i1).casovac();
                        enemies.remove(enemies.get(i1)); 
                	    }

                	    Text gameOverText = new Text("You died :(");
                	    
                	    Button button = new Button("Ukončiť hru");
                	    button.setOnAction(e -> {
                	        System.exit(0);
                	    });

                	    gameOverText.setFill(Color.RED);
                	    gameOverText.setLayoutX(width / 2 - 100); 
                	    gameOverText.setLayoutY(height / 2); 
                	    
                	    root.getChildren().addAll(gameOverText, button); 
                	    
                }

                    live.setText("Life: " + Integer.toString(lives));
                }
            }
           
           } //if (enemy.bullets != null) 
           
 
        } 

     // identifikuj kolizie a zareaguj podla nich
        for (int i = enemies.size() - 1; i >= 0; i--) {
            if (hero.intersectsSprite(enemies.get(i))) {  
                getChildren().remove(enemies.get(i)); 
                enemies.get(i).casovac();
                enemies.remove(enemies.get(i)); 
                
                total_enemiesl--;
                if(total_enemiesl == 0) {
            		end = true;
            	    root.getChildren().clear();
            	    Text gameOverText = new Text("You win!");
            	    
            	    Button button = new Button("Ukončiť hru");
            	    button.setOnAction(e -> {
            	        System.exit(0);
            	    });

            	    gameOverText.setFill(Color.RED);
            	    gameOverText.setLayoutX(width / 2 - 100); 
            	    gameOverText.setLayoutY(height / 2); 
            	    root.getChildren().addAll(gameOverText, button); 
            	    
            }

                score++; 
                counterText.setText("score: " + Integer.toString(score));
            }
        }

    }

}
