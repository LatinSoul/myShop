package com.dljm.myShop.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.dljm.myShop.model.Book;
import com.dljm.myShop.service.BookService;

public class CreateBookHandler implements RequestHandler<Book, String> {

	private final BookService bookService;

	public CreateBookHandler() {
		this.bookService = new BookService();
	}

	@Override
	public String handleRequest(Book book, Context context) {
		// Call service to create a new book
		return bookService.createBook(book);
	}
}
