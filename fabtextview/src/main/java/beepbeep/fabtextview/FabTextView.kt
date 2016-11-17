package beepbeep.fabtextview

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.support.v4.view.ViewCompat
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.widget.RelativeLayout
import kotlinx.android.synthetic.main.fab_text_view.view.*

class FabTextView : RelativeLayout {

    enum class State constructor(num: Int) {
        EXPAND(0), SHRINK(1)
    }

    var state = State.EXPAND // TODO: make this configurable
        private set

    private var distanceX = 0f

    private var iconText: String? = null
    private var iconTextSize = 0
    private var diameter: Int = 0
    private var radius: Int = 0
    private var iconDrawable: Drawable? = null
    private var ftvBackgroundColor: Int = 0

    private var roundDrawable: Drawable? = null
    val isLTR: Boolean
        get() = ViewCompat.getLayoutDirection(this) === ViewCompat.LAYOUT_DIRECTION_LTR

    constructor(context: Context) : super(context) {
        setup()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        obtainAttr(context, attrs)
        setup()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        obtainAttr(context, attrs)
        setup()
    }

    private fun obtainAttr(context: Context, attrs: AttributeSet) {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.FabTextView)
        try {
            iconText = ta.getString(R.styleable.FabTextView_fab_text)
            iconTextSize = ta.getDimensionPixelOffset(R.styleable.FabTextView_fab_textSize, 0)
            iconDrawable = ta.getDrawable(R.styleable.FabTextView_fab_iconSrc)
            ftvBackgroundColor = ta.getColor(R.styleable.FabTextView_fab_backgroundColor, Color.BLUE)
            diameter = ta.getDimensionPixelOffset(R.styleable.FabTextView_fab_diameter, 0)
            radius = diameter / 2
            //            state = State.fromId(ta.getInt(R.styleable.FabTextView_fab_state, 0));
        } finally {
            ta.recycle()
        }
    }

    private fun setup() {
        View.inflate(context, R.layout.fab_text_view, this)

        if (iconText != null && !iconText!!.isEmpty()) {
            shrinkableTextView!!.text = iconText
        }
        if (iconTextSize > 0) {
            shrinkableTextView!!.setTextSize(TypedValue.COMPLEX_UNIT_PX, iconTextSize.toFloat())
        }
        setIconDrawable(iconDrawable)

        shrinkableTextView!!.setBackgroundColor(ftvBackgroundColor)
        roundDrawable = resources.getDrawable(R.drawable.round_bg)
        roundDrawable!!.setColorFilter(ftvBackgroundColor, PorterDuff.Mode.SRC_ATOP)
        startView!!.background = roundDrawable
        iconFrameLayout!!.background = roundDrawable

        layoutDirection = View.LAYOUT_DIRECTION_INHERIT

        // set sizes
        startView!!.layoutParams.height = diameter
        startView!!.layoutParams.width = diameter
        shrinkableTextView!!.layoutParams.height = diameter

        iconFrameLayout!!.layoutParams.height = diameter
        iconFrameLayout!!.layoutParams.width = diameter
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)

        if (isLTR) {
            shrinkableTextView!!.setPadding(0, 0, (diameter * 0.75).toInt(), 0)
        } else {
            shrinkableTextView!!.setPadding((diameter * 0.75).toInt(), 0, 0, 0)
        }

        shiftStart(shrinkableTextView, shrinkableTextView!!.layoutParams as RelativeLayout.LayoutParams)
        shiftStart(iconFrameLayout, iconFrameLayout!!.layoutParams as RelativeLayout.LayoutParams);

    }

    private fun shiftStart(view: View, layoutParams: RelativeLayout.LayoutParams) {
        if (isLTR) {
            layoutParams.setMargins(-radius, 0, 0, 0)
        } else {
            layoutParams.setMargins(0, 0, -radius, 0)
        }
        view.layoutParams = layoutParams
    }


    fun expand() {
        if (state == State.SHRINK) {
            state = State.EXPAND
            val scaleUpX = ObjectAnimator.ofFloat(shrinkableTextView, "scaleX", 1f)
            val moveX = ObjectAnimator.ofFloat(startView, "translationX", 0f)
            moveX.start()
            scaleUpX.start()
        }
    }

    private fun animColor(colorFrom: Int, colorTo: Int) {
        val colorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), colorFrom, colorTo)
        colorAnimation.addUpdateListener { }
        colorAnimation.start()
    }

    fun shrink() {
        if (state == State.EXPAND) {
            state = State.SHRINK
            val scaleDownX = ObjectAnimator.ofFloat(shrinkableTextView, "scaleX", 0f)
            if (isLTR) {
                shrinkableTextView!!.pivotX = shrinkableTextView!!.width.toFloat()
            } else {
                shrinkableTextView!!.pivotX = 0f
            }

            if (distanceX == 0f) {
                distanceX = shrinkableTextView!!.width.toFloat()
            }
            val moveX: ObjectAnimator
            if (isLTR) {
                moveX = ObjectAnimator.ofFloat(startView, "translationX", distanceX)
            } else {
                moveX = ObjectAnimator.ofFloat(startView, "translationX", -distanceX)
            }

            moveX.start()
            scaleDownX.start()
        }
    }

    fun toggle() {
        if (state == State.EXPAND) {
            shrink()
        } else {
            expand()
        }
    }

    fun setIconDrawable(drawable: Drawable?) {
        if (drawable != null) iconImageView!!.setImageDrawable(drawable)
    }
}
