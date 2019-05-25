package unknown.Sistema_Inventario_Android.ADD;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import unknown.Sistema_Inventario_Android.TABLAS.ConexionSQLiteHelper;
import unknown.Sistema_Inventario_Android.MAIN_OPTIONS.Inventario;
import unknown.Sistema_Inventario_Android.R;
import unknown.Sistema_Inventario_Android.TABLAS.tab_inventario;

public class add_inventario extends AppCompatActivity {
    ImageView back;
    EditText n,p,d;
    FloatingActionButton y;
    int grade;
    @Override
    public void onBackPressed(){
        backf();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_inventario);
        grade = Integer.parseInt(getIntent().getExtras().get("grade").toString());
        n=(EditText) findViewById(R.id.nombre);
        p=(EditText) findViewById(R.id.precio);
        d=(EditText) findViewById(R.id.disponibilidad);
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                backf();
            }
        });
        y = (FloatingActionButton) findViewById(R.id.b_add);
        y.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if (isStr(n.getText().toString().toUpperCase())){
                    if (isDob(p.getText().toString())){
                        if (isInt(d.getText().toString())){
                            if(!isExist(n.getText().toString().toUpperCase())){
                                registrar();
                                Toast.makeText(getApplicationContext(),"Registrado con exito",Toast.LENGTH_SHORT).show();
                                backf();
                            }else{
                                Toast.makeText(getApplicationContext(),"El producto ya existe en inventario",Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(getApplicationContext()," Disponible no es un numero entero \n o esta vacio",Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(getApplicationContext(),"Precio no es un numero \n o esta vacio",Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(getApplicationContext(),"Nombre esta vacio",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    private boolean isExist(String n){
        int bandExist=0;
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(this, tab_inventario.TABLA_INVENTARIO,null,1);
        SQLiteDatabase db=conn.getReadableDatabase();
        String [] campos = {tab_inventario.CAMPO_NOMBRE, tab_inventario.CAMPO_PRECIO, tab_inventario.CAMPO_DISPONIBLE};
        Cursor c = db.rawQuery("SELECT * FROM "+ tab_inventario.TABLA_INVENTARIO+" ORDER BY " + tab_inventario.CAMPO_NOMBRE + " asc",null);

        if (checkdb(c)) {
            c.moveToFirst();
            do{
                if(n==c.getString(0) || n.equals(c.getString(0)) ) {
                    db.close();
                    return true;
                }
            }while(c.moveToNext());
            db.close();
            return false;

        } else{
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
    private void backf(){
        Intent intent = new Intent (getApplicationContext(), Inventario.class);
        intent.putExtra("grade",grade);
        startActivityForResult(intent,1);
        finish();
    }
    private boolean isInt(String numero){
        try {
            int num = Integer.parseInt(numero);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    private boolean isDob(String numero){
        try {
            double num = Double.parseDouble(numero);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
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
    private void registrar() {
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(this, tab_inventario.TABLA_INVENTARIO,null,1);
        SQLiteDatabase db=conn.getWritableDatabase();
        ContentValues values=new ContentValues();
        Cursor c = db.rawQuery("SELECT "+ tab_inventario.ID_INVENTARIO+" FROM "+ tab_inventario.TABLA_INVENTARIO+" ORDER BY " + tab_inventario.ID_INVENTARIO+ " asc",null);
        String Row="0";
        if (checkdb(c)) {
            c.moveToLast();
            Row=""+(Integer.parseInt(c.getString(0))+1);
        }
        c.close();
        values.put(tab_inventario.ID_INVENTARIO,Row);
        values.put(tab_inventario.CAMPO_NOMBRE,n.getText().toString().toUpperCase());
        values.put(tab_inventario.CAMPO_PRECIO,p.getText().toString());
        values.put(tab_inventario.CAMPO_DISPONIBLE,d.getText().toString());

        db.insert(tab_inventario.TABLA_INVENTARIO, tab_inventario.CAMPO_NOMBRE,values);
        c = db.rawQuery("SELECT * FROM "+ tab_inventario.TABLA_INVENTARIO,null);
        c.moveToFirst();
        c.close();
        db.close();
    }

}
