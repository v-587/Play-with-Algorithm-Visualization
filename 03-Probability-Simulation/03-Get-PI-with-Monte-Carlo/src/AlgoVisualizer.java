import java.awt.*;
import java.util.LinkedList;
import javax.swing.*;

public class AlgoVisualizer {

    private static int DELAY = 40;

    private Circle circle;
    private int insideCircle = 0;
    private LinkedList<Point> points;
    private AlgoFrame frame;
    private int N;

    public AlgoVisualizer(int sceneWidth, int sceneHeight, int N){

        if(sceneWidth != sceneHeight)
            throw new IllegalArgumentException("This demo must be run in a square window!");

        this.N = N;

        circle = new Circle(sceneWidth/2, sceneHeight/2, sceneWidth/2);
        points = new LinkedList<Point>();

        // 初始化视图
        EventQueue.invokeLater(() -> {
            frame = new AlgoFrame("Monte Carlo", sceneWidth, sceneHeight);

            new Thread(() -> {
                run();
            }).start();
        });
    }

    public void run(){

        for(int i = 0 ; i < N ; i ++){

            //if( i % 10 == 0) {
            if( i % 100 == 0) {
                frame.render(circle, points);
                AlgoVisHelper.pause(DELAY);

                if(points.size() != 0) {
                    int circleArea = insideCircle;
                    int squareArea = points.size();
                    double pi_estimation = (double)circleArea * 4 / squareArea;
                    System.out.println("PI estimation: " + pi_estimation);
                }
            }

            int x = (int)(Math.random() * frame.getCanvasWidth());
            int y = (int)(Math.random() * frame.getCanvasHeight());

            Point p = new Point(x, y);
            points.add(p);
            if(circle.contain(p))
                insideCircle ++;
        }

    }

    public static void main(String[] args) {

        int sceneWidth = 800;
        int sceneHeight = 800;
        int N = 10000;

        AlgoVisualizer vis = new AlgoVisualizer(sceneWidth, sceneHeight, N);
    }
}