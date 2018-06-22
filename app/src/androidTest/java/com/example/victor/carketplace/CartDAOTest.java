package com.example.victor.carketplace;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.RenamingDelegatingContext;

import com.example.victor.carketplace.database.LocalDatabase;
import com.example.victor.carketplace.database.dao.CartDAO;
import com.example.victor.carketplace.database.model.CartItem;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import io.reactivex.functions.Predicate;

import static org.junit.Assert.assertEquals;


@RunWith(AndroidJUnit4.class)
public class CartDAOTest {
    Context mMockContext;
    private LocalDatabase mDatabase;

    @Before
    public void setUp() {
        mMockContext = new RenamingDelegatingContext(InstrumentationRegistry.getTargetContext(), "test_");

        mDatabase = Room.inMemoryDatabaseBuilder(
                InstrumentationRegistry.getContext(),
                LocalDatabase.class)
                // allowing main thread queries, just for testing
                .allowMainThreadQueries()
                .build();
    }

    @After
    public void closeDb() throws Exception {
        mDatabase.close();
    }

    @Test
    public void insertItemTest() throws Exception{
        CartItem item = new CartItem();
        item.setAmount(10);
        item.setId(1);
        item.setPrice(25000);
        item.setImage("");
        item.setName("Test");
        item.setModel("Test");

        long itemId = mDatabase.cartDAO().addToCart(item);

        assertEquals(itemId, 1);
    }

    @Test
    public void deleteItemTest() throws Exception{
        CartItem item = new CartItem();
        item.setAmount(10);
        item.setId(1);
        item.setPrice(25000);
        item.setImage("");
        item.setName("Test");
        item.setModel("Test");
        mDatabase.cartDAO().addToCart(item);

        int deleted = mDatabase.cartDAO().delete(item);

        assertEquals(deleted, 1);
    }
}
