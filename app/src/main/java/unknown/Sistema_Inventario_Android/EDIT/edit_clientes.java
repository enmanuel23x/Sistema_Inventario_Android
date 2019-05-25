package unknown.Sistema_Inventario_Android.EDIT;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentValues;
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
import android.widget.Spinner;
import android.widget.Toast;

import unknown.Sistema_Inventario_Android.MAIN_OPTIONS.Clientes;
import unknown.Sistema_Inventario_Android.TABLAS.ConexionSQLiteHelper;
import unknown.Sistema_Inventario_Android.R;
import unknown.Sistema_Inventario_Android.TABLAS.tab_client;

public class edit_clientes extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    ImageView back,edit;
    EditText n,r,d;
    Spinner rifident;
    FloatingActionButton yes;
    int iterator;
    String nombre,rif2,direccion;
    int band_edit=0;
    int grade;
    ConexionSQLiteHelper conn=new ConexionSQLiteHelper(this, tab_client.TABLA_CLIENTE,null,1);
    @Override
    public void onBackPressed(){
        backf();
    }
    private void backf(){
        Intent intent = new Intent (getApplicationContext(), Clientes.class);
        intent.putExtra("grade",grade);
        startActivityForResult(intent,1);
        finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_clientes);
        grade = Integer.parseInt(getIntent().getExtras().get("grade").toString());
        rifident=(Spinner) findViewById(R.id.spinner_rif);
        ArrayAdapter<CharSequence> adapter= ArrayAdapter.createFromResource(this,R.array.rif_ident,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        n=(EditText) findViewById(R.id.nombre);
        r=(EditText) findViewById(R.id.rif2);
        d=(EditText) findViewById(R.id.direccion);
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                backf();
            }
        });
        iterator = Integer.parseInt(getIntent().getExtras().get("iterator").toString());
        rifident.setAdapter(adapter);
        rifident.setOnItemSelectedListener(this);
        ReadData();
        n.setText(nombre);
        rifident.setEnabled(false);
        n.setEnabled(false);
        r.setText(rif2);
        r.setEnabled(false);
        d.setText(direccion);
        d.setEnabled(false);
        edit = (ImageView) findViewById(R.id.edit);
        yes = (FloatingActionButton) findViewById(R.id.b_yes);
        edit.setOnClickListener(new View.OnClickListener(){
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View view){
                if(band_edit==0){
                    band_edit=1;
                    rifident.setEnabled(true);
                    n.setEnabled(true);
                    r.setEnabled(true);
                    d.setEnabled(true);
                    yes.setVisibility(View.VISIBLE);
                    edit.setImageResource(R.mipmap.ic_x);
                }else{
                    promptDialogedit();
                }
            }
        });
        yes.setOnClickListener(new View.OnClickListener(){
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View view){
                if (isStr(n.getText().toString().toUpperCase())){
                    if (isInt(r.getText().toString()) && r.getText().toString().length()==9){
                        if (isStr(d.getText().toString())){
                            promptDialogyes();
                            Toast.makeText(getApplicationContext(),"Registrado con exito",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getApplicationContext(),"Direccion esta vacio",Toast.LENGTH_SHORT).show();
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
    private  void ReadData() {
        SQLiteDatabase db=conn.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM "+ tab_client.TABLA_CLIENTE+" ORDER BY " + tab_client.CAMPO_NOMBRE + " asc",null);
        c.moveToPosition(iterator);
        String ident=c.getString(1).toString().substring(0,1);
        switch(ident){
            case "E":
                rifident.setSelection(0);
                break;
            case "G":
                rifident.setSelection(1);
                break;
            case "J":
                rifident.setSelection(2);
                break;
            case "V":
                rifident.setSelection(3);
                break;
        }
        nombre = c.getString(1).toString().toUpperCase();
        rif2 = c.getString(2).toString().substring(2);
        direccion = c.getString(3).toString();
        db.close();
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
    private void registrar() {
        SQLiteDatabase db=conn.getWritableDatabase();
        ContentValues values=new ContentValues();
        Cursor c = db.rawQuery("SELECT "+ tab_client.ID_CLIENTES+" FROM "+ tab_client.TABLA_CLIENTE+" ORDER BY " + tab_client.ID_CLIENTES+ " asc",null);
        String Row="0";
        if (checkdb(c)) {
            c.moveToLast();
            Row=""+(Integer.parseInt(c.getString(0))+1);
        }
        c.close();
        values.put(tab_client.ID_CLIENTES,Row);
        values.put(tab_client.CAMPO_NOMBRE,n.getText().toString().toUpperCase());
        values.put(tab_client.CAMPO_RIF,rifident.getSelectedItem().toString()+"-"+r.getText().toString());
        values.put(tab_client.CAMPO_DIRECCION,d.getText().toString());

        db.insert(tab_client.TABLA_CLIENTE, tab_client.CAMPO_NOMBRE,values);
        c = db.rawQuery("SELECT * FROM "+ tab_client.TABLA_CLIENTE,null);
        c.moveToFirst();
        Toast.makeText(getApplicationContext(),"Nombre : "+c.getString(0).toString(),Toast.LENGTH_SHORT).show();
        db.close();
    }
    private void promptDialogedit() {
        final EditText edtText = new EditText(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Eliminar");
        builder.setMessage("¿Eliminar de la lista de clientes?");
        builder.setCancelable(false);
        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SQLiteDatabase db=conn.getWritableDatabase();
                db.delete(tab_client.TABLA_CLIENTE, tab_client.CAMPO_NOMBRE + "='" + nombre+"'", null);
                db.close();
                Toast.makeText(getApplicationContext(),n.getText().toString().toUpperCase() + " fue eliminado de  los clientes",Toast.LENGTH_SHORT).show();
                backf();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.show();
    }
    private void promptDialogyes() {
        final EditText edtText = new EditText(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Modificar");
        builder.setMessage("¿Modificar en el inventario?");
        builder.setCancelable(false);
        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(DialogInterface dialog, int which) {
                band_edit=0;
                SQLiteDatabase db=conn.getReadableDatabase();
                db.delete(tab_client.TABLA_CLIENTE, tab_client.CAMPO_NOMBRE + "='" + nombre+"'", null);
                registrar();
                n.setEnabled(false);
                r.setEnabled(false);
                d.setEnabled(false);
                yes.setVisibility(View.INVISIBLE);
                edit.setImageResource(R.mipmap.ic_edit);
                Toast.makeText(getApplicationContext(),"Cambio registrado con exito",Toast.LENGTH_SHORT).show();
                backf();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.show();
    }
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
