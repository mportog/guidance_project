package com.mportog.widgets.labelbutton

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.StringRes
import androidx.constraintlayout.widget.ConstraintLayout
import guidance_project.widgets.R
import guidance_project.widgets.databinding.LabelWithButtonCustomViewBinding

class LabelWithButtonCustomView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet?,
    defStyle: Int = 0,
    defStyleRes: Int = 0
) : ConstraintLayout(context, attrs, defStyle, defStyleRes) {

    private val binding: LabelWithButtonCustomViewBinding =
        LabelWithButtonCustomViewBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(
                it,
                R.styleable.LabelWithButtonCustomView, 0, 0
            )
            val descriptionText = resources.getText(
                typedArray
                    .getResourceId(
                        R.styleable
                            .LabelWithButtonCustomView_custom_component_description_title,
                        R.string.hello_world_default_label
                    )
            )
            val buttonLabel = resources.getText(
                typedArray
                    .getResourceId(
                        R.styleable
                            .LabelWithButtonCustomView_custom_component_button_label,
                        R.string.hello_world_default_label
                    )
            )

            with(binding) {
                tvLabel.text = descriptionText
                mbButton.text = buttonLabel
            }

            typedArray.recycle()
        }
    }


    fun setText(@StringRes descriptionText: Int) {
        binding.tvLabel.text = resources.getString(descriptionText)
    }

    fun setText(descriptionText: String) {
        binding.tvLabel.text = descriptionText
    }

    fun setButtonClick(a: () -> Unit) {
        binding.mbButton.setOnClickListener {
            a
        }
    }
}