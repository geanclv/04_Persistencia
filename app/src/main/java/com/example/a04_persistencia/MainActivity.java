package com.example.a04_persistencia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button btnCrear;
    private Button btnEliminar;

    private CarSQLiteHelper carSQLiteHelper;
    private SQLiteDatabase db;

    private ListView listView;
    private MyAdapter adapter;

    private List<Car> lstCar = new ArrayList<Car>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initControls();

        btnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crear();
                actualizar();
            }
        });
        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                borrarTodo();
                actualizar();
            }
        });

        //Abrimos la base de datos en modo escritura
        carSQLiteHelper = new CarSQLiteHelper(this, "bdAuto", null, 1);
        db = carSQLiteHelper.getWritableDatabase();

        adapter = new MyAdapter(this, R.layout.list_item, lstCar);
        listView.setAdapter(adapter);

        actualizar();
    }

    private List<Car> obtenerTodos() {
        //Selecciona todos los registos de la tabla
        Cursor cursor = db.rawQuery("SELECT * FROM car", null);
        List<Car> lst = new ArrayList<Car>();

        //Recorriendo el cursor
        if(cursor.moveToFirst()){
            //Iteramos el cursor de resultados
            while(cursor.isAfterLast() == false){
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String nombre = cursor.getString(cursor.getColumnIndex("nombre"));
                String color = cursor.getString(cursor.getColumnIndex("color"));

                lst.add(new Car(id, nombre, color));
                cursor.moveToNext();
            }
        }
        return lst;
    }

    private void crear(){
        //Validamos si hemos abierto correctamente la bd
        if(db != null){
            //Creamos el registro a insertar
            ContentValues nuevoRegistro = new ContentValues();
            //El ID es autoincremental
            nuevoRegistro.put("nombre", "nombreAuto");
            nuevoRegistro.put("color", "colorAuto");

            //Insertamos el registro
            db.insert("car", null, nuevoRegistro);
        }
    }

    private void actualizar(){
        //Borramos todos los elementos de la bd
        lstCar.clear();
        //Obtenemos todos los elementos
        lstCar.addAll(obtenerTodos());
        //Refrescamos el adaptador
        adapter.notifyDataSetChanged();
    }

    private void borrarTodo(){
        db.delete("car", "", null);
    }

    @Override
    protected void onDestroy() {
        //Cerramos la conexión a la BD antes de destruir el activity
        db.close();
        super.onDestroy();
    }

    private void initControls(){
        listView = findViewById(R.id.listview);
        btnCrear = findViewById(R.id.btnCrear);
        btnEliminar = findViewById(R.id.btnEliminar);
    }
}
