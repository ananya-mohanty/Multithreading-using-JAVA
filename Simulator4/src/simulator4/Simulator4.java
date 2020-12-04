/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulator4;

/**
 *
 * @author ananya
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
//import static simulator4.Simulator4.n;
/**
 *
 * @author ananya
 */
class Environment extends JPanel implements ActionListener{

    int n, m;
     int x, y;
 int goalX, goalY;
     int[][] matrix;
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

    Environment() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
   
   public void paint(Graphics g){
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        int factor=Math.max(n,m)/2;
        Ellipse2D.Double circle = new Ellipse2D.Double(goalX*factor, goalY*factor, factor, factor);

        g2d.setColor(Color.GRAY);
        g2d.fill(circle);
        
        for(int i=0; i<n; i++){
            for(int j=0; j<m; j++){
                 g.drawRect(i*factor,j*factor,factor,factor);
                 g.setColor(Color.BLACK);
                if(matrix[i][j]!=0){
                    
                    int t=matrix[i][j];
                     Ellipse2D.Double circle2 = new Ellipse2D.Double(i*factor, j*factor, factor, factor);
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
//        if(matrix[x1][y1]!=index){
//            System.out.println("DANGER1");
//            return;
//        }
            
        matrix[x1][y1] = 0;
//         if(matrix[a][b]!=0){
//             System.out.println("DANGER2");
//             return;
//         }
//               
        if(a!=goalX && b!=goalY)
        matrix[a][b] = index;

        repaint();
        return;
    }
    public void removepoint(int x1, int y1){
         matrix[x1][y1] = 0;
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

    synchronized int nextPosition() {

        int[] xi = new int[2];
        int[] yi = new int[2];
//        
        xi[0] = -1;
        xi[1] = 1;
        yi[0] = -1;
        yi[1] = 1;
        double minDist =999999;
        int d=0, k=0;
while(k==0){
     d = 0;
        for (int i = 0; i < 2; i++) {
            double dist = findDist(x + xi[i], y);
           
            if (Simulator4.isSafe(x+xi[i], y)==1 && minDist > dist) {
                minDist = dist;
                d = xi[i];
                k=1;
            }
        }
        
        for (int i = 0; i < 2; i++) {
            double dist = findDist(x, y + yi[i]);
            
            if ( Simulator4.isSafe(x,yi[i]+ y)==1 && minDist > dist ) {
                minDist = dist;
                d = yi[i]*2;
                k=1;
            }
        }
   
}
        
            
        return d;
//        return;
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
         if(flag==0){
             continue;
         }
               
         else if(flag==1){
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
           int min = 0;
        int max = 9;
                int rand_int1=(int)(Math.random() * (max - min + 1) + min);
            if(rand_int1!=0 && (Simulator4.isSafe(x+dx, y+dy)==1 && Simulator4.sendPosition(x+dx, y+dy,x1, y1, index)==1  )|| (x+dx==goalX && y+dy==goalY))
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

public class Simulator4 extends Thread{

//     static int n=50, m=50, goalX=4, goalY=4;
    static Environment env;
     static int isSafe(int x1, int y1){
//        int min = 0;
//        int max = 9;
//          env.matrix[env.goalX][env.goalY]=0;
        if(x1>=0 && x1<env.n && y1>=0 && y1<env.m && env.matrix[x1][y1]==0)
        return 1;
        else return 0;
    }
     static synchronized int sendPosition(int x2, int y2,int x1, int y1, int index) {
//        if( isSafe(x2,y2)==1)
//   Environment env = new Environment();
              env.updatePosition(x2, y2, x1, y1, index);
//        else if(k==0)
//            env.removepoint(x2,y2);
           env.repaint();
            
//           System.out.println("=============");
        
////        env.action(1);
        System.out.println(index+" "+ x2+" "+ y2);
              return 1;
      
    }


    public static void main(String args[]) throws InterruptedException {

//        Simulator.runn(10, 10);
        Scanner sc = new Scanner(System.in);

        // System.out.println(LLLvjev);
        int  n= sc.nextInt();
        // int m=sc.
       
       Scanner sc = new Scanner(System.in);
     
       System.out.print("Enter size of matrix: N and M :");
       int n=sc.nextInt();
        int m=sc.nextInt();
////          Environment env= new Environment(n, m);
       System.out.print("Enter goalX and goalY :");
       int goalX=sc.nextInt();
       int goalY=sc.nextInt();
       env = new Environment(n, m, goalX, goalY);
        ArrayList<Agent>xi= new ArrayList<Agent>();
        JFrame f =  new JFrame();
        f.setBounds(10,10,1000,1500);
        f.setResizable(true);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(env);

        for (int i = 0; i <10; i++) {
//            int rand_int1 = i;
//            int rand_int2 = i;
                int min = 0;
                int maxx = n-1;
                int maxy = m-1;
                 System.out.println("Enter x and y coordinates :");
                 int rand_int1=sc.nextInt();
                 int rand_int2 = sc.nextInt();
//                int rand_int1=(int)(Math.random() * (maxx - min + 1) + min);
//                int rand_int2=(int)(Math.random() * (maxy - min + 1) + min);
//            while( env.matrix[rand_int1][rand_int2]!=0)    {
//                rand_int1=(int)(Math.random() * (maxx - min + 1) + min);
//                rand_int2=(int)(Math.random() * (maxy - min + 1) + min);
//            }
            Agent a1 = new Agent(goalX, goalY, rand_int1, rand_int2, i+1);
            env.matrix[rand_int1][rand_int2]=i+1;
            xi.add(a1);

        }
         for (int i = 0; i <10; i++) {
           xi.get(i).start();
      
       }
       
       for (int i = 0; i <10; i++) {
           xi.get(i).join();
           
        }
             
            
       }
       
    
        

    }



