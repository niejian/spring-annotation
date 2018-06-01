package cn.com.spring.service;

import org.springframework.beans.factory.annotation.Autowired;

import cn.com.spring.Dao.BookDao;

public class BookService {
	@Autowired
	private BookDao bookDao;
	
	public void print() {
		System.out.println(bookDao);
	}

}
