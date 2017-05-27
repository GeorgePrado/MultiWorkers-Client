
package threads;

import java.util.logging.Level;
import java.util.logging.Logger;

public class saludoHilos extends Thread{
    String saludo;
    private long delay;
    public saludoHilos(String saludo){
        super(saludo);
        delay =100;
 
    }
    
    
    public void run(){
        
        for (int i = 0; i < 10; i++) {           
        
        System.out.println(this.getName());
         
        }
       
      }
    
    
}
