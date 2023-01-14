package com.example.education.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Event implements Parcelable {
    private final int image;
    private final String fullName, date, reference,information;
    private final List<Test> tests;

    public Event(int image, String fullName, String date, String reference, String information, List<Test> tests) {
        this.image = image;
        this.fullName = fullName;
        this.date = date;
        this.reference = reference;
        this.information = information;
        this.tests = tests;
    }

    protected Event(Parcel in) {
        image = in.readInt();
        fullName = in.readString();
        date = in.readString();
        reference = in.readString();
        information = in.readString();
        tests = in.createTypedArrayList(Test.CREATOR);
    }

    public static final Creator<Event> CREATOR = new Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel in) {
            return new Event(in);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };

    public int getImage() {
        return image;
    }

    public String getFullName() {
        return fullName;
    }

    public String getDate() {
        return date;
    }

    public String getReference() {
        return reference;
    }

    public String getInformation() {
        return information;
    }

    public List<Test> getTests() {
        return tests;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(image);
        parcel.writeString(fullName);
        parcel.writeString(date);
        parcel.writeString(reference);
        parcel.writeString(information);
        parcel.writeTypedList(tests);
    }
}
