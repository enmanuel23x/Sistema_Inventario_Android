package unknown.Sistema_Inventario_Android;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import unknown.Sistema_Inventario_Android.TABLAS.ConexionSQLiteHelper;
import unknown.Sistema_Inventario_Android.TABLAS.tab_user;

public class Main extends AppCompatActivity{
    Button continuar;
    EditText u,p;
    ConexionSQLiteHelper conn=new ConexionSQLiteHelper(this, tab_user.TABLA_USUARIOS,null,1);
    CheckBox cb;
    int band2=0;

    @Override
    public void onBackPressed(){
        backf();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SQLiteDatabase db=conn.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM "+ tab_user.TABLA_USUARIOS+" ORDER BY " + tab_user.CAMPO_USER + " asc",null);
        if (!checkdb(c)){
            initdbuser();
        }
        u=(EditText) findViewById(R.id.user);
        p=(EditText) findViewById(R.id.pass);
        continuar =  (Button) findViewById(R.id.bcontinuar);
        continuar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                VerificarUsuarios();
            }
        });
        cb =  (CheckBox) findViewById(R.id.cb_password);
        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    p.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else{
                    p.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
    }

    private void backf(){
        finish();
        android.os.Process.killProcess(android.os.Process.myPid());
    }
    private void VerificarUsuarios() {
        SQLiteDatabase db=conn.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM "+ tab_user.TABLA_USUARIOS+" ORDER BY " + tab_user.CAMPO_USER + " asc",null);
        if (checkdb(c)) {
            c.moveToFirst();
            do{
                if(c.getString(1).equals(u.getText().toString())){
                    if(c.getString(2).equals(p.getText().toString())){
                        band2=0;
                        Intent intent = new Intent (getApplicationContext(), menu.class);
                        intent.putExtra("grade",c.getString(3));
                        startActivityForResult(intent,1);
                        finish();
                        break;
                    }else{
                        band2=1;
                    }
                }else if(band2!=1){
                    band2=2;
                }

            }while(c.moveToNext());
            if(band2==1){
                Toast.makeText(getApplicationContext()," Usuario y contraseña no coinciden",Toast.LENGTH_SHORT).show();
            }else if(band2==2){
                Toast.makeText(getApplicationContext()," Usuario no existe",Toast.LENGTH_SHORT).show();
            }
            band2=0;
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

            rowExists = false;
        }
        return rowExists;
    }
    private void initdbuser() {
        SQLiteDatabase db=conn.getWritableDatabase();
        ContentValues values=new ContentValues();
        //0
        values.put(tab_user.ID_USER,"0");
        values.put(tab_user.CAMPO_USER,"admin");
        values.put(tab_user.CAMPO_PASS,"admin");
        values.put(tab_user.CAMPO_RANK,"1");
        db.insert(tab_user.TABLA_USUARIOS, tab_user.ID_USER,values);
        db.close();
    }

}
