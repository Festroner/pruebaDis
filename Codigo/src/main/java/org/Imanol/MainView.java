package org.Imanol;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

import javax.management.Notification;
import java.awt.*;
import java.util.ArrayList;

/**
 * The main view contains a button and a click listener.
 */
@Route
public class MainView extends VerticalLayout {

    public MainView() {

        LectorJson lector =new LectorJson();
        ArrayList<Ips> lista = lector.leer_json("C:\\Users\\Festr\\OneDrive\\Escritorio\\DIS Extraordinaria\\Codigo\\src\\main\\java\\org\\Imanol\\LocalizaIP.json");

        TextField Texto1= new TextField("Introduce la Ip en formato '192.168.100.1'");
        TextField Resultado = new TextField("El resultado es: ");
        long numero = 0L;


        Button Calculo = new Button("Click me");
        Calculo.addClickListener(e -> {
                long num = Dot2LongIP(Texto1.getValue());
                System.out.println(Texto1.getValue());
                System.out.println("Largo: "+num+"");
                Ips encontrada = Encontrar(num,lista);
                Ips no = new Ips();
                if (encontrada.equals(no)){
                    String desde = longToIp(encontrada.getIp_from());
                    String hasta = longToIp(encontrada.getIp_to());
                    String mensaje = "La ip proporcionada se encuentra entre "+desde+" y "+hasta;
                    com.vaadin.flow.component.notification.Notification f = new com.vaadin.flow.component.notification.Notification(mensaje,3000, com.vaadin.flow.component.notification.Notification.Position.MIDDLE);
                    f.open();
                    Resultado.setValue("La ip proporcionada se encuentra entre "+desde+" y "+hasta);
                }
                else {
                    String aviso= "No encaja con ningún rango de IPs";
                    com.vaadin.flow.component.notification.Notification f = new com.vaadin.flow.component.notification.Notification(aviso,3000, com.vaadin.flow.component.notification.Notification.Position.MIDDLE);
                    Resultado.setValue("No encaja con ningún rango de IPs");
                    f.open();
                }
                
            });



        Texto1.setLabel(longToIp(lista.get(5).getIp_to()));
        add(Texto1,Calculo, Resultado);
    }

    public static Ips Encontrar(long ip, ArrayList<Ips> lista){

        for (Ips i : lista){

            if (i.getIp_from()<= ip && i.getIp_to()>=ip){
                System.out.println("Encontrada");
                return i;

            }
        }
        return new Ips();
    }

    public static String longToIp(long ip) {
        StringBuilder result = new StringBuilder(15);
        for (int i = 0; i < 4; i++) {
            result.insert(0, Long.toString(ip & 0xff));
            if (i < 3) {
                result.insert(0, '.');
            }
            ip = ip >> 8;
        }
        return result.toString();
    }

    public static Long Dot2LongIP(String dottedIP) {
        String[] addrArray = dottedIP.split("\\.");
        long num = 0;
        for (int i=0;i<addrArray.length;i++) {
            int power = 3-i;
            num += ((Integer.parseInt(addrArray[i]) % 256) *
                    Math.pow(256,power));
        }
        return num;
    }
}
