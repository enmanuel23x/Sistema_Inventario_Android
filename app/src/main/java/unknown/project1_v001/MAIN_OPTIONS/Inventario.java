package unknown.project1_v001.MAIN_OPTIONS;

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

import unknown.project1_v001.ADD.add_inventario;
import unknown.project1_v001.TABLAS.ConexionSQLiteHelper;
import unknown.project1_v001.EDIT.edit_inventario;
import unknown.project1_v001.R;
import unknown.project1_v001.TABLAS.tab_inventario;
import unknown.project1_v001.menu;
import unknown.project1_v001.menu_admin;

public class Inventario extends AppCompatActivity {
    ImageView back;
    FloatingActionButton add;
    ListView lista;
    ConexionSQLiteHelper conn=new ConexionSQLiteHelper(this, tab_inventario.TABLA_INVENTARIO,null,1);
    ArrayAdapter<String> adapter;
    int grade;
    @Override
    public void onBackPressed(){
        backf();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventario);
        grade = Integer.parseInt(getIntent().getExtras().get("grade").toString());
        lista= (ListView) findViewById(R.id.lista_contenido);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(grade==1) {
                    Intent intent = new Intent(getApplicationContext(), edit_inventario.class);
                    intent.putExtra("iterator", i);
                    intent.putExtra("grade", grade);
                    startActivityForResult(intent, 2);
                    finish();
                }
            }
        });
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                onBackPressed();
            }
        });
        add = (FloatingActionButton) findViewById(R.id.b_add);
        add.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(grade==1) {
                    Intent intent = new Intent(getApplicationContext(), add_inventario.class);
                    intent.putExtra("grade", grade);
                    startActivityForResult(intent, 2);
                    finish();
                }
            }
        });
        if(grade==2){
            add.hide();
        }
        fillData();
    }
    private  void fillData() {
        SQLiteDatabase db=conn.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM "+ tab_inventario.TABLA_INVENTARIO+" ORDER BY " + tab_inventario.CAMPO_NOMBRE + " asc",null);
        if (checkdb(c)) {
            c.moveToFirst();
            do{
                adapter.add(c.getString(1) + "\nPrecio: "+ c.getString(2)+"$\nDisponible: "+ c.getString(3));
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
        if(grade<=2){
            intent = new Intent (getApplicationContext(), menu_admin.class);
        }
        intent.putExtra("grade",grade);
        startActivityForResult(intent,0);
        finish();
    }

}
