package com.easyprog.android.nerdlauncher.adapter

import android.content.Intent
import android.content.pm.ResolveInfo
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.easyprog.android.nerdlauncher.R

class ActivityAdapter(val activities: List<ResolveInfo>) : RecyclerView.Adapter<ActivityAdapter.ActivityHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivityHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_activity, parent, false)
        return ActivityHolder(view)
    }

    override fun onBindViewHolder(holder: ActivityHolder, position: Int) {
        val resolveInfo = activities[position]
        holder.bindActivity(resolveInfo)
    }

    override fun getItemCount(): Int = activities.size

    class ActivityHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private lateinit var nameActivityTextView: TextView
        private lateinit var iconActivityImageView: ImageView
        private lateinit var resolveInfo: ResolveInfo

        init {
            itemView.setOnClickListener(this)
        }

        fun bindActivity(resolveInfo: ResolveInfo) {
            nameActivityTextView = itemView.findViewById(R.id.text_name_activity)
            iconActivityImageView = itemView.findViewById(R.id.image_icon_activity)

            this.resolveInfo = resolveInfo
            val packageManager = itemView.context.packageManager
            val appName = resolveInfo.loadLabel(packageManager).toString()
            val appIcon = resolveInfo.loadIcon(packageManager)
            nameActivityTextView.text = appName
            iconActivityImageView.setImageDrawable(appIcon)
        }

        override fun onClick(v: View) {
            val activityInfo = resolveInfo.activityInfo
            val intent = Intent(Intent.ACTION_MAIN).apply {
                setClassName(activityInfo.applicationInfo.packageName, activityInfo.name)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            val context = v.context
            context.startActivity(intent)
        }
    }
}