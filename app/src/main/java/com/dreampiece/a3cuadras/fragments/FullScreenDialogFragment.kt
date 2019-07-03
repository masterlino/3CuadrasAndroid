package com.dreampiece.a3cuadras.fragments


import android.os.Bundle
import android.support.v4.app.DialogFragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.dreampiece.a3cuadras.R


class FullScreenDialogFragment : DialogFragment() {


    private var title : String = ""
    private var text : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val b = arguments
        title = if (b != null) b.getString("TITLE", "Dialogo") else throw NullPointerException("Expression 'b' must not be null")
        text = b.getString("TEXT", "Texto Descriptivo")

        val style = DialogFragment.STYLE_NORMAL
        val theme = R.style.FullScreenDialogStyle
        setStyle(style, theme)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.dialog_fragment, container, false)
        val toolbar = view.findViewById(R.id.toolbar) as android.support.v7.widget.Toolbar
        //toolbar.setNavigationIcon(R.drawable.ic_mtrl_chip_close_circle)
        //toolbar.setNavigationOnClickListener({ view1 -> cancelDialog() })
        toolbar.setTitle(title)
        val textView = view.findViewById(R.id.tv_dialog) as TextView
        textView.setText(text)
        return view
    }

    override fun onStart() {
        super.onStart()
        val dialog = dialog
        if (dialog != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            dialog.window?.setLayout(width, height)
        }
    }

    private fun cancelDialog() {
        print("Cancel")
    }

}


