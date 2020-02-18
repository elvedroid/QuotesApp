package org.elveselimoski.quotesapp.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Created by Elvedin Selimoski on 2/18/2020.
 */
@Entity
public class Quote {
    @PrimaryKey
    @NonNull
    private String id;
    private String en;
    private String author;

    public Quote() {
    }

    public Quote(String id, String en, String author) {
        this.id = id;
        this.en = en;
        this.author = author;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEn() {
        return en;
    }

    public void setEn(String en) {
        this.en = en;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
