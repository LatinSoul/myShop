package com.dljm.myShop.repository;

import com.dljm.myShop.model.Book;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class BookRepository {

    private static final String CONFIG_FILE = "database.properties";
    private static final String SQL_FILE = "sql.properties";
    // JDBC connection parameters (replace with your Aurora Serverless database credentials)
    private static String DB_URL;
    private static String DB_USER;
    private static String DB_PASSWORD;
    //private static Properties sqlQueries;

    // SQL queries
    private static String SQL_INSERT_BOOK;
    private static String SQL_SELECT_ALL_BOOKS;
    private static String SQL_SELECT_BOOK_BY_ID;
    private static String SQL_UPDATE_BOOK;
    private static String SQL_DELETE_BOOK;

    public BookRepository() {
        loadDatabaseConfig();
        loadSqlQueries();
    }

    private void loadDatabaseConfig() {
        Properties props = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            props.load(input);
            DB_URL = props.getProperty("db.url");
            DB_USER = props.getProperty("db.user");
            DB_PASSWORD = props.getProperty("db.password");
        } catch (IOException ex) {
            ex.printStackTrace();
            // Handle the exception (e.g., log error, throw custom exception)
        }
    }

    private void loadSqlQueries() {
        Properties sqlQueries = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(SQL_FILE)) {
            sqlQueries.load(input);
            SQL_INSERT_BOOK = sqlQueries.getProperty("insert_book");
            SQL_SELECT_ALL_BOOKS = sqlQueries.getProperty("select_all_books");
            SQL_SELECT_BOOK_BY_ID = sqlQueries.getProperty("select_book_by_id");
            SQL_UPDATE_BOOK = sqlQueries.getProperty("update_book");
            SQL_DELETE_BOOK = sqlQueries.getProperty("delete_book");
        } catch (IOException ex) {
            ex.printStackTrace();
            // Handle the exception (e.g., log error, throw custom exception)
        }
    }
    public void create(Book book) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(SQL_INSERT_BOOK)) {
            statement.setString(1, book.getId());
            statement.setString(2, book.getTitle());
            statement.setString(3, book.getAuthor());
            statement.setDouble(4, book.getPrice());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception (e.g., log error, throw custom exception)
        }
    }

    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL_BOOKS);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                books.add(mapResultSetToBook(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception (e.g., log error, throw custom exception)
        }
        return books;
    }

    public Book getBookById(String id) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BOOK_BY_ID)) {
            statement.setString(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToBook(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception (e.g., log error, throw custom exception)
        }
        return null;
    }

    public void update(Book book) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_BOOK)) {
            statement.setString(1, book.getTitle());
            statement.setString(2, book.getAuthor());
            statement.setDouble(3, book.getPrice());
            statement.setString(4, book.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception (e.g., log error, throw custom exception)
        }
    }

    public void delete(String id) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(SQL_DELETE_BOOK)) {
            statement.setString(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception (e.g., log error, throw custom exception)
        }
    }

    private Book mapResultSetToBook(ResultSet resultSet) throws SQLException {
        Book book = new Book();
        book.setId(resultSet.getString("id"));
        book.setTitle(resultSet.getString("title"));
        book.setAuthor(resultSet.getString("author"));
        book.setPrice(resultSet.getDouble("price"));
        return book;
    }
}