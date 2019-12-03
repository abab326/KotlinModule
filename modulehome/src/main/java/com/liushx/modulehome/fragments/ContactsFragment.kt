package com.liushx.modulehome.fragments

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemChildClickListener
import com.liushx.corelibrary.base.BaseFragment
import com.liushx.corelibrary.mvp.MvpView
import com.liushx.modulehome.R
import com.liushx.modulehome.adapters.CityAdpater
import com.liushx.modulehome.contracts.ContactContract
import com.liushx.modulehome.models.CityBean
import com.liushx.modulehome.persenters.ContactsPresenter
import com.mcxtzhang.indexlib.suspension.SuspensionDecoration
import com.orhanobut.logger.Logger
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener
import kotlinx.android.synthetic.main.fragment_contacts.*


class ContactsFragment : BaseFragment<MvpView, ContactsPresenter>(), ContactContract.IView {
    override fun createPresenter(): ContactsPresenter? {
        return ContactsPresenter(this)
    }

    private val datas = ArrayList<CityBean>()
    private val adapter = CityAdpater()
    private val suspensionDecoration: SuspensionDecoration by lazy {
        SuspensionDecoration(
            context,
            datas
        )
    }

    override fun setContextView(): Int {
        return R.layout.fragment_contacts
    }

    override fun initView() {
        super.initView()

        contacts_smartRefreshLayout.setRefreshHeader(ClassicsHeader(context))
        contacts_smartRefreshLayout.setRefreshFooter(ClassicsFooter(context))
        val linearLayoutManager = LinearLayoutManager(context)
        contacts_recyclerView.layoutManager = linearLayoutManager
        contacts_recyclerView.addItemDecoration(suspensionDecoration)
        contacts_recyclerView.adapter = adapter
        //indexbar初始化
        contacts_indexBar.setmPressedShowTextView(contacts_tv_hint)//设置HintTextView
            .setNeedRealIndex(false)//设置需要真实的索引
            .setmLayoutManager(linearLayoutManager)//设置RecyclerView的LayoutManager
        contacts_recyclerView.addOnItemTouchListener(object : OnItemChildClickListener() {
            override fun onSimpleItemChildClick(
                adapter: BaseQuickAdapter<*, *>?,
                view: View?,
                position: Int
            ) {
                Logger.d("${view?.id}   $position")
                mPresenter?.setUserName("")
            }

        })
    }

    override fun initData() {
        super.initData()
        initCity()

        contacts_indexBar.setmSourceDatas(datas).invalidate()
        adapter.setNewData(datas)

        suspensionDecoration.setmDatas(datas)
    }

    fun initCity() {
        val citys = context!!.resources.getStringArray(R.array.citys)
        citys.forEach {
            datas.add(CityBean(it))
        }
    }

    override fun initEvent() {
        super.initEvent()
        contacts_smartRefreshLayout.setOnRefreshLoadMoreListener(object :
            OnRefreshLoadMoreListener {
            override fun onLoadMore(refreshLayout: RefreshLayout) {
                refreshLayout.finishLoadMore()
            }

            override fun onRefresh(refreshLayout: RefreshLayout) {
                refreshLayout.finishRefresh()
            }

        })
    }
}