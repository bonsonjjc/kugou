package com.kugou.view.ui.dialog

import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import com.kugou.R
import com.kugou.commom.binding.CommBindingAdapter
import com.kugou.repository.netmusic.rank.RankTag
import kotlinx.android.synthetic.main.dialog_speical_detial.*

class SpecialDetailDialog : DialogFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_speical_detial, container, false)
    }

    val rankTag: RankTag by lazy { arguments!!.getParcelable<RankTag>("data") }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        tv_title.text = rankTag.rankname
        val arrays = rankTag.intro!!.split("\r\n")
        tv_source.text = arrays[0]
        tv_sort.text = arrays[1]
        tv_week.text = arrays[2]
        CommBindingAdapter.setUrl(img_cover, rankTag.banner7url)

        img_close.setOnClickListener {
            dismiss()
        }
    }

    override fun onResume() {
        super.onResume()
        // 设置背景透明
        dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setCanceledOnTouchOutside(true)
        val attributes = dialog.window!!.attributes
        attributes.width = ViewGroup.LayoutParams.MATCH_PARENT
        attributes.height = ViewGroup.LayoutParams.MATCH_PARENT
        dialog.window!!.attributes = attributes
    }

    companion object {
        fun newInstance(rankTag: RankTag): SpecialDetailDialog {
            val bundle = Bundle()
            bundle.putParcelable("data", rankTag)

            val fragment = SpecialDetailDialog()
            fragment.arguments = bundle
            return fragment
        }
    }
}