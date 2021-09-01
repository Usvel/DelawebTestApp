package com.example.delawebtestapp.presentation.news

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.delawebtestapp.App
import com.example.delawebtestapp.R
import com.example.delawebtestapp.databinding.FragmentNewsBinding
import com.example.delawebtestapp.presentation.FragmentNewsInterractor
import com.example.delawebtestapp.presentation.MainActivity
import com.example.delawebtestapp.presentation.base.BaseFragment

class NewsFragment : BaseFragment() {

    private var fragmentInterractor: FragmentNewsInterractor? = null

    private lateinit var viewModel: NewsViewModel
    private var idNews = 0

    companion object {

        private const val ARG_ID_NEWS = "ID_NEWS"

        fun newInstance(index: Int) =
            NewsFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_ID_NEWS, index)
                }
            }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentNewsInterractor) {
            fragmentInterractor = context
        }
    }

    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)

        _binding = FragmentNewsBinding.inflate(inflater, container, false)
        idNews = requireArguments().getInt(ARG_ID_NEWS)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setBackBtn()
        setListeners()
        setObservers()
        initNews()
    }

    private fun setBackBtn() {
        (requireActivity() as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setListeners() {
        binding.btnUrlNews.setOnClickListener {
            viewModel.news.value?.url?.let { url ->
                fragmentInterractor?.openWebPage(
                    url
                )
            }
        }
    }

    private fun initNews() {
        viewModel.getNews(idNews)
    }

    private fun setObservers() {
        viewModel.news.observe(viewLifecycleOwner, {
            Glide.with(this).load(it.urlImage).listener(object : RequestListener<Drawable> {
                @SuppressLint("ResourceType")
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    binding.imageNews.setImageResource(R.raw.image_news)
                    return true
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }
            }).into(binding.imageNews)
            binding.textTitleNews.text = it.title
            binding.textContentNews.text = it.description
        })
    }

    override fun initDagger() {
        (requireActivity().application as App).getAppComponent()
            .registerNewsComponent()
            .create()
            .inject(this)
    }

    override fun initViewModule() {
        viewModel = ViewModelProvider(this, viewModelFactory)[NewsViewModel::class.java]
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (requireActivity() as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        _binding = null
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            requireActivity().onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDetach() {
        super.onDetach()
        fragmentInterractor = null
    }
}