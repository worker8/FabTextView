package beepbeep.fabtextview;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.text.TextUtilsCompat;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class FabTextView extends RelativeLayout {

    public enum State {
        EXPAND(0), SHRINK(1);
        int num;

        State(int num) {
            this.num = num;
        }

        public int getNum() {
            return num;
        }

        public static State fromId(int id) {
            for (State type : State.values()) {
                if (type.getNum() == id) {
                    return type;
                }
            }
            return null;
        }
    }

    private State state = State.EXPAND; // TODO: make this configurable
    private View startView;
    private ImageView imageView;
    private TextView shrinkableTextView;
    private FrameLayout frameLayout;
    private float distanceX = 0;

    private String iconText;
    private int iconTextSize = 0, diameter;
    private Drawable iconDrawable;
    private int backgroundColor;

    private Drawable roundDrawable;

    public FabTextView(Context context) {
        super(context);
        setup();
    }

    public FabTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        obtainAttr(context, attrs);
        setup();
    }

    public FabTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        obtainAttr(context, attrs);
        setup();
    }

    private void obtainAttr(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.FabTextView);
        try {
            iconText = ta.getString(R.styleable.FabTextView_fab_text);
            iconTextSize = ta.getDimensionPixelOffset(R.styleable.FabTextView_fab_textSize, 0);
            iconDrawable = ta.getDrawable(R.styleable.FabTextView_fab_iconSrc);
            backgroundColor = ta.getColor(R.styleable.FabTextView_fab_backgroundColor, Color.BLUE);
            diameter = ta.getDimensionPixelOffset(R.styleable.FabTextView_fab_diameter, 0);
//            state = State.fromId(ta.getInt(R.styleable.FabTextView_fab_state, 0));
        } finally {
            ta.recycle();
        }
    }

    private void setup() {
        inflate(getContext(), R.layout.fab_text_view, this);

        shrinkableTextView = (TextView) findViewById(R.id.shrinkable);
        startView = findViewById(R.id.start_view);
        imageView = (ImageView) findViewById(R.id.image_view);
        frameLayout = (FrameLayout) findViewById(R.id.end_view);
        if (iconText != null && !iconText.isEmpty()) {
            shrinkableTextView.setText(iconText);
        }
        if (iconTextSize > 0) {
            shrinkableTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, iconTextSize);
        }
        setIconDrawable(iconDrawable);

        shrinkableTextView.setBackgroundColor(backgroundColor);
        roundDrawable = getResources().getDrawable(R.drawable.round_bg);
        roundDrawable.setColorFilter(backgroundColor, PorterDuff.Mode.SRC_ATOP);
        startView.setBackground(roundDrawable);
        frameLayout.setBackground(roundDrawable);

        // size
        int radius = diameter / 2;

        startView.getLayoutParams().height = diameter;
        startView.getLayoutParams().width = diameter;
        shrinkableTextView.getLayoutParams().height = diameter;

        if (isLTR()) {
            shrinkableTextView.setPadding(0, 0, (int) (diameter * 0.75), 0);
        } else {
            shrinkableTextView.setPadding((int) (diameter * 0.75), 0, 0, 0);
        }


        RelativeLayout.LayoutParams shrinkableTextViewLayoutParams = (RelativeLayout.LayoutParams) shrinkableTextView.getLayoutParams();

        if (isLTR()) {
            shrinkableTextViewLayoutParams.setMargins(-radius, 0, 0, 0);
        } else {
            shrinkableTextViewLayoutParams.setMargins(0, 0, -radius, 0);
        }
        shrinkableTextView.setLayoutParams(shrinkableTextViewLayoutParams);

        RelativeLayout.LayoutParams endViewLayoutParams = (RelativeLayout.LayoutParams) frameLayout.getLayoutParams();
        if (isLTR()) {
            endViewLayoutParams.setMargins(-radius, 0, 0, 0);
        } else {
            endViewLayoutParams.setMargins(0, 0, -radius, 0);
        }
        frameLayout.setLayoutParams(endViewLayoutParams);
        frameLayout.getLayoutParams().height = diameter;
        frameLayout.getLayoutParams().width = diameter;

    }

    public boolean isLTR() {
        return TextUtilsCompat.getLayoutDirectionFromLocale(getResources().getConfiguration().locale) == ViewCompat.LAYOUT_DIRECTION_LTR;
        // return false; // for debugging
    }

    public void expand() {
        if (state == State.SHRINK) {
            state = State.EXPAND;
            ObjectAnimator scaleUpX = ObjectAnimator.ofFloat(shrinkableTextView, "scaleX", 1f);
            ObjectAnimator moveX = ObjectAnimator.ofFloat(startView, "translationX", 0);
            moveX.start();
            scaleUpX.start();
        }
    }

    private void animColor(int colorFrom, int colorTo) {
        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

            }
        });
        colorAnimation.start();
    }

    public void shrink() {
        if (state == State.EXPAND) {
            state = State.SHRINK;
            ObjectAnimator scaleDownX = ObjectAnimator.ofFloat(shrinkableTextView, "scaleX", 0f);
            if (isLTR()) {
                shrinkableTextView.setPivotX(shrinkableTextView.getWidth());
            } else {
                shrinkableTextView.setPivotX(0);
            }

            if (distanceX == 0) {
                distanceX = shrinkableTextView.getWidth();
            }
            ObjectAnimator moveX;
            if (isLTR()) {
                moveX = ObjectAnimator.ofFloat(startView, "translationX", distanceX);
            } else {
                moveX = ObjectAnimator.ofFloat(startView, "translationX", -distanceX);
            }

            moveX.start();
            scaleDownX.start();
        }
    }

    public void toggle() {
        if (state == State.EXPAND) {
            shrink();
        } else {
            expand();
        }
    }

    public void setIconDrawable(Drawable drawable) {
        if (drawable != null) {
            imageView.setImageDrawable(drawable);
        }
    }

    public State getState() {
        return state;
    }
}
