package com.app.hcmut.mymovie.feature.movies

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.hcmut.mymovie.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.bottom_dialog_sign_in.*

class SignInBottomDialog : BottomSheetDialogFragment() {

    private var actionListener: IActionListener? = null

    companion object {
        fun newInstance() = SignInBottomDialog()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.bottom_dialog_sign_in, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnFacebook.setOnClickListener { actionListener?.onClickFacebook() }
        btnGoogle.setOnClickListener { actionListener?.onClickGoogle() }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        actionListener = context as IActionListener
    }

    interface IActionListener {
        fun onClickGoogle()
        fun onClickFacebook()
    }
}