package com.kugou.view.ui.fragment.netmusic.rank

import android.content.Context
import android.os.Bundle
import androidx.annotation.CallSuper
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isGone
import com.kugou.R
import com.kugou.commom.binding.CommBindingAdapter
import com.kugou.repository.netmusic.rank.RankTag
import com.kugou.view.ui.comm.ScrollFragment
import com.kugou.view.ui.dialog.SpecialDetailDialog
import com.kugou.view.ui.dialog.WeekSelectDialog
import java.text.SimpleDateFormat
import java.util.*

abstract class RankFragment : ScrollFragment() {
    override val headLayoutId: Int = R.layout.layout_rank_header
    override val toolbarLayoutId: Int = R.layout.layout_toolbar
    lateinit var rankTag: RankTag

    lateinit var tvTitle: TextView
    lateinit var tvCycle: TextView
    lateinit var tvUpateTime: TextView
    lateinit var imgCover: ImageView


    @CallSuper
    override fun onAttach(context: Context) {
        super.onAttach(context)
        rankTag = arguments!!.getParcelable("tag")
    }

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvTitle = view.findViewById(R.id.tv_title)
        tvCycle = view.findViewById(R.id.tv_cycle)
        tvUpateTime = view.findViewById(R.id.tv_update_time)
        imgCover = view.findViewById(R.id.imgCover)

        view.findViewById<ImageView>(R.id.btn_left).setOnClickListener {
            activity?.onBackPressed()
        }
        view.findViewById<View>(R.id.tv_detail).setOnClickListener {
            SpecialDetailDialog.newInstance(rankTag).show(fragmentManager, "detail")
        }

        CommBindingAdapter.setUrl(imgCover, rankTag.banner7url)

        var updateTime = update(Date())

        if (rankTag.isvol == 1) {
            updateTime += " |"
        }

        tvUpateTime.text = updateTime
        tvTitle.text = rankTag.rankname
        tvCycle.isGone = rankTag.isvol == 0
    }


    fun update(time: Date): String {
        return SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).format(time) + " 更新"
    }
}