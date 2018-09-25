package com.kugou.view.ui.fragment.netmusic.special

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.kugou.R
import com.kugou.commom.WebFragment
import com.kugou.commom.framwork.FrameworkFragment
import com.kugou.repository.netmusic.special.SpecialGroup
import com.kugou.repository.netmusic.special.SpecialService
import com.zhouyou.recyclerview.adapter.HelperRecyclerViewHolder
import com.zhouyou.recyclerview.group.GroupedRecyclerViewAdapter
import com.zhouyou.recyclerview.manager.StateGridLayoutManager
import io.reactivex.android.schedulers.AndroidSchedulers

class SpecialMainFragment : FrameworkFragment() {
    override val layoutId: Int = R.layout.layout_list
    lateinit var adapter: SpecialAdapter


    override fun onAttach(context: Context) {
        super.onAttach(context)
        adapter = SpecialAdapter(context)
        specialService = SpecialService()

        specialService.tags()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.status == 1) {
                        val list = it.data!!.info!!
                        adapter.addGroups(list)
                    }
                }, {
                    it.printStackTrace()
                })
    }


    lateinit var specialService: SpecialService

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.rec_list)
        recyclerView.adapter = adapter

        val layoutManager = StateGridLayoutManager(context, 4)
        recyclerView.layoutManager = layoutManager

        adapter.setOnChildClickListener(object : GroupedRecyclerViewAdapter.OnChildClickListener<SpecialGroup> {
            override fun onChildClick(adapter: GroupedRecyclerViewAdapter<*>?, holder: HelperRecyclerViewHolder?, groupPosition: Int, childPosition: Int, item: SpecialGroup) {
                val data = item.children!![childPosition]
                if (TextUtils.isEmpty(data.jump_url)) {
                    fragmentManager!!.beginTransaction()
                            .add(R.id.container, SpecialListFragment.newInstance(data))
                            .hide(this@SpecialMainFragment)
                            .addToBackStack("name")
                            .commit()
                } else {
                    fragmentManager!!.beginTransaction()
                            .add(R.id.container, WebFragment.newInstance(data.name!!, data.jump_url!!))
                            .hide(this@SpecialMainFragment)
                            .addToBackStack("name")
                            .commit()
                }
            }
        })
    }
}