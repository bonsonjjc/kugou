package com.kugou.view.ui.fragment.mv

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.kugou.R
import com.kugou.commom.framwork.FrameworkFragment
import com.kugou.repository.mv.Video

class MvListFragment : FrameworkFragment() {
    override val layoutId: Int get() = R.layout.layout_list

    val singerId: Int by lazy { arguments!!.getInt("singerId") }
    val singerName: String by lazy { arguments!!.getString("singerName") }

    lateinit var adapter: MvAdapter
    lateinit var viewModel: MvListViewModel
    lateinit var recList: androidx.recyclerview.widget.RecyclerView

    override fun onAttach(context: Context) {
        super.onAttach(context)
        adapter = MvAdapter(context)
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get()
        viewModel.list(singerId, singerName)
        viewModel.dataList.observe(this, Observer<List<Video>> {
            adapter.dataList.addAll(it!!)
            adapter.notifyDataSetChanged()
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recList = view.findViewById(R.id.rec_list)
        recList.adapter = adapter
    }

    companion object {
        fun newInstance(singerId: Int): MvListFragment {
            val bundle = Bundle()
            bundle.putInt("singerId", singerId)
            val fragment = MvListFragment()
            fragment.arguments = bundle
            return fragment
        }

        fun newInstance(singerName: String): MvListFragment {
            val bundle = Bundle()
            bundle.putString("singerName", singerName)
            val fragment = MvListFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}