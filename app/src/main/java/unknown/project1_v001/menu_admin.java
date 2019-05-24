package unknown.project1_v001;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import unknown.project1_v001.MAIN_OPTIONS.Clientes;
import unknown.project1_v001.MAIN_OPTIONS.Inventario;
import unknown.project1_v001.MAIN_OPTIONS.Ventas;
import unknown.project1_v001.MAIN_OPTIONS.usuarios;
import unknown.project1_v001.MAIN_OPTIONS.vendedores;

public class menu_admin extends AppCompatActivity {
    LinearLayout binventario;
    LinearLayout bvendedores;
    LinearLayout bventas;
    LinearLayout bclientes;
    LinearLayout buser;
    int grade ;
    int band=0;
    @Override
    public void onBackPressed(){
        backf();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_admin);
        grade = Integer.parseInt(getIntent().getExtras().get("grade").toString());
        binventario =  (LinearLayout) findViewById(R.id.b_inventario);
        binventario.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if (band==0){
                    band=1;
                    Intent intent = new Intent (getApplicationContext(), Inventario.class);
                    intent.putExtra("grade",grade);
                    startActivityForResult(intent,1);
                    finish();
                }
            }
        });
        bventas =  (LinearLayout) findViewById(R.id.b_ventas);
        bventas.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent (getApplicationContext(), Ventas.class);
                intent.putExtra("grade",grade);
                startActivity(intent);
            }
        });
        bclientes =  (LinearLayout) findViewById(R.id.b_clientes);
        bclientes.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent (getApplicationContext(), Clientes.class);
                intent.putExtra("grade",grade);
                startActivity(intent);
            }
        });
        bvendedores =  (LinearLayout) findViewById(R.id.b_vendedores);
        bvendedores.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent (getApplicationContext(), vendedores.class);
                intent.putExtra("grade",grade);
                startActivity(intent);
            }
        });
        buser =  (LinearLayout) findViewById(R.id.b_user);
        buser.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent (getApplicationContext(), usuarios.class);
                intent.putExtra("grade",grade);
                startActivity(intent);
            }
        });
    }
    private void backf(){
        Intent intent = new Intent (getApplicationContext(), Main.class);
        startActivityForResult(intent,0);
        finish();
    }
}
