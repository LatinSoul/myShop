package com.dljm.myShop.controller;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.dljm.myShop.model.Book;
import com.dljm.myShop.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class ReadBook implements RequestHandler<String, Map<String, Object>> {

    private static final Logger logger = LoggerFactory.getLogger(ReadBook.class);

    private final BookService bookService;

    public ReadBook(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public Map<String, Object> handleRequest(String bookId, Context context) {
        try {
            // Call service to retrieve book details
            Book book = bookService.getBookById(bookId);
            logger.info("Book retrieved successfully: {}", bookId);

            // Construct JSON response
            Map<String, Object> response = new HashMap<>();
            response.put("statusCode", 200);
            response.put("body", "{\"message\": \"Book retrieved successfully\", \"book\": " + bookToJson(book) + "}");
            return response;
        } catch (Exception e) {
            logger.error("Error retrieving book: {}", e.getMessage());

            // Construct error JSON response
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("statusCode", 500);
            errorResponse.put("body", "{\"error\": \"Error retrieving book: " + e.getMessage() + "\"}");
            return errorResponse;
        }
    }

    // Convert Book object to JSON string
    private String bookToJson(Book book) {
        // Implement your logic to convert Book object to JSON string using a library like Jackson or Gson
        // For demonstration purposes, simply returning Book.toString()
        return book.toString();
    }
}
