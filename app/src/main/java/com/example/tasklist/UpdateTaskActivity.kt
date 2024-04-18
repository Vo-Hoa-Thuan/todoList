package com.example.tasklist

import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tasklist.databinding.ActivityUpdateTaskBinding
import java.time.LocalTime

class UpdateTaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateTaskBinding
    private lateinit var db: TaskDatabaseHelper
    private var selectedTime: LocalTime? = null // Biến để lưu giá trị thời gian đã chọn
    @get:JvmName("getTaskIdField")

    private var taskId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = TaskDatabaseHelper(this)

        taskId = intent.getIntExtra("task_id", -1)
        if (taskId == -1) {
            finish()
            return
        }

        val task = db.getTaskById(taskId)
        binding.updateNameTask.setText(task.name)
        binding.updateContentTask.setText(task.content)


        // Xử lý khi người dùng nhấn nút "Set Time"
        binding.updateSetTimeButton.setOnClickListener {
            showTimePickerDialog()
        }

        // Xử lý khi người dùng nhấn nút "Save"
        binding.updateSaveButton.setOnClickListener {
            val newName = binding.updateNameTask.text.toString()
            val newContent = binding.updateContentTask.text.toString()

            // Kiểm tra xem người dùng đã chọn thời gian chưa
            if (selectedTime != null) {
                val updatedTask = Task(taskId, newName, newContent, selectedTime!!)
                db.updateTask(updatedTask)
                finish()
                Toast.makeText(this, "Changes Saved", Toast.LENGTH_SHORT).show()
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
