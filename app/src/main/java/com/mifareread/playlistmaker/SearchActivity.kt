package com.mifareread.playlistmaker

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SearchActivity:AppCompatActivity() {

    private var searchString:String = STRING_DEF
    private val itunesBaseUrl = "https://itunes.apple.com"

    private val retrofit = Retrofit
        .Builder()
        .baseUrl(itunesBaseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private lateinit var placeholder : LinearLayout
    private lateinit var emptyImage : ImageView
    private lateinit var connectProblemImage : ImageView
    private lateinit var placeholderText : TextView
    private lateinit var placeholderButton: Button
    private val ITunesService = retrofit.create(ITunesApi::class.java)

    private val tracks = mutableListOf<Track>()
    private val adapter = TracksAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val buttonClear = findViewById<ImageView>(R.id.clearIcon)
        val searchEditText = findViewById<EditText>(R.id.searchEditText)
        val toolbarBack = findViewById<MaterialToolbar>(R.id.search_button)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        placeholder = findViewById(R.id.placeholder)
        emptyImage = findViewById(R.id.empty_image)
        connectProblemImage = findViewById(R.id.connection_problem)
        placeholderButton = findViewById(R.id.placeholder_button)
        placeholderText = findViewById(R.id.placeholder_text)

        searchEditText.setText(searchString)
        searchEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                search()
                true
            }
            false
        }

        buttonClear.setOnClickListener{ view ->
            searchEditText.setText("")
            hideKeyboard(view)
            clean()
            placeholder.visibility = View.GONE
        }

        placeholderButton.setOnClickListener {
            search()
        }

        toolbarBack.setOnClickListener {
            finish()
        }

        val searchTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0.isNullOrEmpty()) {}
                else{
                    searchString = p0.toString()
                }
                buttonClear.visibility= if (p0.isNullOrEmpty())
                    { View.GONE }
                    else{View.VISIBLE}
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        }
        searchEditText.addTextChangedListener(searchTextWatcher)

        adapter.tracks = tracks
        recyclerView.adapter = adapter

    }

    private fun hideKeyboard(view : View){
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        inputMethodManager?.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(SEARCH_STRING, searchString)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        searchString = savedInstanceState.getString(SEARCH_STRING, STRING_DEF)
    }

    private fun search(){
        if( searchString.isNotEmpty() == true){
            ITunesService.search(searchString)
                .enqueue(object : Callback<TracksResponse>{
                    @SuppressLint("NotifyDataSetChanged")
                    override fun onResponse(
                        call: Call<TracksResponse>,
                        response: Response<TracksResponse>
                    ) {
                        when(response.code()){
                            200 -> {
                                tracks.clear()
                                val listTracks = response.body()?.results!!

                                if(listTracks.isNotEmpty()){
                                    tracks.addAll(listTracks)
                                }
                                adapter.notifyDataSetChanged()

                                if( tracks.isEmpty() ){
                                    showMessage(MessageResponse.ERROR_EMPTY)
                                }
                                else{
                                    showMessage(MessageResponse.ERROR_OK)
                                }
                            }
                            else->{
                                showMessage(MessageResponse.ERROR_CONNECTION_PROBLEM)
                            }
                        }

                    }

                    override fun onFailure(call: Call<TracksResponse>, t: Throwable) {
                        showMessage(MessageResponse.ERROR_CONNECTION_PROBLEM)

                    }
                })
        }
    }

    private fun showMessage(type: MessageResponse) {
        when(type){
            MessageResponse.ERROR_OK -> {
                placeholder.visibility = View.GONE
            }
            MessageResponse.ERROR_EMPTY ->{
                clean()
                placeholder.visibility = View.VISIBLE
                emptyImage.visibility = View.VISIBLE
                connectProblemImage.visibility = View.GONE
                placeholderText.text = getString(R.string.search_empty)
                placeholderButton.visibility = View.GONE
            }
            MessageResponse.ERROR_CONNECTION_PROBLEM -> {
                clean()
                placeholder.visibility = View.VISIBLE
                emptyImage.visibility = View.GONE
                connectProblemImage.visibility = View.VISIBLE
                placeholderText.text = getString(R.string.connection_problem)
                placeholderButton.visibility = View.VISIBLE
            }
        }
    }


    @SuppressLint("NotifyDataSetChanged")
    private fun clean(){
        tracks.clear()
        adapter.notifyDataSetChanged()
    }

    companion object{
        const val SEARCH_STRING = "SEARCH_STRING"
        const val  STRING_DEF = ""
    }

    enum class MessageResponse {
        ERROR_OK,                     //успешный ответ
        ERROR_EMPTY,                  //пустой список
        ERROR_CONNECTION_PROBLEM      //проблемы со связью
    }

}

