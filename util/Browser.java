package util;

import javafx.scene.control.TextField;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;


/**
 *
 * @author Nikhil
 */
public final class Browser extends Application {

    private WebEngine engine;
    private String curr_url = "https://www.google.co.in";
    private TextField url;
    final int WIDTH = 800;
    final int HEIGHT = 600;
    
    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox();
        HBox top = new HBox();
        
        url = new TextField(curr_url);
        Button go_btn = new Button("GO!");
        go_btn.setOnAction(e -> {
            curr_url = url.getText();
            engine.load(curr_url);
            primaryStage.setTitle("JShell WEB - " + engine.getTitle() + " " + engine.getLocation());
        });
        
        top.setHgrow(url, Priority.ALWAYS);
        top.getChildren().addAll(url, go_btn);
        root.getChildren().add(top);
        
        WebView webView = new WebView();
        root.getChildren().add(webView);
        root.setVgrow(webView, Priority.ALWAYS);
        
        Scene scene = new Scene(root, WIDTH, HEIGHT);
        engine = webView.getEngine();
        engine.load(curr_url);
        
        primaryStage.setTitle("JShell WEB - " + engine.getTitle() + " " + engine.getLocation());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void run(String start) {
        if(start != null || !"null".equals(start)) this.curr_url = start;
        launch((String[]) null);
    }
}
