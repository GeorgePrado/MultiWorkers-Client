package threads;


public class NHilosOptimos {  
 static   final double a=Math.pow(10,-4);// limite inferior
   static final double b=Math.pow(10,8);//limite superior
   static int n=1;
  static long  N;
  //  long time_start, time_end=0;
   public static void main(String[] argv) throws InterruptedException{
     
  

     //   System.out.println(N);
       N=100000000000L;
         System.out.println(N);
       Master master;
      for(int i=1;i<80;i++){
          
           master = new Master(a,b,n*i, N);
         //  System.out.print(n*i+"\t");
          System.out.println("Total: "+master.funcion());
             

     }
    
    
    
   }
    
}
