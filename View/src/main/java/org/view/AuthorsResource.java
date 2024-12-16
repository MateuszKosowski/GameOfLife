package org.view;

import java.util.ListResourceBundle;

public class AuthorsResource extends ListResourceBundle {

    @Override
    protected Object[][] getContents() {
        return new Object[][]{
                {"author1", "Mateusz Kosowski"},
                {"author2", "Nikodem Nowak"}
        };
    }
}
