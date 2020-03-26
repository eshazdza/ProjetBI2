package tools;

import entities.lightbulb.Lightbulb;
import entities.trafficlight.TrafficLight;
import javafx.scene.LightBase;
import javafx.scene.effect.Light;

import java.io.*;
import java.nio.file.*;

public class ObjectIO {

    public static void save(Object object) {
        String path = getPath(object);

        System.out.println(path);
        try {
            FileOutputStream f = new FileOutputStream(new File(path));
            ObjectOutputStream o = new ObjectOutputStream(f);

            o.writeObject(object);
            System.out.println("Object Saved");

            o.close();
            f.close();

            System.out.println("Opening Input Stream");
            FileInputStream fi = new FileInputStream(new File(path));
            ObjectInputStream ois = new ObjectInputStream(fi);

            System.out.println("Building object from file");


            Object readObject = null;

            if (object instanceof Lightbulb) {
                readObject = (Lightbulb) ois.readObject();
            } else if (object instanceof TrafficLight) {
                readObject = (TrafficLight) ois.readObject();
            }


            ois.close();
            fi.close();

            System.out.println(readObject);

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public static Object open(String fileName) {
        String path = "src\\assets\\objects\\lightbulbs\\" + fileName;

        if (fileName.contains(".lightbulb")) {
            path = "src\\assets\\objects\\lightbulbs\\" + fileName;

        } else if (fileName.contains(".trafficlight")) {
            path = "src\\assets\\objects\\trafficlights\\" + fileName;
        }

        try {
            FileInputStream fi = new FileInputStream(new File(path));
            ObjectInputStream ois = new ObjectInputStream(fi);

            Object readObject = ois.readObject();

            ois.close();
            fi.close();

            return readObject;

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        return new Lightbulb();
    }

    public static void delete(Object object) {

        String path = getPath(object);

        try {
            Files.deleteIfExists(Paths.get(path));
        } catch (NoSuchFileException e) {
            e.printStackTrace();
        } catch (DirectoryNotEmptyException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static File[] getFilesNameFromDir(String directory) {
        File folder = new File(directory);
        return folder.listFiles();
    }

    private static String getPath(Object object){
        String path;
        if (object instanceof Lightbulb) {
            System.out.println("lightbulb");
            path = "src\\assets\\objects\\lightbulbs\\" + ((Lightbulb) object).getColor() + ".lightbulb";
        } else if (object instanceof TrafficLight) {
            System.out.println(object);
            path = "src\\assets\\objects\\trafficlights\\" + ((TrafficLight) object).getName() + ".trafficlight";
        } else {
            System.out.println("wait wat");
            path = "src\\assets\\objects\\";
        }
        return path;
    }


}
