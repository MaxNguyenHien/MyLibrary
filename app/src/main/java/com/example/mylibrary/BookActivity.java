package com.example.mylibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class BookActivity extends AppCompatActivity {

    public static final String BOOK_ID_KEY = "bookId";

    private TextView txtBookName, txtAuthor, txtPages, txtDescription;
    private Button btnAddToWantToRead, btnAddToAlreadyRead, btnAddToCurrentlyReading, btnAddToFavourite;
    private ImageView bookImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);


        initView();

        Intent intent = getIntent();
        if(intent != null) {
            int bookId = intent.getIntExtra(BOOK_ID_KEY, -1);
            if(bookId != -1) {
                Book incomingBook = Utils.getInstance(this).getBookById(bookId);
                if(incomingBook != null) {
                    setData(incomingBook);

                    handleAlreadyRead(incomingBook);
                    handleWantToReadBooks(incomingBook);
                    handleCurrentlyReadingBook(incomingBook);
                    handleFavouriteBooks(incomingBook);

                }
            }
        }
    }

    private void handleFavouriteBooks(Book incomingBook) {
        ArrayList<Book> favouriteBooks = Utils.getInstance(this).getFavouriteBooks();

        boolean existInFavouriteBooks = false;

        for (Book b: favouriteBooks) {
            if(b.getID() == incomingBook.getID()){
                existInFavouriteBooks = true;
            }
        }
        btnAddToFavourite.setEnabled(true);
        if(existInFavouriteBooks) {
            btnAddToFavourite.setEnabled(false);
        } else {
            btnAddToFavourite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(Utils.getInstance(BookActivity.this).addToFavourite(incomingBook)){
                        Toast.makeText(BookActivity.this, "Book Added", Toast.LENGTH_SHORT).show();
                        btnAddToFavourite.setEnabled(false);

//                        Intent intent = new Intent(BookActivity.this, FavouriteBooksActivity.class);
//                        startActivity(intent);
                    } else {
                        Toast.makeText(BookActivity.this, "Something wrong, try again", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }

    private void handleCurrentlyReadingBook(Book incomingBook) {
        ArrayList<Book> currentlyReadBooks = Utils.getInstance(this).getCurrentlyReadingBooks();

        boolean existInCurrentlyReadBooks = false;

        for (Book b: currentlyReadBooks) {
            if(b.getID() == incomingBook.getID()){
                existInCurrentlyReadBooks = true;
            }
        }

        if(existInCurrentlyReadBooks) {
            btnAddToCurrentlyReading.setEnabled(false);
        } else {
            btnAddToCurrentlyReading.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(Utils.getInstance(BookActivity.this).addToCurrentlyRead(incomingBook)) {
                        Toast.makeText(BookActivity.this, "Book Added", Toast.LENGTH_SHORT).show();
                        btnAddToCurrentlyReading.setEnabled(false);

//                        Intent intent = new Intent(BookActivity.this, CurrentlyReadBookActivity.class);
//                        startActivity(intent);
                    } else {
                        Toast.makeText(BookActivity.this, "Something wrong, try again", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }

    private void handleWantToReadBooks(Book incomingBook) {
        ArrayList<Book> wantToReadBooks = Utils.getInstance(this).getWantToReadBooks();

        boolean existInWantToReadBooks = false;

        for (Book b: wantToReadBooks) {
            if(b.getID() == incomingBook.getID()) {
                existInWantToReadBooks = true;
            }
        }

        if(existInWantToReadBooks) {
            btnAddToWantToRead.setEnabled(false);
        } else {
            btnAddToWantToRead.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(Utils.getInstance(BookActivity.this).addToWantToRead(incomingBook)){
                        Toast.makeText(BookActivity.this, "Book Added", Toast.LENGTH_SHORT).show();
                        btnAddToWantToRead.setEnabled(false);

//                        Intent intent = new Intent(BookActivity.this, WantToReadBookActivity.class);
//                        startActivity(intent);
                    } else {
                        Toast.makeText(BookActivity.this, "Something wrong, try again", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    /**
     * Enable and Disable button,
     * Add the book to Already Read Book ArrayList
     *
     * @param incomingBook
     */

    private void handleAlreadyRead(Book incomingBook) {
        ArrayList<Book> alreadyReadBooks = Utils.getInstance(this).getAlreadyReadBooks();

        boolean existInAlreadyReadBooks = false;

        for (Book b: alreadyReadBooks) {
            if(b.getID() == incomingBook.getID()) {
                existInAlreadyReadBooks = true;
            }
        }

        if(existInAlreadyReadBooks) {
            btnAddToAlreadyRead.setEnabled(false);
        } else {
            btnAddToAlreadyRead.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(Utils.getInstance(BookActivity.this).addToAlreadyRead(incomingBook)){
                        Toast.makeText(BookActivity.this, "Book Added", Toast.LENGTH_SHORT).show();
                        btnAddToAlreadyRead.setEnabled(false);

//                        Intent intent = new Intent(BookActivity.this, ALreadyReadBookActivity.class);
//                        startActivity(intent);
                    } else {
                        Toast.makeText(BookActivity.this, "Something wrong, try again", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }


    private void setData(Book book) {
        txtBookName.setText(book.getName());
        txtAuthor.setText(book.getAuthor());
        txtPages.setText(String.valueOf(book.getPages()));
        txtDescription.setText(book.getLongDesc());

        Glide.with(this)
                .asBitmap()
                .load(book.getImageUrl())
                .into(bookImage);
    }

    private void initView() {

        txtAuthor = findViewById(R.id.txtAuthorNameBA);
        txtBookName = findViewById(R.id.txtBookNameBA);
        txtPages = findViewById(R.id.txtPages);
        txtDescription = findViewById(R.id.txtDescription);

        btnAddToAlreadyRead = findViewById(R.id.btnAddToAlreadyReadList);
        btnAddToCurrentlyReading = findViewById(R.id.btnAddToCurrentlyReading);
        btnAddToFavourite = findViewById(R.id.btnAddToFavourite);
        btnAddToWantToRead = findViewById(R.id.btnAddToWantToReadList);

        bookImage = findViewById(R.id.imageBookBA);





    }


}