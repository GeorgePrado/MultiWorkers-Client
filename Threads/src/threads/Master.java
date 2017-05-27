package threads;

import threads.Masterfunc;

public class Master {
   final double a;//=Math.pow(10,-4);// limite inferior
   final double b;//=Math.pow(10,8);//limite superior
   final int n;//numero de hilos
   final long N;//numero de particviones cada hilo tendra Nparticiones/n particiones cuyo limites seran
   long time_start, time_end=0;

  
   
    public Master(double a,double b,int n,long N){
        this.a=a;
        this.b=b;
        this.n=n;
        this.N=N;
    
    }
    
 public double funcion() throws InterruptedException{      
  double total=0;
    final double steps= (b-a)/n;   
   
    
    Masterfunc[] hilos= new Masterfunc[n];
    Thread[] thread= new Thread[n];
    time_start = System.currentTimeMillis(); 
    // System.out.println("time star"+time_start);
    for(int i=0;i<n;i++){        
            double begin= a+ steps*i;
            double end= a+steps*(i+1);
            hilos[i]= new Masterfunc(Integer.toString(i+1),begin,end, N/n);// se define los valores de la integral  , N/n numero de particiones de cada hilo          
            thread[i] = new Thread(hilos[i]);
            thread[i].start();   
    }        
    
       
  //   System.out.println("time end"+time_end);
    for (int i = 0; i < n; i++) {
            thread[i].join();//el master espera a que todos los hilos hayan ejecuado su tarea
            
            total=total+hilos[i].integral; //se suma el total de cada hilo que vendria a hacer la integral del total con N particiones       

    }
    
    
    
    time_end = System.currentTimeMillis();     
   //System.out.println( ( time_end - time_start ));      
   //System.out.println(total);
   return total; 
    
  
        
 }


}
