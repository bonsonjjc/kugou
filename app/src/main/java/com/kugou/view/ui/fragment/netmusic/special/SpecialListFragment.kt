package com.kugou.view.ui.fragment.netmusic.special

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.kugou.R
import com.kugou.commom.binding.CommBindingAdapter
import com.kugou.repository.netmusic.special.Special
import com.kugou.repository.netmusic.special.SpecialChild
import com.kugou.repository.netmusic.special.SpecialService
import com.kugou.view.ui.comm.ScrollFragment
import com.zhouyou.recyclerview.adapter.BaseRecyclerViewAdapter
import io.reactivex.android.schedulers.AndroidSchedulers

class SpecialListFragment : ScrollFragment() {
    override val headLayoutId: Int = R.layout.layout_special_header
    override val contentLayoutId: Int = R.layout.layout_list
    override val toolbarLayoutId: Int = R.layout.layout_toolbar

    lateinit var specialService: SpecialService

    lateinit var adapter: SpecialListAdapter

    val data: SpecialChild by lazy { arguments!!.getParcelable<SpecialChild>("data") }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        adapter = SpecialListAdapter(context)

        specialService = SpecialService()

        specialService.type(data.id, data.special_tag_id, 1, 30)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.status == 1) {
                        adapter.setListAll(it.data!!.info!!)
                    }
                })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recList = view.findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.rec_list)
        CommBindingAdapter.setUrl(view.findViewById(R.id.img_cover), data.bannerurl)
        view.findViewById<TextView>(R.id.tv_title).text = data.name

        view.findViewById<View>(R.id.btn_left).setOnClickListener {
            activity?.onBackPressed()
        }
        recList.adapter = adapter

        adapter.setOnItemClickListener(object : BaseRecyclerViewAdapter.OnItemClickListener<Special> {
            override fun onItemClick(view: View?, item: Special, position: Int) {
                fragmentManager!!.beginTransaction()
                        .hide(this@SpecialListFragment)
                        .add(R.id.container, SpecialFragment.newInstance(item))
                        .addToBackStack("name")
                        .commit()
            }
        })
    }

    companion object {
        fun newInstance(bean: SpecialChild): SpecialListFragment {
            val bundle = Bundle()
            bundle.putParcelable("data", bean)
            val fragment = SpecialListFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}