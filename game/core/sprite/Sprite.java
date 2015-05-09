package game.core.sprite;

import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
/**
 * Created by LiZhen on 13-12-10.
精灵类
作为绘制游戏精灵的基类
 */

public class Sprite extends Parent {
    public enum Direction {
        Left, Right, Up, Down
    };//枚举方向类型
    public static Direction oppesiteDirection(Direction dir){
        if(dir == Direction.Right)return Direction.Left;
        if(dir == Direction.Left)return Direction.Right;
        if(dir == Direction.Up)return Direction.Down;
        return Direction.Up;
    }
    private Direction direction = Direction.Left;
    private Direction lastDirection;
    private int x, y, width, height;
    private int index = 0;
    private int indexDiv = 5;
    private ImageView mImageView;
    private int speed = 5;

    public Sprite(int x, int y, int width, int height, String url) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        Image actor = new Image(getClass().getResourceAsStream(url));
        mImageView = new ImageView(actor);
        mImageView.setViewport(new Rectangle2D(0, 0, width, height));
        mImageView.setLayoutX(x);
        mImageView.setLayoutY(y);
        getChildren().add(mImageView);
    }

    /**
     * 像下移动
     */
    public void moveDown() {
        direction = Direction.Down;
        if(lastDirection != direction){
            index = 0;
        }
        index++;
        if (index / indexDiv > 2) {
            index = 0;
        }
        mImageView.setViewport(new Rectangle2D(((index / indexDiv) % 3) * width, ((index / indexDiv) / 3) * height, width,
                height));
        mImageView.setLayoutY(mImageView.getLayoutY() + speed);

        lastDirection = direction;
    }

    /**
     * 像左移动
     */
    public void moveLeft() {
        direction = Direction.Left;
        if(lastDirection != direction){
            index = 3 * indexDiv;
        }
        index++;
        if (index / indexDiv > 5) {
            index = 3 * indexDiv;
        }
        mImageView.setViewport(new Rectangle2D(((index / indexDiv) % 3) * width, ((index / indexDiv) / 3) * height, width,
                height));
        mImageView.setLayoutX(mImageView.getLayoutX() - speed);

        lastDirection = direction;
    }

    /**
     * 像右移动
     */
    public void moveRight() {
        direction = Direction.Right;
        if(lastDirection != direction){
            index = 6 * indexDiv;
        }
        index++;
        if (index / indexDiv > 8) {
            index = 6 * indexDiv;
        }
        mImageView.setViewport(new Rectangle2D(((index / indexDiv) % 3) * width, ((index / indexDiv) / 3) * height, width,
                height));
        mImageView.setLayoutX(mImageView.getLayoutX() + speed);

        lastDirection = direction;
    }

    /**
     * 像右移动
     */
    public void moveUp() {
        direction = Direction.Up;
        if(lastDirection != direction){
            index = 9 * indexDiv;
        }
        index++;
        if (index / indexDiv > 11) {
            index = 9 * indexDiv;
        }
        mImageView.setViewport(new Rectangle2D(((index / indexDiv) % 3) * width, ((index / indexDiv) / 3) * height, width,
                height));
        mImageView.setLayoutY(mImageView.getLayoutY() - speed);

        lastDirection = direction;
    }


    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }
    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }
    public int getWidth() {
        return width;
    }
    public void setWidth(int width) {
        this.width = width;
    }
    public int getHeight() {
        return height;
    }
    public void setHeight(int height) {
        this.height = height;
    }
}
