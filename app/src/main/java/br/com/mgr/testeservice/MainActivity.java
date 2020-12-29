package br.com.mgr.testeservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import br.com.mgr.testeservice.service.ServiceTeste;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btAbreTela;
    private Button btStopService;
    private Button btStartService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btStartService = findViewById(R.id.bt_start_service);
        btStopService = findViewById(R.id.bt_stop_service);
        btAbreTela = findViewById(R.id.bt_abre_tela);

        btStartService.setOnClickListener(this);
        btStopService.setOnClickListener(this);
        btAbreTela.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.bt_start_service){
            startService(new Intent(this, ServiceTeste.class));
        } else if(v.getId() == R.id.bt_stop_service){
            stopService(new Intent(this, ServiceTeste.class));
        } else if(v.getId() == R.id.bt_abre_tela){
            startActivity(new Intent(this, TelaActivity.class));
        }


    }

    @Override
    protected void onDestroy() {
        stopService(new Intent(this, ServiceTeste.class));
        super.onDestroy();
    }
}
