package com.dljm.myShop.controller;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.dljm.myShop.model.Book;
import com.dljm.myShop.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class CreateBook implements RequestHandler<Book, Map<String, Object>> {

	private static final Logger logger = LoggerFactory.getLogger(CreateBook.class);

	private final BookService bookService;

	// Constructor with dependency injection
	public CreateBook(BookService bookService) {
		this.bookService = bookService;
	}

	@Override
	public Map<String, Object> handleRequest(Book book, Context context) {
		try {
			// Call service to create a new book
			String bookId = bookService.createBook(book);
			logger.info("Book created successfully: {}", bookId);

			// Construct JSON response
			Map<String, Object> response = new HashMap<>();
			response.put("statusCode", 200);
			response.put("body", "{\"message\": \"Book created successfully\", \"bookId\": \"" + bookId + "\"}");
			return response;
		} catch (Exception e) {
			logger.error("Error creating book: {}", e.getMessage());

			// Construct error JSON response
			Map<String, Object> errorResponse = new HashMap<>();
			errorResponse.put("statusCode", 500);
			errorResponse.put("body", "{\"error\": \"Error creating book: " + e.getMessage() + "\"}");
			return errorResponse;
		}
	}
}
