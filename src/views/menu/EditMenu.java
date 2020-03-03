package views.menu;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

public class EditMenu extends Menu {

    private MenuItem undoMenu;
    private MenuItem redoMenu;
    private MenuItem copyMenu;
    private MenuItem cutMenu;
    private MenuItem pasteMenu;

    public EditMenu(String text) {
        super(text);

        this.undoMenu  = new MenuItem("Undo");
        this.redoMenu  = new MenuItem("Redo");
        this.copyMenu  = new MenuItem("Copy");
        this.cutMenu  = new MenuItem("Cut");
        this.pasteMenu  = new MenuItem("Paste");
        this.getItems().addAll(undoMenu, redoMenu, copyMenu, cutMenu, pasteMenu);

    }
}
