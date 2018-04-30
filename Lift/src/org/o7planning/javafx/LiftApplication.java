package org.o7planning.javafx;

import application.Home;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
 
public class LiftApplication  extends Application {
    
  
    @Override
    public void start(Stage primaryStage) {
        try {
    
            FXMLLoader fxmlloader=new FXMLLoader(getClass().getResource("/MyScene.fxml"));
            Parent root = fxmlloader.load();
            MyController controller;
            controller=fxmlloader.getController();
            Home home=new Home();
            controller.addHome(home);
            primaryStage.setTitle("My Application");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
            
            controller.demonstrate();
         
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
}