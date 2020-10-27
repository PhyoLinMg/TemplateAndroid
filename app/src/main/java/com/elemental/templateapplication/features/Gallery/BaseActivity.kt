package com.elemental.templateapplication.features.Gallery

import android.app.Dialog
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.elemental.templateapplication.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textview.MaterialTextView
import java.util.*


abstract class BaseActivity:AppCompatActivity() {
    protected fun showStringSearchDialog(
        items: List<String>,
        title: String = "",
        hint: String = "Find...",
        action: (position: Int) -> Unit
    ) {
        val dialog = Dialog(this, R.style.AppTheme)
        val view: View = LayoutInflater.from(this).inflate(R.layout.dialog_list_search, null)

        var itemList = items

        val tvTitle: MaterialTextView = view.findViewById(R.id.tv_title)
        val btnClose: ImageView = view.findViewById(R.id.btn_close)
        val etSearch: TextInputEditText = view.findViewById(R.id.et_search)
        val rvItems: RecyclerView = view.findViewById(R.id.rv_items)
        val llEmptyResult: LinearLayout = view.findViewById(R.id.ll_empty_result)

        tvTitle.text = title
        etSearch.hint = hint

        rvItems.visibility = View.VISIBLE
        llEmptyResult.visibility = View.GONE

        val adapter = StringAdapter(this)
        adapter.setOnItemClickListener(object : StringAdapter.OnItemClickListener {
            override fun onItemClick(position: Int, item: String, v: View?) {
                val imm: InputMethodManager =
                    getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(
                    etSearch.windowToken, InputMethodManager.SHOW_IMPLICIT
                )

                action(position)
                dialog.dismiss()
            }
        })

        etSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                itemList = if (etSearch.text.toString().trim() != "") {
                    items.filter {
                        it.toLowerCase(Locale.ENGLISH).contains(
                            etSearch.text.toString().trim().toLowerCase(Locale.ENGLISH)
                        )
                    }
                } else {
                    items
                }
                adapter.setData(itemList)
                if (itemList.isEmpty()) {
                    rvItems.visibility = View.GONE
                    llEmptyResult.visibility = View.VISIBLE
                } else {
                    llEmptyResult.visibility = View.GONE
                    rvItems.visibility = View.VISIBLE
                }
            }
        })

        adapter.setData(itemList)

        rvItems.adapter = adapter
        rvItems.layoutManager = LinearLayoutManager(this)

        dialog.setContentView(view)
        dialog.show()

        btnClose.setOnClickListener {
            val imm: InputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(
                etSearch.windowToken, InputMethodManager.SHOW_IMPLICIT
            )

            dialog.dismiss()
        }
    }
}