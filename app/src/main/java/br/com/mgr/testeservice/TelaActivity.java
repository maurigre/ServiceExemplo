package br.com.mgr.testeservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import br.com.mgr.testeservice.service.ServiceTeste;

public class TelaActivity extends AppCompatActivity implements ServiceConnection, View.OnClickListener {


    private ServiceTeste serviceTeste;
    private Button bt_chama_main;
    private Button bt_pausa_service;
    private Button bt_start_service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela);

        bt_chama_main = findViewById(R.id.bt_chama_main);
        bt_pausa_service = findViewById(R.id.bt_pausa_service);
        bt_start_service = findViewById(R.id.bt_start_service);
        bt_chama_main.setOnClickListener(this);
        bt_pausa_service.setOnClickListener(this);
        bt_start_service.setOnClickListener(this);

        bindService(new Intent(this,ServiceTeste.class),this, 0);
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {

        if (serviceTeste == null){
            serviceTeste = ((ServiceTeste.LocalBinder) service).getService();
        //    Log.i("ServiceTeste", "Status --> " + serviceTeste.getEstado());
        }
        Log.i("ServiceTeste", "Status --> " + serviceTeste.getEstado());

    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        Log.i("ServiceTeste", "Status --> " + serviceTeste.getEstado());

    }

    @Override
    public void onClick(View v) {
        Log.i("ServiceTeste", "onClick --> " + v.getId());

        if (v.getId() == R.id.bt_chama_main) {
            finish();

        } else if(v.getId() == R.id.bt_pausa_service){
            serviceTeste.setPause(true);
            Log.i("ServiceTeste", "Status (Pausa)--> " + serviceTeste.getEstado());
        } else if(v.getId() == R.id.bt_start_service){
            serviceTeste.setPause(false);
            Log.i("ServiceTeste", "Status (Start)--> " + serviceTeste.getEstado());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
