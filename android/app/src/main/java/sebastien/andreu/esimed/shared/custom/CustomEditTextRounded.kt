package sebastien.andreu.esimed.shared.custom

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.text.InputFilter
import android.text.InputType
import android.util.AttributeSet
import android.view.View
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.widget.doOnTextChanged
import sebastien.andreu.esimed.R
import sebastien.andreu.esimed.utils.EMPTY_STRING

class CustomEditTextRounded @JvmOverloads constructor(
    private val context: Context,
    private val attrs: AttributeSet? = null,
    private val defStyle: Int = 0
): RelativeLayout(context, attrs, defStyle) {

    private val DEFAULT_CORNER_RADIUS = resources.getDimension(com.intuit.sdp.R.dimen._10sdp)
    private val DEFAULT_STROKE_WITDH = resources.getDimension(com.intuit.sdp.R.dimen._2sdp)
    private val DEFAULT_TEXT_SIZE = resources.getDimension(com.intuit.sdp.R.dimen._2sdp)
    private val DEFAULT_TITLE_MARGIN = resources.getDimension(com.intuit.sdp.R.dimen._15sdp)

    private var textViewTitleColor: Int = Color.BLACK
    private var textViewTitle: String = EMPTY_STRING
    private var textViewTitleBackground: Int = Color.WHITE
    private var textViewTitleMargin: Float = DEFAULT_TITLE_MARGIN

    private var editTextBorderColor: Int = Color.BLACK
    private var editTextStrokeWidth: Float = DEFAULT_STROKE_WITDH
    private var editTextCornerRadius: Float = DEFAULT_CORNER_RADIUS
    private var editTextBackgroundColor: Int = Color.WHITE
    private var editTextHeight: Int = LayoutParams.WRAP_CONTENT
    private var editTextHint: String = EMPTY_STRING
    private var editTextText: String = EMPTY_STRING
    private var editTextSize: Float = DEFAULT_TEXT_SIZE
    private var editTextColor: Int = Color.BLACK
    private var editTextInputType: Int = InputType.TYPE_CLASS_TEXT
    private var editTextEnabled: Boolean = true

    init {
        inflate(context, R.layout.custom_edit_text_rounded, this)

        findViewById<EditText>(R.id.customEditTextContent)?.doOnTextChanged { text, start, before, count ->
            editTextText = text.toString()
        }

        showWithXmlAttr()
    }

    private fun showWithXmlAttr() {
        attrs?.let {
            val attr = context.obtainStyledAttributes(it, R.styleable.CustomEditTextRounded)

            textViewTitleColor = attr.getColor(R.styleable.CustomEditTextRounded_titleColor, Color.BLACK)
            textViewTitle = attr.getString(R.styleable.CustomEditTextRounded_title) ?: EMPTY_STRING
            textViewTitleBackground = attr.getColor(R.styleable.CustomEditTextRounded_titleBackground, Color.WHITE)
            textViewTitleMargin = attr.getDimension(R.styleable.CustomEditTextRounded_titleMargin, DEFAULT_TITLE_MARGIN)


            editTextBorderColor = attr.getColor(R.styleable.CustomEditTextRounded_editTextBorderColor, Color.BLACK)
            editTextStrokeWidth = attr.getDimension(R.styleable.CustomEditTextRounded_editTextBorderWidth, DEFAULT_STROKE_WITDH)
            editTextBackgroundColor = attr.getColor(R.styleable.CustomEditTextRounded_editTextBackgroundColor, Color.WHITE)
            editTextCornerRadius = attr.getDimension(R.styleable.CustomEditTextRounded_editTextRadius, DEFAULT_CORNER_RADIUS)
            editTextHeight = attr.getDimension(R.styleable.CustomEditTextRounded_editTextHeight, LayoutParams.WRAP_CONTENT.toFloat()).toInt()
            editTextHint = attr.getString(R.styleable.CustomEditTextRounded_editTextHint) ?: EMPTY_STRING
            editTextSize = attr.getDimension(R.styleable.CustomEditTextRounded_editTextSize, DEFAULT_TEXT_SIZE)
            editTextColor = attr.getColor(R.styleable.CustomEditTextRounded_editTextColor, Color.BLACK)
            editTextInputType = attr.getInteger(R.styleable.CustomEditTextRounded_android_inputType, InputType.TYPE_CLASS_TEXT)
            editTextEnabled = attr.getBoolean(R.styleable.CustomEditTextRounded_android_enabled, true)

            setDefaultView()

            attr.recycle()
        }
    }

    fun setupTitle(
        titleColor: Int? = null,
        title: String? = null,
        background: Int? = null,
        titleMargin: Float? = null
    ) {
        findViewById<TextView>(R.id.customEditTextTitle)?.apply {
            (title ?: textViewTitle).let { title ->
                if (title == EMPTY_STRING) {
                    this.visibility = View.GONE
                } else {
                    this.text = title
                }
            }
            this.setTextColor(titleColor ?: textViewTitleColor)
            this.setBackgroundColor(background ?: textViewTitleBackground)

            (titleMargin ?: textViewTitleMargin).let { titleMargin ->
                val param = LayoutParams(this.layoutParams.width, this.layoutParams.height)
                param.setMargins(titleMargin.toInt(), 0, titleMargin.toInt(), 0)
                this.layoutParams = param
            }
        }
    }

    fun setupEditText(
        text: String? = null,
        hint: String? = null,
        textSize: Float? = null,
        textColor: Int? = null,
        backgroundColor: Int? = null,
        borderColor: Int? = null,
        strokeWidth: Float? = null,
        cornerRadius: Float? = null,
        height: Int? = null,
        inputType: Int? = InputType.TYPE_CLASS_TEXT,
        enabled: Boolean? = null
    ) {
        val drawable = GradientDrawable().apply {
            this.shape = GradientDrawable.RECTANGLE
            this.cornerRadius = cornerRadius ?: editTextCornerRadius
            setStroke(strokeWidth?.toInt() ?: editTextStrokeWidth.toInt(), borderColor ?: editTextBorderColor)
            setColor(backgroundColor ?: editTextBackgroundColor)
        }

        findViewById<EditText>(R.id.customEditTextContent)?.apply {
            this.background = drawable
            this.layoutParams.height = height ?: editTextHeight
            this.inputType = inputType ?: editTextInputType
            this.setTextColor(textColor ?: editTextColor)
            this.hint = hint ?: editTextHint
            this.textSize = textSize ?: editTextSize
            this.setText(text ?: editTextText)
            this.isClickable = enabled ?: editTextEnabled
            this.isEnabled = enabled ?: editTextEnabled
        }
    }

    fun setDefaultView() {
        setDefaultTitleView()
        setDefaultEditText()
    }
    fun setDefaultTitleView() {
        setupTitle(textViewTitleColor, textViewTitle, textViewTitleBackground, textViewTitleMargin)
    }

    fun setDefaultEditText() {
        setupEditText(
            editTextText,
            editTextHint,
            editTextSize,
            editTextColor,
            editTextBackgroundColor,
            editTextBorderColor,
            editTextStrokeWidth,
            editTextCornerRadius,
            editTextHeight,
            editTextInputType
        )
    }

    fun setContentText(text: String?) {
        editTextText = text ?: EMPTY_STRING
        findViewById<EditText>(R.id.customEditTextContent)?.setText(text)
    }

    fun getValue(): String {
        return findViewById<EditText>(R.id.customEditTextContent)?.text.toString()
    }

    fun isEmpty(): Boolean {
        return getValue().isEmpty()
    }

    fun setFilter(inputFilter: InputFilter) {
        findViewById<EditText>(R.id.customEditTextContent)?.filters = arrayOf(inputFilter)
    }
}
