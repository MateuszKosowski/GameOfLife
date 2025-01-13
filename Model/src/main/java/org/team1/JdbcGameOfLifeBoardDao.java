package org.team1;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcGameOfLifeBoardDao implements Dao<GameOfLifeBoard>, AutoCloseable{

    private Connection connection;

    private static final String URL = "jdbc:postgresql://localhost:5432/JavaGOL";
    private static final String USER = "postgres";
    private static final String PASSWORD = "Qwerty12.,";

    protected static final Bundle bundle = Bundle.getInstance();
    private static final Logger logger = LogManager.getLogger(JdbcGameOfLifeBoardDao.class);

    public JdbcGameOfLifeBoardDao() {
        connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            connection.setAutoCommit(false);
            logger.info(bundle.getString("db.connection.success"));
        } catch (SQLException e) {
            logger.error(bundle.getString("db.connection.error"), e);
        }
    }

    @Override
    public GameOfLifeBoard read(String name) throws GolReadExp {
        String sql = "SELECT name, height, width FROM Board WHERE name = " + name;

        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String name1 = resultSet.getString("name");
                int height = resultSet.getInt("height");
                int width = resultSet.getInt("width");
                return new GameOfLifeBoard(name1, height, width);
            }

        } catch (SQLException e) {
            logger.error(bundle.getString("db.read.error"), e);
        }
        return null;
    }

    @Override
    public void write(GameOfLifeBoard board) {
        String sql = "INSERT INTO board (name, width, height) VALUES (?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            GameOfLifeCell[][] gameBoard = board.getBoard();
            statement.setString(1, board.getName());
            statement.setInt(2, gameBoard.length);
            statement.setInt(3, gameBoard[0].length);
            statement.executeUpdate();
            logger.info(bundle.getString("db.insert.success"));

        } catch (SQLException e) {
            logger.error(bundle.getString("db.insert.error"), e);
        }
    }

    @Override
    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            logger.error(bundle.getString("db.connection.error"), e);
        }

    }
}
