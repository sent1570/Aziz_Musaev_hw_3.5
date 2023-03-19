package com.example.aziz_musaev_hw_35

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.example.aziz_musaev_hw_35.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    var adapter = PixaAdapter(arrayListOf())
    var page = 1
    var boolean = true
    var list2 = arrayListOf<Hit>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initClickers()
    }

    private fun initClickers() {
        with(binding){
imageRecycler.addOnScrollListener(object : OnScrollListener(){
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if(dy > 0){
            val visibleItemCount = recyclerView.layoutManager?.childCount
            val totalItemCount = recyclerView.layoutManager?.itemCount
            val pastItemsVisible = (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
            if(boolean){

                if(visibleItemCount!= null){
                    if((visibleItemCount + pastItemsVisible) >= totalItemCount!!){
boolean = false
                        page++
                        nextPage()
                        boolean = true

                    }
                }
            }
        }
    }



})

            changePageBtn.setOnClickListener{

                ++page
                doRequest()
            }
            enterBtn.setOnClickListener{
                page = 1
                doRequest()
            }
        }
    }

    private fun ActivityMainBinding.doRequest() {
        PixaService().api.getImages(pictureWord = searchEd.text.toString(), page = page)
            .enqueue(object : Callback<PixaModel> {
                override fun onResponse(call: Call<PixaModel>, response: Response<PixaModel>) {
                    if (response.isSuccessful) {
                        val asd = response.body()?.hits!!
                        list2.addAll(asd)

                        adapterInitialization()
                    }
                }

                override fun onFailure(call: Call<PixaModel>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            })
    }
    private fun ActivityMainBinding.nextPage() {
        PixaService().api.getImages(pictureWord = searchEd.text.toString(), page = page)
            .enqueue(object : Callback<PixaModel> {
                override fun onResponse(call: Call<PixaModel>, response: Response<PixaModel>) {
                    if (response.isSuccessful) {
                        list2 = response.body()?.hits!!
                        adapterInitialization()
                    }
                }

                override fun onFailure(call: Call<PixaModel>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            })
    }

    private fun ActivityMainBinding.adapterInitialization(){
        adapter = PixaAdapter(list2)
        imageRecycler.adapter = adapter
    }
}



