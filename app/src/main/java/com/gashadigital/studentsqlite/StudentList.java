package com.gashadigital.studentsqlite;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class StudentList extends Fragment {

    StdDB dBmain;
    SQLiteDatabase sqLiteDatabase;
    ListView lv;
    ArrayList<Student> modelArrayList = new ArrayList<>();

    public StudentList() {
        // Required empty public constructor
    }

    public static StudentList newInstance(String param1, String param2) {
        StudentList fragment = new StudentList();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dBmain = new StdDB(getContext());
        findid();
        displayData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_student_list, container, false);
    }

    private void findid() {
        lv = getActivity().findViewById(R.id.student_listview);
    }

    private void displayData() {
        sqLiteDatabase  = dBmain.getReadableDatabase();
        Log.w("String", "String");

        Cursor cursor = sqLiteDatabase.rawQuery("select * from course",null);
        while (cursor.moveToNext()){
            int id= cursor.getInt(0);
            String name = cursor.getString(1);
            byte[]image = cursor.getBlob(2);

            modelArrayList.add(new Student(id,name,image));

            //custom adapter
            Custom adapter = new Custom(getContext(),R.layout.student_card,modelArrayList);
            lv.setAdapter(adapter);

        }
    }

    private class Custom extends BaseAdapter {
        private Context context;
        private  int layout;
        private ArrayList<Student>modelArrayList = new ArrayList<>();
        // constructor

        public Custom(Context context, int layout, ArrayList<Student> modelArrayList) {
            this.context = context;
            this.layout = layout;
            this.modelArrayList = modelArrayList;
        }
        private  class  ViewHolder{
            TextView txtname;
            Button edit,delete;
            ImageView imageView;
            CardView cardView;
        }

        @Override
        public int getCount() {
            return modelArrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return modelArrayList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout,null);
            //find id
            holder.txtname = convertView.findViewById(R.id.txtname);
            holder.imageView = convertView.findViewById(R.id.imageview);
            holder.edit = convertView.findViewById(R.id.btn_edit);
            holder.delete = convertView.findViewById(R.id.btn_delete);
            holder.cardView = convertView.findViewById(R.id.cardview);
            convertView.setTag(holder);

            Student model = modelArrayList.get(position);
            // set text in text view
            holder.txtname.setText(model.getName());
            // set image in image view
            byte[]image = model.getImage();
            Bitmap bitmap = BitmapFactory.decodeByteArray(image,0,image.length);
            holder.imageView.setImageBitmap(bitmap);

            holder.edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle data = new Bundle();
                    data.putInt("id",model.getId());
                    data.putString("name",model.getName());
                    data.putByteArray("avatar",model.getImage());

                    Intent intent = new Intent(getContext(),MainActivity.class);
                    intent.putExtra("userdata",data);
                    startActivity(intent);
                }
            });

            // delete record
            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sqLiteDatabase = dBmain.getReadableDatabase();
                    long recdelete = sqLiteDatabase.delete("course","id="+model.getId(),null);
                    if(recdelete!=-1){
                        Snackbar.make(v,"record deleted="+2+"\n"+model.getName(),Snackbar.LENGTH_LONG).show();
                        modelArrayList.remove(position);
                        notifyDataSetChanged();
                    }


                }
            });

            return convertView;
        }
    }
}