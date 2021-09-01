package com.example.delawebtestapp.presentation.lisrt

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.delawebtestapp.App
import com.example.delawebtestapp.R
import com.example.delawebtestapp.data.NetworkRequestState
import com.example.delawebtestapp.databinding.FragmentListNewsBinding
import com.example.delawebtestapp.presentation.FragmentListNewsInterractor
import com.example.delawebtestapp.presentation.base.BaseFragment
import com.example.delawebtestapp.presentation.factory.NewsAdapter

class ListNewsFragment : BaseFragment() {

    private var fragmentInterractor: FragmentListNewsInterractor? = null

    private lateinit var viewModel: ListNewsViewModel

    private var _binding: FragmentListNewsBinding? = null
    private val binding get() = _binding!!

    private var newsAdapter: NewsAdapter? = null

    private var firstResponseError = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initDagger()
        initViewModule()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentListNewsInterractor) {
            fragmentInterractor = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListNewsBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
        setObservers()
        initRecyclerNews()
        initListNews()
    }

    private fun initRecyclerNews() {

        val decoration =
            DividerItemDecoration(binding.recyclerNews.context, DividerItemDecoration.VERTICAL)

        val scrollListener: RecyclerView.OnScrollListener = object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val visibleIteamCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val pastVisiblesIteams = layoutManager.findFirstVisibleItemPosition()
                if ((visibleIteamCount + pastVisiblesIteams) >= totalItemCount) {
                    viewModel.getNextPage()
                }
            }
        }

        initAdapter()

        binding.recyclerNews.apply {
            addOnScrollListener(scrollListener)
            addItemDecoration(decoration)
            adapter = newsAdapter
        }
    }

    private fun initAdapter() {
        newsAdapter =
            NewsAdapter { position -> fragmentInterractor?.onOpenNews(position) }
        newsAdapter?.setHasStableIds(false)
    }

    private fun setListeners() {
        binding.fragmentContentRetryBtn.setOnClickListener {
            initListNews()
        }
    }

    override fun initDagger() {
        (requireActivity().application as App).getAppComponent()
            .registerListNewsComponent()
            .create()
            .inject(this)
    }

    override fun initViewModule() {
        viewModel = ViewModelProvider(this, viewModelFactory)[ListNewsViewModel::class.java]
    }

    private fun setObservers() {
        viewModel.listNews.observe(viewLifecycleOwner) { listNews ->
            newsAdapter?.setListNews(listNews)
        }

        viewModel.diffResult.observe(viewLifecycleOwner) { diffResult ->
            newsAdapter?.let { it -> diffResult.dispatchUpdatesTo(it) }
        }

        viewModel.networkState.observe(viewLifecycleOwner) {
            when (it) {
                NetworkRequestState.LOADING ->
                    binding.fragmentContentProgressBar.isVisible = true
                NetworkRequestState.ERROR -> {
                    binding.fragmentContentProgressBar.isVisible = false
                    if (firstResponseError) {
                        Toast.makeText(
                            context,
                            getString(R.string.error_new_news_massage),
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        binding.fragmentContentErrorLinear.isVisible = true
                    }
                }
                NetworkRequestState.SUCCESS -> {
                    if (!firstResponseError) {
                        firstResponseError = true
                        binding.fragmentContentErrorLinear.isVisible = false
                    }
                    binding.fragmentContentProgressBar.isVisible = false
                }
            }
        }
    }

    private fun initListNews() {
        viewModel.getListNews()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        newsAdapter = null
        _binding?.recyclerNews?.adapter = null
    }

    override fun onDestroy() {
        super.onDestroy()
        fragmentInterractor = null
    }
}