package threads;


//esta clase qyue extiend de Thread calculara la integral de [a, b];
// de la funcion (x-2556)(x-3584)(x-2345)(x-2475)(x-2987) 
public class Masterfunc implements Runnable{
   public double a;
    double b;
    String message;
   final long partition;
  public double integral;
        
    public Masterfunc(String message,double a,double b, long partition){
    this.message=message;
    this.a=a;
    this.b=b;
    this.partition=partition;
    }
        //calculando la integral por el metodo del rectangulo
    public void run(){
        integral=0;
        double steps= (b-a)/partition;
        
            for(double i=a;i<b;i+=steps){
                this.integral+=func(i)*steps;
            
            }
            
   //    System.out.println(message+"solucion"+integral);
    
    }
    public double func(double x){
    
    double f;
     f=(x-2556000)*(x-3584000)*(x-2345000)*(x-2475000)*(x-2987000); 
    return f;
    }

    public double getIntegral() {
        return integral;
    }

    public void setIntegral(double integral) {
        this.integral = integral;
    }
    
    
    
    
}
