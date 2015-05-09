package game.Ulitimate_Speed;

import game.core.component.act.WObject;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.*;

/**
 * Created by LiZhen on 13-12-17.
 */
public class Throne extends WObject {
    private final int width = 230,height = 238;
    private final String url = "content/throne.png";
    private int x,y;
    private Image mImage;
    public Throne(int x, int y) {
        this.x = x;
        this.y = y;
        mImage = new Image(getClass().getResourceAsStream(url));
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.drawImage(mImage,x,y);
    }

    @Override
    public void update() {

    }
}
