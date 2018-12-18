package com.sosina.budgetplannertogo

import android.app.Activity
import android.os.Bundle
import android.preference.DialogPreference
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast

class AlertDialogFragment : DialogFragment() {

        private var content: String? = null

    companion object {

        fun newInstance(content: String): AlertDialogFragment {
            val f = AlertDialogFragment()

            // Supply num input as an argument.
            val args = Bundle()
            args.putString("content", content)
            f.arguments = args

            return f

        }
    }
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            content = arguments?.getString("content")

            // Pick a style based on the num.
            val style = DialogFragment.STYLE_NO_FRAME
            val theme = R.style.DialogTheme
            setStyle(style, theme)
        }

        // Override the Fragment.onAttach() method to instantiate the
        // NoticeDialogListener
        override fun onAttach(activity: Activity?) {
            super.onAttach(activity)



        }

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            val view = inflater!!.inflate(R.layout.alert_dialog_fragment, container, false)

            val btnCancel = view.findViewById<View>(R.id.buttonCancel) as Button
            val btnAccept = view.findViewById<View>(R.id.buttonAccept) as Button

            val textViewContent = view.findViewById<View>(R.id.textViewContent) as TextView
            textViewContent.text = content

            //FontUtils.setTypeface(getActivity(), textViewQuestion, "fonts/mangal.ttf");
            //FontUtils.setTypeface(getActivity(), textViewAnswer, "fonts/mangal.ttf");
            btnCancel.setOnClickListener {
                Toast.makeText(activity, "action cancelled", Toast.LENGTH_SHORT).show()
                dismiss()
            }

            btnAccept.setOnClickListener {
                Toast.makeText(activity, "User Accepted Action", Toast.LENGTH_SHORT).show()
                dismiss()
            }

            return view
        }



    }

