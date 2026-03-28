package com.example.havenhub.database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;


// 建表 SQL 语句

public class SQlite extends SQLiteOpenHelper {
    private SQLiteDatabase db;
    public SQlite(Context context){
        super(context,"db_SuSemaner",null,1);
        db=getReadableDatabase();

    }

    @Override
    public void onCreate(SQLiteDatabase db){
        // 创建用户表
        db.execSQL("CREATE TABLE users(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "username TEXT NOT NULL UNIQUE, " + // 用户名唯一
                "password TEXT NOT NULL, " +
                "name TEXT)");
        
        // 创建学生表
        db.execSQL("CREATE TABLE students(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "student_id TEXT NOT NULL UNIQUE, " + // 学号唯一
                "name TEXT NOT NULL, " +
                "gender TEXT, " +
                "age INTEGER, " +
                "department TEXT, " +
                "major TEXT, " +
                "dormitory TEXT, " +
                "phone TEXT, " +
                "email TEXT)");
        
        // 创建宿舍表
        db.execSQL("CREATE TABLE dormitories(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "dormitory_id TEXT NOT NULL UNIQUE, " + // 宿舍号唯一
                "building TEXT NOT NULL, " +
                "floor INTEGER, " +
                "room_number TEXT, " +
                "bed_count INTEGER, " +
                "occupied_count INTEGER, " +
                "status TEXT, " + // 状态：空闲、已分配、维修中
                "description TEXT)");
        
        // 创建考勤表
        db.execSQL("CREATE TABLE attendance(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "student_id TEXT NOT NULL, " +
                "date TEXT NOT NULL, " + // 考勤日期
                "status TEXT NOT NULL, " + // 状态：出勤、缺勤、迟到、早退
                "remark TEXT, " +
                "FOREIGN KEY(student_id) REFERENCES students(student_id))");
        
        // 创建维修表
        db.execSQL("CREATE TABLE repair(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "student_id TEXT NOT NULL, " +
                "dormitory_id TEXT NOT NULL, " +
                "date TEXT NOT NULL, " + // 申请日期
                "type TEXT NOT NULL, " + // 维修类型
                "description TEXT NOT NULL, " + // 问题描述
                "status TEXT NOT NULL, " + // 状态：待处理、处理中、已完成
                "handler TEXT, " + // 处理人
                "handle_date TEXT, " + // 处理日期
                "remark TEXT, " + // 处理备注
                "FOREIGN KEY(student_id) REFERENCES students(student_id), " +
                "FOREIGN KEY(dormitory_id) REFERENCES dormitories(dormitory_id))");

        // 创建访客表
        db.execSQL("CREATE TABLE visitor(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL, " + // 访客姓名
                "id_card TEXT NOT NULL, " + // 身份证号
                "phone TEXT NOT NULL, " + // 联系电话
                "visit_date TEXT NOT NULL, " + // 访问日期
                "visit_time TEXT NOT NULL, " + // 访问时间
                "visit_reason TEXT NOT NULL, " + // 访问原因
                "room_number TEXT NOT NULL, " + // 访问宿舍号
                "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS students");
        db.execSQL("DROP TABLE IF EXISTS dormitories");
        db.execSQL("DROP TABLE IF EXISTS attendance");
        db.execSQL("DROP TABLE IF EXISTS repair");
        db.execSQL("DROP TABLE IF EXISTS visitor");
        onCreate(db);
    }
    public void add(String username,String password,String name){
        db.execSQL("INSERT INTO users(username,password,name)VALUES(?,?,?)",new Object[]{username,password,name});

    }

    public Cursor getUserByUsername(String username){
        String[]clumns={"id","username","password","name"};
        String selection="username=?";
        String[]selectionArgs={username};
        Cursor cursor=db.query("users",clumns,selection,selectionArgs,null,null,null);
        return cursor;
    }

    public void updatePassword(String username,String newPassword){
        db.execSQL("UPDATE users SET password=? WHERE username=?",new Object[]{newPassword,username});
    }
    public void updateName(String username,String newnName){
        db.execSQL("UPDATE users SET name=? WHERE username=?",new Object[]{newnName,username});
    }

    public ArrayList<User> getAllDATA(){
        ArrayList<User> list = new ArrayList<User>();
        Cursor cursor = db.query("users",null,null,null,null,null,null);
        while(cursor.moveToNext()){
            @SuppressLint("Range") String username = cursor.getString(cursor.getColumnIndex("username"));
            @SuppressLint("Range") String password = cursor.getString(cursor.getColumnIndex("password"));
            @SuppressLint("Range") String name=cursor.getString(cursor.getColumnIndex("name"));
            User user=new User(username,password);
            user.setName(name);
            list.add(user);
        }
        cursor.close();
        return list;
    }
    
    // 学生相关操作
    public void addStudent(String studentId, String name, String gender, int age, String department, String major, String dormitory, String phone, String email){
        db.execSQL("INSERT INTO students(student_id, name, gender, age, department, major, dormitory, phone, email) VALUES(?,?,?,?,?,?,?,?,?)",
                new Object[]{studentId, name, gender, age, department, major, dormitory, phone, email});
    }
    
    public void updateStudent(String studentId, String name, String gender, int age, String department, String major, String dormitory, String phone, String email){
        db.execSQL("UPDATE students SET name=?, gender=?, age=?, department=?, major=?, dormitory=?, phone=?, email=? WHERE student_id=?",
                new Object[]{name, gender, age, department, major, dormitory, phone, email, studentId});
    }
    
    public void deleteStudent(String studentId){
        db.execSQL("DELETE FROM students WHERE student_id=?", new Object[]{studentId});
    }
    
    public Cursor getStudentByStudentId(String studentId){
        String[] columns = {"id", "student_id", "name", "gender", "age", "department", "major", "dormitory", "phone", "email"};
        String selection = "student_id=?";
        String[] selectionArgs = {studentId};
        return db.query("students", columns, selection, selectionArgs, null, null, null);
    }
    
    public ArrayList<Student> getAllStudents(){
        ArrayList<Student> list = new ArrayList<Student>();
        Cursor cursor = db.query("students", null, null, null, null, null, null);
        while(cursor.moveToNext()){
            @SuppressLint("Range") String studentId = cursor.getString(cursor.getColumnIndex("student_id"));
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
            @SuppressLint("Range") String gender = cursor.getString(cursor.getColumnIndex("gender"));
            @SuppressLint("Range") int age = cursor.getInt(cursor.getColumnIndex("age"));
            @SuppressLint("Range") String department = cursor.getString(cursor.getColumnIndex("department"));
            @SuppressLint("Range") String major = cursor.getString(cursor.getColumnIndex("major"));
            @SuppressLint("Range") String dormitory = cursor.getString(cursor.getColumnIndex("dormitory"));
            @SuppressLint("Range") String phone = cursor.getString(cursor.getColumnIndex("phone"));
            @SuppressLint("Range") String email = cursor.getString(cursor.getColumnIndex("email"));
            Student student = new Student(studentId, name, gender, age, department, major, dormitory, phone, email);
            list.add(student);
        }
        cursor.close();
        return list;
    }
    
    public void close() {
        if (db != null && db.isOpen()) {
            db.close();
        }
    }
    
    // 宿舍相关操作
    public void addDormitory(String dormitoryId, String building, int floor, String roomNumber, int bedCount, int occupiedCount, String status, String description){
        db.execSQL("INSERT INTO dormitories(dormitory_id, building, floor, room_number, bed_count, occupied_count, status, description) VALUES(?,?,?,?,?,?,?,?)",
                new Object[]{dormitoryId, building, floor, roomNumber, bedCount, occupiedCount, status, description});
    }
    
    public void updateDormitory(String dormitoryId, String building, int floor, String roomNumber, int bedCount, int occupiedCount, String status, String description){
        db.execSQL("UPDATE dormitories SET building=?, floor=?, room_number=?, bed_count=?, occupied_count=?, status=?, description=? WHERE dormitory_id=?",
                new Object[]{building, floor, roomNumber, bedCount, occupiedCount, status, description, dormitoryId});
    }
    
    public void deleteDormitory(String dormitoryId){
        db.execSQL("DELETE FROM dormitories WHERE dormitory_id=?", new Object[]{dormitoryId});
    }
    
    public Cursor getDormitoryByDormitoryId(String dormitoryId){
        String[] columns = {"id", "dormitory_id", "building", "floor", "room_number", "bed_count", "occupied_count", "status", "description"};
        String selection = "dormitory_id=?";
        String[] selectionArgs = {dormitoryId};
        return db.query("dormitories", columns, selection, selectionArgs, null, null, null);
    }
    
    public ArrayList<Dormitory> getAllDormitories(){
        ArrayList<Dormitory> list = new ArrayList<Dormitory>();
        Cursor cursor = db.query("dormitories", null, null, null, null, null, null);
        while(cursor.moveToNext()){
            @SuppressLint("Range") String dormitoryId = cursor.getString(cursor.getColumnIndex("dormitory_id"));
            @SuppressLint("Range") String building = cursor.getString(cursor.getColumnIndex("building"));
            @SuppressLint("Range") int floor = cursor.getInt(cursor.getColumnIndex("floor"));
            @SuppressLint("Range") String roomNumber = cursor.getString(cursor.getColumnIndex("room_number"));
            @SuppressLint("Range") int bedCount = cursor.getInt(cursor.getColumnIndex("bed_count"));
            @SuppressLint("Range") int occupiedCount = cursor.getInt(cursor.getColumnIndex("occupied_count"));
            @SuppressLint("Range") String status = cursor.getString(cursor.getColumnIndex("status"));
            @SuppressLint("Range") String description = cursor.getString(cursor.getColumnIndex("description"));
            Dormitory dormitory = new Dormitory(dormitoryId, building, floor, roomNumber, bedCount, occupiedCount, status, description);
            list.add(dormitory);
        }
        cursor.close();
        return list;
    }
    
    // 考勤相关操作
    public void addAttendance(String studentId, String date, String status, String remark){
        db.execSQL("INSERT INTO attendance(student_id, date, status, remark) VALUES(?,?,?,?)",
                new Object[]{studentId, date, status, remark});
    }
    
    public void updateAttendance(int id, String status, String remark){
        db.execSQL("UPDATE attendance SET status=?, remark=? WHERE id=?",
                new Object[]{status, remark, id});
    }
    
    public void deleteAttendance(int id){
        db.execSQL("DELETE FROM attendance WHERE id=?", new Object[]{id});
    }
    
    public ArrayList<Attendance> getAllAttendance(){
        ArrayList<Attendance> list = new ArrayList<Attendance>();
        Cursor cursor = db.query("attendance", null, null, null, null, null, "date DESC");
        while(cursor.moveToNext()){
            @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
            @SuppressLint("Range") String studentId = cursor.getString(cursor.getColumnIndex("student_id"));
            @SuppressLint("Range") String date = cursor.getString(cursor.getColumnIndex("date"));
            @SuppressLint("Range") String status = cursor.getString(cursor.getColumnIndex("status"));
            @SuppressLint("Range") String remark = cursor.getString(cursor.getColumnIndex("remark"));
            Attendance attendance = new Attendance(id, studentId, date, status, remark);
            list.add(attendance);
        }
        cursor.close();
        return list;
    }
    
    public ArrayList<Attendance> getAttendanceByStudentId(String studentId){
        ArrayList<Attendance> list = new ArrayList<Attendance>();
        String[] selectionArgs = {studentId};
        Cursor cursor = db.query("attendance", null, "student_id=?", selectionArgs, null, null, "date DESC");
        while(cursor.moveToNext()){
            @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
            @SuppressLint("Range") String date = cursor.getString(cursor.getColumnIndex("date"));
            @SuppressLint("Range") String status = cursor.getString(cursor.getColumnIndex("status"));
            @SuppressLint("Range") String remark = cursor.getString(cursor.getColumnIndex("remark"));
            Attendance attendance = new Attendance(id, studentId, date, status, remark);
            list.add(attendance);
        }
        cursor.close();
        return list;
    }
    
    public ArrayList<Attendance> getAttendanceByDate(String date){
        ArrayList<Attendance> list = new ArrayList<Attendance>();
        String[] selectionArgs = {date};
        Cursor cursor = db.query("attendance", null, "date=?", selectionArgs, null, null, null);
        while(cursor.moveToNext()){
            @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
            @SuppressLint("Range") String studentId = cursor.getString(cursor.getColumnIndex("student_id"));
            @SuppressLint("Range") String status = cursor.getString(cursor.getColumnIndex("status"));
            @SuppressLint("Range") String remark = cursor.getString(cursor.getColumnIndex("remark"));
            Attendance attendance = new Attendance(id, studentId, date, status, remark);
            list.add(attendance);
        }
        cursor.close();
        return list;
    }
    
    // 维修相关操作
    public void addRepair(String studentId, String dormitoryId, String date, String type, String description, String status){
        db.execSQL("INSERT INTO repair(student_id, dormitory_id, date, type, description, status) VALUES(?,?,?,?,?,?)",
                new Object[]{studentId, dormitoryId, date, type, description, status});
    }
    
    public void updateRepair(int id, String status, String handler, String handleDate, String remark){
        db.execSQL("UPDATE repair SET status=?, handler=?, handle_date=?, remark=? WHERE id=?",
                new Object[]{status, handler, handleDate, remark, id});
    }
    
    public void deleteRepair(int id){
        db.execSQL("DELETE FROM repair WHERE id=?", new Object[]{id});
    }
    
    public ArrayList<Repair> getAllRepair(){
        ArrayList<Repair> list = new ArrayList<Repair>();
        Cursor cursor = db.query("repair", null, null, null, null, null, "date DESC");
        while(cursor.moveToNext()){
            @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
            @SuppressLint("Range") String studentId = cursor.getString(cursor.getColumnIndex("student_id"));
            @SuppressLint("Range") String dormitoryId = cursor.getString(cursor.getColumnIndex("dormitory_id"));
            @SuppressLint("Range") String date = cursor.getString(cursor.getColumnIndex("date"));
            @SuppressLint("Range") String type = cursor.getString(cursor.getColumnIndex("type"));
            @SuppressLint("Range") String description = cursor.getString(cursor.getColumnIndex("description"));
            @SuppressLint("Range") String status = cursor.getString(cursor.getColumnIndex("status"));
            @SuppressLint("Range") String handler = cursor.getString(cursor.getColumnIndex("handler"));
            @SuppressLint("Range") String handleDate = cursor.getString(cursor.getColumnIndex("handle_date"));
            @SuppressLint("Range") String remark = cursor.getString(cursor.getColumnIndex("remark"));
            Repair repair = new Repair(id, studentId, dormitoryId, date, type, description, status, handler, handleDate, remark);
            list.add(repair);
        }
        cursor.close();
        return list;
    }
    
    public ArrayList<Repair> getRepairByStudentId(String studentId){
        ArrayList<Repair> list = new ArrayList<Repair>();
        String[] selectionArgs = {studentId};
        Cursor cursor = db.query("repair", null, "student_id=?", selectionArgs, null, null, "date DESC");
        while(cursor.moveToNext()){
            @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
            @SuppressLint("Range") String dormitoryId = cursor.getString(cursor.getColumnIndex("dormitory_id"));
            @SuppressLint("Range") String date = cursor.getString(cursor.getColumnIndex("date"));
            @SuppressLint("Range") String type = cursor.getString(cursor.getColumnIndex("type"));
            @SuppressLint("Range") String description = cursor.getString(cursor.getColumnIndex("description"));
            @SuppressLint("Range") String status = cursor.getString(cursor.getColumnIndex("status"));
            @SuppressLint("Range") String handler = cursor.getString(cursor.getColumnIndex("handler"));
            @SuppressLint("Range") String handleDate = cursor.getString(cursor.getColumnIndex("handle_date"));
            @SuppressLint("Range") String remark = cursor.getString(cursor.getColumnIndex("remark"));
            Repair repair = new Repair(id, studentId, dormitoryId, date, type, description, status, handler, handleDate, remark);
            list.add(repair);
        }
        cursor.close();
        return list;
    }
    
    public ArrayList<Repair> getRepairByDormitoryId(String dormitoryId){
        ArrayList<Repair> list = new ArrayList<Repair>();
        String[] selectionArgs = {dormitoryId};
        Cursor cursor = db.query("repair", null, "dormitory_id=?", selectionArgs, null, null, "date DESC");
        while(cursor.moveToNext()){
            @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
            @SuppressLint("Range") String studentId = cursor.getString(cursor.getColumnIndex("student_id"));
            @SuppressLint("Range") String date = cursor.getString(cursor.getColumnIndex("date"));
            @SuppressLint("Range") String type = cursor.getString(cursor.getColumnIndex("type"));
            @SuppressLint("Range") String description = cursor.getString(cursor.getColumnIndex("description"));
            @SuppressLint("Range") String status = cursor.getString(cursor.getColumnIndex("status"));
            @SuppressLint("Range") String handler = cursor.getString(cursor.getColumnIndex("handler"));
            @SuppressLint("Range") String handleDate = cursor.getString(cursor.getColumnIndex("handle_date"));
            @SuppressLint("Range") String remark = cursor.getString(cursor.getColumnIndex("remark"));
            Repair repair = new Repair(id, studentId, dormitoryId, date, type, description, status, handler, handleDate, remark);
            list.add(repair);
        }
        cursor.close();
        return list;
    }
    
    // 访客相关操作
    public void addVisitor(String name, String idCard, String phone, String visitDate, String visitTime, String visitReason, String roomNumber){
        db.execSQL("INSERT INTO visitor(name, id_card, phone, visit_date, visit_time, visit_reason, room_number) VALUES(?,?,?,?,?,?,?)",
                new Object[]{name, idCard, phone, visitDate, visitTime, visitReason, roomNumber});
    }
    
    public void updateVisitor(int id, String name, String idCard, String phone, String visitDate, String visitTime, String visitReason, String roomNumber){
        db.execSQL("UPDATE visitor SET name=?, id_card=?, phone=?, visit_date=?, visit_time=?, visit_reason=?, room_number=? WHERE id=?",
                new Object[]{name, idCard, phone, visitDate, visitTime, visitReason, roomNumber, id});
    }
    
    public void deleteVisitor(int id){
        db.execSQL("DELETE FROM visitor WHERE id=?", new Object[]{id});
    }
    
    public ArrayList<Visitor> getAllVisitors(){
        ArrayList<Visitor> list = new ArrayList<Visitor>();
        Cursor cursor = db.query("visitor", null, null, null, null, null, "visit_date DESC, visit_time DESC");
        while(cursor.moveToNext()){
            @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
            @SuppressLint("Range") String idCard = cursor.getString(cursor.getColumnIndex("id_card"));
            @SuppressLint("Range") String phone = cursor.getString(cursor.getColumnIndex("phone"));
            @SuppressLint("Range") String visitDate = cursor.getString(cursor.getColumnIndex("visit_date"));
            @SuppressLint("Range") String visitTime = cursor.getString(cursor.getColumnIndex("visit_time"));
            @SuppressLint("Range") String visitReason = cursor.getString(cursor.getColumnIndex("visit_reason"));
            @SuppressLint("Range") String roomNumber = cursor.getString(cursor.getColumnIndex("room_number"));
            Visitor visitor = new Visitor();
            visitor.setId(id);
            visitor.setName(name);
            visitor.setIdCard(idCard);
            visitor.setPhone(phone);
            visitor.setVisitDate(visitDate);
            visitor.setVisitTime(visitTime);
            visitor.setVisitReason(visitReason);
            visitor.setRoomNumber(roomNumber);
            list.add(visitor);
        }
        cursor.close();
        return list;
    }
    
    public ArrayList<Visitor> getVisitorsByDate(String date){
        ArrayList<Visitor> list = new ArrayList<Visitor>();
        String[] selectionArgs = {date};
        Cursor cursor = db.query("visitor", null, "visit_date=?", selectionArgs, null, null, "visit_time DESC");
        while(cursor.moveToNext()){
            @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
            @SuppressLint("Range") String idCard = cursor.getString(cursor.getColumnIndex("id_card"));
            @SuppressLint("Range") String phone = cursor.getString(cursor.getColumnIndex("phone"));
            @SuppressLint("Range") String visitTime = cursor.getString(cursor.getColumnIndex("visit_time"));
            @SuppressLint("Range") String visitReason = cursor.getString(cursor.getColumnIndex("visit_reason"));
            @SuppressLint("Range") String roomNumber = cursor.getString(cursor.getColumnIndex("room_number"));
            Visitor visitor = new Visitor();
            visitor.setId(id);
            visitor.setName(name);
            visitor.setIdCard(idCard);
            visitor.setPhone(phone);
            visitor.setVisitDate(date);
            visitor.setVisitTime(visitTime);
            visitor.setVisitReason(visitReason);
            visitor.setRoomNumber(roomNumber);
            list.add(visitor);
        }
        cursor.close();
        return list;
    }
}

