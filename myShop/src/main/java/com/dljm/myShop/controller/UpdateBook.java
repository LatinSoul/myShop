package com.dljm.myShop.controller;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.dljm.myShop.model.Book;
import com.dljm.myShop.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class UpdateBook implements RequestHandler<Book, Map<String, Object>> {

    private static final Logger logger = LoggerFactory.getLogger(UpdateBook.class);

    private final BookService bookService;

    // Constructor with dependency injection
    public UpdateBook(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public Map<String, Object> handleRequest(Book book, Context context) {
        try {
            // Call service to update the book
            bookService.updateBook(book);
            logger.info("Book updated successfully: {}", book.getId());

            // Construct JSON response
            Map<String, Object> response = new HashMap<>();
            response.put("statusCode", 200);
            response.put("body", "{\"message\": \"Book updated successfully\", \"bookId\": \"" + book.getId() + "\"}");
            return response;
        } catch (Exception e) {
            logger.error("Error updating book: {}", e.getMessage());

            // Construct error JSON response
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("statusCode", 500);
            errorResponse.put("body", "{\"error\": \"Error updating book: " + e.getMessage() + "\"}");
            return errorResponse;
        }
    }
}
