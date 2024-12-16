package org.view;

import javafx.beans.property.adapter.JavaBeanBooleanProperty;
import javafx.beans.property.adapter.JavaBeanBooleanPropertyBuilder;
import org.team1.GameOfLifeCell;

public class GameOfLifeCellAdapter {
    private final GameOfLifeCell cell;
    private JavaBeanBooleanProperty valueProperty;

    public GameOfLifeCellAdapter(GameOfLifeCell cell) {
        this.cell = cell;
        try {
            this.valueProperty = JavaBeanBooleanPropertyBuilder.create()
                    .bean(cell)
                    .name("value")
                    .getter("getValue")
                    .setter("updateState")
                    .build();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public JavaBeanBooleanProperty valueProperty() {
        return valueProperty;
    }
}
