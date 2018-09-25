package com.kugou.view.ui.dialog

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import android.view.*
import com.blankj.rxbus.RxBus
import com.kugou.R
import com.kugou.player.manager.Action
import com.kugou.player.PlayerBackUtils
import com.kugou.player.manager.PlayAction
import com.kugou.utils.screen
import io.reactivex.android.schedulers.AndroidSchedulers

class PlayListDialog : DialogFragment() {
    lateinit var adapter: PlayListAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_playlist, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        adapter = PlayListAdapter(context)
        adapter.setListAll(PlayerBackUtils.playList())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        view.findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.rec_list).adapter = adapter
        RxBus.getDefault().subscribe(this, AndroidSchedulers.mainThread(), object : RxBus.Callback<Action> {
            override fun onEvent(action: Action) {
                if (action.action == PlayAction.PLAY_ACTION_MUSIC) {
                    adapter.notifyDataSetChanged()
                }
            }
        })

        adapter.setOnItemClickListener { _, item, position ->
            PlayerBackUtils.play(position, adapter.list)
        }
    }

    override fun onDetach() {
        super.onDetach()
        RxBus.getDefault().unregister(this)
    }

    override fun onResume() {
        super.onResume()
        // 设置背景透明
        dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setCanceledOnTouchOutside(true)
        dialog.window!!.setGravity(Gravity.BOTTOM)
        val attributes = dialog.window!!.attributes
        attributes.width = ViewGroup.LayoutParams.MATCH_PARENT
        attributes.height = context!!.screen().y / 2
        dialog.window!!.attributes = attributes
    }
}