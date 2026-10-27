package com.example.cutelauncher

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rvApps = findViewById<RecyclerView>(R.id.rvApps)
        rvApps.layoutManager = GridLayoutManager(this, 3) // Hiển thị 3 cột icon

        val apps = loadInstalledApps()
        rvApps.adapter = AppAdapter(apps)
    }

    private fun loadInstalledApps(): List<AppModel> {
        val appsList = mutableListOf<AppModel>()
        val pm = packageManager
        val mainIntent = Intent(Intent.ACTION_MAIN, null).apply {
            addCategory(Intent.CATEGORY_LAUNCHER)
        }

        val resolvedInfos = pm.queryIntentActivities(mainIntent, 0)
        for (info in resolvedInfos) {
            val appName = info.loadLabel(pm).toString()
            val packageName = info.activityInfo.packageName
            val icon = info.loadIcon(pm)

            // Lọc không hiện chính launcher này trong danh sách
            if (packageName != packageName) {
                appsList.add(AppModel(appName, packageName, icon))
            }
        }
        return appsList.sortedBy { it.label.lowercase() }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        // Vô hiệu hóa nút Back để ứng dụng giữ vai trò làm Home Screen
    }
}
