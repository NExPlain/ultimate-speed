package game.Ulitimate_Speed;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/*
游戏结束画面的Panel
在游戏结束时候展现
*/
public class GameOverPanel extends Application{
    private Image backgroundImage;
    private Image button;
    private Image button2;
    private StackPane layout;
    private ScrollPane scroll;
    private Stage myStage;
    private MainGameScreen.GameStatus gameStatus;//游戏状态，以显示游戏结束的状态
    private SoundManager soundBank;//控制声音播放

    public GameOverPanel(MainGameScreen.GameStatus gameStatus){
        super();
        this.gameStatus = gameStatus;
    }

    //变量初始化
    @Override public void init(){
        soundBank = new SoundManager(3);
        backgroundImage=new Image(getClass().getResourceAsStream("content/3.jpg"));
        button=new Image(getClass().getResourceAsStream("content/Button.gif"));
        button2=new Image(getClass().getResourceAsStream("content/ButtonWithMouse.gif"));
        soundBank.loadSoundEffects("MenuMusic","content/sound/MenuMusic.wav");
    }

    //建立GameOverPanel面板并添加组件
    @Override public void start(Stage stage){
        myStage = stage;
        init();
        stage.setFullScreen(true);
        stage.setTitle("MenuPanel");
        layout=new StackPane();
        layout.getChildren().setAll(new ImageView(backgroundImage), createPlayButton());
        layout.getChildren().addAll(createReturnButton(),createExitButton(),createInfoText());

        scroll = createScrollPane(layout);
        Scene scene=new Scene(scroll);
        stage.setScene(scene);
        stage.show();
        // bind the preferred size of the scroll area to the size of the scene.
        scroll.prefWidthProperty().bind(scene.widthProperty());
        scroll.prefHeightProperty().bind(scene.widthProperty());

        // center the scroll contents.
        scroll.setHvalue(scroll.getHmin() + (scroll.getHmax() - scroll.getHmin()) / 2);
        scroll.setVvalue(scroll.getVmin() + (scroll.getVmax() - scroll.getVmin()) / 2);
        soundBank.playSound("MenuMusic",MenuPanel.isSoundOn);
    }

    //创建胜负信息文本
    private Text createInfoText(){
        final Text infoText=new Text();
        infoText.setTranslateX(0);
        infoText.setTranslateY(-50);
        if(gameStatus == MainGameScreen.GameStatus.blueWins){
            infoText.setFill(Color.BLUE);
            infoText.setText("蓝方获胜！\n");
        }
        if(gameStatus == MainGameScreen.GameStatus.redWins){
            infoText.setFill(Color.ORANGE);
            infoText.setText("红方获胜！\n");
        }
        if(gameStatus == MainGameScreen.GameStatus.Duece){
            infoText.setFill(Color.BLUE);
            infoText.setText("       平局！     \n再战500回合吧！");
        }
        infoText.setStyle("-fx-font: 30 arial;-fx-font-weight: bolder");


        return infoText;
    }

    //创建再玩一局按钮及其事件响应
    private Button createPlayButton() {
        final Button playButton = new Button("再玩一局");
        playButton.setGraphic(new ImageView(button));
        playButton.setGraphicTextGap(-115);
        playButton.setStyle("-fx-background-color:transparent;-fx-text-fill:#FFFFCC;-fx-font:20 arial;");
        playButton.setPrefSize(200, 60);
        playButton.setTranslateX(-20);
        playButton.setTranslateY(180);
        playButton.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                playButton.setGraphic(new ImageView(button2));
            }
        });
        playButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                playButton.setGraphic(new ImageView(button));
            }
        });
        playButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                soundBank.soundEffectsMap.get("MenuMusic").stop();
                if (MenuPanel.gameMode == MenuPanel.GameMode.vsCom) {  //人机对战
                    MainGame m = new MainGame();
                    try {
                        m.start(myStage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {                               //玩家对战
                    MainGame m = new MainGame();
                    try {
                        m.start(myStage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        });
        return playButton;
    }

    //创建回主菜单按钮及其事件响应
    private Button createReturnButton() {
        final Button returnButton = new Button("回主菜单");
        returnButton.setGraphic(new ImageView(button));
        returnButton.setGraphicTextGap(-115);
        returnButton.setStyle("-fx-background-color:transparent;-fx-text-fill:#FFFFCC;-fx-font:20 arial;");
        returnButton.setPrefSize(200,60);
        returnButton.setTranslateX(-20);
        returnButton.setTranslateY(260);
        returnButton.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                returnButton.setGraphic(new ImageView(button2));
            }
        });
        returnButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                returnButton.setGraphic(new ImageView(button));
            }
        });
        returnButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent t) {
                MenuPanel p = new MenuPanel();
                try {
                    soundBank.soundEffectsMap.get("MenuMusic").stop();
                    p.start(myStage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return returnButton;
    }

    //创建退出游戏按钮及其事件响应
    private Button createExitButton() {
        final Button exitButton = new Button("退出游戏");
        exitButton.setGraphic(new ImageView(button));
        exitButton.setGraphicTextGap(-115);
        exitButton.setStyle("-fx-background-color:transparent;-fx-text-fill:#FFFFCC;-fx-font:20 arial;");
        exitButton.setPrefSize(200,60);
        exitButton.setTranslateX(-20);
        exitButton.setTranslateY(340);
        exitButton.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                exitButton.setGraphic(new ImageView(button2));
            }
        });
        exitButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                exitButton.setGraphic(new ImageView(button));
            }
        });
        exitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent t) {
                soundBank.soundEffectsMap.get("MenuMusic").stop();
                System.exit(0);
            }
        });
        return exitButton;
    }

    //可以水平竖直移动面板
    private ScrollPane createScrollPane(Pane layout) {
        ScrollPane scroll = new ScrollPane();
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll.setPannable(true);;
        scroll.setPrefSize(2048, 1600);
        scroll.setContent(layout);
        return scroll;
    }
    //public static void main(String[] args) { launch(args); }
}
