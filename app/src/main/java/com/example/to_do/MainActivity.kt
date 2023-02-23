package com.example.to_do

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.to_do.databinding.ActivityMainBinding
import com.example.to_do.home.addtask.AddTaskBottomSheet
import com.example.to_do.home.addtask.OnDismissLisenter
import com.example.to_do.home.list.ListFragment
import com.example.to_do.home.settings.SettingsFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewBinding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(viewBinding.root)

        viewBinding.addTask.setOnClickListener {
            showaddTasksheet()

        }
        viewBinding.bottomNav.setOnItemSelectedListener {it->

            when (it.itemId) {
                R.id.list -> {
                    viewBinding.screenTitle.setText(R.string.title_tasks_list)
                    showFragment(ListFragment())
                }
                R.id.setting -> {
                    viewBinding.screenTitle.setText(R.string.title_settings)
                    showFragment(SettingsFragment())
                }
            }
        return@setOnItemSelectedListener true
        }
        viewBinding.bottomNav.selectedItemId=R.id.list

    }
    var listFragment=ListFragment()

    private fun showaddTasksheet() {
        val addTaskBottomSheet = AddTaskBottomSheet() ;
       addTaskBottomSheet.onDismissLisenter = OnDismissLisenter {
           listFragment.loadTasksByDaylist()
       }
        addTaskBottomSheet.show(supportFragmentManager, null)
    }


    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.layoutContineer, fragment)
            .commit()

    }
}