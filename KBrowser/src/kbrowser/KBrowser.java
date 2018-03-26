/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kbrowser;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/**
 *
 * @author Imo Koßmann
 */
public class KBrowser extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        
        //Web
        WebView wv = new WebView();
        //###
        
        //Textfields
        TextField txt_url = new TextField();
        //###
        
        //Buttons
        Button btn_home = new Button("Home");
        Button btn_goto = new Button("Goto");
        Button btn_reload = new Button("Reload");
        
        btn_goto.setDefaultButton(true);
        
        
        btn_home.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                System.out.println("[Debug] Home Btn pressed!");
                wv.getEngine().load("https://www.duckduckgo.com");
                txt_url.setText(wv.getEngine().getLocation());
            }
        });
        btn_goto.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                System.out.println("[Debug] goto Url of textfield: " + txt_url.getText());
                if(!(txt_url.getText().contains("http://") || txt_url.getText().contains("https://"))) {
                    txt_url.setText("http://" + txt_url.getText());
                }
                try {
                    URL url = new URL(txt_url.getText());
                    wv.getEngine().load(url.toString());
                    txt_url.setText(wv.getEngine().getLocation());
                } catch(Exception e) {
                    wv.getEngine().loadContent("<h1> You fucked up mate!</h1>", "text/html");
                    
                }
                
            }
        });
        btn_reload.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                System.out.println("[Debug] goto Url of textfield: " + txt_url.getText());
                wv.getEngine().reload();
                txt_url.setText(wv.getEngine().getLocation());
            }
        });
        //###
        
        //Window Content
        HBox topbar = new HBox(10);
        VBox contentbox = new VBox();
        VBox root = new VBox();
        
        topbar.getChildren().add(btn_home);
        topbar.getChildren().add(btn_reload);
        topbar.getChildren().add(txt_url);
        topbar.getChildren().add(btn_goto);
        contentbox.getChildren().add(wv);
        root.getChildren().add(topbar);
        root.getChildren().add(contentbox);
        
        root.setMargin(topbar, new Insets(10));
        root.setMargin(contentbox, new Insets(10));
        //##
        
        //Scene Creation
        Scene scene = new Scene(root, 800, 600);
        
        //Formating
        txt_url.setPrefWidth(Integer.MAX_VALUE);
        btn_goto.setMinWidth(Control.USE_PREF_SIZE);
        btn_reload.setMinWidth(Control.USE_PREF_SIZE);
        btn_home.setMinWidth(Control.USE_PREF_SIZE);
        
        wv.setPrefSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
        
        //###
        
        primaryStage.setTitle("KBrowser - A Java Webbrowser by MrKimo");
        primaryStage.setScene(scene);
        primaryStage.show();
        //##
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
