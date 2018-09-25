package com.kugou.view.ui.fragment.netmusic.singer.heat

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.kugou.R
import com.kugou.commom.framwork.FrameworkFragment
import com.kugou.repository.netmusic.singer.Singer
import com.kugou.view.ui.adapter.OnItemClickListener
import com.kugou.view.ui.fragment.netmusic.singer.SingerFragment
import com.kugou.view.ui.fragment.netmusic.singer.classifylist.ClassifyListFragment
import kotlinx.android.synthetic.main.fragment_singers.*

class SingerHeatFragment : FrameworkFragment() {
    lateinit var adapter: SingerHeatAdapter
    lateinit var viewModel: SingerHeatViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get()
        adapter = SingerHeatAdapter(context)
        val observe = Observer<List<Singer>> { t ->
            adapter.dataList.clear()
            adapter.dataList.addAll(t!!)
            adapter.notifyDataSetChanged()
            rec_list.scrollToPosition(0)
        }
        viewModel.hostList.observe(this, observe)
        viewModel.roaringList.observe(this, observe)
        viewModel.hostList(1)
    }

    override val layoutId: Int get() = R.layout.fragment_singers

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rec_list.adapter = adapter
        btn_singer_tags.setOnClickListener {
            val classifyListFragment = ClassifyListFragment()
            childFragmentManager
                    .beginTransaction()
                    .add(R.id.singer_heart_container, classifyListFragment)
                    .commit()
        }
        tv_heat.isSelected = true
        val onClick = View.OnClickListener { it -> select(it) }
        tv_roaring.setOnClickListener(onClick)
        tv_heat.setOnClickListener(onClick)

        adapter.onItemClickListener = object : OnItemClickListener<Singer> {
            override fun itemClick(position: Int, data: Singer) {
                fragmentManager!!.beginTransaction()
                        .hide(this@SingerHeatFragment)
                        .add(R.id.container, SingerFragment.newInstance(data))
                        .addToBackStack("singer")
                        .commit()
            }
        }
    }

    fun select(view: View) {
        if (view == tv_heat && view.isSelected.not()) {
            viewModel.hostList(1)
            view.isSelected = true
            tv_roaring.isSelected = false
            return
        }
        if (view == tv_roaring && view.isSelected.not()) {
            viewModel.hostList(2)
            view.isSelected = true
            tv_heat.isSelected = false
        }
    }
}