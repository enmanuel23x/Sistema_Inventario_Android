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
import android.widget.Toast;

import unknown.Sistema_Inventario_Android.ADD.add_vendedores;
import unknown.Sistema_Inventario_Android.TABLAS.ConexionSQLiteHelper;
import unknown.Sistema_Inventario_Android.EDIT.edit_vendedores;
import unknown.Sistema_Inventario_Android.R;
import unknown.Sistema_Inventario_Android.TABLAS.tab_vendedor;
import unknown.Sistema_Inventario_Android.menu;

public class vendedores extends AppCompatActivity {
    ImageView back;
    FloatingActionButton add;
    ListView lista;
    ConexionSQLiteHelper conn=new ConexionSQLiteHelper(this, tab_vendedor.TABLA_VENDEDORES,null,1);
    ArrayAdapter<String> adapter;
    int grade;
    @Override
    public void onBackPressed(){
        backf();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendedores);
        grade = Integer.parseInt(getIntent().getExtras().get("grade").toString());
        lista= (ListView) findViewById(R.id.lista_contenido);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(grade<3) {
                    Intent intent = new Intent(getApplicationContext(), edit_vendedores.class);
                    intent.putExtra("iterator", i);
                    intent.putExtra("grade", grade);
                    startActivityForResult(intent, 2);
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(),"Nivel de usuario insuficiente",Toast.LENGTH_SHORT).show();
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
                if(grade<3) {
                    Intent intent = new Intent(getApplicationContext(), add_vendedores.class);
                    intent.putExtra("grade", grade);
                    startActivityForResult(intent, 2);
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(),"Nivel de usuario insuficiente",Toast.LENGTH_SHORT).show();
                }
            }
        });
        if(grade==2){
            add.hide();
        }
        fillData();
    }
    private void backf(){
        Intent intent = new Intent (getApplicationContext(), menu.class);
        intent.putExtra("grade",grade);
        startActivityForResult(intent,0);
        finish();
    }
    private  void fillData() {
        SQLiteDatabase db=conn.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM "+ tab_vendedor.TABLA_VENDEDORES+" ORDER BY " + tab_vendedor.CAMPO_NOMBRE + " asc",null);
        if (checkdb(c)) {
            c.moveToFirst();
            do{
                adapter.add(c.getString(1) + "\nRIF: "+ c.getString(2)
                        +"\nTelefono: "+ c.getString(4)+"\nCorreo: "+ c.getString(5)
                +"\nDireccion:"+c.getString(3));
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
}
