package game.Ulitimate_Speed;

import game.core.component.act.WObject;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;


/**
 * Created by LiZhen on 13-12-12.
游戏地图背景类，负责绘制游戏地图
 */
public class GameMap extends WObject{
    Image backGroundImage;
    public static double BufferSizeX;
    public static double BufferSizeY;
    final double times = 1;
    public GameMap(double x, double y){
        BufferSizeX = x * times;
        BufferSizeY = y * times;
        backGroundImage = new Image(getClass().getResourceAsStream("content/BackGround.png"));
    }
    @Override
    public void draw(GraphicsContext gc) {
        //gc.drawImage(backGroundImage,0,0);
        gc.drawImage(backGroundImage ,0 ,0, BufferSizeX * times, BufferSizeY * times);
    }

    @Override
    public void update() {

    }
}
