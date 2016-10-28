package beepbeep.fabtextview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public class FabTextView extends RelativeLayout {
    public FabTextView(Context context) {
        super(context);
        setup();
    }

    public FabTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setup();
    }

    public FabTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setup();
    }

    private void setup() {
        inflate(getContext(), R.layout.fab_text_view,this);
    }


}
