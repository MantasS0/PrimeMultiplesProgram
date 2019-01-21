package com.mantassasnauskas.program;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/*Icons made by https://www.flaticon.com/authors/wanicon from https://www.flaticon.com/
is licensed by http://creativecommons.org/licenses/by/3.0/ Creative Commons BY 3.0*/

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("mainWindow.fxml"));
        try{primaryStage.getIcons().addAll(
                new Image("res/app_icon64x64.png"),
                new Image("res/app_icon32x32.png"),
                new Image("res/app_icon16x16.png")
        );}catch (Exception e){
       primaryStage.initStyle(StageStyle.UTILITY);
            System.out.println("Problem setting up icons. " + e);
        }
        primaryStage.setTitle("Piminių dauginamųjų programa");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
