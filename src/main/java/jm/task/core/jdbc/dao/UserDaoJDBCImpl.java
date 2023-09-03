package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        Util util = new Util();
        String sql = """
                CREATE TABLE IF NOT EXISTS `mydb`.`Users` (
                  `id` BIGINT NOT NULL AUTO_INCREMENT,
                  `name` VARCHAR(45) NOT NULL,
                  `lastName` VARCHAR(45) NOT NULL,
                  `age` TINYINT(45) NOT NULL,
                  PRIMARY KEY (`id`));
                """;

        try (PreparedStatement preparedStatement = util.getConnection().prepareStatement(sql)) {
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

        public void dropUsersTable () {
            Util util = new Util();
            String sql = "DROP TABLE IF EXISTS `mydb`.`Users`;";

            try (PreparedStatement preparedStatement = util.getConnection().prepareStatement(sql)){
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e.getMessage());
            }
        }

        public void saveUser (String name, String lastName,byte age){
            Util util = new Util();
            String sql = "INSERT INTO `mydb`.`Users`(`name`,`lastName`,`age`) VALUES (?,?,?);";

            try (PreparedStatement preparedStatement = util.getConnection().prepareStatement(sql)){
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, lastName);
                preparedStatement.setByte(3, age);

                preparedStatement.executeUpdate();
                System.out.println("User c именем - " + name + " добавлен в базу данных");
            } catch (SQLException e) {
                throw new RuntimeException(e.getMessage());
            }
        }

        public void removeUserById ( long id){
            Util util = new Util();
            String sql = "DELETE FROM `mydb`.`Users` WHERE id=?;";

            try (PreparedStatement preparedStatement = util.getConnection().prepareStatement(sql)){
                preparedStatement.setLong(1, id);

                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e.getMessage());
            }
        }

        public List<User> getAllUsers () {
            List<User> usersList = new ArrayList<>();
            Util util = new Util();
            String sql = "SELECT * FROM mydb.Users;";

            try (PreparedStatement preparedStatement = util.getConnection().prepareStatement(sql)){
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    User user = new User();
                    user.setId(resultSet.getLong("id"));
                    user.setName(resultSet.getString("name"));
                    user.setLastName(resultSet.getString("lastName"));
                    user.setAge(resultSet.getByte("age"));

                    usersList.add(user);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e.getMessage());
            }
            return usersList;
        }

        public void cleanUsersTable () {
            Util util = new Util();
            String sql = "TRUNCATE TABLE mydb.Users;";

            try (PreparedStatement preparedStatement = util.getConnection().prepareStatement(sql)){
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
    }
