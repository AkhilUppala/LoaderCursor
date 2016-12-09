package com.example.rs.myapplication12345;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>, View.OnClickListener{

    private boolean firstTimeLoaded=false;

    private TextView textViewQueryResult;
    private Button buttonLoadData;

    private String[] mColumnProjection = new String[]{
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.ARTIST};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewQueryResult = (TextView) findViewById(R.id.textViewQueryResult);

        buttonLoadData = (Button) findViewById(R.id.buttonLoadData);
        buttonLoadData.setOnClickListener(this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle bundle) {
        if (id == 1) {
            return new CursorLoader(MainActivity.this, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                    mColumnProjection, null, null, null);
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (cursor != null && cursor.getCount() > 0) {
            StringBuilder stringBuilderQueryResult = new StringBuilder("");
            //cursor.moveToFirst();
            while (cursor.moveToNext()) {
                stringBuilderQueryResult.append(cursor.getString(0) + " , " + cursor.getString(1) + " , " + cursor.getString(2) + "\n");
            }
            textViewQueryResult.setText(stringBuilderQueryResult.toString());
        } else {
            textViewQueryResult.setText("No Media in device");
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonLoadData: if(firstTimeLoaded==false){
                getLoaderManager().initLoader(1, null,this);
                firstTimeLoaded=true;
            }else{
                getLoaderManager().restartLoader(1,null,this);
            }
                break;
            default:
                break;
        }
    }

}
