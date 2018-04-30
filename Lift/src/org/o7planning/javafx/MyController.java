package org.o7planning.javafx;
 
import java.net.URL;
import java.util.ResourceBundle;

import application.Globals;
import application.Home;
import javafx.animation.AnimationTimer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.paint.Color;
 
public class MyController implements Initializable {
 
	ObservableList list= FXCollections.observableArrayList();
   @FXML
   private ChoiceBox<Integer> chBox1;
   @FXML
   private ChoiceBox<Integer> chBox2;
   @FXML
   private CheckBox autoAdd;
   
   private Home home;
   
   @FXML
   private Canvas canvas;
   
   private GraphicsContext grp;
   
   @Override
   public void initialize(URL location, ResourceBundle resources) {
	   loadData();
	   autoAdd.setOnAction(e->autoAdded(e));
	   grp=canvas.getGraphicsContext2D();
   }       
   
   private void autoAdded(ActionEvent event)
   {
	   if (autoAdd.isSelected())
	   {
		   home.autoAdded(true);
	   }
	   else
	   {
		   home.autoAdded(false);
	   }
   }
   
   private void loadData()
   {
	   list.removeAll(list);
	   int max=(new Globals()).maxFloor;
	   for (int i=1;i<=max;i++)
	   {
		   list.add(i);
	   }
	   chBox1.getItems().addAll(list);
	   chBox2.getItems().addAll(list);
   }
   
   public void addPerson(ActionEvent event)
   {
	   home.addPerson(chBox1.getValue(),chBox2.getValue());
   }
   
   public void addHome(Home home)
   {
	   this.home=home;
   }
   
   public void update() 
   {
       grp.fillRect(0, 0, Globals.windowWidth, Globals.windowHeight);
       grp.setFill(Color.LIGHTSKYBLUE);
       
       if (home!=null)
       {
	       home.movePeople();
	       home.draw(grp);
       }
   }
   
   
   public void demonstrate()
   {
	   AnimationTimer animator=new AnimationTimer(){

           private long lastUpdate = 0 ;
           @Override
           public void handle(long now) {
                   if (now - lastUpdate >= 28_000_000) {
                	   update();
                       lastUpdate = now ;
                   }
           }
       };
       animator.start();
   }
}