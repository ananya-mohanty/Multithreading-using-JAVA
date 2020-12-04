/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulator2;

import java.util.Scanner;

/**
 *
 * @author ananya
 */

  class Environment{
    
    int n,m;
     static int x, y;
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
    // public setSize(int a, int b){
    //     n= a;
    //     m=a;
        
    // }
    public static void updatePosition(int a, int b){
        x=a;
        y=b;
    }

}

 class Agent{
    // Simulator s;

    static int x, y, goalX, goalY, n, m;
    Agent(int goalx, int goaly, int X, int Y){
        goalX=goalx;
        goalY=goaly;
        x=X;
        y=Y;
    }
    double findDist(int x1, int y1){
        int m1=(goalX-x1)*(goalX-x1);
        m1+=(goalY-y1)*(goalY-y1);
        return m1;
        
        
    }
    int nextPosition(){
        
        int []xi=new int[2];
        int []yi= new int[2];
//        
    xi[0]=-1;
    xi[1]=1;
    yi[0]=-1;
    yi[1]=1;
    double minDist=findDist(x,y);
    int d=0;
    for(int i=0; i<2; i++){
           double dist=findDist(x+xi[i], y);
//           System.out.print(dist);
           if(minDist > dist){
               minDist=dist;
               d=xi[i];
           }
    }


    for(int i=0; i<2; i++){
           double dist=findDist(x, y+yi[i]);
           if(minDist> dist){
               minDist=dist;
               d=yi[i]*2;
           }
    }
    return d;
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

        Simulator2.sendPosition(x,y);
           
        return;
    }


}

public  class Simulator2{
//    static int n=4, m=5;
    static Environment env;

   static void sendPosition(int x2, int y2){
       env.updatePosition(x2, y2);
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
       System.out.println("Agent starts working");
        while(a.x!=goalX || a.y!=goalY){
            a.run(x,y);
            x=a.x;
            y=a.y;
        }
               System.out.println("Agent reaches target at (" + a.x+","+ a.y+")");

    }
        
       


}

