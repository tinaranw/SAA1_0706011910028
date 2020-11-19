package com.uc.saa1_0706011910028.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Student implements Parcelable {
    private String uid;
    private String email;
    private String password;
    private String name;
    private String nim;
    private String gender;
    private String age;
    private String address;
    private String token;

    public Student(){}

    public Student(String uid, String email, String password, String name, String nim, String gender, String age, String address, String token) {
        this.uid = uid;
        this.email = email;
        this.password = password;
        this.name = name;
        this.nim = nim;
        this.gender = gender;
        this.age = age;
        this.address = address;
        this.token = token;
    }

    protected Student(Parcel in) {
        uid = in.readString();
        email = in.readString();
        password = in.readString();
        name = in.readString();
        nim = in.readString();
        gender = in.readString();
        age = in.readString();
        address = in.readString();
        token = in.readString();
    }

    public static final Creator<Student> CREATOR = new Creator<Student>() {
        @Override
        public Student createFromParcel(Parcel in) {
            return new Student(in);
        }

        @Override
        public Student[] newArray(int size) {
            return new Student[size];
        }
    };

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(uid);
        parcel.writeString(email);
        parcel.writeString(password);
        parcel.writeString(name);
        parcel.writeString(nim);
        parcel.writeString(gender);
        parcel.writeString(age);
        parcel.writeString(address);
        parcel.writeString(token);
    }
}
