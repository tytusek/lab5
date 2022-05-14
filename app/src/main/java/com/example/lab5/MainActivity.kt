package com.example.lab5

import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lab5.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.layoutManager = LinearLayoutManager(this);

        val dbHelper = DBHelper(this, null)
        val studentDao = StudentDao(dbHelper)

        var data = studentDao.findStudents()
        val adapter = CustomAdapter(data, studentDao)
        binding.recyclerView.adapter = adapter;

        binding.addButton.setOnClickListener{
            data.add(Student(null, binding.newStudent.text.toString()))
            studentDao.addStudent(Student(null, binding.newStudent.text.toString()))
            adapter.notifyDataSetChanged()
        }
    }

}
data class Student(val id: Int?, val firstName: String){

}

class CustomAdapter(var mList: MutableList<Student>, var studentDao: StudentDao)
    : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_item, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mList.size;
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = mList[position].firstName

        holder.editButton.setOnClickListener {
            holder.editButton.visibility = View.GONE
            holder.editText.visibility = View.VISIBLE
            holder.saveButton.visibility = View.VISIBLE
        }

        holder.saveButton.setOnClickListener {
            holder.editButton.visibility = View.VISIBLE
            holder.editText.visibility = View.GONE
            holder.saveButton.visibility = View.GONE
            val newText = holder.editText.text.toString()
            mList[position] = Student(mList[position].id, newText)
            studentDao.updateStudent(Student(mList[position].id, newText))
            notifyDataSetChanged()
        }

    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView
        val editButton: Button
        val editText: EditText
        val saveButton: Button

        init {
            textView = view.findViewById(R.id.textView)
            editButton = view.findViewById(R.id.buttonEdit)
            editText = view.findViewById(R.id.editText)
            saveButton = view.findViewById(R.id.saveButton)
        }
    }
}