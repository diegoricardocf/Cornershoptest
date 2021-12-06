package com.example.cornershop.helper

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import com.example.cornershop.R
import java.util.*

class ProgressDialog : androidx.fragment.app.DialogFragment() {

    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        isCancelable = false
        return AlertDialog.Builder(activity).setView(requireActivity().layoutInflater.inflate(R.layout.dialog_generic_progress, null)).create()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Objects.requireNonNull<Window>(dialog?.window)
            .setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    fun show(manager: androidx.fragment.app.FragmentManager) {
        if (show || dialog != null && dialog!!.isShowing) return

        try {
            show = true
            super.show(manager, TAG)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun dismiss() {
        try {
            show = false
            super.dismiss()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    companion object {

        private const val TAG = "loading"
        private var genericProgressDialog: ProgressDialog? = null
        private var show = false

        val instance: ProgressDialog
            @Synchronized get() {
                if (genericProgressDialog == null) {
                    genericProgressDialog = ProgressDialog()
                }
                return genericProgressDialog!!
            }
    }
}
