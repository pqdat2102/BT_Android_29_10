package com.example.bai2_danhsachdongian

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.sqrt

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Khai báo các thành phần giao diện
        val edtNumber: EditText = findViewById(R.id.edtNumber)
        val radioEven: RadioButton = findViewById(R.id.radioEven)
        val radioOdd: RadioButton = findViewById(R.id.radioOdd)
        val radioSquare: RadioButton = findViewById(R.id.radioSquare)
        val btnShow: Button = findViewById(R.id.btnShow)
        val listView: ListView = findViewById(R.id.listView)
        val tvError: TextView = findViewById(R.id.tvError)

        // Xử lý sự kiện khi nhấn nút Show
        btnShow.setOnClickListener {
            val input = edtNumber.text.toString()

            // Kiểm tra dữ liệu nhập vào
            if (input.isEmpty() || input.toIntOrNull() == null || input.toInt() < 0) {
                tvError.text = "Có lỗi xảy ra, vui lòng nhập số nguyên dương hợp lệ"
                tvError.visibility = TextView.VISIBLE
                listView.adapter = null
                return@setOnClickListener
            }

            // thông báo lỗi
            tvError.visibility = TextView.GONE
            val n = input.toInt()
            val resultList = mutableListOf<Int>()

            // xử lý logic theo từng loại số
            when {
                radioEven.isChecked -> {
                    resultList.addAll((0..n).filter { it % 2 == 0 })
                }
                radioOdd.isChecked -> {
                    resultList.addAll((1..n).filter { it % 2 != 0 })
                }
                radioSquare.isChecked -> {
                    resultList.addAll((0..n).filter { isSquareNumber(it) })
                }
            }

            // hiển thị kết quả
            val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, resultList)
            listView.adapter = adapter
        }
    }

    private fun isSquareNumber(num: Int): Boolean {
        val sqrtValue = sqrt(num.toDouble()).toInt()
        return sqrtValue * sqrtValue == num
    }
}
