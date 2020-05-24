package tools;

import entities.direction.Direction;
import entities.intersection.Intersection;
import entities.lightbulb.Lightbulb;
import entities.trafficlight.TrafficLight;
import javafx.scene.LightBase;
import javafx.scene.effect.Light;

import java.io.*;
import java.nio.file.*;

public class ObjectIO {

    public static void save(Object object) {
        String path = getPath(object);

        try {
            FileOutputStream f = new FileOutputStream(new File(path));
            ObjectOutputStream o = new ObjectOutputStream(f);

            o.writeObject(object);

            o.close();
            f.close();

            FileInputStream fi = new FileInputStream(new File(path));
            ObjectInputStream ois = new ObjectInputStream(fi);

            Object readObject = null;

            if (object instanceof Lightbulb) {
                readObject = (Lightbulb) ois.readObject();
            } else if (object instanceof TrafficLight) {
                readObject = (TrafficLight) ois.readObject();
            } else if (object instanceof Direction) {
                readObject = (Direction) ois.readObject();
            } else if (object instanceof Intersection) {
                readObject = (Intersection) ois.readObject();
            }


            ois.close();
            fi.close();

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
        } else if (fileName.contains(".direction")) {
            path = "src\\assets\\objects\\directions\\" + fileName;
        } else if (fileName.contains(".intersection")) {
            path = "src\\assets\\objects\\intersections\\" + fileName;
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

    private static String getPath(Object object) {
        String path;
        if (object instanceof Lightbulb) {
            path = "src\\assets\\objects\\lightbulbs\\" + ((Lightbulb) object).getColor() + ".lightbulb";
        } else if (object instanceof TrafficLight) {
            path = "src\\assets\\objects\\trafficlights\\" + ((TrafficLight) object).getName() + ".trafficlight";
        } else if (object instanceof Direction) {
            path = "src\\assets\\objects\\directions\\" + ((Direction) object).getName() + ".direction";
        } else if (object instanceof Intersection) {
            path = "src\\assets\\objects\\intersections\\" + ((Intersection) object).getName() + ".intersection";
        } else {
            path = "src\\assets\\objects\\";
        }
        return path;
    }


}
