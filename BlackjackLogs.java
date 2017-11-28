
package blackjack;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JOptionPane;

public class BlackjackLogs {
     static String  guarda = "";
    public static void main(String[] args) {

        int dinero; // Cantidad de dinero que tiene el usuario.
        int apuesta; // Cantidad de apuestas de usuario en un juego.
        boolean usuarioGana; // ¿El usuario ganó el juego?
        String cad = "";
        cad = "Bienvenido al juego de Blackjack\n";
        dinero = 100; // El usuario comienza con $ 100.

        while (true) {
            do {
                String auxdine = "Tiene " + dinero + " dólares";
                apuesta = Integer.parseInt(JOptionPane.showInputDialog(null, cad + auxdine + "\n¿Cuántos dólares quieres apostar? (Ingresa 0 para finalizar y guardar)"));
                cad = "";
                guarda += cad + auxdine;
                if (apuesta < 0 || apuesta > dinero) {
                    JOptionPane.showMessageDialog(null, "Su respuesta debe estar entre 0 y " + dinero);
                    guarda += "\n" + "Su respuesta debe estar entre 0 y " + dinero + "\n";
                    
                }
                
            } while (apuesta < 0 || apuesta > dinero);
            if (apuesta == 0) {
                guarda+= "\nSales con $ " + dinero;
                guardaDat(guarda);
                break;
            }
            usuarioGana = jugarBlackjack();
            if (usuarioGana) {
                dinero = dinero + apuesta;
            } else {
                dinero = dinero - apuesta;
            }

            if (dinero == 0) {
                JOptionPane.showMessageDialog(null, "Parece que te has quedado sin dinero.");
                guarda+="Parece que te has quedado sin dinero.";
                break;
            }
        }
        JOptionPane.showMessageDialog(null, "\nSales con $ " + dinero);
        
    }

    /**
     * Permita que el usuario juegue un juego de Blackjack , con la computadora
     * como distribuidor .
     *
     * @
     * return true si el usuario gana el juego, falso si el usuario pierde
     *
     * .
     *
     */
    static boolean jugarBlackjack() {
        Baraja baraja; // Una baraja de cartas. Una nueva baraja para cada juego.
        ManoBlackJack manoRepartidor; // La mano del crupier.
        ManoBlackJack manoUsuario; // La mano del usuario.

        baraja = new Baraja();
        manoRepartidor = new ManoBlackJack();
        manoUsuario = new ManoBlackJack();

        /* Baraja el mazo, reparte dos cartas a cada jugador. */
        baraja.barajar();
        manoRepartidor.agregarCarta(baraja.cantidadCartas());
        manoRepartidor.agregarCarta(baraja.cantidadCartas());
        manoUsuario.agregarCarta(baraja.cantidadCartas());
        manoUsuario.agregarCarta(baraja.cantidadCartas());

        /* Verifica si uno de los jugadores tiene Blackjack (dos cartas que suman un total de 21).
         El jugador con Blackjack gana el juego. El distribuidor gana lazos.
         */
        String aux1 = "";
        if (manoRepartidor.getBlackjackValor() == 21) {
            aux1 += "El distribuidor tiene la " + manoRepartidor.getCarta(0)
                    + "y" + manoRepartidor.getCarta(1) + ".";
            aux1 += "El usuario tiene la " + manoUsuario.getCarta(0)
                    + "y" + manoUsuario.getCarta(1) + ".";

            aux1 += "El distribuidor tiene Blackjack. El distribuidor gana.";
            guarda+= aux1;
            return false;
        }
        String aux2 = "";
        if (manoUsuario.getBlackjackValor() == 21) {

            aux2 += "El distribuidor tiene la " + manoRepartidor.getCarta(0)
                    + "y" + manoRepartidor.getCarta(1) + ".";
            aux2 += "El usuario tiene la " + manoUsuario.getCarta(0)
                    + "y" + manoUsuario.getCarta(1) + ".";

            aux2 += "Tienes Blackjack. Tú ganas.";
            guarda+= aux2;
            return true;
        }

        /* Si ninguno de los jugadores tiene Blackjack, juega el juego. Primero el usuario
          tiene la oportunidad de robar cartas (es decir, de "golpear"). El bucle while termina
          cuando el usuario elige "Stand". Si el usuario supera los 21,
          el usuario pierde inmediatamente.
         */
        while (true) {
            String aux3 = "";
            /* Mostrar tarjetas de usuario, y dejar que el usuario decida golpear o pararse. */

            aux3 += "Tus cartas son:\n";
            for (int i = 0; i < manoUsuario.getConteoCarta(); i++) {
                aux3 += manoUsuario.getCarta(i) + "\n";
            }
            aux3 += "Su total es: " + manoUsuario.getBlackjackValor();
            JOptionPane.showMessageDialog(null, aux3);
            aux3 += "\nEl concesionario muestra la " + manoRepartidor.getCarta(0);
            guarda+= aux3;
            String userAction = "";
            do {
                userAction = JOptionPane.showInputDialog(null, "Golpear (G) o Estar (E)?");
                guarda += "Golpear (G) o Estar (E)?\n";
                
                if (!userAction.equalsIgnoreCase("G") && !userAction.equalsIgnoreCase("E")) {
                    userAction += "Responda G o E:\n";
                    guarda+= userAction;
                }
                //userAction = (char) Character.toUpperCase(TextIO.getlnInt());
                /*if (userAction. && userAction != "E") {
                    JOptionPane.showMessageDialog(null,"Responda G o E:");
                }*/
            } while (!userAction.equalsIgnoreCase("G") && !userAction.equalsIgnoreCase("E"));

            /* Si el usuario tiene éxito, el usuario obtiene una tarjeta. Si el usuario está parado,
              el bucle termina (y es el turno del crupier de robar cartas).
             */
            if (userAction.equalsIgnoreCase("e")) {
                // el usuario ha terminado de tomar cartas.
                break;
            } else {// userAction es 'G'. Dale una tarjeta al usuario.  
                // Si el usuario supera los 21, el usuario pierde.
                Carta newCard = baraja.cantidadCartas();
                manoUsuario.agregarCarta(newCard);

                JOptionPane.showMessageDialog(null, "El usuario acierta");
                JOptionPane.showMessageDialog(null, "Su tarjeta es la " + newCard);
                JOptionPane.showMessageDialog(null, "Su total es ahora " + manoUsuario.getBlackjackValor());
                guarda+= "El usuario acierta"+"\nSu tarjeta es la \n" + newCard+"Su total es ahora " + manoUsuario.getBlackjackValor();
                if (manoUsuario.getBlackjackValor() > 21) {

                    JOptionPane.showMessageDialog(null, "Has fallado al pasar de 21. Pierdes.");
                    JOptionPane.showMessageDialog(null, "Otra tarjeta del distribuidor fue el "
                            + manoRepartidor.getCarta(1));
                    guarda+= "\nHas fallado al pasar de 21. Pierdes."+ "Otra tarjeta del distribuidor fue el "
                            + manoRepartidor.getCarta(1);
                    return false;
                }
            }

        } // fin while 

        /* Si llegamos a este punto, el usuario tiene Stood con 21 o menos. Ahora es
         la oportunidad del distribuidor para dibujar. El distribuidor roba cartas hasta que el crupier
         el total es> 16. Si el crupier supera los 21, el crupier pierde.
         */
        String aux5 = "";
        aux5 += "Usuario parado.";
        aux5 += "Las tarjetas del distribuidor son: ";
        aux5 += "" + manoRepartidor.getCarta(0);
        aux5 += "" + manoRepartidor.getCarta(1);
        while (manoRepartidor.getBlackjackValor() <= 16) {
            Carta newCard = baraja.cantidadCartas();
            JOptionPane.showMessageDialog(null, "El distribuidor golpea y obtiene la " + newCard);
            manoRepartidor.agregarCarta(newCard);
            if (manoRepartidor.getBlackjackValor() > 21) {

                JOptionPane.showMessageDialog(null, "Distribuidor detenido por pasar de 21. Usted gana.");
                return true;
            }
        }
        JOptionPane.showMessageDialog(null, "El total del distribuidor es " + manoRepartidor.getBlackjackValor());

        /* Si llegamos a este punto, ambos jugadores tienen 21 o menos. Nosotros
         puede determinar el ganador comparando los valores de sus manos. */
        if (manoRepartidor.getBlackjackValor() == manoUsuario.getBlackjackValor()) {
            JOptionPane.showMessageDialog(null, "El distribuidor gana en un empate. Pierdes.");
            return false;
        } else if (manoRepartidor.getBlackjackValor() > manoUsuario.getBlackjackValor()) {
            JOptionPane.showMessageDialog(null, "El distribuidor gana, " + manoRepartidor.getBlackjackValor()
                    + "apunta a " + manoUsuario.getBlackjackValor() + ".");
            return false;
        } else {
            JOptionPane.showMessageDialog(null,
                    "Usted gana, " + manoUsuario.getBlackjackValor()
                    + "apunta a " + manoRepartidor.getBlackjackValor() + ".");
            return true;
        }

    } // fin de jugoBlackjack() 

    static void guardaDat(String guarda) {
        File archivo = new File("logs.txt");
        try {
            FileWriter escribir = new FileWriter(archivo, true);
            escribir.write(guarda);
            escribir.close();
            JOptionPane.showMessageDialog(null, "Datos guardados con éxito");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "error al escrbir el arcivo");
        }
    }

}
