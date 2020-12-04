/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulator1;

import java.util.Scanner;

/**
 *
 * @author ananya
 */

 class Environment{
    
    int n,m;
      int x, y;
//    private int goalX, goalY;
    private int [][] matrix;
    int steps;

    Environment(int N, int M){

        n=N;
        m=M;
        matrix = new int[n][m];
        for(int i=0; i<n; i++){
            for(int j=0; j<m; j++)
            matrix[i][j]=0;
        }
        // goalX = goalx;
        // goalY = goaly;
    }
    
    public void updatePosition(int a, int b){
//        System.out.println(x+", "+ y+" becomes ");
//        System.out.print(a +", "+ b);
        x=a;
        y=b;
    }

}

 class Agent{

     int x, y, goalX, goalY, n, m;
    Agent(int goalx, int goaly, int X, int Y){
        goalX=goalx;
        goalY=goaly;
        x=X;
        y=Y;
    }
    int nextPosition(){
        int i=Math.abs(goalX-x);
        int j=Math.abs(goalY-y);
        if(i>j){
            if(goalX-x>0)
            return 1;
             if(goalX-x<0)
            return -1;
        }
        else
            if(goalY-y>0)
            return 2;
        else     if(goalY-y<0)
            return -2;
        return 0;

    }
    
    void run(int x2, int y2){

         x = x2;
         y=y2;
         String step="";
        int flag = nextPosition();
        if(flag==0){
            return;
            
        }
        if(flag==1){
            step="RIGHT";
             x+=flag;
        }
        else if(flag==-1){
               step="LEFT";
             x+=flag;
        }   
        else if(flag==2){
            step="UP";
            y+=(flag/2);
        }
        else if(flag==-2){
               step="DOWN";
            y+=(flag/2);
        }   
            
        System.out.println("Agent moves "+ step+ " from ("+ x2 +","+y2 +") to ("+ x+","+ y+")");
        Simulator1.sendPosition(x,y);
        return;
    }

}

public class Simulator1{
//    static int n=4, m=5;
    static Environment env;

   static void sendPosition(int x1, int y1){
   
       env.updatePosition(x1, y1);
   }
    public static void main(String args[]){
       
        Scanner sc = new Scanner(System.in);
     
       System.out.print("Enter size of matrix: N and M :");
       int n=sc.nextInt();
        int m=sc.nextInt();
////          Environment env= new Environment(n, m);
       System.out.print("Enter goalX and goalY :");
       int goalX=sc.nextInt();
       int goalY=sc.nextInt();
       
       env = new Environment(n,m);
       System.out.print("Enter x and y :");
       int x=sc.nextInt();
       int y=sc.nextInt();
       Agent a = new Agent(goalX, goalY,x,y);
       env.updatePosition(x,y);
       System.out.println("Agent starts working");
//       run();
        while(a.x!=goalX || a.y!=goalY){
            a.run(x,y);
            x=a.x;
            y=a.y;
        }
    
              System.out.println("Agent reaches target at (" + a.x+","+ a.y+")");
//        }
       
       
    }
        
       
        // env.setSize(n,m);
        


}