package game.core;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
/**
 * Created by LiZhen on 13-12-19.
 */
/*
是游戏用到所有Application的基类
继承了Application
*/
public abstract class WApplication extends Application {
    private Group mGroup;
    private Scene mScene;
    @Override
    public void start(Stage primaryStage) throws Exception {
        loadBefore();
        mGroup= new Group();
        mScene = new Scene(mGroup, WSystem.WIDTH, WSystem.HEIGHT);
        loadEnd();
        showStage(primaryStage);
    }

    protected abstract void loadBefore();

    protected abstract void loadEnd();

    protected void showStage(Stage stage){
        stage.setScene(mScene);
        stage.show();
    }

    protected Scene getScene(){
        return mScene;
    }

    protected Group getRoot(){
        return mGroup;
    }

    public void setWindowSize(int width, int height){
        WSystem.init(width, height);
    }

}
