package com.example.muirsuus.main_navigation.lit;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "books")
public class books {
    @PrimaryKey(autoGenerate = true)
    Integer id;

    String book_name;
    String book_photo;

    public books(String book_name, String book_photo) {
        this.book_name = book_name;
        this.book_photo = book_photo;
    }

    public String getBook_name() {
        return book_name;
    }

    public void setBook_name(String book_name) {
        this.book_name = book_name;
    }

    public String getBook_photo() {
        return book_photo;
    }

    public void setBook_photo(String book_photo) {
        this.book_photo = book_photo;
    }
}
