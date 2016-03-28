package cn.com.byg.collecttest.db;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;



/**
 * 数据库操作层
 * @author NeoPi
 * @date
 */
public class DatabaseManager {
    
    private MyDBOpenHelper mHelper = null;
    
    private static DatabaseManager instance = null;
    
    private SQLiteDatabase sdb = null;
    public static DatabaseManager getInstance(Context ctx){
        if (instance == null) {
            instance = new DatabaseManager(ctx);
        }
        return instance;
    }
    
    public DatabaseManager(Context ctx){
        mHelper = new MyDBOpenHelper(ctx, "student.db", "student", null, 1);
        sdb = mHelper.getReadableDatabase();
    }
    
    /**
     * 向表中插入数据
     * @param stu
     */
    public void insertData(Student stu){
        if (stu == null)
            return;
        String sql = "insert into student values(?,?,?,?,?);";
        Object[] bindArgs = new Object[] { 
                stu.getId(), 
                stu.getName(), 
                stu.getSex(), 
                stu.getEnglish(), 
                stu.getMath() 
        };
        sdb.execSQL(sql, bindArgs);
        Log.i("123","插入成功。。");
    }
    
    /**
     * 查询表中的所有数据 返回List
     */
    public List<Student> selectDataAll(){
//        String sql = "select * from student;";
        List<Student> list = new ArrayList<Student>();
        Cursor cursor = sdb.query("student", null, null, null, null, null, null);
        if (cursor != null) {
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                Student student = new Student();
                student.setId(cursor.getInt(cursor.getColumnIndex("id")));
                student.setName(cursor.getString(cursor.getColumnIndex("name")));
                student.setSex(cursor.getInt(cursor.getColumnIndex("sex")));
                student.setEnglish(cursor.getFloat(cursor.getColumnIndex("english")));
                student.setMath(cursor.getFloat(cursor.getColumnIndex("math")));
                list.add(student);
            }
        }
        return list;
    }
    
    /**
     * 查询表中的数据，返回cursor集合
     * @return
     */
    public Cursor selectAll(){
        String sql = "select * from student;";
        
        return sdb.rawQuery(sql, null);
    }
    
    /**
     * 根据id来删除表中的数据
     * @param id
     */
    public void deleteData(int id){
        String sql = "delete from student where id=?;";
        
        sdb.execSQL(sql, new Object[]{id});
    }
    
    public void close(){
        if (sdb != null) {
            sdb.close();
        }
    }
}
