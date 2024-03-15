package com.dljm.myShop.controller;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.dljm.myShop.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class DeleteBook implements RequestHandler<String, Map<String, Object>> {

    private static final Logger logger = LoggerFactory.getLogger(DeleteBook.class);

    private final BookService bookService;

    // Constructor with dependency injection
    public DeleteBook(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public Map<String, Object> handleRequest(String bookId, Context context) {
        try {
            // Call service to delete the book
            bookService.deleteBook(bookId);
            logger.info("Book deleted successfully: {}", bookId);

            // Construct JSON response
            Map<String, Object> response = new HashMap<>();
            response.put("statusCode", 200);
            response.put("body", "{\"message\": \"Book deleted successfully\", \"bookId\": \"" + bookId + "\"}");
            return response;
        } catch (Exception e) {
            logger.error("Error deleting book: {}", e.getMessage());

            // Construct error JSON response
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("statusCode", 500);
            errorResponse.put("body", "{\"error\": \"Error deleting book: " + e.getMessage() + "\"}");
            return errorResponse;
        }
    }
}
