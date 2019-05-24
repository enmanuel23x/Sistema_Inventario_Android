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

import unknown.project1_v001.ADD.add_clientes;
import unknown.project1_v001.TABLAS.ConexionSQLiteHelper;
import unknown.project1_v001.EDIT.edit_clientes;
import unknown.project1_v001.R;
import unknown.project1_v001.TABLAS.tab_client;
import unknown.project1_v001.menu;
import unknown.project1_v001.menu_admin;

public class Clientes extends AppCompatActivity {
    ImageView back;
    FloatingActionButton add;
    ListView lista;
    ConexionSQLiteHelper conn=new ConexionSQLiteHelper(this, tab_client.TABLA_CLIENTE,null,1);
    int grade;
    ArrayAdapter<String> adapter;
    @Override
    public void onBackPressed(){
        backf();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clientes);
        grade = Integer.parseInt(getIntent().getExtras().get("grade").toString());
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                backf();
            }
        });
        lista= (ListView) findViewById(R.id.lista_contenido);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent (getApplicationContext(), edit_clientes.class);
                intent.putExtra("iterator",i);
                intent.putExtra("grade",grade);
                startActivityForResult(intent,2);
                finish();
            }
        });
        add = (FloatingActionButton) findViewById(R.id.b_add);
        add.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent (getApplicationContext(), add_clientes.class);
                intent.putExtra("grade",grade);
                startActivityForResult(intent,2);
                finish();
            }
        });
        fillData();
    }//oncreate
    private  void fillData() {
        SQLiteDatabase db=conn.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM "+ tab_client.TABLA_CLIENTE+" ORDER BY " + tab_client.CAMPO_NOMBRE + " asc",null);
        if (checkdb(c)) {
            c.moveToFirst();
            do{
                adapter.add(c.getString(1) + "\n RIF: "+ c.getString(2));
            }while(c.moveToNext());
            lista.setAdapter(adapter);
            c.close();
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
        int grade = Integer.parseInt(getIntent().getExtras().get("grade").toString());
        Intent intent = new Intent (getApplicationContext(), menu.class);
        if(grade<=2){
            intent = new Intent (getApplicationContext(), menu_admin.class);
        }
        intent.putExtra("grade",grade);
        startActivityForResult(intent,0);
        finish();
    }
}
