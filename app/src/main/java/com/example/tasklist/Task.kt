package com.example.tasklist

import java.time.LocalTime

data class Task(val id: Int, val name: String, val content: String, val time: LocalTime) {
}