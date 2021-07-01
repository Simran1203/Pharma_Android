package com.pharmacy.crack.main.view.mainActivities

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import com.pharmacy.crack.R
import com.pharmacy.crack.main.adapter.DrugStoreAdapter
import com.pharmacy.crack.utils.setFullScreen
import kotlinx.android.synthetic.main.activity_drug_store2.*
import kotlinx.android.synthetic.main.toolbar_multicolor.*

class DrugStore2Activity : AppCompatActivity(), View.OnClickListener {
    var listTitl : ArrayList<String> = ArrayList()
     lateinit var adapter: DrugStoreAdapter
    
    @RequiresApi(Build.VERSION_CODES.O_MR1)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullScreen(this)
        setContentView(R.layout.activity_drug_store2)


        initAll()
    }

    private fun initAll() {
        txtToolLime.text = "Drug"
        txtToolWlidStraw.text = "Store"

        listTitl = ArrayList()
        listTitl.add("1 Life")
        listTitl.add("Unlock\nAll\nCategories")
        listTitl.add("1000 Pills")
        listTitl.add("Pro Version\n& Pill Pack")
        listTitl.add("2500 Pills")
        listTitl.add("7500 Pills")
        listTitl.add("1500\npills/Day\nX 90 Days")
        listTitl.add("10000\npills/Day\nX 180 Days")
        listTitl.add("Pro Version\n10000\nPills/Day\nX 180 Days")

        adapter = DrugStoreAdapter(this,listTitl){
                position ->onItemClick(position)
        }
        rvDrugstore.adapter = adapter
    }

    private fun onItemClick(position: Int) {

    }

    override fun onClick(v: View?) {

    }
}