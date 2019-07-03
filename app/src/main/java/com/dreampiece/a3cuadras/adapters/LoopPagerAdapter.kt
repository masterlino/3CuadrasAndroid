package com.dreampiece.a3cuadras.adapters

import android.database.DataSetObserver
import android.os.Parcelable
import android.support.v4.view.PagerAdapter
import android.view.View
import android.view.ViewGroup


class LoopPagerAdapter(private val adapter: PagerAdapter) : PagerAdapter() {

    /**
     * @return the [.getCount] result of the wrapped adapter
     */
    val realCount: Int
        get() = adapter.count

    /*
     * End delegation
     */

    val infiniteCenteredItem: Int
        get() = count / 2 - count / 2 % realCount

    override fun getCount(): Int {
        return if (realCount == 0) {
            0
        } else Integer.MAX_VALUE
        // warning: scrolling to very high values (1,000,000+) results in
        // strange drawing behaviour
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val virtualPosition = position % realCount

        // only expose virtual position to the inner adapter
        return adapter.instantiateItem(container, virtualPosition)
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val virtualPosition = position % realCount

        // only expose virtual position to the inner adapter
        adapter.destroyItem(container, virtualPosition, `object`)
    }

    /*
     * Delegate rest of methods directly to the inner adapter.
     */

    override fun finishUpdate(container: ViewGroup) {
        adapter.finishUpdate(container)
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return adapter.isViewFromObject(view, `object`)
    }

    override fun restoreState(bundle: Parcelable?, classLoader: ClassLoader?) {
        adapter.restoreState(bundle, classLoader)
    }

    override fun saveState(): Parcelable? {
        return adapter.saveState()
    }

    override fun startUpdate(container: ViewGroup) {
        adapter.startUpdate(container)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        val virtualPosition = position % realCount
        return adapter.getPageTitle(virtualPosition)
    }

    override fun getPageWidth(position: Int): Float {
        return adapter.getPageWidth(position)
    }

    override fun setPrimaryItem(container: ViewGroup, position: Int, `object`: Any) {
        adapter.setPrimaryItem(container, position, `object`)
    }

    override fun unregisterDataSetObserver(observer: DataSetObserver) {
        adapter.unregisterDataSetObserver(observer)
    }

    override fun registerDataSetObserver(observer: DataSetObserver) {
        adapter.registerDataSetObserver(observer)
    }

    override fun notifyDataSetChanged() {
        adapter.notifyDataSetChanged()
    }

    override fun getItemPosition(`object`: Any): Int {
        return adapter.getItemPosition(`object`)
    }

    companion object {

        private val TAG = "InfinitePagerAdapter"
        private val DEBUG = true
    }

}
