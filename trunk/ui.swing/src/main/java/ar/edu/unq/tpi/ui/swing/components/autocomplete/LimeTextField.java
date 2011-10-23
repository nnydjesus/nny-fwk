package ar.edu.unq.tpi.ui.swing.components.autocomplete;

import java.awt.AWTEvent;
import java.awt.Component;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.text.Format;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFormattedTextField;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.text.Document;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;

/**
 * A better JTextField.
 */
public class LimeTextField extends JFormattedTextField {

    private static final long serialVersionUID = 1L;

    /**
     * The undo action.
     */
    private static Action UNDO_ACTION = new FieldAction("UNDO") {
        private static final long serialVersionUID = 1L;

        public void actionPerformed(final ActionEvent e) {
            this.getField(e).undo();
        }
    };

    /**
     * The cut action
     */
    private static Action CUT_ACTION = new FieldAction("CUT") {
        private static final long serialVersionUID = 1L;

        public void actionPerformed(final ActionEvent e) {
            this.getField(e).cut();
        }
    };

    /**
     * The copy action.
     */
    private static Action COPY_ACTION = new FieldAction("COPY") {
        private static final long serialVersionUID = 1L;

        public void actionPerformed(final ActionEvent e) {
            this.getField(e).copy();
        }
    };

    /**
     * The paste action.
     */
    private static Action PASTE_ACTION = new FieldAction("PASTE") {
        private static final long serialVersionUID = 1L;

        public void actionPerformed(final ActionEvent e) {
            this.getField(e).paste();
        }
    };

    /**
     * The delete action.
     */
    private static Action DELETE_ACTION = new FieldAction("DELETE") {
        private static final long serialVersionUID = 1L;

        public void actionPerformed(final ActionEvent e) {
            this.getField(e).replaceSelection("");
        }
    };

    /**
     * The select all action.
     */
    private static Action SELECT_ALL_ACTION = new FieldAction("SELECT_ALL") {
        private static final long serialVersionUID = 1L;

        public void actionPerformed(final ActionEvent e) {
            this.getField(e).selectAll();
        }
    };

    /**
     * The sole JPopupMenu that's shared among all the text fields.
     */
    private static final JPopupMenu POPUP = createPopup();

    /**
     * Our UndoManager.
     */
    private UndoManager undoManager;

    /**
     * Constructs a new LimeTextField.
     */
    public LimeTextField(Format format) {
        super(format);
    }
    public LimeTextField() {
        super();
        this.init();
    }

    /**
     * Constructs a new LimeTextField with the given text.
     */
    public LimeTextField(final String text) {
        super(text);
        this.init();
    }

    /**
     * Constructs a new LimeTextField with the given amount of columns.
     */
    public LimeTextField(final int columns) {
        super(columns);
        this.init();
    }

    /**
     * Constructs a new LimeTextField with the given text & number of columns.
     */
    public LimeTextField(final String text, final int columns) {
//        super(text, columns);
        this.init();
    }

    /**
     * Constructs a new LimeTextField with the given document, text, and
     * columns.
     */
    public LimeTextField(final Document doc, final String text, final int columns) {
//        super(doc, text, columns);
        this.init();
    }

    /**
     * Undoes the last action.
     */
    public void undo() {
        try {
            if (undoManager != null) {
                undoManager.undoOrRedo();
            }
        } catch (CannotUndoException ignored) {
        } catch (CannotRedoException ignored) {
        }
    }

    /**
     * Sets the UndoManager (but does NOT add it to the document).
     */
    public void setUndoManager(final UndoManager undoer) {
        undoManager = undoer;
    }

    /**
     * Gets the undo manager.
     */
    public UndoManager getUndoManager() {
        return undoManager;
    }

    /**
     * Intercept the 'setDocument' so that we can null out our manager and
     * possibly assign a new one.
     */
    @Override
    public void setDocument(final Document doc) {
        if (doc != this.getDocument()) {
            undoManager = null;
        }
        super.setDocument(doc);
    }

    /**
     * Initialize the necessary events.
     */
    private void init() {
        // TODO: when we compile with java 1.5, do this.
        // if(CommonUtils.isJava15OrLater())
        // setComponentPopupMenu(POPUP);
        // else
        this.enableEvents(AWTEvent.MOUSE_EVENT_MASK);

        undoManager = new UndoManager();
        undoManager.setLimit(1);
        this.getDocument().addUndoableEditListener(undoManager);
    }

    /**
     * Intercept mouse events if we're below Java 1.5 (which doesn't have
     * internal PopupMenu support), so we can show the popup menu without having
     * to add a listener.
     */
    @Override
    protected void processMouseEvent(final MouseEvent e) {
        super.processMouseEvent(e);

        // TODO: do not do if we compile with java 1.5
        // if(!CommonUtils.isJava15OrLater()) {
        if (!e.isConsumed() && POPUP.isPopupTrigger(e)) {
            e.consume();
            POPUP.show(this, e.getX(), e.getY());
        }
        // }
    }

    /**
     * Creates the JPopupMenu that all LimeTextFields will share.
     */
    private static JPopupMenu createPopup() {
        JPopupMenu popup;

        // initialize the JPopupMenu with necessary stuff.
        popup = new JPopupMenu() {
            private static final long serialVersionUID = 1L;

            @Override
            public void show(final Component invoker, final int x, final int y) {
                ((LimeTextField) invoker).updateActions();
                super.show(invoker, x, y);
            }
        };

        JMenuItem undo = new JMenuItem(UNDO_ACTION);
        undo.setText("deshacer");
//        undo.setText("undo");
        popup.add(undo);
        popup.addSeparator();
        JMenuItem cut = new JMenuItem(CUT_ACTION);
        cut.setText("cortar");
//        cut.setText("cut");
        popup.add(cut);
        JMenuItem copy = new JMenuItem(COPY_ACTION);
        copy.setText("copiar");
//        copy.setText("copy");
        popup.add(copy);
        JMenuItem paste = new JMenuItem(PASTE_ACTION);
        paste.setText("pegar");
//        paste.setText("paste");
        popup.add(paste);
        JMenuItem delete = new JMenuItem(DELETE_ACTION);
        delete.setText("borrar");
//        delete.setText("delete");
        popup.add(delete);
        popup.addSeparator();
        JMenuItem select = new JMenuItem(SELECT_ALL_ACTION);
        select.setText("seleccionar todo");
//        select.setText("select all");
        popup.add(select);
        return popup;
    }

    /**
     * Updates the actions in each text just before showing the popup menu.
     */
    private void updateActions() {
        String selectedText = this.getSelectedText();
        if (selectedText == null) {
            selectedText = "";
        }

        boolean stuffSelected = !selectedText.equals("");
        boolean allSelected = selectedText.equals(this.getText());

        UNDO_ACTION.setEnabled(this.isEnabled() && this.isEditable() && this.isUndoAvailable());
        CUT_ACTION.setEnabled(this.isEnabled() && this.isEditable() && stuffSelected);
        COPY_ACTION.setEnabled(this.isEnabled() && stuffSelected);
        PASTE_ACTION.setEnabled(this.isEnabled() && this.isEditable() && this.isPasteAvailable());
        DELETE_ACTION.setEnabled(this.isEnabled() && stuffSelected);
        SELECT_ALL_ACTION.setEnabled(this.isEnabled() && !allSelected);
    }

    /**
     * Determines if an Undo is available.
     */
    private boolean isUndoAvailable() {
        return this.getUndoManager() != null && this.getUndoManager().canUndoOrRedo();
    }

    /**
     * Determines if paste is currently available.
     */
    private boolean isPasteAvailable() {
        try {
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

            // TODO: When we compile with Java 1.5, do this:
            // if(CommonUtils.isJava15OrLater()) {
            // return clipboard.isDataFlavorAvailable(DataFlavor.stringFlavor);
            // } else {
            Transferable contents = clipboard.getContents(this);
            return contents != null && contents.isDataFlavorSupported(DataFlavor.stringFlavor);
            // }
        } catch (UnsupportedOperationException he) {
            return false;
        } catch (IllegalStateException ise) {
            return false;
        }
    }

    /**
     * Base Action that all LimeTextField actions extend.
     */
    private static abstract class FieldAction extends AbstractAction {

        private static final long serialVersionUID = 1L;

        /**
         * Constructs a new FieldAction looking up the name from the
         * MessagesBundles.
         */
        public FieldAction(final String name) {
            // super(GUIMediator.getStringResource("CONTEXT_MENU_" + name));
        }

        /**
         * Gets the LimeTextField for the given ActionEvent.
         */
        protected LimeTextField getField(final ActionEvent e) {
            JMenuItem source = (JMenuItem) e.getSource();
            JPopupMenu menu = (JPopupMenu) source.getParent();
            return (LimeTextField) menu.getInvoker();
        }

    }
}