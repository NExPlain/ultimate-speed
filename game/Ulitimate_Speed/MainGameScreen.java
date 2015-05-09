package game.Ulitimate_Speed;

import game.core.geom.GeometryClac;
import game.core.component.act.WObject;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import game.core.screen.WScreen;
import javafx.stage.Stage;
import game.Ulitimate_Speed.MenuPanel;

/**
 * Created by LiZhen on 13-12-19.
主游戏屏幕类，基本游戏循环和绘制在这里进行
 */

public class MainGameScreen extends WScreen {
    public SoundManager soundBank = null;
    private MotoSprite bluemoto = null;
    private MotoSprite redmoto = null;
    private WObject pauseLabel = null;
    private Stage myStage = null;
    public static enum GameStatus {blueWins,redWins,Duece};//枚举类型游戏状态
    GameStatus gameStatus;

    public static double WindowWidth;
    public static double WindowHeight;
    public MainGameScreen(double width, double height, Stage stage) {//构造函数构造游戏元素并载入资源
        super(width, height);
        gameStatus = GameStatus.Duece;
        GameMap mGameMap = new GameMap(width,height);
        WindowWidth = width;
        WindowHeight = height;
        bluemoto = new MotoSprite(200,800,2);
        redmoto = new MotoSprite(1800,800,1);
        soundBank = new SoundManager(3);
        soundBank.loadSoundEffects("BackGroundMusic","content/sound/Just_Communication.mp3");
        soundBank.loadSoundEffects("collideMusic","content/sound/collideMusic.wav");
        soundBank.loadSoundEffects("deadMusic","content/sound/deadMusic.wav");
        soundBank.playSound("BackGroundMusic",MenuPanel.isSoundOn);

        bluemoto.pressedRight();
        redmoto.pressedLeft();

        Throne Throne_left_up = new Throne(230,250);
        Throne Throne_right_up = new Throne(1430,250);
        Throne Throne_left_down = new Throne(330,1130);
        Throne Throne_right_down = new Throne(1540,1100);

        addObject(bluemoto);
        addObject(redmoto);
        addObject(Throne_left_down);
        addObject(Throne_left_up);
        addObject(Throne_right_down);
        addObject(Throne_right_up);

        myStage = stage;
        mGameState = GameState.GAME_START;
    }

    @Override
    protected void onKeyPressed(KeyEvent event) {//响应键盘操作
        switch (event.getCode()) {
            case W:
                if(bluemoto.isInBounds())
                bluemoto.pressedUp();
                break;
            case S:
                if(bluemoto.isInBounds())
                bluemoto.pressedDown();
                break;
            case A:
                if(bluemoto.isInBounds())
                bluemoto.pressedLeft();
                break;
            case D:
                if(bluemoto.isInBounds())
                bluemoto.pressedRight();
                break;
            case UP:
                if(redmoto.isInBounds() && MenuPanel.gameMode == MenuPanel.GameMode.vsHum)
                redmoto.pressedUp();
                break;
            case LEFT:
                if(redmoto.isInBounds() && MenuPanel.gameMode == MenuPanel.GameMode.vsHum)
                redmoto.pressedLeft();
                break;
            case DOWN:
                if(redmoto.isInBounds() && MenuPanel.gameMode == MenuPanel.GameMode.vsHum)
                redmoto.pressedDown();
                break;
            case RIGHT:
                if(redmoto.isInBounds() && MenuPanel.gameMode == MenuPanel.GameMode.vsHum)
                redmoto.pressedRight();
                break;
            case ENTER:
                if(mGameState != GameState.GAME_PAUSE){
                    mGameState = GameState.GAME_PAUSE;
                    pause();
                }else{
                    mGameState = GameState.GAME_CONTINUE;
                    mObjects.remove(mObjects.size()-1);
                    super.start();
                }
                break;
            default:
                break;
        }
    }

    @Override public void pause(){//暂停方法
        if(mGameState == GameState.GAME_PAUSE){
                pauseLabel = new WObject() {
                @Override
                public void draw(GraphicsContext gc) {
                    gc.fillText("Pause", 1024, 800);
                }
                @Override
                public void update() {

                }
            };
            addObject(pauseLabel);
        }
        super.pause();
    }
    void check(MotoSprite a,MotoSprite b){//判断是否线段相交的方法
        Point2D s1 = new Point2D(a.x,a.y);
        Point2D s2 = a.getFrontPoint();
        if(a.cornerList.size() > 2)
            for(int i=0;i<a.cornerList.size()-1;i++){
                Point2D t1 = a.cornerList.get(i);
                Point2D t2 = a.cornerList.get(i+1);
                if(GeometryClac.is_line_with_line(s1, s2, t1, t2))a.isAttacked(1);//调用拐点序列中的点，依次调用计算几何类中的方法判断是否线段相交
            }
        for(int i=0;i<b.cornerList.size();i++){
            Point2D t1 = b.cornerList.get(i);
            Point2D t2;
            if(i+1<b.cornerList.size())t2 = b.cornerList.get(i+1);
            else t2 = b.getBackPoint();
            if(GeometryClac.is_line_with_line(s1, s2, t1, t2))a.isAttacked(1);
        }
    }
    @Override
    public void update(){
        if(mObjects.get(0).isCollisionWith(mObjects.get(1))){//两车相撞则游戏结束
            ((MotoSprite)(mObjects.get(0))).isAttacked(((MotoSprite) (mObjects.get(0))).hp);
            ((MotoSprite)(mObjects.get(1))).isAttacked(((MotoSprite) (mObjects.get(1))).hp);
        }
        check(bluemoto,redmoto);
        check(redmoto,bluemoto);

        if(bluemoto.isDead() || redmoto.isDead()){//游戏结束
            soundBank.playSound("deadMusic",MenuPanel.isSoundOn);
            if(bluemoto.isDead() && redmoto.isDead())gameStatus = GameStatus.Duece;
            else soundBank.playSound("collideMusic",MenuPanel.isSoundOn);
            if(bluemoto.isDead() && !redmoto.isDead())gameStatus = GameStatus.redWins;
            if(!bluemoto.isDead() && redmoto.isDead())gameStatus = GameStatus.blueWins;
            stop();
        }
        if(MenuPanel.gameMode == MenuPanel.GameMode.vsCom)redmoto.AIupdate(bluemoto.cornerList,bluemoto.getBackPoint(),redmoto.cornerList,redmoto.getBackPoint());
        super.update();
    }
    public void closeMusic(){
        if(MenuPanel.isSoundOn){
            if(soundBank.soundEffectsMap.get("BackGroundMusic").isPlaying()){
                soundBank.soundEffectsMap.get("BackGroundMusic").stop();
            }
        }
    }
    @Override
    protected void onKeyReleased(KeyEvent event) {

    }

    @Override
    public void draw(GraphicsContext gc){//进行绘制
        super.draw(gc);
    }
    @Override
    public void stop(){
        GameOverPanel gameOverPanel = new GameOverPanel(gameStatus);
        gameOverPanel.start(myStage);
        soundBank.soundEffectsMap.get("BackGroundMusic").stop();
        super.stop();
    }
}
