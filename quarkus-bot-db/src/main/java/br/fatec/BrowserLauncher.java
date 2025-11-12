package br.fatec;

import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.event.Observes;
import java.awt.Desktop;
import java.net.URI;
import java.io.IOException;

import org.jboss.logging.Logger;

public class BrowserLauncher {
    private static final Logger LOG = Logger.getLogger(BrowserLauncher.class);

    void onStart(@Observes StartupEvent ev) {
        String url = "http://localhost:8080";
        LOG.info("Tentando abrir o navegador em: " + url);

        try {
            //Tentativa 1: O modo Java padrão (que você estava usando)
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                Desktop.getDesktop().browse(new URI(url));
                LOG.info("Navegador aberto via java.awt.Desktop.");
            } else {
                //Tentativa 2: O 'Plano B' se o Desktop não for suportado (ex: modo headless)
                LOG.warn("java.awt.Desktop não suportado. Tentando 'Plano B' via Runtime exec...");
                openBrowserManually(url);
            }
        } catch (Exception e) {
            LOG.error("Falha ao abrir navegador via java.awt.Desktop. Tentando 'Plano B'.", e);
            try {
                //Tentativa 3: Se a Tentativa 1 deu exceção
                openBrowserManually(url);
            } catch (Exception e2) {
                LOG.error("Falha total ao abrir o navegador. Acesse manualmente: " + url, e2);
            }
        }
    }

    /**
     * Tenta abrir o navegador usando comandos nativos do sistema operacional.
     * Isso funciona mesmo em ambientes 'headless' onde o java.awt.Desktop falha.
     */
    private void openBrowserManually(String url) throws IOException {
        String os = System.getProperty("os.name").toLowerCase();
        Runtime rt = Runtime.getRuntime();

        if (os.contains("win")) {
            //Para Windows
            rt.exec("cmd /c start " + url);
            LOG.info("Executado comando 'start' do Windows.");
        } else if (os.contains("mac")) {
            //Para macOS
            rt.exec("open " + url);
            LOG.info("Executado comando 'open' do macOS.");
        } else if (os.contains("nix") || os.contains("nux") || os.contains("aix")) {
            //Para Linux/Unix
            rt.exec("xdg-open " + url);
            LOG.info("Executado comando 'xdg-open' do Linux.");
        } else {
            LOG.error("Sistema operacional não suportado para abertura automática do navegador. Acesse manualmente: " + url);
        }
    }
}