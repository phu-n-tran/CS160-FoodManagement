package com.example.foodsavierapp;

import android.content.Context;
import android.test.AndroidTestCase;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest extends AndroidTestCase {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    private DatabaseHelper db;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        RenamingDelegatingContext context = new RenamingDelegatingContext(getContext(), "test_");
        db = new MyDatabase(context);
    }

    @Override
    public void tearDown() throws Exception {
        db.close();
        super.tearDown();
    }

    //According to Zainodis annotation only for legacy and not valid with gradle>1.1:
    //@Test
    public void testAddEntry(){
        // Here I have my new database which is not connected to the standard database of the App
    }

    @Test
    public void databaseTest() {
        Boolean result = db.insertAccount("Leiva", "Diego", "dieago.leiva@sjsu.edu", "1234");
        assertEquals(true, result);
    }
}