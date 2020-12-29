package br.com.mgr.testeservice.service;

import android.os.Bundle;

import br.com.mgr.testeservice.R;
import br.com.mgr.testeservice.objetos.ObLogsEnvio;

public interface ListenerLogEnvios {
    static final String PARAM_INT_QUANTIDADE = "qtd";

    enum StatusEnvio {
        AGUARDANDO(R.string.aguardando),
        SINCRONIZADO(R.string.sincronizado),
        TRANSFERINDO(R.string.transferindo),
        ERRO(R.string.erro),
        PROCESSANDO(R.string.processando),
        SEM_CONEXAO(R.string.sem_conexao),;


        private int descricao;

        StatusEnvio(int descricao) {
            this.descricao = descricao;
        }

        public int getDescricao() {
            return descricao;
        }
    }

    void setMensagemLog(ObLogsEnvio mensagemLog);

    void setStatusEnvio(Class tClass, StatusEnvio statusEnvio, Bundle params);


}
