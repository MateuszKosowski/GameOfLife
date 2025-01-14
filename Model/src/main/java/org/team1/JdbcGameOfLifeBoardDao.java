package org.team1;

/*-
 * #%L
 * GameOfLife
 * %%
 * Copyright (C) 2024 - 2025 Zespol1
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

public class JdbcGameOfLifeBoardDao implements Dao<GameOfLifeBoard>, AutoCloseable {

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
        String selectBoardSql = "SELECT id_board, name, height, width FROM board WHERE name = ?";
        String selectCellsSql = "SELECT value, pos_x, pos_y FROM cell WHERE id_board = ?";

        try (PreparedStatement boardStatement = connection.prepareStatement(selectBoardSql);
             PreparedStatement cellStatement = connection.prepareStatement(selectCellsSql)) {

            // Ustawienie parametru nazwy planszy
            boardStatement.setString(1, name);

            try (ResultSet boardResultSet = boardStatement.executeQuery()) {
                if (boardResultSet.next()) {
                    int boardId = boardResultSet.getInt("id_board");
                    String boardName = boardResultSet.getString("name");
                    int height = boardResultSet.getInt("height");
                    int width = boardResultSet.getInt("width");

                    GameOfLifeBoard board = new GameOfLifeBoard(boardName, height, width);

                    // Ustawienie parametru id planszy do zapytania o komórki
                    cellStatement.setInt(1, boardId);

                    try (ResultSet cellResultSet = cellStatement.executeQuery()) {
                        while (cellResultSet.next()) {
                            boolean value = cellResultSet.getBoolean("value");
                            int posX = cellResultSet.getInt("pos_x");
                            int posY = cellResultSet.getInt("pos_y");

                            board.set(posX, posY, value);
                        }
                    }

                    logger.info(bundle.getString("db.read.success"));
                    return board;
                } else {
                    logger.warn(bundle.getString("db.read.not_found"));
                }
            }

        } catch (SQLException e) {
            logger.error(bundle.getString("db.read.error"), e);
        }

        return null;
    }


    @Override
    public void write(GameOfLifeBoard board) throws GolWriteExp {
        String sql = "INSERT INTO board (name, width, height) VALUES (?, ?, ?)";
        String sql1 = "INSERT INTO cell (value, pos_x, pos_y, id_board) VALUES (?, ?, ?, ?)";

        try (PreparedStatement boardStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement cellStatement = connection.prepareStatement(sql1)
        ) {

            GameOfLifeCell[][] gameBoard = board.getBoard();
            boardStatement.setString(1, board.getName());
            boardStatement.setInt(2, gameBoard.length);
            boardStatement.setInt(3, gameBoard[0].length);
            boardStatement.executeUpdate();

            ResultSet generatedKeys = boardStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int boardId = generatedKeys.getInt(1);

                for (int i = 0; i < gameBoard.length; i++) {
                    for (int j = 0; j < gameBoard[0].length; j++) {
                        cellStatement.setBoolean(1, gameBoard[i][j].getValue());
                        cellStatement.setInt(2, i);
                        cellStatement.setInt(3, j);
                        cellStatement.setInt(4, boardId);
                        cellStatement.addBatch();
                    }
                }

                cellStatement.executeBatch();
                connection.commit();
                generatedKeys.close();
            }

            logger.info(bundle.getString("db.insert.success"));

        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException rollbackEx) {
                logger.error(bundle.getString("db.rollback.error"), rollbackEx);
                throw new GolWriteExp(rollbackEx);
            }
            logger.error(bundle.getString("db.insert.error"), e);
            throw new GolWriteExp(e);
        }
    }

    @Override
    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                logger.info(bundle.getString("db.connection.close"));
            }
        } catch (SQLException e) {
            logger.error(bundle.getString("db.connection.error"), e);
        }

    }
}
