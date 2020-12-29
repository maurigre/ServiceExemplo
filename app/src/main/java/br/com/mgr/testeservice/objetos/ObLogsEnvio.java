package br.com.mgr.testeservice.objetos;

/**
 * Created by Guilherme Biff Zarelli on 17/06/16.
 */
public class ObLogsEnvio {

    public static final int VERBOSE = 1;
    public static final int ERROR = 2;

    private String mensagemLog;
    private int verbosidade;

    public ObLogsEnvio() {
    }

    public ObLogsEnvio(String mensagemLog, int verbosidade) {
        this.mensagemLog = mensagemLog;
        this.verbosidade = verbosidade;
    }

    public String getMensagemLog() {
        return mensagemLog;
    }

    public void setMensagemLog(String mensagemLog) {
        this.mensagemLog = mensagemLog;
    }

    public int getVerbosidade() {
        return verbosidade;
    }

    public void setVerbosidade(int verbosidade) {
        this.verbosidade = verbosidade;
    }
}
