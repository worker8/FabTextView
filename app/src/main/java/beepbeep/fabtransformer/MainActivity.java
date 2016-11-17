package beepbeep.fabtransformer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import beepbeep.fabtextview.FabTextView;

public class MainActivity extends AppCompatActivity {

    View shrinkable;

    FabTextView fabTextView, fabTextViewRtl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        shrinkable = findViewById(R.id.shrinkableTextView);

        fabTextView = (FabTextView) findViewById(R.id.fab_text_view);

        fabTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabTextView.toggle();
            }
        });
        
        fabTextViewRtl = (FabTextView) findViewById(R.id.fab_text_view_rtl);

        fabTextViewRtl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabTextViewRtl.toggle();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
