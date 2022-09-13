package com.example.alertdialogsample

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.roshka.porteriadta.R
import java.lang.IllegalStateException

class MyDialog: DialogFragment(){
//    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
//        return activity?.let {
//            val alertBuilder = AlertDialog.Builder(it)
//            var checkedIndex = ArrayList<Int>()
//
//            alertBuilder.setTitle("Select an option")
//           alertBuilder.setMultiChoiceItems(R.array.data_list, null, DialogInterface.OnMultiChoiceClickListener{
//                _, index, checked ->
//                if (checked){
//                    checkedIndex.add(index)
//                } else if (checkedIndex.contains(index)){
//                    checkedIndex.remove(index)
//                }
//            })
//            alertBuilder.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, id ->
//                Log.d("DialogLog", "OK pressed , checked index is $checkedIndex")
//            })
//
//            alertBuilder.create()
//
//        } ?: throw IllegalStateException("Exception !! Activity is null")
//    }

}