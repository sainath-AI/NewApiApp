package com.masai.sainath.newsapp.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.masai.sainath.newsapp.remote.Status
import com.masai.sainath.newsapp.remote.localDb.ConnectionLiveData
import com.masai.sainath.newsapp.remote.OnItemClicked
import com.masai.sainath.newsapp.remote.responses.ArticleModel
import com.masai.sainath.newsapp.ui.adapters.NewsAdapter
import com.masai.sainath.newsapp.viewModels.AppViewModel
import com.masai.sainath.newsapp.R
import com.masai.sainath.newsapp.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(), OnItemClicked {

    //internet Connectivity check
    private lateinit var connectionLiveData: ConnectionLiveData

    lateinit var bindingHomeFragment: FragmentHomeBinding
    lateinit var adapter: NewsAdapter
    var articleList = emptyList<ArticleModel>()
    var articleListFromDb = mutableListOf<ArticleModel>()
    val viewModel: AppViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bindingHomeFragment =
            DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return bindingHomeFragment.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkNetworkConnectivity()
    }


    private fun checkNetworkConnectivity() {
        connectionLiveData = ConnectionLiveData(requireActivity().application)
        connectionLiveData.observe(viewLifecycleOwner, {
            if (it) {
                loadDatFromApi()
            } else {
                loadDataFromDb()
            }
        })
    }

    private fun setRecyclerView() {
        adapter = NewsAdapter(articleList, this)
        bindingHomeFragment.recyclerView.adapter = adapter
        bindingHomeFragment.recyclerView.layoutManager = LinearLayoutManager(context)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun loadDatFromApi() {
        viewModel.getDataFromAPI().observe(viewLifecycleOwner, {
            when (it.status) {
                Status.ERROR -> {
                    viewModel.getDataFromDb().observe(viewLifecycleOwner, {
                        viewModel.deleteDataFromDb()
                        articleListFromDb.addAll(it)
                        setRecyclerView()
                    })
                    Toast.makeText(context, "Check Internet Connection!", Toast.LENGTH_SHORT).show()
                }
                Status.LOADING -> {
                    Toast.makeText(context, "Still loading", Toast.LENGTH_SHORT).show()
                }
                Status.SUCCESS -> {
                    if (it != null) {
                        articleList = it.data?.articleModels!!
                        setRecyclerView()
                        adapter.notifyDataSetChanged()
                    }
                    viewModel.insertDataToDb(it.data?.articleModels!!)
                }
            }
        })
    }

    private fun loadDataFromDb() {
        viewModel.getDataFromDb().observe(viewLifecycleOwner, {
            viewModel.deleteDataFromDb()
            articleListFromDb.addAll(it)
            setRecyclerView()
        })
    }

    override fun onCardClicked(articleModel: ArticleModel) {
        val action = HomeFragmentDirections.actionHomeFragmentToOnDetailsFragment(articleModel)
        Navigation.findNavController(requireView()).navigate(action)
    }
}