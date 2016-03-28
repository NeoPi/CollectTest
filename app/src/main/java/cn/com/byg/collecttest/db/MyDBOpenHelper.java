package cn.com.byg.collecttest.db;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * 
 * @author NeoPi
 * @date
 */
public class MyDBOpenHelper extends SQLiteOpenHelper{

    private static String TAG = MyDBOpenHelper.class.getSimpleName();
    
    private static String CREATE_TABLE = "create table "; // 创建表的sql语句
    private static String DB_NAME = "test.db";  // 数据库文件名
    private static String TABLE = "test"; // 数据库表名
    private static int VERSION = 1; // 数据库版本号
    
    /**
     * 默认构造方法
     * @param context
     * @param name
     * @param factory
     * @param version
     */
    public MyDBOpenHelper(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    /**
     * 默认构造方法
     * @param context
     * @param name
     * @param factory
     * @param version
     * @param errorHandler
     */
    public MyDBOpenHelper(Context context, String name, CursorFactory factory, int version,
            DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }
    
    /**
     * 为了不用每次都传进数据库名和版本号而自定义的构造方法
     * @param context
     */
    public MyDBOpenHelper(Context context) {
        this(context, DB_NAME, null, VERSION);
    }
    /**
     * 更新数据库版本时调用
     * @param context
     * @param version
     */
    public MyDBOpenHelper(Context context,int version) {
        this(context, DB_NAME, null, version);
    }
    
    /**
     * 根据第二个参数，tableName 来确定创建的表名，
     * @param tableName 表名
     * @param context
     */
    public MyDBOpenHelper(Context context,String tableName) {
        this(context, DB_NAME, null, VERSION);
        if (tableName != null && !"".equals(tableName)) {
            TABLE = tableName;
        }
    }
    
    /**
     * 根据传入的参数来创建新的数据库文件，新的数据库表
     * @param context
     * @param dbName 数据库名   
     * @param tableName 表名
     * @param version
     */
    public MyDBOpenHelper(Context context,String dbName,String tableName,CursorFactory factory,int version) {
        this(context, dbName, factory, version);
        if (tableName != null && !tableName.equals("")) {
            TABLE = tableName;
        }
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        
        CREATE_TABLE = CREATE_TABLE+TABLE+"(id integer Primary Key,"
                                        + "name String,sex integer,"
                                        + "english float,math float);";
        Log.i(TAG, "sql :"+CREATE_TABLE);
        db.execSQL(CREATE_TABLE);
    }

    /**
     * 升级数据库时
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        
    }

}
