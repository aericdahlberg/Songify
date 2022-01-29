package com.ait.songifyait.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.ait.songifyait.Compare
import com.ait.songifyait.MainActivity
import com.ait.songifyait.data.URL
import com.ait.songifyait.databinding.ActivityDialogBinding
import java.lang.RuntimeException

class Dialog : DialogFragment() {
    companion object{
        var FIRST_URL : String = "FIRST_URL"
        var SECOND_URL : String = "SECOND_URL"

    }



    public interface URLHandler{
        fun urlCreated(url: URL)
    }

    lateinit var dialogBinding: ActivityDialogBinding
    lateinit var urlHandler : URLHandler

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is URLHandler){
            urlHandler = context
        }else {
            throw RuntimeException("The handler is not being implemented correctly")
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder = AlertDialog.Builder(requireContext())

        builder.setTitle("Enter Spotify track URLs to compare")

        dialogBinding = ActivityDialogBinding.inflate(layoutInflater)
        builder.setView(dialogBinding.root)



        builder.setPositiveButton("Search"){
            dialog, which ->
            //Put the entries as string extras to result activity
                var firstURL = dialogBinding.etDialogSong1.text.toString()
                var secondURL = dialogBinding.etDialogSong2.text.toString()

                firstURL = getURI(firstURL)
                secondURL = getURI(secondURL)
                var resultIntent = Intent()
                resultIntent.putExtra(FIRST_URL, firstURL)
                resultIntent.putExtra(SECOND_URL, secondURL)

                resultIntent.setClass((context as MainActivity), Compare::class.java)
                startActivity(resultIntent)


        }

        builder.setNegativeButton("Cancel"){
            dialog, which ->
        }

        return builder.create()
    }

    fun getURI(url: String): String {
        var trackURI = ""
        for (i in 31..52) {
            trackURI += url[i]
        }
        return trackURI
    }

}