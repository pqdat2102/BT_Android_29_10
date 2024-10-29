package com.example.bai3_timkiemtrongdanhsach

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.EditText

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: StudentAdapter
    private lateinit var edtSearch: EditText

    // Dữ liệu danh sách sinh viên
    private val studentList = generateStudents()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Khởi tạo RecyclerView và Adapter
        recyclerView = findViewById(R.id.recyclerView)
        edtSearch = findViewById(R.id.edtSearch)

        adapter = StudentAdapter(studentList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        // Lắng nghe sự thay đổi trong ô tìm kiếm
        edtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val keyword = s.toString()
                if (keyword.length > 2) {
                    filterStudents(keyword)
                } else {
                    adapter.updateList(studentList) // Hiển thị toàn bộ danh sách nếu từ khóa < 3 ký tự
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    // Hàm tìm kiếm sinh viên theo từ khóa
    private fun filterStudents(keyword: String) {
        val filteredList = studentList.filter {
            it.name.contains(keyword, ignoreCase = true) ||
                    it.mssv.contains(keyword, ignoreCase = true)
        }
        adapter.updateList(filteredList)
    }


    private fun generateStudents(): List<Student> {
        val names = listOf(
            "Nguyen Van A", "Tran Thi B", "Le Van C", "Pham Thi D",
            "Nguyen Minh E", "Le Thi F", "Hoang Van G", "Tran Van H",
            "Do Thi I", "Ngo Minh J", "Bui Van K", "Pham Van L",
            "Le Thi M", "Nguyen Van N", "Ho Van O", "Tran Van P",
            "Nguyen Thi Q", "Pham Minh R", "Bui Thi S", "Do Van T",
            "Nguyen Van U", "Hoang Minh V", "Tran Van W", "Nguyen Thi X",
            "Bui Van Y", "Pham Van Z", "Ngo Van AA", "Le Minh BB",
            "Nguyen Van CC", "Hoang Thi DD"
        )

        return names.map { name ->
            val mssv = (20210000..20219999).random().toString()
            Student(name, mssv)
        }
    }
}
