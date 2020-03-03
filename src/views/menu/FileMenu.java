package views.menu;

import javafx.application.Platform;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import views.tools.ConfirmBox;

public class FileMenu extends Menu {

    private NewMenu newMenu;
    private MenuItem openMenu;
    private MenuItem saveMenu;
    private MenuItem exitMenu;

    public FileMenu(String text) {
        super(text);

        this.newMenu = new NewMenu("New");
        this.openMenu = new MenuItem("Open");
        this.saveMenu = new MenuItem("Save");
        this.exitMenu = new MenuItem("Exit");
        this.getItems().addAll(newMenu, openMenu, saveMenu, exitMenu);

        setActionEvents();
    }

    public void setActionEvents(){
        this.openMenu.setOnAction(e->{
            System.out.println("open file");
        });

        this.saveMenu.setOnAction(e->{
            System.out.println("save file");
        });

        this.exitMenu.setOnAction(e->{
            boolean answer = ConfirmBox.display("title", "Confirm Exit?");
            if (answer) {
                Platform.exit();
            }
        });
    }

}
