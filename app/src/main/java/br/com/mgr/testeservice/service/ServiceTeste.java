package br.com.mgr.testeservice.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import br.com.mgr.testeservice.objetos.ObLogsEnvio;


public class ServiceTeste extends Service {


    private static final String LOG = "ServiceTeste";
    public enum ServiceEnviosEstado {
        STATUS_NAO_INICIADO, STATUS_RODANDO, STATUS_PAUSANDO, STATUS_PAUSADO, STATUS_FINALIZADO;
    }

    private final IBinder localBinder = new LocalBinder(this);
    private volatile ListenerLogEnvios listenerLogEnvios;
    private volatile ServiceEnviosEstado enumEstado = ServiceEnviosEstado.STATUS_NAO_INICIADO;

    public class LocalBinder extends Binder {
        private ServiceTeste serviceTeste;

        public LocalBinder(ServiceTeste serviceTeste) {
            this.serviceTeste = serviceTeste;
        }

        public ServiceTeste getService() {
            return serviceTeste;
        }


    }

    private boolean isRunning() {
        return enumEstado.equals(ServiceEnviosEstado.STATUS_RODANDO);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return localBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        statService();
        return START_NOT_STICKY;
    }


    private void statService() {
        if (enumEstado.equals(ServiceEnviosEstado.STATUS_NAO_INICIADO)) {
            enumEstado = ServiceEnviosEstado.STATUS_RODANDO;

            Thread threadProcessaClasses = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (!enumEstado.equals(ServiceEnviosEstado.STATUS_FINALIZADO)) {
                        try {
                            if (isRunning()) {
                                processar();
                            }

                            int count = 0;
                            while (count++ < 10) {
                                if (enumEstado.equals(ServiceEnviosEstado.STATUS_PAUSANDO)) {
                                    processar();
                                    enumEstado = ServiceEnviosEstado.STATUS_PAUSADO;
                                }
                                Thread.sleep(1_000);
                            }
                        } catch (Throwable tw) {
                            tw.printStackTrace();
                        }
                    }
                }
            });
            threadProcessaClasses.setName(LOG + "-ProcessaClasses");
            threadProcessaClasses.setDaemon(true);
            threadProcessaClasses.start();
        }

    }

    private void processar() {
        inserirLog("PROCESSANDO: " + this.getClass().getName());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        enumEstado = ServiceEnviosEstado.STATUS_FINALIZADO;
        inserirLog("onDestroy: " + enumEstado.name());
        setListenerLogEnvios(null);
    }

    protected void inserirLog(String mensagem) {
        inserirLog(mensagem, ObLogsEnvio.VERBOSE, null);
    }

    protected void inserirLog(String mensagem, Throwable e) {
        inserirLog(mensagem, ObLogsEnvio.ERROR, e);
    }

    protected void inserirLog(String mensagem, int verbosidade, Throwable e) {
        if (verbosidade == ObLogsEnvio.ERROR) {
            Log.e(LOG, mensagem, e);
            mensagem = mensagem + ":" + e.getMessage();
        } else {
            Log.d(LOG, mensagem);
        }
        if (listenerLogEnvios != null) {
            listenerLogEnvios.setMensagemLog(new ObLogsEnvio(mensagem, verbosidade));
        }
    }

    public void setPause(boolean pausa) {
        if (pausa) {
            enumEstado = ServiceEnviosEstado.STATUS_PAUSANDO;
        } else {
            enumEstado = ServiceEnviosEstado.STATUS_RODANDO;
        }
    }

    public void setPauseTemp(final long time) {
        setPause(true);

        new Thread("setPauseTemp") {
            @Override
            public void run() {
                try {
                    Thread.sleep(time);
                } catch (Throwable t) {
                    t.printStackTrace();
                }
                setPause(false);
            }
        }.start();
    }

    public ServiceEnviosEstado getEstado() {
        return enumEstado;
    }

    public void setListenerLogEnvios(ListenerLogEnvios listenerLogEnvios) {
        this.listenerLogEnvios = listenerLogEnvios;
    }


}
