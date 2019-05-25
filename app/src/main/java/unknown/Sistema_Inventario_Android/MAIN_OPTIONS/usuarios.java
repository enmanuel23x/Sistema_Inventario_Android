package unknown.Sistema_Inventario_Android.MAIN_OPTIONS;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import unknown.Sistema_Inventario_Android.TABLAS.ConexionSQLiteHelper;
import unknown.Sistema_Inventario_Android.R;
import unknown.Sistema_Inventario_Android.TABLAS.tab_user;
import unknown.Sistema_Inventario_Android.menu;
import unknown.Sistema_Inventario_Android.ADD.add_usuarios;

public class usuarios extends AppCompatActivity {
    ImageView back;
    FloatingActionButton add;
    ListView lista;
    ConexionSQLiteHelper conn=new ConexionSQLiteHelper(this, tab_user.TABLA_USUARIOS,null,1);
    ArrayAdapter<String> adapter;
    ArrayList<String> ids = new ArrayList<>();
    int grade;
    int max=0;
    @Override
    public void onBackPressed(){
        backf();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuarios);
        grade = Integer.parseInt(getIntent().getExtras().get("grade").toString());
        lista= (ListView) findViewById(R.id.lista_contenido);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(i>max) {
                    promptDialogedit(i);
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
                    Intent intent = new Intent(getApplicationContext(), add_usuarios.class);
                    intent.putExtra("grade", grade);
                    startActivityForResult(intent, 1);
                    finish();

            }
        });
        fillData();
    }
    private  void fillData() {
        SQLiteDatabase db=conn.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM "+ tab_user.TABLA_USUARIOS+" ORDER BY " + tab_user.ID_USER + " asc",null);
        if (checkdb(c)) {
            c.moveToFirst();
            do{
                if(Integer.parseInt(c.getString(3).toString())>grade || grade==1) {
                    adapter.add("Usuario: " + c.getString(1) + "\nContraseña: " + c.getString(2) + "\nRank: " + c.getString(3));
                    ids.add(c.getString(0));
                    if (c.getString(3).equals(grade)) {
                        max++;
                    }
                }
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
    private void promptDialogedit(final int position) {
        final EditText edtText = new EditText(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Eliminar");
        builder.setMessage("¿Eliminar de los usuarios?");
        builder.setCancelable(false);
        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SQLiteDatabase db2=conn.getReadableDatabase();
                Cursor c = db2.rawQuery("SELECT * FROM "+ tab_user.TABLA_USUARIOS+" ORDER BY " + tab_user.ID_USER + " asc",null);
                c.moveToPosition(Integer.parseInt(ids.get(position)));
                String nombre=c.getString(1);
                SQLiteDatabase db=conn.getWritableDatabase();
                db.delete(tab_user.TABLA_USUARIOS, tab_user.ID_USER + "='" + Integer.parseInt(ids.get(position))+"'", null);
                db.close();
                Toast.makeText(getApplicationContext(),nombre + " fue eliminado de los usuarios",Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.show();
    }
}
