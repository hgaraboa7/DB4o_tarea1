/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package gestorBD;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Acceso a datos
 */
public class Llamada {
    public static void main(String[] args) {
                
        String[] comando={"C:\\Program Files\\Java\\jdk-13.0.2\\bin\\java","-jar","./src/main/java/gestorBD/objectmanager.jar"}; 
        
        try {
            Process p = Runtime.getRuntime().exec(comando);
        } catch (IOException ex) {
            Logger.getLogger(Llamada.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
