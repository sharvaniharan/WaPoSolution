package com.example.sharvani.myapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.sharvani.myapplication.R;
import com.example.sharvani.myapplication.adapters.NewsAdapter;
import com.example.sharvani.myapplication.async.JSONParserAsync;
import com.example.sharvani.myapplication.models.News;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String CONTENT_MESSAGE = "CONTENT";
    ArrayList<News> mNewsList = new ArrayList<>();
    private List<News> mNewArray;
    private NewsAdapter mNewsAdapter;
    Button mSortByTitleBtn;
    Button mSortByDateBtn;
    ListView listView;
    private boolean sortABCToggle = false;
    private boolean sortDateToggle = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Setting UI and Listeners
        setUI();
        setListeners();

        //Adapter for ListView
        mNewsAdapter = new NewsAdapter(this, mNewsList);
        listView.setAdapter(mNewsAdapter);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
//AsynTask for Network call
        new JSONParserAsync(MainActivity.this).execute();
    }


    private void setUI() {
        listView = (ListView) findViewById(R.id.newsListView);
        mSortByDateBtn = (Button) findViewById(R.id.sort_by_date);
        mSortByTitleBtn = (Button) findViewById(R.id.sort_by_title);

    }

    private void setListeners() {
        mSortByDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortDate(sortDateToggle);
                sortDateToggle = !sortDateToggle;
                mNewsAdapter.clear();
                mNewsAdapter.addAll(mNewArray);

            }
        });
        mSortByTitleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortABC(sortABCToggle);
                sortABCToggle = !sortABCToggle;
                mNewsAdapter.clear();
                mNewsAdapter.addAll(mNewArray);

            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                News news = (News) parent.getItemAtPosition(position);
                String content = news.getmContent();
                Intent intent = new Intent(getApplicationContext(), ViewContents.class);
                intent.putExtra(CONTENT_MESSAGE, content);
                startActivity(intent);
            }
        });
    }

    public void setList(List<News> list) {
        mNewArray = list;
        mNewsAdapter.clear();
        mNewsAdapter.addAll(mNewArray);
    }

    private void sortABC(boolean sortToggle) {
        //Comparators sorting alphabetically and reverse
        if (sortToggle) {
            Collections.sort(mNewArray, new Comparator<News>() {
                @Override
                public int compare(News a1, News a2) {
                    return a1.getmTitle().compareTo(a2.getmTitle());
                }
            });
        } else {
            Collections.sort(mNewArray, new Comparator<News>() {
                @Override
                public int compare(News a1, News a2) {
                    return a2.getmTitle().compareTo(a1.getmTitle());
                }
            });
        }
    }

    private void sortDate(boolean sortToggle) {
        //Comparators sorting Date wise and reverse

        if (sortToggle) {
            Collections.sort(mNewArray, new Comparator<News>() {
                @Override
                public int compare(News a1, News a2) {
                    return a1.getmDate().compareTo(a2.getmDate());
                }
            });
        } else {
            Collections.sort(mNewArray, new Comparator<News>() {
                @Override
                public int compare(News a1, News a2) {
                    return a2.getmDate().compareTo(a1.getmDate());
                }
            });
        }
    }


}
