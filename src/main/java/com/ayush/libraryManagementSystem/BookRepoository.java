package com.ayush.libraryManagementSystem;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepoository extends JpaRepository <Book,Long> {

}
