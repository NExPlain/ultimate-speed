package game.core.geom;

import javafx.geometry.Point2D;

/**
 * Created by LiZhen on 13-12-19.
这是计算几何类
用于游戏中的判断直线相交
 */
public class GeometryClac {
    public final static double EPS = 1e-8;
    public static double add(double a,double b){
        if(Math.abs(a + b) < EPS * (Math.abs(a) + Math.abs(b))) return 0;
        return a+b;
    }//带精度判断的加法操作

    public static double dot(Point2D a,Point2D b){
        return add(a.getX() * b.getX(), a.getY() * b.getY());
    }//向量点乘

    public static double det(Point2D a,Point2D b){
        return add(a.getX() * b.getY(), -1 * a.getY() * b.getX());
    }//向量叉积

    public static Point2D add(Point2D p,Point2D q){
        return new Point2D(p.getX() + q.getX() ,p.getY() + q.getY());
    }//向量相加

    public static Point2D minus(Point2D p,Point2D q){
        return new Point2D(p.getX() - q.getX() ,p.getY() - q.getY());
    }//向量相减

    public static Point2D mul(Point2D p,double x){
        return new Point2D(p.getX() * x,p.getY() * x);
    }//向量乘常数

    public static boolean on_seg(Point2D p1, Point2D p2,Point2D q){
        return det((minus(p1, q)),minus(p2,q)) == 0 && dot(minus(p1,q),minus(p2,q)) <= 0;
    }//点q在p1、p2构成的直线上

    public static Point2D intersection(Point2D p1, Point2D p2, Point2D q1, Point2D q2){
        return add(p1, mul(minus(p2,p1), (det(minus(q2,q1),minus(q1,p1)) / det(minus(q2,q1),minus(p2,p1)))));
    }//直线p1、p2和直线q1、q2的交点

    public static boolean is_line_with_line(Point2D p1, Point2D p2, Point2D q1, Point2D q2){
        Point2D l1 = minus(p2 ,p1);
        Point2D l2 = minus(q2, q1);
        if(l1.getX() * l2.getY() == l1.getY()* l2.getX()){
            if(on_seg(p1,p2,q1) || on_seg(p1,p2,q2) || on_seg(q1,q2,p1) || on_seg(q1,q2,p2))return true;
            return false;
        }
        Point2D v = intersection(p1,p2,q1,q2);
        if(on_seg(p1,p2,v) && on_seg(q1,q2,v))return true;
        return false;
    }//直线p1、p2和直线q1、q2是否相交
};
