package com.kugou.view.ui.fragment.netmusic.singer.classifylist

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.children
import com.kugou.R
import com.kugou.commom.framwork.FrameworkFragment
import com.kugou.view.ui.fragment.netmusic.singer.classify.ClassifyFragment

class ClassifyListFragment : FrameworkFragment() {
    override val layoutId: Int = R.layout.fragment_singer_special

    val names = arrayOf(Classify("华语男歌手", 1, 1), Classify("华语女歌手", 1, 2), Classify("华语女组合", 1, 3)
            , Classify("欧美男歌手", 2, 1), Classify("欧美女歌手", 2, 2), Classify("欧美组合", 2, 3)
            , Classify("韩国男歌手", 3, 1), Classify("韩国女歌手", 3, 2), Classify("韩国组合", 3, 3)
            , Classify("日本男歌手", 5, 1), Classify("日本女歌手", 5, 2), Classify("日本组合", 5, 3)
            , Classify("其他歌手", 4, 0), Classify("原创音乐人", 0, 0, 3))


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val itemsParent = view.findViewById<ViewGroup>(R.id.special_items)
        itemsParent.children.forEach {
            if (it is TextView) {
                it.setOnClickListener {
                    dismiss()
                    val index = Integer.parseInt(it.tag.toString())
                    val type = names[index]
                    val fragment = ClassifyFragment.newInstance(type)
                    parentFragment!!.fragmentManager!!.beginTransaction()
                            .add(R.id.container, fragment)
                            .hide(parentFragment!!)
                            .addToBackStack("classify")
                            .commit()
                }
            }
        }
        view.findViewById<View>(R.id.view_out).setOnClickListener {
            dismiss()
        }
    }

    private fun dismiss() {
        fragmentManager!!.beginTransaction().remove(this).commit()
    }
}