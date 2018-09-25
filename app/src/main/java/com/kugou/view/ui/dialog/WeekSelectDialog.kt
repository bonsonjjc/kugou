package com.kugou.view.ui.dialog

import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import com.kugou.R
import com.kugou.repository.netmusic.rank.RankYear
import com.kugou.repository.netmusic.rank.Volid
import com.kugou.view.ui.adapter.WeekAdapter
import kotlinx.android.synthetic.main.dialog_week_select.*

class WeekSelectDialog : DialogFragment() {
    val dataList: List<RankYear>  by lazy { arguments!!.getParcelableArrayList<RankYear>("list") }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.dialog_week_select, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        rec_year.adapter = WeekAdapter(dataList, context!!)
        rec_week.adapter = WeekAdapter(dataList[0].vols!!, context!!)

        WheelHelper().attachToRecyclerView(rec_year)
        WheelHelper().attachToRecyclerView(rec_week)
    }

    override fun onResume() {
        super.onResume()
        // 设置背景透明
        dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setCanceledOnTouchOutside(true)
        val attributes = dialog.window!!.attributes
        attributes.width = ViewGroup.LayoutParams.MATCH_PARENT
        attributes.height = ViewGroup.LayoutParams.WRAP_CONTENT
        attributes.gravity = Gravity.BOTTOM
        dialog.window!!.attributes = attributes
    }

    companion object {
        fun newInstance(list: ArrayList<RankYear>): WeekSelectDialog {
            val bundle = Bundle()
            bundle.putParcelableArrayList("list", list)
            val fragment = WeekSelectDialog()
            fragment.arguments = bundle
            return fragment
        }
    }
}