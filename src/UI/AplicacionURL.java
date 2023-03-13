package UI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

import javax.swing.*;

public class AplicacionURL extends JFrame implements ActionListener {

    private JTextField urlField;
    private JTextArea outputArea;
    private JButton limpiarButton;
    private JButton cancelarButton;
    private JButton aceptarButton;
    private JButton ayudaButton;
    private JLabel autorLabel;

    private JPanel panelPing;
    private JTextArea pingArea;
    private JPanel panelTracert;
    private JTextArea tracertArea;
    private JPanel panelNslookup;
    private JTextArea nslookupArea;
    private JPanel panelCurl;
    private JTextArea curlArea;
    private JPanel panelTelnet;
    private JTextArea telnetArea;

    private JTabbedPane tabbedPane;

    private static final String AUTOR = "Jordi Sumba";

    private Process process;

    public AplicacionURL() {
        super("Aplicacion URL");

        urlField = new JTextField(30);
        outputArea = new JTextArea(40, 70);
        limpiarButton = new JButton("Limpiar");
        cancelarButton = new JButton("Cancelar");
        aceptarButton = new JButton("Aceptar");
        ayudaButton = new JButton("Ayuda");
        autorLabel = new JLabel("Autor: " + AUTOR);
        pingArea = new JTextArea(40, 90);
        pingArea.setEditable(false);
        tracertArea = new JTextArea(40, 90);
        tracertArea.setEditable(false);
        nslookupArea = new JTextArea(40, 90);
        nslookupArea.setEditable(false);
        curlArea = new JTextArea(40, 90);
        curlArea.setEditable(false);
        telnetArea = new JTextArea(40, 90);
        telnetArea.setEditable(false);
        tabbedPane = new JTabbedPane();

        JPanel urlPanel = new JPanel();
        urlPanel.add(new JLabel("URL: "));
        urlPanel.add(urlField);
        urlPanel.add(limpiarButton);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(ayudaButton);
        buttonPanel.add(cancelarButton);
        buttonPanel.add(aceptarButton);


        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(autorLabel, BorderLayout.WEST);
        bottomPanel.add(buttonPanel, BorderLayout.EAST);

        panelPing = new JPanel();
        panelPing.add(new JScrollPane(pingArea));

        panelTracert = new JPanel();
        panelTracert.add(new JScrollPane(tracertArea));

        panelNslookup = new JPanel();
        panelNslookup.add(new JScrollPane(nslookupArea));

        panelCurl = new JPanel();
        panelCurl.add(new JScrollPane(curlArea));

        panelTelnet = new JPanel();
        panelTelnet.add(new JScrollPane(telnetArea));

        tabbedPane.addTab("Ping", panelPing);
        tabbedPane.addTab("Tracert", panelTracert);
        tabbedPane.addTab("Nslookup", panelNslookup);
        tabbedPane.addTab("Curl", panelCurl);
        tabbedPane.addTab("Telnet", panelTelnet);

        getContentPane().add(urlPanel, BorderLayout.NORTH);
        getContentPane().add(tabbedPane, BorderLayout.CENTER);
        getContentPane().add(bottomPanel, BorderLayout.PAGE_END);

        limpiarButton.addActionListener(this);
        cancelarButton.addActionListener(this);
        aceptarButton.addActionListener(this);
        ayudaButton.addActionListener(this);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }


    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == limpiarButton) {
            urlField.setText("");
            if (panelPing.isShowing()) {
                pingArea.setText("");
            } else if (panelTracert.isShowing()) {
                tracertArea.setText("");
            } else if (panelNslookup.isShowing()) {
                nslookupArea.setText("");
            } else if (panelCurl.isShowing()) {
                curlArea.setText("");
            } else if (panelTelnet.isShowing()) {
                telnetArea.setText("");
            }
        } else if (e.getSource() == aceptarButton) {
            String url = urlField.getText().trim();
            if (!url.isEmpty()) {
                ejecutarComando("ping " + url, pingArea);
                ejecutarComando("curl " + url, curlArea);
                ejecutarTelnet(url, 80, telnetArea);
                ejecutarComando("nslookup " + url, nslookupArea);
                try {
                    Thread.sleep(300);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
                ejecutarComando("nslookup -type=soa " + url, nslookupArea);
                new Thread(new Runnable() {
                    public void run() {
                        try {
                            process = Runtime.getRuntime().exec("tracert -d " + url);
                            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
                            String linea;
                            tracertArea.append("Hecho por Jordi Sumba\n\n");
                            tracertArea.append("Comando: tracert -d " + url + "\n");
                            while ((linea = br.readLine()) != null) {
                                tracertArea.append(linea + "\n");
                            }
                            tracertArea.append("\n");
                            tracertArea.setCaretPosition(tracertArea.getDocument().getLength());
                            process.waitFor();

                            process = Runtime.getRuntime().exec("tracert " + url);
                            br = new BufferedReader(new InputStreamReader(process.getInputStream()));
                            tracertArea.append("Comando: tracert " + url + "\n");
                            while ((linea = br.readLine()) != null) {
                                tracertArea.append(linea + "\n");
                            }
                            tracertArea.append("\n");
                            tracertArea.setCaretPosition(tracertArea.getDocument().getLength());
                        } catch (IOException | InterruptedException ex) {
                            tracertArea.append("Error al ejecutar el comando: " + ex.getMessage() + "\n\n");
                            tracertArea.setCaretPosition(tracertArea.getDocument().getLength());
                        }
                    }
                }).start();
            }
        } else if (e.getSource() == ayudaButton) {
            mostrarAyuda();
        } else if (e.getSource() == cancelarButton) {
            if (process != null) {
                process.destroy();
            }
        }
    }

    private void ejecutarComando(String comando, JTextArea outputArea) {
        new Thread(new Runnable() {
            public void run() {
                try {
                    outputArea.append("Hecho por: Jordi Sumba\n\n");
                    outputArea.append("Comando: " + comando + "\n\n");
                    outputArea.setCaretPosition(outputArea.getDocument().getLength());

                    process = Runtime.getRuntime().exec(comando);
                    BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
                    String linea;
                    while ((linea = br.readLine()) != null) {
                        outputArea.append(linea + "\n");
                    }
                    outputArea.append("\n");
                    outputArea.setCaretPosition(outputArea.getDocument().getLength());
                    outputArea.setCaretPosition(outputArea.getDocument().getLength());
                } catch (IOException ex) {
                    outputArea.append("Error al ejecutar el comando: " + ex.getMessage() + "\n\n");
                    outputArea.setCaretPosition(outputArea.getDocument().getLength());
                }
            }
        }).start();
    }

    public void ejecutarTelnet(String url, int port, JTextArea outputArea) {
        new Thread(new Runnable() {
            public void run() {
                try {
                    ProcessBuilder pb = new ProcessBuilder();
                    pb.redirectErrorStream(true);
                    process = new ProcessBuilder("telnet", url, Integer.toString(port)).start();
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
                    BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
                    process.waitFor();
                    bw.newLine();
                    bw.write("get \\");
                    bw.flush();

                    String linea;
                    while ((linea = br.readLine()) != null) {
                        outputArea.append(linea + "\n");
                    }
                    outputArea.append("\n");
                    outputArea.setCaretPosition(outputArea.getDocument().getLength());
                } catch (IOException ex) {
                    outputArea.append("Error al ejecutar el comando: " + ex.getMessage() + "\n\n");
                    outputArea.setCaretPosition(outputArea.getDocument().getLength());
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            }
        ).start();
    }

    private void mostrarAyuda() {
        JFrame ventanaAyuda = new JFrame("Ayuda");
        ventanaAyuda.setSize(600, 400);
        ventanaAyuda.setLocationRelativeTo(this);
        ventanaAyuda.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JButton acercaDeButton = new JButton("Acerca de");
        acercaDeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mostrarAcercaDe();
            }
        });
        JTabbedPane pestañasAyuda = new JTabbedPane();

        JPanel panelPing = new JPanel();
        panelPing.setLayout(new BoxLayout(panelPing, BoxLayout.Y_AXIS));
        panelPing.add(new JLabel("Comando Ping"));
        panelPing.add(new JLabel("Este comando permite verificar la conectividad con un host remoto."));
        panelPing.add(new JLabel("Ejemplo: ping www.google.com"));
        pestañasAyuda.addTab("Ping", panelPing);

        JPanel panelTracert = new JPanel();
        panelTracert.setLayout(new BoxLayout(panelTracert, BoxLayout.Y_AXIS));
        panelTracert.add(new JLabel("Comando Tracert"));
        panelTracert.add(new JLabel("Este comando muestra la ruta que sigue un paquete de red hasta un host remoto."));
        panelTracert.add(new JLabel("Ejemplo: tracert www.google.com"));
        pestañasAyuda.addTab("Tracert", panelTracert);

        JPanel panelNslookup = new JPanel();
        panelNslookup.setLayout(new BoxLayout(panelNslookup, BoxLayout.Y_AXIS));
        panelNslookup.add(new JLabel("Comando Nslookup"));
        panelNslookup.add(new JLabel("Este comando permite consultar los registros DNS de un dominio."));
        panelNslookup.add(new JLabel("Ejemplo: nslookup www.google.com"));
        pestañasAyuda.addTab("Nslookup", panelNslookup);

        JPanel panelCurl = new JPanel();
        panelCurl.setLayout(new BoxLayout(panelCurl, BoxLayout.Y_AXIS));
        panelCurl.add(new JLabel("Comando Curl"));
        panelCurl.add(new JLabel("Este comando permite realizar peticiones HTTP a un servidor web."));
        panelCurl.add(new JLabel("Ejemplo: curl www.google.com"));
        pestañasAyuda.addTab("Curl", panelCurl);

        JPanel panelTelnet = new JPanel();
        panelTelnet.setLayout(new BoxLayout(panelTelnet, BoxLayout.Y_AXIS));
        panelTelnet.add(new JLabel("Comando Telnet"));
        panelTelnet.add(new JLabel("Este comando permite conectarse a un servidor remoto utilizando el protocolo Telnet."));
        panelTelnet.add(new JLabel("Ejemplo: telnet www.google.com 80"));
        pestañasAyuda.addTab("Telnet", panelTelnet);

        ventanaAyuda.getContentPane().add(pestañasAyuda);
        ventanaAyuda.add(acercaDeButton, BorderLayout.PAGE_END);
        ventanaAyuda.setVisible(true);

    }

    private void mostrarAcercaDe() {
        final String autor = "Jordi Sumba";
        final String nombreApp = "Aplicacion URL";
        final String version = "1.0";
        final String copyright = "© 2023 Jordi Sumba";
        String acercaDe = nombreApp + " - Versión " + version + "\n\n"
                + copyright + "\n\n"
                + "Creado por " + autor;

        JOptionPane.showMessageDialog(this, acercaDe, "Acerca de", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new AplicacionURL().setVisible(true);
            }
        });
    }
}
