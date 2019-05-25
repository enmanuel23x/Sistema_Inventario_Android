package unknown.Sistema_Inventario_Android.ADD;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import unknown.Sistema_Inventario_Android.MAIN_OPTIONS.usuarios;
import unknown.Sistema_Inventario_Android.R;
import unknown.Sistema_Inventario_Android.TABLAS.ConexionSQLiteHelper;
import unknown.Sistema_Inventario_Android.TABLAS.tab_user;

public class add_usuarios extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    ConexionSQLiteHelper conn=new ConexionSQLiteHelper(this, tab_user.TABLA_USUARIOS,null,1);
    ImageView back;
    Spinner user_levels;
    EditText user,pass;
    TextView id;
    FloatingActionButton y;
    String Row;
    int grade;
    @Override
    public void onBackPressed(){
        backf();
    }
    private void backf(){
        Intent intent = new Intent (getApplicationContext(), usuarios.class);
        intent.putExtra("grade",grade);
        startActivityForResult(intent,1);
        finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_usuarios);
        grade = Integer.parseInt(getIntent().getExtras().get("grade").toString());
        SQLiteDatabase db=conn.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM "+ tab_user.TABLA_USUARIOS+" ORDER BY " + tab_user.ID_USER+ " asc",null);
        c.moveToLast();
        Row=""+(Integer.parseInt(c.getString(0))+1);
        id =(TextView)  findViewById(R.id.id);
        id.setText(Row);
        user_levels=(Spinner) findViewById(R.id.level);
        String [] userlevel = new String[]{""};
        switch (grade){
            case 1:
                userlevel= new String[]{
                        "2","3","4"
                };
                break;
            case 2:
                userlevel= new String[]{
                        "3","4"
                };
                break;
            case 3:
                userlevel= new String[]{
                        "4"
                };
                break;
        }
        List<String> stringlist;
        stringlist= new ArrayList<>(Arrays.asList(userlevel));
        ArrayAdapter<String> adapter;
        adapter= new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,stringlist);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        user_levels.setAdapter(adapter);
        user_levels.setOnItemSelectedListener(this);
        user= (EditText) findViewById(R.id.user);
        pass= (EditText) findViewById(R.id.pass);
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                onBackPressed();
            }
        });
        y = (FloatingActionButton) findViewById(R.id.b_add);
        y.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if (isStr(user.getText().toString().toUpperCase())){
                    if(isStr(pass.getText().toString().toUpperCase())) {
                        if (!isExist(user.getText().toString().toUpperCase())) {
                            registrar();
                            backf();
                            Toast.makeText(getApplicationContext(), "Registrado con exito", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "El nombre de usuario ya esta registrado", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(getApplicationContext(),"Contraseña esta vacio",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(),"Usuario esta vacio",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void registrar() {
        ContentValues values=new ContentValues();
        SQLiteDatabase db=conn.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM "+ tab_user.TABLA_USUARIOS+" ORDER BY " + tab_user.ID_USER+ " asc",null);
        c.moveToLast();
        values.put(tab_user.ID_USER,Row);
        values.put(tab_user.CAMPO_USER,user.getText().toString().toUpperCase());
        values.put(tab_user.CAMPO_PASS,pass.getText().toString().toUpperCase());
        values.put(tab_user.CAMPO_RANK,user_levels.getSelectedItem().toString());
        db.insert(tab_user.TABLA_USUARIOS, tab_user.ID_USER,values);
        c = db.rawQuery("SELECT * FROM "+ tab_user.TABLA_USUARIOS,null);
        c.moveToFirst();
        c.close();
        db.close();
    }
    private boolean isExist(String n){
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(this, tab_user.TABLA_USUARIOS,null,1);
        SQLiteDatabase db=conn.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM "+ tab_user.TABLA_USUARIOS+" ORDER BY " + tab_user.CAMPO_USER + " asc",null);
        if (checkdb(c)) {
            c.moveToFirst();
            do{
                if(n==c.getString(1) || n.equals(c.getString(1)) ) {
                    c.close();
                    db.close();
                    return true;
                }
            }while(c.moveToNext());
            c.close();
            db.close();
            return false;

        } else{
            c.close();
            db.close();
            return false;
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
    private boolean isStr(String ed_text){
        if(ed_text.isEmpty() || ed_text.length() == 0 || ed_text.equals("") || ed_text == null)
        {
            return false;
        }
        else
        {
            return true;
        }
    }
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        }
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

        }
}
