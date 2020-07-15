package com.example.a04_persistencia;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class MyAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<Car> lstCar;

    public MyAdapter(Context context, int layout, List<Car> lstCar) {
        this.context = context;
        this.layout = layout;
        this.lstCar = lstCar;
    }

    @Override
    public int getCount() {
        return lstCar.size();
    }

    @Override
    public Object getItem(int position) {
        return lstCar.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        /*View Holder Pattern -> nos permite mejorar la performace debido a que almacena
         * en cache las referencias*/
        ViewHolder holder;

        if(convertView == null){
            //Inflando la vista con el layout creado (list_item)
            LayoutInflater layoutInflater = LayoutInflater.from(this.context);
            convertView = layoutInflater.inflate(this.layout, null);

            holder = new ViewHolder();
            //Agregando la referencia del textView al Holder
            holder.txtId = convertView.findViewById(R.id.txtId);
            holder.txtNombre = convertView.findViewById(R.id.txtNombre);
            holder.txtColor = convertView.findViewById(R.id.txtColor);

            //Llenando el holder
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //Obtenemos el valor actual para mostrar
        Car car = this.lstCar.get(position);
        int id = car.getId();
        String nombre = car.getNombre();
        String color = car.getColor();

        //Completando los textView
        holder.txtId.setText("" + id);
        holder.txtNombre.setText(nombre);
        holder.txtColor.setText(color);

        //Devolvemos la vista inflada y modificada
        return convertView;
    }

    static class ViewHolder {
        private TextView txtId;
        private TextView txtNombre;
        private TextView txtColor;
    }
}
