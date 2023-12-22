package com.example.zd3_2up

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentSearch.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentSearch : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }
    private val apiKey = "46fc067b"
    private val omdbApiService: OmdbApiService
    private lateinit var editTxt: EditText
    private lateinit var img: ImageView
    private lateinit var writeTxt: TextView
    private lateinit var button : Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentLayout=inflater.inflate(R.layout.fragment_search, container, false)
        editTxt = fragmentLayout.findViewById(R.id.seachtext)
        img = fragmentLayout.findViewById(R.id.setImg)
        writeTxt = fragmentLayout.findViewById(R.id.setTxt)
        button = fragmentLayout.findViewById(R.id.buttonSeqcrh)
        button.setOnClickListener {
            val searchTxt = editTxt.text
            if(searchTxt.isNotEmpty()){
                vivod(searchTxt.toString(), img, writeTxt)

            }
            else{
                Toast.makeText(requireContext(), "Ошибка. Введите название фильма и попробуйте еще раз", Toast.LENGTH_SHORT).show()
            }
        }

        return fragmentLayout
    }
    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.omdbapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        omdbApiService = retrofit.create(OmdbApiService::class.java)
    }
    private fun vivod(nameFilm: String, img: ImageView, txt: TextView) {
        val call = omdbApiService.getMovieDetails(apiKey, nameFilm)

        call.enqueue(object : Callback<MovieSearch> {
            override fun onResponse(call: Call<MovieSearch>, response: Response<MovieSearch>) {
                if (response.isSuccessful) {
                    val movieSearch = response.body()

                    if (movieSearch != null) {
                        val title = movieSearch.Title
                        val posterUrl = movieSearch.Poster
                        val relis = movieSearch.Released
                        val year = movieSearch.Year
                        val writter = movieSearch.Writer
                        val actors = movieSearch.Actors
                        Log.d("MovieDetails", "$response")
                        Log.d("MovieDetails", "Title: $title, Poster URL: $posterUrl, Realesed $relis")


                        Picasso.get().load(posterUrl).into(img)
                        txt.text = "Название: $title\nРелиз: $relis\nГода: $year\nРежисер: $writter\nАктеры: $actors"
                        if(txt.text.isEmpty()){
                            Toast.makeText(requireContext(), "Ошибка. Ничего не найдено", Toast.LENGTH_SHORT).show()
                        }
                    }
                    else{
                        Toast.makeText(requireContext(), "Ошибка. Ничего не найдено", Toast.LENGTH_SHORT).show()
                    }
                }
                else{
                    Toast.makeText(requireContext(), "Ошибка. Ничего не найдено", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<MovieSearch>, t: Throwable) {
                // Обработка ошибок
                Log.e("MovieDetails", "Failed to retrieve movie details", t)
            }
        })
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FragmentSearch.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentSearch().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}