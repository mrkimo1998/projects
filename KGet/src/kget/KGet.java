/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kget;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Path;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Imo Koßmann
 */
public class KGet extends Application implements EventHandler<ActionEvent> {

    Button btn_close = new Button("Close");
    Button btn_dl = new Button("Download");
    Label l_dl = new Label("URL: ");
    Label l_dest = new Label("Destination Folder: ");
    TextField t_dl = new TextField();
    TextField t_dest = new TextField();
    ProgressBar p_dl = new ProgressBar();

    VBox layout = new VBox();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("KGet");

        btn_close.setOnAction(this);
        btn_dl.setOnAction(this);

        layout.setFillWidth(true);
        layout.setSpacing(5);
        layout.setAlignment(Pos.CENTER);
        layout.setPrefWidth(500);
        layout.getChildren().addAll(l_dl, t_dl, l_dest, t_dest, p_dl, btn_dl, btn_close);

        Scene scene = new Scene(layout);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void handle(ActionEvent event) {
        if (event.getSource() == btn_close) {
            System.exit(0);
        }
        if (event.getSource() == btn_dl) {
            download(t_dl.getText());
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    private void download(String url) {
        //SOURCE    https://alvinalexander.com/blog/post/java/jget-something-like-wget
        //          https://www.mkyong.com/java/how-to-convert-inputstream-to-file-in-java/

        URL u;
        InputStream is = null;
        OutputStream out = null;
        DataInputStream dis;
        String s;

        try {
            u = new URL(url);
            is = u.openStream();
            dis = new DataInputStream(new BufferedInputStream(is));

            String[] parts = url.split("/");

            out = new FileOutputStream(new File(Paths.get(t_dest.getText() + "/" + parts[parts.length - 1]).toString()));

            int read = 0;
            byte[] bytes = new byte[1024];
            while ((read = dis.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
        } catch (MalformedURLException mue) {
            System.err.println("Ouch - a MalformedURLException happened.");
            mue.printStackTrace();
            System.exit(2);
        } catch (IOException ioe) {
            System.err.println("Oops- an IOException happened.");
            ioe.printStackTrace();
            System.exit(3);
        } finally {
            try {
                out.close();
                is.close();
            } catch (IOException ioe) {
            }
        }
    }

}
