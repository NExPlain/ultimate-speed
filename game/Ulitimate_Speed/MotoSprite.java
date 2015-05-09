package game.Ulitimate_Speed;

import game.core.component.act.WObject;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import game.core.geom.GeometryClac;

import java.util.*;

/**
 * Created by LiZhen on 13-12-19.
主摩托类
继承了WObject，实现了update和AIupdate
 */

public class MotoSprite extends WObject {
    private enum Direction {
        Left, Right, Up, Down
    };
    private Direction oppositeDirection(Direction direction){
        if(direction == Direction.Left)return Direction.Right;
        if(direction == Direction.Up)return Direction.Down;
        if(direction == Direction.Down)return Direction.Up;
        if(direction == Direction.Right)return Direction.Left;
        return null;
    }


    private final static Direction directionList[] = {Direction.Up,Direction.Right,Direction.Down,Direction.Left};
    private Direction direction = Direction.Left;
    private Direction lastDirection;

    public double x, y, width, height;
    private int index = 0;
    private int indexDiv = 5;
    private Image mImage = null;
    private final static double speed[] = {1,3,5};
    private Random random;

    public int getCurSpeed() {
        return curSpeed;
    }

    public void setCurSpeed(int curSpeed) {
        if(curSpeed > 2)curSpeed = 2;
        if(curSpeed < 0)curSpeed = 0;
        this.curSpeed = curSpeed;
    }

    private int curSpeed;
    private String preUrl = null;
    public ArrayList<Point2D> cornerList;//拐点序列
    private int fraction;
    final float collisionOffset = 10f;
    final int defaultDamage = 5;
    int hp = 0;


    public MotoSprite(int x, int y, int fraction){//根据阵营载入资源
        random = new Random(System.currentTimeMillis());
        curSpeed = 0;
        this.x = x;
        this.y = y;
        this.width = 40;
        this.height = 102;
        this.fraction = fraction;
        hp = 1;
        if(fraction == 1)preUrl = "content/redMoto/";
        else preUrl = "content/blueMoto/";
        mImage = new Image(getClass().getResourceAsStream(preUrl + (fraction != 1 ?"right.png":"left.png")));
        if(fraction == 1)direction = Direction.Left;
        else direction = Direction.Right;
        cornerList = new ArrayList<Point2D>();
        cornerList.add(new Point2D(this.x,this.y));
    }

    public void pressedMoveButton(Direction direction){
        if(direction == Direction.Right)pressedRight();
        if(direction == Direction.Left)pressedLeft();
        if(direction == Direction.Down)pressedDown();
        if(direction == Direction.Up)pressedUp();
    }

    /**
     * 像下移动
     */
    public void pressedDown() {
        if(direction == Direction.Up){
            setCurSpeed(getCurSpeed()-1);
        }
        if(direction == Direction.Down){
            setCurSpeed(getCurSpeed()+1);
        }
        width = 40;
        height = 102;
        if(direction != Direction.Down && direction != Direction.Up)
                 direction = Direction.Down;
        if(lastDirection != direction){
            cornerList.add(new Point2D(x,y));
            index = 0;
            String url = preUrl + "down.png";
            mImage = new Image(getClass().getResourceAsStream(url));
        }
        index++;
        if (index / indexDiv > 2) {
            index = 0;
        }
        y += speed[curSpeed];
        lastDirection = direction;
    }

    /**
     * 像左移动
     */
    public void pressedLeft() {
        if(direction == Direction.Right){
            setCurSpeed(getCurSpeed()-1);
        }
        if(direction == Direction.Left){
            setCurSpeed(getCurSpeed()+1);
        }
        width = 102;
        height = 40;
        if(direction != Direction.Left && direction != Direction.Right)
            direction = Direction.Left;
        if(lastDirection != direction){
            cornerList.add(new Point2D(x,y));
            index = 3 * indexDiv;
            String url = preUrl + "left.png";
            mImage = new Image(getClass().getResourceAsStream(url));
        }
        index++;
        if (index / indexDiv > 5) {
            index = 3 * indexDiv;
        }
        x -= speed[curSpeed];
        lastDirection = direction;
    }

    /**
     * 像右移动
     */
    public void pressedRight() {
        if(direction == Direction.Left){
            setCurSpeed(getCurSpeed()-1);
        }
        if(direction == Direction.Right){
            setCurSpeed(getCurSpeed()+1);
        }
        width = 102;
        height = 40;
        if(direction != Direction.Right && direction != Direction.Left)
            direction = Direction.Right;
        if(lastDirection != direction){
            cornerList.add(new Point2D(x,y));
            index = 6 * indexDiv;
            String url = preUrl + "right.png";
            mImage = new Image(getClass().getResourceAsStream(url));
        }
        index++;
        if (index / indexDiv > 8) {
            index = 6 * indexDiv;
        }
        x += speed[curSpeed];
        lastDirection = direction;
    }

    /**
     * 像右移动
     */
    public void pressedUp() {
        if(direction == Direction.Down){
            setCurSpeed(getCurSpeed()-1);
        }
        if(direction == Direction.Up){
            setCurSpeed(getCurSpeed()+1);
        }
        width = 40;
        height = 102;
        if(direction != Direction.Up && direction != Direction.Down)
                direction = Direction.Up;
        if(lastDirection != direction){
            cornerList.add(new Point2D(x,y));
            index = 9 * indexDiv;
            String url = preUrl + "up.png";
            mImage = new Image(getClass().getResourceAsStream(url));
        }
        index++;
        if (index / indexDiv > 11) {
            index = 9 * indexDiv;
        }
        y -= speed[curSpeed];
        lastDirection = direction;
    }

    public void move()
    {
        if(direction == Direction.Right)
            this.x += speed[curSpeed];
        if(direction == Direction.Left)
            this.x -= speed[curSpeed];
        if(direction == Direction.Down)
            this.y += speed[curSpeed];
        if(direction == Direction.Up)
            this.y -= speed[curSpeed];
    }

    @Override
    public void draw(GraphicsContext gc) {//绘制摩托
        gc.drawImage(mImage,x-width/2,y-height/2);
        for(int i=0;i<cornerList.size()-1;i++){
            drawLine(gc,cornerList.get(i),cornerList.get(i+1));
        }
        Point2D backPoint = getBackPoint();
        drawLine(gc,cornerList.get(cornerList.size()-1),backPoint);
    }

    @Override
    public void update() {//循环更新函数
        if(mImage == null)System.out.println("None Image Pointer");

        double xb = GameMap.BufferSizeX,yb = GameMap.BufferSizeY;
        if(direction == Direction.Right){
            double nx = x + width/2,ny = y;
            if(nx >= xb){
                pressedUp();
            }
        }else if(direction == Direction.Left){
            double nx = x - width/2,ny = y;
            if(nx < 0){
                pressedDown();
            }
        }else if(direction == Direction.Up){
            double nx = x,ny = y - height/2;
            if(ny < 0){
                pressedLeft();
            }
        }else {
            double nx = x,ny = y + height / 2;
            if(ny >= yb){
                pressedRight();}
        }
        move();
    }

    private boolean willCollideLine(Point2D q1,Point2D q2,ArrayList<Point2D> list1,Point2D p1,ArrayList<Point2D> list2,Point2D p2){//是否会碰到直线
        for(int i=0;i<list1.size()-1;i++){
                if(GeometryClac.is_line_with_line(q1,q2,list1.get(i),list1.get(i+1)))return true;
            }
        if(GeometryClac.is_line_with_line(q1,q2,list1.get(list1.size()-1),p1))return true;
        for(int i=0;i<list2.size()-1;i++){
            if(GeometryClac.is_line_with_line(q1,q2,list2.get(i),list2.get(i+1)))return true;
        }
        return GeometryClac.is_line_with_line(q1, q2, list2.get(list2.size() - 1), p2);
    }

    public void AIupdate(ArrayList<Point2D> list1,Point2D p1,ArrayList<Point2D> list2,Point2D p2){//人机对战时的AI函数
        int seed = random.nextInt();
        int frequency = 200;
        if(GeometryClac.dot(p1,p2) >= 1000*1000){
            frequency = 300;
        }
        if((seed % frequency + frequency)%frequency == 0){
            int num = (random.nextInt()%4 + 4)%4;
            Direction nxtdir = directionList[num];
            pressedMoveButton(nxtdir);
        }
            Point2D q2 = this.getFrontPoint();
            Point2D q1 = GeometryClac.add(GeometryClac.mul(GeometryClac.minus(this.getFrontPoint(), this.getBackPoint()), 0.4), this.getFrontPoint());
            if(willCollideLine(q1,q2,list1,p1,list2,p2)){
                boolean ok = false;
                while(!ok){
                    int num = (random.nextInt()%4 + 4)%4;
                    Direction nxtdir = directionList[num];
                    if(nxtdir != direction && nxtdir != oppositeDirection(direction)){
                        ok = true;
                        pressedMoveButton(nxtdir);
                    }
                }
            }
    }

    public Rectangle2D collisionRect;
    public Rectangle2D getCollisionRect(){
        return new Rectangle2D((int)(this.x  - (float)width/2) + collisionOffset,
                (int)(this.y - (float)height/2) + collisionOffset,
                width - 2*collisionOffset,
                height - 2*collisionOffset);
    }

    public boolean isInBounds(){
        double xb = GameMap.BufferSizeX,yb = GameMap.BufferSizeY;
        boolean res = false;
        double nx = 0,ny = 0;
        if(direction == Direction.Right){
            nx = x + width/2;
            ny = y;
        }else if(direction == Direction.Left){
            nx = x - width/2;
            ny = y;
        }else if(direction == Direction.Up){
            nx = x;
            ny = y - height/2;
        }else {
            nx = x;
            ny = y + height / 2;
        }
        if( nx>=0 && ny>=0 && nx<=xb && ny<=yb ){
            res = true;
        }
        return res;
    }
    /// <summary>
    /// 摩托车是否阵亡
    /// </summary>
    /// <returns></returns>
    public boolean isDead()
    {
        return this.hp <= 0;
    }

    private void drawLine(GraphicsContext gc,Point2D first,Point2D second){
        double a = first.getX(),b = first.getY();
        double p = second.getX(),q = second.getY();
        gc.save();
        if(fraction == 2)
        gc.setStroke(Color.BLUE);
        else
        gc.setStroke(Color.RED);

        gc.setLineWidth(2);
        gc.strokeLine(a,b,p,q);
        gc.restore();
    }

    /// <summary>
    /// 摩托车受到伤害
    /// </summary>
    /// <param name="damage"></param>
    public void isAttacked(int damage)
    {
        hp -= damage;
        if (hp <= 0)
        {
            hp = 0;
        }
    }

    public void isAttacked()
    {
        isAttacked(defaultDamage);
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getY() {
        return y - getHeight()/2;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getX() {
        return x - getWidth()/2;
    }

    public void setX(double x) {
        this.x = x;
    }

    @Override
    public boolean isCollisionWith(WObject baseObject){
        return getX() + getWidth() > baseObject.getX() && getX() < baseObject.getX() + baseObject.getWidth() && getY() + getHeight() > baseObject.getY() && getY() < baseObject.getY() + baseObject.getHeight();
    }

    Point2D getBackPoint(){
        double nx = x,ny = y;
        if(direction == Direction.Right){
            nx -= getWidth()/2;
        }else if(direction == Direction.Left){
            nx += getWidth()/2;
        }else if(direction == Direction.Up){
            ny += getHeight()/2;
        }else if(direction == Direction.Down){
            ny -= getHeight()/2;
        }
        return new Point2D(nx,ny);
    }

    Point2D getFrontPoint(){
        Point2D nowPoint =  new Point2D(2*this.x - getBackPoint().getX(),2*this.y - getBackPoint().getY());
        if(direction == Direction.Up){
            nowPoint = new Point2D(nowPoint.getX(),nowPoint.getY()+5);
        }else if(direction == Direction.Down){
            nowPoint = new Point2D(nowPoint.getX(),nowPoint.getY()-5);
        }else if(direction == Direction.Left){
            nowPoint = new Point2D(nowPoint.getX()+5,nowPoint.getY());
        }else if(direction == Direction.Right){
            nowPoint = new Point2D(nowPoint.getX()-5,nowPoint.getY());
        }
        return nowPoint;
    }

}
