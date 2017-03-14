package com.example.lex.todolist;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;



public class MainActivity extends AppCompatActivity {

    // state the listview and the edittext
    ListView TODOList;
    EditText add_todo;

    // state  the watchlist
    ArrayList<String> todos = new ArrayList<String>();

    private DBManager dbManager;
    TodoCursorAdapter cursorAdapter;
    Cursor cursor;

    // state this activity
    MainActivity main = this;

    ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbManager = new DBManager(this);
        dbManager.open();

        cursor = dbManager.fetch();
        cursorAdapter = new TodoCursorAdapter(this, cursor);

        // sate the add bar
        add_todo = (EditText) findViewById(R.id.add_todo);
        assert add_todo != null;

        // check for a savedinstancestate
        if (savedInstanceState != null) {
            // set the text to the saved state
            String savedsearch = savedInstanceState.getString("add_todo");
            add_todo.setText(savedsearch);
        }

        // make a listview of the watchlist
        TODOList = (ListView) findViewById(R.id.TODOList);
        assert TODOList != null;
        TODOList.setAdapter(cursorAdapter);

        // set an onclick listener for every list item
        TODOList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                // state the movie and the asynctask

                dbManager.delete(id);

                // notify the array adapter
                cursor = dbManager.fetch();
                cursorAdapter.changeCursor(cursor);
                cursorAdapter.notifyDataSetChanged();

                return true;
        }});
    }

    // add a to do
    public void add_todo_button(View view) {
        String todo = add_todo.getText().toString();

        dbManager.insert(todo);

        // clear the edittext
        add_todo.getText().clear();

        // notify the array adapter
        cursor = dbManager.fetch();
        cursorAdapter.changeCursor(cursor);
        cursorAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        // save the edittext
        outState.putString("add_todo", add_todo.getText().toString());

        super.onSaveInstanceState(outState);
    }

    public class TodoCursorAdapter extends CursorAdapter {
        public TodoCursorAdapter(Context context, Cursor cursor) {
            super(context, cursor, 0);
        }

        // The newView method is used to inflate a new view and return it,
        // you don't bind any data to the view at this point.
        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        }

        // The bindView method is used to bind all data to a given view
        // such as setting the text on a TextView.
        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            // Find fields to populate in inflated template
            TextView listitem = (TextView) view.findViewById(R.id.textView_list);

            // Extract properties from cursor
            String body = cursor.getString(cursor.getColumnIndexOrThrow("subject"));

            // Populate fields with extracted properties
            listitem.setText(body);
        }
    }
}
