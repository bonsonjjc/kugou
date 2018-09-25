package com.kugou.view.ui.fragment.netmusic.singer.classify

import androidx.lifecycle.Observer
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.kugou.R
import com.kugou.commom.framwork.FrameworkFragment
import com.kugou.view.ui.fragment.netmusic.singer.classifylist.Classify
import com.kugou.view.ui.fragment.netmusic.singer.SingerFragment
import kotlinx.android.synthetic.main.fragment_singer_classify.*

class ClassifyFragment : FrameworkFragment() {

    override val layoutId: Int = R.layout.fragment_singer_classify

    lateinit var viewModel: ClassifyViewModel

    lateinit var adapter: ClassifyAdapter

    val data by lazy { arguments!!.getParcelable<Classify>("type") }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get()
        adapter = ClassifyAdapter(context)
        viewModel.list(data.musician, data.type, data.sexType)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.rec_list)
        recyclerView.adapter = adapter
        btn_left.setOnClickListener { activity?.onBackPressed() }
        btn_search.setOnClickListener {

        }
        tv_title.text = data.name
        viewModel.hostList.observe(this, Observer {
            adapter.setGroups(it!!)
        })

        adapter.setOnChildClickListener { _, _, groupPosition, childPosition, item ->
            val data = adapter.getGroup(groupPosition).singer!![childPosition]
            fragmentManager!!.beginTransaction()
                    .add(R.id.container, SingerFragment.newInstance(data))
                    .hide(this)
                    .addToBackStack("singer")
                    .commit()
        }
    }

    companion object {
        fun newInstance(classify: Classify): ClassifyFragment {
            val bundle = Bundle()
            bundle.putParcelable("type", classify)
            val fragment = ClassifyFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}