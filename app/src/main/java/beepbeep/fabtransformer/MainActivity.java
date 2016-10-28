package beepbeep.fabtransformer;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    View startView, shrinkable, endView;
    boolean toggleFlag = true;

    float distanceX = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        shrinkable = findViewById(R.id.shrinkable);
        startView = findViewById(R.id.start);
        endView = findViewById(R.id.end);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        endView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (toggleFlag) {
                    toggleFlag = false;
                    ObjectAnimator scaleDownX = ObjectAnimator.ofFloat(shrinkable, "scaleX", 0f);
                    shrinkable.setPivotX(shrinkable.getWidth());
                    if (distanceX == 0) {
                        distanceX = shrinkable.getWidth();
                    }
                    Log.d("ddw", "onClick: " + distanceX);
                    ObjectAnimator moveX = ObjectAnimator.ofFloat(startView, "translationX", distanceX);
                    moveX.start();
                    scaleDownX.start();
                } else {
                    toggleFlag = true;
                    ObjectAnimator scaleUpX = ObjectAnimator.ofFloat(shrinkable, "scaleX", 1f);
                    Log.d("ddw", "backward] onClick: " + distanceX);
                    ObjectAnimator moveX = ObjectAnimator.ofFloat(startView, "translationX", 0);
                    moveX.start();
                    scaleUpX.start();
                }
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
