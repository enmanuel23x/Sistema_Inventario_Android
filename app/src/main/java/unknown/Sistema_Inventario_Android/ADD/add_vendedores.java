package unknown.Sistema_Inventario_Android.ADD;

import android.content.ContentValues;
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
import android.widget.Spinner;
import android.widget.Toast;

import unknown.Sistema_Inventario_Android.TABLAS.ConexionSQLiteHelper;
import unknown.Sistema_Inventario_Android.R;
import unknown.Sistema_Inventario_Android.TABLAS.tab_vendedor;
import unknown.Sistema_Inventario_Android.MAIN_OPTIONS.vendedores;

public class add_vendedores extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    ImageView back;
    Spinner rifident;
    EditText n,r,t,em,d;
    FloatingActionButton y;
    int grade;
    @Override
    public void onBackPressed(){
        backf();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vendedores);
        grade = Integer.parseInt(getIntent().getExtras().get("grade").toString());
        rifident=(Spinner) findViewById(R.id.spinner_rif);
        ArrayAdapter<CharSequence> adapter= ArrayAdapter.createFromResource(this,R.array.rif_ident,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        rifident.setAdapter(adapter);
        rifident.setOnItemSelectedListener(this);
        n= (EditText) findViewById(R.id.nombre);
        r= (EditText) findViewById(R.id.rif2);
        t= (EditText) findViewById(R.id.telefono);
        em= (EditText) findViewById(R.id.email);
        d= (EditText) findViewById(R.id.direccion);
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
                if (isStr(n.getText().toString().toUpperCase())){
                    if (isInt(r.getText().toString()) && r.getText().toString().length()==9){
                        if (isStr(d.getText().toString())){
                            if(!isExist(rifident.getSelectedItem().toString()+"-"+r.getText().toString())){
                                registrar();
                                Toast.makeText(getApplicationContext(),"Registrado con exito",Toast.LENGTH_SHORT).show();
                                backf();
                            }else{
                                Toast.makeText(getApplicationContext(),"El vendedor ya esta registrado",Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(getApplicationContext()," Direccion esta vacio",Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(getApplicationContext(),"Error en RIF",Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(getApplicationContext(),"Nombre esta vacio",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private boolean isExist(String n){
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(this, tab_vendedor.TABLA_VENDEDORES,null,1);
        SQLiteDatabase db=conn.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM "+ tab_vendedor.TABLA_VENDEDORES+" ORDER BY " + tab_vendedor.CAMPO_NOMBRE + " asc",null);
        if (checkdb(c)) {
            c.moveToFirst();
            do{
                if(n==c.getString(2) || n.equals(c.getString(2)) ) {
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
    private void backf(){
        Intent intent = new Intent (getApplicationContext(), vendedores.class);
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
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(this, tab_vendedor.TABLA_VENDEDORES,null,1);
        SQLiteDatabase db=conn.getWritableDatabase();
        ContentValues values=new ContentValues();
        Cursor c = db.rawQuery("SELECT "+ tab_vendedor.ID_VENDEDORES+" FROM "+ tab_vendedor.TABLA_VENDEDORES+" ORDER BY " + tab_vendedor.ID_VENDEDORES+ " asc",null);
        String Row="0";
        if (checkdb(c)) {
            c.moveToLast();
            Row=""+(Integer.parseInt(c.getString(0))+1);
        }
        c.close();
        values.put(tab_vendedor.ID_VENDEDORES,Row);
        values.put(tab_vendedor.CAMPO_NOMBRE,n.getText().toString().toUpperCase());
        values.put(tab_vendedor.CAMPO_RIF,rifident.getSelectedItem().toString()+"-"+r.getText().toString());
        values.put(tab_vendedor.CAMPO_DIRECCION,d.getText().toString());
        values.put(tab_vendedor.CAMPO_TELEFONO,t.getText().toString());
        values.put(tab_vendedor.CAMPO_EMAIL,em.getText().toString());
        db.insert(tab_vendedor.TABLA_VENDEDORES, tab_vendedor.ID_VENDEDORES,values);
        c = db.rawQuery("SELECT * FROM "+ tab_vendedor.TABLA_VENDEDORES,null);
        c.moveToFirst();
        c.close();
        db.close();
    }
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
