package tools;

import entities.lightbulb.Lightbulb;

import java.io.*;
import java.nio.file.*;

public class ObjectIO {

    public static void save(Lightbulb object) {

        try {
            FileOutputStream f = new FileOutputStream(new File("src\\assets\\objects\\lightbulbs\\" + object.getColor() + ".lightbulb"));
            ObjectOutputStream o = new ObjectOutputStream(f);

            o.writeObject(object);
            System.out.println("Object Saved");

            o.close();
            f.close();

            System.out.println("Opening Input Stream");
            FileInputStream fi = new FileInputStream(new File("src\\assets\\objects\\lightbulbs\\" + object.getColor() + ".lightbulb"));
            ObjectInputStream ois = new ObjectInputStream(fi);

            System.out.println("Building object from file");
            Lightbulb lightbulb = (Lightbulb) ois.readObject();

            ois.close();
            fi.close();

            System.out.println(lightbulb);

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public static Lightbulb open(String fileName) {
        try {
            FileInputStream fi = new FileInputStream(new File("src\\assets\\objects\\lightbulbs\\" + fileName));
            ObjectInputStream ois = new ObjectInputStream(fi);


            System.out.println("Building object from file");
            Lightbulb lightbulb = (Lightbulb) ois.readObject();
            System.out.println("derp");

            ois.close();
            fi.close();

            return lightbulb;
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        return new Lightbulb();
    }

    public static void delete(Lightbulb lightbulb) {

        String path = "src\\assets\\objects\\lightbulbs\\" + lightbulb.getColor() + ".lightbulb";
        System.out.println(path);
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


}
