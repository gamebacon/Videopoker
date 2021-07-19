package net.gamebacon.videopoker.builder;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;

public class JBuilder {
    
    private final JComponent component;
    private final Class clazz;


    private JBuilder(Class <? extends JComponent> clazz) {
        try {
            this.component = clazz.newInstance();
            this.clazz = clazz;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new IllegalArgumentException(clazz.getName() +  " is not a " + JComponent.class.getName());
        }
    }

    public static JBuilder init(final Class<? extends JComponent> clazz) {
        return new JBuilder(clazz);
    }

    public JBuilder font(Font font) {
        component.setFont(font); return this;
    }

    public JBuilder opaque(boolean opaque) {
        component.setOpaque(opaque); return this;
    }


    @Deprecated
    public JBuilder columns(int columns) {
        try {
            ((JTextField) component).setColumns(columns);
        } catch (ClassCastException e) {
            throw new UnsupportedOperationException(String.format("%s is not a %s", component.getClass().getName(), JTextField.class.getName()));
        }
        return this;
    }

    @Deprecated
    public JBuilder editable(boolean editable) {
        try {
            ((JTextComponent) component).setEditable(editable);
        } catch (ClassCastException e) {
            throw new UnsupportedOperationException(String.format("%s is not a %s", component.getClass().getName(), JTextComponent.class.getName()));
        }
        return this;
    }

    @Deprecated
    public JBuilder text(String text) {
        if(component instanceof AbstractButton)
            ((AbstractButton) component).setText(text);
        else if(component instanceof JTextComponent)
            ((JTextComponent) component).setText(text);
        else
            throw new UnsupportedOperationException(String.format("%s is not a text component", component.getClass().getName()));

        return this;
    }


    public JBuilder border(Border border) {
        component.setBorder(border); return this;
    }

    public JBuilder toolTip(String text) {
        component.setToolTipText(text); return this;
    }
    public JBuilder preferredSize(Dimension dimension) {
        component.setPreferredSize(dimension); return this;
    }

    public JBuilder keyListener(KeyListener listener) {
        component.addKeyListener(listener); return this;
    }

    public JBuilder background(Color color) {
        component.setBackground(color); return this;
    }

    public JBuilder foreground(Color color) {
        component.setForeground(color); return this;
    }


    @Deprecated
    public JBuilder actionListener(ActionListener listener) {
        try {
            ((AbstractButton) component).addActionListener(listener);
        } catch (ClassCastException e) {
            throw new UnsupportedOperationException(String.format("%s is not a %s", component.getClass().getName(), AbstractButton.class.getName()));
        }
        return this;
    }

   public JComponent bulid() {
        return component;
   }
   
}
