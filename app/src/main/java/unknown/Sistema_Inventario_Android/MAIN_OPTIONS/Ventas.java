package unknown.Sistema_Inventario_Android.MAIN_OPTIONS;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import unknown.Sistema_Inventario_Android.ADD.add_ventas;
import unknown.Sistema_Inventario_Android.TABLAS.ConexionSQLiteHelper;
import unknown.Sistema_Inventario_Android.EDIT.edit_inventario;
import unknown.Sistema_Inventario_Android.R;
import unknown.Sistema_Inventario_Android.TABLAS.tab_vent;
import unknown.Sistema_Inventario_Android.menu;

public class Ventas extends AppCompatActivity {
    ImageView back;
    FloatingActionButton add;
    ListView lista;
    ConexionSQLiteHelper conn=new ConexionSQLiteHelper(this, tab_vent.TABLA_VENTA,null,1);
    ArrayAdapter<String> adapter;
    int grade;
    @Override
    public void onBackPressed(){
        backf();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ventas);
        grade = Integer.parseInt(getIntent().getExtras().get("grade").toString());
        lista= (ListView) findViewById(R.id.lista_contenido);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                /*EMPTY*/
            }
        });
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);
        fillData();
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                backf();
            }
        });
        add = (FloatingActionButton) findViewById(R.id.b_add);
        add.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent (getApplicationContext(), add_ventas.class);
                intent.putExtra("grade",grade);
                startActivityForResult(intent,2);
                finish();
            }
        });

    }
    private  void fillData() {
        SQLiteDatabase db=conn.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM "+ tab_vent.TABLA_VENTA+" ORDER BY " + tab_vent.ID_VENTAS + " asc",null);
        if (checkdb(c)) {
            c.moveToFirst();
            do{
                adapter.add(c.getString(0) + "\nCliente: "+ c.getString(1));
            }while(c.moveToNext());
            lista.setAdapter(adapter);
            db.close();
        }
    }
    public Boolean checkdb(Cursor c){
        Boolean rowExists;

        if (c.moveToFirst())
        {
            // DO SOMETHING WITH CURSOR
            rowExists = true;

        } else
        {
            // I AM EMPTY
            rowExists = false;
        }
        return rowExists;
    }
    private void backf(){
        Intent intent = new Intent (getApplicationContext(), menu.class);
        intent.putExtra("grade",grade);
        startActivityForResult(intent,0);
        finish();
    }
}
