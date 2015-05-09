package game.Ulitimate_Speed;
import com.sun.deploy.ui.AboutDialog;
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
import javafx.stage.Stage;
import javafx.scene.text.*;

public class MenuPanel extends Application{
    public static enum GameMode {vsCom, vsHum}; //vsCom:人机对战 vsHum:玩家对战
    public static GameMode gameMode;
    private Image backgroundImage;
    private Image AboutImage;
    private Image HelpImage;
    private Image button;
    private Image button2;
    private StackPane layout;
    private ScrollPane scroll;
    private Stage myStage;
    static public boolean isSoundOn;

    //变量初始化
    @Override public void init(){
        backgroundImage = new Image(getClass().getResourceAsStream("content/TRON_wallpaper_race.jpg"));
        AboutImage= new Image(getClass().getResourceAsStream("content/1.jpg"));
        HelpImage= new Image(getClass().getResourceAsStream("content/1.jpg"));
        button=new Image(getClass().getResourceAsStream("content/Button.gif"));
        button2=new Image(getClass().getResourceAsStream("content/ButtonWithMouse.gif"));
        gameMode =GameMode.vsCom;
        isSoundOn = true;
    }

    //建立MenuPanel主面板并添加组件
    @Override public void start(Stage stage){
        myStage = stage;
        init();
        stage.setFullScreen(true);
        stage.setTitle("Ultimate_Speed");
        layout=new StackPane();
        ImageView BackGroundImageView = new ImageView(backgroundImage);
        BackGroundImageView.setFitWidth(2048);
        BackGroundImageView.setFitWidth(1600);
        layout.getChildren().setAll(BackGroundImageView, createPlayButton1());
        layout.getChildren().addAll(createPlayButton2(),createHelpButton(),createAboutButton(),createExitButton(),createTitle(600,400,10,-270));

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

    }

    //创建人机对战按钮及其事件响应
    private Button createPlayButton1() {
        final Button playButton = new Button("人机对战");
        playButton.setGraphic(new ImageView(button));
        playButton.setGraphicTextGap(-115);
        playButton.setStyle("-fx-background-color:transparent;-fx-text-fill:#FFFFCC;-fx-font:20 arial;");
        playButton.setPrefSize(200, 60);
        playButton.setTranslateX(-20);
        playButton.setTranslateY(20);
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
            @Override public void handle(ActionEvent t) {
                gameMode = GameMode.vsCom;
                MainGame m = new MainGame();
                try {
                    m.start(myStage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return playButton;
    }

    //创建关闭音效按钮及其事件响应
    private Button createMusicButton() {
        final Button MusicButton = new Button("关闭音效");
        MusicButton.setGraphic(new ImageView(button));
        MusicButton.setGraphicTextGap(-115);
        MusicButton.setStyle("-fx-background-color:transparent;-fx-text-fill:#FFFFCC;-fx-font:20 arial;");
        MusicButton.setPrefSize(200, 60);
        MusicButton.setTranslateX(200);
        MusicButton.setTranslateY(230);
        if(!isSoundOn)MusicButton.setText("开启音效");
        MusicButton.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                MusicButton.setGraphic(new ImageView(button2));
            }
        });
        MusicButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                MusicButton.setGraphic(new ImageView(button));
            }
        });
        MusicButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent t) {
                if(isSoundOn){
                    isSoundOn = false;
                    MusicButton.setText("开启音效");
                }else{
                    isSoundOn = true;
                    MusicButton.setText("关闭音效");
                }
            }
        });
        return MusicButton;
    }

    //创建玩家对战按钮及其事件响应
    private Button createPlayButton2() {
        final Button playButton = new Button("玩家对战");
        playButton.setGraphic(new ImageView(button));
        playButton.setGraphicTextGap(-115);
        playButton.setStyle("-fx-background-color:transparent;-fx-text-fill:#FFFFCC;-fx-font:20 arial;");
        playButton.setPrefSize(200, 60);
        playButton.setTranslateX(-20);
        playButton.setTranslateY(100);
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
            @Override public void handle(ActionEvent t) {
                gameMode =GameMode.vsHum;
                MainGame m = new MainGame();
                try {
                    m.start(myStage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return playButton;
    }

    //创建帮助按钮及其事件响应
    private Button createHelpButton() {
        final Button helpButton = new Button("　帮助　");
        helpButton.setGraphic(new ImageView(button));
        helpButton.setGraphicTextGap(-115);
        helpButton.setStyle("-fx-background-color:transparent;-fx-text-fill:#FFFFCC;-fx-font:20 arial;");
        helpButton.setPrefSize(200,60);
        helpButton.setTranslateX(-20);
        helpButton.setTranslateY(180);
        helpButton.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                helpButton.setGraphic(new ImageView(button2));
            }
        });
        helpButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                helpButton.setGraphic(new ImageView(button));
            }
        });
        helpButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent t) {
                layout.getChildren().addAll(new ImageView(HelpImage),createReturnButton(),createBackRec(600,440,-220,-100),createHelpText(),createMusicButton());
            }
        });
        return helpButton;
    }

    //创建帮助文本
    private Text createHelpText(){
        final Text helpText=new Text();
        helpText.setTranslateX(-200);
        helpText.setTranslateY(-100);
        helpText.setFill(Color.WHITE);
        helpText.setStyle("-fx-font: 30 arial;-fx-font-weight: bolder");
        helpText.setText("游戏规则：\n在地图上双方驾驶摩托竞速\n摩托走过的路径上会产生光路\n一旦碰到光路摩托会立即毁灭！\n\n操作方式：\n玩家1操作蓝色摩托，WASD控制移动\n玩家2控制红色摩托，上下左右控制移动\n游戏屏幕可拖拽移动！\n\n尽可能生存并且击败对手！\n右下角按钮可开启/关闭音效");
        return helpText;
    }

    //帮助文本背景
    private ImageView createBackRec(double w,double h,double lx,double ly){
        final ImageView BackRec = new ImageView(new Image(getClass().getResourceAsStream("content/backRec.jpg")));
        BackRec.setFitHeight(h);
        BackRec.setFitWidth(w);
        BackRec.setOpacity(0.7);
        BackRec.setTranslateX(lx);
        BackRec.setTranslateY(ly);
        return BackRec;
    }

    //标题图片
    private ImageView createTitle(double w,double h,double lx,double ly){
        final ImageView Title = new ImageView(new Image(getClass().getResourceAsStream("content/Title.png")));
        Title.setFitHeight(h);
        Title.setFitWidth(w);
        Title.setOpacity(0.7);
        Title.setTranslateX(lx);
        Title.setTranslateY(ly);
        return Title;
    }

    //创建关于按钮及其事件响应
    private Button createAboutButton() {
        final Button aboutButton = new Button("　关于　");
        aboutButton.setGraphic(new ImageView(button));
        aboutButton.setGraphicTextGap(-115);
        aboutButton.setStyle("-fx-background-color:transparent;-fx-text-fill:#FFFFCC;-fx-font:20 arial;");
        aboutButton.setPrefSize(200, 60);
        aboutButton.setTranslateX(-20);
        aboutButton.setTranslateY(260);
        aboutButton.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                aboutButton.setGraphic(new ImageView(button2));
            }
        });
        aboutButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                aboutButton.setGraphic(new ImageView(button));
            }
        });
        aboutButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent t) {
                layout.getChildren().addAll(new ImageView(AboutImage),createReturnButton(),createBackRec(600,330,-200,-100),createAboutText());
            }
        });
        return aboutButton;
    }

    //创建关于文本
    private  Text createAboutText(){
        final Text aboutText=new Text();
        aboutText.setTranslateX(-200);
        aboutText.setTranslateY(-100);
        aboutText.setFill(Color.WHITE);
        aboutText.setStyle("-fx-font: 30 arial;-fx-font-weight: bolder");
        aboutText.setText("游戏设计：李珎\t\t 主游戏编写：李珎\n\n界面设计：李珎、李沛伦\t 界面编写：李沛伦\n\n御用绘师：李孟奇  \t\t 音乐：李珎\n\nBGM:  Just Communication - TWO-MIX\n");
        return aboutText;
    }

   //创建退出按钮及其事件响应
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
                System.exit(0);
            }
        });
        return exitButton;
    }

    //创建返回按钮及其事件响应
    private Button createReturnButton() {
        final Button returnButton = new Button("回主菜单");
        returnButton.setGraphic(new ImageView(button));
        returnButton.setGraphicTextGap(-115);
        returnButton.setStyle("-fx-background-color:transparent;-fx-text-fill:#FFFFCC;-fx-font:20 arial;");
        returnButton.setPrefSize(200,60);
        returnButton.setTranslateX(200);
        returnButton.setTranslateY(300);
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
            @Override public void handle(ActionEvent t) {     //依次清除掉“关于”和“帮助”面板所添加的组件
                layout.getChildren().remove(7);
                layout.getChildren().remove(7);
                layout.getChildren().remove(7);
                layout.getChildren().remove(7);
                if(layout.getChildren().size()>7)
                layout.getChildren().remove(7);
            }
        });

        return returnButton;
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

    public static void main(String[] args) { launch(args); }

}
