package com.example.lab5

import com.example.lab5.DBHelper.Companion.ID_COL
import com.example.lab5.DBHelper.Companion.NAME_COl

class StudentDao(private val dbHelper: DBHelper) {

    fun addStudent(newStudent: Student){
        dbHelper.addName(newStudent.firstName)
    }

    fun findStudents(): ArrayList<Student> {
        val students = ArrayList<Student>()
        with(dbHelper.selectAll()) {
            while (moveToNext()) {
                students.add(Student(
                    getInt(getColumnIndexOrThrow(ID_COL)),
                    getString(getColumnIndexOrThrow(NAME_COl))
                ))
            }
        }
        dbHelper.selectAll().close()
        return students
    }

    fun updateStudent(student: Student){
        student.id?.let { dbHelper.updateCourse(it, student.firstName) }
    }
}