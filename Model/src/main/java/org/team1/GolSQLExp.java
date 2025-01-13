package org.team1;

import java.sql.SQLException;

public class GolSQLExp extends SQLException {

    protected static final Bundle bundle = Bundle.getInstance();

    public GolSQLExp(Throwable cause) {
        super(bundle.getString("exp.sql.error"), cause);
    }
}
