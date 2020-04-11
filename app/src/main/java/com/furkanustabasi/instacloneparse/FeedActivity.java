package com.furkanustabasi.instacloneparse;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.LogOutCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.fcm.ParseFCM;

import java.util.ArrayList;
import java.util.List;

public class FeedActivity extends AppCompatActivity {

    ListView listView;

    ArrayList<String> userNameFromParse;
    ArrayList<String> userCommentFromParse;
    ArrayList<Bitmap> userBitmapFromParse;
    PostClass postClass;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);


        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.add_post) {
            Intent intent = new Intent(FeedActivity.this, UploadActivity.class);
            startActivity(intent);

        } else if (item.getItemId() == R.id.sign_out) {

            ParseUser.logOutInBackground(new LogOutCallback() {
                @Override
                public void done(ParseException e) {
                    if (e != null) {
                        Toast.makeText(FeedActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Intent intent = new Intent(FeedActivity.this, SignUpActivity.class);
                        startActivity(intent);
                    }
                }
            });
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        listView = findViewById(R.id.feed_activity_listView);

        userBitmapFromParse = new ArrayList<>();
        userCommentFromParse = new ArrayList<>();
        userNameFromParse = new ArrayList<>();

        postClass = new PostClass(userNameFromParse, userCommentFromParse, userBitmapFromParse, this);

        listView.setAdapter(postClass);

        download();

    }

    public void download() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Posts");
        query.findInBackground(new FindCallback<ParseObject>() {

            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e != null) {
                    Toast.makeText(FeedActivity.this, e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                } else {

                    if (objects.size() > 0) {
                        for (final ParseObject object : objects) {

                            ParseFile parseFile = (ParseFile) object.get("image");
                            parseFile.getDataInBackground(new GetDataCallback() {
                                @Override
                                public void done(byte[] data, ParseException e) {
                                    if (e == null && data != null) {
                                        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                                        userBitmapFromParse.add(bitmap);
                                        userNameFromParse.add(object.getString("username"));
                                        userCommentFromParse.add(object.getString("comment"));

                                        postClass.notifyDataSetChanged();
                                    }
                                }
                            });
                        }
                    }
                }


            }
        });

    }
}
