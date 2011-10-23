package ar.edu.unq.tpi.ui.swing.components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import ar.edu.unq.tpi.util.common.ReflectionUtils;

public class ActionMethodListener implements ActionListener, ChangeListener, MouseListener, KeyListener, FocusListener {

    private String method;

    private Object object;

    private Object[] args;


    public ActionMethodListener(final Object object, final String method, final Object... args) {
        this.object = object;
        this.method = method;
        this.args = args;
    }
    
    public void execute(){
        ReflectionUtils.invokeMethod(object, method, args);
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        ReflectionUtils.invokeMethod(object, method, args);
    }

    @Override
    public void stateChanged(final ChangeEvent e) {
        ReflectionUtils.invokeMethod(object, method);
    }

    @Override
    public void mouseClicked(final java.awt.event.MouseEvent e) {
        // ReflectionUtils.invokeMethod(object, method);
    }

    @Override
    public void mousePressed(final java.awt.event.MouseEvent e) {
        // ReflectionUtils.invokeMethod(object, method);
    }

    @Override
    public void mouseReleased(final java.awt.event.MouseEvent e) {
        // ReflectionUtils.invokeMethod(object, method);
    }

    @Override
    public void mouseEntered(final java.awt.event.MouseEvent e) {
        // ReflectionUtils.invokeMethod(object, method);
    }

    @Override
    public void mouseExited(final java.awt.event.MouseEvent e) {
        // ReflectionUtils.invokeMethod(object, method);
    }

    @Override
    public void keyTyped(final KeyEvent e) {
        // ReflectionUtils.invokeMethod(object, method);
    }

    @Override
    public void keyPressed(final KeyEvent e) {
         ReflectionUtils.invokeMethod(object, method);
    }

    @Override
    public void keyReleased(final KeyEvent e) {
        // ReflectionUtils.invokeMethod(object, method);
    }

    @Override
    public void focusGained(final FocusEvent e) {
//        ReflectionUtils.invokeMethod(object, method);
    }

    @Override
    public void focusLost(final FocusEvent e) {
        ReflectionUtils.invokeMethod(object, method);
    }
    
}
