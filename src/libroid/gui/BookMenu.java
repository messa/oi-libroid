package libroid.gui;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import libroid.model.Book;
import libroid.model.Model;

/**
 * Menu, které se objeví po pravém kliku v tabulce knih.
 */
class BookMenu extends JPopupMenu {

    private LibraryTable libraryTable;
    private Model model;
    private ListsInventory listsInventory;

    public BookMenu(LibraryTable lt, Model m, ListsInventory li) {
        this.libraryTable = lt;
        this.model = m;
        this.listsInventory = li;

        if (lt.getSelectedRowCount() == 1) {
            JMenuItem menuItem = new JMenuItem("Open book");
            menuItem.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    Book book = libraryTable.getSelectedBooks().get(0);
                    try {
                        File f = book.getFile();
                        if (Desktop.isDesktopSupported()) {
                            Desktop.getDesktop().open(f);
                        } else {
                            throw new IOException("Desktop not supported");
                        }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null,
                                "Couldn't open the book. Eithter the source file wasn't found or the filetype isn't supported.",
                                "An error has occured",
                                JOptionPane.WARNING_MESSAGE);
                    }
                }
            });
            add(menuItem);

            menuItem = new JMenuItem("Add to list");
            menuItem.addActionListener(new SelectListDialog(model, libraryTable.getSelectedBooks()));
            add(menuItem);

            menuItem = new JMenuItem("Remove book");
            menuItem.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    libraryTable.removeSelectedBooks();
                }
            });
            add(menuItem);

        } else {
            JMenuItem menuItem = new JMenuItem("Create new list");
            menuItem.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    listsInventory.createNewList(libraryTable.getSelectedBooks());
                }
            });
            add(menuItem);

            menuItem = new JMenuItem("Add to list");
            menuItem.addActionListener(new SelectListDialog(model, libraryTable.getSelectedBooks()));
            add(menuItem);

            menuItem = new JMenuItem("Remove books");
            menuItem.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    libraryTable.removeSelectedBooks();
                }
            });
            add(menuItem);
        }
    }
}