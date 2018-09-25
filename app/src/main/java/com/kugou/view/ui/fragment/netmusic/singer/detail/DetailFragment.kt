package com.kugou.view.ui.fragment.netmusic.singer.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.kugou.R
import com.kugou.commom.BindingFragment
import com.kugou.commom.WebFragment
import com.kugou.databinding.FragmentSingerDetailBinding
import com.kugou.view.ui.WebActivity

class DetailFragment : BindingFragment<FragmentSingerDetailBinding>() {
    lateinit var viewModel: DetailViewModel

    override val layoutId: Int = R.layout.fragment_singer_detail

    val singerId: Int by lazy { arguments!!.getInt("singerId") }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get()
        viewModel.detail(singerId)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.setLifecycleOwner(this)
        binding.tvLinkDetail.setOnClickListener {
            val url = "http://m.kugou.com/webapp/singerInfo/info.html?id=$singerId"
            val intent = Intent(activity, WebActivity::class.java)
            intent.putExtra("url", url)
            intent.putExtra("title", "歌手详情")
            startActivity(intent)
        }
    }

    companion object {
        fun newInstance(singerId: Int): DetailFragment {
            val bundle = Bundle()
            bundle.putInt("singerId", singerId)
            val fragment = DetailFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}