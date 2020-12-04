/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stimulator3;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
/**
 *
 * @author ananya
 */
class Environment extends JPanel implements ActionListener{

    int n, m;
     int x, y;
 int goalX, goalY;
     int[][] matrix;
    int steps;
    JFrame frame;
    
    Environment(int N, int M, int goalx, int goaly) {

        n = N;
        m = M;
        goalX=goalx;
       goalY=goaly;
        matrix = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                matrix[i][j] = 0;
            }
        }
//        repaint();

 }
   
   public void paint(Graphics g){
       super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        Ellipse2D.Double circle = new Ellipse2D.Double(goalX*30, goalY*30, 28, 28);

        g2d.setColor(Color.GRAY);
        g2d.fill(circle);
        for(int i=0; i<n; i++){
            for(int j=0; j<m; j++){
                 g.drawRect(i*30,j*30,30,30);
                 g.setColor(Color.BLACK);
                if(matrix[i][j]!=0){
                    
                    int t=matrix[i][j];
                     Ellipse2D.Double circle2 = new Ellipse2D.Double(i*30, j*30, 28, 28);
//                    g.drawRect(i*5,j*5,5,5);
                    if(t%4==0)
                         g2d.setColor(Color.RED);
//                        g.setColor(Color.BLUE);
                    else if(t%4==1)
                         g2d.setColor(Color.GREEN);
//                        g.setColor(Color.YELLOW);
                    else if(t%4==2)
                         g2d.setColor(Color.PINK);
//                        g.setColor(Color.PINK);
                    else  if(t%4==3)
//                        g.setColor(Color.ORANGE);
                            g2d.setColor(Color.BLUE);
        g2d.fill(circle2);
//                    g.fillRect(i*5,j*5,5,5);
                }else{
//                     g.drawRect(i*5,j*5,5,5);
//                     g.setColor(Color.WHITE);
//                     g.fillRect(i*5,j*5,5,5);
                     
                }

            
            }
        }
   }
    
    

    public void updatePosition(int a, int b, int x1, int y1,int index) {
         matrix[x1][y1] = 0;
        if(a!=goalX && b!=goalY)
            matrix[a][b] = index;
        
       
        repaint();
        return;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
//        repaint();
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


}

class Agent extends Thread {
    // Simulator s;

     int x, y, goalX, goalY, n, m, index;

    Agent(int goalx, int goaly, int X, int Y, int ind) {
        goalX = goalx;
        goalY = goaly;
        x = X;
        y = Y;
        index = ind;
    }

    double findDist(int x1, int y1) {
        int m1 = (goalX - x1) * (goalX - x1);
        m1 += (goalY - y1) * (goalY - y1);
        return Math.sqrt(m1);

    }

    int nextPosition() {

        int[] xi = new int[2];
        int[] yi = new int[2];    
        xi[0] = -1;
        xi[1] = 1;
        yi[0] = -1;
        yi[1] = 1;

        double minDist = 9999999;

        int d=0, k=0;
while(k==0){
     d = 0;
        for (int i = 0; i < 2; i++) {
            double dist = findDist(x + xi[i], y);
           
            if (Simulator3.isSafe(x+xi[i], y)==1 && minDist > dist) {
                minDist = dist;
                d = xi[i];
                k=1;
            }
        }
        
        for (int i = 0; i < 2; i++) {
            double dist = findDist(x, y + yi[i]);
            
            if ( Simulator3.isSafe(x,yi[i]+ y)==1 && minDist > dist ) {
                minDist = dist;
                d = yi[i]*2;
                k=1;
            }
        }
//        return;
}
    return d;
    }
    public synchronized void run(){

        while (x != goalX || y != goalY) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(Agent.class.getName()).log(Level.SEVERE, null, ex);
            }
            int x1=x, y1=y;
            int flag = nextPosition();
            int dx=0,dy=0;
            String step="";
         if(flag==0)
               return;
        if(flag==1){
            step="RIGHT";
             dx=1;
        }
        else if(flag==-1){
               step="LEFT";
             dx=-1;
        }   
        else if(flag==2){
            step="UP";
            dy=(flag/2);
        }
        else if(flag==-2){
               step="DOWN";
            dy=(flag/2);
        }   
            if((Simulator3.isSafe(x+dx, y+dy)==1 && Simulator3.sendPosition(x+dx, y+dy,x1, y1, index)==1  )|| (x+dx==goalX && y+dy==goalY))
            {
                x+=dx;
                y+=dy;  
                 System.out.println("Agent:"+ index +" moves "+ step+ " from ("+ x1 +","+y1 +") to ("+ x+","+ y+")");
              
               
            }
            else{
                flag=nextPosition();
            }
            
            
        }
         System.out.println("Agent:"+ index+" reaches target at (" + x+","+ y+")");

    }

}

public class Simulator3 extends Thread {

    static int n = 1000, m = 1000;
    static Environment env = new Environment(n, m, 4, 4);
    static synchronized int isSafe(int x1, int y1){
        
        if(x1>=0 && x1<n && y1>=0 && y1<m && env.matrix[x1][y1]==0)
        return 1;
        else return 0;
    }
    static synchronized int sendPosition(int x2, int y2,int x1, int y1, int index) {
              env.updatePosition(x2, y2, x1, y1, index);
           env.repaint();
//            
//           System.out.println("=============");
//        for(int mm=0; mm<n; mm++){
//            
//            for(int j=0; j<m; j++){
//                System.out.print(env.matrix[mm][j]+" ");
//            }
//            
//            System.out.println();
//        } 
//        System.out.println("=============");
////        env.action(1);
        System.out.println(index+" "+ x2+" "+ y2);
              return 1;
      
    }


    public static void main(String args[]) throws InterruptedException {

//        Simulator.runn(10, 10);
       int goalX=4;
       int goalY=4;
        ArrayList<Agent>xi= new ArrayList<Agent>();
         JFrame f =  new JFrame();
        f.setBounds(10,10,1500,1500);
        f.setResizable(true);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(env);

        for (int i = 1; i <=100; i++) {
                int min = 0;
                int max = 99;
                int rand_int1=(int)(Math.random() * (max - min + 1) + min);
                int rand_int2=(int)(Math.random() * (max - min + 1) + min);
                 while( env.matrix[rand_int1][rand_int2]!=0)    {
                rand_int1=(int)(Math.random() * (max - min + 1) + min);
                rand_int2=(int)(Math.random() * (max - min + 1) + min);
            }
            Agent a1 = new Agent(goalX, goalY, rand_int1, rand_int2, i);
            xi.add(a1);

        }
         for (int i = 0; i <100; i++) {
           xi.get(i).start();
      
       }
       
       for (int i = 0; i <100; i++) {
           xi.get(i).join();
           
        }
             
            
       }
       
    
        

    }
