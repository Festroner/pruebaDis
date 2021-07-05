package org.Imanol;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.googlecode.gentyref.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class LectorJson {

    public ArrayList<Ips> leer_json(String ruta){
        ArrayList<Ips> lista = new ArrayList<Ips>();
        final Gson gson = new GsonBuilder().create();
        String fichero = "";

        //Leemos linea a linea el JSON y lo guardamos todo en la variable fichero
        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            String linea;
            //leemos linea a linea y lo guardamos en fichero
            while ((linea = br.readLine()) != null) {
                fichero += linea;
            }
            Reader reader = Files.newBufferedReader(Paths.get(ruta));
            System.out.println("problema");
            Type type = new TypeToken<ArrayList<Ips>>(){}.getType();
            lista = gson.fromJson(fichero,type);
            reader.close();

        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public void escribir_json(ArrayList<Ips> lista) throws IOException {
        Gson gson= new GsonBuilder().setPrettyPrinting().create();

        // create a writer
        Writer writer = Files.newBufferedWriter(Paths.get("Lista_Ips.json"));

        // convert book object to JSON file
        gson.toJson(lista, writer);

        writer.close();
    }

}
