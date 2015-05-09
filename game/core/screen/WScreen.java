package game.core.screen;

import java.util.ArrayList;
import java.util.List;

import game.core.component.act.WObject;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;

/**
 * Created by LiZhen on 13-12-10.
这是游戏中的Screen类，作为主屏幕的画布基类出现
 */
public abstract class WScreen extends Canvas {
    protected enum GameState {GAME_MENU, GAME_START, GAME_CONTINUE, GAME_HELP, GAME_SET,GAME_EXIT,GAME_PAUSE};//枚举类型：游戏状态
    protected List<WObject> mObjects = new ArrayList<WObject>();//画布上的WObject的List
    protected Timeline timeline;
    protected KeyFrame keyFrame;
    protected int duration = 10;
    protected GameState mGameState = GameState.GAME_MENU;
    public WScreen(double width, double height) {
        super(width, height);
        initTimeLine();
    }

    public void initEvents(){
        getParent().getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent event) {
                onKeyPressed(event);
            }
        });
        getParent().getScene().setOnKeyReleased(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent event) {
                onKeyReleased(event);
            }
        });
    }

    protected abstract void onKeyPressed(KeyEvent event);

    protected abstract void onKeyReleased(KeyEvent event);


    /**
     * add the object
     * @param baseObject the object to be add
     */
    public void addObject(WObject baseObject){
        this.mObjects.add(baseObject);
    }

    /**
     * remove the object
     * @param baseObject the object to be remove
     */
    public void removeObject(WObject baseObject){
        this.mObjects.remove(baseObject);
    }

    /**
     * remove the object with the index
     * @param index the index of the object
     */
    public void removeObjectAtIndex(int index){
        this.mObjects.remove(index);
    }

    /**
     * draw the objects
     * @param gc
     */
    public void draw(GraphicsContext gc){
        gc.clearRect(0, 0, getWidth(), getHeight());
        for (int i = 0; i < mObjects.size(); i++) {
            WObject wObject = mObjects.get(i);
            if (wObject.isVisible()) {
                wObject.draw(gc);
            }
        }
    }

    /**
     * update all the objects
     */
    public void update(){
        for (int i = 0; i < mObjects.size(); i++) {
            WObject wObject = mObjects.get(i);
            if (wObject.isUpdate()) {
                mObjects.get(i).update();
            }
        }
    }

    /**
     * init the timeline
     */
    protected void initTimeLine() {
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);

        keyFrame = new KeyFrame(Duration.millis(duration), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                draw(getGraphicsContext2D());
                update();
            }
        });
        timeline.getKeyFrames().add(keyFrame);
    }

    /**
     * start the update timeline
     */
    public void start(){
        timeline.play();
    }

    /**
     * pause the update timeline
     */
    public void pause(){
        timeline.pause();
    }

    /**
     * stop the update timeline
     */
    public void stop(){
        timeline.stop();
    }

}
