package com.example.nikulsheev.ui.main

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.nikulsheev.R
import com.example.nikulsheev.databinding.FragmentMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * A placeholder fragment containing a simple view.
 */
class PlaceholderFragment : Fragment() {

    private lateinit var pageViewModel: PageViewModel
    private var _binding: FragmentMainBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageViewModel = ViewModelProvider(this).get(PageViewModel::class.java).apply {
            setIndex(arguments?.getInt(ARG_SECTION_NUMBER) ?: 1)
        }


    }

    @SuppressLint("ResourceType")
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMainBinding.inflate(inflater, container, false)
        val root = binding.root





        loadGIF("android.resource://com.example.nikulsheev/drawable/jumpingarrow")

        val retrofit = Retrofit.Builder()
            .baseUrl("https://developerslife.ru/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service : EchoController = retrofit.create(EchoController::class.java)
        val call: Call<gif> = service.test(true)
        _binding!!.btnNext.setOnClickListener {

        call.clone().enqueue(object :Callback<gif>{
            override fun onFailure(call: Call<gif>, t: Throwable) {

                Log.v("MYTAG","fail")
            }

            override fun onResponse(call: Call<gif>, response: Response<gif>) {
                Log.v("MYTAG","success")
                val gif:gif? = response.body()

                if (gif != null) {
                    Log.v("MYTAG",gif.gifURL)
                    loadGIF(gif.gifURL)
                }
            }
        })
        }

        return root
    }

    private fun loadGIF(url : String?){
        //Glide.with(this).load(url)
        //    .placeholder(R.drawable.load)
        //    .error(R.drawable.test).into(_binding!!.gifHolder)

        Glide.with(this).load(url)
            .thumbnail(Glide.with(this).load(R.drawable.load))
            .fitCenter()
            .error(R.drawable.error)
            .into(_binding!!.gifHolder);

    }

    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private const val ARG_SECTION_NUMBER = "section_number"

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        @JvmStatic
        fun newInstance(sectionNumber: Int): PlaceholderFragment {
            return PlaceholderFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}