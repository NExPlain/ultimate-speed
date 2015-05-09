package game.Ulitimate_Speed;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import game.core.WApplication;
import game.core.WSystem;
import javafx.stage.WindowEvent;

/**
 * Created by LiZhen on 13-12-19.
主游戏类
 */

public class MainGame extends WApplication {
    private Stage myStage = null;
    private Image backGroundImage = null;
    private MainGameScreen mainGameScreen = null;
    private ScrollPane scroll = null;
    private Image hpImage = null;

    @Override
    protected void loadBefore() {//载入资源设置参数
        //setWindowSize(800, 600);
        setWindowSize(2048,1600);
        backGroundImage = new Image(getClass().getResourceAsStream("content/BackGround.png"));
        hpImage = new Image(getClass().getResourceAsStream("content/hp.png"));
    }

    protected MainGameScreen initTS(){//载入MainGameScreen
        mainGameScreen = new MainGameScreen(WSystem.WIDTH , WSystem.HEIGHT,myStage);
        getRoot().getChildren().add(mainGameScreen);
        mainGameScreen.start();
        mainGameScreen.initEvents();
        return mainGameScreen;
    }

    @Override
    protected void loadEnd() {
        /*
        mainGameScreen = new MainGameScreen(WSystem.WIDTH * 3 / 2, WSystem.HEIGHT * 3 / 2);
        getRoot().getChildren().add(mainGameScreen);
        mainGameScreen.start();
        mainGameScreen.initEvents();
        */
        getScene().setFill(Color.BLACK);
    }

    @Override
    protected void showStage(Stage stage){//将需要显示的游戏元素用stage显示出来
        myStage = stage;
        stage.setFullScreen(true);
        super.showStage(stage);

        StackPane layout = new StackPane();

        // wrap the scene contents in a pannable scroll pane.
        scroll = createScrollPane(layout);
        // show the scene.
        Scene scene = new Scene(scroll);
        stage.setScene(scene);

        layout.setMouseTransparent(true);
        ImageView hpImageView = new ImageView(hpImage);
        hpImageView.setFitWidth(30);
        hpImageView.setFitHeight(30);
        layout.getChildren().setAll(
                new ImageView(backGroundImage),
                initTS()
        );

        ((MainGameScreen)(layout.getChildren().get(1))).initEvents();//启动MainGameScreen的事件响应机制
        // bind the preferred size of the scroll area to the size of the scene.
        scroll.prefWidthProperty().bind(scene.widthProperty());
        scroll.prefHeightProperty().bind(scene.widthProperty());

        // center the scroll contents.
        scroll.setHvalue(scroll.getHmin() + (scroll.getHmax() - scroll.getHmin()) / 2);
        scroll.setVvalue(scroll.getVmin() + (scroll.getVmax() - scroll.getVmin()) / 2);

        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                mainGameScreen.closeMusic();
            }
        });

        stage.setTitle("SpaceMoto - Ultimate Speed");
    }

    /** @return a ScrollPane which scrolls the layout. */
    private ScrollPane createScrollPane(Pane layout) {
        ScrollPane scroll = new ScrollPane();
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll.setPannable(true);;
        scroll.setPrefSize(2048, 1600);
        scroll.setContent(layout);
        return scroll;
    }
/*
    public static void main(String[] args) {
        launch(args);
    }
*/
}