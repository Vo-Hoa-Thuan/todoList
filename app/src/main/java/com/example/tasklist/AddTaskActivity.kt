package com.example.tasklist

import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tasklist.databinding.ActivityAddTaskBinding
import java.time.LocalTime

class AddTaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddTaskBinding
    private lateinit var db: TaskDatabaseHelper
    private var selectedTime: LocalTime? = null // Biến để lưu giá trị thời gian đã chọn

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = TaskDatabaseHelper(this)

        // Xử lý khi người dùng nhấn nút "Set Time"
        binding.setTimeButton.setOnClickListener {
            showTimePickerDialog()
        }

        // Xử lý khi người dùng nhấn nút "Save"
        binding.saveButton.setOnClickListener {
            val name = binding.nameTask.text.toString()
            val content = binding.contentTask.text.toString()

            // Kiểm tra xem người dùng đã chọn thời gian chưa
            if (selectedTime != null) {
                val task = Task(0, name, content, selectedTime!!)
                db.insertTask(task)
                finish()
                Toast.makeText(this, "TASK SAVED", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Please set a time", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showTimePickerDialog() {
        val currentTime = LocalTime.now()
        val hour = currentTime.hour
        val minute = currentTime.minute

        val timePickerDialog = TimePickerDialog(
            this,
            { _, hourOfDay, minute ->
                // Lưu giá trị thời gian đã chọn
                selectedTime = LocalTime.of(hourOfDay, minute)
            },
            hour, minute, true
        )
        timePickerDialog.show()
    }
}
