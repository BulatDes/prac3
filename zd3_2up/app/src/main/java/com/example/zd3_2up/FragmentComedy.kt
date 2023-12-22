package com.example.zd3_2up

import android.content.ContentValues
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentComedy.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentComedy : Fragment() {
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentLayout=inflater.inflate(R.layout.fragment_comedy, container, false)
        val fetchMoviesTask = FetchMoviesTask(fragmentLayout)
        fetchMoviesTask.execute()
        return fragmentLayout
    }
    val apiKey = "46fc067b"
    var answer: MutableList<Movie> = mutableListOf()
    private inner class FetchMoviesTask(val fragmentLayout: View) : AsyncTask<Void, Void, String>() {
        override fun doInBackground(vararg params: Void?): String? {
            try {
                val apiUrl = "https://www.omdbapi.com/?apikey=$apiKey&s=comedia&type=movie&plot=full"
                val url = URL(apiUrl)
                val connection = url.openConnection() as HttpURLConnection
                val scanner = Scanner(connection.inputStream, "UTF-8").useDelimiter("\\A")
                return if (scanner.hasNext()) scanner.next() else null
            } catch (e: Exception) {
                Log.e(ContentValues.TAG, "Ошибка при выполнении запроса: ${e.message}")
                return null
            }
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            if (result != null) {
                val gson = Gson()
                val data = gson.fromJson(result, Map::class.java)

                // Печатаем топ-6 комедий
                if (data.containsKey("Search")) {
                    val top5HorrorMovies = (data["Search"] as List<Map<String, String>>).take(6)
                    top5HorrorMovies.forEach { movie ->
                        val img = "${movie["Poster"]}"
                        val name = "${movie["Title"]}"
                        val info = "Год выпуска: ${movie["Year"]}, Тип: ${movie["Type"]}"
                        val wr: Movie = Movie( name, img, info)
                        answer.add(wr)
                        if(answer.size == 6){
                            val rec: RecyclerView = fragmentLayout.findViewById(R.id.recyclerView2)
                            rec.layoutManager = GridLayoutManager(requireContext(), 3)
                            rec.adapter = FillScreen(requireContext(), MyObjTwo(answer).list)
                            Log.d(ContentValues.TAG, name + " " + img + " " + info)
                        }

                    }
                } else {
                    Log.e(ContentValues.TAG, "Не удалось получить данные. Пожалуйста, проверьте ваш API-ключ и запрос.")
                }
            }
        }

    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FragmentComedy.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentComedy().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}