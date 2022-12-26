package com.example.audiorecorder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore.Audio
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.audiorecorder.databinding.ActivityGalleryBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class GalleryActivity : AppCompatActivity(), OnItemClickListener {

    private lateinit var binding: ActivityGalleryBinding

    private lateinit var records : ArrayList<AudioRecord>
    private lateinit var mAdapter : Adapter
    private lateinit var db : AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGalleryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        records = ArrayList()

        db = Room.databaseBuilder(
            this,
            AppDatabase::class.java,
            "audioRecords"
        ).build()

        mAdapter = Adapter(records, this)

        binding.recyclerview.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(context)

        }
        fetchAll()
    }


    private fun fetchAll(){
        GlobalScope.launch {
            records.clear()
            val queryResult = db.audioRecordDao().getAll()
            records.addAll(queryResult)

            mAdapter.notifyDataSetChanged()
        }
    }

    override fun onItemClickListener(position: Int) {
        Toast.makeText(this, "Simple Click", Toast.LENGTH_SHORT).show()
    }

    override fun onItemLongClickListener(position: Int) {
        Toast.makeText(this, "Long Click", Toast.LENGTH_SHORT).show()
    }

}