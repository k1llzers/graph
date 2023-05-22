import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Graph extends Canvas {
    public static int countOfScreen = 1;
    public static double a;
    public static double min;
    public static double max;
    public static double step;
    public static Graph currentGraph;



    public void paint(Graphics g) {
        setBackground(Color.PINK);
        g.setColor(Color.BLACK);
        g.drawLine(30,350,700,350);
        g.drawLine(365,30,365,675);
        g.drawString("0",375,370);
        g.drawLine(476,350,476,360);
        g.drawString("10",471,375);
        g.drawLine(588,350,588,360);
        g.drawString("20",583,375);
        g.drawLine(700,350,700,360);
        g.drawString("30",695,375);
        g.drawLine(30,350,30,360);
        g.drawString("-30",20,375);
        g.drawLine(141,350,141,360);
        g.drawString("-20",131,375);
        g.drawLine(253,350,253,360);
        g.drawString("-10",243,375);
        g.drawLine(365,30,375,30);
        g.drawString("30",380,35);
        g.drawLine(365,141,375,141);
        g.drawString("20",380,146);
        g.drawLine(365,253,375,253);
        g.drawString("10",380,258);
        g.drawLine(365,458,375,458);
        g.drawString("-10",380,463);
        g.drawLine(365,566,375,566);
        g.drawString("-20",380,571);
        g.drawLine(365,675,375,675);
        g.drawString("-30",380,680);

        Graphics2D graphics2D = (Graphics2D) g;
        if (step <= 0.001)
            for (double i = min;i <= max;i += step){
                double r = a * i * Math.PI;
                double x = 365 + r * Math.cos(i * Math.PI) * 22.2;
                double y = 350 - r * Math.sin(i * Math.PI) * 22.2;
                graphics2D.draw(new Line2D.Double(x,y,x,y));
            }
        else
            for (double i = min;i <= max;i += step){
                double r = a * i * Math.PI;
                double x = 365 + r * Math.cos(i * Math.PI) * 22.2;
                double y = 350 - r * Math.sin(i * Math.PI) * 22.2;
                g.fillOval((int)x,(int)y,5,6);
            }
    }

    public static void setFields(double newA,double newMin,double newMax,double newStep){
        a = newA;
        min = newMin;
        max = newMax;
        step = newStep;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setTitle("Graph");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000,800);
        frame.setLayout(null);
        JLabel r = new JLabel("r = a * φ");
        r.setBounds(820,30,100,20);
        frame.add(r);
        JLabel x = new JLabel("x = r * cos(φ)");
        x.setBounds(820,60,100,20);
        frame.add(x);
        JLabel y = new JLabel("y = r * sin(φ)");
        y.setBounds(820,90,100,20);
        frame.add(y);
        JLabel a = new JLabel("a = ");
        a.setBounds(820,150,20,20);
        frame.add(a);
        JTextField aField = new JTextField("1");
        aField.setBounds(840,150,70,20);
        frame.add(aField);
        JLabel minFi = new JLabel("min(φ) = π * ");
        minFi.setBounds(820,180,80,20);
        frame.add(minFi);
        JTextField minFiField = new JTextField("0");
        minFiField.setBounds(895,180,70,20);
        frame.add(minFiField);
        JLabel maxFi = new JLabel("max(φ) = π * ");
        maxFi.setBounds(820,210,80,20);
        frame.add(maxFi);
        JTextField maxFiField = new JTextField("4");
        maxFiField.setBounds(895,210,70,20);
        frame.add(maxFiField);
        JLabel step = new JLabel("step = ");
        step.setBounds(820,240,50,20);
        frame.add(step);
        JTextField stepField = new JTextField("0.001");
        stepField.setBounds(860,240,70,20);
        frame.add(stepField);
        JButton show = new JButton("show");
        show.setBounds(820,270,100,20);
        show.addActionListener(e -> {
            try {
                if (Double.parseDouble(minFiField.getText()) > Double.parseDouble(maxFiField.getText()) || Double.parseDouble(stepField.getText()) <= 0 || Double.parseDouble(minFiField.getText()) < 0)
                    throw new RuntimeException();
                setFields(Double.parseDouble(aField.getText()),Double.parseDouble(minFiField.getText()),Double.parseDouble(maxFiField.getText()),Double.parseDouble(stepField.getText()));
            } catch (Exception ex){
                JOptionPane.showMessageDialog(new Frame(),"Введено некоректе значення");
                aField.setText("");
                return;
            }
            if (currentGraph != null)
                frame.remove(currentGraph);
            currentGraph = new Graph();
            currentGraph.setBounds(0,0,800,800);
            frame.add(currentGraph);
            frame.setVisible(false);
            frame.setVisible(true);
        });
        frame.add(show);
        JButton save = new JButton("save");
        save.addActionListener(e -> {
            takeScreen();
        });
        save.setBounds(820,300,100,20);
        frame.add(save);
        frame.setVisible(true);
    }

    public static void takeScreen(){
        try {
            Robot robot = new Robot();
            BufferedImage screenCapture = robot.createScreenCapture(new Rectangle(1000, 800));
            File file = new File("D:\\GraphPracTask1");
            if (!file.exists())
                Files.createDirectory(file.toPath());
            file = new File("D:\\GraphPracTask1\\screenshotsOfGraphs" + countOfScreen + ".png");
            while (file.exists()){
                countOfScreen+=1;
                file = new File("D:\\GraphPracTask1\\screenshotsOfGraphs" + countOfScreen + ".png");
            }
            ImageIO.write(screenCapture,"png",new File("D:\\GraphPracTask1\\screenshotsOfGraphs" + countOfScreen + ".png"));
            countOfScreen+=1;
        } catch (AWTException ex) {
            throw new RuntimeException(ex);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
