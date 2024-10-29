package com.example.bai1_taoformnhapthongtin

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etMSSV = findViewById<EditText>(R.id.etMSSV)
        val etFullName = findViewById<EditText>(R.id.etFullName)
        val rgGender = findViewById<RadioGroup>(R.id.rgGender)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPhone = findViewById<EditText>(R.id.etPhone)
        val btnShowCalendar = findViewById<Button>(R.id.btnShowCalendar)
        val tvSelectedDate = findViewById<TextView>(R.id.tvSelectedDate)
        val calendarView = findViewById<CalendarView>(R.id.calendarView)
        val spProvince = findViewById<Spinner>(R.id.spProvince)
        val spDistrict = findViewById<Spinner>(R.id.spDistrict)
        val spWard = findViewById<Spinner>(R.id.spWard)
        val cbSports = findViewById<CheckBox>(R.id.cbSports)
        val cbMovies = findViewById<CheckBox>(R.id.cbMovies)
        val cbMusic = findViewById<CheckBox>(R.id.cbMusic)
        val cbAgreeTerms = findViewById<CheckBox>(R.id.cbAgreeTerms)
        val btnSubmit = findViewById<Button>(R.id.btnSubmit)

        // Ẩn/hiện CalendarView
        btnShowCalendar.setOnClickListener {
            calendarView.visibility = if (calendarView.visibility == View.GONE) View.VISIBLE else View.GONE
        }

        // Thiết lập sự kiện chọn ngày cho CalendarView
        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            // Định dạng ngày đã chọn
            val selectedDate = "$dayOfMonth/${month + 1}/$year"
            tvSelectedDate.text = "Ngày sinh: $selectedDate"
            calendarView.visibility = View.GONE // Ẩn CalendarView sau khi chọn ngày
        }

        // Xử lý khi nhấn Submit
        btnSubmit.setOnClickListener {
            val mssv = etMSSV.text.toString()
            val fullName = etFullName.text.toString()
            val email = etEmail.text.toString()
            val phone = etPhone.text.toString()
            val gender = when (rgGender.checkedRadioButtonId) {
                R.id.rbMale -> "Nam"
                R.id.rbFemale -> "Nữ"
                else -> ""
            }

            // Kiểm tra các trường bắt buộc
            if (mssv.isEmpty() || fullName.isEmpty() || email.isEmpty() || phone.isEmpty() || gender.isEmpty() || !cbAgreeTerms.isChecked) {
                Toast.makeText(this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show()
            }
        }

        // Sử dụng AddressHelper để lấy dữ liệu và thiết lập spinner
        val addressHelper = AddressHelper(resources)
        val provinces = addressHelper.getProvinces()
        spProvince.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, provinces)

        spProvince.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val selectedProvince = provinces[position]
                val districts = addressHelper.getDistricts(selectedProvince)
                spDistrict.adapter = ArrayAdapter(this@MainActivity, android.R.layout.simple_spinner_item, districts)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        spDistrict.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val selectedProvince = provinces[spProvince.selectedItemPosition]
                val selectedDistrict = spDistrict.selectedItem.toString()
                val wards = addressHelper.getWards(selectedProvince, selectedDistrict)
                spWard.adapter = ArrayAdapter(this@MainActivity, android.R.layout.simple_spinner_item, wards)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }
}
