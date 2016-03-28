package cn.com.byg.collecttest.db;

import java.io.Serializable;

import android.os.Parcel;
import android.os.Parcelable;

public class Student implements Parcelable{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private int id;             // 学生id
    private int sex;            // 学生性别
    private float english;      // 英语成绩
    private float math;         // 数学成绩
    private String name;        // 学生姓名
    
    
    public Student() {
        super();
    }

    @Override
    public int hashCode() {
    	return super.hashCode();
    }
    
    public Student(int id, int sex, float english, float math, String name) {
        super();
        this.id = id;
        this.sex = sex;
        this.english = english;
        this.math = math;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public float getEnglish() {
        return english;
    }

    public void setEnglish(float english) {
        this.english = english;
    }

    public float getMath() {
        return math;
    }

    public void setMath(float math) {
        this.math = math;
    }

    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Student [id=" + id + ", sex=" + sex + ", english=" + english + ", math=" + math + ", name=" + name
                + "]";
    }

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		
	}
    
}
